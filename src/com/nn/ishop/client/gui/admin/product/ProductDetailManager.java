package com.nn.ishop.client.gui.admin.product;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.log4j.Logger;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.ProgressEvent;
import com.nn.ishop.client.ProgressListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.barcode.JBarcodeBean;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CTextArea;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.ImageLabel;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.ProductCatLogic;
import com.nn.ishop.client.logic.admin.ProductLogic;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.product.ProductCategory;
import com.nn.ishop.server.dto.product.ProductItem;
import com.nn.ishop.server.dto.product.ProductUOM;
import com.toedter.calendar.JDateChooser;

public class ProductDetailManager extends AbstractGUIManager implements
		GUIActionListener, ListSelectionListener, TableModelListener,
		ProgressListener, CActionListener {
	CTextField txtItemSKU;
	CTextField txtItemName;
	CTextField txtItemStyle;
	CTextField txtItemOriginalCountry;
	CTextField txtItemProducedCompany;
	JDateChooser dateItemProducedated;
	JDateChooser dateItemExpiredDate;
	CTextArea atxtItemDescription;
	ImageLabel avataImg;

	static final String NULL_ID = "0";
	boolean isDirty = false;
	String productId = NULL_ID;
	CWhitePanel productDetailNavBar;
	public static final Logger logger = Logger
			.getLogger(ProductDetailManager.class);
	JBarcodeBean itemBarcode;
	CTextField txtItemBarcodeId;
	CComboBox comboItemUnitPrice;
	boolean userChangeComboValue = false;

	void init() {
		initTemplate(this, "admin/sanpham/productDetailPage.xml");
		render();
		bindEventHandlers();
	}

	public ProductDetailManager(String locale) {
		setLocale(locale);
		init();
	}

	@Override
	protected void applyStyles() {
		productDetailNavBar.setBorder(new CLineBorder(CConstants.TEXT_BORDER_COLOR,
				CLineBorder.DRAW_TOP_BORDER));
	}

	void updateCategoryCombo() {
	}

	@Override
	protected void bindEventHandlers() {
		itemBarcode.requestFocusInWindow();
		avataImg.addCActionListener(this);
		handleDocumentChangeEvent(txtItemSKU);		
		handleDocumentChangeEvent(txtItemName);
		handleDocumentChangeEvent(txtItemStyle);
		handleDocumentChangeEvent(txtItemOriginalCountry);
		handleDocumentChangeEvent(txtItemProducedCompany);
		handleDocumentChangeEvent(atxtItemDescription);	
		comboItemUnitPrice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				userChangeComboValue = true;
				super.mouseClicked(e);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				userChangeComboValue = true;
				super.mouseReleased(e);
			}
		});
		comboItemUnitPrice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(userChangeComboValue)
					isDirty = true;				
				userChangeComboValue = false;
			}
		});
	}
	/**
	 * Set listener for form fields dirty detection
	 * @param comp
	 */
	void handleDocumentChangeEvent(JComponent comp){
		if(comp instanceof JTextField){
			JTextField tf = (JTextField) comp;
			tf.getDocument().addDocumentListener(new DocumentListener() {
				public void removeUpdate(DocumentEvent e) {
					if(e.getOffset() >0)
						isDirty = true;			
				}
				
				public void insertUpdate(DocumentEvent e) {
					if(e.getOffset() >0)
						isDirty = true;				
				}
				
				public void changedUpdate(DocumentEvent e) {
				}
			});
		}else if(comp instanceof JTextArea){
			JTextArea tf = (JTextArea)comp;
			tf.getDocument().addDocumentListener(new DocumentListener() {
				public void removeUpdate(DocumentEvent e) {
					if(e.getOffset() >0)
						isDirty = true;			
				}
				
				public void insertUpdate(DocumentEvent e) {
					if(e.getOffset() >0)
						isDirty = true;				
				}
				
				public void changedUpdate(DocumentEvent e) {
				}
			});
		}
	}
	@Override
	public Object getData(String var) {
		return null;
	}

	public Action cmdItemSave = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			saveItem();
		}
	};
	

	/**
	 * Validate data on input form
	 * 
	 * @return
	 */
	boolean isValidData() {
		return ((txtItemName.getText() != null && (!txtItemName.getText()
				.trim().equals("")))
				&& (txtItemBarcodeId.getText() != null && (!txtItemBarcodeId
						.getText().trim().equals(""))) && (selectCat != null) && (!comboItemUnitPrice
				.getSelectedItemId().equals("-1")));
	}

	/**
	 * Saving new item
	 */
	void saveItem() {
		if (!isValidData()) {
			SystemMessageDialog.showMessageDialog(null, Language.getInstance()
					.getString("require.field.is.null"),
					SystemMessageDialog.SHOW_OK_OPTION);
			return;
		}

		try {
			int catId = 0, uom = 0/* , sku = 0 */;
			try {
				catId = selectCat.getCategoryId();/*
												 * Integer.parseInt(
												 * comboItemCategory
												 * .getSelectedItemId());
												 */
				uom = Integer.parseInt(comboItemUnitPrice.getSelectedItemId());
			} catch (Exception e) {
				e.printStackTrace();
			}

			byte[] avataBytes = Util
					.getImageAsByteArray(avataImg.getIconPath());

			ProductItem pcg = new ProductItem(txtItemBarcodeId.getText(),
					catId, txtItemName.getText(),
					txtItemOriginalCountry.getText(),
					txtItemProducedCompany.getText(),
					dateItemProducedated.getCalendar(),
					dateItemExpiredDate.getCalendar(),
					atxtItemDescription.getText(),
					/* itemPackingType */1, /* itemUnitPrice */0, /* itemUOM */
					uom, avataBytes);
			if (productId == NULL_ID) {//
				ProductLogic.setExtraEntityInfor(pcg, CAction.ADD);
				ProductLogic.getInstance().saveProduct(pcg, CAction.ADD);
			} else {
				pcg.setProductId(productId.trim());
				ProductLogic.setExtraEntityInfor(pcg, CAction.UPDATE);
				ProductLogic.getInstance().saveProduct(pcg, CAction.UPDATE);
			}
			fireCAction(new CActionEvent(this,
					CActionEvent.PRODUCT_CHANGE, pcg));
			newProduct();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(" save item error: " + e.getMessage());
		}
	}

	/**
	 * Check if the form is dirty
	 * 
	 * @return
	 */
	private boolean isDirty() {
		boolean ret = (isDirty == true);
		if (ret) {
			int res = SystemMessageDialog.showConfirmDialog(null, Language
					.getInstance().getString("form.dirty"),
					SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
			if (res == 0)
				return false;
		}
		return ret;
	}

	/**
	 * Clear all fields and ready for update new product
	 */
	void newProduct() {
		isDirty = false;
		productId = NULL_ID;
		resetForm();
	}

	/**
	 * Reset all form field to new instance
	 */
	void resetForm() {
		avataImg.initIcon();
		txtItemBarcodeId.setText("");
		// catId,
		txtItemName.setText("");
		txtItemOriginalCountry.setText("");
		txtItemProducedCompany.setText("");
		txtItemStyle.setText("");
		dateItemProducedated.setCalendar(null);
		dateItemExpiredDate.setCalendar(null);
		atxtItemDescription.setText("");
		isDirty = false;
	}

	/**
	 * Initialize a product from event before doing update
	 * 
	 * @param pi
	 */
	void initializeUpdateItem(ProductItem pi) {
		if (isDirty())
			return;
		isDirty = true;
		productId = pi.getProductId();

		resetForm();
		txtItemBarcodeId.setText(pi.getProductId());
		try {
			selectCat = ProductCatLogic.getInstance().getCatById(
					pi.getItemCategory());
			txtItemCategory.setText(selectCat.getCategoryName());
		} catch (Exception e) {
			e.printStackTrace();
			SystemMessageDialog.showMessageDialog(null, "ERROR: " + e.getMessage(),
					SystemMessageDialog.SHOW_OK_OPTION);
		}
		// catId,
		txtItemName.setText(pi.getProductName());
		txtItemOriginalCountry.setText(pi.getProductMadein());
		txtItemProducedCompany.setText(pi.getMade_by());
		dateItemProducedated.setCalendar(pi.getProductProducedDate());
		dateItemExpiredDate.setCalendar(pi.getProductExpiredDate());
		atxtItemDescription.setText(pi.getProductDescription());

		comboItemUnitPrice.setSelectedItemById(String.valueOf(pi.getItemUOM()));

		try {
			Util.setImageLabelIcon(this.avataImg, pi.getAvata());
		} catch (Exception e) {
			//- ignore
		}
	}

	public Action cmdItemPrint = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
		}
	};
	public Action cmdItemRollbackUpdate = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (isDirty())
				return;
			else
				newProduct();
		}
	};
	
	public Action cmdUpdateBarcode = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			itemBarcode.setCode(txtItemBarcodeId.getText());
		}
	};
	ProductCategory selectCat = null;
	CTextField txtItemCategory;
	public Action cmdChooseCatDialog = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			selectCat = ProductHelper.chooseCategory();
			if (selectCat != null)
				txtItemCategory.setText(selectCat.getCategoryName());
		}
	};

	protected void fireCAction(CActionEvent action) {
		for (CActionListener cActionListener : cActionListenerVector)
			cActionListener.cActionPerformed(action);
	}

	private Vector<CActionListener> cActionListenerVector = new Vector<CActionListener>();

	
	@SuppressWarnings("unchecked")
	public void cActionPerformed(CActionEvent evt) {
		if (evt.getData() == null)
			return;
		if (evt.getAction() == CActionEvent.UPDATE_COMBO_BOX) {
			List data = (List) evt.getData();
			if (data != null && data.size() > 0
					&& data.get(0) instanceof ProductUOM) {
				CommonLogic.updateCategory(data, comboItemUnitPrice);
			}
		} else if (evt.getAction() == CActionEvent.INIT_UPDATE_PRODUCT) {
			String prodId = (String) evt.getData();
			ProductItem pd = ProductLogic.getInstance().getItem(prodId);
			initializeUpdateItem(pd);
		} else if (evt.getAction() == CActionEvent.INIT_ADD_PRODUCT) {
			if (isDirty())
				return;
			else
				newProduct();
		} else if (evt.getAction() == CActionEvent.DELETE_PRODUCT) {
			// Just avoid loop event
		} else if (evt.getAction() == CActionEvent.UPDATE && evt.getSource() instanceof  ImageLabel) {
			// Notify from ImageLabel for image icon change			
			isDirty = true;
		} else {
			fireCAction(evt);
		}

	}

	public void addCActionListener(CActionListener al) {
		cActionListenerVector.add(al);

	}

	public void removeCActionListener(CActionListener al) {
		cActionListenerVector.remove(al);

	}

	@Override
	public void update() {
	}

	@Override
	public void updateList() {
	}

	public void guiActionPerformed(GUIActionEvent action) {
	}

	public void valueChanged(ListSelectionEvent e) {
	}

	public void tableChanged(TableModelEvent e) {
	}

	@Override
	protected void checkPermission() {
	}

	// -- PROGRESS LISTENER--//
	private Vector<ProgressListener> progressListenerVector = new Vector<ProgressListener>();

	public void addProgressListener(ProgressListener progressListener) {
		progressListenerVector.add(progressListener);
	}

	public void removeProgressListener(ProgressListener progressListener) {
		progressListenerVector.remove(progressListener);
	}

	protected void callProgressListener(ProgressEvent e) {
		for (ProgressListener progressListener : progressListenerVector)
			progressListener.progressStatusChanged(e);
	}

	public void progressStatusChanged(ProgressEvent evt) {
		callProgressListener(evt);
	}

}
