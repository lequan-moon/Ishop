package com.nn.ishop.client.gui.admin.country;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.admin.country.controller.CountryController;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CWhitePanel;

public class AdminCountryGUIManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener {
	CWhitePanel countryActionPanel;
	CDialogsLabelButton countrySaveButton;
	CTextField txtCountryCode;
	CTextField txtCountryName;
	CWhitePanel countryMasterPanel;
	
	public AdminCountryGUIManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "admin/quocgia/adminCountryMasterPage.xml", getLocale());
		}else{
			initTemplate(this, "admin/quocgia/adminCountryMasterPage.xml");		
			}
		render();
		bindEventHandlers();
	}
	protected void applyStyles() {
		countryActionPanel.setBorder(new CLineBorder(null, CLineBorder.DRAW_TOP_BORDER));
	}
	
	protected void bindEventHandlers() {
		// TODO Auto-generated method stub
		countrySaveButton.addActionListener(
			new AbstractAction() {		
				private static final long serialVersionUID = -6917660089900313865L;

				public void actionPerformed(ActionEvent e) {
					CountryController.addCountry(txtCountryCode.getText(), txtCountryName.getText());					
				}
			}
		);
		

	}

	public Object getData(String var) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateList() {
		// TODO Auto-generated method stub

	}

	public void cActionPerformed(CActionEvent action) {
		// TODO Auto-generated method stub

	}

	public void guiActionPerformed(GUIActionEvent action) {
		// TODO Auto-generated method stub

	}

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}

	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub

	}
	protected void checkPermission() {
		// TODO Auto-generated method stub
		
	}
	public void addCActionListener(CActionListener al) {
		// TODO Auto-generated method stub
		
	}
	public void removeCActionListener(CActionListener al) {
		// TODO Auto-generated method stub
		
	}

}
