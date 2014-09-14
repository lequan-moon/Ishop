package com.nn.ishop.client.gui.printable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.barcode.JBarcodeBean;
import com.nn.ishop.client.gui.barcode.impl.Code128;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CTable;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.dialogs.CNavigatorPanel;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.util.PrintUtility;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.util.Formatter;

public class PrintableInvoice extends com.nn.ishop.client.gui.components.CWhitePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8679655193763990330L;
	Object[][] dataDetail;
	String[] dataHeader;
	JLabel reportTitle = new JLabel(Language.getInstance().getString("storebill.header"));
	JBarcodeBean barCode = new JBarcodeBean();
	CWhitePanel mainPnl = new CWhitePanel(new GridBagLayout());
	CWhitePanel companyInfoPnl = new CWhitePanel(new GridBagLayout());
	CWhitePanel titleInfoPnl = new CWhitePanel(new BorderLayout());
	CWhitePanel billInfoPnl = new CWhitePanel(new GridBagLayout());
	
	CWhitePanel headerInfoPnl = new CWhitePanel(new GridBagLayout());
	CWhitePanel headerInfoPnl2 = new CWhitePanel(new GridBagLayout());
	
	CWhitePanel headerDetailInfoPnl = new CWhitePanel(new GridBagLayout());
	CWhitePanel detailInfoPnl = new CWhitePanel(new GridBagLayout());
	
	CWhitePanel footerInfoPnl = new CWhitePanel(new FlowLayout(FlowLayout.CENTER,30,15));
	
	JLabel companyName = new JLabel(Language.getInstance().getString("storebill.company.name"));
	JLabel factoryName = new JLabel(Language.getInstance().getString("ten.xuong"));
	JLabel companyAddress = new JLabel(Language.getInstance().getString("storebill.company.address"));
	JLabel companyTelephone = new JLabel(Language.getInstance().getString("factory.and.fax"));
	
	JLabel receiptDate = new JLabel("");
	String dayMonthYear = "";
	
	JLabel customerName = new JLabel("");
	JLabel customerAddress = new JLabel("");
	JLabel customerTaxCode = new JLabel("");
	JLabel customerTelephone = new JLabel("");
	
	JLabel billIssuer = new JLabel(Language.getInstance().getString("storebill.issuer"));
	JLabel billReceiver = new JLabel(Language.getInstance().getString("storebill.receiver"));
	JLabel billDeliverer = new JLabel(Language.getInstance().getString("storebill.deliverer"));
	JLabel billAccountant = new JLabel(Language.getInstance().getString("storebill.accountant"));
	JDialog parentdlg;
	
	CTable detailTbl = null;
	JLabel titleVAT = new JLabel(Language.getInstance().getString("vat.concern"));
	JLabel amountInWordTitle = new JLabel(Language.getInstance().getString("amount.in.word"));
	JTextArea amountInWord = new JTextArea();
	JLabel details = new JLabel(Language.getInstance().getString("storebill.detail"));	
	
	Formatter fm = new Formatter();
	SystemMessageDialog d;
	
	static Logger log = Logger.getLogger(PrintableInvoice.class);

	public void setTitle(String text){
		reportTitle.setText(text);
	}
	
	public PrintableInvoice(JDialog parentdlg,
			Object[][] dataDetail, 
			String[] dataHeader,
			String barCode,
			String creationDate,
			String providerName,
			String providerAddress,
			String providerTaxCode,
			String providerTelephone,
			String caution
			) {		
		this.parentdlg = parentdlg;
		String newDataHeader[] = { 
				"STT",
	    		Language.getInstance().getString("item.name"),
	    		Language.getInstance().getString("item.unit"),
	    		Language.getInstance().getString("card.item.quantity"),
	    		Language.getInstance().getString("common.buying.price"),
	    		Language.getInstance().getString("card.item.total.money") 
	    		};
		Object[][] newData = new Object[dataDetail.length+1][];	
		double amt = 0.0;
		for(int i=0;i<dataDetail.length;i++){
			newData[i] = new Object[newDataHeader.length];
			try {
				amt += fm.str2num((String) dataDetail[i][5]).doubleValue();
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			for (int j=0;j<newDataHeader.length;j++)
			{
				newData[i][j] = dataDetail[i][j];
			}
		}
		newData[newData.length -1] = new Object[newDataHeader.length];		
		newData[newData.length -1][0] = Language.getInstance().getString("form.grand.amount");
		newData[newData.length -1][1] = "";
		newData[newData.length -1][2] = " ";
		newData[newData.length -1][3] = " ";
		newData[newData.length -1][4] = " ";
		newData[newData.length -1][5] = fm.num2str(amt);
		
		for(int i=0;i<newData.length;i++){
			if(i+1 == newData.length)
			{
				newData[i][0] = String.valueOf(Language.getInstance().getString("form.grand.amount"));
			}else
				newData[i][0] = String.valueOf(i+1);
		}
		
		try {
			dayMonthYear = Language.getInstance().getString("day")
					+ creationDate.split("/")[0]
					+ Language.getInstance().getString("month")
					+ creationDate.split("/")[1]
					+ Language.getInstance().getString("year")
					+ creationDate.split("/")[2];
		} catch (Exception e) {
			// TODO: handle exception
		}
		receiptDate = new JLabel(dayMonthYear);
		
		customerName = new JLabel(Language.getInstance().getString("card.provider")+": " + providerName);
		customerAddress = new JLabel(Language.getInstance().getString("card.provider.address")+": " + providerAddress);
		customerTaxCode = new JLabel(Language.getInstance().getString("card.provider.tax.code")+": " 
				+ providerTaxCode);
		customerTelephone = new JLabel(Language.getInstance().getString("card.provider.telephone")+": " 
				+ providerTelephone);
		details = new JLabel(Language.getInstance().getString("storebill.detail")+
				caution);
		// TODO
//		detailTbl = new CTable(newData, newDataHeader,
//				CTable.TBL_PRINT_BILL,
//				new CMap4IssuePrintTable()
//				);
//		TableUtil.formatPrintableTable(detailTbl, newData, newDataHeader, false);
		
		detailTbl.setBackground(Color.WHITE);
		
		this.barCode.setCode(barCode);
		this.barCode.setCodeType(new Code128());
		this.barCode.setShowText(true);
		
		amountInWord.setAutoscrolls(false);
		amountInWord.setEditable(false);
		amountInWord.setWrapStyleWord(true);
		amountInWord.setBackground(getBackground());
		amountInWord.setLineWrap(true);
		amountInWord.setText(" ("+Util.translateNumsToWords(fm.num2str(amt))+") ");
		
//		Dimension d1 = amountInWord.getPreferredSize();
//		d1.width = 220;
//		amountInWord.setMinimumSize(d1);
		int length = amountInWord.getDocument().getLength();		 
		amountInWord.setCaretPosition(length);
		amountInWord.setBackground(Color.WHITE);
		this.requestFocus();
		jbInit();
		this.validate();
	}
	void jbInit()
	{
		setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0f, 1.0f,
				GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1), 0, 0);
		//- build company onfor
		GridBagConstraints comInfoGbc = new GridBagConstraints(0, 0, 1, 1, 1.0f, 1.0f,
				GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1), 0, 0);
		companyInfoPnl.add(companyName,comInfoGbc);
		comInfoGbc.gridy=1;
		companyInfoPnl.add(factoryName,comInfoGbc);
		comInfoGbc.gridy=2;
		companyInfoPnl.add(companyAddress,comInfoGbc);
		comInfoGbc.gridy=3;
		companyInfoPnl.add(companyTelephone,comInfoGbc);
		
		titleInfoPnl.add(reportTitle, BorderLayout.NORTH);
		titleInfoPnl.add(new JLabel("                            "+dayMonthYear), BorderLayout.CENTER);
		
		
		GridBagConstraints billInfoGbc = new GridBagConstraints(0, 0, 1, 1, 1.0f, 1.0f,
				GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(1,1,1,1), 0, 0);
		
		billInfoGbc.anchor = GridBagConstraints.NORTHEAST;
		
		billInfoPnl.add(barCode, billInfoGbc);
		
		
		GridBagConstraints headerInfoPnlGbc = new GridBagConstraints(0, 0, 1, 1, 1.0f, 1.0f,
				GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1), 0, 0);
		headerInfoPnl.add(companyInfoPnl, headerInfoPnlGbc);
		headerInfoPnlGbc.gridx=1;		
		headerInfoPnlGbc.anchor = GridBagConstraints.NORTHEAST;		
		headerInfoPnl.add(billInfoPnl, headerInfoPnlGbc);
		
		GridBagConstraints headerInfoPnlGbc2 = new GridBagConstraints(0, 0, 1, 1, 1.0f, 1.0f,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(1,1,1,1), 0, 0);

		headerInfoPnlGbc2.gridwidth = 2;
		headerInfoPnlGbc2.anchor = GridBagConstraints.CENTER;
		headerInfoPnl2.add(titleInfoPnl, headerInfoPnlGbc2);
		
		GridBagConstraints headerDetailInfoPnlGbc = new GridBagConstraints(0, 0, 1, 1, 1.0f, 1.0f,
				GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1), 0, 0);
		headerDetailInfoPnl.add(customerName,headerDetailInfoPnlGbc);
//		headerDetailInfoPnlGbc.gridy+=1;
//		headerDetailInfoPnl.add(customerAddress,headerDetailInfoPnlGbc);
		headerDetailInfoPnlGbc.gridy+=1;
		headerDetailInfoPnl.add(customerTaxCode,headerDetailInfoPnlGbc);
		headerDetailInfoPnlGbc.gridy+=1;
		headerDetailInfoPnl.add(customerTelephone,headerDetailInfoPnlGbc);

		
		CWhitePanel tempPnlDetail = new CWhitePanel(new GridBagLayout());
		GridBagConstraints detailGbc0 = new GridBagConstraints(0, 0, 1, 1, 1.0f, 1.0f,
				GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1), 0, 0);
		

		
		tempPnlDetail.add(details, detailGbc0);
		detailGbc0.gridy=1;
		tempPnlDetail.add(detailInfoPnl,detailGbc0);
		
		GridBagConstraints detailGbc = new GridBagConstraints(0, 0, 1, 1, 1.0f, 1.0f,
				GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1), 0, 0);
		JScrollPane scrTable = new JScrollPane(detailTbl);
		detailTbl.setBorder(new CLineBorder(Color.BLACK, CLineBorder.DRAW_ALL_BORDER));
		
		Dimension d0 = detailTbl.getPreferredSize();
		
		d0.height += 22;
		scrTable.setMaximumSize(d0);
		scrTable.setMinimumSize(d0);
		scrTable.setPreferredSize(d0);
		
		scrTable.setBackground(Color.WHITE);
		scrTable.getViewport().setBackground(Color.WHITE);
		scrTable.setFocusable(false);
		scrTable.setBorder(BorderFactory.createEmptyBorder());
		
		detailInfoPnl.add(scrTable, detailGbc);
		detailGbc.gridy+=1;
		detailGbc.anchor = GridBagConstraints.CENTER;

		CWhitePanel detailInfoPnlSub1 = 
			new CWhitePanel(new FlowLayout(FlowLayout.CENTER,2,2));
		detailInfoPnlSub1.add(titleVAT);
		detailInfoPnl.add(detailInfoPnlSub1, detailGbc);
		
//		CWhitePanel detailInfoPnlSub2 = 
//			new CWhitePanel(new FlowLayout(FlowLayout.LEFT,2,2));
//		detailInfoPnlSub2.add(amountInWordTitle);		
		
		amountInWord.setText(amountInWordTitle.getText()+amountInWord.getText());
		detailGbc.fill = GridBagConstraints.HORIZONTAL;
		detailGbc.anchor = GridBagConstraints.NORTHWEST;
		detailGbc.gridy+=1;
		detailInfoPnl.add(amountInWord, detailGbc);
		
//		detailGbc.anchor = GridBagConstraints.NORTHWEST;
//		detailGbc.gridx+=1;
//		detailInfoPnl.add(amountInWord, detailGbc);
		
		detailGbc.anchor = GridBagConstraints.NORTHEAST;
		detailGbc.fill = GridBagConstraints.HORIZONTAL;
		detailGbc.gridy+=1;detailGbc.gridx = 0;
		receiptDate.setBorder(BorderFactory.createEtchedBorder());
		receiptDate = new JLabel(receiptDate.getText(),JLabel.RIGHT);
		detailInfoPnl.add(receiptDate, detailGbc);
		detailTbl.validate();
		
		//-- FOOTER
		footerInfoPnl.add(billIssuer);
		footerInfoPnl.add(billReceiver);
		footerInfoPnl.add(billDeliverer);
		footerInfoPnl.add(billAccountant);
		
		
		mainPnl.add(headerInfoPnl,gbc);
		gbc.gridy=1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPnl.add(headerInfoPnl2,gbc);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = 2;
		mainPnl.add(headerDetailInfoPnl,gbc);
		gbc.gridy=3;
		mainPnl.add(tempPnlDetail, gbc);
		gbc.gridy=4;
		mainPnl.add(footerInfoPnl,gbc);
		CWhitePanel t = new CWhitePanel(new BorderLayout());
		t.add(mainPnl, BorderLayout.WEST);
		CNavigatorPanel nav1 = getNavPanel();
		CNavigatorPanel nav2 = getNavPanel();
		t.add(nav2, BorderLayout.SOUTH);
		t.add(nav1, BorderLayout.NORTH);
		JScrollPane mainScr = new JScrollPane(t,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(mainScr, BorderLayout.NORTH);
		setFonts(this);
		
		// set font for customer
		customerName.setFont(new Font("Arial",Font.BOLD,10));
		customerAddress.setFont(new Font("Arial",Font.BOLD,10));
		customerTaxCode.setFont(new Font("Arial",Font.BOLD,10));
		customerTelephone.setFont(new Font("Arial",Font.BOLD,10));
		
		
		reportTitle.setFont(new Font("Arial",Font.BOLD,14));
		companyName.setFont(new Font("Arial",Font.BOLD,10));
		amountInWord.setFont(new Font("Arial",Font.ITALIC,9));
		amountInWordTitle.setFont(new Font("Arial",Font.BOLD,9));
		titleVAT.setFont(new Font("Arial",Font.BOLD,9));
			
	}
	public void printForm()
	{
		parentdlg.setAlwaysOnTop(false);
		PrintUtility.printComponent(mainPnl);
		parentdlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		parentdlg.dispose();
	}
	private CNavigatorPanel getNavPanel(){
		CNavigatorPanel navPnl = new CNavigatorPanel();
		navPnl.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		CDialogsLabelButton saveBtn = new CDialogsLabelButton(
				Language.getInstance().getString("button.print.form")/*,
				Util.getIcon("dialog/btn_normal.png"),
				Util.getIcon("dialog/btn_active.png")*/);	
		saveBtn.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				printForm();
			}
		});
// TODO
//		navPnl.addButton(saveBtn);
		
		return navPnl;
	}
	protected void setFonts(Component comp)
	{
		if(comp instanceof JLabel)
		{
			((JLabel)comp).setFont(new Font("Arial",Font.PLAIN,9));
		}
		else if(comp instanceof JTextField )
		{
			((JTextField)comp).setFont(new Font("Arial",Font.PLAIN,9));
		}
		else if(comp instanceof JTextArea )
		{
			((JTextArea)comp).setFont(new Font("Arial",Font.PLAIN,9));
		}
		else if(comp instanceof JBarcodeBean )
		{
			((JBarcodeBean)comp).setFont(new Font("Arial",Font.PLAIN,9));
		}		
		else if(comp instanceof Container)
		{
			Component[] subComps = ((Container)comp).getComponents();
			for(Component subComp:subComps)
			setFonts(subComp);
		}
	}
}
