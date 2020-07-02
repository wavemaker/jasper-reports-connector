package com.wavemaker.connector.jasper.service;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wavemaker.connector.jasper.JasperExportType;
import com.wavemaker.connector.jasper.util.JasperExportUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 26/6/20
 */
@Service
public class JasperConnectorService {

    private static final Logger logger = LoggerFactory.getLogger(JasperConnectorService.class);

    public OutputStream generateReport(String jrxmlResourcePath, Map<String, Object> parameters, DataSource dataSource, JasperExportType jasperExportType) {
        try (Connection conn = dataSource.getConnection()) {
            logger.info("Jasper report generation started, loading jrxml file");
            JasperReport jasperReport = getJasperReport(jrxmlResourcePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            return exportReport(jasperPrint, jasperExportType);
        } catch (JRException | SQLException e) {
            throw new RuntimeException("Failed to generate jasper report", e);
        }
    }

    public void generateReport(String jrxmlResourcePath, Map<String, Object> parameters, DataSource dataSource, JasperExportType jasperExportType, File destFile) {
        try (Connection conn = dataSource.getConnection()) {
            logger.info("Jasper report generation started, loading jrxml file");
            JasperReport jasperReport = getJasperReport(jrxmlResourcePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            exportReport(jasperPrint, jasperExportType, destFile);
        } catch (JRException | FileNotFoundException | SQLException e) {
            throw new RuntimeException("Failed to generate jasper report", e);
        }
    }

    public OutputStream generateReport(String jrxmlResourcePath, Map<String, Object> parameters, String data, JasperExportType jasperExportType) {
        try {
            logger.info("Jasper report generation started, loading jrxml file");
            JasperReport jasperReport = getJasperReport(jrxmlResourcePath);
            JsonDataSource jsonDataSource = new JsonDataSource(new ByteArrayInputStream(data.getBytes()));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jsonDataSource);
            return exportReport(jasperPrint, jasperExportType);
        } catch (JRException e) {
            throw new RuntimeException("Failed to generate jasper report", e);
        }
    }

    public void generateReport(String jrxmlResourcePath, Map<String, Object> parameters, String data, JasperExportType jasperExportType, File destFile) {
        try {
            logger.info("Jasper report generation started, loading jrxml file");
            JasperReport jasperReport = getJasperReport(jrxmlResourcePath);
            JsonDataSource jsonDataSource = new JsonDataSource(new ByteArrayInputStream(data.getBytes()));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jsonDataSource);
            exportReport(jasperPrint, jasperExportType, destFile);
        } catch (JRException | FileNotFoundException e) {
            throw new RuntimeException("Failed to generate jasper report", e);
        }
    }


    private OutputStream exportReport(JasperPrint jasperPrint, JasperExportType exportType) throws JRException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        switch (exportType) {
            case PDF:
                JasperExportUtil.exportReportToPdfStream(jasperPrint, outputStream);
                break;
            case XLS:
                JasperExportUtil.exportReportXLSToStream(jasperPrint, outputStream);
                break;
            case CSV:
                JasperExportUtil.exportReportCSVToStream(jasperPrint, outputStream);
                break;
            case HTML:
                JasperExportUtil.exportReportHTMLToStream(jasperPrint, outputStream);
                break;
            case DOC:
                JasperExportUtil.exportReportDocToStream(jasperPrint, outputStream);
                break;
            default:
                throw new RuntimeException("ExportType is not support by jasper " + exportType.name());
        }

        return outputStream;

    }


    private void exportReport(JasperPrint jasperPrint, JasperExportType exportType, File destFile) throws JRException, FileNotFoundException {

        switch (exportType) {
            case PDF:
                JasperExportUtil.exportReportToPdfFile(jasperPrint, destFile.getAbsolutePath());
                break;
            case XLS:
                JasperExportUtil.exportToXLSFile(jasperPrint, destFile.getAbsolutePath());
                break;
            case CSV:
                JasperExportUtil.exportToCSVFile(jasperPrint, destFile.getAbsolutePath());
                break;
            case HTML:
                JasperExportUtil.exportReportToHtmlFile(jasperPrint, destFile.getAbsolutePath());
                break;
            case DOC:
                JasperExportUtil.exportReportToDocFile(jasperPrint, destFile.getAbsolutePath());
                break;
            default:
                throw new RuntimeException("ExportType is not support by jasper " + exportType.name());
        }
    }


    private JasperReport getJasperReport(String jrxml) throws JRException {
        InputStream jrxmlInput = getResource(jrxml);
        JasperDesign design = JRXmlLoader.load(jrxmlInput);
        return JasperCompileManager.compileReport(design);
    }

    private InputStream getResource(String jrxmlResourcePath) {
        try {
            return this.getClass().getClassLoader().getResource(jrxmlResourcePath).openStream();
        } catch (IOException e) {
            throw new RuntimeException("Resource not found in class path " + jrxmlResourcePath, e);
        }
    }

}
