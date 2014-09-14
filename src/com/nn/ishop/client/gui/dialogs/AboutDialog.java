package com.nn.ishop.client.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.InputStream;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CButton;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.util.Util;

public class AboutDialog extends CWizardDialog
{

	/**
	 *  Serial version UID
	 */
	private static final long serialVersionUID = -5433452306453838358L;
//	private static String buildNumber = "-1";

	public AboutDialog(JFrame parentFrame
			, boolean modal
			){
		super(parentFrame, modal,  Language.getInstance().getString("about.infor.about.header.title")
				, "",
				"");
		createDialogArea();
		CHeaderPanel newPnlNorth = new CHeaderPanel(
				new JLabel(Language.getInstance().getString("about.infor.about.header.title"))
		,new JLabel(),"dialog/page_title.png");

		super.updateNorthPanel(newPnlNorth);

		// Center the dialog

        this.setPreferredSize(new Dimension(435, 280));

		pack();
		this.setModal(true);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	/**
	 * Builds controls in the dialog
	 */
	protected void createDialogArea(){

		CWhitePanel mainPnl = new CWhitePanel(new BorderLayout());

		CWhitePanel newPnlCenter = new CWhitePanel(new GridBagLayout());
		CWhitePanel leftPnl = new CWhitePanel(new BorderLayout());

        JLabel authorImg = new JLabel(Util.getIcon("profile/2nlogo.png"));
        leftPnl.setBorder(new EmptyBorder(2,2,2,2));
        leftPnl.add(authorImg, BorderLayout.CENTER);


        mainPnl.add(leftPnl, BorderLayout.WEST);
        mainPnl.add(newPnlCenter, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1,1,1,1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JLabel lbl1 = new JLabel( Language.getInstance().getString("application.name"));
        newPnlCenter.add(lbl1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.REMAINDER;
        JLabel lbl2 = new JLabel(Language.getInstance().getString("about.infor.version"));
        newPnlCenter.add(lbl2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.REMAINDER;
        JLabel lbl3 = new JLabel(Language.getInstance().getString("about.infor.copyright"));
        newPnlCenter.add(lbl3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.REMAINDER;
        JLabel lbl4 = new JLabel(Language.getInstance().getString("about.infor.contact.email"));
        newPnlCenter.add(lbl4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.REMAINDER;
        JLabel lbl41 = new JLabel(Language.getInstance().getString("about.infor.contact.tel"));
        newPnlCenter.add(lbl41, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.REMAINDER;
        JLabel lbl5 = new JLabel(Language.getInstance().getString("about.infor.contact.mobile"));
        newPnlCenter.add(lbl5, gbc);

		super.updateCenterPanel(mainPnl);
		CNavigatorPanel nav = new CNavigatorPanel();
		nav.setBackground(Color.WHITE);
		nav.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		nav.setBorder(null);

		CDialogsLabelButton okButton
		= nav.addOkButton();
		okButton.addActionListener(new AbstractAction(){
			private static final long serialVersionUID = -3230805332347054146L;

			public void actionPerformed(ActionEvent e) {
				doCancelAction();
			}
		});
		super.updateNavigatorPanel(nav);
		this.validate();
	}

	public void doCancelAction() {
		this.dispose();
	}

	public boolean isValidData() {
		return false;
	}

	public void panelActive() {

	}

	public void panelDeactive() {

	}

//	public ConnectLicense updateLicense() {
//		return null;
//	}

	public void doBackAction() {

	}

	public void doFinishAction() {

	}

	public void doNextAction() {

	}

	public boolean saveSettings() {
		return false;
	}

	public void doOkAction() {

	}

	@Override
	public void performFinish() {
		// TODO Auto-generated method stub

	}
	private static String getBuildNumber()
	{
		String ret = "";
		try
		{
			InputStream in = AboutDialog.class.getResourceAsStream(".revision");
			int buffer = 0;
			byte[] b = new byte[1];

//			if(in == null)
			while((buffer = in.read(b))!= -1){
				ret += new String(b);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ret;

	}
	public static void main(String[] args)
	{
//		AboutDialog ad = new AboutDialog();
//		String t = getBuildNumber();
		AboutDialog dlg = new AboutDialog(null,true);
		dlg.centerDialogRelative(null, dlg);
		dlg.setVisible(true);
	}

}