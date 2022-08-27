package com.journal.florist.app.common.utils.jasper;

import com.journal.florist.app.common.utils.DateConverter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JasperReportService implements ReportService {
    private final StorageService storageService;

    @Override
    public JasperReportRequest generatePDFReport(JasperReportRequest request) {
        byte[] bytes;
        JasperPrint jasperPrint;
        JasperReport jasperReport = loadJasperReport(request);

        JRDataSource dataSource = new JRBeanCollectionDataSource(request.getReportData());
        try {
            jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    request.getParameters(),
                    dataSource);
            bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        request.setContent(bytes);

        return request;
    }

    @Override
    public void exportToPdf(String outputFilename, JasperPrint jasperPrint, HttpServletResponse response) {
        try {
            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

            SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");

            pdfExporter.setConfiguration(reportConfig);
            pdfExporter.setConfiguration(exportConfig);

            setHeaderAndContentType(response, outputFilename);

            pdfExporter.exportReport();
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Find file report if file have extension(.jasper) will be load
     * if file have extension(.jrxml) will be compiled to jasper
     * and then save file to root folder /report
     *
     * @param request body fill details report
     * @return jasperReport
     */
    @SneakyThrows
    private JasperReport loadJasperReport(JasperReportRequest request) {
        JasperReport jasperReport = null;

        Resource jasper = storageService.getJasperFile(request.getReportFileName());

        if(!jasper.exists()) {
            InputStream jrxml = storageService.getJrxmlFile(request.getReportFileName());
            getLogger().info("{} loaded, compiling report", jrxml);
            jasperReport = JasperCompileManager.compileReport(jrxml);
            // Save compiled report
            JRSaver.saveObject(jasperReport, jrxml.toString().replace(".jrxml", "jasper"));

            return jasperReport;
        }

        getLogger().info("{} loaded, saving report", jasper);
        jasperReport = (JasperReport) JRLoader.loadObject(jasper.getFile());

        return jasperReport;
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

        return "attachment;filename="
                .concat(fileName)
                .concat(".pdf")
                .concat(";");
    }
}
