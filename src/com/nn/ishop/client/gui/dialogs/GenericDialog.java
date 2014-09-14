package com.nn.ishop.client.gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.nn.ishop.client.Main;


/**
 * @author NguyenNghia
 *
 */
public class GenericDialog extends CWizardDialog {
	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 3763207923899392994L;
	static Logger logger = Logger.getLogger(Main.class.getName());

	public GenericDialog(){
		ActionListener escListener = new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	GenericDialog.this.dispose();
	        }
	    };

	    GenericDialog.this.getRootPane().registerKeyboardAction(escListener,
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
	            JComponent.WHEN_IN_FOCUSED_WINDOW);	
		this.setResizable(true);		
	}

	public void panelDeactive() {
	}

	public void panelActive() {
	}

	@Override
	public void doCancelAction() {
		
	}

	@Override
	public boolean isValidData() {
		return false;
	}

	@Override
	public void performFinish() {
		
	}
	
}