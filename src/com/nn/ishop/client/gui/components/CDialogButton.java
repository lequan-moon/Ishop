package com.nn.ishop.client.gui.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;



/**
 * <p>Title: Connect Stand alone Client</p>
 *  <p>Description: This button will be highlight on mouse enter and out</p>
 *  <p>Copyright: Copyright (c) 2004</p>
 *  <p>Company: shiftTHINK Ltd.</p>
 *
 * @author Nguyen Van Nghia
 * @version 1.0
 */
public class CDialogButton extends JButton 
{    
	//private String normalIconPath = "";
	
	//private String activeIconPath = "";
    private ImageIcon normalIcon = null;
    private ImageIcon activeIcon = null;

	private static final long serialVersionUID = -1563526140429704735L;


    public CDialogButton(String text
            , ImageIcon normalIcon
            , ImageIcon activeIcon
            ) 
    {
        super(text);
        //commonSetting();
        this.setIcon(normalIcon);
        this.normalIcon = normalIcon;
        this.activeIcon = activeIcon;
        this.setIconTextGap(5);
        this.setHorizontalTextPosition(JLabel.CENTER);
        addMouseListener(new MouseHandler());
        this.validate();
    }  

//    void commonSetting(){
//		setHorizontalTextPosition(JButton.CENTER);
//		setBorderPainted(false);
//		setFocusPainted(false);
//		setBorder(Library.TIGHT_BORDER);
//	}
    
    
   /**
     * The mouse handler which makes the highlighting.
     * 
     * @author nvnghia
     */
    private class MouseHandler extends MouseAdapter 
    {

        /**
         * When the mouse passes over the button.
         * 
         * @param e The event.
         */
        public void mouseEntered(MouseEvent e)
        {
        	if (isEnabled()){         		
        	    setIcon(activeIcon);
        	}        	
        }

        /**
         * When the mouse passes out of the button.
         * 
         * @param e The event.
         */
        public void mouseExited(MouseEvent e)
        {
        	if (isEnabled()){        	    
        		setIcon(normalIcon);
        	}
        }
    }	
    public ImageIcon getIcon(String rs) {
        return new ImageIcon(CDialogButton.class.getResource(rs));
    }
    
    public ImageIcon getNormalIcon()
    {
        return normalIcon;
    }
    
    public void setNormalIcon(ImageIcon normalIcon)
    {
        this.normalIcon = normalIcon;
    }
    
    public ImageIcon getActiveIcon()
    {
        return activeIcon;
    }
    
    public void setActiveIcon(ImageIcon activeIcon)
    {
        this.activeIcon = activeIcon;
    }
}