package com.journal.florist.backend.feature.order.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.utils.DateConverter;
import com.journal.florist.app.common.utils.jasper.JasperReportRequest;
import com.journal.florist.app.common.utils.jasper.JasperReportService;
import com.journal.florist.backend.feature.order.dto.AddOrderRequest;
import com.journal.florist.backend.feature.order.service.DeliveryNoteService;
import com.journal.florist.backend.feature.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.journal.florist.app.constant.ApiUrlConstant.ORDER_URL;

@RestController
@RequestMapping(ORDER_URL)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final DeliveryNoteService deliveryNoteService;
    private final JasperReportService jasperService;

    @PostMapping("/add-order")
    public ResponseEntity<BaseResponse> addOrder(@Valid @RequestBody AddOrderRequest request) {
        var response = orderService.addOrder(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/printed-delivery-note")
    public ResponseEntity<?> printDeliveryNote(@RequestParam(name = "order-id") String orderId,
                                               @RequestParam(name = "gnr-id") String gnrId,
                                               HttpServletResponse httpResponse) throws IOException, JRException {
        HttpHeaders headers = new HttpHeaders();
        JasperReportRequest jasperRequest = deliveryNoteService.create(orderId, gnrId);
        setHeaderAndContentType(httpResponse, jasperRequest.getOutputFileName());

        jasperService.exportToPdf(jasperRequest.getReportFileName());
        JasperExportManager.exportReportToPdfStream(jasperService.getJasperPrint(), httpResponse.getOutputStream());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    private void setHeaderAndContentType(HttpServletResponse response,
                                         String outputFileName) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, getHeaderValue(outputFileName));
        response.setContentType("application/pdf");
    }

    private String getHeaderValue(String outputFileName) {
        DateTimeFormatter dateTimeFormatter = DateConverter.formatDateTime();
        String date = DateConverter.toLocalDate(new Date(System.currentTimeMillis()))
                .format(dateTimeFormatter);
        String fileName = URLEncoder
                .encode(outputFileName
                        .substring(0, 3)
                        .concat(date), StandardCharsets.UTF_8);

        return "attachment; filename="
                .concat(fileName)
                .concat(".pdf")
                .concat(";");
    }
}
