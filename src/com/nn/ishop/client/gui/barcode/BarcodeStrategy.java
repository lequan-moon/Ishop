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
 * Interface which defines the barcode strategy for any
 * given type of barcode.  Classes that implement this interface exist for each
 * of the barcode types such as Code39, Interleaved25, etc.
 */
public interface BarcodeStrategy {

  /**
   * When returned by {@link #requiresChecksum}, indicates that this type of
   * barcode does not support a checksum.
   */
  public static final int NO_CHECKSUM = 0;

  /**
   * When returned by {@link #requiresChecksum}, indicates that this type of
   * barcode always has a checksum.
   */
  public static final int MANDATORY_CHECKSUM = 1;

  /**
   * When returned by {@link #requiresChecksum}, indicates that this type of
   * barcode may have an optional checksum.
   */
  public static final int OPTIONAL_CHECKSUM = 2;

  /**
   * Subclasses implement this method to encode some text into a barcode.
   *
   * @param text The raw text to encode.
   * @param checked <tt>true</tt> if a checksum is to be calculated, <tt>false</tt> if not.
   *
   * @return The fully encoded barcode, represented as bars and spaces, wrapped
   * in a {@link EncodedBarcode} object.
   *
   * @throws BarcodeException Typically caused by passing in
   * a String containing illegal characters (characters that cannot be encoded in
   * this type of barcode).
   */
  public EncodedBarcode encode(String text, boolean checked) throws BarcodeException;

  /**
   * Subclasses implement this method to determine whether this type of barcode
   * has a mandatory, optional or no checksum.  Returns one of the constants
   * defined in the interface.
   */
  public int requiresChecksum();
}
