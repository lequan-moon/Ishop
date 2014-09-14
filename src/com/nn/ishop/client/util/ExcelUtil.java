package com.nn.ishop.client.util;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.nn.ishop.client.Main;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
//import com.nn.ishop.server.bo.Item;
//import com.nn.ishop.server.bo.ItemCat;
//import com.nn.ishop.server.bo.Warehouse;
import com.nn.ishop.server.jdbc.PostgreSQLConnectionPool;

public class ExcelUtil {
	static Map<String, CellStyle> cellStylesMasterStockReport = new  HashMap<String, CellStyle>();
	public static final String CELL_TITLE_STYLE = "TITLE";
	public static final String CELL_ALIGN_LEFT = "ITEM_LEFT";
	public static final String CELL_ALIGN_RIGHT = "ITEM_RIGHT";
	public static final String CELL_STYLE_INPUT_DOLLA = "INPUT_$";
	public static final String CELL_STYLE_INPUT_PERCENT = "INPUT_%";
	public static final String CELL_STYLE_INPUT_INTEGER = "INPUT_I";
	public static final String CELL_STYLE_INPUT_DECIMAL = "INPUT_D";
	
	public static final String CELL_STYLE_FORMULA_DOLLA = "FORMULA_$";
	public static final String CELL_STYLE_FORMULA_INTEGER = "FORMULA_I";
//	static ArrayList<Warehouse> warehouses = Warehouse.loadWarehousesList();
//	
//	public static Warehouse getWarehouse(String warehouseId){
//		Warehouse ret = null;
//		for(Warehouse w:warehouses)
//			if(w.getWarehouseCode().equalsIgnoreCase(warehouseId))
//				ret = w;
//		return ret;
//	}
	
//	public static void taoBaoCaoCanDoiNhapXuatTon(String fromDate
//			, String toDate
//			, String warehouseId
//			, String itemCat
//			, String itemId
//			, String itemSize
//			, String itemStyle)throws Exception{
//		Workbook wb = new HSSFWorkbook();
//		updateCellStylesMasterStockReport(wb);
//		String warehouseName = getWarehouse(warehouseId).getWarehouseName();
//		Sheet sheet = wb.createSheet("BĂ¡o CĂ¡o CĂ¢n Ä?á»‘i Nháº­p-Xuáº¥t-Tá»“n, kho " + warehouseName.toUpperCase());
//        sheet.setPrintGridlines(false);
//        sheet.setDisplayGridlines(false);
//
//        PrintSetup printSetup = sheet.getPrintSetup();
//        printSetup.setLandscape(true);
//        sheet.setFitToPage(true);
//        sheet.setHorizontallyCenter(true);
//        
//        updateCanDoiNhapXuatTonReportWorkbookHeader(wb, fromDate,toDate,warehouseName);
//        updateCanDoiNhapXuatTonReportDetailData(wb, fromDate,toDate,warehouseId,itemCat,itemId, itemSize, itemStyle);
//        
//        /** Write to file */
//        JFileChooser chooser = new JFileChooser();
//		
//		chooser.setCurrentDirectory(new File("reports"));
//		chooser.setDialogTitle("LÆ°u bĂ¡o cĂ¡o vĂ o thÆ° má»¥c");
//		
//		
//		chooser.setFileFilter(new FileFilter(){
//			
//			public boolean accept(File f) {
//				return f.getName().toLowerCase().endsWith(".xls")
//					|| f.isDirectory();	
//			}
//			
//			public String getDescription() {
//				return "MS Excel Files";
//			}
//		});
//		JFrame tempFrame = new JFrame();
//		tempFrame.setIconImage(Toolkit.getDefaultToolkit().createImage(
//				Main.class.getResource("img/logo32x32.png")));
////		WSMSFrame.centerDialog(tempFrame);
//		tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		int r = chooser.showSaveDialog(tempFrame);
//		String fileName = null;
//		if(r == JFileChooser.APPROVE_OPTION){
//			fileName = chooser.getSelectedFile().getPath();
//			if(!fileName.endsWith(".xls")){
//				CommonDialog.showMessageDialog(null, "Báº¡n chá»?n sai kiá»ƒu táº­p tin, " +
//						"tĂªn táº­p tin lÆ°u pháº£i lĂ  táº­p tin Excel (.xls)"
//						, CommonDialog.SHOW_OK_OPTION);
//				fileName += ".xls"; 
//			}
//			
//		}
//		// File chooser
//		
//		if(fileName == null || fileName.equals(""))
//			fileName = "reports/BaoCaoCanDoiNhapXuatTon.xls";
//		tempFrame.dispose();
//        FileOutputStream out = new FileOutputStream(new File(fileName));
//        wb.write(out);
//        out.close();
//		
//	}
	static void updateCanDoiNhapXuatTonReportDetailData(Workbook wb
			, String fromDate
			, String toDate
			, String warehouseId
			, String itemCat
			, String itemId
			, String itemSize
			, String itemStyle
			) throws Exception{
//		Sheet sheet = wb.getSheetAt(0);
//		sheet.autoSizeColumn((short)1);sheet.autoSizeColumn((short)2);sheet.autoSizeColumn((short)3);
//		/** Step #1 getting item list */
//		ArrayList<Item> items = Item.getItems();
//		int i=0;
//		
//		long sumCf = 0l;
//		long sumBf = 0l;
//		long sumInQuantity = 0l;
//		long sumSaleQuantity = 0l;
//		long sumTransferQuantity = 0l;
//		
//		long sumCfCash = 0l;
//		long sumBfCash = 0l;
//		long sumInQuantityCash = 0l;
//		long sumSaleQuantityCash = 0l;
//		long sumTransferQuantityCash = 0l;
//		
//		for (Item item:items)
//		{
//			Row row = sheet.createRow(12+i);
//			//*********** Valid condition *************//
//			if(itemCat != null && !itemCat.equals("") && !itemCat.equals("NONE_SELECTION"))
//				if(!item.getItemCatCode().equalsIgnoreCase(itemCat) )continue;	
//			
//			if(itemId != null && !itemId.trim().equals(""))
//				if(!item.getItemCode().equalsIgnoreCase(itemId) )continue;
//			
//			
//			if(itemSize != null && !itemSize.trim().equals(""))
//				if(!item.getItemMadeIn().equalsIgnoreCase(itemSize) )continue;
//			
//			if(itemStyle != null && !itemStyle.trim().equals(""))
//				if(!item.getItemStyle().equalsIgnoreCase(itemStyle) )continue;
//			//***********END Valid condition *************//
//			
//			row.createCell(1).setCellValue(item.getItemName());
//			row.getCell(1).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.getCell(1).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
////			row.createCell(2).setCellValue(ItemCat.getCatByCatID(item.getItemCatCode()).getCatName());		
//			row.getCell(2).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.getCell(2).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
//			row.createCell(3).setCellValue(item.getItemStyle());		
//			row.getCell(3).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.getCell(3).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
//			row.createCell(4).setCellValue(item.getItemCode());		
//			row.getCell(4).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.getCell(4).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
//			row.createCell(5).setCellValue(Long.valueOf(item.getItemMadeIn()));		
//			row.getCell(5).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.getCell(5).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
//			row.createCell(6).setCellValue(Long.valueOf(item.getItemSellingPrice()));		
//			row.getCell(6).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//			row.getCell(6).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
//			//loop for each warehouse
//			long price = Long.valueOf(item.getItemSellingPrice()).longValue();
//			long bf = getBfQuantity(fromDate
//					, warehouseId, item.getItemCode());
//			
//			
//			long inQty = getInOutQuantity(fromDate
//					,toDate, warehouseId, item.getItemCode(),true);
//			
//			long saleQty = getOutQtyFromWarehouse(fromDate
//					,toDate, warehouseId, item.getItemCode(), false);
//			long transferQty = getOutQtyFromWarehouse(fromDate
//					,toDate, warehouseId, item.getItemCode(), true);
//			long cf = (bf + inQty - saleQty - transferQty);
//			sumInQuantity += inQty;
//			sumBf+=bf;
//			sumSaleQuantity += saleQty;
//			sumTransferQuantity += transferQty;
//			sumCf += cf;
//			
//			
//			sumCfCash += cf*price;
//			sumBfCash += bf * price;
//			sumInQuantityCash += inQty * price;
//			sumSaleQuantityCash += saleQty * price;
//			sumTransferQuantityCash += transferQty * price;
//			
//			row.createCell(7).setCellValue(bf);
//			row.getCell(7).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));			
//			row.createCell(8).setCellValue(bf*price);
//			row.getCell(8).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//			
//			row.createCell(9).setCellValue(inQty);
//			row.getCell(9).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//			row.createCell(10).setCellValue(inQty*price);
//			row.getCell(10).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//			
//			
//			//  extract hang xuat ban
//			row.createCell(11).setCellValue(saleQty);
//			row.getCell(11).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//			row.createCell(12).setCellValue(saleQty*price);
//			row.getCell(12).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//			
//			//  extract hang xuat tra lai (xuat noi bo + xuat tra lai)
//			row.createCell(13).setCellValue(transferQty);
//			row.getCell(13).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//			row.createCell(14).setCellValue(transferQty*price);
//			row.getCell(14).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//			
//			//  CF
//			row.createCell(15).setCellValue(cf);
//			row.getCell(15).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//			row.createCell(16).setCellValue(cf*price);
//			row.getCell(16).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//			
//
//			i++;
//		} // END for item
//		//  create GrandSum row 11 with grand data
//		Row row = sheet.createRow(11);
//		row.createCell(1).setCellValue("Tá»”NG Cá»˜NG");row.getCell(1).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//		row.createCell(2).setCellValue("-");row.getCell(2).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//		row.createCell(3).setCellValue("-");row.getCell(3).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//		row.createCell(4).setCellValue("-");row.getCell(4).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//		row.createCell(5).setCellValue("-");row.getCell(5).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//		row.createCell(6).setCellValue("-");row.getCell(6).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//		
//		row.createCell(7).setCellValue(sumBf);row.getCell(7).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//		row.createCell(8).setCellValue(sumBfCash);row.getCell(8).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//		row.createCell(9).setCellValue(sumInQuantity);row.getCell(9).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//		row.createCell(10).setCellValue(sumInQuantityCash);row.getCell(10).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//		row.createCell(11).setCellValue(sumSaleQuantity);row.getCell(11).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//		row.createCell(12).setCellValue(sumSaleQuantityCash);row.getCell(12).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//		row.createCell(13).setCellValue(sumTransferQuantity);row.getCell(13).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//		row.createCell(14).setCellValue(sumTransferQuantityCash);row.getCell(14).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//		row.createCell(15).setCellValue(sumCf);row.getCell(15).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//		row.createCell(16).setCellValue(sumCfCash);row.getCell(16).setCellStyle(getCellStyles(wb).get(CELL_STYLE_INPUT_DOLLA));
//		
//		
	}
	
	static long getOutQtyFromWarehouse(String fromDate, String toDate, String warehouseId, String itemId, boolean isInternalTransfer){
		long ret = 0l;
		String sql1 = " Select sum(retint(t2.item_quantity)) as so_xuat "
					+ " from sic_retailer.item_order as t1,"
					+ " sic_retailer.item_order_association as t2 "
					+ " where t1.id = t2.order_id " 
					+ " and t2.item_id =?"
					+ " and t1.ORDER_TYPE='SALE_ORDER' "
					+ " AND t1.WAREHOUSE_FROM =? "
					+ " AND t1.WAREHOUSE_TO='KHACH_HANG' " 
					+ " AND (to_date(t1.issue_date, 'dd/MM/yyyy') between to_date('"
					+fromDate+"', 'dd/MM/yyyy') and to_date('"+toDate+"', 'dd/MM/yyyy')) ";	
		String sql2 = " Select sum(retint(t2.item_quantity)) as so_xuat "
					+ " from sic_retailer.item_order as t1,"
					+ " sic_retailer.item_order_association as t2 "
					+ " where t1.id = t2.order_id "
					+ " and t2.item_id =?"
					+ " and t1.ORDER_TYPE='INTERNAL_TRANSFER_ORDER' OR  t1.ORDER_TYPE='GOODS_RETURN_ORDER'"
					+ " AND t1.WAREHOUSE_FROM =? "
					+ " AND t1.WAREHOUSE_TO <> 'KHACH_HANG' "				
					+ " AND (to_date(t1.issue_date, 'dd/MM/yyyy') between to_date('"
					+ fromDate +"', 'dd/MM/yyyy') and to_date('"+toDate+"', 'dd/MM/yyyy') )";	
		
		 try {
			Connection conn = PostgreSQLConnectionPool.getConnection();
			PreparedStatement stmt = conn.prepareStatement(isInternalTransfer?sql2:sql1);
			stmt.setString(1, itemId);
			stmt.setString(2, warehouseId);
			
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			while( rs.next()){
				ret = rs.getLong("so_xuat");
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	static long getPOQtyFromCentral(String warehouseId, String itemId){
		long ret = 0l;
		
		String sql = "Select sum(retint(t2.item_quantity)) as so_nhap_tu_cong_ty "
		+ " from sic_retailer.item_receipt as t1,"
		+" sic_retailer.items_receipt_association as t2 "
		+" where t1.id = t2.receipt_id "
		+" and t1.PO_TYPE='CENTRAL_NORMAL_PO' "
		+" AND t1.WAREHOUSE_FROM <> 'KHACH_HANG' "
		+" AND t1.WAREHOUSE_TO=? " 
		+" and t2.item_id =? " ;
		 try {
			Connection conn = PostgreSQLConnectionPool.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, warehouseId);
			stmt.setString(2, itemId);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			while( rs.next()){
				ret = rs.getLong("so_nhap_tu_cong_ty");
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
	static void updateCanDoiNhapXuatTonReportWorkbookHeader(Workbook wb
			, String fromDate
			, String toDate
			, String warehouseName
	){
		Sheet sheet = wb.getSheetAt(0);
		Row companyLogoRow = sheet.createRow(1);
//		ImageIcon logo = new ImageIcon("report/nhabe_logo.png");
//		wb.addPicture(n, Workbook.PICTURE_TYPE_PNG);
		
		Row companyTitleRow = sheet.createRow(2);
		companyTitleRow.setHeightInPoints(25);
		companyTitleRow.createCell(3).setCellValue("CĂ”NG TY Cá»” PHáº¦N KINH DOANH NHĂ€ BĂˆ PHĂ?A Báº®C");
		companyTitleRow.getCell(3).setCellStyle(getCellStyles(wb).get(CELL_TITLE_STYLE_COMPANY));		
		
		Row companyAddressRow = sheet.createRow(3);
		companyAddressRow.setHeightInPoints(25);
		companyAddressRow.createCell(3).setCellValue("Ä?C: 1,3,5 Ä?INH TIĂ�N HOĂ€NG - HOĂ€N KIáº¾M - HĂ€ Ná»˜I - Ä?T:043 8242 739");
		companyAddressRow.getCell(3).setCellStyle(getCellStyles(wb).get(CELL_TITLE_STYLE_ADDRESS));
		
		   Row titleRow = sheet.createRow(4);
	        titleRow.setHeightInPoints(35);
	        for (int i = 1; i <17; i++) 
	            titleRow.createCell(i).setCellStyle(getCellStyles(wb).get(CELL_TITLE_STYLE));
	        
	        Cell titleCell = titleRow.getCell(1);
	        titleCell.setCellValue("BĂ?O CĂ?O CĂ‚N Ä?á»?I NHáº¬P XUáº¤T Tá»’N THĂ?NG " + toDate.split("/")[1] + " NÄ‚M "  + toDate.split("/")[2]);
	        titleCell.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);	      
	        Row titleRow2 = sheet.createRow(5);
	        titleRow2.createCell(1).setCellValue(warehouseName.toUpperCase());
	        titleRow2.getCell(1).setCellStyle(getCellStyles(wb).get(CELL_TITLE_STYLE));
	        titleRow2.getCell(1).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);	 
	        
	        sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 2));
	        
	        sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 17));
	        sheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 17));
	        sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 17));
	        sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 17));
	        
	        
	        /** ROW 2 Tu ngay **/
	        Row rowFromDate = sheet.createRow(6);	        
	        Cell fromDateCell = rowFromDate.createCell(1);
	        fromDateCell.setCellValue("Tá»« ngĂ y:");
	        fromDateCell.setCellStyle(getCellStyles(wb).get(CELL_ALIGN_LEFT));
	        // tu ngay value
	        Cell fromDateValueCell = rowFromDate.createCell(2);
	        fromDateValueCell.setCellValue(fromDate);
	        fromDateValueCell.setCellStyle(getCellStyles(wb).get(CELL_ALIGN_LEFT));
	        /** ROW 3 den ngay **/
	        // den ngay 
	        Row rowToDate = sheet.createRow(7);	    
	        Cell toDateCell = rowToDate.createCell(1);	   
	        toDateCell.setCellValue("Ä?áº¿n ngĂ y:");
	        toDateCell.setCellStyle(getCellStyles(wb).get(CELL_ALIGN_LEFT));
	        // den ngay value
	        Cell toDateValueCell = rowToDate.createCell(2);
	        toDateValueCell.setCellValue(toDate);
	        toDateValueCell.setCellStyle(getCellStyles(wb).get(CELL_ALIGN_LEFT));
	 
	        
	        /** ROW 4 Tieu de **/
	        Row headerRow = sheet.createRow(9);	 
	        for(int i=1;i<7;i++)
	        	sheet.addMergedRegion(new CellRangeAddress(9, 10, i, i));
	        
	        int startCol = 7;
	        for (int k=0;k<6;k++){
	        	sheet.addMergedRegion(new CellRangeAddress(9, 9, k + startCol, k + startCol+1));
	        	startCol+=1;
	        }
	        String[] headerString1 = new String[]{"TĂªn hĂ ng","NhĂ³m hĂ ng","Quy cĂ¡ch","MĂ£ hĂ ng","Size","Ä?Æ¡n giĂ¡"};
	        for(int i=1;i<7;i++){
	        	headerRow.createCell(i).setCellValue(headerString1[i-1]);
	        	headerRow.getCell(i).setCellStyle(getCellStyles(wb).get("HEADER_CELL"));
	        }
	        
	        // Chi tiáº¿t kho
	        String[] headerTitles = new String[]{"Tá»’N Ä?áº¦U Ká»²","NHáº¬P HĂ€NG CĂ”NG TY","XUáº¤T BĂ?N","XUáº¤T TRáº¢ Láº I","Tá»’N CUá»?I Ká»²"};
	        int idx = 0;
	        for(int i=7; i < 16;i+=2){
	        	Cell cellKhoTT1 = headerRow.createCell(i);
	        	headerRow.createCell(i+1).setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
		        cellKhoTT1.setCellValue(headerTitles[idx]);
		        cellKhoTT1.setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
		        idx++;
	        }	 
	        
	        /**** ROW  2 TONDAU, NHAP_TK, XUAT_TK, TON_CUOI ****/
	         
	        Row headerRow2 = sheet.createRow(10);
	        for(int i=1;i<7;i++){
	        	headerRow2.createCell(i).setCellStyle(getCellStyles(wb).get("HEADER_CELL"));
	        }
	        	        	        
	        String[] headString = new String[]{"Sá»‘ lÆ°á»£ng","TT láº»"};
	      
	        
	        for (int i=7;i< 17;i+=2){   	
	        	for(int j=0;j<2;j++){
	        		Cell c =  headerRow2.createCell(i+j);
	        		c.setCellValue(headString[j]);
	        		c.setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
	        	}
	        }
	}
	
	public static void createMasterStockReport(String fromDate
			, String toDate
			, String itemCat
			, String itemId
			, String itemSize
			, String itemStyle)throws Exception{
		
		Workbook wb = new HSSFWorkbook();
		updateCellStylesMasterStockReport(wb);
		Sheet sheet = wb.createSheet("BĂ¡o CĂ¡o Tá»•ng Há»£p Nháº­p-Xuáº¥t-Tá»“n");
        sheet.setPrintGridlines(false);
        sheet.setDisplayGridlines(false);

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        
        updateMasterStockReportWorkbookHeader(wb, fromDate,toDate);
        updateMasterStockReportDetailData(wb, fromDate,toDate,itemCat,itemId, itemSize, itemStyle);
        
        /** Write to file */
        JFileChooser chooser = new JFileChooser();
		
		chooser.setCurrentDirectory(new File("reports"));
		chooser.setDialogTitle("LÆ°u bĂ¡o cĂ¡o vĂ o thÆ° má»¥c");
		
		chooser.setFileFilter(new FileFilter(){
			
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".xls")
					|| f.isDirectory();	
			}
			
			public String getDescription() {
				return "MS Excel Files";
			}
		});
		
		int r = chooser.showSaveDialog(null);
		String fileName = null;
		if(r == JFileChooser.APPROVE_OPTION){
			fileName = chooser.getSelectedFile().getPath();
			if(!fileName.endsWith(".xls")){
				SystemMessageDialog.showMessageDialog(null, "Báº¡n chá»?n sai kiá»ƒu táº­p tin, " +
						"tĂªn táº­p tin lÆ°u pháº£i lĂ  táº­p tin Excel (.xls)"
						, SystemMessageDialog.SHOW_OK_OPTION);
				fileName += ".xls"; 
			}
			
		}
		// File chooser
		
		if(fileName == null || fileName.equals(""))
			fileName = "reports/BaoCaoTongHopNhapXuatTon.xls";
			
        FileOutputStream out = new FileOutputStream(new File(fileName));
        wb.write(out);
        out.close();
	}
	

	static void updateMasterStockReportDetailData(Workbook wb
			, String fromDate
			, String toDate
			, String itemCat
			, String itemId
			, String itemSize
			, String itemStyle
			) throws Exception{
//		Sheet sheet = wb.getSheetAt(0);
//		sheet.autoSizeColumn((short)1);sheet.autoSizeColumn((short)2);sheet.autoSizeColumn((short)3);
//		/** Step #1 getting item list */
//		ArrayList<Item> items = Item.getItems();
//		int i=0;
//		
//		for (Item item:items)
//		{
//			Row row = sheet.createRow(6+i);
//			if(itemCat != null && !itemCat.equals("") && !itemCat.equals("NONE_SELECTION"))
//				if(!item.getItemCatCode().equalsIgnoreCase(itemCat) )continue;	
//			
//			if(itemId != null && !itemId.trim().equals(""))
//				if(!item.getItemCode().equalsIgnoreCase(itemId) )continue;
//			
//			
//			if(itemSize != null && !itemSize.trim().equals(""))
//				if(!item.getItemMadeIn().equalsIgnoreCase(itemSize) )continue;
//			
//			if(itemStyle != null && !itemStyle.trim().equals(""))
//				if(!item.getItemStyle().equalsIgnoreCase(itemStyle) )continue;
//			
//			row.createCell(1).setCellValue(item.getItemName());
//			row.getCell(1).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.getCell(1).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
//			row.createCell(2).setCellValue(ItemCat.getCatByCatID(item.getItemCatCode()).getCatName());		
//			row.getCell(2).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.getCell(2).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
//			row.createCell(3).setCellValue(item.getItemStyle());		
//			row.getCell(3).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.getCell(3).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
//			row.createCell(4).setCellValue(item.getItemCode());		
//			row.getCell(4).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.getCell(4).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
//			row.createCell(5).setCellValue(item.getItemMadeIn());		
//			row.getCell(5).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.getCell(5).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
//			row.createCell(6).setCellValue(Long.valueOf(item.getItemSellingPrice()));		
//			row.getCell(6).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.getCell(6).getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
//			
//			//loop for each warehouse
//			int j = 7;
//			long sumCf = 0l;
//			long sumBf = 0l;
//			long sumInQuantity = 0l;
//			long sumOutQuantity = 0l;
//			
//			for(Warehouse warehouse: warehouses){
//				long bf = getBfQuantity(fromDate
//						, warehouse.getWarehouseCode(), item.getItemCode());
//				row.createCell(4+j).setCellValue(bf);
//				row.getCell(4+j).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//				sumBf+=bf;
//				long inQty = getInOutQuantity(fromDate
//						,toDate, warehouse.getWarehouseCode(), item.getItemCode(),true);
//				row.createCell(5+j).setCellValue(inQty);
//				row.getCell(5+j).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//				sumInQuantity += inQty;
//				
//				long outQty = getInOutQuantity(fromDate
//						,toDate, warehouse.getWarehouseCode(), item.getItemCode(),false);
//				row.createCell(6+j).setCellValue(outQty);
//				row.getCell(6+j).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//				sumOutQuantity += outQty;
//				
//				sumCf += (bf + inQty + outQty);
//				row.createCell(7+j).setCellValue(bf + inQty + outQty);
//				row.getCell(7+j).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//				j += 4;
//			}
//			row.createCell(7).setCellValue(sumBf);
//			row.getCell(7).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			row.createCell(8).setCellValue(sumInQuantity);
//			row.getCell(8).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			
//			row.createCell(9).setCellValue(sumOutQuantity);
//			row.getCell(9).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			
//			row.createCell(10).setCellValue(sumCf);
//			row.getCell(10).setCellStyle(getCellStyles(wb).get("DATA_CELL"));
//			// end loop for ware house
//			i++;
//		}
	}
	public static void main(String[] args) throws Exception{
//		ExcelUtil.taoBaoCaoCanDoiNhapXuatTon("12/09/2010", "16/09/2010","557732", "","", "", "");
//		ExcelUtil.createMasterStockReport("12/09/2010", "16/09/2010","","", "", "");
	}
	static void updateMasterStockReportWorkbookHeader(Workbook wb, String fromDate, String toDate){
//		Sheet sheet = wb.getSheetAt(0);
//		   Row titleRow = sheet.createRow(1);
//	        titleRow.setHeightInPoints(35);
//	        for (int i = 1; i <= warehouses.size(); i++) 
//	            titleRow.createCell(i).setCellStyle(getCellStyles(wb).get(CELL_TITLE_STYLE));
//	        
//	        Cell titleCell = titleRow.getCell(1);
//	        titleCell.setCellValue("BĂ?O CĂ?O Tá»”NG Há»¢P NHáº¬P XUáº¤T Tá»’N");
//	        titleCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);	        
//	        /** ROW 2 Tu ngay --> den ngay **/
//	        Row row = sheet.createRow(2);	        
//	        Cell fromDateCell = row.createCell(1);
//	        fromDateCell.setCellValue("Tá»« ngĂ y:");
//	        fromDateCell.setCellStyle(getCellStyles(wb).get(CELL_ALIGN_LEFT));
//	        // tu ngay value
//	        Cell fromDateValueCell = row.createCell(2);
//	        fromDateValueCell.setCellValue(fromDate);
//	        fromDateValueCell.setCellStyle(getCellStyles(wb).get(CELL_ALIGN_LEFT));
//	        // den ngay 
//	        Cell toDateCell = row.createCell(5);	   
//	        toDateCell.setCellValue("Ä?áº¿n ngĂ y:");
//	        toDateCell.setCellStyle(getCellStyles(wb).get(CELL_ALIGN_LEFT));
//	        // den ngay value
//	        Cell toDateValueCell = row.createCell(6);
//	        toDateValueCell.setCellValue(toDate);
//	        toDateValueCell.setCellStyle(getCellStyles(wb).get(CELL_ALIGN_LEFT));
//	 
//	        /** ROW 3 Tieu de **/
//	        Row headerRow = sheet.createRow(4);	 
//	        for(int i=1;i<7;i++)
//	        	sheet.addMergedRegion(new CellRangeAddress(4, 5, i, i));
//	        
//	        int startCol = 7;
//	        for (int k=0;k<warehouses.size()+1;k++){
//	        	sheet.addMergedRegion(new CellRangeAddress(4, 4, k + startCol, k + startCol+3));
//	        	startCol+=3;
//	        }
//	        String[] headerString1 = new String[]{"TĂªn hĂ ng","NhĂ³m hĂ ng","Quy cĂ¡ch","MĂ£ hĂ ng","Size","Ä?Æ¡n giĂ¡"};
//	        for(int i=1;i<7;i++){
//	        	headerRow.createCell(i).setCellValue(headerString1[i-1]);
//	        	headerRow.getCell(i).setCellStyle(getCellStyles(wb).get("HEADER_CELL"));
//	        }
//	        
//	        // tong ton kho
//	        for(int i=7;i<11;i++){
//	        	Cell cellGrandSum = headerRow.createCell(i);
//	        	headerRow.createCell(i+1).setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
//	        	headerRow.createCell(i+2).setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
//	        	headerRow.createCell(i+3).setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
//	        	cellGrandSum.setCellValue("Tá»•ng cá»™ng");
//	        	cellGrandSum.setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
//	        }
//	        // Chi tiáº¿t kho
//	        int idx = 0;
//	        for(int i=11; i < (warehouses.size()*4)+11;i+=4){// bá»? qua 11 Ă´ Ä‘áº§u tiĂªn
//	        	Warehouse warehouse = (Warehouse) warehouses.get(idx);
//	        	Cell cellKhoTT1 = headerRow.createCell(i);
//	        	headerRow.createCell(i+1).setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
//	        	headerRow.createCell(i+2).setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
//	        	headerRow.createCell(i+3).setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
//		        cellKhoTT1.setCellValue(warehouse.getWarehouseName());
//		        cellKhoTT1.setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
//		        idx++;
//	        }	 
//	        
//	        /**** ROW  2 TONDAU, NHAP_TK, XUAT_TK, TON_CUOI ****/
//	         
//	        Row headerRow2 = sheet.createRow(5);
//	        for(int i=1;i<7;i++){
//	        	headerRow2.createCell(i).setCellStyle(getCellStyles(wb).get("HEADER_CELL"));
//	        }
//	        	        	        
//	        String[] headString = new String[]{"Tá»“n Ä‘áº§u","Nháº­p TK","Xuáº¥t TK","Tá»“n cuá»‘i"};
//	        for(int i=7;i<11;i++){
//	        	Cell c =  headerRow2.createCell(i);
//	        	c.setCellValue(headString[i-7]);
//        		c.setCellStyle(getCellStyles(wb).get("HEADER_CELL"));
//	        }
//	        
//	        for (int i=11;i< (warehouses.size()*4+ 11);i+=4){   	// bá»? qua 11 Ă´ Ä‘áº§u tiĂªn 
//	        	for(int j=0;j<4;j++){
//	        		Cell c =  headerRow2.createCell(i+j);
//	        		c.setCellValue(headString[j]);
//	        		c.setCellStyle(getCellStyles(wb).get("HEADER_CELL"));	
//	        	}
//	        }
	    
	}
	static long getCfQuantity(String toDate, String warehouseId, String itemId)throws Exception{
		
		String sql = "select cf from sic_retailer.daily_stock where item_code = '"+ itemId
		+ "' and warehouse_id = '"+ warehouseId
		+ "' and (cal_date='"+ toDate +"')";
		
		java.sql.Connection conn = PostgreSQLConnectionPool.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.execute();
		ResultSet rs = stmt.getResultSet();
		long cfNumber = 9999999999999l;
		
		while(rs.next()){
			cfNumber = rs.getLong("bf");
			break;
		}
		// Null data 
		if(cfNumber == 9999999999999l || stmt.getFetchSize() <=0){
			stmt.close();
			
			String sql2 = "select cf, to_char(max(to_date(cal_date,'')),'dd/MM/yyyy') as cal_date" +
					" from sic_retailer.daily_stock where item_code = '"+ itemId
			+ "' and warehouse_id = '"+ warehouseId
			+ "' group by cf";
			stmt = conn.prepareStatement(sql2);
			stmt.execute();
			rs = stmt.getResultSet();
			while(rs.next()){
				cfNumber = rs.getLong("cf");
				break;
			}			
			stmt.close();
		}
		return (cfNumber == 9999999999999l)?0l:cfNumber;
	}
	
	static long getBfQuantity(String fromDate, String warehouseId, String itemId) throws Exception{
		long ret = 0l;
		
		
		String sqlString ="select bf from sic_retailer.daily_stock where item_code = '"+ itemId
		+ "' and warehouse_id = '"+ warehouseId
		+ "' and (cal_date='"+ fromDate +"')";
		java.sql.Connection conn = PostgreSQLConnectionPool.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sqlString);
		stmt.execute();
		ResultSet rs = stmt.getResultSet();
		while(rs.next()){
			ret = rs.getLong("bf");
			break;
		}
		return ret;
	}
	
	static long getInOutQuantity(String fromDate, String toDate, String warehouseId, String itemId, boolean isInputExtraction) throws Exception{
		String sqlInputString = "select sum(in_qty) as qty from sic_retailer.daily_stock where item_code = '"+ itemId
		+ "' and warehouse_id = '"+ warehouseId
		+"' and (to_date(cal_date,'dd/MM/yyyy') between to_date('"+fromDate+"','dd/MM/yyyy') and to_date('"+toDate+"','dd/MM/yyyy')+1)";
		
		String sqlOutputString = "select sum(out_qty) as qty from sic_retailer.daily_stock where item_code = '"+ itemId
		+ "' and warehouse_id = '"+ warehouseId
		+"' and (to_date(cal_date,'dd/MM/yyyy') between to_date('"+fromDate+"','dd/MM/yyyy') and to_date('"+toDate+"','dd/MM/yyyy')+1)";
		
		java.sql.Connection conn = PostgreSQLConnectionPool.getConnection();
		PreparedStatement stmt = conn.prepareStatement(isInputExtraction?sqlInputString:sqlOutputString);
		stmt.execute();
		ResultSet rs = stmt.getResultSet();
		long ret = 0l;
		while(rs.next()){
			ret = rs.getLong("qty");
			break;
		}
		return ret;
		
	}
	
	public static Map<String, CellStyle> getCellStyles(Workbook wb){
		 return cellStylesMasterStockReport;
	}
	public static final String CELL_TITLE_STYLE_COMPANY = "CELL_TITLE_STYLE_BIG";
	public static final String CELL_TITLE_STYLE_ADDRESS = "CELL_TITLE_STYLE_ADDRESS";
	
	public static Map<String, CellStyle> updateCellStylesMasterStockReport(Workbook wb){
		cellStylesMasterStockReport = new HashMap<String, CellStyle>();
		CellStyle style;
		Font addressFont = wb.createFont();
		//********************************* Header cell style ***********************//
		addressFont.setFontHeightInPoints((short)9);
		addressFont.setFontName("Arial");
        style = wb.createCellStyle();
        style.setFont(addressFont);
        cellStylesMasterStockReport.put(CELL_TITLE_STYLE_ADDRESS, style);
		//********************************* Header cell style ***********************//
        Font comFont = wb.createFont();
        comFont.setFontHeightInPoints((short)12);
        comFont.setFontName("Arial");
        style = wb.createCellStyle();
        style.setFont(comFont);
        cellStylesMasterStockReport.put(CELL_TITLE_STYLE_COMPANY, style);
        
		//********************************* Header cell style ***********************//
        Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short)14);
        titleFont.setFontName("Arial");
        style = wb.createCellStyle();
        style.setFont(titleFont);
        cellStylesMasterStockReport.put(CELL_TITLE_STYLE, style);
        
      //********************************* Item cell style left ***********************//        
        Font itemFont = wb.createFont();
        itemFont.setFontHeightInPoints((short)9);
        itemFont.setFontName("Arial");
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        style.setBorderTop(CellStyle.BORDER_MEDIUM);
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        style.setFont(itemFont);
        cellStylesMasterStockReport.put("HEADER_CELL", style);
        
        //***************** DATA CELL STYLE ***************//
        Font itemFontDataCell = wb.createFont();
        itemFontDataCell.setFontHeightInPoints((short)9);
        itemFontDataCell.setFontName("Arial");
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        style.setBorderTop(CellStyle.BORDER_MEDIUM);
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        
        style.setFont(itemFontDataCell);
        cellStylesMasterStockReport.put("DATA_CELL", style);
        //********************************* Item cell style left ***********************//
        itemFont = wb.createFont();
        itemFont.setFontHeightInPoints((short)9);
        itemFont.setFontName("Arial");
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(itemFont);
        cellStylesMasterStockReport.put(CELL_ALIGN_LEFT, style);
        
      //********************************* Item cell style right ***********************//
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(itemFont);
        cellStylesMasterStockReport.put(CELL_ALIGN_RIGHT, style);
        
      //********************************* Item cell style $ format***********************//
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(itemFont);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.ALIGN_RIGHT);
        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        style.setBorderTop(CellStyle.BORDER_MEDIUM);
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        style.setDataFormat(wb.createDataFormat().getFormat("_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)"));
        cellStylesMasterStockReport.put(CELL_STYLE_INPUT_DOLLA, style);
        
      //********************************* Item cell style $ format %***********************//
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(itemFont);
        style.setBorderRight(CellStyle.BORDER_DOTTED);
        style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_DOTTED);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_DOTTED);
        style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_DOTTED);
        style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setDataFormat(wb.createDataFormat().getFormat("0.000%"));
        cellStylesMasterStockReport.put(CELL_STYLE_INPUT_PERCENT, style);
        
      //********************************* Item cell style integer format***********************//
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(itemFont);
        style.setBorderRight(CellStyle.BORDER_DOTTED);
        style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_DOTTED);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_DOTTED);
        style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_DOTTED);
        style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setDataFormat(wb.createDataFormat().getFormat("0"));
        cellStylesMasterStockReport.put(CELL_STYLE_INPUT_INTEGER, style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(itemFont);
        style.setDataFormat(wb.createDataFormat().getFormat("m/d/yy"));
        cellStylesMasterStockReport.put(CELL_STYLE_INPUT_DECIMAL, style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(itemFont);
        style.setBorderRight(CellStyle.BORDER_DOTTED);
        style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_DOTTED);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_DOTTED);
        style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_DOTTED);
        style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setDataFormat(wb.createDataFormat().getFormat("$##,##0.00"));
        style.setBorderBottom(CellStyle.BORDER_DOTTED);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStylesMasterStockReport.put(CELL_STYLE_FORMULA_DOLLA, style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(itemFont);
        style.setBorderRight(CellStyle.BORDER_DOTTED);
        style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_DOTTED);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_DOTTED);
        style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_DOTTED);
        style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setDataFormat(wb.createDataFormat().getFormat("0"));
        style.setBorderBottom(CellStyle.BORDER_DOTTED);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStylesMasterStockReport.put(CELL_STYLE_FORMULA_INTEGER, style);
        
        
        
		return cellStylesMasterStockReport;
	}
}
