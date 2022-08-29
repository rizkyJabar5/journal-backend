package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.common.utils.jasper.JasperReportRequest;
import com.journal.florist.app.common.utils.jasper.ReportService;
import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.feature.order.model.DeliveryNote;
import com.journal.florist.backend.feature.order.model.OrderDetails;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.order.repositories.DeliveryNoteRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryNoteServiceImpl implements DeliveryNoteService {

    private final DeliveryNoteRepository deliveryNoteRepository;
    private final OrderService orderService;
    private final ReportService jasperService;

    @Override
    public JasperReportRequest create(String orderId, String gnrId) {
        DeliveryNote entity = new DeliveryNote();
        Orders order = orderService.findOrderById(orderId);
        String createdBy = SecurityUtils.getAuthentication().getName();

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
        var list = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("deliveryNoteId", deliveryNoteId);
        data.put("recipientName", order.getOrderShipment().getRecipientName());
        data.put("deliveryAddress", order.getOrderShipment().getDeliveryAddress().getFullAddress());
        data.put("quantity", order.getOrderDetails().stream().parallel().map(OrderDetails::getQuantity).toList());
        data.put("productName", order.getOrderDetails().stream().parallel().map(product -> product.getProduct().getProductName()).toList());
        data.put("sender", order.getCustomer().getName());
        data.put("deliveryDate", order.getOrderShipment().getDeliveryDate());
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
            throw new AppBaseException(String.format("Travel document in order %s is already printed", orderId));
        }
    }
}
