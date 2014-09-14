package com.nn.ishop.client.gui.admin.product;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.Main;
import com.nn.ishop.client.ProgressEvent;
import com.nn.ishop.client.ProgressListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.GUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.admin.customer.CustomerListManager;
import com.nn.ishop.client.gui.admin.user.AdminUserGUIManager;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.RoundedBorderComponent;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.gui.components.VerticalFlowLayout;
import com.nn.ishop.client.gui.components.CTwoModePanel.DOCKING_DIRECTION;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.logic.admin.ProductCatLogic;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.product.ProductCategory;
import com.nn.ishop.server.dto.product.ProductUOM;
import com.nn.ishop.server.types.FunctionType;

public class AdminProductGUIManager extends AbstractGUIManager implements
	GUIActionListener,CActionListener, TableModelListener, ListSelectionListener, ProgressListener{
	
	JTextField item_name_text;
	CTwoModePanel  itemCatInforMainContainer;
	CWhitePanel itemDetailPnl, itemListPnl, productDetailInformationComp;
	JSplitPane itemDetailSplit, mainProductSplit;
	RoundedBorderComponent productDetailMasterContainer;
	
	@Override
	public void update() {
	}
	
	
	@Override	
	protected void applyStyles() {		
		itemDetailSplit.setOrientation(0);
		if(itemDetailSplit != null){
			customizeSplitPaneHideFirstComponent(itemDetailSplit);
			itemDetailSplit.setOrientation(0);		
		}
		if(mainProductSplit != null){
			customizeSplitPaneHideFirstComponent(mainProductSplit);
			mainProductSplit.setOrientation(1);		
		}
		productDetailMasterContainer.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(1,1,1,1),				
				new RoundedCornerBorder(CConstants.BORDER_CORNER_8PX_RADIUS)
				));
	}
	ProductCatManager pCat;
	ProductDetailManager pProDetail;
	void init() {
		// 
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "admin/sanpham/adminProductMasterPage.xml", getLocale());
		}else{
			initTemplate(this, "admin/sanpham/adminProductMasterPage.xml");		
			}
		render();
		bindEventHandlers();
	}
	public AdminProductGUIManager (String locale){
		setLocale(locale);
		init();
	}
	@Override
	protected void bindEventHandlers() {
		/** Initialize Category Layout **/
		pCat = new ProductCatManager(getLocale());
		pCat.addProgressListener(this);
		pCat.addCActionListener(this);
		Component cCat = pCat.getRootComponent();
		itemCatInforMainContainer.addContent(cCat);	
		
		itemCatInforMainContainer.addGUIActionListener(this);
		itemCatInforMainContainer.setManagerClazz(ProductCatManager.class);
		itemCatInforMainContainer.setDockableBarLayout(new VerticalFlowLayout(1, VerticalFlowLayout.TOP), DOCKING_DIRECTION.East);
		
		/** Item detail **/
		pProDetail = new ProductDetailManager(getLocale());		
		pProDetail.addProgressListener(this);
		pProDetail.addCActionListener(this);
		
		Component cProDetail = pProDetail.getRootComponent();
		itemDetailPnl.add(cProDetail, BorderLayout.CENTER);
		
		//TODO un-mark
		/*if( Main.loggedInUser.canPerformFunction(FunctionType.PROD_CREATE_CMDFunc))
			GUIManager.locker.setLocked((JComponent)itemDetailPnl, false);*/
		
		/** Initialize Search Layout **/
		ProductListManager pSearch = new ProductListManager(getLocale());
		Component cSearch = pSearch.getRootComponent();
		pSearch.addCActionListener(this);
		
		itemListPnl.add(cSearch, BorderLayout.CENTER);	
				
		
		pCat.addCActionListener(this);
		pCat.addCActionListener(pSearch);
		pCat.addCActionListener(pProDetail);
		
		pSearch.addCActionListener(pProDetail);
		
		pProDetail.addCActionListener(this);
		pProDetail.addCActionListener(pSearch);
		
		//- Because we need all GUI object be initialized already so this action must be here 
		try {
			List<ProductCategory> catLst = ProductCatLogic.getInstance().loadLeafCat();			
			CActionEvent updateControllDataCatEvt = new CActionEvent(this,
					CActionEvent.UPDATE_COMBO_BOX, catLst);
			pCat.cActionPerformed(updateControllDataCatEvt);
			
			List<ProductUOM> uomLst = ProductCatLogic.getInstance().loadUOM();
			CActionEvent updateControllDataUOMEvt = new CActionEvent(this,
					CActionEvent.UPDATE_COMBO_BOX, uomLst);
			pCat.cActionPerformed(updateControllDataUOMEvt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	public void updateList() {

	}

	public void guiActionPerformed(GUIActionEvent action) {
		GUIActionType guideType = action.getAction(); 
		Object srcObj = action.getSource();
		if(guideType.equals( GUIActionType.MINIMIZE_WINDOW)){
			int location = ((Dimension)action.getData()).height;
			if(srcObj.equals(ProductCatManager.class))
				mainProductSplit.setDividerLocation(location);
		}
		if(guideType.equals( GUIActionType.MAXIMIZE_WINDOW)){
			if(srcObj.equals(ProductCatManager.class))
				mainProductSplit.resetToPreferredSizes();
		}
	}

	public void tableChanged(TableModelEvent arg0) {
		
	}

	public void valueChanged(ListSelectionEvent e) {
		
	}
	void fireCAction(CActionEvent action){
		for(CActionListener al:cActionListenerVector)
			al.cActionPerformed(action);	
	}
	CWhitePanel productDetailInformationContainer;
	public void cActionPerformed(CActionEvent action) {
		fireCAction(action);
	}
	protected void checkPermission(){		
	}

	private Vector<ProgressListener> progressListenerVector = new Vector<ProgressListener>();
	
	public void addProgressListener(ProgressListener progressListener){
		progressListenerVector.add(progressListener);
	}
	public void removeProgressListener(ProgressListener progressListener){
		progressListenerVector.remove(progressListener);
	}
	protected void callProgressListener(ProgressEvent e){
		for(ProgressListener progressListener:progressListenerVector)
			progressListener.progressStatusChanged(e);
	}
	public void progressStatusChanged(ProgressEvent evt) {
		callProgressListener(evt);
		
	}
	private Vector<CActionListener> cActionListenerVector = new Vector<CActionListener>();
	public void addCActionListener(CActionListener al) {
		cActionListenerVector.add(al);
		
	}

	public void removeCActionListener(CActionListener al) {
		cActionListenerVector.remove(al);
		
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
		f.setIconImage(Util.getImage(Library.PROGRAM_LOGO));
		guiManager = new AdminProductGUIManager(Language.getInstance().getLocale());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}

}
