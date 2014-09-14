package com.nn.ishop.client.gui.admin.customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.ImageLabel;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.CustomerLogic;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.client.validator.Validator;
import com.nn.ishop.server.dto.bank.Bank;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.customer.CustomerType;
import com.nn.ishop.server.dto.geographic.Location;
import com.nn.ishop.server.dto.pricelist.PriceListType;
import com.toedter.calendar.JDateChooser;

public class CustomerInformationManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener, ItemListener
		 {
	
	CDialogsLabelButton customerSaveButton, customerNewButton;
	
	CWhitePanel customerInforActionPanel,  customerInforContentContainer, customerNetworkContainer1
	, customerContractInforPanel, customerShiptoInforPanel, customerBilltoInforPanel;
	
	CTabbedPane customerExtraInforTabbedPane, customerInforTabbedPane;
	//-- Data fields
	CTextField txtIdCardNumber, txtCustomerTaxCode, txtCustomerGtalk, txtCustomerSkype, 
	txtCustomerYM, txtCustomerWebsite, txtCustomerBankAccount, txtCustomerFax, txtCustomerTelephone
	,txtCustomerSubAddress, txtCustomerMainAddress,txtCustomerEmail,txtCustomerContactPerson,txtCustomerName,
	txtDue_date, txtContract_address, txtContract_sign_person, txtContract_sign_person_position, 
	txtContract_sign_person_phone, txtContract_sign_person_mobile, txtContract_sign_person_email, 
	txtContract_full_name, txtShipto_address;
	
	JDateChooser txtCustomerEstablishedDate;

	ImageLabel txtContactAvata;
	
	CComboBox comboCustomerType, comboProvince, comboDistrict, comboCountry, comboBank_code, comboCustomerRank
	, comboContract_province, comboContract_district, comboContract_country;
    
    CTextField txtShipto_person, txtShipto_person_position, txtShipto_person_phone, txtShipto_person_mobile
    , txtShipto_person_email, txtBillto_address, txtBillto_person, txtBillto_person_position
    , txtBillto_person_phone, txtBillto_person_mobile, txtBillto_person_email, txtBillto_note, txtDebt_limit;
	
	private Customer customer = null;	
	public CustomerInformationManager (String locale){
		setLocale(locale);
		init();
	}
	CWhitePanel customerInforContentPanel;
	
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "admin/khachhang/customerInforPage.xml", getLocale());
		}else{
			initTemplate(this, "admin/khachhang/customerInforPage.xml");		
			}		
		render();
		bindEventHandlers();
		customerNetworkContainer1.add(
				(new CustomerNetworkManager(getLocale())).getRootComponent(), 
				BorderLayout.CENTER);
	}
	@Override
	protected void applyStyles() {
		customerInforTabbedPane.setTitleAt(0, Language.getInstance().getString("customer.infor.tab0.title"));
		customerInforTabbedPane.setTitleAt(1, Language.getInstance().getString("customer.infor.tab1.title"));
		
		//customerInforActionPanel.setBackground(Library.DEFAULT_FORM_ACTION_BACKGROUND);
		customerInforActionPanel.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(CConstants.TEXT_BORDER_COLOR, CLineBorder.DRAW_TOP_BORDER),
				new EmptyBorder(0,0,0,0)
				));
		
		/** Set icon for tab */
		customerInforTabbedPane.setIconAt(0, Util.getIcon("customer.png"));
		customerInforTabbedPane.setIconAt(1, Util.getIcon("network/network.png"));
		
		customerInforContentContainer.setBackground(Color.WHITE);	
		
		customerExtraInforTabbedPane.setTitleAt(0, Language.getInstance().getString("customer.contract.infor"));
		customerExtraInforTabbedPane.setTitleAt(1, Language.getInstance().getString("customer.shipto.infor"));
		customerExtraInforTabbedPane.setTitleAt(2, Language.getInstance().getString("customer.billto.infor"));
		
		customerExtraInforTabbedPane.setIconAt(0, Util.getIcon("tabbed/tabbed-icon-contract-info.png"));
		customerExtraInforTabbedPane.setIconAt(1, Util.getIcon("tabbed/tabbed-icon-shipto.png"));
		customerExtraInforTabbedPane.setIconAt(2, Util.getIcon("tabbed/tabbed-icon-billto.png"));
	}

	@Override
	protected void bindEventHandlers() {
		initialize();
		comboCountry.addItemListener(this);
		comboProvince.addItemListener(this);
		
		comboContract_province.addItemListener(this);
		comboContract_country.addItemListener(this);
		
		ACTION_SAVE_CUS.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));			
		customerSaveButton.getActionMap().put("customerSaveButton", ACTION_SAVE_CUS);
		customerSaveButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		        (KeyStroke) ACTION_SAVE_CUS.getValue(Action.ACCELERATOR_KEY), "customerSaveButton");
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

	public void cActionPerformed(CActionEvent action) {		
		if(action.getAction() == CActionEvent.LIST_SELECT_ITEM){
			Customer c = (Customer)action.getData();
			loadCustomer(c);
		}
	}

	public void guiActionPerformed(GUIActionEvent action) {
	}

	public void valueChanged(ListSelectionEvent e) {
	}

	@Override
	protected void checkPermission() {
	}
	Vector<CActionListener> vctActionListener = new Vector<CActionListener>();
	public void addCActionListener(CActionListener al) {
		vctActionListener.add(al);
	}
	public void removeCActionListener(CActionListener al) {
		vctActionListener.remove(al);
	}
	void loadCustomer(Customer c){
		if(c == null) return;
		try {			
			this.customer = c;		
			
			txtCustomerTaxCode.setText(c.getTax_code());
			txtCustomerGtalk.setText(c.getGm());
			txtCustomerSkype.setText(c.getSkype());
			txtCustomerYM.setText(c.getYm());
			txtCustomerBankAccount.setText(c.getBank_account());			
			txtCustomerSubAddress.setText(c.getAddress_2());
			txtCustomerMainAddress.setText(c.getAddress_1());
			txtCustomerEmail.setText(c.getEmail());
			txtCustomerContactPerson.setText(c.getContact_person());
			txtCustomerEstablishedDate.setDate(c.getEstablished_date());
			txtCustomerName.setText(c.getName());			
			//-- new field
			
			comboCustomerType.setSelectedItemById(String.valueOf(c.getCustomer_type()));
			
			comboCountry.setSelectedItemById(String.valueOf(c.getCountry()));
			comboProvince.setSelectedItemById(String.valueOf(c.getProvince()));
			comboDistrict.setSelectedItemById(String.valueOf(c.getDistrict()));
			comboCustomerRank.setSelectedItemById(c.getCustomer_rank_id());
			comboBank_code.setSelectedItemById(String.valueOf(c.getBank_code()));
			
			txtDue_date.setText(String.valueOf(c.getDue_date()));			
			txtContract_address.setText(c.getContract_address());
			
			comboContract_country.setSelectedItemById(String.valueOf(c.getContract_country()));
			comboContract_province.setSelectedItemById(String.valueOf(c.getContract_province()));
			comboContract_district.setSelectedItemById(String.valueOf(c.getContract_district()));
			
			txtContract_sign_person.setText(String.valueOf(c.getContract_sign_person()));
			txtContract_sign_person_position.setText(String.valueOf(c.getContract_sign_person_position()));
			txtContract_sign_person_phone.setText(String.valueOf(c.getContract_sign_person_phone()));
			txtContract_sign_person_mobile.setText(String.valueOf(c.getContract_sign_person_mobile()));
			txtContract_sign_person_email.setText(String.valueOf(c.getContract_sign_person_email()));
			txtContract_full_name.setText(String.valueOf(c.getContract_full_name()));
			txtShipto_address.setText(String.valueOf(c.getShipto_address()));
			txtShipto_person.setText(String.valueOf(c.getShipto_person()));
			txtShipto_person_position.setText(String.valueOf(c.getShipto_person_position()));
			txtShipto_person_phone.setText(String.valueOf(c.getShipto_person_phone()));
			txtShipto_person_mobile.setText(String.valueOf(c.getShipto_person_mobile()));
			txtShipto_person_email.setText(String.valueOf(c.getShipto_person_email()));
			txtBillto_address.setText(String.valueOf(c.getBillto_address()));
			txtBillto_person.setText(String.valueOf(c.getBillto_person()));
			txtBillto_person_position.setText(String.valueOf(c.getBillto_person_position()));
			txtBillto_person_phone.setText(String.valueOf(c.getBillto_person_phone()));
			txtBillto_person_mobile.setText(String.valueOf(c.getBillto_person_mobile()));
			txtBillto_person_email.setText(String.valueOf(c.getBillto_person_email()));
			txtBillto_note.setText(String.valueOf(c.getBillto_note()));
			txtDebt_limit.setText(String.valueOf(c.getDebt_limit()));			
			Util.setImageLabelIcon(txtContactAvata, c.getAvata());
			txtCustomerFax.setText(String.valueOf(c.getFax()));
			txtCustomerTelephone.setText(String.valueOf(c.getTelephone()));
			txtIdCardNumber.setText(String.valueOf(c.getId_card_number()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	void newCustomer(){
		txtIdCardNumber.setText(null);txtCustomerTaxCode.setText(null);txtCustomerGtalk.setText(null);
		txtCustomerSkype.setText(null);txtCustomerYM.setText(null);txtCustomerWebsite.setText(null);
		txtCustomerBankAccount.setText(null);txtCustomerFax.setText(null);txtCustomerTelephone.setText(null);
		txtCustomerSubAddress.setText(null);txtCustomerMainAddress.setText(null);txtCustomerEmail.setText(null);
		txtCustomerContactPerson.setText(null);txtCustomerEstablishedDate.setDate(null);txtCustomerName.setText(null);
		
		comboCustomerType.setSelectedItemById("-1");
		comboCountry.setSelectedItemById("-1");
		comboProvince.setSelectedItemById("-1");
		comboDistrict.setSelectedItemById("-1");		
		comboBank_code.setSelectedItemById("-1");
		comboCustomerRank.setSelectedIndex(0);
		
		txtDue_date.setText(null);
		txtContract_address.setText(null);
		
		comboContract_country.setSelectedItemById("-1");
		comboContract_province.setSelectedItemById("-1");
		comboContract_district.setSelectedItemById("-1");
		
		txtContract_sign_person.setText(null);
		txtContract_sign_person_position.setText(null);
		txtContract_sign_person_phone.setText(null);
		txtContract_sign_person_mobile.setText(null);
		txtContract_sign_person_email.setText(null);
		txtContract_full_name.setText(null);
		txtShipto_address.setText(null);
		txtShipto_person.setText(null);
		txtShipto_person_position.setText(null);
		txtShipto_person_phone.setText(null);
		txtShipto_person_mobile.setText(null);
		txtShipto_person_email.setText(null);
		txtBillto_address.setText(null);
		txtBillto_person.setText(null);
		txtBillto_person_position.setText(null);
		txtBillto_person_phone.setText(null);
		txtBillto_person_mobile.setText(null);
		txtBillto_person_email.setText(null);
		txtBillto_note.setText(null);
		txtDebt_limit.setText(null);
		
		txtContactAvata.initIcon();
		customer = null;
	}
	boolean validateRequireFields(){
		boolean ret = true;
		ret &=Validator.validateObjectNotNull(comboCustomerRank.getSelectedItemId(),
				Language.getInstance().getString("customer.rank"));
		ret &=Validator.validateObjectNotNull(txtCustomerName.getText(),
				Language.getInstance().getString("customer.name"));
		ret &=Validator.validateObjectNotNull(txtCustomerMainAddress.getText(),
				Language.getInstance().getString("customer.main.address"));
		ret &=Validator.validateObjectNotNull(txtCustomerBankAccount.getText(),
				Language.getInstance().getString("customer.bank.account"));		
		ret &=Validator.validateNumber(txtCustomerTelephone, Long.class,txtCustomerTelephone.getBackground(),
				Language.getInstance().getString("customer.telephone"));
		ret &=Validator.validateNumber(txtCustomerFax, Long.class,txtCustomerFax.getBackground(),
				Language.getInstance().getString("customer.fax"));
		ret &=Validator.validateObjectNotNull(txtCustomerEstablishedDate.getDate(),
				Language.getInstance().getString("customer.established.date"));
		ret &=Validator.validateEmailFormat(txtCustomerEmail, txtCustomerEmail.getBackground(),
				Language.getInstance().getString("customer.established.date"));
		ret &=Validator.validateObjectNotNull(txtCustomerContactPerson.getText(),
				Language.getInstance().getString("customer.contact.person"));
		Validator.validateNumber(txtIdCardNumber, Long.class,txtIdCardNumber.getBackground(),
				Language.getInstance().getString("contact.id.card"));
		ret &=Validator.validateDateChooser(txtCustomerEstablishedDate,
				Language.getInstance().getString("customer.established.date"));
		ret &=Validator.validateFilePath(txtContactAvata.getIconPath(), 
				Language.getInstance().getString("user.avata"));
		ret &=Validator.validateObjectNotNull(txtCustomerTaxCode.getText(),
				Language.getInstance().getString("customer.tax.code"));
		ret &=Validator.validateSelectedComboBox(comboCustomerType, comboCustomerType.getBackground(),
				Language.getInstance().getString("customer.type"));
		ret &=Validator.validateSelectedComboBox(comboCountry, comboCountry.getBackground(),
				Language.getInstance().getString("country"));
		ret &=Validator.validateSelectedComboBox(comboProvince, comboProvince.getBackground(),
				Language.getInstance().getString("province"));
		ret &=Validator.validateSelectedComboBox(comboDistrict, comboDistrict.getBackground(),
				Language.getInstance().getString("district"));
		return ret;
	}
	private void saveCustomer(){
		try {
			boolean valid = validateRequireFields();
			
			if(valid == false) {
				SystemMessageDialog.showMessageDialog(null, 
						Language.getInstance().getString("validate.general.notification"), 
						SystemMessageDialog.SHOW_OK_OPTION);
				return;
			}
			if (customer == null) { // add new
				//Validate 				
				customer = new Customer(
						comboCustomerRank.getSelectedItemId(), 
						txtCustomerName.getText(),
						txtCustomerMainAddress.getText(), 
						txtCustomerSubAddress.getText(), 
						txtCustomerBankAccount.getText(),
						Integer.parseInt(txtCustomerTelephone.getText()==null?"0":txtCustomerTelephone.getText()),
						Integer.parseInt(txtCustomerFax.getText() == null?"0":txtCustomerFax.getText()),
						txtCustomerEstablishedDate.getDate(), 
						txtCustomerEmail.getText(), 
						txtCustomerContactPerson.getText(),
						-1, txtCustomerYM.getText(),
						txtCustomerGtalk.getText(), 
						Integer.parseInt(txtIdCardNumber.getText()==null?"0":txtIdCardNumber.getText()), 
						Util.getImageAsByteArray(txtContactAvata.getIconPath()), 
						txtCustomerSkype.getText(), 
						txtCustomerTaxCode.getText(),
						Integer.parseInt(comboCustomerType.getSelectedItemId()==null?"0":comboCustomerType.getSelectedItemId())
						);
				int cusTypeInt = Integer.parseInt(
						(comboCustomerType.getSelectedItemId() != null && !comboCustomerType.getSelectedItemId().equals(""))?
								comboCustomerType.getSelectedItemId():"0"
						);
				customer.setCustomer_type(cusTypeInt);
				
				int countryCode = Integer.parseInt(
						(comboCountry.getSelectedItemId() != null && !comboCountry.getSelectedItemId().equals(""))?
								comboCountry.getSelectedItemId():"0"
						);
				customer.setCountry(countryCode);
				int provinceCode = Integer.parseInt(
						(comboProvince.getSelectedItemId() != null && !comboProvince.getSelectedItemId().equals(""))?
								comboProvince.getSelectedItemId():"0"
						);
				customer.setProvince(provinceCode);
				
				int districtCode = Integer.parseInt(
						(comboDistrict.getSelectedItemId() != null && !comboDistrict.getSelectedItemId().equals(""))?
								comboDistrict.getSelectedItemId():"0"
						);
				customer.setDistrict(districtCode);
				customer.setBank_code(comboBank_code.getSelectedItemId());
				
				int dueDate = Integer.parseInt(
						(txtDue_date.getText() != null && !txtDue_date.getText().equals(""))?
								txtDue_date.getText():"0"
						);
				
				customer.setDue_date(dueDate);				
				customer.setContract_address(txtContract_address.getText());
				
				int contractCountryId = Integer.parseInt(
						(comboContract_country.getSelectedItemId() != null && !comboContract_country.getSelectedItemId().equals(""))?
								comboContract_country.getSelectedItemId():"0"
						);
				customer.setContract_country( contractCountryId);
				
				int contractProvinceId = Integer.parseInt(
						(comboContract_province.getSelectedItemId() != null && !comboContract_province.getSelectedItemId().equals(""))?
								comboContract_province.getSelectedItemId():"0"
						);
				customer.setContract_province(contractProvinceId);
				int contractDistrictId = Integer.parseInt(
						(comboContract_district.getSelectedItemId() != null && !comboContract_district.getSelectedItemId().equals(""))?
								comboContract_district.getSelectedItemId():"0"
						);
				customer.setContract_district(contractDistrictId);
				
				customer.setContract_sign_person( txtContract_sign_person.getText());
				customer.setContract_sign_person_position( txtContract_sign_person_position.getText());
				customer.setContract_sign_person_phone( txtContract_sign_person_phone.getName());
				customer.setContract_sign_person_mobile(txtContract_sign_person_mobile.getText());
				customer.setContract_sign_person_email( txtContract_sign_person_email.getText());
				customer.setContract_full_name(txtContract_full_name.getText());
				customer.setShipto_address( txtShipto_address.getText());
				customer.setShipto_person( txtShipto_person.getText());
				customer.setShipto_person_position( txtShipto_person_position.getText());
				customer.setShipto_person_phone( txtShipto_person_phone.getText());
				customer.setShipto_person_mobile( txtShipto_person_mobile.getText());
				customer.setShipto_person_email( txtShipto_person_email.getText());
				customer.setBillto_address( txtBillto_address.getText());
				customer.setBillto_person( txtBillto_person.getText());
				customer.setBillto_person_position( txtBillto_person_position.getText());
				customer.setBillto_person_phone( txtBillto_person_phone.getText());
				customer.setBillto_person_mobile( txtBillto_person_mobile.getText());
				customer.setBillto_person_email( txtBillto_person_email.getText());
				customer.setBillto_note( txtBillto_note.getText());
				
				int debtLim = Integer.parseInt(
						(txtDebt_limit.getText() != null && !txtDebt_limit.getText().equals(""))?
								txtDebt_limit.getText():"0"
						);
				customer.setDebt_limit(debtLim);
				
				CustomerLogic.getInstance().insertCustomer(customer);
				
				//newCustomer();				
			}else if(customer != null){
				customer.setCustomer_rank_id(comboCustomerRank.getSelectedItemId());
				customer.setName(txtCustomerName.getText());
				customer.setAddress_2(txtCustomerSubAddress.getText());
				customer.setBank_account(txtCustomerBankAccount.getText());
				
				int telNum = Integer.parseInt(
						(txtCustomerTelephone.getText() != null && !txtCustomerTelephone.getText().equals(""))?
								txtCustomerTelephone.getText():"0"
						);
				customer.setTelephone(telNum);
				
				int faxNum = Integer.parseInt(
						(txtCustomerFax.getText() != null && !txtCustomerFax.getText().equals(""))?
								txtCustomerFax.getText():"0"
						);
				customer.setFax(faxNum);
				
				customer.setEstablished_date(
						txtCustomerEstablishedDate.getDate());
				customer.setEmail(txtCustomerEmail.getText());
				customer.setContact_person(txtCustomerContactPerson.getText());
				customer.setYm(txtCustomerYM.getText());
				customer.setGm(txtCustomerGtalk.getText());
				
				int idCard = Integer.parseInt(
						(txtIdCardNumber.getText() != null && !txtIdCardNumber.getText().equals(""))?
								txtIdCardNumber.getText():"0"
						);
				customer.setId_card_number(idCard);
				customer.setAvata(Util
								.getImageAsByteArray(txtContactAvata
										.getIconPath()));
				customer.setSkype(txtCustomerSkype
								.getText());
				customer.setTax_code(txtCustomerTaxCode.getText());
				
				int cusTypeInt = Integer.parseInt(
						(comboCustomerType.getSelectedItemId() != null && !comboCustomerType.getSelectedItemId().equals(""))?
								comboCustomerType.getSelectedItemId():"0"
						);
				customer.setCustomer_type(cusTypeInt);
				
				int countryCode = Integer.parseInt(
						(comboCountry.getSelectedItemId() != null && !comboCountry.getSelectedItemId().equals(""))?
								comboCountry.getSelectedItemId():"0"
						);
				customer.setCountry(countryCode);
				int provinceCode = Integer.parseInt(
						(comboProvince.getSelectedItemId() != null && !comboProvince.getSelectedItemId().equals(""))?
								comboProvince.getSelectedItemId():"0"
						);
				customer.setProvince(provinceCode);
				
				int districtCode = Integer.parseInt(
						(comboDistrict.getSelectedItemId() != null && !comboDistrict.getSelectedItemId().equals(""))?
								comboDistrict.getSelectedItemId():"0"
						);
				customer.setDistrict(districtCode);
				
				customer.setBank_code(comboBank_code.getSelectedItemId());
				
				int dueDate = Integer.parseInt(
						(txtDue_date.getText() != null && !txtDue_date.getText().equals(""))?
								txtDue_date.getText():"0"
						);
				
				customer.setDue_date(dueDate);					
				customer.setContract_address(txtContract_address.getText());
				
				try {
					customer
							.setContract_country(Integer
									.parseInt(comboContract_country
											.getSelectedItemId()));
					customer.setContract_province(Integer
							.parseInt(comboContract_province
									.getSelectedItemId()));
					customer.setContract_district(Integer
							.parseInt(comboContract_district
									.getSelectedItemId()));
				} catch (Exception e) {
					// do nothing
				}
				customer.setContract_sign_person( txtContract_sign_person.getText());
				customer.setContract_sign_person_position( txtContract_sign_person_position.getText());
				customer.setContract_sign_person_phone( txtContract_sign_person_phone.getName());
				customer.setContract_sign_person_mobile(txtContract_sign_person_mobile.getText());
				customer.setContract_sign_person_email( txtContract_sign_person_email.getText());
				customer.setContract_full_name(txtContract_full_name.getText());
				customer.setShipto_address( txtShipto_address.getText());
				customer.setShipto_person( txtShipto_person.getText());
				customer.setShipto_person_position( txtShipto_person_position.getText());
				customer.setShipto_person_phone( txtShipto_person_phone.getText());
				customer.setShipto_person_mobile( txtShipto_person_mobile.getText());
				customer.setShipto_person_email( txtShipto_person_email.getText());
				customer.setBillto_address( txtBillto_address.getText());
				customer.setBillto_person( txtBillto_person.getText());
				customer.setBillto_person_position( txtBillto_person_position.getText());
				customer.setBillto_person_phone( txtBillto_person_phone.getText());
				customer.setBillto_person_mobile( txtBillto_person_mobile.getText());
				customer.setBillto_person_email( txtBillto_person_email.getText());
				customer.setBillto_note( txtBillto_note.getText());
				try {
					customer.setDebt_limit(Double.parseDouble(txtDebt_limit
							.getText()));
				} catch (Exception e) {
					//ignore				
				}
				CustomerLogic.getInstance().updateCustomer(customer);
				//fireCAction(new CActionEvent(this,CActionEvent.NOTIFY_MODIFY_CUSTOMER,customer));
				//newCustomer();
			}
			fireCAction(new CActionEvent(this,CActionEvent.NOTIFY_MODIFY_CUSTOMER, customer));
			SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("save.success"), SystemMessageDialog.SHOW_OK_OPTION);
			
		} catch (Exception e) {
			SystemMessageDialog.showMessageDialog(null, 
					Language.getInstance().getString("error") + e.getMessage(),
					SystemMessageDialog.SHOW_OK_OPTION);
			e.printStackTrace();
		}
	}
	void fireCAction(CActionEvent action){
		for(CActionListener a:vctActionListener)
			a.cActionPerformed(action);
		
	}
	public Action ACTION_SAVE_CUS = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {			
			saveCustomer();
		}
	};
	public Action ACTION_NEW_CUS = new AbstractAction() {		
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {			
			newCustomer();
		}
	};
	public static final Logger logger = Logger.getLogger(CustomerInformationManager.class);
	
	/**
	 * Load sub combobox data when parent changed
	 * @param parentId
	 * @param targetCombobox
	 * @param locationType
	 */
	private void loadSubComboData(int parentId, CComboBox targetCombobox, int locationType){
		try {
			List<Location> lstLocation = CommonLogic.loadLocationByParentId(parentId, locationType);
			CommonLogic.updateComboBox(lstLocation, targetCombobox);			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		
	}
	
	private void initialize(){

		try {
			List<Bank> lstBank = CommonLogic.loadBank();
			CommonLogic.updateComboBox(lstBank, comboBank_code);
			
			List<Location> lstCountry = CommonLogic.loadCountry();
			CommonLogic.updateComboBox(lstCountry, comboCountry);
			CommonLogic.updateComboBox(lstCountry, comboContract_country);
			
			List<Location> lstProvince = CommonLogic.loadLocationByParentId(1, 1);			
			
			CommonLogic.updateComboBox(lstProvince, comboProvince);
			CommonLogic.updateComboBox(lstProvince, comboContract_province);
			
			List<CustomerType> lstCustomerType = CommonLogic.loadCustomerType();
			CommonLogic.updateComboBox(lstCustomerType, comboCustomerType);
			
			List<PriceListType> lstCustomerRank = CommonLogic.loadPriceListType();
			CommonLogic.updateComboBox(lstCustomerRank, comboCustomerRank);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}	
		}
	public void itemStateChanged(ItemEvent e) {		
		try {			
			if (e.getSource() == comboCountry) {
				if(comboCountry.getSelectedItemId().equals("-1"))
					return;
				loadSubComboData(Integer.parseInt(comboCountry.getSelectedItemId()),
						comboProvince, 1);
			} else if (e.getSource() == comboProvince) {
				if(comboProvince.getSelectedItemId().equals("-1"))
					return;
				loadSubComboData(Integer.parseInt(comboProvince.getSelectedItemId()),
						comboDistrict, 2);

			} else if (e.getSource() == comboContract_country) {
				if(comboContract_country.getSelectedItemId().equals("-1"))
					return;
				loadSubComboData(Integer.parseInt(comboContract_country.getSelectedItemId()),
						comboContract_province, 1);

			} else if (e.getSource() == comboContract_province) {
				if(comboContract_province.getSelectedItemId().equals("-1"))
					return;
				loadSubComboData(Integer.parseInt(comboContract_province.getSelectedItemId()),
						comboContract_district, 2);
			} 
		} catch (Exception e2) {
			logger.error(e2.getMessage());			
		}
	}	
}
