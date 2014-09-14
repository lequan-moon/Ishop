package com.nn.ishop.client.validator;

import java.awt.Color;
import java.text.ParseException;

import javax.swing.JPasswordField;
import javax.swing.JTable;

import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CTable;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.util.CommonUtil;
import com.nn.ishop.client.util.ConvertUtil;
import com.nn.ishop.server.dto.sale.SaleOrderDetail;
import com.toedter.calendar.JDateChooser;

public class Validator {

	/**
	 * Validate max size
	 * 
	 * @param target
	 * @param maxSize
	 * @return isValid
	 */
	public static boolean validateMaxSize(CTextField targetControl, int maxSize, Color oriBackground, String errorMessage) {
		boolean isValid = false;
		String target = targetControl.getText();
		if (target != null && maxSize != 0) {
			char[] charArray = target.toCharArray();
			if (charArray.length > maxSize) {
				isValid = true;
				targetControl.setBackground(oriBackground);
			} else {
				isValid = false;
				targetControl.setBackground(SystemConfiguration.DEFAULT_ERROR_TEXT);
				SystemMessageDialog.showMessageDialog(null,errorMessage + Language.getInstance().getString("error.validateMaxSize") + maxSize,
						SystemMessageDialog.SHOW_OK_OPTION);
			}
		}
		return isValid;
	}

	/**
	 * Validate minsize
	 * 
	 * @param target
	 * @param minSize
	 * @return isValid
	 */
	public static boolean validateMinSize(CTextField targetControl, int minSize, Color oriBackground, String errorMessage) {
		boolean isValid = false;
		String target = targetControl.getText();
		if (target != null && minSize != 0) {
			char[] charArray = target.toCharArray();
			if (charArray.length < minSize) {
				isValid = true;
				targetControl.setBackground(Color.WHITE);
			} else {
				isValid = false;
				targetControl.setBackground(SystemConfiguration.DEFAULT_ERROR_TEXT);
				SystemMessageDialog.showMessageDialog(null, errorMessage + Language.getInstance().getString("error.validateMinSize") + minSize,
						SystemMessageDialog.SHOW_OK_OPTION);
			}
		}
		return isValid;
	}

	/**
	 * Validate number
	 * 
	 * @param target
	 * @param datatype
	 *            Integer/Double/Long
	 * @return isValid
	 */
	public static boolean validateNumber(CTextField targetControl, Class<?> datatype, Color oriBackground, String errorMessage) {
		boolean isValid = false;
		String target = targetControl.getText();		
		if(target == null || target.equals(""))
			return false;
		try {
			Number num = ConvertUtil.convertCTextToNumber(targetControl);
			if (/*target != null &&*/ datatype != null) {
				String type = datatype.getName();
				if (Integer.class.getName().equals(type)) {
					int value = num.intValue();//Integer.parseInt(target);
					isValid = true;
					targetControl.setBackground(oriBackground);
				} else if (Long.class.getName().equals(type)) {
					long value = num.longValue();//Long.parseLong(target);
					isValid = true;
					targetControl.setBackground(oriBackground);
				} else if (Double.class.getName().equals(type)) {
					double value = num.doubleValue();//Double.parseDouble(target);
					isValid = true;
					targetControl.setBackground(oriBackground);
				}
			}
		} catch (NumberFormatException ex) {
			isValid = false;
			targetControl.setBackground(SystemConfiguration.DEFAULT_ERROR_TEXT);
			SystemMessageDialog.showMessageDialog(null, errorMessage + Language.getInstance().getString("error.validateNumber"), SystemMessageDialog.SHOW_OK_OPTION);
		} catch (Exception pex){
			isValid = false;
			targetControl.setBackground(SystemConfiguration.DEFAULT_ERROR_TEXT);
			SystemMessageDialog.showMessageDialog(null, errorMessage + Language.getInstance().getString("error.validateNumber"), SystemMessageDialog.SHOW_OK_OPTION);
		}
		return isValid;
	}
	public static boolean validateNumber(String target, Class<?> datatype, String errorMessage) {
		boolean isValid = false;
		if(target == null || target.equals("")){
			SystemMessageDialog.showMessageDialog(null, errorMessage + Language.getInstance().getString("error.validateNumber"), SystemMessageDialog.SHOW_OK_OPTION);
			return false;
		}
			
		try {
			Number num = ConvertUtil.convertCTextToNumber(target);
			if (/*target != null &&*/ datatype != null) {
				String type = datatype.getName();
				if (Integer.class.getName().equals(type)) {
					int value = num.intValue();//Integer.parseInt(target);
					isValid = true;
				} else if (Long.class.getName().equals(type)) {
					long value = num.longValue();//Long.parseLong(target);
					isValid = true;
				} else if (Double.class.getName().equals(type)) {
					double value = num.doubleValue();//Double.parseDouble(target);
					isValid = true;
				}
			}
		} catch (NumberFormatException ex) {
			isValid = false;
			SystemMessageDialog.showMessageDialog(null, errorMessage + Language.getInstance().getString("error.validateNumber"), SystemMessageDialog.SHOW_OK_OPTION);
		}
		return isValid;
	}

	/**
	 * validate empty string
	 * 
	 * @param target
	 * @return
	 */
	public static boolean validateEmpty(CTextField targetControl, Color oriBackground, String errorMessage) {
		boolean isValid = false;
		String target = targetControl.getText();
		if (!"".equals(target)) {
			isValid = true;
			targetControl.setBackground(oriBackground);
		} else {
			isValid = false;
			targetControl.setBackground(SystemConfiguration.DEFAULT_ERROR_TEXT);
			SystemMessageDialog.showMessageDialog(null, errorMessage + Language.getInstance().getString("error.validateEmpty"), SystemMessageDialog.SHOW_OK_OPTION);
		}
		return isValid;
	}
	
	/**
	 * validate empty string for password control
	 * 
	 * @param target
	 * @return
	 */
	public static boolean validateEmptyPassword(JPasswordField targetControl, Color oriBackground, String errorMessage) {
		boolean isValid = false;
		String target = String.valueOf(targetControl.getPassword());
		if (!"".equals(target)) {
			isValid = true;
			targetControl.setBackground(oriBackground);
		} else {
			isValid = false;
			targetControl.setBackground(SystemConfiguration.DEFAULT_ERROR_TEXT);
			SystemMessageDialog.showMessageDialog(null, errorMessage + Language.getInstance().getString("error.validateEmpty"), SystemMessageDialog.SHOW_OK_OPTION);
		}
		return isValid;
	}

	/**
	 * validate email format
	 * 
	 * @param target
	 * @return isValid
	 */
	public static boolean validateEmailFormat(CTextField targetControl, Color oriBackground, String errorMessage) {
		boolean isValid = false;
		String target = targetControl.getText();
		if(target != null && !"".equals(target)){
			String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			if (target.matches(EMAIL_PATTERN)) {
				isValid = true;
				targetControl.setBackground(oriBackground);
			} else {
				isValid = false;
				targetControl.setBackground(SystemConfiguration.DEFAULT_ERROR_TEXT);
				SystemMessageDialog.showMessageDialog(null, errorMessage+ Language.getInstance().getString("error.validateEmailFormat"),
						SystemMessageDialog.SHOW_OK_OPTION);
			}
		}else{
			isValid = false;// null
		}
		return isValid;
	}

	/**
	 * validate range from minSize to maxSize
	 * 
	 * @param target
	 * @param minSize
	 * @param maxSize
	 * @return isValid
	 */
	public static boolean validateRange(CTextField targetControl, int minSize, int maxSize, Color oriBackground, String errorMessage) {
		boolean isValid = false;
		if (!"".equals(targetControl.getText())) {
			isValid = validateMinSize(targetControl, minSize, oriBackground,errorMessage) 
			&& validateMaxSize(targetControl, maxSize, oriBackground,errorMessage);
		} else {
			isValid = false;
			targetControl.setBackground(SystemConfiguration.DEFAULT_ERROR_TEXT);
			SystemMessageDialog.showMessageDialog(null, errorMessage 
					+ Language.getInstance().getString("error.validateRange") + minSize + " " + maxSize,
					SystemMessageDialog.SHOW_OK_OPTION);
		}
		return isValid;
	}

	/**
	 * validate String is equivalent to expression
	 * 
	 * @param target
	 * @param expression
	 * @return
	 */
	public static boolean validateRegularExpression(CTextField targetControl, String expression, Color oriBackground) {
		boolean isValid = false;
		String target = targetControl.getText();
		if (target.matches(expression)) {
			isValid = true;
			targetControl.setBackground(oriBackground);
		} else {
			isValid = false;
			targetControl.setBackground(SystemConfiguration.DEFAULT_ERROR_TEXT);
			SystemMessageDialog.showMessageDialog(null,  Language.getInstance().getString("error.validateRegularExpression" + expression),
					SystemMessageDialog.SHOW_OK_OPTION);
		}
		return isValid;
	}

	public static boolean validateSelectedComboBox(CComboBox targetControl, Color oriBackground, String errorMessage){
		boolean isValid = false;
		String target = targetControl.getSelectedItemId();
		if (target != null && !"-1".equals(target)) {
			isValid = true;
			targetControl.setBackground(oriBackground);
		} else {
			isValid = false;
			targetControl.setBackground(SystemConfiguration.DEFAULT_ERROR_TEXT);
			SystemMessageDialog.showMessageDialog(null, errorMessage+Language.getInstance().getString("error.validateSelectedComboBox") + errorMessage ,
					SystemMessageDialog.SHOW_OK_OPTION);
		}
		return isValid;
	}
	
	public static boolean validateDateChooser(JDateChooser target, String errorMessage){
		boolean ret = false;
		if(target!= null && target.getDate() != null){
			ret = true;
		}else{
			ret = false;
			SystemMessageDialog.showMessageDialog(null, errorMessage 
					+ Language.getInstance().getString("error.inputDate") + errorMessage ,
					SystemMessageDialog.SHOW_OK_OPTION);
		}
		return ret;
	}
	
	public static boolean validateTableNotNull(JTable target, String errorMessage){
		boolean ret = false;
		if(target.getModel() != null && target.getModel().getRowCount()>0){
			ret = true;
		}else{
			ret = false;
			SystemMessageDialog.showMessageDialog(null, errorMessage 
					+ Language.getInstance().getString("error.tableIsNull") + errorMessage ,
					SystemMessageDialog.SHOW_OK_OPTION);
		}
		return ret;
	}
	public static boolean validateSOContainProduct(CTable table, int idColumn, SaleOrderDetail soDt){
		boolean ret = false;
		CTableModel model = (CTableModel)table.getModel();
		String itemId = soDt.getItem_id();
		for(int i=0;i<model.getRowCount();i++){
			String itemInTable = (String)model.getValueAt(table.convertRowIndexToModel(i), idColumn);
			ret |= (itemInTable != null && itemInTable.trim().equalsIgnoreCase(itemId.trim()));  
		}
		return ret;
	}
	public static boolean validateObjectNotNull(Object target, String errorMessage){
		boolean ret = false;		
		if(target != null && !target.toString().equals("")){
			ret = true;
		}else{
			ret = false;
			SystemMessageDialog.showMessageDialog(null,
					Language.getInstance().getString("error.object.is.null") + errorMessage ,
					SystemMessageDialog.SHOW_OK_OPTION);
		}
		return ret;
	}
	public static boolean validateFilePath(String filePath, String errorMessage){
		boolean ret = false;
		ret = CommonUtil.isValidPath(filePath);
		if(!ret)
			SystemMessageDialog.showMessageDialog(null, 
					errorMessage, 
					SystemMessageDialog.SHOW_OK_OPTION);
		return ret;
	}

}
