package com.nn.ishop.client.gui.dialogs;

import javax.swing.JFrame;

/**
 * <p>Title: Connect Standalone Client</p>
 * <p>Description: This class represents about confirm dialog when user close system</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: shiftTHINK Ltd.</p>
 * @author Nguyen Hung Cuong
 * @version 1.0
 */
public class QuitDialog extends com.nn.ishop.client.gui.dialogs.CWizardDialog {
	private static final long serialVersionUID = 459765133794182980L;
//	private boolean isExit = false;
//	private Properties systemProperties;
//	private com.nn.ishop.client.gui.components.CDialogButton cmdExit;
//	private com.nn.ishop.client.gui.components.CDialogButton cmdCancel;
//	private JCheckBox chkConfirm;
//	private JLabel lblConfirm;
//	private JPanel pnlInfo;

	public QuitDialog(JFrame frame, boolean modal, String dialogMessage, String header1, String header2) {
		super(frame, true, "CONNECT", "", "");
	}

	/**
	 * Cancel exit action
	 */
	@SuppressWarnings("unused")
	private void cancel() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Exit system
	 */
	@SuppressWarnings("unused")
	private void exit() {
		throw new UnsupportedOperationException();
	}

	/**
	 * register event handler for ActionListeners and handle event
	 * for each components.
	 */
	@SuppressWarnings("unused")
	private void handleEvent() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return exit status of system
	 * @return isExit A boolean value indicates whether system will exit
	 */
	public boolean isExit() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Set exit for system
	 * @param isExit A boolean value indicates whether system will exit
	 */
	public void setExit(boolean isExit) {
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