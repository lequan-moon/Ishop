package com.nn.ishop.client.gui.common;

import java.util.List;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.server.dto.grn.GrnHistoryBean;

public class ShowGRNHistoryManager extends AbstractGUIManager implements
		GUIActionListener {
	GenericDialog DlgGRNHis;
	CPaging pageGRNHis;
	String ppId;
	void init() {
		initTemplate(this, "common/showGRNHistory.xml");
		render();
		bindEventHandlers();		
	}
	public ShowGRNHistoryManager(String ppId){
			setLocale(Language.getInstance().getLocale());
			this.ppId = ppId;
			init();
	}
	public ShowGRNHistoryManager() {
	}

	public void guiActionPerformed(GUIActionEvent action) {

	}

	@Override
	protected void bindEventHandlers() {
		try {
			List<GrnHistoryBean> listGrnHis = CommonLogic.loadGRNHistory(ppId);
			TableUtil.addListToTable(pageGRNHis, pageGRNHis.getTable(),
					listGrnHis);
			DlgGRNHis.setModal(false);
			DlgGRNHis.setAlwaysOnTop(true);
		} catch (Throwable  e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	protected void applyStyles() {

	}

	@Override
	public void update() {

	}

	@Override
	public void updateList() {

	}

	@Override
	protected void checkPermission() {

	}	 

}