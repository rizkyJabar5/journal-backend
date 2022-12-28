package com.journal.florist.backend.feature.order.controller;

import com.journal.florist.app.common.utils.converter.DateConverter;
import com.journal.florist.app.common.utils.jasper.JasperReportRequest;
import com.journal.florist.app.common.utils.jasper.JasperReportService;
import com.journal.florist.backend.feature.order.service.DeliveryNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.journal.florist.app.constant.ApiUrlConstant.PRINTED_DELIVERY_NOTE;

@Tag(name = "Printed GNR",
        description = "Generate GNR to pdf or print")
@RequiredArgsConstructor
@RestController
@RequestMapping(PRINTED_DELIVERY_NOTE)
public class PrintedDeliveryNoteOrder {
    private final DeliveryNoteService deliveryNoteService;
    private final JasperReportService jasperService;

    @Operation(summary = "Print or Generate GNR",
            description = "Printed delivery order by order ID if GNR id is blank it will auto generate to random alpha numeric")
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<?> printDeliveryNote(@RequestParam(name = "order-id") String orderId,
                                               @RequestParam(name = "gnr-id", required = false) String gnrId,
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
        String date = DateConverter.toLocalDateTime(new Date(System.currentTimeMillis()))
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
