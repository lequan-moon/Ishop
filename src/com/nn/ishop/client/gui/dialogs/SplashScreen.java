/***************************************************************************/
/* File Description  : SplashScreen                                        */
/* File Version      : 1.0                                                 */
/* Legal Copyright   : Copyright (c) 2004-2012 by nghia.n.v2007@gmail.com. */
/* Company Name      : NN.                                                 */
/* Original Filename : SplashScreen.java                                   */
/* Product Version   : 1.0                                                 */
/* Product Name      : iShop Project                                     */
/***************************************************************************/

package com.nn.ishop.client.gui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CProgressBarUI;
/**
 * <p>Title: iShop System</p>
 *  <p>Description: This class represent the SplashScreen of application</p>
 *  <p>Copyright: Copyright (c) 2004-2012</p>
 *  <p>Company: NN Ltd.,</p>
 *
 * @author Nguyen Van Nghia, nvnghia@gbitvn.com
 * @version 1.0
 */
public class SplashScreen extends JDialog  implements ISplashScreen
{
    Border border1;
    /** Progressive bar for monitoring loading process */
    JProgressBar progressBar;
    boolean complete = false;

    /**
     * SplashScreen constructor
     *
     * @param frame parent frame
     * @param title Title of dialog
     * @param modal Modal mode of dialog
     */
    public SplashScreen(Frame frame, String title, boolean modal)
    {
        super(frame, title, modal);

        try
        {
            jbInit(frame);
            pack();
        }
        catch (Exception ex)
        {
//            ConnectApplicationExceptionHandler.processException(
//            		ConnectApplicationExceptionUtil.
//            		createException(ex, "",
//                    ConnectApplicationException.INFO));
        }
    }

    /**
     * Creates a new SplashScreen object. Default constructor
     */
    public SplashScreen(Frame parent) {
        this(parent, "", false);
    }

    /**
     * initialize UI Components.
     *
     * @throws Exception An instance of type Exception
     */
    private void jbInit(Frame frame) throws Exception
    {
    	JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel label1 = new JLabel( " " );
        label1.setForeground(Color.white);
        content.add( label1 );

        JLabel label2 = new JLabel( " " );
        label1.setForeground(Color.white);
        content.add( label2 );
        JLabel label3 = new JLabel( " " );
        label1.setForeground(Color.white);
        content.add( label3 );
        JLabel label4 = new JLabel( " " );
        label1.setForeground(Color.white);
        content.add( label4 );
        JLabel label5 = new JLabel( " " );
        label1.setForeground(Color.white);
        content.add( label5 );
        JLabel label6 = new JLabel( " " );
        label1.setForeground(Color.white);
        content.add( label6 );
        JLabel label7 = new JLabel( " " );
        label1.setForeground(Color.white);
        content.add( label7 );
        JLabel label8 = new JLabel( " " );
        label1.setForeground(Color.white);
        content.add( label8 );
        progressBar = new JProgressBar();
        progressBar.setMaximum(1000);
        progressBar.setValue(0);
        progressBar.setIndeterminate(false);
        progressBar.setUI(new CProgressBarUI());
        progressBar.setMaximumSize(new Dimension(300,11));
        progressBar.setMinimumSize(new Dimension(300,11));
        progressBar.setPreferredSize(new Dimension(300,11));
        //progressBar.setStringPainted(true);

        //set transparent background color
        progressBar.setBackground(new Color(224,223,227));//new Color(255,255,255,80));
        progressBar.setForeground(new Color(127,157,185));
        progressBar.setBorder(new CLineBorder(null
        		, CLineBorder.DRAW_ALL_BORDER));
        content.add( progressBar );

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(content);

    	((JPanel)getContentPane()).setOpaque(false);
    	ImageIcon logoIcon =  new ImageIcon(com.nn.ishop.client.Main.class.getResource("img/splash2.png"));
        int winc = logoIcon.getIconWidth();
        int hinc = logoIcon.getIconHeight();

        this.setMaximumSize(new Dimension(winc+1, hinc+1));
        this.setMinimumSize(new Dimension(winc+1, hinc+1));
        this.setPreferredSize(new Dimension(winc+1, hinc+1));
        this.setTitle("");
        JLabel normalLbl = new JLabel(logoIcon);
        normalLbl.setBounds(0,0,winc,hinc);
        getLayeredPane().add(normalLbl,  new Integer(Integer.MIN_VALUE));

        // Position the dialog correctly
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((int) screen.getWidth() / 2) - ((int)
        		winc / 2),
          ((int) screen.getHeight() / 2) - ((int) hinc / 2));
        this.setUndecorated(true);
        this.setModal(false);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.pack();
    }
    public void setProgressiveValue(int acruedValue)
    {
    	this.progressBar.setValue(this.progressBar.getValue()+acruedValue);
    }

	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		SplashScreen d
		= new SplashScreen(null);
		d.validate();
		d.setVisible(true);
		try
		{
			for(int i=0;i<1000;i++){
				d.setProgressiveValue(i);
	        	Thread.currentThread().sleep(100);
        }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		while (true){
			if (!d.isVisible()){
				f.dispose();
				break;
			}
		}
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public void setMaxProgressBarValues(int maxValues){
		this.progressBar.setMaximum(maxValues);
	}
	public int getMaxProgressBarValues(){
		return this.progressBar.getMaximum();
	}
	public String getSeletedProject(){
		return null;
	}
	public boolean isStartLoading(){
		return false;
	}
	public void setStartLoading(boolean startLoading){

	}
}
