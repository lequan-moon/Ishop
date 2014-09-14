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
import com.nn.ishop.server.dto.product.ProductCategory;

public class ProductCategoryDialog  extends CWizardDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Product category instance */
	ProductCategory productCat;
	
	/** Parent category id */
	int parentCatId = -1;
	
	/** Category group Id */
	int catGroupId = -1;
	boolean isShowAllInfor = false;
	
	/** TRUE if user click cancel*/
	boolean isCancel = false;
	
	public ProductCategoryDialog(JFrame frame, boolean modal,
			String dialogMessage, String header1, String header2,
			ProductCategory productCat, int parentCatId, int catGroupId,
			boolean isShowAllInfor) {
		super(frame, modal, Language.getInstance().getString("quan.ly.hang.muc"), header1, header2);
		setIconImage(Util.getImage("logo32.png"));
		this.productCat = productCat;
		this.parentCatId = parentCatId;
		this.catGroupId = catGroupId;
		this.isShowAllInfor = isShowAllInfor;
		if(productCat != null){
			txtCatName.setText(productCat.getCategoryName());
			txtCatDesc.setText(productCat.getCategoryNote());
		}
		txtCatGroupId.setText(String.valueOf(catGroupId));
		txtParentCatId.setText(String.valueOf(parentCatId));
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		createDialogArea();		
		//updateNorthPanel(new JPanel());
		this.setPreferredSize(new Dimension(600, 400));        
        pack();
        this.setResizable(false);        
        this.setAlwaysOnTop(true);
        centerDialog(this);
        setFocusable(true);
        txtCatName.requestFocus();
        addWindowListener(new WindowAdapter() {        	
        	@Override
        	public void windowClosing(WindowEvent e) {
        		windowClosedHandler();
        		super.windowClosing(e);
        	}
		});
	}
	CLabel lblCatName = new CLabel(Language.getInstance().getString("category.name"));
	CTextField txtCatName = new CTextField();
	
	CLabel lblCatDesc = new CLabel(Language.getInstance().getString("category.desc"));	
	CTextField txtCatDesc = new CTextField();
	
	CLabel lblCatGroupId = new CLabel(Language.getInstance().getString("group.id"));
	CTextField txtCatGroupId = new CTextField();
	
	CLabel lblParentCatId = new CLabel(Language.getInstance().getString("parent.category"));
	CTextField txtParentCatId = new CTextField();
	
	
	JLabel lblError = new JLabel("");
	protected void windowClosedHandler(){
		productCat = null;
		isCancel = true;
	}
	protected void createDialogArea(){
		JPanel newPnlCenter = new JPanel(new GridBagLayout());
        newPnlCenter.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,10);
        
        //== Name
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(lblCatName, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 2; gbc.gridheight = 1;
        newPnlCenter.add(txtCatName, gbc);
        
        //== Desc.
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(lblCatDesc, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(txtCatDesc, gbc);
        
        //=== Cat Group 
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(lblCatGroupId, gbc);        
        
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(txtCatGroupId, gbc);
        txtCatGroupId.setEditable(isShowAllInfor);
        
        //=== Parent Id
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(lblParentCatId, gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        newPnlCenter.add(txtParentCatId, gbc);
        txtParentCatId.setEditable(isShowAllInfor);
        
        //== Error message
        gbc.gridx = 0;gbc.gridy = 4;
        gbc.gridwidth = 3;gbc.gridheight = 1;        
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
		productCat = null;
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
	public ProductCategory getReturnValue(){
		if(productCat == null && ! isCancel)
		{
			productCat = new ProductCategory(
					txtCatName.getText(), 
					parentCatId, txtCatDesc.getText(), catGroupId);
		}else if(productCat != null)
		{
			//- update
			productCat.setCategoryName(txtCatName.getText());
			productCat.setCategoryNote(txtCatDesc.getText());
		}
		return productCat;
	}
}
