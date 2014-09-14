package com.nn.ishop.client.gui.admin.customer;

import java.awt.BorderLayout;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.NetworkGraphPane;
import com.nn.ishop.client.gui.NetworkSparseGraph;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.server.dto.AbstractIshopEntity;
import com.nn.ishop.server.dto.EntityRelation;

public class CustomerNetworkManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener {
	CWhitePanel customerNetworkContainer;	
	
	public CustomerNetworkManager (String locale){
		setLocale(locale);
		init();
	}
	
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "admin/khachhang/customerNetworkPage.xml", getLocale());
		}else{
			initTemplate(this, "admin/khachhang/customerNetworkPage.xml");		
			}		
		render();
		bindEventHandlers();		
	}
	
	@Override
	protected void applyStyles() {		
			
	}

	@Override
	protected void bindEventHandlers() {
		NetworkGraphPane netGraphPane = new NetworkGraphPane();
		NetworkSparseGraph<AbstractIshopEntity,EntityRelation> graph = new NetworkSparseGraph<AbstractIshopEntity,EntityRelation>();
		graph.buildGraph();
		netGraphPane.init(graph);		
		customerNetworkContainer.add(netGraphPane, BorderLayout.CENTER);
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
	protected void checkPermission() {
	}
	public void addCActionListener(CActionListener al) {
	}
	public void removeCActionListener(CActionListener al) {
	}

}
