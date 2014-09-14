package com.nn.ishop.client.gui.helper;

import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.common.ChooseProductDialogManager;
import com.nn.ishop.client.gui.common.CurencyDialogManager;
import com.nn.ishop.client.gui.common.ExRateDialogManager;
import com.nn.ishop.client.gui.common.PriceListDialogManager;
import com.nn.ishop.client.gui.common.RoleMaintenanceManager;
import com.nn.ishop.client.gui.common.ShowGRNHistoryManager;
import com.nn.ishop.client.gui.common.ShowItemStockDialogManager;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.nn.ishop.client.gui.profile.ProfileGUIManager;
import com.nn.ishop.client.util.Util;

public class DialogHelper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
	
	public static void showDialog(JFrame mainFrame, Container rootComponent){
		GenericDialog dlg = (GenericDialog)rootComponent;		
		dlg.setIconImage(Util.getImage("logo32.png"));	
		//dlg.centerDialog(mainFrame);				
		dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dlg.pack();
		dlg.validate();
		dlg.centerDialogRelative(mainFrame, dlg);
		dlg.setVisible(true);
	}
	
	public static void openCurencyDialog(JFrame mainFrame) {		
		CurencyDialogManager dlgGui = new CurencyDialogManager(Language.getInstance().getLocale());
		showDialog(mainFrame,dlgGui.getRootComponent());	
				
	}
	
	public static void openExRateDialog(JFrame mainFrame) {
		ExRateDialogManager dlgGui = new ExRateDialogManager(Language.getInstance().getLocale());
		showDialog(mainFrame,dlgGui.getRootComponent());				
	}
	
	public static void openPriceListDialog(JFrame mainFrame) {
		PriceListDialogManager dlgGui = new PriceListDialogManager(Language.getInstance().getLocale());
		showDialog(mainFrame,dlgGui.getRootComponent());				
	}
	
	public static void openChooseProductDialog(JFrame mainFrame) {
		ChooseProductDialogManager dlgGui = new ChooseProductDialogManager(Language.getInstance().getLocale());
		showDialog(mainFrame,dlgGui.getRootComponent());				
	}
	public static void openRoleDialog(JFrame mainFrame) {
		RoleMaintenanceManager dlgGui = new RoleMaintenanceManager(Language.getInstance().getLocale());
		showDialog(mainFrame,dlgGui.getRootComponent());				
	}
	
	/**
	 * Open dialog to display stock of each item
	 * @param mainFrame
	 */
	public static void openItemStockDialog(JFrame mainFrame) {
		ShowItemStockDialogManager dlgGui = new ShowItemStockDialogManager(Language.getInstance().getLocale());
		showDialog(mainFrame,dlgGui.getRootComponent());				
	}
	public static void openProfileDialog(JFrame mainFrame) {
		ProfileGUIManager dlgGui = new ProfileGUIManager(Language.getInstance().getLocale());
		showDialog(mainFrame, dlgGui.getRootComponent());				
	}
	public static void openPurchaseHisDialog(JFrame mainFrame) {
		ShowGRNHistoryManager   grnHisGui = new ShowGRNHistoryManager(Language.getInstance().getLocale());
		showDialog(mainFrame, grnHisGui.getRootComponent());				
	}
}
