package com.nn.ishop.client.gui.profile;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPasswordField;

import com.nn.ishop.client.Main;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CButton;
import com.nn.ishop.client.gui.components.CLabel;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.ImageLabel;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.admin.CompanyLogic;
import com.nn.ishop.client.logic.admin.UserLogic;
import com.nn.ishop.client.logic.object.EmployeeWrapper;
import com.nn.ishop.client.util.CryptoToolkit;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.user.Employee;
import com.nn.ishop.server.dto.user.UserRole;
import com.toedter.calendar.JDateChooser;

public class ProfileGUIManager extends AbstractGUIManager{
	JPasswordField txtOldPassword;
	JPasswordField txtNewPassword;
	JPasswordField txtConfirmPassword;
	CButton saveButton;
	CButton cancelButton;
	CLabel lblTenNguoiDung;
	CLabel lblRole;
	CTextField txtTenDangNhap;
	CTextField txtTenCongTy;
	CTextField txtDiaChi;
	CTextField txtDienThoai;
	CTextField txtDiDong;
	CTextField txtViTri1;
	ImageLabel txtPhoto;
	JDateChooser dcNgayBatDau;
	JDateChooser dcNgayKetThuc;
	CTabbedPane changePasswordTab;
	
	public ProfileGUIManager(String locale){
		setLocale(locale);
		init();
		loadAndBindData();
	}
	
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "common/profileDialogPage.xml", getLocale());
		}else{
			initTemplate(this, "profile/profileDialogPage.xml");		
			}
		render();
		txtOldPassword.setBorder(new RoundedCornerBorder(12));
		txtNewPassword.setBorder(new RoundedCornerBorder(12));
		txtConfirmPassword.setBorder(new RoundedCornerBorder(12));
		changePasswordTab.setTitleAt(0, Language.getInstance().getString("password.tab"));
		bindEventHandlers();
	}
	
	public void loadAndBindData(){
		EmployeeWrapper loggedInUser = Main.loggedInUser;
		Employee user = loggedInUser.getEm();
		// Bind full name
		lblTenNguoiDung.setText(user.getName());
		txtTenDangNhap.setText(user.getLogin_id());
		Company userCompany = CompanyLogic.getInstance().getCompanyById(user.getCompany_id());
		txtTenCongTy.setText(userCompany.getName());
		txtDiaChi.setText(user.getAddress());
		txtDienThoai.setText(String.valueOf(user.getHome_telephone()));
		txtDiDong.setText(String.valueOf(user.getMobile()));
		
		txtViTri1.setText(String.valueOf(user.getPosition()));
		Date startDate = user.getStart_date();
		if(startDate != null){
			dcNgayBatDau.setDate(startDate);
		}
		Date endDate = user.getEnd_date();
		if(endDate != null){
			dcNgayKetThuc.setDate(endDate);
		}
		
		// Process role string
		String roleString = "";
		List<UserRole> listRole = loggedInUser.getRole();
		int iter = 0;
		for (UserRole userRole : listRole) {
			if(iter < listRole.size()){
				roleString += userRole.getRoleName() + "  ";
			}else{
				roleString += userRole.getRoleName();
			}
		}
		lblRole.setText(roleString);
		
		try {
			if (user.getAvata() != null) {
				Util.setImageLabelIcon(this.txtPhoto, user.getAvata());
			} else {
				this.txtPhoto.initIcon();
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	public Action SAVE_NEW_PASSWORD = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			String oldPasswordStr = null;
			String newPasswordStr = null;
			String confirmPasswordStr = null;
			if(txtOldPassword.getPassword() != null){
				oldPasswordStr = String.valueOf(txtOldPassword.getPassword());
			}
			if(txtNewPassword.getPassword() != null){
				newPasswordStr = String.valueOf(txtNewPassword.getPassword());
			}
			if(txtConfirmPassword.getPassword()!= null){
				confirmPasswordStr = String.valueOf(txtConfirmPassword.getPassword());
			}
			if(oldPasswordStr != null && newPasswordStr != null && confirmPasswordStr != null){
				String currentPassword = Main.loggedInUser.getEm().getLogin_password();
				if(currentPassword.equals(CryptoToolkit.getSHA512Hash(oldPasswordStr))){
					// Old password is valid
					// Check length must be more than 6 characters
					if(newPasswordStr.length() >= 6){
						if(newPasswordStr.equals(confirmPasswordStr)){
							// New password and confirm password are valid
							// Update into database
							String newLoginPassword = CryptoToolkit.getSHA512Hash(newPasswordStr);
							Main.loggedInUser.getEm().setLogin_password(newLoginPassword);
							UserLogic.getInstance().updateEmployee(Main.loggedInUser.getEm());
							
							SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("change.password.success"), 0);
							// Reset fields
							txtOldPassword.setText("");
							txtNewPassword.setText("");
							txtConfirmPassword.setText("");
						}else{
							SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("new.confirm.password.invalid"), 0);
						}
					}else{
						SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("password.invalid.six.character"), 0);
					}
				}else{
					SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("old.password.invalid"), 0);
				}
			}
			
		}
	};
	
	public Action CANCEL_CHANGE_PASSWORD = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			txtOldPassword.setText("");
			txtNewPassword.setText("");
			txtConfirmPassword.setText("");
		}
	};

	@Override
	protected void bindEventHandlers() {
		
	}

	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	protected void applyStyles() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateList() {
		
	}

	@Override
	protected void checkPermission() {
		
	}

}
