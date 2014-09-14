package com.nn.ishop.client.gui.admin.company;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import org.apache.axis2.databinding.types.xsd.Integer;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CLabel;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.admin.CompanyLogic;
import com.nn.ishop.client.util.CommonUtil;
import com.nn.ishop.client.util.Identifiable;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.validator.Validator;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.company.CompanyBusinessField;
import com.toedter.calendar.JDateChooser;

public class CompanyInformationManager extends AbstractGUIManager implements CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener {
	CWhitePanel companyInformationActionPanel;

	Company currentCompany;
	List<CompanyBusinessField> listBusinessField;

	CLabel txtCompanyName;
	CLabel txtCompanyAddress;
	CLabel txtCompanyTelephone;
	CLabel txtCompanyFax;
	CLabel txtContactPerson;
	CLabel dcestablishedDate;
	
	CLabel lblCompanyName;
	CLabel lblCompanyAddress;
	CLabel lblCompanyTelephone;
	CLabel lblCompanyFax;
	CLabel lblContactPerson;
	CLabel lblEstablishedDate;

	CPaging businessFieldPaging;
	CTextField txtFieldName;
	CompanyLogic companyIns = CompanyLogic.getInstance();
	CompanyBusinessField edittingBusinessField = null;
	
	public CompanyInformationManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "admin/congty/companyInformationPage.xml", getLocale());
		} else {
			initTemplate(this, "admin/congty/companyInformationPage.xml");
		}
		render();
		bindEventHandlers();
	}

	@Override
	protected void applyStyles() {
		if (companyInformationActionPanel != null)
			companyInformationActionPanel.setBorder(new CLineBorder(null, CLineBorder.DRAW_TOP_BORDER));
		/*Font boldTitleFont = lblCompanyName.getFont().deriveFont(Font.BOLD,11);
		lblCompanyName.setFont(boldTitleFont);
		lblCompanyAddress.setFont(boldTitleFont);
		lblCompanyTelephone.setFont(boldTitleFont);
		lblCompanyFax.setFont(boldTitleFont);
		lblContactPerson.setFont(boldTitleFont);
		lblEstablishedDate.setFont(boldTitleFont);*/

	}

	@Override
	protected void bindEventHandlers() {
		businessFieldPaging.addCActionListener(this);
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
		try {
			System.out.println(action.getAction() + ":" + action.getData());
			if (action.getAction() == CActionEvent.COMPANY_NODE_SELECT) {
				currentCompany = (Company) action.getData();
				updateControls();
			}
			if (action.getAction() == CActionEvent.DELETE_PRODUCT) {
				if (action.getData() instanceof String) {
					String data = (String) action.getData();
					CompanyBusinessField field2delete = null;
					for (CompanyBusinessField field : listBusinessField) {
						if (field.getBusiness_field().equals(data)) {
							field2delete = field;
							break;
						}
					}
					if (field2delete != null) {
						field2delete = companyIns.updateCompanyBusinessField(field2delete, CAction.DELETE);
						if (field2delete != null) {
							// Successful
							updateControls();
						} else {
							SystemMessageDialog.showMessageDialog(null, "There are exceptions while delete!", 0);
						}
					}
				}
			}
			if(action.getAction() == CActionEvent.INIT_UPDATE_PRODUCT){
				String editbusinessFieldStr = (String)action.getData();
				if(editbusinessFieldStr != null){
					// This mean refresh list
					updateControls();
				}
				for (CompanyBusinessField item : listBusinessField) {
					if(item.getBusiness_field().equals(editbusinessFieldStr)){
						edittingBusinessField = item;
						txtFieldName.setText(edittingBusinessField.getBusiness_field());
						break;
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void updateControls() {
		
		try {
			if (currentCompany != null) {
				txtCompanyName.setText(currentCompany.getName());
				txtCompanyAddress.setText(currentCompany.getAddress());
				txtCompanyTelephone.setText(currentCompany.getTelephone());
				txtCompanyFax.setText(currentCompany.getFax());
				txtContactPerson.setText(currentCompany.getContact_person());
				dcestablishedDate.setText(SystemConfiguration.sdf.format(currentCompany.getEstablished_date()));
			}
			int companyId = currentCompany.getId();
			listBusinessField = companyIns.getListCompanyBusinessField(companyId);
			if (listBusinessField != null) {
				TableUtil.addListToTable(this.businessFieldPaging, businessFieldPaging.getTable(), listBusinessField);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public Action SAVE_BUSINESS_FIELD = new AbstractAction() {

		public void actionPerformed(ActionEvent arg0) {
			try {
				String businessFieldName = txtFieldName.getText();
				if (Validator.validateEmpty(txtFieldName, Color.WHITE, "not empty please!")) {
					boolean isExisted = false;
					for (CompanyBusinessField item : listBusinessField) {
						if (businessFieldName.equals(item.getBusiness_field())) {
							isExisted = true;
							break;
						}
					}
					if (!isExisted) {
						if(edittingBusinessField == null){
							// Add new
							CompanyBusinessField newField = new CompanyBusinessField();
							newField.setCompany_id(currentCompany.getId());
							newField.setBusiness_field(businessFieldName);

							newField = companyIns.insertCompanyBusinessField(newField);
							if (newField != null) {
								// Successful
								txtFieldName.setText("");
								updateControls();
							} else {
								SystemMessageDialog.showMessageDialog(null, "There are exceptions while insert!", 0);
							}
						}else{
							edittingBusinessField.setBusiness_field(businessFieldName);
							edittingBusinessField = companyIns.updateCompanyBusinessField(edittingBusinessField, CAction.UPDATE);
							if(edittingBusinessField != null){
								// Successful
								txtFieldName.setText("");
								updateControls();
							}else{
								SystemMessageDialog.showMessageDialog(null, "There are exceptions while insert!", 0);
							}
							edittingBusinessField = null;
						}
					} else {
						SystemMessageDialog.showConfirmDialog(null, "This field is existed", 0);
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	};

	public void guiActionPerformed(GUIActionEvent action) {
		// TODO Auto-generated method stub

	}

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}

	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub

	}

	protected void checkPermission() {
		// TODO Auto-generated method stub

	}

	public void addCActionListener(CActionListener al) {
		// TODO Auto-generated method stub

	}

	public void removeCActionListener(CActionListener al) {
		// TODO Auto-generated method stub

	}

}
