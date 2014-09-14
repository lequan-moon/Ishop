package com.nn.ishop.client.gui.home;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.ChartUtil;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.gui.sale.SaleMasterGUIManager;
import com.nn.ishop.client.util.Util;

public class HomeGUIManager extends AbstractGUIManager {

	CWhitePanel pnlChart1, pnlChart2, pnlChart3, pnlChart4, pnlChart5, pnlChart6;	
	CTabbedPane homeTabbedPane;
	
	public HomeGUIManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "home/homeMasterPage.xml", getLocale());
		}else{
			initTemplate(this, "home/homeMasterPage.xml");		
			}
		render();
		bindEventHandlers();
	}
	@Override
	protected void applyStyles() {
		homeTabbedPane.setTitleAt(0, Language.getInstance().getString("home.title.0"));
		homeTabbedPane.setIconAt(0, Util.getIcon("tabbed/tabbed-icon-general.png"));
	}

	@Override
	protected void bindEventHandlers() {
		try {
			pnlChart1
					.setBorder(BorderFactory
							.createCompoundBorder(
									new RoundedCornerBorder()/*new CLineBorder(null, CLineBorder.DRAW_ALL_BORDER)*/,
									BorderFactory.createEmptyBorder(1, 1, 1, 1)));
			pnlChart2
					.setBorder(BorderFactory
							.createCompoundBorder(
									new RoundedCornerBorder()/*new CLineBorder(null, CLineBorder.DRAW_ALL_BORDER)*/,
									BorderFactory.createEmptyBorder(1, 1, 1, 1)));
			pnlChart3
					.setBorder(BorderFactory
							.createCompoundBorder(
									new RoundedCornerBorder()/*new CLineBorder(null, CLineBorder.DRAW_ALL_BORDER)*/,
									BorderFactory.createEmptyBorder(1, 1, 1, 1)));
			pnlChart4
					.setBorder(BorderFactory
							.createCompoundBorder(
									new RoundedCornerBorder()/*new CLineBorder(null, CLineBorder.DRAW_ALL_BORDER)*/,
									BorderFactory.createEmptyBorder(1, 1, 1, 1)));
			pnlChart5
					.setBorder(BorderFactory
							.createCompoundBorder(
									new RoundedCornerBorder()/*new CLineBorder(null, CLineBorder.DRAW_ALL_BORDER)*/,
									BorderFactory.createEmptyBorder(1, 1, 1, 1)));
			pnlChart6
					.setBorder(BorderFactory
							.createCompoundBorder(
									new RoundedCornerBorder()/*new CLineBorder(null, CLineBorder.DRAW_ALL_BORDER)*/,
									BorderFactory.createEmptyBorder(1, 1, 1, 1)));
			final ChartUtil cu = new ChartUtil();
			Runnable  lazyBuilder = new Runnable(){
				public void run() {	
					pnlChart1.add(cu.drawLineChart(null), BorderLayout.NORTH);
					pnlChart2.add(cu.drawBarChart(null), BorderLayout.NORTH);
					pnlChart3.add(ChartUtil.drawPieChart(null), BorderLayout.NORTH);
					pnlChart4.add(ChartUtil.drawPieChart(null), BorderLayout.NORTH);
					pnlChart5.add(cu.drawBarChart(null), BorderLayout.NORTH);
					pnlChart6.add(cu.drawLineChart(null), BorderLayout.NORTH);
				}
			};
			Thread t = new Thread(lazyBuilder);
			t.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void checkPermission() {
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
	public static void main(String[] args ) throws Exception{
		AbstractGUIManager guiManager; 
		try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}	
		Language.loadLanguage("vn");
		
		JFrame f = new JFrame();
		
		guiManager = new HomeGUIManager(Language.getInstance().getLocale());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}
}
