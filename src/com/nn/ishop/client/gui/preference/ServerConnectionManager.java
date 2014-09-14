package com.nn.ishop.client.gui.preference;

import java.util.Properties;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.util.Util;

public class ServerConnectionManager extends AbstractGUIManager implements
GUIActionListener, TableModelListener, ListSelectionListener {
	void init() {
		initTemplate(this, "preference/serverConnectionPage.xml");
		render();
		bindEventHandlers();
		
	}
	public ServerConnectionManager(String locale){
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
	CTextField txtServerAddress;
	CTextField txtServerPort;
	CComboBox comboProtocol;
	CTextField txtServerContext;
	CTextField txtServerSubContext;
	
	public void guiActionPerformed(GUIActionEvent action) {
		if(!action.getAction().equals(GUIActionType.SAVE))
			return;
		Properties sysProps = Util.getSystemProperties();
		sysProps.setProperty("pref.server.address", txtServerAddress.getText());
		
		sysProps.setProperty("pref.server.context", txtServerContext.getText());
		sysProps.setProperty("pref.server.port", txtServerPort.getText());
		sysProps.setProperty("pref.server.protocol", comboProtocol.getSelectedItemId());
		sysProps.setProperty("pref.server.sub.context", txtServerSubContext.getText());
		
	}
	void postAction(){
		Properties sysProps = Util.getSystemProperties();
		txtServerAddress.setText(sysProps.getProperty("pref.server.address"));
		txtServerContext.setText(sysProps.getProperty("pref.server.context"));
		txtServerPort.setText(sysProps.getProperty("pref.server.port"));
		ItemWrapper[] items = new ItemWrapper[]{
				new ItemWrapper("http","http")
				,new ItemWrapper("https","https")
		};
		comboProtocol.addItems(items);		
		comboProtocol.setSelectedItemById(sysProps.getProperty("pref.server.protocol"));
		txtServerSubContext.setText(sysProps.getProperty("pref.server.sub.context"));
	}
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
