package com.nn.ishop.client.gui.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CTwoModePanel;

public class ReportMasterGUIManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener {
	CTwoModePanel reportConditionContainer;
	CTwoModePanel reportListContainer;
	CTabbedPane reportTabbedPane;
	ReportConditionManager reportCondition;
	ReportListManager reportList;
	
	public ReportMasterGUIManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "baocao/reportMasterPage.xml", getLocale());
		}else{
			initTemplate(this, "baocao/reportMasterPage.xml");		
			}
		render();
		reportCondition = new ReportConditionManager(getLocale());		
		if(reportConditionContainer != null) {
			reportConditionContainer.addContent(reportCondition.getRootComponent());
		}
		reportList = new ReportListManager(getLocale());
		if(reportListContainer != null) {
			reportListContainer.addContent(reportList.getRootComponent());
		}
		bindEventHandlers();
	}
	@Override
	protected void applyStyles() {
		Map<Object, String> tabTitleMap = new HashMap<Object, String>();		
		List<Object> tabObjects = new ArrayList<Object>();
		
		if(reportConditionContainer != null) {
			tabObjects.add(reportConditionContainer);
			tabTitleMap.put(reportConditionContainer,Language.getInstance().getString("report.tabbed.title"));
		}
		
		if(reportListContainer != null) {
			tabObjects.add(reportListContainer);
			tabTitleMap.put(reportListContainer,Language.getInstance().getString("statistics.tabbed.title"));
		}
		
		for(int i=0;i<tabObjects.size();i++){
			Object obj = tabObjects.get(i);
			reportTabbedPane.setTitleAt(i,tabTitleMap.get(obj));
		}
		/*reportTabbedPane.setTitleAt(0, 
				com.nn.ishop.client.gui.Language.getInstance().getString("report.tabbed.title"));
		reportTabbedPane.setTitleAt(1, 
				com.nn.ishop.client.gui.Language.getInstance().getString("statistics.tabbed.title"));*/
	}

	@Override
	protected void bindEventHandlers() {
		reportList.addCActionListener(reportList);
		reportList.addCActionListener(reportCondition);
		reportCondition.addCActionListener(reportList);
		reportCondition.addCActionListener(reportCondition);
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
