package com.nn.ishop.client.gui.dialogs;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import com.nn.ishop.client.util.Util;

/**
 * The abstract class for normal wizard
 * @author nguyen.van.nghia
 *
 */
public abstract class CWizardDialog extends JDialog{
    /**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 2480584828274129377L;
	/** Header panel */
	private CHeaderPanel pnlHeader = null;
	
	/** center panel */
	private JPanel pnlCenter = new JPanel(); 
	/** Buttons panel */
    private JPanel pnlSouth = new JPanel();
    
    /** Root content panel */
    protected JPanel contentPane = new JPanel();
    
    /** User can finish or not*/
    boolean canFinish = false;
    
    /**
     * Constructor for this class
     * @param frame parent frame
     * @param modal true if it is modal dialog otherwise it will be false
     * @param dialogMessage dialog title
     * @param pnlCenter center data panel, need to build outside of 
     * this class
     */
    public CWizardDialog(JFrame frame
    		, boolean modal
    		, String dialogMessage
    		, String header1
    		, String header2) {
        super(frame, modal);
        contentPane = new JPanel(new BorderLayout());
//        contentPane.setBackground(CConstants.BG_COLOR);
        //HEADER PANEL
        pnlHeader = new CHeaderPanel(
        		new JLabel(header1)
        		, new JLabel(header2));
        
        contentPane.add(pnlHeader, BorderLayout.NORTH);
        //CENTER PANEL
        pnlCenter.setBackground(CConstants.BG_COLOR);
        contentPane.add(pnlCenter, BorderLayout.CENTER);
        //SOUTH PANEL        
        contentPane.add(pnlSouth, BorderLayout.SOUTH);
        
        getContentPane().add(contentPane);
        setTitle(dialogMessage);   
        
        // The fault preferred size
        //this.setPreferredSize(new Dimension(482, 391));
        this.setResizable(true);
//        setUndecorated(true);
//        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        // Post action when panel is active
        panelActive();        
    }
    public CWizardDialog(){
    	setIconImage(Util.getImage("logo48.png"));
//    	setAlwaysOnTop(true);
		setModal(true);
    	contentPane = new JPanel(new BorderLayout());
    	setLocation(GraphicsEnvironment
    			.getLocalGraphicsEnvironment().getCenterPoint());
//      contentPane.setBackground(CConstants.BG_COLOR);
      //HEADER PANEL
      pnlHeader = new CHeaderPanel(
      		new JLabel("")
      		, new JLabel(""));
      
      contentPane.add(pnlHeader, BorderLayout.NORTH);
      //CENTER PANEL
      pnlCenter.setBackground(CConstants.BG_COLOR);
      contentPane.add(pnlCenter, BorderLayout.CENTER);
      //SOUTH PANEL        
      contentPane.add(pnlSouth, BorderLayout.SOUTH);
      
      getContentPane().add(contentPane);
      setTitle("");   
      
      // The fault preferred size
      //this.setPreferredSize(new Dimension(482, 391));
      this.setResizable(false);
//      setUndecorated(true);
//      getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
      // Post action when panel is active
      panelActive(); 
    }
    
    /**
     * Update north panel
     * @param newPnlNorth
     */
    public void updateNorthPanel(JPanel newPnlNorth){
    	/** TODO add header panel with data here */
    	contentPane.remove(this.pnlHeader);
    	contentPane.add(newPnlNorth, BorderLayout.NORTH);
    	getContentPane().validate();
    }
    
    /**
     * Update center panel
     * @param newPnlCenter
     */
    public void updateCenterPanel(JPanel newPnlCenter){
    	/** TODO add center panel with data here */
    	contentPane.remove(this.pnlCenter);
    	contentPane.add(newPnlCenter, BorderLayout.CENTER);
    	getContentPane().validate();
    }
    /**
     * Update button bar
     * @param newPnlNavigator
     */
    public void updateNavigatorPanel(JPanel newPnlNavigator){
    	/** TODO add center panel with data here */
    	contentPane.remove(this.pnlSouth);
    	contentPane.add(newPnlNavigator, BorderLayout.SOUTH);
    	getContentPane().validate();
    }
    
	/**
     * Centers a window on screen. should use for login dialog
     * 
     * @param frame The window instance.
     */
    public void centerDialog(Window frame)
    {
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getCenterPoint();        
        Dimension frameSize = frame.getSize();
        frame.setLocation(center.x - frameSize.width / 2
        		, center.y - frameSize.height / 2 - 10);
    }    
    
	/**
     * Centers a window on screen. should use for login dialog
     * 
     * @param frame The window instance.
     */
    public void centerDialogRelative(Window parent, Window childDlg)
    {
    	if(parent == null) {
    		centerDialog(childDlg);
    		return;
    	}
    		
    	Dimension dlgSize = childDlg.getPreferredSize();
        Dimension frmSize = parent.getSize();
        Point loc = parent.getLocation();
        childDlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,20
        		/*(frmSize.height - dlgSize.height) / 2 + loc.y*/);
    }  
    
  

    /**
     * Must override for subclass
     */
    public abstract void performFinish();
    
    /**
     * Must override for subclass
     */
	public void doOkAction(){
		
	}
	
	 /**
     * Must override for subclass
     */
	public void doBackAction(){
		
	}
	 /**
     * Must override for subclass
     */
	public void doNextAction(){
		
	}
	
	 /**
     * Must override for subclass
     */
	public abstract void doCancelAction();
	 /**
     * Must override for subclass
     */
	public abstract boolean isValidData();
	 /**
     * Must override for subclass
     */
//	public ConnectLicense updateLicense(){
//		return null;
//		
//	}
	 /**
     * Must override for subclass
     */
	public boolean saveSettings(){
		return false;
	}
	 /**
     * Must override for subclass
     */
	public abstract void panelActive();
	 /**
     * Must override for subclass
     */
	public abstract void panelDeactive();
	 
}