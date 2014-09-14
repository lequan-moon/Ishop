package com.nn.ishop.client.gui.purchase;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;

public class SearchByCategoryGUIManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener {
	public SearchByCategoryGUIManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "purchase/search/searchByCategory.xml", getLocale());
		}else{
			initTemplate(this, "purchase/search/searchByCategory.xml");		
			}
		render();
		bindEventHandlers();
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
		// TODO Auto-generated method stub

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
