## Connector  Introduction

Connector is a Java based backend extension for WaveMaker applications. Connectors are built as Java modules & exposes java based SDK to interact with the connector implementation.
Each connector is built for a specific purpose and can be integrated with one of the external services. Connectors are imported & used in the WaveMaker application. Each connector runs on its own container thereby providing the ability to have it’s own version of the third party dependencies.

## Features of Connectors

1. Connector is a java based extension which can be integrated with external services and reused in many Wavemaker applications.
1. Each connector can work as an SDK for an external system.
1. Connectors can be imported once in a WaveMaker application and used many times in the applications by creating multiple instances.
1. Connectors are executed in its own container in the WaveMaker application, as a result there are no dependency version conflict issues between connectors.

## About Jasper Report Connector

## Introduction
JasperReports is an open source java reporting engine. JasperReports is a Java class library, and it is meant for those Java developers who need to add reporting capabilities to their applications.

This connector will provides multiple api to generate jasper report.In addition it also support of export following export type
HTML, PDF, XLS, CSV, DOC

## Prerequisite

1. Java 1.8 or above
1. Maven 3.1.0 or above
1. Any java editor such as Eclipse, Intelij..etc
1. Internet connection


## Build
You can build this connector using following command
```
mvn clean install
```

## Deploy
You can import connector dist/jasper.zip artifact in WaveMaker studio application using file upload option.

## Use jasper connector in WaveMaker

```

@Autowired
private JasperConnector connectorInstance;

File pdffile = new File(reportDir + "/employee.pdf");
connectorInstance.generateReport(JasperExportType.PDF, "employee/emp.jrxml", new HashMap<>(), dataSourceProvider.getDataSource(), pdffile);
System.out.println("PDF report generated successfully using data source");

```

Apart from above api, there are multiple apis exposed in this connector to generate jasper reports, visit JasperConnector java class in api module.









