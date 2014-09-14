package com.nn.ishop.client.gui.report;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JList;
import javax.swing.Timer;
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
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.util.ItemWrapper;

public class ReportListManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener, ActionListener {
	JList reportList;
	CWhitePanel reportActionPanel;
	CDialogsLabelButton reportActionPanelCreateReportButton;
	List<ReportWrapper> listReport = new ArrayList<ReportListManager.ReportWrapper>();
	List<CActionListener> lstListener = new ArrayList<CActionListener>();
	Language langIns = Language.getInstance();
	Timer timer = new Timer(300, this);
	
	public ReportListManager(String locale) {
		setLocale(locale);
		init();
	}

	@SuppressWarnings("unchecked")
	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "baocao/reportListPage.xml", getLocale());
		} else {
			initTemplate(this, "baocao/reportListPage.xml");
		}
		render();

		listReport.add(new ReportWrapper(0, langIns
				.getString("report.list.CompanyListReport"), langIns
				.getString("report.list.item.0")));
		listReport.add(new ReportWrapper(1, langIns
				.getString("report.list.DailySaleReport"), langIns
				.getString("report.list.item.1")));
		listReport.add(new ReportWrapper(2, langIns
				.getString("report.list.DailyStockRemainReport"), langIns
				.getString("report.list.item.2")));
		listReport.add(new ReportWrapper(3, langIns
				.getString("report.list.ItemListReport"), langIns
				.getString("report.list.item.3")));
		listReport.add(new ReportWrapper(4, langIns
				.getString("report.list.PurchaseReport"), langIns
				.getString("report.list.item.4")));
		listReport.add(new ReportWrapper(5, langIns
				.getString("report.list.InternalSaleReport"), langIns
				.getString("report.list.item.5")));
		listReport.add(new ReportWrapper(6, langIns
				.getString("report.list.SaleReport"), langIns
				.getString("report.list.item.6")));
		listReport.add(new ReportWrapper(7, langIns
				.getString("report.list.WhatTheFuckReport"), langIns
				.getString("report.list.item.7")));
		listReport.add(new ReportWrapper(8, langIns
				.getString("report.list.MixedBriefReport"), langIns
				.getString("report.list.item.8")));
		listReport.add(new ReportWrapper(9, langIns
				.getString("report.list.MixedDetailReport"), langIns
				.getString("report.list.item.9")));
		listReport.add(new ReportWrapper(10, langIns
				.getString("report.list.ReturningItemReport"), langIns
				.getString("report.list.item.10")));
		listReport.add(new ReportWrapper(11, langIns
				.getString("report.list.EmployeeListReport"), langIns
				.getString("report.list.item.11")));
		listReport.add(new ReportWrapper(12, langIns
				.getString("report.list.ItemReport"), langIns
				.getString("report.list.item.12")));
		listReport.add(new ReportWrapper(13, langIns
				.getString("report.list.WarehouseLotReport"), langIns
				.getString("report.list.item.13")));
		listReport.add(new ReportWrapper(14, langIns
				.getString("report.list.GoodsReceiveReport"), langIns
				.getString("report.list.item.14")));
		listReport.add(new ReportWrapper(15, langIns
				.getString("report.list.ListItemDetail"), langIns
				.getString("report.list.item.15")));
		listReport.add(new ReportWrapper(16, langIns
				.getString("report.list.ListCustomerReport"), langIns
				.getString("report.list.item.16")));
		listReport.add(new ReportWrapper(17, langIns
				.getString("report.list.PurchasingTransactionList"), langIns
				.getString("report.list.item.17")));
		listReport.add(new ReportWrapper(18, langIns
				.getString("report.list.ProviderListReport"), langIns
				.getString("report.list.item.18")));
		listReport.add(new ReportWrapper(19, langIns
				.getString("report.list.ContractDetailReport"), langIns
				.getString("report.list.item.19")));
		listReport.add(new ReportWrapper(20, langIns
				.getString("report.list.ContractListReport"), langIns
				.getString("report.list.item.20")));
		listReport.add(new ReportWrapper(21, langIns
				.getString("report.list.PurchasingDetailReport"), langIns
				.getString("report.list.item.21")));
		listReport.add(new ReportWrapper(22, langIns
				.getString("report.list.TransactionValueReport"), langIns
				.getString("report.list.item.22")));
		listReport.add(new ReportWrapper(23, langIns
				.getString("report.list.WarehouseStockCardReport"), langIns
				.getString("report.list.item.23")));
		listReport.add(new ReportWrapper(24, langIns
				.getString("report.list.OutSaleTransactionList"), langIns
				.getString("report.list.item.24")));
		listReport.add(new ReportWrapper(25, langIns
				.getString("report.list.SaleDetailReport"), langIns
				.getString("report.list.item.25")));
		listReport.add(new ReportWrapper(26, langIns
				.getString("report.list.SaleTransactionValueReport"), langIns
				.getString("report.list.item.26")));
		bindEventHandlers();

	}

	public AbstractAction printButtonAction = new AbstractAction() {

		public void actionPerformed(ActionEvent e) {
			reportActionPanelCreateReportButton.setEnabled(false);
			reportActionPanelCreateReportButton.setText(langIns.getString("button.loading"));
			timer.start();
		}
	};

	@Override
	protected void applyStyles() {
		reportActionPanel.setBorder(new CLineBorder(null,
				CLineBorder.DRAW_TOP_BORDER));
	}

	@Override
	protected void bindEventHandlers() {
		ItemWrapper[] lstItem = new ItemWrapper[listReport.size()];
		for (ReportWrapper report : listReport) {
			lstItem[report.getId()] = new ItemWrapper(String.valueOf(report
					.getId()), report.getAlias());
		}
		reportList.setListData(lstItem);

		reportList.addListSelectionListener(this);
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
		if (action.getAction() == CActionEvent.EXPORT_REPORT_FINISH) {
			// Disable button print and change label of button
			reportActionPanelCreateReportButton.setEnabled(true);
			reportActionPanelCreateReportButton.setText(langIns.getString("button.create"));
		}
	}

	public void guiActionPerformed(GUIActionEvent action) {
		// TODO Auto-generated method stub

	}

	public void valueChanged(ListSelectionEvent e) {
		ItemWrapper selectedReport = (ItemWrapper) reportList
				.getSelectedValue();
		ReportWrapper report = listReport.get(Integer.valueOf(selectedReport
				.getId()));
		String reportName = report.getName();
		CActionEvent caEvent = new CActionEvent(this,
				CActionEvent.LIST_SELECT_ITEM, reportName);
		fireCAction(caEvent);
	}

	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void checkPermission() {
		// TODO Auto-generated method stub

	}

	public void addCActionListener(CActionListener al) {
		if (!lstListener.contains(al)) {
			lstListener.add(al);
		}
	}

	public void removeCActionListener(CActionListener al) {
		// TODO Auto-generated method stub

	}

	public void fireCAction(CActionEvent e) {
		for (CActionListener listener : lstListener) {
			listener.cActionPerformed(e);
		}
	}

	class ReportWrapper {
		private int id;
		private String name;
		private String alias;

		public ReportWrapper(int id, String name, String alias) {
			super();
			this.id = id;
			this.name = name;
			this.alias = alias;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}
	}

	public void actionPerformed(ActionEvent e) {
		timer.stop();
		ItemWrapper selectedReport = (ItemWrapper) reportList
				.getSelectedValue();
		ReportWrapper report = listReport.get(Integer
				.valueOf(selectedReport.getId()));
		String reportName = report.getName();
		CActionEvent caEvent = new CActionEvent(this,
				CActionEvent.EXPORT_REPORT, reportName);
		fireCAction(caEvent);
	}

}
