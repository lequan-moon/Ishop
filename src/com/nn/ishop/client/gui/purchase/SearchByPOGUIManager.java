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

public class SearchByPOGUIManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener {
	public SearchByPOGUIManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "purchase/search/searchByPO.xml", getLocale());
		}else{
			initTemplate(this, "purchase/search/searchByPO.xml");		
			}
		render();
		bindEventHandlers();
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
	public void cActionPerformed(CActionEvent action) {

	}
	public void guiActionPerformed(GUIActionEvent action) {

	}
	public void valueChanged(ListSelectionEvent e) {

	}
	public void tableChanged(TableModelEvent e) {

	}
	@Override
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
