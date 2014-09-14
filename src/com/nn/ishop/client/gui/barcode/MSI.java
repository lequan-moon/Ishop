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
 * This class, which implements the {@link BarcodeStrategy} interface,
 * knows how to encode the MSI barcode type.
 */
public class MSI extends AbstractBarcodeStrategy implements java.io.Serializable {

  private static CharacterCode[] codes = {
    new CharacterCode('1', new byte[] {1,2,1,2,1,2,2,1}, 1),
    new CharacterCode('2', new byte[] {1,2,1,2,2,1,1,3}, 2),
    new CharacterCode('3', new byte[] {1,2,1,2,2,1,2,1}, 3),
    new CharacterCode('4', new byte[] {1,2,2,1,1,2,1,3}, 4),
    new CharacterCode('5', new byte[] {1,2,2,1,1,2,2,1}, 5),
    new CharacterCode('6', new byte[] {1,2,2,1,2,1,1,3}, 6),
    new CharacterCode('7', new byte[] {1,2,2,1,2,1,2,1}, 7),
    new CharacterCode('8', new byte[] {2,1,1,2,1,2,1,3}, 8),
    new CharacterCode('9', new byte[] {2,1,1,2,1,2,2,1}, 9),
    new CharacterCode('0', new byte[] {1,2,1,2,1,2,1,3}, 0),
    new CharacterCode('A', new byte[] {2,1}, -1),     // Start
    new CharacterCode('B', new byte[] {1,2,1}, -1)    // Stop
  };

  /**
   * Always returns {@link BarcodeStrategy#MANDATORY_CHECKSUM}.
   */
  public int requiresChecksum() {
    // Checksum is mandatory
    return MANDATORY_CHECKSUM;
  }

  /**
   * This implementation of <tt>getCodes</tt> returns an array of
   * {@link AbstractBarcodeStrategy.CharacterCode CharacterCode} objects
   * for the MSI format.
   */
  protected CharacterCode[] getCodes() {
    return MSI.codes;
  }

  /**
   * Returns a String containing the checksum-encoded version of the text passed
   * to the method.
   * Start and End sentinels must NOT be included in the text passed to this method.
   */
  protected String augmentWithChecksum(String text) throws BarcodeException {
    String newNum = "";
    for (int i = text.length() - 1; i >= 0; i -= 2 ) {
      newNum = text.charAt(i) + newNum;
    }
    int check1 = Integer.parseInt(newNum) * 2;
    newNum = new Integer(check1).toString();
    int check2 = 0;
    for (int i = 0; i < newNum.length(); i++) {
      check2 += Integer.parseInt(newNum.substring(i, i + 1));
    }
    for (int i = text.length() - 2; i >= 0; i -= 2 ) {
      check2 += Integer.parseInt(text.substring(i, i + 1));
    }
    int checkDigit = (10 - ((check2) % 10)) % 10;
    text = text + new Integer(checkDigit).toString();
    return text;
  }

  /**
   * Does nothing except return the String passed to the method.
   */
  protected String postprocess(String text) {
    return text;
  }

  /**
   * Does nothing except return the String passed to the method.
   */
  protected String preprocess(String text) {
    return text;
  }

  /**
   * Always returns <tt>false</tt>
   */
  protected boolean isInterleaved() {
    return false;
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
   * Does nothing except return the String passed to the method.
   */
  protected String getBarcodeLabelText(String text) {
    return text;
  }

}
