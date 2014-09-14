package com.nn.ishop.client.gui.common;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.log4j.Logger;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.server.dto.bank.Bank;
import com.nn.ishop.server.dto.exrate.Currency;
import com.nn.ishop.server.dto.exrate.ExRate;
import com.nn.ishop.server.util.Formatter;
import com.toedter.calendar.JDateChooser;

public class ExRateDialogManager extends AbstractGUIManager implements
GUIActionListener, TableModelListener, ListSelectionListener{	
	public static Logger logger = Logger.getLogger(ExRateDialogManager.class);
	CTabbedPane currencyMasterTabbedPane;

	JDateChooser dateExRate;	
	CComboBox comboExRateCurency;
	CComboBox comboExRateBank;
	CTextField txtExRateValue;
	
	
	Object[][] exRateTabularData = null;
	CPaging exRateListPage;
	ExRate updateExRate = null;
	Boolean isUpdateExRate = false;
	JSplitPane exRateSplit;
	
	void init() {
		initTemplate(this, "common/exRateDialogPage.xml");
		render();
		bindEventHandlers();
		
	}
	public ExRateDialogManager(String locale){
			setLocale(locale);
			init();
	}
	@Override
	protected void applyStyles() {
		postAction();		
		currencyMasterTabbedPane.setTitleAt(0, Language.getInstance().getString("exRateTitle"));	
		customizeSplitPaneHideSecondComponent(exRateSplit);
	}

	@Override
	protected void bindEventHandlers() {
		initializeData();
	}

	@Override
	protected void checkPermission() {
	}

	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	public void update() {
	}

	@Override
	public void updateList() {
	}
	
	public void guiActionPerformed(GUIActionEvent action) {		
	}
	void postAction(){		
	}
	public void tableChanged(TableModelEvent e) {
	}
	public void valueChanged(ListSelectionEvent e) {
	}
	
	private void saveData(){
		try {
			double number = Formatter.str2num(txtExRateValue.getText()).doubleValue();
			Date d = dateExRate.getDate();
			Date newDate = new Date(d.getYear(), d.getMonth(), d.getDate());
			
			ExRate exRate = new ExRate(comboExRateCurency.getSelectedItemId(), 
					comboExRateBank.getSelectedItemId(), 
					number, 
					Formatter.date2str(newDate));			
			CommonLogic.insertExRate(exRate);
			prepareData();
			updateExRate = null;
			isUpdateExRate = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void newData(){
		dateExRate.cleanup();	
		comboExRateCurency.setSelectedIndex(0);
		comboExRateBank.setSelectedIndex(0);
		txtExRateValue.setText(null);
		
		updateExRate = null;
		isUpdateExRate = false;
	}
	public Action ACT_SAVE_EXRATE = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveData();
		}
	};
	public Action ACT_NEW_EXRATE = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			newData();
			updateExRate = null;
		}
	};
	
	private void prepareData(){
		try {
			List<ExRate> data = CommonLogic.loadExRate();
			TableUtil.addListToTable(exRateListPage, exRateListPage.getTable(), data, Arrays.asList(new Integer(0)));
		} catch (Throwable e) {			
			logger.info(" updateCatGroupInfor: "+e.getMessage());			
		}
	}
	
	private void initializeData(){
		try {
			prepareData();
			List<Bank> listBank = CommonLogic.loadBank();
			CommonLogic.updateComboBox(listBank, comboExRateBank);			
			List<Currency> listCurrency = CommonLogic.loadCurrency();
			CommonLogic.updateComboBox(listCurrency, comboExRateCurency);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	public static void main(String[] args) throws Exception{
		
		System.out.println("Value = "+ Formatter.str2num("30000.00"));
	}
}
