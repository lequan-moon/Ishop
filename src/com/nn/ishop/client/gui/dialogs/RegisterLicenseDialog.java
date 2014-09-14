package com.nn.ishop.client.gui.dialogs;

import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * <p>Title: SIC Register License </p>
 * <p>Description: This class shows a dialog for users import
 * license</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author nvnghia
 * @version 1.0
 */
public class RegisterLicenseDialog extends com.nn.ishop.client.gui.dialogs.CWizardDialog {
	private static final long serialVersionUID = -57824462174109169L;
	byte[] licBytes = null;
	/**
	 * private Properties sysProperties;
	 */
	JTextField txtDat;

	public RegisterLicenseDialog(JFrame frame) {
		super(frame, true, "CONNECT", "", "");
	}

	/**
	 * Constructs a new ImportLicenseDialog
	 * @param parentFrame JFrame parent
	 * @param licenseDialog LicenseDialog
	 * @param model Modal of the dialog
	 */
	public RegisterLicenseDialog(JFrame frame, boolean modal, String dialogTitle, String header1, String header2) {
		super(frame, true, "CONNECT", "", "");
	}

	/**
	 * Builds controls in the dialog
	 */
	protected void createDialogArea() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Handles the dialog events
	 */
	protected void handleDialogEvents() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Open file chooser dialog
	 * @param isDatFile True is .dat file and False is .key file
	 */
	@SuppressWarnings("unused")
	private void addLicenseFile(boolean isDatFile) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Handles import button action
	 */
	@SuppressWarnings("unused")
	private void importPressed() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks validity of given path
	 * @param path The file license path including .dat and .key file
	 * @param isDatFile True is .dat file and False is .key file
	 * @return Returns true if the give path is valid
	 */
	@SuppressWarnings("unused")
	private boolean isValidPath(String path, boolean isDatFile) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Operates importing new license action
	 */
	@SuppressWarnings("unused")
	private void doLicenseImport() {
		throw new UnsupportedOperationException();
	}

	public void doCancelAction() {
		throw new UnsupportedOperationException();
	}

	public boolean isValidData() {
		throw new UnsupportedOperationException();
	}

	public void panelActive() {
		throw new UnsupportedOperationException();
	}

	public void panelDeactive() {
		throw new UnsupportedOperationException();
	}

	public void performFinish() {
		throw new UnsupportedOperationException();
	}
}