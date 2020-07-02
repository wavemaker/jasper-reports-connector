package com.wavemaker.connector.jasper.impl;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.wavemaker.connector.jasper.JasperConnector;
import com.wavemaker.connector.jasper.JasperExportType;
import com.wavemaker.connector.jasper.service.JasperConnectorService;

@Service
@Primary
public class JasperConnectorImpl implements JasperConnector {

    private static final Logger logger = LoggerFactory.getLogger(JasperConnectorImpl.class);

    @Autowired
    private JasperConnectorService jasperConnectorService;

    @Override
    public OutputStream generateReport(JasperExportType jasperExportType, String jrxmlResourcePath, Map<String, Object> parameters, DataSource dataSource) {
        return jasperConnectorService.generateReport(jrxmlResourcePath, parameters, dataSource, jasperExportType);
    }

    @Override
    public void generateReport(JasperExportType jasperExportType, String jrxmlResourcePath, Map<String, Object> parameters, DataSource dataSource, File destFile) {
        jasperConnectorService.generateReport(jrxmlResourcePath, parameters, dataSource, jasperExportType, destFile);
    }

    @Override
    public OutputStream generateReport(JasperExportType jasperExportType, String jrxmlResourcePath, Map<String, Object> parameters, String data) {
        return jasperConnectorService.generateReport(jrxmlResourcePath, parameters, data, jasperExportType);
    }

    @Override
    public void generateReport(JasperExportType jasperExportType, String jrxmlResourcePath, Map<String, Object> parameters, String data, File destFile) {
        jasperConnectorService.generateReport(jrxmlResourcePath, parameters, data, jasperExportType, destFile);
    }
}