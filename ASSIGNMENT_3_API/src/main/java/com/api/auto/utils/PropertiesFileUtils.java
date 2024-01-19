package com.api.auto.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PropertiesFileUtils {
	
	private static String CONFIG_PATH = "./Configuration/Configs.xlsx";
	private static String TOKEN_PATH = "./Configuration/Tokens.xlsx";
	static String value;
	
	public static String getConfig (String key) throws Exception {
		
		FileInputStream fis = new FileInputStream(CONFIG_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("data");
		
		for(int i=0; i<sheet.getPhysicalNumberOfRows();i++) {
			XSSFRow row = sheet.getRow(i);
			XSSFCell cell_0 = row.getCell(0);
			if(key.equals(cell_0.getStringCellValue())) {
				XSSFCell cell_1 = row.getCell(1);
				value = cell_1.getStringCellValue();
				return value;
			}
		}
		throw new Exception("Config not found!");
	}
	
	public static void setToken(String key, String value) throws FileNotFoundException {
		

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Tokens");
		
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell_0 = row.createCell(0);
		cell_0.setCellValue(key);
		XSSFCell cell_1 = row.createCell(1);
		cell_1.setCellValue(value);
		
		try {
			FileOutputStream fos = new FileOutputStream(TOKEN_PATH);
			workbook.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getToken(String key) throws Exception {
		FileInputStream fis = new FileInputStream(TOKEN_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Tokens");
		XSSFRow row = sheet.getRow(0);
		for(int i=0; i<sheet.getPhysicalNumberOfRows(); i++) {
			XSSFCell cell_0 = row.getCell(0);
			if(key.equals(cell_0.getStringCellValue())) {
				XSSFCell cell_1 = row.getCell(1);
				value = cell_1.getStringCellValue();
				return value;
			}	
		}
		throw new Exception("Token not found!");
	}
}
	

