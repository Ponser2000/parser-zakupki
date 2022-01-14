package com.ponser2000.parserzakupki.utils;

import com.ponser2000.parserzakupki.service.dto.FieldsOrder;
import com.ponser2000.parserzakupki.service.dto.Order;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Sergey Ponomarev on 08.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.utils
 */
public class ExelWorker {

    public void createWorkbook(List<Order> ordersList, String fileName) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Test page");
        AutoFilter filter = sheet.setAutoFilter(CellRangeAddress.valueOf("A2:G2"));

        CreationHelper createHelper = workbook.getCreationHelper();


        int numRow = 0;
        Row row = sheet.createRow(numRow);

        ++numRow;
        row = sheet.createRow(numRow);

        row.createCell(0).setCellValue("Номер Закупки");
        row.createCell(1).setCellValue("Форма закупки");
        row.createCell(2).setCellValue("Заказчик");
        row.createCell(3).setCellValue("Описание закупки");
        row.createCell(4).setCellValue("Цена");
        row.createCell(5).setCellValue("Размещено");
        row.createCell(6).setCellValue("Обновлено");
        row.createCell(7).setCellValue("Окончание");

        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 7000);
        sheet.setColumnWidth(2, 19500);
        sheet.setColumnWidth(3, 22500);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 3000);

        // создаем шрифт
        HSSFFont fontBold = workbook.createFont();
        // указываем, что хотим его видеть жирным

        fontBold.setFontName("Arial");
        fontBold.setFontHeightInPoints((short) 9);
        fontBold.setBold(true);

        HSSFCellStyle styleBold = workbook.createCellStyle();
        styleBold.setFont(fontBold);
        styleBold.setWrapText(true);
        styleBold.setBorderBottom(BorderStyle.MEDIUM);
        styleBold.setBorderLeft(BorderStyle.MEDIUM);
        styleBold.setBorderRight(BorderStyle.MEDIUM);
        styleBold.setBorderTop(BorderStyle.MEDIUM);
        styleBold.setVerticalAlignment(VerticalAlignment.CENTER);
        styleBold.setAlignment(HorizontalAlignment.CENTER);

        setCellStyle(row, styleBold, styleBold);

        HSSFCellStyle styleNonBold = workbook.createCellStyle();
        HSSFFont fontNonBold = workbook.createFont();
        fontNonBold.setFontName("Arial");
        fontNonBold.setFontHeightInPoints((short) 9);
        fontNonBold.setBold(false);
        styleNonBold.setFont(fontNonBold);
        styleNonBold.setWrapText(true);
        styleNonBold.setBorderBottom(BorderStyle.THIN);
        styleNonBold.setBorderLeft(BorderStyle.THIN);
        styleNonBold.setBorderRight(BorderStyle.THIN);
        styleNonBold.setBorderTop(BorderStyle.NONE);
        styleNonBold.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFCellStyle hlinkstyle = workbook.createCellStyle();
        HSSFFont hlinkfont = workbook.createFont();
        hlinkfont.setUnderline(HSSFFont.U_SINGLE);
        hlinkfont.setColor(IndexedColors.BLUE.getIndex());
        hlinkfont.setFontHeightInPoints((short) 9);
        hlinkstyle.setFont(hlinkfont);
        hlinkstyle.setBorderBottom(BorderStyle.THIN);
        hlinkstyle.setBorderLeft(BorderStyle.THIN);
        hlinkstyle.setBorderRight(BorderStyle.THIN);
        hlinkstyle.setBorderTop(BorderStyle.NONE);
        hlinkstyle.setVerticalAlignment(VerticalAlignment.CENTER);

        for (Order order : ordersList) {
            ++numRow;

            row = sheet.createRow(numRow);
            row.createCell(0).setCellValue(order.fieldsOrder().get(FieldsOrder.NUMBER));

            HSSFHyperlink link = (HSSFHyperlink) createHelper.createHyperlink(HyperlinkType.URL);
            link.setAddress(order.fieldsOrder().get(FieldsOrder.URL));
            row.getCell(0).setHyperlink(link);

            row.createCell(1).setCellValue(order.fieldsOrder().get(FieldsOrder.PURCHASE_METHOD));
            row.createCell(2).setCellValue(order.fieldsOrder().get(FieldsOrder.CUSTOMER));
            row.createCell(3).setCellValue(order.fieldsOrder().get(FieldsOrder.OBJECT_DESCRIPTION));
            row.createCell(4).setCellValue(String.format("%.2f", order.price()));

            row.createCell(5).setCellValue(order.fieldsOrder().get(FieldsOrder.DATE_OF_ALLOCATED));
            row.createCell(6).setCellValue(order.fieldsOrder().get(FieldsOrder.DATE_OF_UPDATED));
            row.createCell(7).setCellValue(order.fieldsOrder().get(FieldsOrder.DATE_OF_ENDED));

            setCellStyle(row, styleNonBold, hlinkstyle);

        }

        //try (FileOutputStream out = new FileOutputStream(new File(fileName))) {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("File created!!!");
    }

    private void setCellStyle(Row row, HSSFCellStyle styleNonBold, HSSFCellStyle hlinkstyle) {
        row.getCell(0).setCellStyle(hlinkstyle);
        row.getCell(1).setCellStyle(styleNonBold);
        row.getCell(2).setCellStyle(styleNonBold);
        row.getCell(3).setCellStyle(styleNonBold);
        row.getCell(4).setCellStyle(styleNonBold);
        row.getCell(5).setCellStyle(styleNonBold);
        row.getCell(6).setCellStyle(styleNonBold);
        row.getCell(7).setCellStyle(styleNonBold);
    }
}
