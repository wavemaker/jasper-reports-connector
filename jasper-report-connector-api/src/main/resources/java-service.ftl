/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
 package ${packageName};

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.service.annotations.ExposeToClient;
import com.wavemaker.runtime.service.annotations.HideFromClient;

import com.wavemaker.runtime.file.model.DownloadResponse;
import com.wavemaker.runtime.file.model.Downloadable;

import com.wavemaker.connector.jasper.JasperConnector;
import com.wavemaker.connector.jasper.JasperExportType;
import com.wavemaker.runtime.data.datasource.WMDataSource;

 //import ${packageName}.model.*;

 /**
  * This is a singleton class with all its public methods exposed as REST APIs via generated controller class.
  * To avoid exposing an API for a particular public method, annotate it with @HideFromClient.
  *
  * Method names will play a major role in defining the Http Method for the generated APIs. For example, a method name
  * that starts with delete/remove, will make the API exposed as Http Method "DELETE".
  *
  * Method Parameters of type primitives (including java.lang.String) will be exposed as Query Parameters &
  * Complex Types/Objects will become part of the Request body in the generated API.
  *
  * NOTE: We do not recommend using method overloading on client exposed methods.
  */
 @ExposeToClient
 public class ${className} {

    private static final Logger logger = LoggerFactory.getLogger(${className}.class);

	@Autowired
    private JasperConnector jasperConnector;

    @Autowired
    @Qualifier("${r"${dataSourceName}"}")
    private WMDataSource dataSource;

	//Generating PDF report from the data source.
    public Downloadable generatePDFReportFromDataSource(String jrxmlFilePath, String outputFileName) {
         logger.info("Calling connector to generate the jasperReport");
         //Report can be generated to any HTML, PDF, XLS, CSV, DOC file type.
	 Map<String, Object> parametersMap = new HashMap<>();
	 
         ByteArrayOutputStream pdfReportStream = (ByteArrayOutputStream) jasperConnector.generateReport(JasperExportType.PDF, jrxmlFilePath, parametersMap, dataSource);

         DownloadResponse downloadableResponse = new DownloadResponse(new ByteArrayInputStream(pdfReportStream.toByteArray()), "application/pdf", outputFileName);
         downloadableResponse.setInline(false);
         return downloadableResponse;
    }

	//Generating PDF report from the response that is returned from the web service.
    public Downloadable generatePDFReport(String restServiceUrl, String jrxmlFilePath, String outputFileName) {
         //Invoking WebService to get the response
         String data = invokeService(restServiceUrl);

         logger.info("Calling connector to generate the jasperReport");
         //Report can be generated to any HTML, PDF, XLS, CSV, DOC file type.
	 Map<String, Object> parametersMap = new HashMap<>();
	 
         ByteArrayOutputStream pdfReportStream = (ByteArrayOutputStream) jasperConnector.generateReport(JasperExportType.PDF, jrxmlFilePath, parametersMap, data);

         DownloadResponse downloadableResponse = new DownloadResponse(new ByteArrayInputStream(pdfReportStream.toByteArray()), "application/pdf", outputFileName);
         downloadableResponse.setInline(false);
         return downloadableResponse;
    }

    // This method actually connects to the external web service
    private String invokeService(String restServiceUrl) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(restServiceUrl, String.class);
        logger.info(" Rest response : " + response);
        return response;
    }
 }
