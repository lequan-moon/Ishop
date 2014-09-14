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
 * Code 128 barcode strategy implementation.  This format can encode the
 * full 128 character ASCII character set (from 0 to 127 decimal), plus
 * four special Code 128 function codes.  These four codes are defined
 * as constant class members for convenience: FNC_1 through FNC_4.
 */
public class Code128 extends AbstractBarcodeStrategy {

  /** Code 128 FUNCTION CODE 1 */
  public static final char FNC_1 = '\u0080';
  /** Code 128 FUNCTION CODE 2 */
  public static final char FNC_2 = '\u0081';
  /** Code 128 FUNCTION CODE 3 */
  public static final char FNC_3 = '\u0082';
  /** Code 128 FUNCTION CODE 4 */
  public static final char FNC_4 = '\u0083';

  private static final char START_A = '\u0084';
  private static final char START_B = '\u0085';
  private static final char START_C = '\u0086';

  private static final char MODE_A = '\u0087';
  private static final char MODE_B = '\u0088';
  private static final char MODE_C = '\u0089';

  private static final char SHIFT = '\u008A';
  private static final char STOP = '\u008B';

  /**
   * A static array of
   * {@link AbstractBarcodeStrategy.CharacterCode CharacterCode} objects
   * for Code 128.  The
   * {@link AbstractBarcodeStrategy#getCodes() getCodes()} method
   * returns this array.
   *
   * The <tt>character</tt> member of the elements in this array corresponds to the
   * MODE B version of the character.
   */
  protected static CharacterCode[] codes = {
    new CharacterCode(' ', new byte[] {2,1,2,2,2,2}, 0),
    new CharacterCode('!', new byte[] {2,2,2,1,2,2}, 1),
    new CharacterCode('"', new byte[] {2,2,2,2,2,1}, 2),
    new CharacterCode('#', new byte[] {1,2,1,2,2,3}, 3),
    new CharacterCode('$', new byte[] {1,2,1,3,2,2}, 4),
    new CharacterCode('%', new byte[] {1,3,1,2,2,2}, 5),
    new CharacterCode('&', new byte[] {1,2,2,2,1,3}, 6),
    new CharacterCode('\'', new byte[] {1,2,2,3,1,2}, 7),
    new CharacterCode('(', new byte[] {1,3,2,2,1,2}, 8),
    new CharacterCode(')', new byte[] {2,2,1,2,1,3}, 9),
    new CharacterCode('*', new byte[] {2,2,1,3,1,2}, 10),
    new CharacterCode('+', new byte[] {2,3,1,2,1,2}, 11),
    new CharacterCode(',', new byte[] {1,1,2,2,3,2}, 12),
    new CharacterCode('-', new byte[] {1,2,2,1,3,2}, 13),
    new CharacterCode('.', new byte[] {1,2,2,2,3,1}, 14),
    new CharacterCode('/', new byte[] {1,1,3,2,2,2}, 15),
    new CharacterCode('0', new byte[] {1,2,3,1,2,2}, 16),
    new CharacterCode('1', new byte[] {1,2,3,2,2,1}, 17),
    new CharacterCode('2', new byte[] {2,2,3,2,1,1}, 18),
    new CharacterCode('3', new byte[] {2,2,1,1,3,2}, 19),
    new CharacterCode('4', new byte[] {2,2,1,2,3,1}, 20),
    new CharacterCode('5', new byte[] {2,1,3,2,1,2}, 21),
    new CharacterCode('6', new byte[] {2,2,3,1,1,2}, 22),
    new CharacterCode('7', new byte[] {3,1,2,1,3,1}, 23),
    new CharacterCode('8', new byte[] {3,1,1,2,2,2}, 24),
    new CharacterCode('9', new byte[] {3,2,1,1,2,2}, 25),
    new CharacterCode(':', new byte[] {3,2,1,2,2,1}, 26),
    new CharacterCode(';', new byte[] {3,1,2,2,1,2}, 27),
    new CharacterCode('<', new byte[] {3,2,2,1,1,2}, 28),
    new CharacterCode('=', new byte[] {3,2,2,2,1,1}, 29),
    new CharacterCode('>', new byte[] {2,1,2,1,2,3}, 30),
    new CharacterCode('?', new byte[] {2,1,2,3,2,1}, 31),
    new CharacterCode('@', new byte[] {2,3,2,1,2,1}, 32),
    new CharacterCode('A', new byte[] {1,1,1,3,2,3}, 33),
    new CharacterCode('B', new byte[] {1,3,1,1,2,3}, 34),
    new CharacterCode('C', new byte[] {1,3,1,3,2,1}, 35),
    new CharacterCode('D', new byte[] {1,1,2,3,1,3}, 36),
    new CharacterCode('E', new byte[] {1,3,2,1,1,3}, 37),
    new CharacterCode('F', new byte[] {1,3,2,3,1,1}, 38),
    new CharacterCode('G', new byte[] {2,1,1,3,1,3}, 39),
    new CharacterCode('H', new byte[] {2,3,1,1,1,3}, 40),
    new CharacterCode('I', new byte[] {2,3,1,3,1,1}, 41),
    new CharacterCode('J', new byte[] {1,1,2,1,3,3}, 42),
    new CharacterCode('K', new byte[] {1,1,2,3,3,1}, 43),
    new CharacterCode('L', new byte[] {1,3,2,1,3,1}, 44),
    new CharacterCode('M', new byte[] {1,1,3,1,2,3}, 45),
    new CharacterCode('N', new byte[] {1,1,3,3,2,1}, 46),
    new CharacterCode('O', new byte[] {1,3,3,1,2,1}, 47),
    new CharacterCode('P', new byte[] {3,1,3,1,2,1}, 48),
    new CharacterCode('Q', new byte[] {2,1,1,3,3,1}, 49),
    new CharacterCode('R', new byte[] {2,3,1,1,3,1}, 50),
    new CharacterCode('S', new byte[] {2,1,3,1,1,3}, 51),
    new CharacterCode('T', new byte[] {2,1,3,3,1,1}, 52),
    new CharacterCode('U', new byte[] {2,1,3,1,3,1}, 53),
    new CharacterCode('V', new byte[] {3,1,1,1,2,3}, 54),
    new CharacterCode('W', new byte[] {3,1,1,3,2,1}, 55),
    new CharacterCode('X', new byte[] {3,3,1,1,2,1}, 56),
    new CharacterCode('Y', new byte[] {3,1,2,1,1,3}, 57),
    new CharacterCode('Z', new byte[] {3,1,2,3,1,1}, 58),
    new CharacterCode('[', new byte[] {3,3,2,1,1,1}, 59),
    new CharacterCode('\\', new byte[] {3,1,4,1,1,1}, 60),
    new CharacterCode(']', new byte[] {2,2,1,4,1,1}, 61),
    new CharacterCode('^', new byte[] {4,3,1,1,1,1}, 62),
    new CharacterCode('_', new byte[] {1,1,1,2,2,4}, 63),
    new CharacterCode('`', new byte[] {1,1,1,4,2,2}, 64),
    new CharacterCode('a', new byte[] {1,2,1,1,2,4}, 65),
    new CharacterCode('b', new byte[] {1,2,1,4,2,1}, 66),
    new CharacterCode('c', new byte[] {1,4,1,1,2,2}, 67),
    new CharacterCode('d', new byte[] {1,4,1,2,2,1}, 68),
    new CharacterCode('e', new byte[] {1,1,2,2,1,4}, 69),
    new CharacterCode('f', new byte[] {1,1,2,4,1,2}, 70),
    new CharacterCode('g', new byte[] {1,2,2,1,1,4}, 71),
    new CharacterCode('h', new byte[] {1,2,2,4,1,1}, 72),
    new CharacterCode('i', new byte[] {1,4,2,1,1,2}, 73),
    new CharacterCode('j', new byte[] {1,4,2,2,1,1}, 74),
    new CharacterCode('k', new byte[] {2,4,1,2,1,1}, 75),
    new CharacterCode('l', new byte[] {2,2,1,1,1,4}, 76),
    new CharacterCode('m', new byte[] {4,1,3,1,1,1}, 77),
    new CharacterCode('n', new byte[] {2,4,1,1,1,2}, 78),
    new CharacterCode('o', new byte[] {1,3,4,1,1,1}, 79),
    new CharacterCode('p', new byte[] {1,1,1,2,4,2}, 80),
    new CharacterCode('q', new byte[] {1,2,1,1,4,2}, 81),
    new CharacterCode('r', new byte[] {1,2,1,2,4,1}, 82),
    new CharacterCode('s', new byte[] {1,1,4,2,1,2}, 83),
    new CharacterCode('t', new byte[] {1,2,4,1,1,2}, 84),
    new CharacterCode('u', new byte[] {1,2,4,2,1,1}, 85),
    new CharacterCode('v', new byte[] {4,1,1,2,1,2}, 86),
    new CharacterCode('w', new byte[] {4,2,1,1,1,2}, 87),
    new CharacterCode('x', new byte[] {4,2,1,2,1,1}, 88),
    new CharacterCode('y', new byte[] {2,1,2,1,4,1}, 89),
    new CharacterCode('z', new byte[] {2,1,4,1,2,1}, 90),
    new CharacterCode('{', new byte[] {4,1,2,1,2,1}, 91),
    new CharacterCode('|', new byte[] {1,1,1,1,4,3}, 92),
    new CharacterCode('}', new byte[] {1,1,1,3,4,1}, 93),
    new CharacterCode('~', new byte[] {1,3,1,1,4,1}, 94),
    new CharacterCode('\u007F', new byte[] {1,1,4,1,1,3}, 95),
    new CharacterCode(FNC_3, new byte[] {1,1,4,3,1,1}, 96),
    new CharacterCode(FNC_2, new byte[] {4,1,1,1,1,3}, 97),
    new CharacterCode(SHIFT, new byte[] {4,1,1,3,1,1}, 98),
    new CharacterCode(MODE_C, new byte[] {1,1,3,1,4,1}, 99),
    new CharacterCode(FNC_4, new byte[] {1,1,4,1,3,1}, 100),
    new CharacterCode(MODE_A, new byte[] {3,1,1,1,4,1}, 101),
    new CharacterCode(FNC_1, new byte[] {4,1,1,1,3,1}, 102),
    new CharacterCode(START_A, new byte[] {2,1,1,4,1,2}, 103),
    new CharacterCode(START_B, new byte[] {2,1,1,2,1,4}, 104),
    new CharacterCode(START_C, new byte[] {2,1,1,2,3,2}, 105),
    new CharacterCode(STOP, new byte[] {2,3,3,1,1,1,2}, 106)
  };

  /**
   * Always returns {@link BarcodeStrategy#MANDATORY_CHECKSUM}.
   */
  public int requiresChecksum() {
    // Checksum is not mandatory
    return MANDATORY_CHECKSUM;
  }

  /**
   * This implementation of <tt>getCodes</tt> returns an array of
   * {@link AbstractBarcodeStrategy.CharacterCode CharacterCode} objects
   * for the Code 128 format.
   */
  protected CharacterCode[] getCodes() {
    return codes;
  }

  /**
   * Returns the <tt>text</tt> parameter with function characters and
   * control codes stripped out.
   */
  protected String getBarcodeLabelText(String text) {
    char ch;
    String label = new String();

    for (int i = 0; i < text.length(); i++) {
      ch = text.charAt(i);
      if (ch >= ' ' && ch <= '~') {
        label += ch;
      }
    }

    return label;
  }

  /**
   * Always returns 11 (eleven).
   */
  protected byte getMarginWidth() {
    return 11;
  }

  /**
   * Always returns <tt>0xffff</tt>, signalling to <tt><b>AbstractBarcodeStrategy</b></tt>
   * superclass that it should not prefix a standard start character.  In
   * Code 128 there are three possible start characters, and the
   * <tt><b>preprocess</b></tt> method handles the insertion of the correct
   * start code instead.
   */
  protected char getStartSentinel() {
    return 0xffff;
  }

  /**
   * Always returns the Code 128 STOP character.
   */
  protected char getStopSentinel() {
    return STOP;
  }

  /**
   * Always returns <tt>false</tt>.
   */
  protected boolean isInterleaved() {
    return false;
  }

  /**
   * Inserts start character and code change characters.
   */
  protected String preprocess(String text) throws BarcodeException {

    String preprocessed = new String();
    char mode = 0;
    char startFunction = 0;
    char c1 = 0;
    char c2 = 0;

    // Base the choice of start character on the first two characters
    // after the optional function character.

    c1 = text.charAt(0);
    if (c1 == FNC_1 || c1 == FNC_2 || c1 == FNC_3 || c1 == FNC_4) {
      startFunction = c1;
      c1 = text.charAt(1);
      if (text.length() > 2) {
        c2 = text.charAt(2);
      }
    }
    else {
      if (text.length() > 1) {
        c2 = text.charAt(1);
      }
    }

    if (c1 >= '0' && c1 <= '9' && c2 >= '0' && c2 <= '9' &&
        startFunction != FNC_2 && startFunction != FNC_3 &&
        startFunction != FNC_4) {
      // Use "C" start character.
      preprocessed += START_C;
      mode = MODE_C;
    }
    else if (c1 >= ' ' && c1 <= '\u007f') {
      // Use "B" start character.
      preprocessed += START_B;
      mode = MODE_B;
    }
    else {
      // Use "A" start character.
      preprocessed += START_A;
      mode = MODE_A;
    }

    for (int i = 0; i < text.length(); i++) {
      c1 = text.charAt(i);
      if (i + 1 < text.length()) {
        c2 = text.charAt(i + 1);
      } else {
        c2 = 0;
      }

      if (c1 > FNC_4) {
        throw new BarcodeException("Invalid character in barcode");
      }

      switch (mode) {

        case MODE_C :

          if (c1 == FNC_1) {
            // FNC_1.
            preprocessed += FNC_1;
          }
          else if (c1 >= '0' && c1 <= '9' && c2 >= '0' && c2 <= '9') {
            // Two digit encode.
            preprocessed += convertCodeC(text.substring(i,i+2));
            i++;
          }
          else if (c1 >= ' ' && c1 <= '\u007f') {
            // Need to change to mode B.
            preprocessed += FNC_4;  // Corresponds to Mode B.
            mode = MODE_B;
            i--;
          }
          else {
            // Need to change to mode A.
            preprocessed += MODE_A;
            mode = MODE_A;
            i--;
          }

          break;

        case MODE_B :

          if (c1 >= '0' && c1 <= '9' && c2 >= '0' && c2 <= '9') {
            // Two digits; Change to mode C.
            preprocessed += MODE_C;
            mode = MODE_C;
            i--;
          }
          else if (c1 >= ' ' && c1 <= '\u007f') {
            // Acceptable mode B character.
            preprocessed += c1;
          }
          else if (c1 == FNC_1 || c1 == FNC_2 || c1 == FNC_3 || c1 == FNC_4)  {
            // A function code.
            preprocessed += c1;
          }
          else {
            // Control character; Change to mode A.
            preprocessed += MODE_A;
            mode = MODE_A;
            i--;
          }

          break;

        case MODE_A :

          if (c1 >= '0' && c1 <= '9' && c2 >= '0' && c2 <= '9') {
            // Two digits; Change to mode C.
            preprocessed += MODE_C;
            mode = MODE_C;
            i--;
          }
          else if (c1 < ' ') {
            // Control character.
            preprocessed += (c1 + '`');
          }
          else if (c1 <= '_') {
            // Upper case character.
            preprocessed += c1;
          }
          else if (c1 == FNC_1 || c1 == FNC_2 || c1 == FNC_3)  {
            // Function code 1, 2 or 3.
            preprocessed += c1;
          }
          else if (c1 == FNC_4)  {
            // Function code 4.
            preprocessed += MODE_A;   // Corresponds to FNC_4.
          }
          else {
            // Change to mode B.
            preprocessed += FNC_4;  // Corresponds to Mode B.
            mode = MODE_B;
            i--;
          }

          break;
      }
    }

    return preprocessed;
  }

  /**
   * Converts the Mode C two-digit combinations to their corresponding
   * Mode B counterparts.
   */
  private char convertCodeC(String twoDigits) {

    char result = (char) (' ' + new Integer(twoDigits).intValue());

    switch (result) {

      case '\u0080' :   // 96
        result = FNC_3;
        break;

      case '\u0081' :   // 97
        result = FNC_2;
        break;

      case '\u0082' :   // 98
        result = SHIFT;
        break;

      case '\u0083' :   // 99
        result = MODE_C;
        break;
    }

    return result;
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
   * It is assumed that the text passed to this method includes the Start
   * character (populated by <tt>preprocess</tt>), but not the Stop character
   * (end sentinel).
   */
  protected String augmentWithChecksum(String text) throws BarcodeException {
    int checkTotal = 0;
    CharacterCode cc;

    // Start character
    cc = getCharacterCode(text.charAt(0));
    checkTotal = cc.check;

    for (int i = 1; i < text.length(); i++) {
      char ch = text.charAt(i);
      cc = getCharacterCode(ch);            // get code by character
      if (cc == null) {
        throw new BarcodeException("Invalid character in barcode");
      }
      checkTotal += cc.check * i;
    }
    cc = getCharacterCode(checkTotal % 103); // get code by check number
    return text + cc.character;
  }
}
