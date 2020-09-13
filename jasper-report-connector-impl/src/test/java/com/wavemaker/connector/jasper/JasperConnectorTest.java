package com.wavemaker.connector.jasper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JasperConnectorTestConfiguration.class)
public class JasperConnectorTest {

    private static String userHome = System.getProperty("user.dir");
    private static File reportDir;

    static {
        reportDir = new File(userHome + "/target/reports");
        reportDir.mkdir();
    }

    @Autowired
    private JasperConnector connectorInstance;

    @Autowired
    private DataSourceProvider dataSourceProvider;

    @Test
    public void generatePDFReport() {
        String data = getStringFromResource("department/department.json");
        File pdffile = new File(reportDir + "/dept.pdf");
        connectorInstance.generateReport(JasperExportType.PDF, "department/dept.jrxml", new HashMap<>(), data, pdffile);
        System.out.println("PDF report generated successfully");
    }

    @Test
    public void generateHTMLReport() {
        String data = getStringFromResource("department/department.json");
        File htmlFile = new File(reportDir + "/dept.html");
        connectorInstance.generateReport(JasperExportType.HTML, "department/dept.jrxml", new HashMap<>(), data, htmlFile);
        System.out.println("HTML report generated successfully");
    }

    @Test
    public void generateXLSReport() {
        String data = getStringFromResource("department/department.json");
        File xlsFile = new File(reportDir + "/dept.xls");
        connectorInstance.generateReport(JasperExportType.XLS, "department/dept.jrxml", new HashMap<>(), data, xlsFile);
        System.out.println("XLS report generated successfully");
    }

    @Test
    public void generateCSVReport() {
        String data = getStringFromResource("department/department.json");
        File csvFile = new File(reportDir + "/dept.csv");
        connectorInstance.generateReport(JasperExportType.CSV, "department/dept.jrxml", new HashMap<>(), data, csvFile);
        System.out.println("CSV report generated successfully");
    }

    @Test
    public void generateDocReport() {
        String data = getStringFromResource("department/department.json");
        File xmlFile = new File(reportDir + "/dept.doc");
        connectorInstance.generateReport(JasperExportType.DOC, "department/dept.jrxml", new HashMap<>(), data, xmlFile);
        System.out.println("DOC report generated successfully");
    }

    @Test
    public void generatePDFReportByDataSource() {
        File pdffile = new File(reportDir + "/employee.pdf");
        connectorInstance.generateReport(JasperExportType.PDF, "employee/emp.jrxml", new HashMap<>(), dataSourceProvider.getDataSource(), pdffile);
        System.out.println("PDF report generated successfully using data source");
    }

    @Test
    public void generatePDFReportByDataSourceHavingPlacceholders() {
        File pdffile = new File(reportDir + "/employee_ph.pdf");
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("age",60);
        connectorInstance.generateReport(JasperExportType.PDF, "employeeWithPlaceholders/emp.jrxml", objectObjectHashMap , dataSourceProvider.getDataSource(), pdffile);
        System.out.println("PDF report generated successfully using data source");
    }

    @Test
    public void generateXLSReportByDataSource() {
        File reportFile = new File(reportDir + "/employee.xls");
        connectorInstance.generateReport(JasperExportType.XLS, "employee/emp.jrxml", new HashMap<>(), dataSourceProvider.getDataSource(), reportFile);
        System.out.println("XLS report generated successfully using data source");
    }

    @Test
    public void generateCSVReportByDataSource() {
        File reportFile = new File(reportDir + "/employee.csv");
        try {
            File.createTempFile("tmp", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectorInstance.generateReport(JasperExportType.CSV, "employee/emp.jrxml", new HashMap<>(), dataSourceProvider.getDataSource(), reportFile);
        System.out.println("CSV report generated successfully using connector data source");
    }

    @Test
    public void generatePDFWithParamatersReport() {
        String data = getStringFromResource("deptwithparams/department.json");
        File pdffile = new File(reportDir + "/deptwithparam.pdf");

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("REPORTHEADER", "Departments by City");
        parameters.put("PAGEHEADER", "Departments Report");

        connectorInstance.generateReport(JasperExportType.PDF, "deptwithparams/deptwithparam.jrxml", parameters, data, pdffile);
        System.out.println("PDF report generated successfully");
    }

    @Test
    public void generateReportWithStream() {
        File pdffile = new File(reportDir + "/emp.pdf");
        ByteArrayOutputStream outputStream = (ByteArrayOutputStream) connectorInstance.generateReport(JasperExportType.PDF, "employee/emp.jrxml", new HashMap<>(),dataSourceProvider.getDataSource());
        byte[] bytes = outputStream.toByteArray();
        writeByte(bytes,pdffile);
        try {
            if(outputStream!= null)
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getStringFromResource(String resource) {
        try {
            InputStream jrxmlInput = this.getClass().getClassLoader().getResource(resource).openStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(jrxmlInput, writer, "UTF-8");
            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert resource to string", e);
        }
    }

    private static void writeByte(byte[] bytes, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(bytes);
            System.out.println("Successfully"
                    + " byte inserted");

            os.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}