package com.nn.ishop.client.gui.arap;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

import com.google.gson.Gson;
import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.GUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.common.ChooseCustomerDialogManager;
import com.nn.ishop.client.gui.components.CCheckBox;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.components.CTextArea;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.arap.ARAPLogic;
import com.nn.ishop.client.logic.receipt.ReceiptLogic;
import com.nn.ishop.client.logic.sale.SaleOrderLogic;
import com.nn.ishop.client.logic.transaction.DailyTranxLogic;
import com.nn.ishop.client.util.ConvertUtil;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.client.validator.Validator;
import com.nn.ishop.server.dto.bstranx.DailyTransaction;
import com.nn.ishop.server.dto.bstranx.TransactionType;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.exrate.Currency;
import com.nn.ishop.server.dto.receipt.AccountReceivable;
import com.nn.ishop.server.dto.receipt.Receipt;
import com.toedter.calendar.JDateChooser;

public class ARAPMasterGUIManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener {	
	CTabbedPane  receiptTabbedPane;	
	CTextField txtCustomer, txtReceiptNumber, txtCustomerEmail, txtTranxNum, txtCustomerBF;
	CTextField txtDueAmount,txtReceiptAmount, txtCustomerBankAcc, txtPayer, txtVCNum, txtCustomerCfAmount;
	CTextField txtCustomerDebtLimit,txtCustomerDueDate,txtPaidAmount;
	CTextArea txtReceiptNote;
	JRadioButton radioReceivable, radioPayable;
	JDateChooser dcReceiptDate, dcSearchFromDate, dcSearchToDate;
	CComboBox comboSearchTranxType, comboCurrency, comboPaymentMethod;
	CTextArea txtAreaCustomerAddr;
	CPaging pagingSearchTranx;
	
	Gson gson;
	
	CCheckBox chkPayall;
	CDialogsLabelButton btnSave;
	CWhitePanel pnlStats,pnlMainMasterContent;
	
	Customer selectedCustomer = null;
	static int SO_DATE_COL = 5, SO_DELIVERY_DATE_COL =11, AMT_COL = 14;
	final List<Integer> editableColums = Arrays.asList(new Integer[]{0});	
	
	public ARAPMasterGUIManager (String locale){
		setLocale(locale);
		init();
	}
	
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "arap/masterPage.xml", getLocale());
		}else{
			initTemplate(this, "arap/masterPage.xml");		
			}
		render();
		ARAPStatsGUIManager pnlStatsPage = new ARAPStatsGUIManager(getLocale());
		if(pnlStats != null) {
			pnlStats.add(pnlStatsPage.getRootComponent(), BorderLayout.CENTER);
		}
		bindEventHandlers();
	}
	
	@Override
	protected void applyStyles() {
		Map<Object, String> tabTitleMap = new HashMap<Object, String>();
		List<Object> tabObjects = new ArrayList<Object>();
		
		if(pnlMainMasterContent != null) {
			tabObjects.add(pnlMainMasterContent);
			tabTitleMap.put(pnlMainMasterContent,Language.getInstance().getString("rc.tab.title"));
		}
		
		if(pnlStats != null) {
			tabObjects.add(pnlStats);
			tabTitleMap.put(pnlStats,Language.getInstance().getString("tabbed.receivable.stats"));			
		}
		
		for(int i=0;i<tabObjects.size();i++){
			Object obj = tabObjects.get(i);
			receiptTabbedPane.setTitleAt(i,tabTitleMap.get(obj));
		}		
	}
	
	@Override
	protected void bindEventHandlers() {
		if(chkPayall != null) {
			chkPayall.addActionListener(new AbstractAction() {
				private static final long serialVersionUID = 1L;
	
				public void actionPerformed(ActionEvent e) {
					if(chkPayall.isSelected()){
						/*SystemMessageDialog.showConfirmDialog(null,
								Language.getInstance().getString(""),
								SystemMessageDialog.SHOW_OK_CANCEL_OPTION);*/
						txtReceiptAmount.setEditable(false);
					}else{
						txtReceiptAmount.setEditable(true);
					}				
					try {
						calculateReceivableByUIComponent();
					} catch (Exception e2) {
						logAction(ARAPMasterGUIManager.class, LOG_LEVEL.ERROR, e2);
					}
				}
			});
		}
		if(txtReceiptAmount != null) {
			txtReceiptAmount.setEditable(!chkPayall.isSelected());
			txtReceiptAmount.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					try {
						calculateReceivableByUIComponent();
						
					} catch (Exception e2) {
						logAction(ARAPMasterGUIManager.class, LOG_LEVEL.ERROR, e2);					
					}
					super.focusLost(e);
				}
			});		
		}
		if(txtCustomerBF != null) {
			txtCustomerBF.setEditable(false);
			txtCustomerCfAmount.setEditable(false);
		}
		if(btnSave != null) {
			ACT_SAVE_PAYMENT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));			
			btnSave.getActionMap().put("savePaymentAction", ACT_SAVE_PAYMENT);
			btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
			(KeyStroke) ACT_SAVE_PAYMENT.getValue(Action.ACCELERATOR_KEY), "savePaymentAction");
		}
		initData();
	}
	protected void initData(){
		Date d = new Date();
		if(dcReceiptDate != null) {
			dcReceiptDate.setDate(d);
		}
		if(dcSearchFromDate != null) {
			dcSearchFromDate.setDate(Util.getDateOfMonth(d, true));
		}
		if(dcSearchToDate != null) {
			dcSearchToDate.setDate(Util.getDateOfMonth(d, false));
		}
		try {
			List<TransactionType> listTtxType = CommonLogic
					.loadTransactionType();
			if (listTtxType != null && listTtxType.size() > 0 && comboSearchTranxType != null) {
				CommonLogic.updateComboBox(listTtxType, comboSearchTranxType);
				comboSearchTranxType.setSelectedIndex(1);
			}
			List<Currency> currencyList = SaleOrderLogic.loadCurrency();
			if (currencyList != null && currencyList.size() > 0 && comboCurrency != null) {
				CommonLogic.updateComboBox(currencyList, comboCurrency);
				comboCurrency.setSelectedItemById("VND");
			}
			if(comboPaymentMethod != null) {
				CommonLogic.updateComboBox(SystemConfiguration.PAYMENT_METHOD, comboPaymentMethod);
				comboPaymentMethod.setSelectedIndex(0);
			}
		} catch (Exception e) {
			logAction(ARAPMasterGUIManager.class, LOG_LEVEL.ERROR, e);
		}
		
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

	public void addCActionListener(CActionListener al) {

	}

	public void cActionPerformed(CActionEvent action) {

	}

	public void removeCActionListener(CActionListener al) {

	}

	public void guiActionPerformed(GUIActionEvent action) {

	}
	
	void clearForm() throws Throwable{
		txtAreaCustomerAddr.setText(null);
		txtCustomer.setText(null);
		txtCustomerBankAcc.setText(null);
		txtCustomerBF.setText(null);
		txtCustomerCfAmount.setText(null);
		txtCustomerEmail.setText(null);
		txtDueAmount.setText(null);
		txtPayer.setText(null);
		txtReceiptAmount.setText(null);
		txtReceiptNumber.setText(null);
		txtTranxNum.setText(null);
		txtVCNum.setText(null);
		txtCustomerDebtLimit.setText(null);
		txtCustomerDueDate.setText(null);
		List<AccountReceivable> tranxs = new ArrayList<AccountReceivable>();
		TableUtil.addListToTable(pagingSearchTranx,  pagingSearchTranx.getTable(),tranxs);
	}

	void searchDailyTranx(){
		if(selectedCustomer == null) return;
		if(!radioReceivable.isSelected()){
			logAction(ARAPMasterGUIManager.class, LOG_LEVEL.INFO, "We only implement receivable at the moment");
		}else{
			try {			
				//-- load daily transaction that match the selected customer
				List<AccountReceivable> tranxs = ARAPLogic.search(selectedCustomer.getId(),
								dcSearchFromDate.getCalendar(), dcSearchToDate
										.getCalendar());			
				if(tranxs != null && tranxs.size() > 0){
					Object[][] rowData = new Object[tranxs.size()][];
					int i = 0;
					for(AccountReceivable ar: tranxs){
						rowData[i] = ar.toObjectArray();
						i++;
					}
					String[] colHeader = tranxs.get(0).getObjectHeader();
					
					CTableModel model = preparedModel(rowData, colHeader, editableColums);				
					TableUtil.formatTable(pagingSearchTranx,  pagingSearchTranx.getTable(), model);	
					
				}else{
					TableUtil.addListToTable(pagingSearchTranx,  pagingSearchTranx.getTable(),tranxs);
				}
				//-- Calculate customer debt balance
				//--- Calculate receipt balance, payment_type = BUDGET / CREDIT
				Receipt bfRec = ReceiptLogic.getBalanceForward(selectedCustomer.getId());
				
				//-- Get paid information from Receipt
				try{
					List<Receipt> listReceipt = ReceiptLogic.search(selectedCustomer.getId(), dcSearchFromDate.getCalendar()
							, dcSearchToDate.getCalendar());
					double paidAmt = 0.0d;
					for(Receipt rc:listReceipt){
						paidAmt += rc.getPayment_amount();
					}
					txtPaidAmount.setText(SystemConfiguration.decfm.format(paidAmt));
				}catch(Exception ex){
					 logAction(ARAPMasterGUIManager.class, LOG_LEVEL.DEBUG, ex);
				}
				//-- Calculate result to display on GUI 
				calculateReceivable(tranxs,bfRec);
				txtCustomerBF.setText((bfRec != null)? (bfRec.getPayment_amount()<0)? "("
						+ SystemConfiguration.decfm.format(bfRec.getPayment_amount())+")"
						:SystemConfiguration.decfm.format(bfRec.getPayment_amount()):"0");
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	void calculateReceivable(List<AccountReceivable> tranxs, Receipt bfRec) throws Exception{
		double receivableAmt = 0.0d;
		for(AccountReceivable ar: tranxs){
			receivableAmt += ar.getAmount();
		}		
		double receiveAmt = chkPayall.isSelected()?receivableAmt
				:ConvertUtil.convertCTextToNumber(txtReceiptAmount).doubleValue();
		double paidAmt = ConvertUtil.convertCTextToNumber(txtPaidAmount).doubleValue();
		
		double cfAmt = (bfRec==null?0.0d:bfRec.getPayment_amount()) + receivableAmt - receiveAmt - paidAmt;
		if(chkPayall.isSelected())
			txtReceiptAmount.setText(SystemConfiguration.decfm.format(receivableAmt));
		txtDueAmount.setText(SystemConfiguration.decfm.format(receivableAmt));
		txtCustomerCfAmount.setText(SystemConfiguration.decfm.format(cfAmt));		
	}
	/**
	 * Calculate CF based on UI components
	 * @throws Exception instance of ParseException
	 */
	void calculateReceivableByUIComponent() throws Exception{		
		double receivableAmt = ConvertUtil.convertCTextToNumber(txtDueAmount).doubleValue();	
		double receiveAmt = chkPayall.isSelected()?ConvertUtil.convertCTextToNumber(txtDueAmount).doubleValue()
				:ConvertUtil.convertCTextToNumber(txtReceiptAmount).doubleValue();
		double bfAmt = ConvertUtil.convertCTextToNumber(txtCustomerBF).doubleValue();
		double paidAmt = ConvertUtil.convertCTextToNumber(txtPaidAmount).doubleValue(); 
		double cfAmt = bfAmt + receivableAmt - receiveAmt - paidAmt;		
		txtReceiptAmount.setText(SystemConfiguration.decfm.format(receiveAmt));
		txtCustomerCfAmount.setText(SystemConfiguration.decfm.format(cfAmt));	
		txtReceiptAmount.selectAll();
	}
	
	protected CTableModel preparedModel(Object[][] rowData, String[] colHeader, final List<Integer> editableColums){
		
		//- Modified column name to for localization
		String[] columnNames = new String[colHeader.length];		
		for (int i = 0; i < colHeader.length; i++) {
			String realTitle = Language.getInstance().getString(colHeader[i]);
			columnNames[i] = realTitle;
		}
		CTableModel model = new CTableModel(rowData, columnNames, rowData.length) {
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int column) {
				if(column == AMT_COL)
					return Double.class;
				//else if(column == SO_DATE_COL || column == SO_DELIVERY_DATE_COL)
					//return Date.class;
				else
					return Object.class;				        
		      }
			@Override
			public boolean isCellEditable(int row, int col) {
				if (editableColums != null && editableColums.size()>0 
						&& editableColums.contains(new Integer(col)))
					return true;
				return super.isCellEditable(row, col);
			}
		};		
		return model;
	}
	
	public Action ACT_SEARCH_PAYMENT = new AbstractAction(){
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			searchDailyTranx();
		}
		
	};
	public Action ACT_SAVE_PAYMENT = new AbstractAction(){
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			try {
				btnSave.requestFocusInWindow();
				//-- Validate condition
				//--- 1. Voucher list must not be empty
				List<AccountReceivable> listReceivable = ARAPLogic.search(selectedCustomer.getId(),
						dcSearchFromDate.getCalendar(), dcSearchToDate
								.getCalendar());	
				if(listReceivable == null || listReceivable.size() == 0)
				{
					SystemMessageDialog.showMessageDialog(null
							, Language.getInstance().getString("no.voucher.for.receipt")
							, SystemMessageDialog.SHOW_OK_OPTION);
					return;
				}
				double screenArAmt = ConvertUtil.convertCTextToNumber(txtDueAmount).doubleValue();				
				double receivableAmt = 0.0d;
				List<String> listVoucherId = new ArrayList<String>();
				List<String> listTranxId = new ArrayList<String>();
				Map<String, String> mapTxVoucher = new HashMap<String, String>();
				for (AccountReceivable ar : listReceivable) {
					receivableAmt += ar.getAmount();
					listVoucherId.add(ar.getOrder_id());
					listTranxId.add(ar.getTranx_id());
					mapTxVoucher.put(ar.getTranx_id(), ar.getOrder_id());
				}
				
				if(screenArAmt != receivableAmt){
					SystemMessageDialog.showMessageDialog(null
							, Language.getInstance().getString("receivable.amount.changed.use.new.one")
							, SystemMessageDialog.SHOW_OK_OPTION);
				}
				boolean ret = Validator.validateNumber(txtReceiptAmount
						, Double.class, txtReceiptAmount.getBackground()
						, Language.getInstance().getString("error.validateNumber"));
				if(ret == false)
					return;
				gson = new Gson();
				String jsonListVoucherId = gson.toJson(listVoucherId);
				String jsonListTranxId = gson.toJson(listTranxId);
				
				//-- Prepare to create receipt
				//-- Create receipt
				String receiptId  = Util.generateSerial("REC_");
				txtReceiptNumber.setText(receiptId);
				Receipt rec = new Receipt(receiptId
						, selectedCustomer.getId()
						, jsonListVoucherId
						, comboCurrency.getSelectedItemId()
						, ConvertUtil.convertCTextToNumber(txtCustomerBF).doubleValue()
						, jsonListTranxId
						, "CR"
						, ConvertUtil.convertCTextToNumber(txtCustomerDueDate).intValue()
						, ConvertUtil.convertCTextToNumber(txtReceiptAmount).doubleValue()
						, dcReceiptDate.getDate()
						, comboPaymentMethod.getSelectedItemId()
						, txtCustomerBankAcc.getText()
						, txtVCNum.getText()
						, txtPayer.getText()
						, ConvertUtil.convertCTextToNumber(txtCustomerCfAmount).doubleValue()
						, comboSearchTranxType.getSelectedItemId()
						, txtCustomerBankAcc.getText()
						, txtReceiptNote.getText());
				//-- Save receipt
				AccountReceivable[] arArr = listReceivable.toArray(new AccountReceivable[listReceivable.size()]);
				rec = ReceiptLogic.getInstance().insertWithAr(rec, arArr);				
				//-- update voucher number for transaction
				for(String tx:listTranxId){
					DailyTransaction tranx = DailyTranxLogic.getInstance().get(tx);
					String currentVc = tranx.getVoucher_id();
					currentVc += "," +receiptId;
					tranx.setCurrent_step("ISSUE_SLIP");					
				}
				//-- clear form
				if(SystemConfiguration.decfm.parse(txtCustomerCfAmount.getText()).doubleValue() == 0 ){
					clearForm();
				}else{
					searchDailyTranx();
				}
				//-- fire event		
				
			} catch (Throwable e) {
				e.printStackTrace();
				logAction(ARAPMasterGUIManager.class, LOG_LEVEL.ERROR, e);
			}
		}		
	};
	
	public Action ACT_PRINT_PAYMENT = new AbstractAction(){
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			//TODO print payment
		}		
	};
	public Action ACT_OPEN_CUSTOMER_DIALOG = new AbstractAction(){
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			ChooseCustomerDialogManager dlgGui = new ChooseCustomerDialogManager(Language.getInstance().getLocale());
			GenericDialog dlg = (GenericDialog)dlgGui.getRootComponent();		
			dlg.setIconImage(Util.getImage("logo32.png"));
			//dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dlg.pack();
			dlg.validate();
			dlg.centerDialogRelative(GUIManager.mainFrame, dlg);
			dlg.setLocation(dlg.getLocation().x, 0);
			dlg.setVisible(true);			
			selectedCustomer = dlgGui.getChoosenCustomer();
			if(selectedCustomer == null) return;
			txtCustomer.setText(selectedCustomer.getName());			
			txtCustomerBankAcc.setText(String.valueOf(selectedCustomer.getBank_account()));			
			txtCustomerEmail.setText(String.valueOf(selectedCustomer.getEmail()));		
			txtAreaCustomerAddr.setText(String.valueOf(selectedCustomer.getAddress_1()));
			txtCustomerDebtLimit.setText(String.valueOf(selectedCustomer.getDebt_limit()));
			txtCustomerDueDate.setText(String.valueOf(selectedCustomer.getDue_date()));
			searchDailyTranx();
		}
	};	
}
