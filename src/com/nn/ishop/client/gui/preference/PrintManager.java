package com.nn.ishop.client.gui.preference;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;

public class PrintManager extends AbstractGUIManager implements
GUIActionListener, TableModelListener, ListSelectionListener{
	void init() {
		initTemplate(this, "preference/printPage.xml");
		render();
		bindEventHandlers();
		
	}
	public PrintManager(String locale){
			setLocale(locale);
			init();
	}
	@Override
	protected void applyStyles() {
		// TODO Auto-generated method stub

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
	public void guiActionPerformed(GUIActionEvent action) {
		// TODO Auto-generated method stub
		
	}
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
