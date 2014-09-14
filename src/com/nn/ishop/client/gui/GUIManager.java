package com.nn.ishop.client.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import javax.help.search.SearchEvent;
import javax.help.search.SearchListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.apache.velocity.VelocityContext;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.ProgressEvent;
import com.nn.ishop.client.ProgressListener;
import com.nn.ishop.client.gui.admin.AdminGUIManager;
import com.nn.ishop.client.gui.arap.ARAPMasterGUIManager;
import com.nn.ishop.client.gui.components.CButton;
import com.nn.ishop.client.gui.components.CLabel;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CProgressBar;
import com.nn.ishop.client.gui.components.ComponentLocker;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.gui.dialogs.AboutDialog;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.nn.ishop.client.gui.helper.DialogHelper;
import com.nn.ishop.client.gui.home.HomeGUIManager;
import com.nn.ishop.client.gui.preference.PreferenceGUIManager;
import com.nn.ishop.client.gui.purchase.PurchaseMasterGUIManager;
import com.nn.ishop.client.gui.report.ReportMasterGUIManager;
import com.nn.ishop.client.gui.sale.SaleMasterGUIManager;
import com.nn.ishop.client.typedef.PerspectiveType;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.Util;

public class GUIManager extends AbstractGUIManager
       implements GUIActionListener, ProgressListener, CActionListener {

	private PerspectiveType currentPerspective;

	public static JFrame mainFrame;
	public static ComponentLocker guiLocker;
	private JPanel contentPanel;
	private JPanel rootPanel; 
	private JPanel statusBar;

	/** instances of tool bar button */
	private CButton adminButton;
	private CButton purchaseButton;
	private CButton saleButton;
	private CButton reportButton;
	//- New
	private CButton homeButton;
	private CButton payableButton;	
	//private CButton receiptableButton;
	AdminGUIManager adminGUIManager;
	
	private Hashtable <CButton, PerspectiveType> toolBarButtonsHashtable = new Hashtable <CButton, PerspectiveType>();
	
	private JPanel adminPerspective;
	private JPanel receivePerspective;
	private JPanel salePerspective;
	private JPanel reportPerspective;
	// - new 	
	private JPanel homePerspective;
	private JPanel payablePerspective;
	private JPanel receiptablePerspective;
	
	private JTextField searchText;
	CButton searchButton;
	private JPanel mainCompositeToolBar;
	public static ComponentLocker locker = null;

	private Vector<CActionListener> cListenerVector = new Vector<CActionListener>();
	private Vector<SearchListener> searchListenerVector = new Vector<SearchListener>();
	
	private CProgressBar progressBar;
	private CLabel systemInfoLabel;
	
	
	JMenuItem aboutMenuItem;	
	JMenuBar mainMenuBar;
	private JPanel searchBar;
	private JPanel menuAndSearchBar;

	private Hashtable <PerspectiveType, Component> perspectives = new Hashtable <PerspectiveType, Component>();

	public GUIManager( String locale) {
		init(locale);
	}
	public GUIManager() {
		init("vn");
	}
	private void setDefaultLookAndFeel(){
		try {
			    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
	private void init(String locale) {
		velocityContext = new VelocityContext();
		velocityContext.put("canManageAdmin", Boolean.FALSE);
		setDefaultLookAndFeel();
		initTemplate(this, "mainFrame.xml", locale);
		render();
		commonSetting(locale);
		bindEventHandlers();
	}
	

	void commonSetting(String locale)
	{		
//		DisabledGlassPane glassPane = new DisabledGlassPane();
//		JRootPane rootPane = SwingUtilities.getRootPane(GUIManager.mainFrame);
//		rootPane.setGlassPane( glassPane );
//		glassPane.activate(Language.getInstance().getString("please.wait.while.loading"));		
		locker = new ComponentLocker(mainFrame.getLayeredPane());
		
//	    GraphicsEnvironment env = GraphicsEnvironment
//	            .getLocalGraphicsEnvironment();
//	    GraphicsDevice vc = env.getDefaultScreenDevice();
//	    mainFrame.setUndecorated(true);
//	    mainFrame.setResizable(false);
//	    vc.setFullScreenWindow(mainFrame);

		//start panel for main use cases person
		adminGUIManager = new AdminGUIManager(locale);
		adminGUIManager.addProgressListener(this);
		adminGUIManager.addCActionListener(this);
		
		adminGUIManager.addGUIActionListener(this);
		
		//- if user can use administrator module
		if(adminButton != null)
			adminPerspective = (JPanel)adminGUIManager.getRootComponent();
		
		reportPerspective = (JPanel)(new ReportMasterGUIManager(locale)).getRootComponent();
		salePerspective = (JPanel)(new SaleMasterGUIManager(getLocale())).getRootComponent();
		receivePerspective = (JPanel)(new PurchaseMasterGUIManager(getLocale())).getRootComponent();
		
		homePerspective = (JPanel)(new HomeGUIManager(locale)).getRootComponent();
		
		payablePerspective = (JPanel)(new ARAPMasterGUIManager(getLocale())).getRootComponent();		
		
		receiptablePerspective = new JPanel(new BorderLayout());
		receiptablePerspective.add(new JLabel(Util.getIcon("dialog/page_title.png"))
		, BorderLayout.CENTER);
		
		contentPanel.add(homePerspective,BorderLayout.CENTER);
		
		mainFrame.validate();
		mainFrame.setVisible(true);
		/** register current view */		
		setCurrentPerspective((currentPerspective != null)? currentPerspective :PerspectiveType.SYSADMIN);
		bindEventHandlers();
		/*
		 * Set mainframe visible only when everything is done
		 */
//		mainFrame.setVisible(true);
//		glassPane.deactivate();
	}
	protected void checkPermission(){
	}
	public void switchLanguage(String locale)
	{
	 	loadLanguage(locale);
		mainFrame.getContentPane().remove(rootPanel);
		rootPanel = null;
		rootPanel = (JPanel)getSubComponent("rootPanel");
		mainFrame.getContentPane().add("Center", rootPanel);
		mainFrame.setTitle(Language.getInstance().getString("appTitle"));
		applyStyles();
		commonSetting(locale);
		mainFrame.validate();
		System.gc();
		
	}

	protected void bindEventHandlers() {
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});

		// old style
		//mainToolBar.setAcl(this);

		searchText.addFocusListener(new FocusAdapter(){
			@Override
			public void focusGained(FocusEvent e) {
				searchText.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				searchText.setText(Language.getInstance().getString("defaultSearchText"));
			}
		});
		if(adminPerspective != null)
			perspectives.put(PerspectiveType.SYSADMIN, adminPerspective);
		if(receivePerspective != null)
			perspectives.put(PerspectiveType.PURCHASE , receivePerspective);
		
		if(salePerspective != null)
		perspectives.put(PerspectiveType.SALE, salePerspective);
		
		//perspectives.put(PerspectiveType.NETWORK, new JPanel(new BorderLayout()));
		if(reportPerspective != null)
		perspectives.put(PerspectiveType.REPORT, reportPerspective);
		
		perspectives.put(PerspectiveType.HOME, homePerspective);
		
		if(payablePerspective != null)
		perspectives.put(PerspectiveType.PAYABLE, payablePerspective);
		//perspectives.put(PerspectiveType.RECEIPTABLE, receiptablePerspective);
		
		if(adminButton != null)
			toolBarButtonsHashtable.put(adminButton,PerspectiveType.SYSADMIN);
		
		if(purchaseButton != null)
			toolBarButtonsHashtable.put(purchaseButton, PerspectiveType.PURCHASE);
		
		if(saleButton != null)
			toolBarButtonsHashtable.put(saleButton, PerspectiveType.SALE);
		
		if(reportButton != null)
			toolBarButtonsHashtable.put(reportButton, PerspectiveType.REPORT);
		
		toolBarButtonsHashtable.put(homeButton, PerspectiveType.HOME);
		
		if(payableButton != null)
			toolBarButtonsHashtable.put(payableButton, PerspectiveType.PAYABLE);
		
		homeButton.addActionListener(new AbstractAction(){
			private static final long serialVersionUID = -5022861995237472198L;
			public void actionPerformed(ActionEvent e) {
				switchPerspective(homeButton);
			}
		});
	
		if(payableButton != null)
			payableButton.addActionListener(new AbstractAction(){
				private static final long serialVersionUID = -5022861995237472198L;
				public void actionPerformed(ActionEvent e) {
					switchPerspective(payableButton);
				}
			});
		if(adminButton != null)
			adminButton.addActionListener(new AbstractAction(){
				private static final long serialVersionUID = -5022861995237472198L;
				public void actionPerformed(ActionEvent e) {
					switchPerspective(adminButton);
				}
			});
		if(purchaseButton != null)
			purchaseButton.addActionListener(new AbstractAction(){
				private static final long serialVersionUID = -5022861995237472198L;
				public void actionPerformed(ActionEvent e) {
					switchPerspective(purchaseButton);
				}
			});
		if(saleButton != null)
			saleButton.addActionListener(new AbstractAction(){
				private static final long serialVersionUID = -5022861995237472198L;
				public void actionPerformed(ActionEvent e) {
					switchPerspective(saleButton);
				}
			});
		if(reportButton != null)
			reportButton.addActionListener(new AbstractAction(){
				private static final long serialVersionUID = -5022861995237472198L;
				public void actionPerformed(ActionEvent e) {
					switchPerspective(reportButton);
				}
			});  

		//set default selected button
		homeButton.setSelected(true);		
		switchPerspective(homeButton);
		// Add GUI listener
		searchText.addKeyListener(new KeyAdapter(){
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					callSearchListener(new SearchEvent(this,
							searchText.getText().trim(), true));
			}
		});
	}// End bindEventHandlers
	void  switchPerspective(CButton selectedButton)
	{
//		DisabledGlassPane glassPane = new DisabledGlassPane();
//		JRootPane rootPane = SwingUtilities.getRootPane(GUIManager.mainFrame);
//		rootPane.setGlassPane( glassPane );
//		glassPane.activate(Language.getInstance().getString("please.wait.while.loading"));
		Set<CButton> keySet = toolBarButtonsHashtable.keySet();
		for(CButton c:keySet){			
			if(selectedButton != c){
				c.setSelected(false);
			}
		}
		switchPerspective(toolBarButtonsHashtable.get(selectedButton));
//		glassPane.deactivate();
	}
	private void switchPerspective(PerspectiveType pType)
	{
		Component c = perspectives.get(pType);
		if(getCurrentPerspective().equals(pType))return;
		removeContentPanel();
		contentPanel.add(c,BorderLayout.CENTER);
		setCurrentPerspective(pType);
		// Re-laying out child component
		contentPanel.validate();
		// repaint :S
		contentPanel.repaint();
	}
	void removeContentPanel()
	{	
		try {
			if(receivePerspective != null)
				contentPanel.remove(receivePerspective);
			if(adminPerspective != null)
				contentPanel.remove(adminPerspective);
			if(salePerspective != null)
				contentPanel.remove(salePerspective);
			if(reportPerspective != null)
				contentPanel.remove(reportPerspective);
			if(homePerspective != null)
				contentPanel.remove(homePerspective);
			if(payablePerspective != null)
				contentPanel.remove(payablePerspective);
			if(receiptablePerspective != null)
				contentPanel.remove(receiptablePerspective);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Object getData(String var)
	{
		return null;
	}
	public void setData(String var, Object data) {

	}
	protected void applyStyles() {
		mainFrame.setIconImage(Util.getImage(Library.PROGRAM_LOGO));
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		statusBar.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(null,
				CLineBorder.DRAW_TOP_BORDER),
				BorderFactory.createEmptyBorder(2, 2, 2, 2)));		
		searchBar.setBorder(BorderFactory.createCompoundBorder(
				new RoundedCornerBorder(20)/*new CLineBorder(null,
						CLineBorder.DRAW_ALL_BORDER)*/,
						BorderFactory.createEmptyBorder(1, 1, 1, 1)));	
		mainCompositeToolBar.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(null,
						CLineBorder.DRAW_RIGHT_BORDER),
						BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		menuAndSearchBar.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(null,
						CLineBorder.DRAW_BOTTOM_BORDER),
						BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		mainMenuBar.setBorder(new EmptyBorder(0,0,0,0));
	}
	public void progressStatusChanged(ProgressEvent evt) {
				
		if(evt.getProgressMessage() != null){
			progressBar.setValue(0);
			//progressBar.setStringPainted(true);
			progressBar.setIndeterminate(evt.isInfinitive());
			//progressBar.setString(evt.getProgressMessage());
			systemInfoLabel.setText(evt.getProgressMessage() + evt.isInfinitive());
		}else
		{
			//-- Phan nay dung cho cac thong diep khi khoi dong ung dung
		}
			
		
		
	}

	public void guiActionPerformed(GUIActionEvent action) {
		/*if (action.getAction() == GUIActionEvent.GUIActionType.NEW) {
			fireCAction(new CActionEvent(action.getSource(),
					CActionEvent.ADD,
					action.getData()));
		}else if (action.getAction() == GUIActionEvent.GUIActionType.SAVE) {
			fireCAction(new CActionEvent(action.getSource(),
					CActionEvent.SAVE,
					action.getData()));
		}else if (action.getAction() == GUIActionEvent.GUIActionType.DELETE) {
			fireCAction(new CActionEvent(action.getSource(),
					CActionEvent.REMOVE,
					action.getData()));
		} else if (action.getAction() == GUIActionEvent.GUIActionType.NEW) {
			fireCAction(new CActionEvent(action.getSource(),
					CActionEvent.ADD,
					action.getData()));
		}*/
	}
	public void cActionPerformed(CActionEvent action) {
		if(action.getAction() == CActionEvent.NOTIFY_ACTION){
			systemInfoLabel.setText(action.actionDesc() 
			+ " " + action.getData());
		}else{
			systemInfoLabel.setText(action.actionDesc());
		}
	}

	public PerspectiveType getCurrentPerspective() {
		return currentPerspective;
	}

	public void setCurrentPerspective(PerspectiveType currentPerspective) {
		this.currentPerspective = currentPerspective;
	}
	private void fireCAction(CActionEvent evt){
		for(CActionListener cActionListener:cListenerVector)
			cActionListener.cActionPerformed(evt);

	}
	public void addCActionListener(CActionListener cActionListener)
	{
		cListenerVector.add(cActionListener);
	}
	public void removeCActionListener(CActionListener cActionListener)
	{
		cListenerVector.remove(cActionListener);
	}

	private void callSearchListener(SearchEvent evt){
		for(SearchListener l:searchListenerVector)
			l.searchStarted(evt);

	}
	public void addSearchListener(SearchListener sl)
	{
		searchListenerVector.add(sl);
	}
	public void removeSearchListener(SearchListener sl)
	{
		searchListenerVector.remove(sl);
	}

	@Override
	public void update() {
	}
	@Override
	public void updateList() {
	}

	public void cleanup(){
		mainFrame.getContentPane().removeAll();
		mainFrame.removeAll();
		mainFrame.setVisible(false);
	}
	public void dispose(){
		mainFrame.dispose();
	}
	public void openAboutDialog(){
		AboutDialog dlg = new AboutDialog(mainFrame,true);
		dlg.centerDialogRelative(mainFrame, dlg);
		dlg.setVisible(true);
	}
	
	/** About Menu Item Action **/
	public Action aboutAction = new AbstractAction(){
		private static final long serialVersionUID = -7760726493194272123L;

		public void actionPerformed(ActionEvent e) {
			openAboutDialog();
		}
	};	
	// Menu Action - test switching language
	public Action switchLangVn = new AbstractAction(){
		private static final long serialVersionUID = 0L;

		public void actionPerformed(ActionEvent e) {
			switchLanguage("vn");
		}
	};
	// Menu Action - test switching language
	public Action switchLangCn = new AbstractAction(){
		private static final long serialVersionUID = 0L;

		public void actionPerformed(ActionEvent e) {
			switchLanguage("cn");
		}
	};
	
	public Action switchLangEn = new AbstractAction(){
		private static final long serialVersionUID = -5022861995237472198L;

		public void actionPerformed(ActionEvent e) {
			switchLanguage("en");
		}
	};
	public Action switchLangDe = new AbstractAction(){
		private static final long serialVersionUID = -6798328647251737478L;

		public void actionPerformed(ActionEvent e) {
			switchLanguage("de");
		}
	};
	
	public Action AC_PREF = new AbstractAction(){
		private static final long serialVersionUID = -6798328647251737478L;

		public void actionPerformed(ActionEvent e) {
			PreferenceGUIManager preferenceGui = new PreferenceGUIManager(Language.getInstance().getLocale());
			GenericDialog prefdlg = (GenericDialog)preferenceGui.getRootComponent();			
			prefdlg.centerDialogRelative(mainFrame, prefdlg);
			prefdlg.pack();
			prefdlg.setVisible(true);
		}
	};
	public Action ACT_OPEN_CURRENCY_DIALOG = new AbstractAction(){		
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			DialogHelper.openCurencyDialog(mainFrame);
		}
	};
	
	public Action ACT_OPEN_EXRATE_DIALOG = new AbstractAction(){		
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			DialogHelper.openExRateDialog(mainFrame);
		}
	};
	
	public Action AC_PRICE_LIST_MAITAIN = new AbstractAction(){		
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			DialogHelper.openPriceListDialog(mainFrame);
		}
	};
    public Action ACT_LOG_OUT = new AbstractAction(){		
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			fireCAction(new CActionEvent(GUIManager.this, CActionEvent.LOG_OUT, null));
		}
	};
	public Action AC_PROFILE = new AbstractAction(){		
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			// TODO change password go here
			DialogHelper.openProfileDialog(mainFrame);
		}
	};
	public Action AC_QUIT = new AbstractAction(){		
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			mainFrame.dispose();
		}
	};
}