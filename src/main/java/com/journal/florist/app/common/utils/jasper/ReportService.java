package com.journal.florist.app.common.utils.jasper;

import com.journal.florist.app.common.utils.HasLogger;
import net.sf.jasperreports.engine.JasperPrint;

import javax.servlet.http.HttpServletResponse;

public interface ReportService extends HasLogger {

    JasperReportRequest generatePDFReport(JasperReportRequest request);
    void exportToPdf(String outputFilename, JasperPrint jasperPrint, HttpServletResponse response);
}
