package com.nn.ishop.client.gui.dialogs;

import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

/**
 * <p>Title: Connect Standalone Client</p>
 * <p>Description: This class shows a dialog for users register
 * via email</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: shiftTHINK Ltd.</p>
 * @author nvnghia
 * @version 1.0
 */
public class RegisterViaEmailDialog extends JDialog {
	private static final long serialVersionUID = 1652593315664426035L;
//	private JDateChooser dateChooser;
	Logger log = Logger.getLogger(RegisterViaEmailDialog.class);
	Properties systemProperties = null;
//	private JPanel pnlParent;
//	private JPanel pnlArea;
//	private JPanel pnlButton;
//	private JTextField txtName;
//	private JTextField txtEmail;
//	private JComboBox cbFeature;
//	private JButton btnRegister;
//	private JButton btnCancel;
	com.nn.ishop.client.gui.dialogs.RegisterLicenseDialog licenseDialog;

	/**
	 * Constructs a new RegisterViaEmailDialog object
	 * @param parent JFrame parent
	 */
	public RegisterViaEmailDialog(JFrame parent) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Builds components in the dialog
	 */
	protected void createDialogArea() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Builds buttons in the dialog
	 */
	protected void createButtonsArea() {
		throw new UnsupportedOperationException();
	}

	protected void handleDialogEvents() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Handles the event when register button
	 * is clicked
	 */
	@SuppressWarnings("unused")
	private void registerPressed() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Does license registration action
	 */
	@SuppressWarnings("unused")
	private void registerLicense() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Sends email to administrator from customer
	 * in order to register a license
	 * @param smtpHost SMTP configuration
	 * @param from Customer's email
	 * @param to Administration's email
	 * @param title	Email subject
	 * @param content Email content
	 */
	@SuppressWarnings("unused")
	private void sendEmail(String smtpHost, String from, String to, String title, String content) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Exit the application
	 */
	@SuppressWarnings("unused")
	private void exit() {
		throw new UnsupportedOperationException();
	}
}