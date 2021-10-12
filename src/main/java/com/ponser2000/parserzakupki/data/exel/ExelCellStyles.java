package com.ponser2000.parserzakupki.data.exel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;

/**
 * @author Sergey Ponomarev on 08.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.data.exel
 */
public class ExelCellStyles {

    private HSSFCellStyle style;
    private HSSFFont font;

    public ExelCellStyles(HSSFWorkbook workbook) {
        this.style = workbook.createCellStyle();
        this.font = workbook.createFont();
    }

    public HSSFCellStyle getStyle() {
        return style;
    }

    public void setBoldFont() {
        font.setBold(true);
        style.setFont(font);
    }

    public void setNormalFont() {
        font.setBold(false);
        style.setFont(font);
    }

    public void setThinBorderCell() {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
    }

    public void setMediumBorderCell() {
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
    }


}
