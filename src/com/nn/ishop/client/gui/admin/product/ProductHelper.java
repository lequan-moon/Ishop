package com.nn.ishop.client.gui.admin.product;

import com.nn.ishop.client.gui.dialogs.CategoryChooserDialog;
import com.nn.ishop.server.dto.product.ProductCategory;

/**
 * This class provide methods to help solving client side operation related to product
 * @author nguyennghia
 *
 */
public class ProductHelper {
	public static ProductCategory chooseCategory(){
		CategoryChooserDialog d = new CategoryChooserDialog(null, true, "", 
				"", "");
		d.setVisible(true);
		ProductCategory selectCat = d.getReturnValue();
		return selectCat;
	}
}
