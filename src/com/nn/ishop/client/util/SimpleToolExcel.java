package com.nn.ishop.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.server.dto.AbstractIshopEntity;
import com.nn.ishop.server.dto.warehouse.ImportInvoke;

public class SimpleToolExcel<E extends AbstractIshopEntity> {
	public void exportData(List<E> listData, String[] headers, String title, String type, String fileName) {
		JFileChooser fileChooser = new JFileChooser();
		if(!fileName.endsWith(".xlsx") || !fileName.endsWith(".xls")){
			fileChooser.setSelectedFile(new File(fileName + ".xlsx"));	
		}
		int choice = fileChooser.showSaveDialog(null);
		if (choice == JFileChooser.APPROVE_OPTION) {
			File outputFile = fileChooser.getSelectedFile();
			if (!outputFile.getName().endsWith(".xlsx")) {
				outputFile = new File(outputFile.getAbsolutePath() + ".xlsx");
			}

			if (listData != null && listData.size() > 0) {
				Workbook newWorkbook = new XSSFWorkbook();
				Sheet sheet = newWorkbook.createSheet();

				// Generate Tilte
				Row row0 = sheet.createRow(0);
				Cell cell00 = row0.createCell(0);
				cell00.setCellValue(type);

				Row row1 = sheet.createRow(1);
				Cell cell13 = row1.createCell(3, 1);
				cell13.setCellValue(title);

				// generate collumn header
				Row row4 = sheet.createRow(4);
				for (int i = 0; i < headers.length; i++) {
					Cell cell = row4.createCell(i);
					cell.setCellValue(headers[i]);
				}

				// Generate data rows
				int inte = 5;
				for (E data : listData) {
					Row row = sheet.createRow(inte);
					Object[] properties = data.toObjectArray();
					for (int i = 0; i < properties.length; i++) {
						Cell cell = row.createCell(i);
						if (properties[i] != null) {
							if (properties[i] instanceof String) {
								cell.setCellValue((String) properties[i]);
							}
							if (properties[i] instanceof Long) {
								cell.setCellValue(Double.parseDouble(properties[i].toString()));
							}
							if (properties[i] instanceof Integer) {
								cell.setCellValue(Double.parseDouble(properties[i].toString()));
							}
						}
					}
					inte++;
				}

				try {
					OutputStream out = new FileOutputStream(outputFile);
					newWorkbook.write(out);
					SystemMessageDialog.showMessageDialog(null, "Exported to " +  outputFile.getName(), SystemMessageDialog.SHOW_OK_OPTION);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public List<E> importData(E object, String type, Class clazz) {
		List<E> lstData = new ArrayList<E>();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel 2003 file", "xls"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel 2007 file", "xlsx"));
		int choice = fileChooser.showOpenDialog(null);
		if (choice == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls")) {
				try {
					FileInputStream in = new FileInputStream(file);
					Workbook workbook = WorkbookFactory.create(in);
					Sheet sheet = workbook.getSheetAt(0);
					String excelType = sheet.getRow(0).getCell(0).getStringCellValue();
					if (excelType.equals(type)) {
						Row row4 = sheet.getRow(4);

						String[] headers = object.getObjectHeader();
						Method[] methods = object.getClass().getMethods();
						for (int inte = 5; inte <= sheet.getLastRowNum(); inte++) {
							Row row = sheet.getRow(inte);
							E newobject = (E) clazz.newInstance();
							newobject = bindData(newobject, methods, headers, row4, row);
							lstData.add(newobject);
						}
					} else {
						SystemMessageDialog.showMessageDialog(null, "Diffirent type of file. Choose a diffirent file",
								SystemMessageDialog.SHOW_OK_OPTION);
					}
				} catch (InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				SystemMessageDialog.showMessageDialog(null, "Wrong format! Choose an excel file instead", SystemMessageDialog.SHOW_OK_OPTION);
			}
		}
		return lstData;
	}

	public E bindData(E object, Method[] methods, String[] headers, Row headerRow, Row row) {
		E bindedObject = object;
		try {
			for (Method method : methods) {
				ImportInvoke anno = method.getAnnotation(ImportInvoke.class);
				if (anno != null) {
					for (int i = 0; i <= headers.length; i++) {
						String cellHeader = headerRow.getCell(i) != null ? headerRow.getCell(i).getStringCellValue() : null;
						String annoMapping = anno.mapping();
						if (cellHeader != null && annoMapping != null) {
							if (cellHeader.equals(annoMapping)) {
								Cell cell = row.getCell(i);
								if ("String".equals(anno.type())) {
									if (cell != null) {
										method.invoke(bindedObject, row.getCell(i).getStringCellValue());
									} else {
										method.invoke(bindedObject, "");
									}
								}
								if ("long".equals(anno.type())) {
									if (cell != null) {
										double value = row.getCell(i).getNumericCellValue();
										method.invoke(bindedObject, new Double(value).longValue());
									} else {
										method.invoke(bindedObject, 0);
									}
								}
								if ("Boolean".equals(anno.type())) {
									if (cell != null) {
										method.invoke(bindedObject, row.getCell(i).getBooleanCellValue());
									} else {
										method.invoke(bindedObject, false);
									}
								}
								if ("int".equals(anno.type())) {
									if (cell != null) {
										double value = row.getCell(i).getNumericCellValue();
										method.invoke(bindedObject, new Double(value).intValue());
									} else {
										method.invoke(bindedObject, 0);
									}
								}
								if ("double".equals(anno.type())) {
									if (cell != null) {
										double value = row.getCell(i).getNumericCellValue();
										method.invoke(bindedObject, value);
									} else {
										method.invoke(bindedObject, 0);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bindedObject;
	}
}
