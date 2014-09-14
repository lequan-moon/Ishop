package com.nn.ishop.client.gui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLabel;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.product.ProductCategoryGroup;

public class ProductCategoryGroupDialog  extends CWizardDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ProductCategoryGroup catGroup;
	int sectionId = -1;
	boolean isShowSectionId = false;
	boolean isCancel;
	
	CLabel lblCatGroupName = new CLabel(Language.getInstance().getString("group.name"));
	CTextField txtCatGroupName = new CTextField();
	
	CLabel lblCatGroupDesc = new CLabel(Language.getInstance().getString("group.desc"));	
	CTextField txtCatGroupDesc = new CTextField();
	
	CLabel lblSectionId = new CLabel(Language.getInstance().getString("item.section.id"));
	CTextField txtSectionId = new CTextField();
	
	JLabel lblError = new JLabel("");
	
	public ProductCategoryGroupDialog(JFrame frame, boolean modal,
			String dialogMessage, String header1, String header2,
			ProductCategoryGroup catGroup, int sectionId,
			boolean isShowSectionId) {
		super(frame, modal, Language.getInstance().getString("quan.ly.nhom.hang.muc"), header1, header2);
		setIconImage(Util.getImage("logo32.png"));
		this.catGroup = catGroup;
		this.sectionId = sectionId;
		txtSectionId.setText(String.valueOf(sectionId));
		this.isShowSectionId = isShowSectionId;
		
		if(catGroup != null){
			txtCatGroupName.setText(catGroup.getGroupName());
			txtCatGroupDesc.setText(catGroup.getGroupDesc());			
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		createDialogArea();		
		//updateNorthPanel(new JPanel());
		this.setPreferredSize(new Dimension(300, 200));        
        pack();
        this.setResizable(false);        
        this.setAlwaysOnTop(true);
        centerDialog(this);
        setFocusable(true);
        txtCatGroupName.requestFocus();
        addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		closingWindowHandler();
        		super.windowClosing(e);
        	}
		});
	}
	protected void closingWindowHandler(){
		doCancelAction();		
	}

	protected void createDialogArea(){
		JPanel newPnlCenter = new JPanel(new GridBagLayout());
        newPnlCenter.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(lblCatGroupName, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 2; gbc.gridheight = 1;
        newPnlCenter.add(txtCatGroupName, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(lblCatGroupDesc, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(txtCatGroupDesc, gbc);
        
        //== Section ID
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(lblSectionId, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(txtSectionId, gbc);
        txtSectionId.setEditable(isShowSectionId);
        
     // Row 3 error message
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        
        newPnlCenter.add(lblError, gbc);
        lblError.setForeground(Color.RED);
        newPnlCenter.setBorder(new CLineBorder(new Color(171,171,175), 
        		CLineBorder.DRAW_BOTTOM_BORDER));
		updateCenterPanel(newPnlCenter);
		
		CNavigatorPanel nav = new CNavigatorPanel();
		nav.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		nav.setBorder(null);
		CDialogsLabelButton saveButton
		= new CDialogsLabelButton(Language.getInstance().getString("buttonSave")/*
				,Util.getIcon("dialog/btn_normal.png")
				,Util.getIcon("dialog/btn_over.png")*/
		);
		saveButton.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {	
				setVisible(false);
			}
		});
		CDialogsLabelButton cancelButton
		= new CDialogsLabelButton(Language.getInstance().getString("buttonCancel")/*
				,Util.getIcon("dialog/btn_normal.png")
				,Util.getIcon("dialog/btn_over.png")*/
		);
		cancelButton.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				// set null to avoid add new or update
				doCancelAction() ;
				dispose();
			}
		});
		nav.addButton(saveButton);nav.addButton(cancelButton);
		nav.setBackground(Color.WHITE);
		updateNavigatorPanel(nav);	
	}
	
	
	
	@Override
	public void doCancelAction() {
		isCancel = true;
		catGroup = null;
		dispose();
		
	}

	@Override
	public boolean isValidData() {
		return false;
	}

	@Override
	public void panelActive() {
		
	}

	@Override
	public void panelDeactive() {
		
	}

	@Override
	public void performFinish() {
		
	}
	public ProductCategoryGroup getReturnValue(){
		if(catGroup == null && ! isCancel)
		{
			//-- add new
			catGroup = new ProductCategoryGroup(txtCatGroupName.getText(), txtCatGroupDesc.getText(),sectionId);			
		}else if(catGroup != null)
		{
			//- update
			catGroup.setGroupName(txtCatGroupName.getText());
			catGroup.setGroupDesc(txtCatGroupDesc.getText());
		}
		return catGroup;
	}
}
