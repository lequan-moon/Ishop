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
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLabel;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.util.Identifiable;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.product.ProductSection;
import com.toedter.calendar.JDateChooser;

public class CompanyDialog extends CWizardDialog {
	CLabel lblType = new CLabel(Language.getInstance().getString("dto.company.type"));
	CComboBox cbbCompanyType = new CComboBox();
	CLabel lblCompanyName = new CLabel(Language.getInstance().getString("dto.company.name"));
	CTextField txtCompanyName = new CTextField();
	CLabel lblCountry = new CLabel(Language.getInstance().getString("dto.company.country"));
	CComboBox cbbCountry = new CComboBox();
	CLabel lblAddress = new CLabel(Language.getInstance().getString("dto.company.address"));
	CTextField txtAddress = new CTextField();
	CLabel lbltelephone = new CLabel(Language.getInstance().getString("dto.company.telephone"));
	CTextField txtTelephone = new CTextField();
	CLabel lblEstablishedDate = new CLabel(Language.getInstance().getString("dto.company.establishedDate"));
	JDateChooser dcEstablishedDate = new JDateChooser();
	CLabel lblContactPerson = new CLabel(Language.getInstance().getString("dto.company.contactPerson"));
	CTextField txtContactPerson = new CTextField();
	CLabel lblError = new CLabel("");
	Company company;
	boolean isCancel;

	public CompanyDialog(JFrame frame, boolean modal, String dialogMessage, String header1, String header2, Company company) {
		super(frame, modal, Language.getInstance().getString("quanly.congty"), header1, header2);
		setIconImage(Util.getImage("logo32.png"));
		// -- build the form
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		createDialogArea();
		handleEvent();
		updateNorthPanel(new JPanel());
		//this.setPreferredSize(new Dimension(300, 250));
		pack();
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		centerDialog(this);
		setFocusable(true);
		this.company = company;
		if (company != null) {
			txtCompanyName.setText(company.getName());
			cbbCountry.setSelectedItemById(String.valueOf(company.getCountry_id()));
			txtAddress.setText(company.getAddress());
			txtTelephone.setText(company.getTelephone());
			dcEstablishedDate.setDate(company.getEstablished_date());
			txtContactPerson.setText(company.getContact_person());
			cbbCompanyType.setSelectedItemById(String.valueOf(company.getType()));
		}
		txtCompanyName.requestFocus();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closingWindowHandler();
				super.windowClosing(e);
			}
		});
	}

	protected void createDialogArea() {
		JPanel newPnlCenter = new JPanel(new GridBagLayout());
		newPnlCenter.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 10);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(lblCompanyName, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		newPnlCenter.add(txtCompanyName, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(lblAddress, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(txtAddress, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(lblCountry, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		ItemWrapper country0 = new ItemWrapper("-1", "-select a country-");
		ItemWrapper country1 = new ItemWrapper("1", "Vietnam");
		ItemWrapper country2 = new ItemWrapper("2", "England");
		ItemWrapper country3 = new ItemWrapper("3", "Japan");
		cbbCountry.setItems(new Identifiable[]{country0, country1, country2, country3});
		newPnlCenter.add(cbbCountry, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(lblContactPerson, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(txtContactPerson, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(lblEstablishedDate, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		newPnlCenter.add(dcEstablishedDate, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(lbltelephone, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(txtTelephone, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		newPnlCenter.add(lblType, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		ItemWrapper item0 = new ItemWrapper("-1", "-select a type-");
		ItemWrapper item1 = new ItemWrapper("1", "Company");
		ItemWrapper item2 = new ItemWrapper("2", "Department");
		ItemWrapper item3 = new ItemWrapper("3", "Group");
		cbbCompanyType.setItems(new Identifiable[] { item0, item1, item2, item3 });
		newPnlCenter.add(cbbCompanyType, gbc);
		// Row 3 error message
		gbc.gridx = 0;
		gbc.gridy = 7;
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

	public Company getReturnValue() {
		if (company == null && !isCancel) {
			// -- add new
			company = new Company(0, Integer.valueOf(cbbCountry.getSelectedItemId()), txtCompanyName.getText(), txtAddress.getText(), txtTelephone.getText(),
					dcEstablishedDate.getDate() != null ? dcEstablishedDate.getDate() : new Date(), 1, txtContactPerson.getText(),
					Integer.valueOf(cbbCompanyType.getSelectedItemId()));
		} else if (company != null) {
			// - update
			company.setName(txtCompanyName.getText());
			company.setAddress(txtAddress.getText());
			company.setCountry_id(Integer.valueOf(cbbCountry.getSelectedItemId()));
			company.setContact_person(txtContactPerson.getText());
			company.setTelephone(txtTelephone.getText());
			company.setEstablished_date(dcEstablishedDate.getDate() != null ? dcEstablishedDate.getDate() : new Date());
			company.setType(Integer.valueOf(cbbCompanyType.getSelectedItemId()));
		}
		return company;
	}

	private void handleEvent() {

	}

	protected void closingWindowHandler() {
		company = null;
		isCancel = true;
	}

	@Override
	public void performFinish() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doCancelAction() {
		company = null;
		isCancel = true;
		dispose();
	}

	@Override
	public boolean isValidData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void panelActive() {
		// TODO Auto-generated method stub

	}

	@Override
	public void panelDeactive() {
		// TODO Auto-generated method stub

	}

}
