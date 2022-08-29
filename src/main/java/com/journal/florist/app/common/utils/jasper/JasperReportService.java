package com.journal.florist.app.common.utils.jasper;

import lombok.Getter;
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
import org.springframework.stereotype.Service;

import java.io.InputStream;

@RequiredArgsConstructor
@Service
@Getter
public class JasperReportService implements ReportService {
    private final StorageService storageService;
    private JasperPrint jasperPrint;

    @Override
    public JasperReportRequest generatePDFReport(JasperReportRequest request) {
        byte[] bytes;
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
    public void exportToPdf(String outputFilename) {
        try {
            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFilename));

            SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");

            pdfExporter.setConfiguration(reportConfig);
            pdfExporter.setConfiguration(exportConfig);

            pdfExporter.exportReport();
        } catch (JRException e) {
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
        JasperReport jasperReport;
        Resource jasper = storageService.getJasperFile(request.getReportFileName());

        if (!jasper.exists()) {
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

}
