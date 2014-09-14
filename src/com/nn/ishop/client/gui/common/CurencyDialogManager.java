package com.nn.ishop.client.gui.common;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.bank.Bank;
import com.nn.ishop.server.dto.exrate.Currency;

public class CurencyDialogManager extends AbstractGUIManager implements
GUIActionListener, TableModelListener, ListSelectionListener{	
	CTabbedPane currencyMasterTabbedPane;
	
	CTextField txtCurrencyId;
	CTextField txtCurrencyDesc;
	Object[][] currencyTabularData = null;
	CPaging currencyListPage;
	Currency updateCurrency = null;
	Boolean isUpdateCurrency = false;
	
	CTextField txtBankId;
	CTextField txtBankDesc;
	Object[][] bankTabularData = null;
	CPaging bankListPage;
	Bank updateBank = null;
	Boolean isUpdateBank = false;
	JSplitPane creencySplit,bankSplit;
	
	void init() {
		initTemplate(this, "common/currencyDialogPage.xml");
		render();
		bindEventHandlers();
		
	}
	public CurencyDialogManager(String locale){
			setLocale(locale);
			init();
	}
	@Override
	protected void applyStyles() {		
		currencyMasterTabbedPane.setTitleAt(0, Language.getInstance().getString("currency"));
		currencyMasterTabbedPane.setTitleAt(1, Language.getInstance().getString("bankTitle"));		
		
		currencyMasterTabbedPane.setIconAt(0, Util.getIcon("tabbed/tabbed-icon-money.png"));
		currencyMasterTabbedPane.setIconAt(1, Util.getIcon("tabbed/tabbed-icon-bank.png"));
		customizeSplitPaneHideFirstComponent(creencySplit);
		customizeSplitPaneHideFirstComponent(bankSplit);
	}

	@Override
	protected void bindEventHandlers() {
		loadData();
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
	
	public void tableChanged(TableModelEvent e) {
	}
	public void valueChanged(ListSelectionEvent e) {
	}
	private void saveData(){
		try {
			Currency c = new Currency(txtCurrencyId.getText(), txtCurrencyDesc
					.getText());
			CommonLogic.insertCurrency(c);
			prepareCurrencyData();
			updateCurrency = null;
			isUpdateCurrency = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void newData(){
		txtCurrencyId.setText(null);
		txtCurrencyDesc.setText(null);
		updateCurrency = null;
		isUpdateCurrency = false;
	}
	
	private void saveBankData(){
		try {
			Bank b = new Bank(txtBankId.getText(), txtBankDesc.getText());
			CommonLogic.insertBank(b);			
			prepareBankData();
			updateBank = null;
			isUpdateBank = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void newBankData(){
		txtBankId.setText(null);
		txtBankDesc.setText(null);
		updateBank = null;
		isUpdateBank = false;
	}
	public Action ACT_SAVE_CURRENCY = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveData();
		}
	};
	public Action ACT_NEW_CURRENCY = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			newData();
			updateCurrency = null;
		}
	};
	
	public Action ACT_SAVE_BANK = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveBankData();
		}
	};
	public Action ACT_NEW_BANK = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			newBankData();
			updateCurrency = null;
		}
	};
	private void loadData(){
		prepareCurrencyData();
		prepareBankData();
	}
	private void prepareCurrencyData(){
		try {
			List<Currency> currencyData = CommonLogic.loadCurrency();
			TableUtil.addListToTable(currencyListPage, currencyListPage.getTable(), currencyData, Arrays.asList(new Integer(0)));
		} catch (Throwable e) {			
			logger.info(" updateCatGroupInfor: "+e.getMessage());			
		}
	}
	
	private void prepareBankData(){
		try {					
			List<Bank> bankData = CommonLogic.loadBank();
			TableUtil.addListToTable(bankListPage, bankListPage.getTable(), bankData, Arrays.asList(new Integer(0)));
		} catch (Throwable e) {			
			logger.info(" updateCatGroupInfor: "+e.getMessage());			
		}
	}
}
