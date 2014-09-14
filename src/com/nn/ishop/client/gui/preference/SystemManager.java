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
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.util.Util;

public class SystemManager extends AbstractGUIManager implements
GUIActionListener, TableModelListener, ListSelectionListener{
	void init() {
		initTemplate(this, "preference/systemPage.xml");
		render();
		bindEventHandlers();
		
	}
	public SystemManager(String locale){
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
	CCheckBox checkAlwaysCache ;
	CTextField txtCacheFolder;
	CCheckBox  checkDbAction;
	CCheckBox checkBackendAction;
	CCheckBox checkFrontendAction;
	CCheckBox checkAllAction;
	CCheckBox whenSystemShutdown;
	CCheckBox manualBackup;
	CCheckBox neverBackup;
	Properties sysProps;
	CTextField txtDateTimeFormat;
	public void guiActionPerformed(GUIActionEvent action) {
		if(!action.getAction().equals(GUIActionType.SAVE))
			return;
		sysProps = Util.getSystemProperties();
		sysProps.setProperty("always.cache", String.valueOf(checkAlwaysCache.isSelected()));	
		sysProps.setProperty("cache.folder",txtCacheFolder.getText() );
		
		sysProps.setProperty("pref.system.audit.db.action",String.valueOf(checkDbAction.isSelected()) );
		sysProps.setProperty("pref.system.audit.backend.action",String.valueOf(checkBackendAction.isSelected()) );
		sysProps.setProperty("pref.system.audit.frontend.action", String.valueOf(checkFrontendAction.isSelected()));
		sysProps.setProperty("pref.system.audit.all.action",String.valueOf(checkAllAction.isSelected()) );
		
		sysProps.setProperty("pref.system.autobackup.shutdown.system",String.valueOf(whenSystemShutdown.isSelected()) );
		sysProps.setProperty("pref.system.autobackup.manual", String.valueOf(manualBackup.isSelected()));
		sysProps.setProperty("pref.system.autobackup.never",String.valueOf(neverBackup.isSelected()) );
		
		sysProps.setProperty("date.pattern",txtDateTimeFormat.getText() );
		
		Util.setSystemProperties(sysProps);
	}
	void postAction(){
		sysProps = Util.getSystemProperties();
		checkAlwaysCache.setSelected(Boolean.valueOf(sysProps.getProperty("always.cache")));
		txtCacheFolder.setText(sysProps.getProperty("cache.folder"));
		checkDbAction.setSelected(Boolean.valueOf(sysProps.getProperty("pref.system.audit.db.action")));
		checkBackendAction.setSelected(Boolean.valueOf(sysProps.getProperty("pref.system.audit.backend.action")));		
		checkFrontendAction.setSelected(Boolean.valueOf(sysProps.getProperty("pref.system.audit.frontend.action")));
		checkAllAction.setSelected(Boolean.valueOf(sysProps.getProperty("pref.system.audit.all.action")));		
		whenSystemShutdown.setSelected(Boolean.valueOf(sysProps.getProperty("pref.system.autobackup.shutdown.system")));		
		manualBackup.setSelected(Boolean.valueOf(sysProps.getProperty("pref.system.autobackup.manual")));		
		neverBackup.setSelected(Boolean.valueOf(sysProps.getProperty("pref.system.autobackup.never")));				
		txtDateTimeFormat.setText(sysProps.getProperty("date.pattern"));
	}
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
