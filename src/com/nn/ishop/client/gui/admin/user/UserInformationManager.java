package com.nn.ishop.client.gui.admin.user;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPasswordField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.company.CompanyHelper;
import com.nn.ishop.client.gui.components.CButton2;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.ImageLabel;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.admin.CompanyLogic;
import com.nn.ishop.client.logic.admin.UserLogic;
import com.nn.ishop.client.logic.object.EmployeeWrapper;
import com.nn.ishop.client.util.CryptoToolkit;
import com.nn.ishop.client.util.EmailHelper;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.client.validator.Validator;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.user.Employee;
import com.toedter.calendar.JDateChooser;

public class UserInformationManager extends AbstractGUIManager implements GUIActionListener, CActionListener, ListSelectionListener,
		TableModelListener {

	public CTextField txtTenNguoiDung;
	public CTextField txtTenCongTy;
	public CTextField txtTenDangNhap;
	public JPasswordField txtMatKhau;
	public CTextField txtDiaChi;
	public CTextField txtDienThoai;
	public CTextField txtDiDong;
	public CTextField txtViTri1;
	public JDateChooser dcNgayBatDau;
	public JDateChooser dcNgayKetThuc;
	public CTextField txtBangCap;
	public CTextField txtEmail;
	public ImageLabel txtPhoto;
	public CButton2 btnChooseCompany;
	public CompanyLogic companyLogic = CompanyLogic.getInstance();
	public UserLogic userLogic = UserLogic.getInstance();
	CButton2 btnResetPassword;

	Company selectCompany = null;

	public Employee currentUser = null;
	public EmployeeWrapper currentEmployeeWrapper = null;

	public Language languageInstance = Language.getInstance();

	/** for checking update/add/delete */
	private int lastEventEnum;

	void storeData() {
			logger.info("UserInformationManager: Last event number "+lastEventEnum );
			switch (lastEventEnum) {
			case CActionEvent.UPDATE_USER:
				if (Validator.validateEmpty(txtTenNguoiDung, SystemConfiguration.DEFAULT_DISABLED_TEXT, languageInstance.getString("validate.input.full.name"))
						&& Validator.validateEmpty(txtTenDangNhap, SystemConfiguration.DEFAULT_DISABLED_TEXT, languageInstance.getString("validate.input.login.id"))
						&& Validator.validateEmailFormat(txtEmail, SystemConfiguration.DEFAULT_DISABLED_TEXT, languageInstance.getString("validate.input.valid.email"))) {
					currentUser.setName(txtTenNguoiDung.getText());
					if(selectCompany != null){
						currentUser.setCompany_id(Integer.valueOf(selectCompany.getId()));
					}
					currentUser.setLogin_id(txtTenDangNhap.getText());
					// currentUser.setLogin_password(txtMatKhau.getText());
					currentUser.setAddress(txtDiaChi.getText());
					currentUser.setHome_telephone(Integer.valueOf(txtDienThoai.getText()));
					currentUser.setMobile(Integer.valueOf(txtDiDong.getText()));
					currentUser.setPosition(txtViTri1.getText());
					currentUser.setQualification(txtBangCap.getText());
					currentUser.setStart_date(dcNgayBatDau.getDate());
					currentUser.setEnd_date(dcNgayKetThuc.getDate());
						currentUser.setEmail(txtEmail.getText());
					try {
						currentUser.setUserAvata(Util.getImageAsByteArray(txtPhoto.getIconPath()));
					} catch (Exception e) {
						e.printStackTrace();
					}
	
					currentEmployeeWrapper.setEm(currentUser);
					//-- Notify UserRoleManager to update role for this user
					CActionEvent aeUpdate = new CActionEvent(this, CActionEvent.NOTIFY_UPDATE_USER, currentEmployeeWrapper);
					fireCAction(aeUpdate);	
					setDefaultValue();
					currentUser = null;
				}
				break;
			case CActionEvent.NEW_USER:
				if (Validator.validateEmpty(txtTenNguoiDung, SystemConfiguration.DEFAULT_REQUIRED_TEXT, languageInstance.getString("validate.input.full.name"))
						&& Validator.validateEmpty(txtTenDangNhap, SystemConfiguration.DEFAULT_REQUIRED_TEXT, languageInstance.getString("validate.input.login.id"))
						&& Validator.validateEmptyPassword(txtMatKhau, SystemConfiguration.DEFAULT_REQUIRED_TEXT, languageInstance.getString("validate.input.password"))
						&& Validator.validateEmailFormat(txtEmail, SystemConfiguration.DEFAULT_TEXT, languageInstance.getString("validate.input.valid.email"))) {
					
					// Check user existed
					List<EmployeeWrapper> lstEmps = userLogic.loadListEmployeeWrapper();
					for (EmployeeWrapper employeeWrapper : lstEmps) {
						String loginId = employeeWrapper.getUserId();
						if(loginId!=null && loginId.equals(txtTenDangNhap.getText())){
							txtTenDangNhap.setBackground(SystemConfiguration.DEFAULT_ERROR_TEXT);
							SystemMessageDialog.showMessageDialog(null, languageInstance.getString("validate.input.login.id.existed"), 0);
							return;
						}
					}
					
					Employee newUser = new Employee();
					try {
						newUser.setName(txtTenNguoiDung.getText());
						if (selectCompany != null) {
							newUser.setCompany_id(Integer.valueOf(selectCompany
									.getId()));
						}
						newUser.setLogin_id(txtTenDangNhap.getText());
						newUser.setLogin_password(CryptoToolkit
								.getSHA512Hash(String.valueOf(txtMatKhau
										.getPassword())));
						newUser.setAddress(txtDiaChi.getText());
						newUser.setHome_telephone(Integer.valueOf(txtDienThoai
								.getText()));
						newUser.setMobile(Integer.valueOf(txtDiDong.getText()));
						newUser.setPosition(txtViTri1.getText());
						newUser.setQualification(txtBangCap.getText());
						newUser.setStart_date(dcNgayBatDau.getDate());
						newUser.setEnd_date(dcNgayKetThuc.getDate());
						newUser.setEmail(txtEmail.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
					CActionEvent aeAdd = new CActionEvent(this, CActionEvent.NOTIFY_ADD_USER, newUser);
					fireCAction(aeAdd);
					setDefaultValue();
				}
				break;
			default:
				break;
			}
	}

	private void updateControls(Employee u) {
		if (u != null) {
			this.txtTenNguoiDung.setText(u.getName());
			this.txtTenDangNhap.setText(u.getLogin_id());
			this.txtBangCap.setText(u.getQualification());
			this.txtDiaChi.setText(u.getAddress());
			this.txtDiDong.setText(String.valueOf(u.getMobile()));
			this.txtDienThoai.setText(String.valueOf(u.getHome_telephone()));
			// this.txtMatKhau.setText(u.getLogin_password());
			this.txtEmail.setText(u.getEmail());

			selectCompany = companyLogic.getCompanyById(u.getCompany_id());
			if (selectCompany != null) {
				this.txtTenCongTy.setText(selectCompany.getName());
			} else {
				this.txtTenCongTy.setText("");
			}
			this.txtViTri1.setText(String.valueOf(u.getPosition()));
			this.dcNgayBatDau.setDate(u.getStart_date());
			this.dcNgayKetThuc.setDate(u.getEnd_date());

			try {
				if (u.getAvata() != null) {
					Util.setImageLabelIcon(this.txtPhoto, u.getAvata());
				} else {
					this.txtPhoto.initIcon();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
			lastEventEnum = CActionEvent.UPDATE_USER;
			

		}
	}

	public String newPassword() {
		return UUID.randomUUID().toString().substring(0, 6);
	}

	private void setDefaultValue() {
		updateControls(new Employee());
		txtMatKhau.setText("");
	}

	public UserInformationManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "admin/nguoidung/userInformationPage.xml", getLocale());
		} else {
			initTemplate(this, "admin/nguoidung/userInformationPage.xml");
		}
		render();

		txtMatKhau.setBorder(new RoundedCornerBorder(12));

		prepareData();
		bindEventHandlers();
	}

	public void prepareData() {
		dcNgayBatDau.setDate(new Date());
		dcNgayKetThuc.setDate(new Date());
	}

	public Action cmdChooseCompanyDialog = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			selectCompany = CompanyHelper.chooseCompany();
			if (selectCompany != null) {
				txtTenCongTy.setText(String.valueOf(selectCompany.getName()));
			}
		}
	};

	public Action cmdResetPassword = new AbstractAction() {
		public void actionPerformed(ActionEvent arg0) {
			if (currentUser != null) {
				int confirm = SystemMessageDialog.showConfirmDialog(null, 
						languageInstance.getString("alert.confirm.reset.password"), 
						SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
				if (confirm == SystemMessageDialog.OK_BUTTON_CLICK) {
					// Prevent spam click
					btnResetPassword.setEnabled(false);
					
					String newPassword = newPassword();
					// Update into database
					currentUser.setLogin_password(CryptoToolkit.getSHA512Hash(newPassword));
					currentEmployeeWrapper.setEm(currentUser);
					CActionEvent aeUpdate = new CActionEvent(this, CActionEvent.NOTIFY_UPDATE_USER, currentEmployeeWrapper);
					fireCAction(aeUpdate);
					// Send email to user
					String userEmail = currentUser.getEmail();
					if (userEmail != null && userEmail != "") {
						EmailHelper emHelper = EmailHelper.getInstance();
						if (emHelper.sendEmail(userEmail, languageInstance.getString("change.password.email.subject"),
								"Dear " + currentUser.getLogin_id() + "\n" + languageInstance.getString("change.password.email.message") + newPassword)) {
							SystemMessageDialog.showMessageDialog(null, languageInstance.getString("alert.new.password") + newPassword, 0);
						} else {
							SystemMessageDialog.showMessageDialog(null, languageInstance.getString("alert.change.ok.send.mail.fail") + newPassword, 0);
						}
					} else {
						SystemMessageDialog.showMessageDialog(null, languageInstance.getString("alert.change.ok.no.email.to.send" + newPassword), 0);
					}
					setDefaultValue();
					currentUser = null;
				}
			}
		}
	};

	public void fireCAction(CActionEvent action) {
		for (CActionListener al : cActionListnerVct) {
			al.cActionPerformed(action);
		}
	}

	@Override
	protected void applyStyles() {
	}

	@Override
	protected void bindEventHandlers() {
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

	public void cActionPerformed(CActionEvent action) {
		int actionNum = action.getAction();
		EmployeeWrapper data = null;
		if (CActionEvent.LIST_SELECT_ITEM == actionNum) {
			data = (EmployeeWrapper) action.getData();
			if (data == null)
				return;
			currentEmployeeWrapper = data;
			currentUser = data.getEm();
			
			updateControls(currentUser != null ? currentUser : new Employee());
			fireCAction(new CActionEvent(this, CActionEvent.NOTIFY_ACTION, 
					Language.getInstance().getString("notify.complete.loading.selected.item")));
			
		} else if (CActionEvent.NEW_USER == actionNum) {
			newUser();
			fireCAction(new CActionEvent(this, CActionEvent.NOTIFY_ACTION, 
					Language.getInstance().getString("notify.complete.initialize.new.user")));

		} else if (CActionEvent.SAVE_USER == actionNum) {
			storeData();
			fireCAction(new CActionEvent(this, CActionEvent.NOTIFY_ACTION, 
					Language.getInstance().getString("notify.complete.store.data")));
		}
	}

	public void valueChanged(ListSelectionEvent e) {
	}

	public void tableChanged(TableModelEvent e) {
	}

	@Override
	protected void checkPermission() {
	}

	Vector<CActionListener> cActionListnerVct = new Vector<CActionListener>();

	public void addCActionListener(CActionListener al) {
		cActionListnerVct.add(al);
	}

	public void removeCActionListener(CActionListener al) {
	}

	void newUser() {
//		if (!isActived) {
			setDefaultValue();
			txtMatKhau.setEnabled(true);
			lastEventEnum = CActionEvent.NEW_USER;
			
//			isActived = true;
//		} else {
//			cActionPerformed(new CActionEvent(this, CActionEvent.NOTIFY_ACTION, Language.getInstance().getString("finish.current.process.first")));
//		}
	}
}
