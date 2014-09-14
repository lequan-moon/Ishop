package com.nn.ishop.client.gui.dialogs;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.util.Util;

public class CNavigatorPanel extends JPanel {

	/**
	 * Generated UID serial version number
	 */
	private static final long serialVersionUID = -7503507012397690336L;
	private ImageIcon normalBtnIcon = null;
    public ImageIcon activeBtnIcon = null;
    
	public CNavigatorPanel() {
		setBorder(new CLineBorder(new Color(171,171,175),3));
        setLayout(new FlowLayout(FlowLayout.RIGHT,5,5));
//        setBackground(CConstants.BG_COLOR);    
		this.normalBtnIcon = Util.getIcon(CConstants.NORMAL_BUTTON_ICON);
	    this.activeBtnIcon = Util.getIcon(CConstants.ACTIVE_BUTTON_ICON);		
	}
	
	public CDialogsLabelButton addButton(CDialogsLabelButton button){
		add(button);
		return button;
	}
	public boolean setButtons(CDialogsLabelButton[] buttons){
		for(int i=0; i< buttons.length;i++)
		{
			add(buttons[i]);	
		}		
		return true;
	}
	public CDialogsLabelButton addLoginButton(){
		CDialogsLabelButton button = new CDialogsLabelButton("Login"/*
				, normalBtnIcon, activeBtnIcon*/);
		add(button);
		return button;
	}
	public CDialogsLabelButton addCancelButton(){
		CDialogsLabelButton button = new CDialogsLabelButton("Cancel"/*
				, normalBtnIcon, activeBtnIcon*/);
		add(button);
		return button;
	}
	public CDialogsLabelButton addOkButton(){
		CDialogsLabelButton button = new CDialogsLabelButton("Ok"/*
				, normalBtnIcon, activeBtnIcon*/);
		add(button);
		return button;
	}
	public CDialogsLabelButton addUpdateLicenseButton(){
		CDialogsLabelButton button = new CDialogsLabelButton("Update"/*
				, normalBtnIcon, activeBtnIcon*/);
		add(button);
		return button;
	}
	public CDialogsLabelButton addApplyButton(){
		CDialogsLabelButton button = new CDialogsLabelButton(
				"apply.button"/*, 
				normalBtnIcon, activeBtnIcon*/);
		add(button);
		return button;
	}
	public CDialogsLabelButton addBackButton(){
		CDialogsLabelButton button = new CDialogsLabelButton("< Back"/*, normalBtnIcon, activeBtnIcon*/);
		add(button);
		return button;
	}
	public CDialogsLabelButton addNextButton(){
		CDialogsLabelButton button = new CDialogsLabelButton("Next >"/*, normalBtnIcon, activeBtnIcon*/);
		add(button);
		return button;
	}
	public boolean addCommonWizardButtons(){
		this.removeAll();
		CDialogsLabelButton backButton 
		= new CDialogsLabelButton("< Back"/*, normalBtnIcon, activeBtnIcon*/);
		CDialogsLabelButton nextButton 
		= new CDialogsLabelButton("Next >"/*, normalBtnIcon, activeBtnIcon*/);
		CDialogsLabelButton finishButton 
		= new CDialogsLabelButton("Finish"/*, normalBtnIcon, activeBtnIcon*/);
		CDialogsLabelButton cancelButton 
		= new CDialogsLabelButton("Cancel"/*, normalBtnIcon, activeBtnIcon*/);
		add(backButton);
		add(nextButton);
		add(finishButton);
		add(cancelButton);
		this.validate();
		return true;
	}
	public boolean addCommonDialogButton(){
		this.removeAll();
		CDialogsLabelButton okButton 
		= new CDialogsLabelButton("Ok"/*, normalBtnIcon, activeBtnIcon*/);
		CDialogsLabelButton cancelButton 
		= new CDialogsLabelButton("Cancel"/*, normalBtnIcon, activeBtnIcon*/);
		CDialogsLabelButton applyButton 
		= new CDialogsLabelButton("Apply"/*, normalBtnIcon, activeBtnIcon*/);
		add(okButton);
		add(cancelButton);
		add(applyButton);
		this.validate();
		return true;
	}

}
