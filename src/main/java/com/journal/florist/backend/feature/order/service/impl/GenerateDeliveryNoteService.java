package com.journal.florist.backend.feature.order.service.impl;

import com.journal.florist.app.common.utils.jasper.JasperReportRequest;
import com.journal.florist.app.common.utils.jasper.ReportService;
import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.feature.order.enums.OrderStatus;
import com.journal.florist.backend.feature.order.model.DeliveryNote;
import com.journal.florist.backend.feature.order.model.OrderDetails;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.order.repositories.DeliveryNoteRepository;
import com.journal.florist.backend.feature.order.service.DeliveryNoteService;
import com.journal.florist.backend.feature.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.journal.florist.app.constant.JournalConstants.DELIVERY_NOTE_ALREADY_PRINTED;
import static com.journal.florist.app.constant.JournalConstants.ORDER_IS_NOT_FOR_DELIVERY;

@Service
@RequiredArgsConstructor
@Transactional
public class GenerateDeliveryNoteService implements DeliveryNoteService {

    private final DeliveryNoteRepository deliveryNoteRepository;
    private final OrderService orderService;
    private final ReportService jasperService;

    @Override
    public JasperReportRequest create(String orderId, String gnrId) {
        DeliveryNote entity = new DeliveryNote();
        Orders order = orderService.findOrderById(orderId);
        String createdBy = SecurityUtils.getAuthentication().getName();

        if(deliveryNoteRepository.existsByGnrId(gnrId)) {
            throw new IllegalException("GNR id must be unique. It's already taken");
        }

        validateOrderHasStatusSent(order);
        validatePrinted(orderId);

        entity.setGnrId(gnrId);
        entity.setOrder(order);
        entity.setPrinted(true);
        entity.setCreatedBy(createdBy);

        deliveryNoteRepository.save(entity);
        JasperReportRequest reportRequest = printedDeliveryNote(entity.getGnrId(), order.getPublicKey());

        return jasperService.generatePDFReport(reportRequest);

    }

    private JasperReportRequest printedDeliveryNote(String deliveryNoteId, String orderId) {
        JasperReportRequest request = new JasperReportRequest();

        String user = SecurityUtils.getAuthentication().getName();
        Orders order = orderService.findOrderById(orderId);

        List<String> productName = order.getOrderDetails().stream().parallel().map(product -> product.getProduct().getProductName()).toList();
        String recipientName = order.getOrderShipment().getRecipientName();
        String fullAddress = order.getOrderShipment().getDeliveryAddress().getFullAddress();
        List<Integer> quantity = order.getOrderDetails().stream().parallel().map(OrderDetails::getQuantity).toList();
        Date deliveryDate = order.getOrderShipment().getDeliveryDate();
        String sender = order.getCustomer().getName();

        var list = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("deliveryNoteId", deliveryNoteId);
        data.put("recipientName", recipientName);
        data.put("deliveryAddress", fullAddress);
        data.put("quantity", quantity);
        data.put("productName", productName);
        data.put("sender", sender);
        data.put("deliveryDate", deliveryDate);
        list.add(data);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        Map<String, Object> param = new HashMap<>();
        param.put("createdBy", user);

        request.setReportFileName("travel-document");
        request.setParameters(param);
        request.setReportData(list);
        request.setDataSource(dataSource);
        request.setOutputFileName(deliveryNoteId.concat(".pdf"));

        return request;
    }

    private void validatePrinted(String orderId) {
        boolean isPrinted = deliveryNoteRepository.orderPrinted(orderId);
        if (isPrinted) {
            throw new AppBaseException(
                    String.format(DELIVERY_NOTE_ALREADY_PRINTED, orderId)
            );
        }
    }

    private void validateOrderHasStatusSent(Orders order) {
        if(order.getOrderStatus() != OrderStatus.SENT) {
            throw new AppBaseException(
              String.format(ORDER_IS_NOT_FOR_DELIVERY)
            );
        }
    }
}
