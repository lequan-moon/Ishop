package com.nn.ishop.client.gui;

import java.awt.Container;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.ElementFilter;
import org.jdom.output.XMLOutputter;
import org.jfree.chart.ChartPanel;
import org.swixml.SwingEngine;
import org.swixml.TagLibrary;
import org.xhtmlrenderer.simple.XHTMLPanel;
import org.xhtmlrenderer.swing.BasicPanel;
import org.xhtmlrenderer.swing.FSMouseListener;
import org.xhtmlrenderer.swing.LinkListener;

import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.barcode.JBarcodeBean;
import com.nn.ishop.client.gui.components.AutoCompleteComboBox;
import com.nn.ishop.client.gui.components.BasicNextArrowButton;
import com.nn.ishop.client.gui.components.BasicPrevArrowButton;
import com.nn.ishop.client.gui.components.CButton;
import com.nn.ishop.client.gui.components.CButton2;
import com.nn.ishop.client.gui.components.CCheckBox;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLabel;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CList;
import com.nn.ishop.client.gui.components.CMenu;
import com.nn.ishop.client.gui.components.CMenuBar;
import com.nn.ishop.client.gui.components.CMenuItem;
import com.nn.ishop.client.gui.components.CNonSelectableButton;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CPopupMenu;
import com.nn.ishop.client.gui.components.CProductDetailPaging;
import com.nn.ishop.client.gui.components.CProgressBar;
import com.nn.ishop.client.gui.components.CScalableXHTMLPanel;
import com.nn.ishop.client.gui.components.CSeparator;
import com.nn.ishop.client.gui.components.CTable;
import com.nn.ishop.client.gui.components.CTableCellRenderer;
import com.nn.ishop.client.gui.components.CTableCheckBoxRenderer;
import com.nn.ishop.client.gui.components.CTextArea;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.ImageLabel;
import com.nn.ishop.client.gui.components.RoundedBorderComponent;
import com.nn.ishop.client.gui.components.ScrollableBar;
import com.nn.ishop.client.gui.dialogs.CHeaderPanel;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.toedter.calendar.JDateChooser;


public class CSwingEngine extends SwingEngine {

	/** As form of <html panel id, String value of XHTMLPanel element> */
	private HashMap<String,String> xhtmlContents;

	public CSwingEngine(){
		super();
		init();
	}

	public CSwingEngine(Object client){
		super(client);
		init();
	}

	protected void init(){
		//init xhtmlContents Map
		xhtmlContents = new HashMap<String, String>();

		
		//add tags specific for connect
		TagLibrary lib = getTaglib();		
		
		lib.registerTag("cbutton",CButton.class);
		lib.registerTag("cbutton2",CButton2.class);
		lib.registerTag("ccheckbox",CCheckBox.class);
		lib.registerTag("ccombobox",CComboBox.class);
		lib.registerTag("clabel",CLabel.class);
		lib.registerTag("CLineBorder",CLineBorder.class);
		lib.registerTag("clist",CList.class);
		
		lib.registerTag("cpaging",CPaging.class);
		lib.registerTag("cproductpaging",CProductDetailPaging.class);
		lib.registerTag("cpopupmenu",CPopupMenu.class);
		lib.registerTag("ctabbedpane",CTabbedPane.class);
		lib.registerTag("ctable",CTable.class);
		lib.registerTag("ctablecellrenderer",CTableCellRenderer.class);
		lib.registerTag("ctablecheckboxrenderer",CTableCheckBoxRenderer.class);
		lib.registerTag("ctextfield",CTextField.class);
		lib.registerTag("ctextarea",CTextArea.class);
		lib.registerTag("xhtmlpanel", CScalableXHTMLPanel.class);
		lib.registerTag("cprogressbar", CProgressBar.class);
		lib.registerTag("datechooser", JDateChooser.class);
		lib.registerTag("basicnextarrowbutton", BasicNextArrowButton.class);
		lib.registerTag("basicprevarrowbutton", BasicPrevArrowButton.class);
		lib.registerTag("autocompletecombobox", AutoCompleteComboBox.class);
		lib.registerTag("cwhitepanel", CWhitePanel.class);
		lib.registerTag("cdlgbutton", CDialogsLabelButton.class);
		lib.registerTag("ctwomodepanel", CTwoModePanel.class);
		lib.registerTag("imagelabel", ImageLabel.class);
		
		lib.registerTag("scrollablebar", ScrollableBar.class);
		lib.registerTag("nonselectablebutton", CNonSelectableButton.class);
		lib.registerTag("cmenu", CMenu.class);
		lib.registerTag("cmenubar", CMenuBar.class);
		lib.registerTag("cmenuitem", CMenuItem.class);
		lib.registerTag("cseparator",CSeparator.class);
		
		lib.registerTag("genericdialog", GenericDialog.class);
		lib.registerTag("dialogheader", CHeaderPanel.class);
		lib.registerTag("barcode", JBarcodeBean.class);		
		lib.registerTag("chartpanel", ChartPanel.class);
		lib.registerTag("roundbordercomponent", RoundedBorderComponent.class);
	}

	/*
	 * Target of this Override method is filter out xhtmlpanel  add custom
	 * LinkListener to each html hyper link of each XHTMLPanel
	 * @see org.swixml.SwingEngine#render(org.jdom.Document)
	 */
	@SuppressWarnings("unchecked")
	public Container render(final Document jdoc) throws Exception {
		// Filter out xhtmlpanel contents
        Iterator<Element> it = jdoc.getDocument().getDescendants(
        		new ElementFilter("xhtmlpanel")
        		);
    	XMLOutputter xmlWriter = new XMLOutputter();
        String id;
        int i = 0;
        while(it.hasNext()){
        	Element elt = it.next();
        	id = elt.getAttributeValue("id");
        	//set id attribute
        	if(id == null || id.equals("")){
        		id = "_xhtmlpanel_" + i;
        		elt.setAttribute("id", id);
        	}
        	xhtmlContents.put(id, xmlWriter.outputString(elt.removeContent()));
        	i++;
        }

        // Rendering the whole document
		Container root = super.render(jdoc);

		// Add xhtml to xhtml panels
		Iterator<String> ids = xhtmlContents.keySet().iterator();
		while(ids.hasNext()){
			id = ids.next();
			// Finding out the XHTMLPanel by id
			XHTMLPanel x = (XHTMLPanel)find(id);
			// Remove default link listener
			Iterator mlIt = x.getMouseTrackingListeners().iterator();
		    while (mlIt.hasNext()) {
		    	FSMouseListener ml = (FSMouseListener)mlIt.next();
		        if (ml instanceof LinkListener) {
		            x.removeMouseTrackingListener(ml);
		        }
	        }
		    // Add link listener for internal handling
		    x.addMouseTrackingListener(new CLinkListener(id));

			// Add contents
		    x.setDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
				new ByteArrayInputStream(xhtmlContents.get(id).getBytes("UTF-8"))));
		}

		return root;
	}

	
    class CLinkListener extends LinkListener{

    	String panelId;

    	CLinkListener(String panelId){
    		this.panelId = panelId;
    	}

    	public void linkClicked(BasicPanel panel, String uri) {
			if(getClient() instanceof GUIActionListener){
	    		if (uri.startsWith("connect:")) {
					String[] chunks = uri.split(":");
					if (chunks[1].equals("refresh")) {
						String pId = panelId;
						if(chunks.length == 3 && !chunks[2].equals("") && !chunks[2].equals("this")){
							pId = chunks[2];
						}
						((GUIActionListener)getClient()).guiActionPerformed(
								new GUIActionEvent(this, GUIActionEvent.GUIActionType.RESTART_GUI, pId));

					}else if (chunks[1].equals("view.all.detail")) {
						try
						{
							((GUIActionListener)getClient()).guiActionPerformed(
									new GUIActionEvent(this, GUIActionEvent.GUIActionType.NAVIGATE, null));
						}
						catch(Exception ex){

						}
					}
					// TODO other html hyper link actions go here
				}else
				{
					// Do something out of connect action

				}
			}
    	}// end linkClicked()
    }// end inner class	 CLinkListener
}// end main class

