package com.wavemaker.connector.jasper.util;

import java.io.*;
import java.nio.file.Files;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JsonExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.*;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 29/6/20
 */
public class JasperExportUtil {

    public static void exportReportHTMLToStream(JasperPrint jasperPrint, ByteArrayOutputStream outputStream) throws JRException {
        HtmlExporter exporter = new HtmlExporter();
        SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jasperPrint);
        exporter.setExporterInput(simpleExporterInput);
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
        exporter.exportReport();
    }

    public static void exportReportXLSToStream(JasperPrint jasperPrint, ByteArrayOutputStream outputStream) throws JRException {
        JRXlsExporter exporter = new JRXlsExporter();
        SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jasperPrint);
        OutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(outputStream);
        exporter.setExporterInput(simpleExporterInput);
        exporter.setExporterOutput(simpleOutputStreamExporterOutput);
        exporter.exportReport();
    }

    public static void exportReportCSVToStream(JasperPrint jasperPrint, ByteArrayOutputStream outputStream) throws JRException {
        JRCsvExporter exporter = new JRCsvExporter();
        exporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.exportReport();
    }

    public static void exportReportDocToStream(JasperPrint jasperPrint, ByteArrayOutputStream outputStream) throws JRException {
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();
    }

    public static void exportReportToPdfStream(JasperPrint jasperPrint, ByteArrayOutputStream outputStream) throws JRException {
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }

    public static void exportReportToXmlStream(JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);
    }

    public static void exportToXLSFile(JasperPrint jasperPrint, String destFilePath) throws FileNotFoundException, JRException {
        JRXlsExporter exporter = new JRXlsExporter();
        SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jasperPrint);
        OutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(new FileOutputStream(new File(destFilePath)));
        exporter.setExporterInput(simpleExporterInput);
        exporter.setExporterOutput(simpleOutputStreamExporterOutput);

        //setting configuration
        SimpleXlsReportConfiguration reportExportConfiguration = new SimpleXlsReportConfiguration();
        reportExportConfiguration.setOnePagePerSheet(false);
        reportExportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        reportExportConfiguration.setWhitePageBackground(true);
        exporter.setConfiguration(reportExportConfiguration);

        exporter.exportReport();
    }

    public static void exportReportToHtmlFile(JasperPrint jasperPrint, String destFilePath) throws JRException {
        JasperExportManager.exportReportToHtmlFile(jasperPrint, destFilePath);
    }

    public static void exportReportToPdfFile(JasperPrint jasperPrint, String destFilePath) throws JRException {
        JasperExportManager.exportReportToPdfFile(jasperPrint, destFilePath);
    }

    public static void exportReportToXmlFile(JasperPrint jasperPrint, String destFilePath) throws JRException {
        JasperExportManager.exportReportToXmlFile(jasperPrint, destFilePath, false);
    }

    public static void exportReportToDocFile(JasperPrint jasperPrint, String destFilePath) throws JRException {
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFilePath));
        exporter.exportReport();
    }

    public static void exportToCSVFile(JasperPrint jasperPrint, String destFilePath) throws JRException {
        JRCsvExporter exporter = new JRCsvExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(destFilePath));

        SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
        configuration.setWriteBOM(Boolean.TRUE);
        configuration.setRecordDelimiter("\r\n");
        exporter.setConfiguration(configuration);

        exporter.exportReport();
    }
}
