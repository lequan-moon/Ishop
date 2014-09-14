package com.nn.ishop.client.gui.dialogs;

import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.nn.ishop.license.IShopLicense;

public class LicenseInfoDialog extends com.nn.ishop.client.gui.dialogs.CWizardDialog {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -5433452306453838358L;
	Properties sysProperties;
	JFileChooser chooser = null;

	/**
	 * (non-Javadoc)
	 * @see com.nn.ishop.cclient.dialog.CWizardDialog#performFinish()
	 */
	public void performFinish() {
		throw new UnsupportedOperationException();
	}

	/**
	 * (non-Javadoc)
	 * @see com.nn.ishop.cclient.helper.dialog.CWizardDialog#doCancelAction()
	 */
	public void doCancelAction() {
		throw new UnsupportedOperationException();
	}

	/**
	 * (non-Javadoc)
	 * @see com.nn.ishop.cclient.helper.dialog.CWizardDialog#isValidData()
	 */
	public boolean isValidData() {
		throw new UnsupportedOperationException();
	}

	/**
	 * (non-Javadoc)
	 * @see com.nn.ishop.cclient.helper.dialog.CWizardDialog#panelActive()
	 */
	public void panelActive() {
		throw new UnsupportedOperationException();
	}

	/**
	 * (non-Javadoc)
	 * @see com.nn.ishop.cclient.helper.dialog.CWizardDialog#panelDeactive()
	 */
	public void panelDeactive() {
		throw new UnsupportedOperationException();
	}

	public LicenseInfoDialog(JFrame parentFrame, String customerName, String feature, String licSerial, String licType, boolean modal) {
		super(parentFrame, true, "CONNECT", "", "");
	}

	/**
	 * Builds controls in the dialog
	 */
	protected void createDialogArea() {
	}

	/**
	 * (non-Javadoc)
	 * @see com.nn.ishop.cclient.helper.dialog.CWizardDialog#updateLicense()
	 */
	public IShopLicense updateLicense() {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.nn.ishop.cclient.helper.dialog.CWizardDialog#doBackAction()
	 */
	public void doBackAction() {
	}

	/**
	 * (non-Javadoc)
	 * @see com.nn.ishop.cclient.helper.dialog.CWizardDialog#doNextAction()
	 */
	public void doNextAction() {
	}

	/**
	 * (non-Javadoc)
	 * @see com.nn.ishop.cclient.helper.dialog.CWizardDialog#saveSettings()
	 */
	public boolean saveSettings() {
		return false;
	}

	/**
	 * (non-Javadoc)
	 * @see com.nn.ishop.cclient.helper.dialog.CWizardDialog#doOkAction()
	 */
	public void doOkAction() {
		
	}

//	/**
//	 * Open file chooser dialog
//	 * @param isDatFile True is .dat file and False is .key file
//	 */
//	private String getLicenseFilePath() {
//		return null;
//	}
//
//	/**
//	 * Operates importing new license action
//	 */
//	private void doLicenseImport() {
//	}
//
//	private void updateLicInfodialog(ConnectLicense license) {
//	}
}