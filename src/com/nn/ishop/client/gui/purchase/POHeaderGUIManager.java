package com.nn.ishop.client.gui.purchase;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.lowagie.text.pdf.AcroFields.Item;
import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.admin.CustomerLogic;
import com.nn.ishop.client.logic.admin.PurchaseGrnLogic;
import com.nn.ishop.client.logic.admin.PurchaseLogic;
import com.nn.ishop.client.logic.transaction.DailyTranxLogic;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.validator.Validator;
import com.nn.ishop.server.dto.bstranx.DailyTransaction;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.purchase.PurchasingPlan;
import com.nn.ishop.server.dto.purchase.PurchasingPlanType;
import com.nn.ishop.server.util.Formatter;
import com.toedter.calendar.JDateChooser;

/**
 * @author nghia
 *
 */
/**
 * @author nghia
 *
 */
public class POHeaderGUIManager extends AbstractGUIManager implements CActionListener, GUIActionListener, ListSelectionListener, TableModelListener,
		ItemListener {
	private CTextField txtPPId;
	private CTextField txtContractId;
	private CComboBox cbbPPType;
	private JDateChooser txtSignedDate;
	private CComboBox cbbProvider;
	private JDateChooser txtDeadline;
	private CTextField txtNote;
	boolean isActived = false;
	private CTextField txtTranxId;
	boolean isDataChanged = false; 

	private PurchaseLogic logicInstance = PurchaseLogic.getInstance();
	private PurchasingPlan pp;
	Vector<CActionListener> lstListener = new Vector<CActionListener>();
	private DailyTranxLogic dailyTranxLogic = DailyTranxLogic.getInstance();
	private DailyTransaction currentTranx;

	public POHeaderGUIManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "purchase/po/poHeaderPage.xml", getLocale());
		} else {
			initTemplate(this, "purchase/po/poHeaderPage.xml");
		}
		render();
		prepareData();
		bindEventHandlers();
	}

	private void prepareData() {
		// Prepare ppType
		PurchaseLogic pLogic = PurchaseLogic.getInstance();
		List<PurchasingPlanType> lstPpType = new ArrayList<PurchasingPlanType>();
		lstPpType = pLogic.getListPurchasingPlanType();
		ItemWrapper[] lstItem = new ItemWrapper[lstPpType.size()+1];
		int i = 1;
		lstItem[0] = new ItemWrapper("-1", Language.getInstance().getString("not.select"));
		for (PurchasingPlanType purchasingPlanType : lstPpType) {
			ItemWrapper item = new ItemWrapper(String.valueOf(purchasingPlanType.getPPTypeId()), 
					Language.getInstance().getString(purchasingPlanType.getPPTypeName()));
			lstItem[i] = item;
			i++;
		}
		cbbPPType.setItems(lstItem);
		
		try {
			CustomerLogic customerLogic = CustomerLogic.getInstance();
			List<Customer> lstCustomer = new ArrayList<Customer>();
			lstCustomer = customerLogic.loadCustomer();
			List<Customer> filterProvider = new ArrayList<Customer>();
			for (Customer customer : lstCustomer) {
				if(customer.getCustomer_type() == 2){
					filterProvider.add(customer);
				}
			}
			ItemWrapper[] lstProvider = new ItemWrapper[filterProvider.size() + 1];
			lstProvider[0] = new ItemWrapper("-1", Language.getInstance().getString("not.select"));
			int j = 1;
			for (Customer customer : filterProvider) {
				ItemWrapper newProvider = new ItemWrapper(String.valueOf(customer.getId()), customer.getName());
				lstProvider[j] = newProvider;
				j++;
			}
			cbbProvider.setItems(lstProvider);
		} catch (Exception e) {
			e.printStackTrace();
		}

		txtSignedDate.setDate(new Date());
		txtDeadline.setDate(new Date());
	}

	private void resetData() {
		this.txtPPId.setText("");
		this.txtContractId.setText("");
		this.txtSignedDate.setDate(new Date());
		this.cbbProvider.setSelectedItemById("-1");
		this.txtDeadline.setDate(new Date());
		this.cbbPPType.setSelectedItemById("1");
		this.txtNote.setText("");
		this.txtTranxId.setText("");		
		isDataChanged = false;
	}

	private void generatePPId() {
		String ppId = "PP_" +Formatter.sdfTimeId.format((new Date()).getTime());		
		this.txtPPId.setText(ppId);
		if ("2".equals(cbbPPType.getSelectedItemId())) {
			this.txtContractId.setText(this.txtPPId.getText());
		}		
	}

	@Override
	protected void applyStyles() {
	}

	@Override
	protected void bindEventHandlers() {
		// Changed handler
		txtNote.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;			
			}
			
			public void insertUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;				
			}
			
			public void changedUpdate(DocumentEvent e) {
			}
		});
		txtContractId.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;			
			}
			
			public void insertUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;				
			}
			
			public void changedUpdate(DocumentEvent e) {
			}
		});
		txtPPId.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;			
			}
			
			public void insertUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;				
			}
			
			public void changedUpdate(DocumentEvent e) {
			}
		});
		txtTranxId.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;			
			}
			
			public void insertUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;				
			}
			
			public void changedUpdate(DocumentEvent e) {
			}
		});
		cbbPPType.addItemListener(this);
		cbbProvider.addItemListener(this);
	}
	public boolean isDirty(){
		return isDataChanged;
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

	public PurchasingPlan saveData() {
		try {
			pp = new PurchasingPlan();
			pp.setPpId(txtPPId.getText());
			pp.setPpTypeId(cbbPPType.getSelectedItemId());
			pp.setContractId(txtContractId.getText());
			pp.setDeadline(txtDeadline.getDate().toString());
			pp.setSignedDate(txtSignedDate.getDate().toString());
			pp.setProviderId(Integer.valueOf(cbbProvider.getSelectedItemId()));
			pp.setTranx_id(txtTranxId.getText());
			pp.setNote(txtNote.getText());
			pp = logicInstance.insertPurchasingPlan(pp);

			currentTranx.setVoucher_id(txtPPId.getText());
			currentTranx.setCurrent_step("GOOD_RECEIVE_NOTE");
			currentTranx = dailyTranxLogic.update(currentTranx);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pp;
	}

	public void cActionPerformed(CActionEvent action) {
		if (action.getAction() == CActionEvent.ADD ) {			
			if(isDataChanged){
				int ret = SystemMessageDialog.showConfirmDialog(null, 
						"EVENT ADD PP :"+Language.getInstance().getString("form.dirty"),
						SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
				if(ret != 0)
					return;
			}			
			if(action.getData() instanceof PurchasingPlan){
				isDataChanged = false;
				PurchasingPlan pp = (PurchasingPlan)action.getData();
				txtTranxId.setText(pp.getTranx_id());
				try {
					currentTranx = DailyTranxLogic.getInstance().get(
							pp.getTranx_id());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			generatePPId();
			isActived = true;
			cbbPPType.requestFocusInWindow();
			cbbPPType.showPopup();
		}
		if(action.getAction() == CActionEvent.UPDATE_PP){//- From master screen
			if(isDataChanged){
				int ret = SystemMessageDialog.showConfirmDialog(null, 
						"EVENT UPDATE_PP :"+Language.getInstance().getString("form.dirty"),
						SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
				if(ret != 0)
					return;
			}
			isDataChanged = false;
			if(txtPPId.getText() == null || txtPPId.getText().equals("")){
				generatePPId();
			}
			if(action.getData() instanceof DailyTransaction){
				currentTranx = (DailyTransaction) action.getData();
				txtTranxId.setText(currentTranx.getId());
			}
			
		}
		
		if (action.getAction() == CActionEvent.SAVE) {
			// Validate
			if (Validator.validateEmpty(txtPPId, SystemConfiguration.DEFAULT_DISABLED_TEXT, Language.getInstance().get("PPId")) && Validator.validateSelectedComboBox(cbbProvider, SystemConfiguration.DEFAULT_DISABLED_TEXT, Language.getInstance().get("PPType")) && Validator.validateEmpty(txtTranxId, SystemConfiguration.DEFAULT_DISABLED_TEXT, Language.getInstance().get("TransactionId"))) {
				// write to database
				saveData();
				isDataChanged = false;
				action.setData(pp);
				fireCActionEvent(action);
				isActived = false;
				resetData();
			}
		}
		if (action.getAction() == CActionEvent.CANCEL) {
			if(isDataChanged){
				int ret = SystemMessageDialog.showConfirmDialog(null, 
						"EVENT: CANCEL :"+Language.getInstance().getString("form.dirty"),
						SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
				if(ret != 0)
					return;
			}
			fireCActionEvent(action);
			isActived = false;
			resetData();
		}
		
	}

	public void fireCActionEvent(CActionEvent action) {
		for (CActionListener listener : lstListener) {
			listener.cActionPerformed(action);
		}
	}

	public void guiActionPerformed(GUIActionEvent action) {
	}

	public void valueChanged(ListSelectionEvent e) {
	}

	public void tableChanged(TableModelEvent e) {
	}

	@Override
	protected void checkPermission() {
	}

	public void addCActionListener(CActionListener al) {
		lstListener.add(al);
	}

	public void removeCActionListener(CActionListener al) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
		String typeId = ((ItemWrapper) e.getItem()).getId();
		if (e.getSource() == cbbPPType){
			if (e.getStateChange() == ItemEvent.SELECTED && "HDMH".equals(typeId)) {
				String ppId = this.txtPPId.getText();
				if (!"".equals(ppId)) {
					this.txtContractId.setText(ppId);				
				}
			} else {
				this.txtContractId.setText("");
			}
			 // When form data is not saved, showing Provider popup
			if(cbbProvider.getSelectedItemId() == "-1" && isActived){
				cbbProvider.showPopup();
			}
			//Determine data changed
			if(!cbbPPType.getSelectedItemId().equals("-1"))
				isDataChanged = true;
		}else if(e.getSource() == cbbProvider){
			//Determine data changed			
			if(!cbbProvider.getSelectedItemId().equals("-1"))
				isDataChanged = true;
		}
	}

	public boolean isActived() {
		return isActived;
	}

	public void setActived(boolean isActived) {
		this.isActived = isActived;
	}

	public PurchasingPlan getPp() {
		return pp;
	}

	public void setPp(PurchasingPlan pp) {
		this.pp = pp;
	}
}
