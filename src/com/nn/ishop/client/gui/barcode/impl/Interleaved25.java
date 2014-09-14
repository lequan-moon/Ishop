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
package com.nn.ishop.client.gui.barcode.impl;

import com.nn.ishop.client.gui.barcode.AbstractBarcodeStrategy;
import com.nn.ishop.client.gui.barcode.BarcodeException;
import com.nn.ishop.client.gui.barcode.BarcodeStrategy;
import com.nn.ishop.client.gui.barcode.AbstractBarcodeStrategy.CharacterCode;

/**
 * This class, which implements the {@link BarcodeStrategy} interface,
 * knows how to encode the 3:1 (wide)
 * variant of the Interleaved Code 2 of 5 barcode type.
 */
public class Interleaved25 extends AbstractBarcodeStrategy implements java.io.Serializable {

  private static CharacterCode[] codes = {
    new CharacterCode('1', new byte[] {3,1,1,1,3}, 1),
    new CharacterCode('2', new byte[] {1,3,1,1,3}, 2),
    new CharacterCode('3', new byte[] {3,3,1,1,1}, 3),
    new CharacterCode('4', new byte[] {1,1,3,1,3}, 4),
    new CharacterCode('5', new byte[] {3,1,3,1,1}, 5),
    new CharacterCode('6', new byte[] {1,3,3,1,1}, 6),
    new CharacterCode('7', new byte[] {1,1,1,3,3}, 7),
    new CharacterCode('8', new byte[] {3,1,1,3,1}, 8),
    new CharacterCode('9', new byte[] {1,3,1,3,1}, 9),
    new CharacterCode('0', new byte[] {1,1,3,3,1}, 0),
    new CharacterCode('A', new byte[] {1,1,1,1}, -1),   // Start
    new CharacterCode('B', new byte[] {3,1,1}, -1)      // Stop
  };

  /**
   * Always returns {@link BarcodeStrategy#OPTIONAL_CHECKSUM}.
   */
  public int requiresChecksum() {
    // Checksum is not mandatory
    return OPTIONAL_CHECKSUM;
  }

  /**
   * This implementation of <tt>getCodes</tt> returns an array of
   * {@link AbstractBarcodeStrategy.CharacterCode CharacterCode} objects
   * for the wide Interleaved Code25 format.
   */
  protected CharacterCode[] getCodes() {
    return Interleaved25.codes;
  }

  /**
   * Returns a String containing the checksum-encoded version of the text passed
   * to the method.
   * Start and End sentinels must NOT be included in the text passed to this method.
   */
  protected String augmentWithChecksum(String text) throws BarcodeException {
    int check1 = 0;
    int check2 = 0;
    CharacterCode cc;
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      cc = getCharacterCode(ch);            // get code by character
      if (cc == null) {
        throw new BarcodeException("Invalid character in barcode");
      }
      // Exclude start and end sentinels from checksum
      if (cc.check > 0) {
        if ((i + text.length()) % 2 == 0) {
          check1 += cc.check;
        } else {
          check2 += cc.check;
        }
      }
    }
    check2 *= 3;
    int checkDigit = (10 - ((check1 + check2) % 10)) % 10;
    text = text + new Integer(checkDigit).toString();
    return text;
  }

  /**
   * Adds a leading zero if the length of <tt>text</tt> is odd.
   */
  protected String postprocess(String text) {
    if (text.length() % 2 != 0) {
      // Length is odd; add a leading zero.
      text = "0" + text;
    }
    return text;
  }

  /**
   * No preprocessing performed. <tt>text</tt> is returned unmodified.
   */
  protected String preprocess(String text) {
    return text;
  }

  /**
   * Always returns <tt>true</tt>.
   */
  protected boolean isInterleaved() {
    return true;
  }

  /**
   * Always returns 'A'.
   */
  protected char getStartSentinel() {
    return 'A';
  }

  /**
   * Always returns 'B'.
   */
  protected char getStopSentinel() {
    return 'B';
  }

  /**
   * Always returns 11 (eleven).
   */
  protected byte getMarginWidth() {
    return 11;
  }

  /**
   * Returns <tt>text</tt> unmodified.
   */
  protected String getBarcodeLabelText(String text) {
    return text;
  }

}
