/*****************************************************************************/
/* File Description  : CommonUtil                                            */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright (c) 2004-2007 by shiftTHINK Ltd. Liab. Co.  */
/* Company Name      : shiftTHINK Ltd. Liab. Co.                             */
/* Original Filename : CommonUtil.java                                       */
/* Product Version   : 1.0                                                   */
/* Product Name      : CONNECT Project 							             */
/*****************************************************************************/

package com.nn.ishop.client.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CommonUtil 
{
	/** Maximum of entities in each export file */
	public static final int EXPORT_MAX_LIMIT_COUNT = 1000;
	
	/** Minimum of entities in each export file */
	public static final int EXPORT_MIN_LIMIT_COUNT = 0;
	
	/** Variables involved in checking*/
	private static String strPattern;
	private static Pattern ptnCheck;
	private static Matcher mchMatch;
	private static boolean flag;
	
	/**
	 * Check the limitCount range
	 * from 0 to 1000
	 * @param limitCount Limit count of entities
	 * @return True if limit's in the range
	 */
	public static boolean isValidExportLimitCount(int limitCount)
	{
		if(limitCount >= EXPORT_MIN_LIMIT_COUNT && 
				limitCount <= EXPORT_MAX_LIMIT_COUNT)
			return true;
		else
			return false;
	}
	
	/**
	 * Check if input value is string 
	 * @return flag A boolean value indicates whether input parameter is string
	 */
	public static boolean isString(String input)
	{
		strPattern = "^([0-9])+$";
        ptnCheck = Pattern.compile(strPattern);
        mchMatch = ptnCheck.matcher(input);
        flag = (mchMatch.find());
		/*
		strPattern = "^([a-zA-Z]+\\s?[a-zA-Z]+)+$";
        ptnCheck = Pattern.compile(strPattern);
        mchMatch = ptnCheck.matcher(input);
        flag = (mchMatch.find());
        */
		return !flag;
	}
	
	/**
	 * Check if input value is number
	 * @return flag A boolean value indicates whether input parameter is number
	 */
	public static boolean isNumber(String input)
	{
		strPattern = "^\\d+$";
		ptnCheck = Pattern.compile(strPattern);
        mchMatch = ptnCheck.matcher(input);
        flag = (mchMatch.find());
		return flag;
	}
	
	/**
	 * Check if input value is email
	 * @return flag A boolean value indicates whether input parameter is email address
	 */
	public static boolean isMail(String input)
	{
		strPattern = "^\\S+@\\S+$";
		ptnCheck = Pattern.compile(strPattern);
        mchMatch = ptnCheck.matcher(input);
        flag = (mchMatch.find());
		return flag;
	}
	
	/**
	 * Checks if the file path is valid or not
	 * @param path The xml document path to import
	 * @return True if the path is valid and vice versa
	 */
	public static boolean isValidPath(String path)
	{
		File f = new File(path);
		if(f.isFile())
			return true;
		
		return false;
	}
	
	/**
	 * Checks if the selected file is xml document or not
	 * @param path The xml document path to import
	 * @return True if the file is an xml document and vice versa
	 */
	public static boolean isXMLDocument(String path)
	{
		int pos = path.lastIndexOf(".");
		int length = path.length();
		String extension = path.substring(pos, length);
		if(extension.toLowerCase().equals(".xml"))
			return true;
		
		return false;
	}
}
