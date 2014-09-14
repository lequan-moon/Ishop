package com.nn.ishop.client.gui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;

import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.util.CredentialSetter;


public class LoginDialog extends CWizardDialog
{

	/**
	 *  Serial version UID
	 */
	private static final long serialVersionUID = -5433452306453838358L;

    JLabel unLabel = new JLabel();
    CTextField userNameField;
    JLabel pwLabel = new JLabel();
    JPasswordField passwordField;
    
    CredentialSetter setter;
    
    private JLabel lblError = new JLabel("");
        
    /*private UsernamePasswordHandler loginHandler;    
    private LoginContext lc;*/

    /**
     * Creates a new LoginDialog object with an object of CredentialSetter
     *
     * @param setter An instance of type CredentialSetter
     */
    public LoginDialog(JFrame parent,CredentialSetter setter) {
        this(parent,  true, setter);
    }
   
	public LoginDialog(JFrame parentFrame
			, boolean modal
			, CredentialSetter setter
			){
		super(parentFrame, modal, Language.getInstance().getString("login.title"), "","");
		this.setter = setter;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
		createDialogArea(); 
		CHeaderPanel newPnlNorth = new CHeaderPanel(new JLabel()
		,new JLabel(),"dialog/login_header.png");
		
		super.updateNorthPanel(newPnlNorth);
		
		// Center the dialog
		
        handleEvent();
        this.setPreferredSize(new Dimension(400, 250));        
        pack();
		this.setModal(true);
        this.setResizable(false);        
        this.setAlwaysOnTop(true);
        centerDialog(this);
        setFocusable(true);
        setFocus();
        userNameField.requestFocus();
        
        //this.setIconImage(Util.getImage(Library.PROGRAM_LOGO));
	}
	
	/**
	 * Builds controls in the dialog
	 */
	protected void createDialogArea(){
    	    	
        JPanel newPnlCenter = new JPanel(new GridBagLayout());
        newPnlCenter.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,10);
        
        // ROW 1 - user id
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        
        unLabel.setText(Language.getInstance().getString("login.id"));
        newPnlCenter.add(unLabel, gbc);
        
        // password field
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.REMAINDER;
        
        
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        
        userNameField = new CTextField();
        Dimension d = userNameField.getPreferredSize();
        d.width = 120;
        d.height = 20;
        userNameField.setText("");
        userNameField.setOpaque(true);
        userNameField.setPreferredSize(d);
        userNameField.setMinimumSize(d);
        userNameField.setMaximumSize(d);
        
        newPnlCenter.add(userNameField, gbc);
        
        // ROW 2 - password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;

        pwLabel.setText(Language.getInstance().getString("login.password"));
        newPnlCenter.add(pwLabel, gbc);
        
        gbc.gridwidth = 2;
        gbc.gridheight = 1;

        gbc.gridx = 1;
        gbc.gridy = 1;

        passwordField = new JPasswordField();
        passwordField.setOpaque(true);
        passwordField.setPreferredSize(d);
        passwordField.setMinimumSize(d);
        passwordField.setMaximumSize(d);
        passwordField.setBorder(new RoundedCornerBorder(12));

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.REMAINDER;
        
        newPnlCenter.add(passwordField, gbc);
        
        // Row 3 error message
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        
        newPnlCenter.add(lblError, gbc);
        lblError.setForeground(Color.RED);
        newPnlCenter.setBorder(new CLineBorder(new Color(171,171,175), CLineBorder.DRAW_BOTTOM_BORDER));
		super.updateCenterPanel(newPnlCenter);
		
		CNavigatorPanel nav = new CNavigatorPanel();
		nav.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		nav.setBorder(null);
		CDialogsLabelButton loginButton
		= new CDialogsLabelButton(Language.getInstance().getString("login.button"));
		loginButton.addActionListener(new AbstractAction(){
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				doOkAction();				
			}
		});
		CDialogsLabelButton cancelButton
		= new CDialogsLabelButton(Language.getInstance().getString("cancel.button"));
		cancelButton.addActionListener(new AbstractAction(){
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				doCancelAction();				
			}
		});
		nav.addButton(loginButton);nav.addButton(cancelButton);
		nav.setBackground(Color.WHITE);
		super.updateNavigatorPanel(nav);
	}
	public void setFocus()
	{
		if(userNameField.getText() != null){        	
			this.setFocusable(true);
        	passwordField.setFocusable(true);
        	passwordField.requestFocus(true);
        }else{
        	this.setFocusable(true);
        	userNameField.setFocusable(true);
        	userNameField.requestFocus(true);
        }
	}
    /**
     * set boolean value for this form to show message error
     *
     * @return boolean Boolean value indicates validate or not 
     */    
	private boolean validateData() {
		char[] cPasswd = passwordField.getPassword();
		String sPasswd = "";
		String sUsername = userNameField.getText().trim();
		for (int i = 0; i < cPasswd.length; i++) {
			sPasswd = String.valueOf(cPasswd);
		}
		if (sUsername.equalsIgnoreCase("")) {
			lblError.setText(Language.getInstance().getString(
					"login.invalid.password"));
			userNameField.requestFocus(true);
			return false;
		}

		if (sPasswd.trim().equalsIgnoreCase("")) {
			lblError.setText(Language.getInstance().getString("login.invalid"));
			passwordField.requestFocus(true);
			return false;
		}
		return true;

	}


        
    /**
     * event handler for ActionListeners. This method is called when
     * there is an ActionEvent happens on buttons.
     */
    private void handleEvent()
    {
    	passwordField.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent ae)
    		{
    			if(validateData() == false)
    			{
    				setVisible();
    				setFocus();
    				return;
    			}
    			else
    			{
    				login();
    				setInvisible();
    			}
    		}
    	});
    	
    	userNameField.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent ae)
    		{
    			if(validateData() == false)
    			{
    				setVisible();
    				setFocus();
    				return;
    			}
    			else
    			{
    				login();
    				setInvisible();
    			}
    		}
    	});
    }
       
    private void handleCancelButton()
    {
    	setter.setOkPressed(false);
    	this.setVisible(false);
    }
    
    private void login()
    {
    	char[] cPasswd = passwordField.getPassword();
    	
        String sPasswd = "";

        for (int i = 0; i < cPasswd.length; i++) 
        {
            sPasswd = String.valueOf(cPasswd);
        }
        
        setter.setPassword(sPasswd);
        setter.setUsername(userNameField.getText());
        setter.setOkPressed(true);  
    } 
	
	public void doCancelAction() {	
		handleCancelButton();
	}

	public boolean isValidData() {
		return false;
	}

	public void panelActive() {
		
	}

	public void panelDeactive() {
		
	}
	
	public void doBackAction() {
		
	}

	public void doFinishAction() {
		
	}

	public void doNextAction() {
		
	}

	public boolean saveSettings() {
		return false;
	}
   private void setVisible()
    {
    	this.setVisible(true);
    }
    
    private void setInvisible()
    {
    	this.setVisible(false);
    }

	public void doOkAction() {
		if(validateData() == false)
		{
			setVisible();
			setFocus();
			return;
		}
		else
		{
			login();
			setInvisible();
		}
	}

	public void performFinish() {
		
	}

	public void setLblError(String lblError) {
		this.lblError.setText(lblError);
	}
}