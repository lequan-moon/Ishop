package com.nn.ishop.client.gui.arap;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JRadioButton;

import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.common.ChooseCustomerDialogManager;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.components.CTextArea;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.arap.ARAPLogic;
import com.nn.ishop.client.logic.receipt.ReceiptLogic;
import com.nn.ishop.client.logic.sale.SaleOrderLogic;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.bstranx.TransactionType;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.exrate.Currency;
import com.nn.ishop.server.dto.receipt.AccountReceivable;
import com.nn.ishop.server.dto.receipt.Receipt;
import com.toedter.calendar.JDateChooser;

public class ARAPStatsGUIManager extends AbstractGUIManager {
	CPaging pagingSearchTranx;
	JDateChooser dcSearchFromDate, dcSearchToDate;
	CComboBox comboSearchTranxType, comboCurrency, comboPaymentMethod;
	JRadioButton radioReceivable, radioPayable;
	Customer selectedCustomer = null;
	static int SO_DATE_COL = 2, SO_DELIVERY_DATE_COL = 11, AMT_COL = 3;
	final List<Integer> editableColums = Arrays.asList(new Integer[] { 0 });
	CTextField txtCustomerDebtLimit, txtCustomerDueDate, txtPaidAmount;
	CTextArea txtAreaCustomerAddr;
	CTextField txtDueAmount, txtReceiptAmount, txtCustomerCfAmount,txtCustomer, txtCustomerEmail, txtCustomerBankAcc;

	public ARAPStatsGUIManager(String locale) {
		setLocale(locale);
		init();
	}

	@Override
	protected void applyStyles() {
	}

	@Override
	protected void bindEventHandlers() {
		initData();
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

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "arap/arapStatsPage.xml", getLocale());
		} else {
			initTemplate(this, "arap/arapStatsPage.xml");
		}
		render();
		bindEventHandlers();
	}

	protected void initData() {
		Date d = new Date();
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
				CommonLogic.updateComboBox(SystemConfiguration.PAYMENT_METHOD,
						comboPaymentMethod);
				comboPaymentMethod.setSelectedIndex(0);
			}
		} catch (Exception e) {
			logAction(ARAPMasterGUIManager.class, LOG_LEVEL.ERROR, e);
		}

	}

	void searchDailyTranx() {
		if (selectedCustomer == null)
			return;
		if (!radioReceivable.isSelected()) {
			logAction(ARAPMasterGUIManager.class, LOG_LEVEL.INFO,
					"We only implement receivable at the moment");
		} else {
			try {

				// -- Calculate customer debt balance
				// --- Calculate receipt balance, payment_type = BUDGET / CREDIT
				Receipt bfRec = ReceiptLogic.getBalanceForward(selectedCustomer
						.getId());

				// -- Get paid information from Receipt
				try {
					List<Receipt> listReceipt = ReceiptLogic.search(
							selectedCustomer.getId(), dcSearchFromDate
									.getCalendar(), dcSearchToDate
									.getCalendar());
					if (listReceipt != null && listReceipt.size() > 0) {
						Object[][] rowData = new Object[listReceipt.size()][];
						int i = 0;
						for (Receipt rc : listReceipt) {
							rowData[i] = rc.toObjectArray();
							i++;
						}
						String[] colHeader = listReceipt.get(0)
								.getObjectHeader();
						CTableModel model = preparedModel(rowData, colHeader,
								editableColums);
						TableUtil.formatTable(pagingSearchTranx,
								pagingSearchTranx.getTable(), model);
					} else {
						TableUtil.addListToTable(pagingSearchTranx,
								pagingSearchTranx.getTable(), listReceipt);
					}
					double paidAmt = 0.0d;
					for (Receipt rc : listReceipt) {
						paidAmt += rc.getPayment_amount();
					}
					txtPaidAmount.setText(SystemConfiguration.decfm
							.format(paidAmt));
				} catch (Exception ex) {
					logAction(ARAPMasterGUIManager.class, LOG_LEVEL.DEBUG, ex);
				}
				// -- load daily transaction that match the selected customer
				List<AccountReceivable> tranxs = ARAPLogic.search(
						selectedCustomer.getId(), dcSearchFromDate
								.getCalendar(), dcSearchToDate.getCalendar());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	protected CTableModel preparedModel(Object[][] rowData, String[] colHeader,
			final List<Integer> editableColums) {

		// - Modified column name to for localization
		String[] columnNames = new String[colHeader.length];
		for (int i = 0; i < colHeader.length; i++) {
			String realTitle = Language.getInstance().getString(colHeader[i]);
			columnNames[i] = realTitle;
		}
		CTableModel model = new CTableModel(rowData, columnNames,
				rowData.length) {
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int column) {
				if (column == AMT_COL)
					return Double.class;
				// else if(column == SO_DATE_COL || column ==
				// SO_DELIVERY_DATE_COL)
				else
					return Object.class;
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				if (editableColums != null && editableColums.size() > 0
						&& editableColums.contains(new Integer(col)))
					return true;
				return super.isCellEditable(row, col);
			}
		};
		return model;
	}

	public Action ACT_SEARCH_PAYMENT = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			searchDailyTranx();
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
