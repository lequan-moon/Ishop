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
 * Base class for Code39 variants.
 */
public abstract class BaseCode39 extends AbstractBarcodeStrategy {

  /**
   * A static array of
   * {@link AbstractBarcodeStrategy.CharacterCode CharacterCode} objects
   * for the 3:1 (wide) variants of Code 39.  The
   * {@link AbstractBarcodeStrategy#getCodes() getCodes()} method of the wide
   * Code 39 subclasses ({@link Code39} and {@link ExtendedCode39})
   * return this array.
   */
  protected static CharacterCode[] codes = {
    new CharacterCode('0', new byte[] {1,1,1,3,3,1,3,1,1,1}, 0),
    new CharacterCode('1', new byte[] {3,1,1,3,1,1,1,1,3,1}, 1),
    new CharacterCode('2', new byte[] {1,1,3,3,1,1,1,1,3,1}, 2),
    new CharacterCode('3', new byte[] {3,1,3,3,1,1,1,1,1,1}, 3),
    new CharacterCode('4', new byte[] {1,1,1,3,3,1,1,1,3,1}, 4),
    new CharacterCode('5', new byte[] {3,1,1,3,3,1,1,1,1,1}, 5),
    new CharacterCode('6', new byte[] {1,1,3,3,3,1,1,1,1,1}, 6),
    new CharacterCode('7', new byte[] {1,1,1,3,1,1,3,1,3,1}, 7),
    new CharacterCode('8', new byte[] {3,1,1,3,1,1,3,1,1,1}, 8),
    new CharacterCode('9', new byte[] {1,1,3,3,1,1,3,1,1,1}, 9),
    new CharacterCode('A', new byte[] {3,1,1,1,1,3,1,1,3,1}, 10),
    new CharacterCode('B', new byte[] {1,1,3,1,1,3,1,1,3,1}, 11),
    new CharacterCode('C', new byte[] {3,1,3,1,1,3,1,1,1,1}, 12),
    new CharacterCode('D', new byte[] {1,1,1,1,3,3,1,1,3,1}, 13),
    new CharacterCode('E', new byte[] {3,1,1,1,3,3,1,1,1,1}, 14),
    new CharacterCode('F', new byte[] {1,1,3,1,3,3,1,1,1,1}, 15),
    new CharacterCode('G', new byte[] {1,1,1,1,1,3,3,1,3,1}, 16),
    new CharacterCode('H', new byte[] {3,1,1,1,1,3,3,1,1,1}, 17),
    new CharacterCode('I', new byte[] {1,1,3,1,1,3,3,1,1,1}, 18),
    new CharacterCode('J', new byte[] {1,1,1,1,3,3,3,1,1,1}, 19),
    new CharacterCode('K', new byte[] {3,1,1,1,1,1,1,3,3,1}, 20),
    new CharacterCode('L', new byte[] {1,1,3,1,1,1,1,3,3,1}, 21),
    new CharacterCode('M', new byte[] {3,1,3,1,1,1,1,3,1,1}, 22),
    new CharacterCode('N', new byte[] {1,1,1,1,3,1,1,3,3,1}, 23),
    new CharacterCode('O', new byte[] {3,1,1,1,3,1,1,3,1,1}, 24),
    new CharacterCode('P', new byte[] {1,1,3,1,3,1,1,3,1,1}, 25),
    new CharacterCode('Q', new byte[] {1,1,1,1,1,1,3,3,3,1}, 26),
    new CharacterCode('R', new byte[] {3,1,1,1,1,1,3,3,1,1}, 27),
    new CharacterCode('S', new byte[] {1,1,3,1,1,1,3,3,1,1}, 28),
    new CharacterCode('T', new byte[] {1,1,1,1,3,1,3,3,1,1}, 29),
    new CharacterCode('U', new byte[] {3,3,1,1,1,1,1,1,3,1}, 30),
    new CharacterCode('V', new byte[] {1,3,3,1,1,1,1,1,3,1}, 31),
    new CharacterCode('W', new byte[] {3,3,3,1,1,1,1,1,1,1}, 32),
    new CharacterCode('X', new byte[] {1,3,1,1,3,1,1,1,3,1}, 33),
    new CharacterCode('Y', new byte[] {3,3,1,1,3,1,1,1,1,1}, 34),
    new CharacterCode('Z', new byte[] {1,3,3,1,3,1,1,1,1,1}, 35),
    new CharacterCode('-', new byte[] {1,3,1,1,1,1,3,1,3,1}, 36),
    new CharacterCode('.', new byte[] {3,3,1,1,1,1,3,1,1,1}, 37),
    new CharacterCode(' ', new byte[] {1,3,3,1,1,1,3,1,1,1}, 38),
    new CharacterCode('*', new byte[] {1,3,1,1,3,1,3,1,1,1}, -1), // Start and End Sentinel
    new CharacterCode('$', new byte[] {1,3,1,3,1,3,1,1,1,1}, 39),
    new CharacterCode('/', new byte[] {1,3,1,3,1,1,1,3,1,1}, 40),
    new CharacterCode('+', new byte[] {1,3,1,1,1,3,1,3,1,1}, 41),
    new CharacterCode('%', new byte[] {1,1,1,3,1,3,1,3,1,1}, 42)
  };

  /**
   * A static array of
   * {@link AbstractBarcodeStrategy.CharacterCode CharacterCode} objects
   * for the 2:1 (narrow) variants of Code 39.  The
   * {@link AbstractBarcodeStrategy#getCodes() getCodes()} method of the narrow
   * Code 39 subclasses ({@link Code39_2to1} and {@link ExtendedCode39_2to1})
   * return this array.
   */
  protected static CharacterCode[] codes2to1 = {
    new CharacterCode('0', new byte[] {1,1,1,2,2,1,2,1,1,1}, 0),
    new CharacterCode('1', new byte[] {2,1,1,2,1,1,1,1,2,1}, 1),
    new CharacterCode('2', new byte[] {1,1,2,2,1,1,1,1,2,1}, 2),
    new CharacterCode('3', new byte[] {2,1,2,2,1,1,1,1,1,1}, 3),
    new CharacterCode('4', new byte[] {1,1,1,2,2,1,1,1,2,1}, 4),
    new CharacterCode('5', new byte[] {2,1,1,2,2,1,1,1,1,1}, 5),
    new CharacterCode('6', new byte[] {1,1,2,2,2,1,1,1,1,1}, 6),
    new CharacterCode('7', new byte[] {1,1,1,2,1,1,2,1,2,1}, 7),
    new CharacterCode('8', new byte[] {2,1,1,2,1,1,2,1,1,1}, 8),
    new CharacterCode('9', new byte[] {1,1,2,2,1,1,2,1,1,1}, 9),
    new CharacterCode('A', new byte[] {2,1,1,1,1,2,1,1,2,1}, 10),
    new CharacterCode('B', new byte[] {1,1,2,1,1,2,1,1,2,1}, 11),
    new CharacterCode('C', new byte[] {2,1,2,1,1,2,1,1,1,1}, 12),
    new CharacterCode('D', new byte[] {1,1,1,1,2,2,1,1,2,1}, 13),
    new CharacterCode('E', new byte[] {2,1,1,1,2,2,1,1,1,1}, 14),
    new CharacterCode('F', new byte[] {1,1,2,1,2,2,1,1,1,1}, 15),
    new CharacterCode('G', new byte[] {1,1,1,1,1,2,2,1,2,1}, 16),
    new CharacterCode('H', new byte[] {2,1,1,1,1,2,2,1,1,1}, 17),
    new CharacterCode('I', new byte[] {1,1,2,1,1,2,2,1,1,1}, 18),
    new CharacterCode('J', new byte[] {1,1,1,1,2,2,2,1,1,1}, 19),
    new CharacterCode('K', new byte[] {2,1,1,1,1,1,1,2,2,1}, 20),
    new CharacterCode('L', new byte[] {1,1,2,1,1,1,1,2,2,1}, 21),
    new CharacterCode('M', new byte[] {2,1,2,1,1,1,1,2,1,1}, 22),
    new CharacterCode('N', new byte[] {1,1,1,1,2,1,1,2,2,1}, 23),
    new CharacterCode('O', new byte[] {2,1,1,1,2,1,1,2,1,1}, 24),
    new CharacterCode('P', new byte[] {1,1,2,1,2,1,1,2,1,1}, 25),
    new CharacterCode('Q', new byte[] {1,1,1,1,1,1,2,2,2,1}, 26),
    new CharacterCode('R', new byte[] {2,1,1,1,1,1,2,2,1,1}, 27),
    new CharacterCode('S', new byte[] {1,1,2,1,1,1,2,2,1,1}, 28),
    new CharacterCode('T', new byte[] {1,1,1,1,2,1,2,2,1,1}, 29),
    new CharacterCode('U', new byte[] {2,2,1,1,1,1,1,1,2,1}, 30),
    new CharacterCode('V', new byte[] {1,2,2,1,1,1,1,1,2,1}, 31),
    new CharacterCode('W', new byte[] {2,2,2,1,1,1,1,1,1,1}, 32),
    new CharacterCode('X', new byte[] {1,2,1,1,2,1,1,1,2,1}, 33),
    new CharacterCode('Y', new byte[] {2,2,1,1,2,1,1,1,1,1}, 34),
    new CharacterCode('Z', new byte[] {1,2,2,1,2,1,1,1,1,1}, 35),
    new CharacterCode('-', new byte[] {1,2,1,1,1,1,2,1,2,1}, 36),
    new CharacterCode('.', new byte[] {2,2,1,1,1,1,2,1,1,1}, 37),
    new CharacterCode(' ', new byte[] {1,2,2,1,1,1,2,1,1,1}, 38),
    new CharacterCode('*', new byte[] {1,2,1,1,2,1,2,1,1,1}, -1), // Start and End Sentinel
    new CharacterCode('$', new byte[] {1,2,1,2,1,2,1,1,1,1}, 39),
    new CharacterCode('/', new byte[] {1,2,1,2,1,1,1,2,1,1}, 40),
    new CharacterCode('+', new byte[] {1,2,1,1,1,2,1,2,1,1}, 41),
    new CharacterCode('%', new byte[] {1,1,1,2,1,2,1,2,1,1}, 42)
  };

  /**
   * Always returns {@link BarcodeStrategy#OPTIONAL_CHECKSUM}.
   */
  public int requiresChecksum() {
    // Checksum is not mandatory
    return OPTIONAL_CHECKSUM;
  }

  /**
   * Always returns 11 (eleven).
   */
  protected byte getMarginWidth() {
    return 11;
  }

  /**
   * Always returns '*' (asterisk).
   */
  protected char getStartSentinel() {
    return '*';
  }

  /**
   * Always returns '*' (asterisk).
   */
  protected char getStopSentinel() {
    return '*';
  }

  /**
   * Always returns <tt>false</tt>.
   */
  protected boolean isInterleaved() {
    return false;
  }

  /**
   * Does nothing except return the String passed to the method.
   */
  protected String postprocess(String text) {
    return text;
  }

  /**
   * Returns a String containing the checksum-encoded version of the text passed
   * to the method.
   * Start and End sentinels must NOT be included in the text passed to this method.
   */
  protected String augmentWithChecksum(String text) throws BarcodeException {
    int checkTotal = 0;
    CharacterCode cc;
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      cc = getCharacterCode(ch);            // get code by character
      if (cc == null) {
        throw new BarcodeException("Invalid character in barcode");
      }
      // Exclude start and end sentinels from checksum
      if (cc.check > 0) {
        checkTotal += cc.check;
      }
    }
    cc = getCharacterCode(checkTotal % 43); // get code by check number
    return text + cc.character;
  }


}
