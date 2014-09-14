package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.nn.ishop.client.util.Util;


public class CTabbedPane3 extends JTabbedPane implements MouseListener {
	private static final long serialVersionUID = 2912304658050193728L;
	private JPopupMenu popupMenu;
	protected Vector<ActionListener> vctActionListeners;
	private Integer selectedTabIndex;	
	private boolean closeAble = false;
	private CTabbedPaneUI UIDelegate;
    
    
    public static final String CLOSE = "close tab";
    public static final String CLOSE_OTHER = "close other tab";
    public static final String CLOSE_ALL = "close all tab";
    
    public static final String REFRESH = "refresh";
    public static final String REFRESH_ALL = "refresh all";
    
    public static final String EXPORT = "export";
    public static final String EXPORT_ALL = "export all";
    
    public static final String PROJECT_TABBED ="project_tabbed";
    public static final String PERSON_TABBED ="person_tabbed";
    public static final String ORG_TABBED ="org_tabbed";
    public static final String NETWORK_TABBED ="network_tabbed";
    public static final String ADMIN_TABBED ="admin_tabbed";
    
    private String tabbedType = null;
    public CTabbedPane3()
    {
    	this.tabbedType = PROJECT_TABBED;
    	this.closeAble = false;
    	setBackground(Color.WHITE);
        vctActionListeners = new Vector<ActionListener>();
        UIDelegate = new CTabbedPaneUI();
        setUI(UIDelegate);
        addMouseListener(this);
    }
    /**
     * Creates a new CTabbedPane object. Default constructor
     */
    public CTabbedPane3(boolean closeAble, String tabbedType) {    	
    	this.tabbedType = tabbedType;
    	this.closeAble = closeAble;
    	setBackground(Color.WHITE);
        vctActionListeners = new Vector<ActionListener>();
        UIDelegate = new CTabbedPaneUI();
        setUI(UIDelegate);
        addMouseListener(this);
    }   
    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    protected void paintComponent(Graphics g){
        /** TODO this not an elegant way to solve a UI delegate reset for the component, 
         * pls review me - uka */ 
    	if(! (getUI() instanceof CTabbedPaneUI))setUI(UIDelegate);
    	else super.paintComponent(g);
    }
    
    private class CTabCloseAction extends AbstractAction {

        private static final long serialVersionUID = -2625928077474199856L;        
        public void actionPerformed(ActionEvent actionEvent) {
        	if(selectedTabIndex==0)return;
            removeTabAt(selectedTabIndex);
            validate();
        }

		public CTabCloseAction(String name) {
		}
    }
    
    private class CTabCloseOthersAction  extends AbstractAction{

        private static final long serialVersionUID = -2625928077474199856L;
      

        public void actionPerformed(ActionEvent actionEvent) {
            
            int tabCount = getTabCount();
            String title = getTitleAt(selectedTabIndex);
            int tabTobeRemoved = 1;
            if(selectedTabIndex == 1)
            {
            	tabTobeRemoved = 2;
            }
            while(tabCount > tabTobeRemoved)
            {     
            	if(getTitleAt(tabTobeRemoved).equals(title)){
            		if(tabCount==2)break;
            		else
            		{
            			removeTabAt(tabTobeRemoved+1);
            			tabCount = getTabCount();
            		}
            	}else{
            		removeTabAt(tabTobeRemoved);
                	tabCount = getTabCount();
            	}
            	
            }
            
        }

		public CTabCloseOthersAction (String name) {
		}
    }

    private class CTabCloseAllAction  extends AbstractAction{

        private static final long serialVersionUID = -2625928077474199856L;

        public void actionPerformed(ActionEvent actionEvent) {
            int tabCount = getTabCount();
            while (tabCount >1){
            	removeTabAt(1);
            	tabCount = getTabCount();
            }
        }

		public CTabCloseAllAction(String name) {
		}
    }
    
    /**
     * @see com.nn.ishop.helper.ui.CListOld
     * @param al
     */
    public void addActionListener(ActionListener al) {
        vctActionListeners.add(al);
    }
    /**
     * @see com.nn.ishop.helper.ui.CListOld
     * @param al
     */
    public void removeActionListener(ActionListener al) {
        vctActionListeners.remove(al);
    }
    /**
     * @see com.nn.ishop.helper.ui.CListOld
     * @param e
     */
    protected void callActionListeners(ActionEvent e) {
        for (int i = 0; i < vctActionListeners.size(); i++)
            ((ActionListener) vctActionListeners.get(i)).actionPerformed(e);
    }
   private JPopupMenu createPopupMenu() {
        
        popupMenu = new JPopupMenu();
        popupMenu.setBackground(Color.WHITE);
        JMenuItem close = new JMenuItem("Close");
        JMenuItem closeOther = new JMenuItem("Close other");
        JMenuItem closeAll = new JMenuItem("Close all");
        
        JMenuItem refresh = new JMenuItem("Refresh");
        JMenuItem refreshAll = new JMenuItem("Refresh all");
        
        JMenuItem export = new JMenuItem("Export");
        JMenuItem exportAll = new JMenuItem("Export all");
        
        close.addActionListener(new CTabCloseAction(CLOSE));
        closeOther.addActionListener(new CTabCloseOthersAction(CLOSE_OTHER));
        closeAll.addActionListener(new CTabCloseAllAction(CLOSE_ALL));
        
        refresh.setActionCommand("Refreshing");
        refresh.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 2176918619450769881L;

			public void actionPerformed(ActionEvent e) {
            	callActionListeners(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
            			REFRESH));
            }
        });
        refreshAll.setActionCommand("Refreshing All");
        refreshAll.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = -2665680113808908950L;

			public void actionPerformed(ActionEvent e) {
            	callActionListeners(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
            			REFRESH_ALL));
            }
        });
        
        export.setActionCommand("Export");
        export.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 851739031884900551L;

			public void actionPerformed(ActionEvent e) {
            	callActionListeners(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
            			EXPORT));
            }
        });
        exportAll.setActionCommand("Export All");
        exportAll.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = -5400442685244541177L;

			public void actionPerformed(ActionEvent e) {
            	callActionListeners(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
            			EXPORT_ALL));
            }
        });
        int tabCount = getTabCount();
        if(tabCount == 1){
        	close.setEnabled(false);
        	closeOther.setEnabled(false);
        	closeAll.setEnabled(false);
        	refreshAll.setEnabled(false);
        	exportAll.setEnabled(false);
        }
        if(selectedTabIndex == 0)close.setEnabled(false);
        if(closeAble){
	        popupMenu.add(close);
	        popupMenu.add(closeOther);
	        popupMenu.add(closeAll);
	        popupMenu.addSeparator();
        }
       
        popupMenu.add(refresh);
        popupMenu.add(refreshAll);
        popupMenu.addSeparator();
        popupMenu.add(export);
        popupMenu.add(exportAll);        
        return popupMenu;
    }
    /**
     * add new tab
     *
     * @param title Title of component
     * @param component An instance of type Component
     */
    public void addTab(String title, Component component) {
        this.addTab(title, component, null);
    }

    /**
     * add new tab with extra icon
     *
     * @param title Title of component
     * @param component An instance of type Component
     * @param extraIcon Extra icon of component
     */
    public void addTab(String title, Component component, Icon extraIcon) {
        super.addTab(title, (extraIcon !=null)?new CloseTabIcon(extraIcon):null, component);
    }

    /**
     * add new tab with extra icon and tool tip
     *
     * @param title Title of component
     * @param component An instance of type Component
     * @param extraIcon Extra icon of component
     * @param tip Tip of component
     */
    public void addTab(String title, Icon extraIcon, Component component, String tip) {
        super.addTab(title,(extraIcon !=null)? new CloseTabIcon(extraIcon):null, component, tip);
    }

    /**
     * insert new tab with extra icon, tool tip and index
     *
     * @param title Title of component
     * @param extraIcon Extra icon of component
     * @param component An instance of type Component
     * @param tip Tip of component
     * @param index Index of component
     */
    public void insertTab(String title, Icon extraIcon, Component component, String tip, int index) {
        super.insertTab(title, (extraIcon !=null)?new CloseTabIcon(extraIcon):null, component, tip, index);
    }

    /**
     * insert NotClosing tab with extra icon, tool tip and index
     *
     * @param title Title of component
     * @param extraIcon Extra icon of component
     * @param component An instance of type Component
     * @param tip Tip of component
     * @param index Index of component
     */
    public void insertNotClosingTab(String title, Icon extraIcon, Component component, String tip, int index) {
        super.insertTab(title, extraIcon, component, tip, index);
    }
    
    /**
     * event handler for MouseListener. This method is called when an mouse 
     * click happens
     *
     * @param e An instance of type MouseEvent
     */
    public void mouseClicked(MouseEvent e) {
    	try{
	        selectedTabIndex = getUI().tabForCoordinate(this, e.getX(), e.getY());
	        if(!closeAble)return;
	        if (selectedTabIndex < 1) {
	            return;
	        }	
	        Rectangle rect = ((CloseTabIcon) getIconAt(selectedTabIndex)).getBounds();	
	        if (rect.contains(e.getX(), e.getY())) {
	            //the tab is being closed
	            this.removeTabAt(selectedTabIndex);
	        }
    	}catch(Exception ex){
    		// no need to handle exception here since we do not use close icon any more
    		//ex.printStackTrace();
    	}
    }

    /**
     * event handler for MouseListener. This method is called when an mouse 
     * enter happens
     *
     * @param e An instance of type MouseEvent
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * event handler for MouseListener. This method is called when an mouse 
     * exit happens
     *
     * @param e An instance of type MouseEvent
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * event handler for MouseListener. This method is called when an mouse 
     * pressed happens
     *
     * @param e An instance of type MouseEvent
     */
    public void mousePressed(MouseEvent e) {
    	selectedTabIndex = getUI().tabForCoordinate(this, e.getX(), e.getY());
        if (SwingUtilities.isRightMouseButton(e)) {
            selectedTabIndex = indexAtLocation(e.getX(), e.getY());
        
            //Show when tab located at top only
            if (getTabPlacement() == JTabbedPane.TOP) {
            	createPopupMenu();
                popupMenu.show(this, e.getX(), e.getY());
            }
        }
    }

    /**
     * event handler for MouseListener. This method is called when an mouse 
     * release happens
     *
     * @param e An instance of type MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
    	selectedTabIndex = getUI().tabForCoordinate(this, e.getX(), e.getY());
        if (SwingUtilities.isRightMouseButton(e)) {
            selectedTabIndex = indexAtLocation(e.getX(), e.getY());
        
            // Only show for top row
            if (getTabPlacement() == JTabbedPane.TOP) {
            	createPopupMenu();
                popupMenu.show(this, e.getX(), e.getY());
            }
        }
    }
	public boolean isCloseAble() {
		return closeAble;
	}
	public String getTabbedType() {
		return tabbedType;
	}
	public void setTabbedType(String tabbedType) {
		this.tabbedType = tabbedType;
	}

	public void updateLanguage(String country){
	}
	public void setCloseAble(boolean closeAble) {
		this.closeAble = closeAble;
	}
}

/**
 * The class which generates the 'X' icon for the tabs. 
 * The constructor accepts an icon which is extra to the 'X'
 * icon, so you can have tabs like in JBuilder. 
 * This value is null if no extra icon is required.
 */
class CloseTabIcon extends ImageIcon {
	private static final long serialVersionUID = 0L;
	static Icon closeIcon = null;//Util.getIcon("helpers/icon_delete_small_black_8.png");
    private Icon fileIcon;
    private int x_pos;
    private int y_pos;

    /**
     * Creates a new CloseTabIcon object.
     *
     * @param fileIcon An instance of type File
     */
    public CloseTabIcon(Icon fileIcon) {    	
        this.fileIcon = fileIcon;
    }

    /**
     * paint icon
     *
     * @param c An instance of type Component
     * @param g An instance of type Graphic
     * @param x x coordinate of component
     * @param y y coordinate of component
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x_pos = x;
        this.y_pos = y;
        closeIcon.paintIcon(c, g, x, y);

        if (fileIcon != null) {
            fileIcon.paintIcon(c, g, x + closeIcon.getIconWidth() + 2, y);
        }
    }

    /**
     * get width of icon
     *
     * @return width of icon
     */
    public int getIconWidth() {
        return closeIcon.getIconWidth() + ((fileIcon != null) ? (fileIcon.getIconWidth() + 2) : 0);
    }

    /**
     * get height of icon
     *
     * @return height of icon
     */
    public int getIconHeight() {
        return closeIcon.getIconHeight();
    }

    /**
     * get bounds
     *
     * @return An instance of type Rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, closeIcon.getIconWidth(), closeIcon.getIconHeight());
    }
    
    
    
}
