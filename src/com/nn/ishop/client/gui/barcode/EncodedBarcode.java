/**
 *  This library is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License (LGPL) as
 *  published by the Free Software Foundation; either version 3.0 of the
 *  License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY of FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details. 
 */

/**
 * Title:        JBarcodeBean
 * Description:  Barcode JavaBeans Component
 * Copyright:    Copyright (C) 2004
 * Company:      Dafydd Walters
 */
package com.nn.ishop.client.gui.barcode;

/**
 * This class represents the bars, spaces and caption (text) that make up a
 * fully encoded barcode.  The {@link BarcodeStrategy#encode encode} method
 * of {@link BarcodeStrategy} returns an instance of this class.
 */
public class EncodedBarcode implements java.io.Serializable {

  /** The bars and spaces in the barcode */
  public BarcodeElement[] elements;
  /** The text caption that is displayed underneath the barcode */
  public String barcodeLabelText;
  public String barcodeItemName;

  /** Initializing constructor */
  public EncodedBarcode(BarcodeElement[] elements, 
		  String barcodeLabelText) {
    this.elements = elements;
    this.barcodeLabelText = barcodeLabelText;
  }
}
