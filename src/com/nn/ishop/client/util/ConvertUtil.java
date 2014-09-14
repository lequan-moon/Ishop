package com.nn.ishop.client.util;

import java.text.ParseException;

import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.components.CTextField;

public class ConvertUtil {
	public static Number convertCTextToNumber(CTextField comp) throws Exception{
		double ret = 0.0d;
		ret = (comp!=null && comp.getText() != null && !comp.getText().equals("")) ? SystemConfiguration.decfm
				.parse(comp.getText()).doubleValue()
				: 0.0d;
		return ret;
	}
	public static Number convertCTextToNumber(String numStr){
		double ret = 0.0d;
		try {
			ret = (numStr!=null && !numStr.equals("")) ? SystemConfiguration.decfm
					.parse(numStr).doubleValue()
					: 0.0d;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
