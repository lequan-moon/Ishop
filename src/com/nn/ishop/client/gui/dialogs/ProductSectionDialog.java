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
import com.nn.ishop.server.dto.product.ProductSection;

public class ProductSectionDialog extends CWizardDialog {
	CLabel lblSectionName = new CLabel(Language.getInstance().getString("item.section.name"));
	CTextField txtSectionName = new CTextField();
	CLabel lblSectionDesc = new CLabel(Language.getInstance().getString("category.desc"));
	CTextField txtSectionDesc = new CTextField();
	CLabel lblError = new CLabel("");
	ProductSection pSection;
	boolean isCancel;

	// ins_user_id, ins_time, upd_user_id, upd_time, usage_flg,
	public ProductSectionDialog(JFrame frame, boolean modal, String dialogMessage, String header1, String header2, ProductSection pSection) {
		super(frame, modal, Language.getInstance().getString("quan.ly.linh.vuc"), header1, header2);
		setIconImage(Util.getImage("logo32.png"));
		this.pSection = pSection;
		if (pSection != null) {
			txtSectionName.setText(pSection.getSectionName());
			txtSectionDesc.setText(pSection.getSectionDesc());

		}
		// -- build the form
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		createDialogArea();
		handleEvent();
		//updateNorthPanel(new JPanel());
		this.setPreferredSize(new Dimension(300, 200));
		pack();
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		centerDialog(this);
		setFocusable(true);
		txtSectionName.requestFocus();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closingWindowHandler();
				super.windowClosing(e);
			}
		});
	}

	protected void closingWindowHandler() {
		pSection = null;
		isCancel = true;
	}

	private void handleEvent() {

	}

	protected void createDialogArea() {
		JPanel newPnlCenter = new JPanel(new GridBagLayout());
		newPnlCenter.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 10);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(lblSectionName, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.REMAINDER;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		newPnlCenter.add(txtSectionName, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(lblSectionDesc, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.REMAINDER;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(txtSectionDesc, gbc);

		// Row 3 error message
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;

		newPnlCenter.add(lblError, gbc);
		lblError.setForeground(Color.RED);
		newPnlCenter.setBorder(new CLineBorder(new Color(171, 171, 175), CLineBorder.DRAW_BOTTOM_BORDER));
		updateCenterPanel(newPnlCenter);

		CNavigatorPanel nav = new CNavigatorPanel();
		nav.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		nav.setBorder(null);
		CDialogsLabelButton saveButton = new CDialogsLabelButton(Language.getInstance().getString("buttonSave")/*,
				Util.getIcon("dialog/btn_normal.png"), Util.getIcon("dialog/btn_over.png")*/);
		saveButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		CDialogsLabelButton cancelButton = new CDialogsLabelButton(Language.getInstance().getString("buttonCancel")/*,
				Util.getIcon("dialog/btn_normal.png"), Util.getIcon("dialog/btn_over.png")*/);
		cancelButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				// set null to avoid add new or update
				doCancelAction();
				dispose();
			}
		});
		nav.addButton(saveButton);
		nav.addButton(cancelButton);
		nav.setBackground(Color.WHITE);
		updateNavigatorPanel(nav);
	}

	@Override
	public void doOkAction() {

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doCancelAction() {
		pSection = null;
		isCancel = true;
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

	public ProductSection getReturnValue() {
		if (pSection == null && !isCancel) {
			// -- add new
			pSection = new ProductSection(txtSectionName.getText(), txtSectionDesc.getText());
		} else if (pSection != null) {
			// - update
			pSection.setSectionName(txtSectionName.getText());
			pSection.setSectionDesc(txtSectionDesc.getText());
		}
		return pSection;
	}

}
