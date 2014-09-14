package com.nn.ishop.client.gui.admin.company;

import com.nn.ishop.client.gui.dialogs.CompanyChooserDialog;
import com.nn.ishop.server.dto.company.Company;

public class CompanyHelper {

	public static Company chooseCompany() {
		CompanyChooserDialog d = new CompanyChooserDialog(null, true, "", 
				"", "");
		d.setVisible(true);
		Company selectedCompany = d.getReturnValue();
		return selectedCompany;
	}
}
