package com.nn.ishop.client.gui.preference;

import java.util.Properties;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.components.CCheckBox;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.license.PropertyUtil;

public class GeneralManager extends AbstractGUIManager implements
GUIActionListener, TableModelListener, ListSelectionListener{
	CWhitePanel generalInforDetail;
	
	void init() {
		initTemplate(this, "preference/generalPage.xml");
		render();
		bindEventHandlers();
		
	}
	public GeneralManager(String locale){
			setLocale(locale);
			init();
	}
	@Override
	protected void applyStyles() {
		postAction();
	}

	@Override
	protected void bindEventHandlers() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void checkPermission() {
		// TODO Auto-generated method stub

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
	CCheckBox requiredLogin;
	CComboBox comboboxSelectDefaultLanguage;
	CTextField txtLanguagePrefix;
	CTextField txtLogFilePath;
	CTextField txtLicenseFilePath;
	CTextField txtBackupFilePath;
	public void guiActionPerformed(GUIActionEvent action) {
		if(!action.getAction().equals(GUIActionType.SAVE))
			return;
		Properties sysProps = Util.getSystemProperties();
		
		sysProps.setProperty("login.infor.need.login", String.valueOf(requiredLogin.isSelected()));		
		try{
			ItemWrapper defLang = (ItemWrapper)comboboxSelectDefaultLanguage.getSelectedItem();
			sysProps.setProperty("login.infor.default.language", defLang.getId()) ;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		sysProps.setProperty("login.infor.language.prefix", txtLanguagePrefix.getText());
		sysProps.setProperty("log.infor.log.file.path", txtLogFilePath.getText());
		sysProps.setProperty("license.infor.file.path", txtLicenseFilePath.getText());
		sysProps.setProperty("backup.infor.file.path", txtBackupFilePath.getText());
		
		Util.setSystemProperties(sysProps);
		

		
	}
	void postAction(){
		Properties sysProps = Util.getSystemProperties();
		
		sysProps.getProperty("login.infor.need.login");
		if(sysProps.getProperty("login.infor.need.login") != null && 
				sysProps.getProperty("login.infor.need.login").equalsIgnoreCase("true"))
			requiredLogin.setSelected(true);		
		
		txtLanguagePrefix.setText(sysProps.getProperty("login.infor.language.prefix"));
		txtLogFilePath.setText(sysProps.getProperty("log.infor.log.file.path" ));
		txtLicenseFilePath.setText(sysProps.getProperty("license.infor.file.path"));
		txtBackupFilePath.setText(sysProps.getProperty("backup.infor.file.path"));
		ItemWrapper[] items = new ItemWrapper[]{new ItemWrapper("vn", "Vietnamese")
		,new ItemWrapper("cn", "Chinese")
		, new ItemWrapper("en", "English")
		, new ItemWrapper("de", "Deusch")};
		comboboxSelectDefaultLanguage.addItems(items);
		comboboxSelectDefaultLanguage.setSelectedItem(sysProps.getProperty("login.infor.default.language"));
	}
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
