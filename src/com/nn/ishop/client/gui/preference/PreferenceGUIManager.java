package com.nn.ishop.client.gui.preference;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.Util;

public class PreferenceGUIManager extends AbstractGUIManager implements
	GUIActionListener, TableModelListener, ListSelectionListener{
	/*
	 * GUIAction listener vector
	 */
	private Vector<GUIActionListener> guiListenerVector = new Vector<GUIActionListener>();
	GeneralManager generalManager;
	ImportExportManager importExportManager;
	PrintManager printManager;
	ServerConnectionManager serverConnectionManager;
	SystemManager systemManager;
	ViewManager viewManager;
	
	CWhitePanel prefGeneralContainer;
	CWhitePanel prefSystemContainer;
	CWhitePanel prefServerConnectionContainer;
	CWhitePanel prefPrintContainer;
	CWhitePanel prefImportExportContainer;
	CWhitePanel prefViewContainer;
	
	CTabbedPane prefMasterTabbedPane;
	JLabel headerTitle;
	JLabel headerLogo;
	CWhitePanel prefMasterContainer;
	CWhitePanel prefCommandContainer;
	GenericDialog prefdialog;
	void init() {
		initTemplate(this, "preference/masterPage.xml");
		render();
		generalManager = new GeneralManager(getLocale());
		importExportManager = new ImportExportManager(getLocale());
		printManager = new PrintManager(getLocale());
		serverConnectionManager = new ServerConnectionManager(getLocale());
		systemManager = new SystemManager(getLocale());
		viewManager = new ViewManager(getLocale());

		Container cont = generalManager.getRootComponent();
		prefGeneralContainer.add(cont);
		
		prefSystemContainer.add(systemManager.getRootComponent());		
		prefServerConnectionContainer.add(serverConnectionManager.getRootComponent());
		prefPrintContainer.add(printManager.getRootComponent());
		prefImportExportContainer.add(importExportManager.getRootComponent());
		prefViewContainer.add(viewManager.getRootComponent());
		addGUIActionListener(generalManager);
		addGUIActionListener(importExportManager);
		addGUIActionListener(printManager);
		addGUIActionListener(serverConnectionManager);
		addGUIActionListener(systemManager);
		addGUIActionListener(viewManager);
		
		bindEventHandlers();		
	}
	public PreferenceGUIManager(String locale){
		setLocale(locale);
		init();
	}
	@Override
	protected void applyStyles() {
		prefMasterTabbedPane.setTitleAt(0, Language.getInstance().getString("pref.general"));
		prefMasterTabbedPane.setTitleAt(1, Language.getInstance().getString("pref.system"));
		prefMasterTabbedPane.setTitleAt(2, Language.getInstance().getString("pref.connection"));
		prefMasterTabbedPane.setTitleAt(3, Language.getInstance().getString("pref.print"));
		prefMasterTabbedPane.setTitleAt(4, Language.getInstance().getString("pref.import.export"));
		prefMasterTabbedPane.setTitleAt(5, Language.getInstance().getString("pref.view"));
		
		headerLogo.setIcon(Util.getIcon("dialog/page_title.png"));
		headerTitle.setFont(Library.HEADER_TITLE_FONT);
		
		prefMasterContainer.setBorder(new CLineBorder(Library.UI_SIC_GRAY,CLineBorder.DRAW_ALL_BORDER));
		prefCommandContainer.setBorder(new CLineBorder(Library.UI_SIC_GRAY,CLineBorder.DRAW_ALL_BORDER));
		
	}

	@Override
	protected void bindEventHandlers() {

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

	public void guiActionPerformed(GUIActionEvent action) {
		if(action.getAction().equals(GUIActionType.SAVE)){
			for(GUIActionListener al:guiListenerVector)
				al.guiActionPerformed(action);
			SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("save.success") 
					, SystemMessageDialog.SHOW_OK_OPTION);
		}
		/**
		 * Print properties file
		 */
		if(action.getAction().equals(GUIActionType.CANCEL))
			prefdialog.dispose();
	}

	public void tableChanged(TableModelEvent e) {
		
	}

	public void valueChanged(ListSelectionEvent e) {
		
	}
	
	public void addGUIActionListener(GUIActionListener guiActionListioner) {
		guiListenerVector.add(guiActionListioner);
	}

	public void removeGUIActionListener(GUIActionListener guiActionListioner) {
		guiListenerVector.remove(guiActionListioner);
	}
	
	/**
	 * Save properties file
	 */
	public Action CMD_SAVE = new AbstractAction() {
		private static final long serialVersionUID = 3525690917821123280L;

		public void actionPerformed(ActionEvent e) {
			guiActionPerformed(new GUIActionEvent(this, GUIActionType.SAVE, null));
			
		}
	};
	/**
	 * Print the properties file
	 */
	public Action CMD_PRINT = new AbstractAction() {
		private static final long serialVersionUID = 3525690917821123280L;

		public void actionPerformed(ActionEvent e) {
			
		}
	};
	public Action CMD_EXIT = new AbstractAction() {
		private static final long serialVersionUID = -5616632449461372260L;

		public void actionPerformed(ActionEvent e) {
			guiActionPerformed(new GUIActionEvent(this, GUIActionType.CANCEL, null));
			
		}
	};
}
