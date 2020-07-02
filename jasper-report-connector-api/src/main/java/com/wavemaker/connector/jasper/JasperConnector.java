package com.wavemaker.connector.jasper;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import javax.sql.DataSource;

import com.wavemaker.runtime.connector.annotation.WMConnector;


@WMConnector(name = "jasper",
        description = "This connector is used to generate reports using jasper framework")
public interface JasperConnector {

    public OutputStream generateReport(JasperExportType jasperExportType, String jrxmlResourcePath, Map<String, Object> parameters, DataSource dataSource);

    public void generateReport(JasperExportType jasperExportType, String jrxmlResourcePath, Map<String, Object> parameters, DataSource dataSource, File destFile);

    public OutputStream generateReport(JasperExportType jasperExportType, String jrxmlResourcePath, Map<String, Object> parameters, String data);

    public void generateReport(JasperExportType jasperExportType, String jrxmlResourcePath, Map<String, Object> parameters, String data, File destFile);

}