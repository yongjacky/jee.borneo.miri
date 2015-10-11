package com.borneo.framework.common.utils;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * jasperReport
 */

public class ReportUtils {

    public static JRAbstractExporter getJRExporter(DocType docType) {
        JRAbstractExporter exporter = null;
        switch (docType) {
        case PDF:
            exporter = new JRPdfExporter();
            break;
        case HTML:
            exporter = new JRHtmlExporter();
            break;
        case XLS:
            exporter = new JExcelApiExporter();
            break;
        case XML:
            exporter = new JRXmlExporter();
            break;
        case RTF:
            exporter = new JRRtfExporter();
            break;
        case CSV:
            exporter = new JRCsvExporter();
            break;
        case TXT:
            exporter = new JRTextExporter();
            break;
        }
        return exporter;
    }

    /**
     * get Content type
     * @param docType
     * @return
     */
    public String getContentType(DocType docType) {
        String contentType = "text/html";
        switch (docType) {
        case PDF:
            contentType = "application/pdf";
            break;
        case XLS:
            contentType = "application/vnd.ms-excel";
            break;
        case XML:
            contentType = "text/xml";
            break;
        case RTF:
            contentType = "application/rtf";
            break;
        case CSV:
            contentType = "text/plain";
            break;
        }
        return contentType;
    }

    /**
     * define the doc type
     */
    public static enum DocType {
        PDF, HTML, XLS, XML, RTF, CSV, TXT
    }

    public static void report(String templatePath, String filePath, DocType docType, List<?> dataSource, Map parameters) throws Exception {
        // wrap the list to JRBeanCollectionDataSource
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(dataSource);
        //get jrprint file
        String jrprintFile = JasperFillManager.fillReportToFile(templatePath, parameters, collectionDataSource);
        File sourceFile = new File(jrprintFile);
        JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
        JRExporter exporter = getJRExporter(docType);

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
        exporter.exportReport();
    }

    static String fileName = "E:/workspace/js_test/jrxml/HelloWorld.jrxml";

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        //将报表的定义文件HelloWorld.jrxml编译成HelloWorld.jasper文件
        String jasperFile = JasperCompileManager.compileReportToFile(fileName);
        //向HelloWorld.jasper文件中填充数据，这一步将生产出HelloWorld .jrprint文件
        String jrprintFile = JasperFillManager.fillReportToFile(jasperFile, null, new JREmptyDataSource());
        //将.jrprint文件转换成HTML格式
        JasperExportManager.exportReportToHtmlFile(jrprintFile);
        //将.jrprint文件转换成PDF格式

        //JasperExportManager.exportReportToPdfFile(jrprintFile);
        //将.jrprint文件转换成XML格式
        JasperExportManager.exportReportToXmlFile(jrprintFile, false);
        //将.jrprint文件转换成XLS格式(即Excel文件)，需要用到POI类库.
        File sourceFile = new File(jrprintFile);
        JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".xls");
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        exporter.exportReport();
        long endTime = System.currentTimeMillis();
        long time = (endTime - startTime) / 1000;
        System.out.println("success with " + time + " s");
    }

}
