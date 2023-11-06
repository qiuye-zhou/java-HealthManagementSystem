package com.liusp.test;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * @author cr
 * @date 2023年11月02日 8:54
 * @description 测试poi解析 excel表格
 */
public class TestPoi {
    public static void main(String[] args) throws IOException {
/*        //1.创建工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\cr\\Desktop\\hellopoi.xlsx");
        //2.获取工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        //3.获取包含有效数据每一行 rows
        for (Row row : sheet) {
            for (Cell cell : row) {
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }
        workbook.close();*/
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\\\Users\\\\cr\\\\Desktop\\\\hellopoi.xlsx");
        //获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取当前工作表最后一行的行号，行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for(int i=0;i<=lastRowNum;i++){
            //根据行号获取行对象
            XSSFRow row = sheet.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for(short j=0;j<lastCellNum;j++){
                String value = row.getCell(j).getStringCellValue();
                System.out.println(value);
            }
        }
        workbook.close();
    }
}
