package com.nn.ishop.client.gui.report;

import java.awt.Component;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.hibernate.dialect.FirebirdDialect;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CLabel;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.CompanyLogic;
import com.nn.ishop.client.logic.admin.PurchaseLogic;
import com.nn.ishop.client.logic.admin.WarehouseLogic;
import com.nn.ishop.client.logic.sale.SaleOrderLogic;
import com.nn.ishop.client.util.CommonUtil;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.util.ReportUlti;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.license.PropertyUtil;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.exrate.Currency;
import com.nn.ishop.server.dto.purchase.PurchasingPlan;
import com.nn.ishop.server.dto.sale.SaleOrder;
import com.nn.ishop.server.dto.warehouse.Warehouse;
import com.toedter.calendar.JDateChooser;

public class ReportConditionManager extends AbstractGUIManager implements CActionListener, GUIActionListener, ListSelectionListener, TableModelListener {
	Vector<CActionListener> lstListener = new Vector<CActionListener>();
	JDateChooser txtFromDate;
	JDateChooser txtToDate;

	// ListEmployee
	CTextField txtEmployeeName;
	CTextField txtEmployeeAddress;
	CTextField txtEmployeeEmail;
	CTextField txtEmployeeLoginId;
	CTextField txtEmployeePosition;
	CLabel lblEmployeePosition;
	CLabel lblEmployeeLoginId;
	CLabel lblEmployeeEmail;
	CLabel lblEmployeeAddress;
	CLabel lblEmployeeName;
	CLabel lblFromDate;
	CLabel lblToDate;

	// ListItem
	CLabel lblCurrency;
	CLabel lblPriceListType;
	CLabel lblProduct;
	CTextField txtProduct;
	CComboBox cbbPLType;
	CComboBox cbbCurrency;

	// List Company
	CLabel lblCompanyName;
	CLabel lblWarehouse;
	CLabel lblLocation;
	CComboBox cbbCompany;
	CComboBox cbbWarehouse;
	CTextField txtLocation;

	// Goods Received Items
	CLabel lblItemId;
	CTextField txtItemId;
	CLabel lblItemName;
	CTextField txtItemName;

	// ListItemDetail
	CLabel lblsectionName;
	CTextField txtsectionName;
	CLabel lblcategoryGroup;
	CTextField txtcategoryGroup;
	CLabel lblcategory;
	CTextField txtcategory;
	CLabel lbllistItemDetailItemId;
	CTextField txtlistItemDetailItemId;
	CLabel lbllistItemDetailItemName;
	CTextField txtlistItemDetailItemName;
	CLabel lblmadeIn;
	CTextField txtmadeIn;
	CLabel lblpackingStyle;
	CTextField txtpackingStyle;

	// ListCustomerReport
	CLabel lblCustomerName;
	CTextField txtCustomerName;

	// ProviderListReport
	CLabel lblProviderName;
	CTextField txtProviderName;

	// ContractDetailReport
	CLabel lblContract_id;
	CComboBox cbbContract_id;

	// ContractListReport
	CLabel lblContractList_contract_id;
	CTextField txtContractList_contract_id;
	CLabel lblContractList_customer_name;
	CTextField txtContractList_customer_name;
	CLabel lblContractList_current_step;
	CTextField txtContractList_current_step;

	// PurchasingDetailReport
	CLabel lblPurchasingDetailReport_currency_id;
	CComboBox cbbPurchasingDetailReport_currency_id;
	CLabel lblPurchasingDetailReport_pp_id;
	CComboBox cbbPurchasingDetailReport_pp_id;
	CLabel lblPurchasingDetailReport_provider_name;
	CTextField txtPurchasingDetailReport_provider_name;
	CLabel lblPurchasingDetailReport_item_id;
	CTextField txtPurchasingDetailReport_item_id;
	CLabel lblPurchasingDetailReport_item_name;
	CTextField txtPurchasingDetailReport_item_name;

	// TransactionValueReport
	CLabel lblTransactionValueReport_purchase_currency_id;
	CComboBox cbbTransactionValueReport_purchase_currency_id;
	CLabel lblTransactionValueReport_purchase_note;
	CTextField txtTransactionValueReport_purchase_note;
	CLabel lblTransactionValueReport_provider_name;
	CTextField txtTransactionValueReport_provider_name;
	CLabel lblTransactionValueReport_tranx_id;
	CTextField txtTransactionValueReport_tranx_id;
	CLabel lblTransactionValueReport_pp_id;
	CTextField txtTransactionValueReport_pp_id;
	CLabel lblTransactionValueReport_employee_name;
	CTextField txtTransactionValueReport_employee_name;
	CLabel lblTransactionValueReport_current_step;
	CTextField txtTransactionValueReport_current_step;
	CLabel lblTransactionValueReport_status;
	CTextField txtTransactionValueReport_status;

	// WHSCReport
	CLabel lblWHSCReport_item_id;
	CTextField txtWHSCReport_item_id;
	CLabel lblWHSCReport_item_name;
	CTextField txtWHSCReport_item_name;
	CLabel lblWHSCReport_wh_name;
	CTextField txtWHSCReport_wh_name;

	// PurchasingTranxList and SaleTranxList
	JDateChooser dcInsTime;
	CLabel lblInsTime;
	CComboBox cbbStatus;
	CLabel lblStatus;
	CComboBox cbbCurrentStep;
	CLabel lblCurrentStep;
	CTextField txtTranxListEmployeeName;
	CLabel lblTranxListEmployeeName;

	// SaleDetailReport
	CLabel lblsale_order_id;
	CComboBox cbbsale_order_id;
	CLabel lblsale_item_id;
	CTextField txtsale_item_id;
	CLabel lblsale_item_name;
	CTextField txtsale_item_name;

	// SaleTransactionValue
	CLabel lblTransactionValueReport_sale_currency_id;
	CComboBox cbbTransactionValueReport_sale_currency_id;
	CLabel lblTransactionValueReport_sale_description;
	CTextField txtTransactionValueReport_sale_description;
	CLabel lblTransactionValueReport_customer_name;
	CTextField txtTransactionValueReport_customer_name;
	CLabel lblTransactionValueReport_sale_tranx_id;
	CTextField txtTransactionValueReport_sale_tranx_id;
	CLabel lblTransactionValueReport_sale_order_id;
	CTextField txtTransactionValueReport_sale_order_id;
	CLabel lblTransactionValueReport_sale_login_id;
	CTextField txtTransactionValueReport_sale_login_id;
	CLabel lblTransactionValueReport_sale_current_step;
	CTextField txtTransactionValueReport_sale_current_step;
	CLabel lblTransactionValueReport_sale_status;
	CTextField txtTransactionValueReport_sale_status;

	JLabel Space2;

	Language langIns = Language.getInstance();

	public ReportConditionManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "baocao/reportConditionPage.xml", getLocale());
		} else {
			initTemplate(this, "baocao/reportConditionPage.xml");
		}
		render();
		bindEventHandlers();
		List<JComponent> initComponents = new ArrayList<JComponent>();
		initComponents.add(lblFromDate);
		initComponents.add(txtFromDate);
		initComponents.add(lblToDate);
		initComponents.add(txtToDate);
		initComponents.add(Space2);
		showComponents(initComponents);
	}

	@Override
	protected void applyStyles() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void bindEventHandlers() {
		try {
			List<Currency> lstCurrency = CommonLogic.loadCurrency();
			ItemWrapper[] currencyItems = new ItemWrapper[lstCurrency.size()];
			int i = 0;
			for (Currency currency : lstCurrency) {
				currencyItems[i] = new ItemWrapper(currency.getId(), currency.getDescriprion());
				i++;
			}
			cbbCurrency.addItems(currencyItems);
			cbbPurchasingDetailReport_currency_id.addItems(currencyItems);
			cbbTransactionValueReport_purchase_currency_id.addItems(currencyItems);
			cbbTransactionValueReport_sale_currency_id.addItems(currencyItems);
			i = 0;

			PropertyUtil properties = new PropertyUtil();
			String[] plTypeStr = properties.getProperty("pricelist.type").split(",");
			ItemWrapper[] plTypeItems = new ItemWrapper[plTypeStr.length];
			for (int j = 0; j < plTypeStr.length; j++) {
				plTypeItems[j] = new ItemWrapper(plTypeStr[j], plTypeStr[j]);
			}
			cbbPLType.addItems(plTypeItems);

			List<Company> lstCompany = CompanyLogic.getInstance().getListCompany();
			ItemWrapper[] companyItems = new ItemWrapper[lstCompany.size() + 1];
			companyItems[0] = new ItemWrapper("0", "");
			i = 1;
			for (Company company : lstCompany) {
				ItemWrapper newItem = new ItemWrapper(company.getIdAsString(), company.getName());
				companyItems[i] = newItem;
				i++;
			}
			cbbCompany.addItems(companyItems);
			i = 0;

			List<Warehouse> lstWarehouse = WarehouseLogic.loadWarehouse();
			ItemWrapper[] whItems = new ItemWrapper[lstWarehouse.size() + 1];
			whItems[0] = new ItemWrapper("0", "");
			i = 1;
			for (Warehouse wh : lstWarehouse) {
				ItemWrapper newItem = new ItemWrapper(wh.getId(), wh.getName());
				whItems[i] = newItem;
				i++;
			}
			cbbWarehouse.addItems(whItems);
			i = 0;

			Properties props = Util.getProperties("cfg/daily_tranx.cfg");
			String stepsStr = props.getProperty("PO_TRANSACTION");
			String[] steps = stepsStr.replaceAll("\"", "").split(",");
			ItemWrapper[] lstItemSteps = new ItemWrapper[steps.length + 1];
			lstItemSteps[0] = new ItemWrapper("0", "");
			for (String string : steps) {
				lstItemSteps[i + 1] = new ItemWrapper(String.valueOf(i + 1), string);
				i++;
			}
			cbbCurrentStep.addItems(lstItemSteps);
			i = 0;

			ItemWrapper[] lstItemStatus = new ItemWrapper[3];
			lstItemStatus[0] = new ItemWrapper("0", "");
			lstItemStatus[1] = new ItemWrapper("1", "ACTIVE");
			lstItemStatus[2] = new ItemWrapper("2", "CLOSED");
			cbbStatus.addItems(lstItemStatus);

			List<PurchasingPlan> lstPp = PurchaseLogic.getInstance().getListPurchasingPlan();
			ItemWrapper[] contractArr = new ItemWrapper[lstPp.size()];

			for (PurchasingPlan pp : lstPp) {
				String contractId = pp.getContractId();
				if (!"".equals(contractId)) {
					contractArr[i] = new ItemWrapper(String.valueOf(i), contractId);
					i++;
				}
			}
			cbbContract_id.addItems(contractArr);
			i = 0;

			ItemWrapper[] ppArr = new ItemWrapper[lstPp.size() + 1];
			ppArr[0] = new ItemWrapper("0", "");
			i = 1;
			for (PurchasingPlan pp : lstPp) {
				ppArr[i] = new ItemWrapper(String.valueOf(i), pp.getPpId());
				i++;
			}
			cbbPurchasingDetailReport_pp_id.addItems(ppArr);
			i = 0;

			cbbContract_id.addItems(contractArr);
			i = 0;

			List<SaleOrder> lstso = SaleOrderLogic.getInstance().load();
			ItemWrapper[] soArr = new ItemWrapper[lstso.size() + 1];
			soArr[0] = new ItemWrapper("", "");
			i = 1;
			for (SaleOrder so : lstso) {
				soArr[i] = new ItemWrapper(so.getOrder_id(), so.getOrder_id());
				i++;
			}
			cbbsale_order_id.addItems(soArr);
			i = 0;

		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getData(String var) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateList() {
		// TODO Auto-generated method stub

	}

	public void cActionPerformed(CActionEvent action) {
		if (action.getAction() == CActionEvent.EXPORT_REPORT) {
			try {
				String reportName = (String) action.getData();
				previewReport(reportName);
				// boolean isSuccessful = previewReport(reportName);
				// if(!isSuccessful){
				// SystemMessageDialog.showMessageDialog(null,
				// langIns.getString("report.fail"), 0);
				// }
			} catch (Exception ex) {
				SystemMessageDialog.showMessageDialog(null, langIns.getString("report.fail") + "\n" + ex.getMessage(), 0);
			}
		}
		if (action.getAction() == CActionEvent.LIST_SELECT_ITEM) {
			String reportName = (String) action.getData();
			filterConditions(reportName);
		}
	}

	/**
	 * Filter which components are shown
	 * 
	 * @param reportName
	 *            Report's name
	 */
	private void filterConditions(String reportName) {
		List<JComponent> listShowComponent = new ArrayList<JComponent>();
		if (langIns.getString("report.list.CompanyListReport").equals(reportName)) {
			// listShowComponent.add(lblFromDate);
			// listShowComponent.add(lblToDate);
			// listShowComponent.add(txtFromDate);
			// listShowComponent.add(txtToDate);
		}
		if (langIns.getString("report.list.EmployeeListReport").equals(reportName)) {
			listShowComponent.add(lblEmployeePosition);
			listShowComponent.add(lblEmployeeLoginId);
			listShowComponent.add(lblEmployeeEmail);
			listShowComponent.add(lblEmployeeAddress);
			listShowComponent.add(lblEmployeeName);
			// listShowComponent.add(lblFromDate);
			// listShowComponent.add(lblToDate);
			listShowComponent.add(txtEmployeeName);
			listShowComponent.add(txtEmployeeAddress);
			listShowComponent.add(txtEmployeeEmail);
			listShowComponent.add(txtEmployeeLoginId);
			listShowComponent.add(txtEmployeePosition);
			// listShowComponent.add(txtFromDate);
			// listShowComponent.add(txtToDate);
		}
		if (langIns.getString("report.list.ItemReport").equals(reportName)) {
			listShowComponent.add(lblCurrency);
			listShowComponent.add(lblPriceListType);
			listShowComponent.add(lblProduct);

			listShowComponent.add(txtProduct);
			listShowComponent.add(cbbCurrency);
			listShowComponent.add(cbbPLType);
		}
		if (langIns.getString("report.list.WarehouseLotReport").equals(reportName)) {
			listShowComponent.add(lblCompanyName);
			listShowComponent.add(lblWarehouse);
			listShowComponent.add(lblLocation);

			listShowComponent.add(cbbCompany);
			listShowComponent.add(cbbWarehouse);
			listShowComponent.add(txtLocation);
		}
		if (langIns.getString("report.list.GoodsReceiveReport").equals(reportName)) {
			listShowComponent.add(lblItemId);
			listShowComponent.add(lblItemName);

			listShowComponent.add(txtItemId);
			listShowComponent.add(txtItemName);
		}
		if (langIns.getString("report.list.ListItemDetail").equals(reportName)) {
			listShowComponent.add(lblsectionName);
			listShowComponent.add(lblcategoryGroup);
			listShowComponent.add(lblcategory);
			listShowComponent.add(lbllistItemDetailItemId);
			listShowComponent.add(lbllistItemDetailItemName);
			listShowComponent.add(lblmadeIn);
			listShowComponent.add(lblpackingStyle);

			listShowComponent.add(txtsectionName);
			listShowComponent.add(txtcategoryGroup);
			listShowComponent.add(txtcategory);
			listShowComponent.add(txtlistItemDetailItemId);
			listShowComponent.add(txtlistItemDetailItemName);
			listShowComponent.add(txtmadeIn);
			listShowComponent.add(txtpackingStyle);
		}
		if (langIns.getString("report.list.ListCustomerReport").equals(reportName)) {
			listShowComponent.add(lblCustomerName);
			listShowComponent.add(txtCustomerName);
		}
		if (langIns.getString("report.list.ProviderListReport").equals(reportName)) {
			listShowComponent.add(lblProviderName);
			listShowComponent.add(txtProviderName);
		}
		if (langIns.getString("report.list.ContractDetailReport").equals(reportName)) {
			listShowComponent.add(lblContract_id);
			listShowComponent.add(cbbContract_id);
		}
		if (langIns.getString("report.list.PurchasingDetailReport").equals(reportName)) {
			listShowComponent.add(lblPurchasingDetailReport_currency_id);
			listShowComponent.add(cbbPurchasingDetailReport_currency_id);
			listShowComponent.add(lblPurchasingDetailReport_pp_id);
			listShowComponent.add(cbbPurchasingDetailReport_pp_id);
			listShowComponent.add(lblPurchasingDetailReport_provider_name);
			listShowComponent.add(txtPurchasingDetailReport_provider_name);
			listShowComponent.add(lblPurchasingDetailReport_item_id);
			listShowComponent.add(txtPurchasingDetailReport_item_id);
			listShowComponent.add(lblPurchasingDetailReport_item_name);
			listShowComponent.add(txtPurchasingDetailReport_item_name);
		}
		if (langIns.getString("report.list.ContractListReport").equals(reportName)) {
			listShowComponent.add(lblContractList_contract_id);
			listShowComponent.add(txtContractList_contract_id);
			listShowComponent.add(lblContractList_customer_name);
			listShowComponent.add(txtContractList_customer_name);
			listShowComponent.add(lblContractList_current_step);
			listShowComponent.add(txtContractList_current_step);
		}
		if (langIns.getString("report.list.PurchasingTransactionList").equals(reportName) || langIns.getString("report.list.OutSaleTransactionList").equals(reportName)) {
			listShowComponent.add(dcInsTime);
			listShowComponent.add(lblInsTime);
			listShowComponent.add(cbbStatus);
			listShowComponent.add(lblStatus);
			listShowComponent.add(cbbCurrentStep);
			listShowComponent.add(lblCurrentStep);
			listShowComponent.add(txtTranxListEmployeeName);
			listShowComponent.add(lblTranxListEmployeeName);
		}
		if (langIns.getString("report.list.WarehouseStockCardReport").equals(reportName)) {
			listShowComponent.add(lblWHSCReport_item_id);
			listShowComponent.add(txtWHSCReport_item_id);
			listShowComponent.add(lblWHSCReport_item_name);
			listShowComponent.add(txtWHSCReport_item_name);
			listShowComponent.add(lblWHSCReport_wh_name);
			listShowComponent.add(txtWHSCReport_wh_name);
		}
		if (langIns.getString("report.list.TransactionValueReport").equals(reportName)) {
			listShowComponent.add(lblTransactionValueReport_purchase_currency_id);
			listShowComponent.add(cbbTransactionValueReport_purchase_currency_id);
			listShowComponent.add(lblTransactionValueReport_purchase_note);
			listShowComponent.add(txtTransactionValueReport_purchase_note);
			listShowComponent.add(lblTransactionValueReport_provider_name);
			listShowComponent.add(txtTransactionValueReport_provider_name);
			listShowComponent.add(lblTransactionValueReport_tranx_id);
			listShowComponent.add(txtTransactionValueReport_tranx_id);
			listShowComponent.add(lblTransactionValueReport_pp_id);
			listShowComponent.add(txtTransactionValueReport_pp_id);
			listShowComponent.add(lblTransactionValueReport_employee_name);
			listShowComponent.add(txtTransactionValueReport_employee_name);
			listShowComponent.add(lblTransactionValueReport_current_step);
			listShowComponent.add(txtTransactionValueReport_current_step);
			listShowComponent.add(lblTransactionValueReport_status);
			listShowComponent.add(txtTransactionValueReport_status);
		}
		if (langIns.getString("report.list.SaleDetailReport").equals(reportName)) {
			listShowComponent.add(lblsale_order_id);
			listShowComponent.add(cbbsale_order_id);
			listShowComponent.add(lblsale_item_id);
			listShowComponent.add(txtsale_item_id);
			listShowComponent.add(lblsale_item_name);
			listShowComponent.add(txtsale_item_name);
		}
		if (langIns.getString("report.list.SaleTransactionValueReport").equals(reportName)) {
			listShowComponent.add(lblTransactionValueReport_sale_currency_id);
			listShowComponent.add(cbbTransactionValueReport_sale_currency_id);
			listShowComponent.add(lblTransactionValueReport_sale_description);
			listShowComponent.add(txtTransactionValueReport_sale_description);
			listShowComponent.add(lblTransactionValueReport_customer_name);
			listShowComponent.add(txtTransactionValueReport_customer_name);
			listShowComponent.add(lblTransactionValueReport_sale_tranx_id);
			listShowComponent.add(txtTransactionValueReport_sale_tranx_id);
			listShowComponent.add(lblTransactionValueReport_sale_order_id);
			listShowComponent.add(txtTransactionValueReport_sale_order_id);
			listShowComponent.add(lblTransactionValueReport_sale_login_id);
			listShowComponent.add(txtTransactionValueReport_sale_login_id);
			listShowComponent.add(lblTransactionValueReport_sale_current_step);
			listShowComponent.add(txtTransactionValueReport_sale_current_step);
			listShowComponent.add(lblTransactionValueReport_sale_status);
			listShowComponent.add(txtTransactionValueReport_sale_status);
		}
		showComponents(listShowComponent);
	}

	/**
	 * Show all components in this list only
	 * 
	 * @param listComponent
	 *            Component's list
	 */
	private void showComponents(List<JComponent> listComponent) {
		// Hide all components first
		Component[] lstAllComponent = this.getRootComponent().getComponents();
		for (Component component : lstAllComponent) {
			// Default must have startDate and EndDate
			if (Space2.equals(component) || lblFromDate.equals(component) || lblToDate.equals(component) || txtFromDate.equals(component) || txtToDate.equals(component)) {
				continue;
			}
			component.setVisible(false);
		}

		// Show component in list
		for (JComponent jComponent : listComponent) {
			jComponent.setVisible(true);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean previewReport(String reportName) throws Exception {
		// jasperParameter is a Hashmap contains the parameters
		// passed from application to the jrxml layout
		HashMap jasperParameter = new HashMap();

		// id for CompanyListReport
		jasperParameter.put("id", 0);

		// EmployeeName, EmployeeAddress, EmployeeEmail, EmployeeLoginId,
		// EmployeePosition for EmployeeListReport
		String employeeName = txtEmployeeName.getText();
		jasperParameter.put("EmployeeName", decoratedString(employeeName));

		String employeeAddress = txtEmployeeAddress.getText();
		jasperParameter.put("EmployeeAddress", decoratedString(employeeAddress));

		String employeeEmail = txtEmployeeEmail.getText();
		jasperParameter.put("EmployeeEmail", decoratedString(employeeEmail));

		String employeeLoginId = txtEmployeeLoginId.getText();
		jasperParameter.put("EmployeeLoginId", decoratedString(employeeLoginId));

		String employeePosition = txtEmployeePosition.getText();
		jasperParameter.put("EmployeePosition", decoratedString(employeePosition));

		// currency, pl_type, txtProduct for ItemListReport
		String productStr = txtProduct.getText();
		jasperParameter.put("productName", decoratedString(productStr));

		String selectedCurrency = cbbCurrency.getSelectedItemId();
		jasperParameter.put("currency", selectedCurrency);

		String selectedPLType = cbbPLType.getSelectedItemId();
		jasperParameter.put("pl_type", selectedPLType);

		// CompanyName, WarehouseName, Location for WarehouseLotReport
		String companyName = ((ItemWrapper) cbbCompany.getSelectedItem()).toString();
		jasperParameter.put("companyName", decoratedString(companyName));

		String warehouseName = ((ItemWrapper) cbbWarehouse.getSelectedItem()).toString();
		jasperParameter.put("warehouseName", decoratedString(warehouseName));

		String whLocation = txtLocation.getText();
		jasperParameter.put("location", decoratedString(whLocation));

		// ItemId, ItemName for GoodsReceiveReport
		String itemIdGrn = txtItemId.getText();
		jasperParameter.put("itemId", decoratedString(itemIdGrn));

		String itemNameGrn = txtItemName.getText();
		jasperParameter.put("itemName", decoratedString(itemNameGrn));

		// For ListItemDetail's report
		String sectionName = txtsectionName.getText();
		jasperParameter.put("sectionName", decoratedString(sectionName));

		String categoryGroup = txtcategoryGroup.getText();
		jasperParameter.put("categoryGroup", decoratedString(categoryGroup));

		String category = txtcategory.getText();
		jasperParameter.put("category", decoratedString(category));

		String listItemDetailItemId = txtlistItemDetailItemId.getText();
		jasperParameter.put("listItemDetailItemId", decoratedString(listItemDetailItemId));

		String listItemDetailItemName = txtlistItemDetailItemName.getText();
		jasperParameter.put("listItemDetailItemName", decoratedString(listItemDetailItemName));

		String madeIn = txtmadeIn.getText();
		jasperParameter.put("madeIn", decoratedString(madeIn));

		String packingStyle = txtpackingStyle.getText();
		jasperParameter.put("packingStyle", decoratedString(packingStyle));

		// For Customer list's report
		String customerName = txtCustomerName.getText();
		jasperParameter.put("customerName", decoratedString(customerName));

		// For ProviderListReport
		String providerName = txtProviderName.getText();
		jasperParameter.put("provider_name", decoratedString(providerName));

		// For ContractDetailReport
		String contract_id = cbbContract_id.getSelectedItem() != null ? cbbContract_id.getSelectedItem().toString() : "";
		jasperParameter.put("contract_id", contract_id);

		// For ContractListReport
		String contractList_contract_id = txtContractList_contract_id.getText();
		jasperParameter.put("contractList_contract_id", decoratedString(contractList_contract_id));
		String contractList_customer_name = txtContractList_customer_name.getText();
		jasperParameter.put("contractList_customer_name", decoratedString(contractList_customer_name));
		String contractList_current_step = txtContractList_current_step.getText();
		jasperParameter.put("contractList_current_step", decoratedString(contractList_current_step));

		// For PurchasingDetailReport
		String purchasingDetailReport_currency_id = cbbPurchasingDetailReport_currency_id.getSelectedItemId();
		jasperParameter.put("PurchasingDetailReport_currency_id", purchasingDetailReport_currency_id);
		String purchasingDetailReport_pp_id = cbbPurchasingDetailReport_pp_id.getSelectedItem().toString();
		jasperParameter.put("PurchasingDetailReport_pp_id", decoratedString(purchasingDetailReport_pp_id));
		String purchasingDetailReport_provider_name = txtPurchasingDetailReport_provider_name.getText();
		jasperParameter.put("PurchasingDetailReport_provider_name", decoratedString(purchasingDetailReport_provider_name));
		String purchasingDetailReport_item_id = txtPurchasingDetailReport_item_id.getText();
		jasperParameter.put("PurchasingDetailReport_item_id", decoratedString(purchasingDetailReport_item_id));
		String purchasingDetailReport_item_name = txtPurchasingDetailReport_item_name.getText();
		jasperParameter.put("PurchasingDetailReport_item_name", decoratedString(purchasingDetailReport_item_name));

		// For Purchasing Transaction List and Sale Transaction List
		String tranxListEmployeeName = txtTranxListEmployeeName.getText();
		jasperParameter.put("tranxList_employee_name", decoratedString(tranxListEmployeeName));

		String current_step = ((ItemWrapper) cbbCurrentStep.getSelectedItem()).toString();
		jasperParameter.put("tranxList_current_step", decoratedString(current_step));

		String tranx_status = ((ItemWrapper) cbbStatus.getSelectedItem()).toString();
		jasperParameter.put("tranxList_status", decoratedString(tranx_status));

		// For TransactionValueReport
		String TransactionValueReport_purchase_currency_id = cbbTransactionValueReport_purchase_currency_id.getSelectedItemId();
		String TransactionValueReport_purchase_note = txtTransactionValueReport_purchase_note.getText();
		String TransactionValueReport_provider_name = txtTransactionValueReport_provider_name.getText();
		String TransactionValueReport_tranx_id = txtTransactionValueReport_tranx_id.getText();
		String TransactionValueReport_pp_id = txtTransactionValueReport_pp_id.getText();
		String TransactionValueReport_employee_name = txtTransactionValueReport_employee_name.getText();
		String TransactionValueReport_current_step = txtTransactionValueReport_current_step.getText();
		String TransactionValueReport_status = txtTransactionValueReport_status.getText();
		jasperParameter.put("TransactionValueReport_purchase_currency_id", TransactionValueReport_purchase_currency_id);
		jasperParameter.put("TransactionValueReport_purchase_note", decoratedString(TransactionValueReport_purchase_note));
		jasperParameter.put("TransactionValueReport_provider_name", decoratedString(TransactionValueReport_provider_name));
		jasperParameter.put("TransactionValueReport_tranx_id", decoratedString(TransactionValueReport_tranx_id));
		jasperParameter.put("TransactionValueReport_pp_id", decoratedString(TransactionValueReport_pp_id));
		jasperParameter.put("TransactionValueReport_employee_name", decoratedString(TransactionValueReport_employee_name));
		jasperParameter.put("TransactionValueReport_current_step", decoratedString(TransactionValueReport_current_step));
		jasperParameter.put("TransactionValueReport_status", decoratedString(TransactionValueReport_status));

		// For WHSCReport
		String WHSCReport_item_id = txtWHSCReport_item_id.getText();
		String WHSCReport_item_name = txtWHSCReport_item_name.getText();
		String WHSCReport_wh_name = txtWHSCReport_wh_name.getText();
		jasperParameter.put("WHSCReport_item_id", decoratedString(WHSCReport_item_id));
		jasperParameter.put("WHSCReport_item_name", decoratedString(WHSCReport_item_name));
		jasperParameter.put("WHSCReport_wh_name", decoratedString(WHSCReport_wh_name));

		Date ins_time = dcInsTime.getDate();
		if (ins_time != null) {
			SimpleDateFormat sdfTransaction = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			jasperParameter.put("tranxList_ins_time", sdfTransaction.format(ins_time));
		} else {
			jasperParameter.put("tranxList_ins_time", "");
		}

		// For SaleDetailReport
		String sale_order_id = cbbsale_order_id.getSelectedItemId();
		String sale_item_id = txtsale_item_id.getText();
		String sale_item_name = txtsale_item_name.getText();
		jasperParameter.put("sale_order_id", decoratedString(sale_order_id));
		jasperParameter.put("sale_item_id", decoratedString(sale_item_id));
		jasperParameter.put("sale_item_name", decoratedString(sale_item_name));

		// For SaleTransactionValueReport
		String sale_tranx_value_currency_id = cbbTransactionValueReport_sale_currency_id.getSelectedItemId();
		String sale_tranx_value_description = txtTransactionValueReport_sale_description.getText();
		String sale_tranx_value_customer_name = txtTransactionValueReport_customer_name.getText();
		String sale_tranx_value_tranx_id = txtTransactionValueReport_sale_tranx_id.getText();
		String sale_tranx_value_order_id = txtTransactionValueReport_sale_order_id.getText();
		String sale_tranx_value_login_id = txtTransactionValueReport_sale_login_id.getText();
		String sale_tranx_value_current_step = txtTransactionValueReport_sale_current_step.getText();
		String sale_tranx_value_sale_status = txtTransactionValueReport_sale_status.getText();
		jasperParameter.put("TransactionValueReport_sale_currency_id", decoratedString(sale_tranx_value_currency_id));
		jasperParameter.put("TransactionValueReport_sale_description", decoratedString(sale_tranx_value_description));
		jasperParameter.put("TransactionValueReport_customer_name", decoratedString(sale_tranx_value_customer_name));
		jasperParameter.put("TransactionValueReport_sale_tranx_id", decoratedString(sale_tranx_value_tranx_id));
		jasperParameter.put("TransactionValueReport_sale_order_id", decoratedString(sale_tranx_value_order_id));
		jasperParameter.put("TransactionValueReport_sale_login_id", decoratedString(sale_tranx_value_login_id));
		jasperParameter.put("TransactionValueReport_sale_current_step", decoratedString(sale_tranx_value_current_step));
		jasperParameter.put("TransactionValueReport_sale_status", decoratedString(sale_tranx_value_sale_status));

		// Must have StartDate and EndDate
		Date startDate = txtFromDate.getDate();
		Date endDate = txtToDate.getDate();
		if (startDate == null) {
			startDate = new Date(0);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		Timestamp startDateTimestamp = new Timestamp(startDate.getTime());
		Timestamp endDateTimestamp = new Timestamp(endDate.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		jasperParameter.put("STARTDATE", startDateTimestamp);
		jasperParameter.put("ENDDATE", endDateTimestamp);

		// Generate preview window for report
		boolean isSuccessful = ReportUlti.previewReport(reportName, jasperParameter);
		fireCAction(new CActionEvent(this, CActionEvent.EXPORT_REPORT_FINISH, null));
		return isSuccessful;
	}

	private String decoratedString(String target) {
		String result = "";
		if (target == null || target == "") {
			result = "%%";
		} else {
			target.replaceAll("%", "");
			result = "%" + target.trim() + "%";
		}
		return result;
	}

	private void fireCAction(CActionEvent action) {
		for (CActionListener listener : lstListener) {
			listener.cActionPerformed(action);
		}
	}

	public void guiActionPerformed(GUIActionEvent action) {
		// TODO Auto-generated method stub

	}

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}

	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void checkPermission() {
		// TODO Auto-generated method stub

	}

	public void addCActionListener(CActionListener al) {
		if (!lstListener.contains(al)) {
			lstListener.add(al);
		}
	}

	public void removeCActionListener(CActionListener al) {
		// TODO Auto-generated method stub

	}

}
