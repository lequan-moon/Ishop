package com.nn.ishop.client.gui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.Util;

public class SystemMessageDialog extends CWizardDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 8722411172041123478L;
	/** UI Option */
	public static final int SHOW_OK_OPTION = 0;
	public static final int SHOW_OK_CANCEL_OPTION = 1;
	public static final int SHOW_KNE_SPIN_BAR_OPTION = 2;

	/** Return value: the same as JOptionPane return values */
	public static final int OK_BUTTON_CLICK = 0;
	public static final int NO_BUTTON_CLICK = 1;
	public static final int CANCEL_BUTTON_CLICK = 2;

	/** Real return value, default = -1 */
	private int retVal = -1;
	private static Icon exclaimIcon;
	private static Icon questionIcon;
	private  JTextArea aaMessage;
	static JFrame tempFrame = new JFrame();

	public SystemMessageDialog(JFrame frame, String message, int buttonOptions) {
		super(frame, true, "iShop System", "", "");
		setVisible(false);
		tempFrame.setIconImage(Util.getImage("logo32.png"));
		tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (frame == null)
			frame = tempFrame;
		Dimension d1 = this.getPreferredSize();
		d1.width = 400;
		d1.height = 150;
		setMinimumSize(d1);
		setPreferredSize(d1);
		setResizable(true);
		aaMessage = new JTextArea();
		aaMessage.setAutoscrolls(false);
		aaMessage.setEditable(false);
		aaMessage.setWrapStyleWord(true);
		//- Commet out to use system font
		//aaMessage.setFont(new Font("Arial", Font.PLAIN, 12));
		aaMessage.setLineWrap(true);
		aaMessage.setOpaque(true);
		aaMessage.setBackground(getBackground());
		aaMessage.setOpaque(false);
		JPanel newPnlNorth = new JPanel();
		updateNorthPanel(newPnlNorth);
		exclaimIcon = Util.getIcon("dialog/exclaim.png");
		questionIcon = Util.getIcon("dialog/question.png");
		// -- Navigation panel
		CNavigatorPanel nav = new CNavigatorPanel();
		nav.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		nav.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));		

		CDialogsLabelButton okButton = new CDialogsLabelButton(
				Language.getInstance().getString("buttonOK"));
		okButton.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = -3605142019690133527L;

			public void actionPerformed(ActionEvent e) {
				doOkAction();
			}
		});		
		okButton.setText(okButton.getText().toUpperCase());
		okButton.setFocusable(true);
		okButton.requestFocus();
		CDialogsLabelButton cancelButton = null;
		if (buttonOptions == SHOW_KNE_SPIN_BAR_OPTION
				|| buttonOptions == SHOW_OK_CANCEL_OPTION) {
			cancelButton = new CDialogsLabelButton(
					Language.getInstance().getString("buttonCancel"));
			cancelButton.setFocusable(true);
			cancelButton.addActionListener(new AbstractAction() {
				private static final long serialVersionUID = 6781514334705836341L;

				public void actionPerformed(ActionEvent e) {
					doCancelAction();
				}
			});
			nav.add(okButton);nav.add(cancelButton);
		}
		else{
			nav.add(okButton);
		}
		updateNavigatorPanel(nav);
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}
		});
		this.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER
						|| e.getKeyCode() == KeyEvent.VK_ESCAPE)
					dispose();
			}
		});
		if (frame == null) {
			centerDialog(this);
		} else {
			centerDialog(frame);
			centerDialogRelative(frame, this);
		}
		setAlwaysOnTop(true);
		doLayout();
		super.pack();
		this.pack();
		requestFocus();
		okButton.requestFocus();
				
	}

	/**
	 * showConfirm Dialog and return user selection
	 * 
	 * @param parent
	 * @param message
	 * @param buttonOptions
	 * @param messageType
	 * @return integer value: 0 if ok, 1 if cancel
	 */
	public static int showConfirmDialog(JFrame parent, String message,
			int buttonOptions) {
		tempFrame.setIconImage(Util.getImage("logo32.png"));
		// TODO
		// WSMSFrame.centerDialog(tempFrame);
		tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		SystemMessageDialog d = new SystemMessageDialog(tempFrame, message, buttonOptions);
		d.setVisible(false);
		
		JPanel centerPnl = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0f;

		JLabel lblIcon = new JLabel(questionIcon);
		centerPnl.add(lblIcon, gbc);

		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0f;
		gbc.weighty = 1.0f;
		gbc.anchor = GridBagConstraints.NORTHWEST;// CENTER;

		d.aaMessage.setText(message);
		FontMetrics metrics = d.aaMessage.getFontMetrics(d.aaMessage.getFont());
		int lengthOfMessage = metrics.charsWidth(d.aaMessage.getText().toCharArray(),
				0, d.aaMessage.getText().toCharArray().length);
		
		int length = d.aaMessage.getDocument().getLength();
		d.aaMessage.setCaretPosition(length);
		
		d.aaMessage.setBackground(Library.DEFAULT_FORM_BACKGROUND);
		//d.aaMessage.setBackground(Color.PINK);
		centerPnl.add(d.aaMessage, gbc);

		d.updateCenterPanel(centerPnl);
		d.doLayout();
		d.pack();
		d.validate();
		d.setVisible(true);
		d.requestFocus();
		tempFrame.dispose();
		return d.getReturnValue();

	}

	public static void showMessageDialog(JFrame parent, String message,
			int buttonOptions) {

		// caught error
		// tempFrame.setIconImage(Toolkit.getDefaultToolkit().createImage(
		// com.nn.ishop.client.Main.class.getResource("img/logo32x32.png")));
		tempFrame.setIconImage(Util.getImage("logo32.png"));
		// TODO
		// WSMSFrame.centerDialog(tempFrame);
		tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		SystemMessageDialog d = new SystemMessageDialog(tempFrame, message, buttonOptions);
		
		JPanel centerPnl = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0f;

		JLabel lblIcon = new JLabel(exclaimIcon);
		centerPnl.add(lblIcon, gbc);

		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0f;
		gbc.weighty = 1.0f;
		gbc.anchor = GridBagConstraints.NORTHWEST;// CENTER;

		d.aaMessage.setText(message);
		Dimension d1 = d.getPreferredSize();
		d1.width = 300;
		d.aaMessage.setMinimumSize(d1);
		d.aaMessage.setBackground(Library.DEFAULT_FORM_BACKGROUND);

		int length = d.aaMessage.getDocument().getLength();
		d.aaMessage.setCaretPosition(length);

		centerPnl.add(d.aaMessage, gbc);

		d.updateCenterPanel(centerPnl);
		d.pack();
		d.setVisible(true);
		d.setFocusable(true);
		d.requestFocus();
		tempFrame.dispose();
	}

	public static int showInputKNEDialog(JFrame parent, String message,
			int buttonOptions) {
		return -1;
	}

	public void doOkAction() {
		setReturnValue(SystemMessageDialog.OK_BUTTON_CLICK);
		tempFrame.dispose();
		this.dispose();
	}

	public void doCancelAction() {
		tempFrame.dispose();
		setReturnValue(-1);
		dispose();
	}

	public void setReturnValue(int value) {
		this.retVal = value;
	}

	public int getReturnValue() {
		return this.retVal;
	}

	public boolean isValidData() {
		return false;
	}

	public void panelActive() {

	}

	public void panelDeactive() {

	}

	public void performFinish() {

	}

	public static void main(String[] args) {
		//JFrame f = new JFrame();
		//f.setLocation(new Point(200, 200));
		try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}	
		int retVal = SystemMessageDialog
				.showConfirmDialog(null/*f*/, "Do you agree with this test,"
						+ "Do you agree with this test,Do you agree with"
						+ " this test,Do you agree with this test,"						
						
						+ "with this test?", SystemMessageDialog.SHOW_OK_CANCEL_OPTION);


//		f.dispose();
	}

	public static void centerDlg(Window frame) {
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();
		Dimension frameSize = frame.getSize();
		frame.setLocation(center.x - frameSize.width / 2, center.y
				- frameSize.height / 2 - 10);
	}

	/**
	 * Centers a window on screen. should use for login dialog
	 * 
	 * @param frame
	 *            The window instance.
	 */
	public static void centerDlgRelative(Window parent, Window childDlg) {
		Dimension dlgSize = childDlg.getPreferredSize();
		Dimension frmSize = parent.getSize();
		Point loc = parent.getLocation();
		childDlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
				(frmSize.height - dlgSize.height) / 2 + loc.y);
	}
}
