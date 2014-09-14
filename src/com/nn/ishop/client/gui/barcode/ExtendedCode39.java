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
 * knows how to encode the 3:1 (wide)
 * variant of the Extended Code 3 of 9 barcode type.
 */
public class ExtendedCode39 extends BaseCode39 implements java.io.Serializable {

  private static Conversion[] conversions = {
    new Conversion('\u0000', "%U"),
    new Conversion('\u0001', "$A"),
    new Conversion('\u0002', "$B"),
    new Conversion('\u0003', "$C"),
    new Conversion('\u0004', "$D"),
    new Conversion('\u0005', "$E"),
    new Conversion('\u0006', "$F"),
    new Conversion('\u0007', "$G"),
    new Conversion('\b', "$H"),
    new Conversion('\t', "$I"),
    new Conversion('\n', "$J"),
    new Conversion('\u000B', "$K"),
    new Conversion('\f', "$L"),
    new Conversion('\r', "$M"),
    new Conversion('\u000E', "$N"),
    new Conversion('\u000F', "$O"),
    new Conversion('\u0010', "$P"),
    new Conversion('\u0011', "$Q"),
    new Conversion('\u0012', "$R"),
    new Conversion('\u0013', "$S"),
    new Conversion('\u0014', "$T"),
    new Conversion('\u0015', "$U"),
    new Conversion('\u0016', "$V"),
    new Conversion('\u0017', "$W"),
    new Conversion('\u0018', "$X"),
    new Conversion('\u0019', "$Y"),
    new Conversion('\u001A', "$Z"),
    new Conversion('\u001B', "%A"),
    new Conversion('\u001C', "%B"),
    new Conversion('\u001D', "%C"),
    new Conversion('\u001E', "%D"),
    new Conversion('\u001F', "%E"),
    // Space is not converted
    new Conversion('!', "/A"),
    new Conversion('"', "/B"),
    new Conversion('#', "/C"),
    new Conversion('$', "/D"),
    new Conversion('%', "/E"),
    new Conversion('&', "/F"),
    new Conversion('\'', "/G"),
    new Conversion('(', "/H"),
    new Conversion(')', "/I"),
    new Conversion('*', "/J"),
    new Conversion('+', "/K"),
    new Conversion(',', "/L"),
    new Conversion('-', "/M"),
    new Conversion('.', "/N"),
    new Conversion('/', "/O"),
    // Digits 0 through 9 not converted
    new Conversion(':', "/Z"),
    new Conversion(';', "%F"),
    new Conversion('<', "%G"),
    new Conversion('=', "%H"),
    new Conversion('>', "%I"),
    new Conversion('?', "%J"),
    new Conversion('@', "%V"),
    // Upper case letters A through Z not converted
    new Conversion('[', "%K"),
    new Conversion('\\', "%L"),
    new Conversion(']', "%M"),
    new Conversion('^', "%N"),
    new Conversion('_', "%O"),
    new Conversion('`', "%W"),
    new Conversion('a', "+A"),
    new Conversion('b', "+B"),
    new Conversion('c', "+C"),
    new Conversion('d', "+D"),
    new Conversion('e', "+E"),
    new Conversion('f', "+F"),
    new Conversion('g', "+G"),
    new Conversion('h', "+H"),
    new Conversion('i', "+I"),
    new Conversion('j', "+J"),
    new Conversion('k', "+K"),
    new Conversion('l', "+L"),
    new Conversion('m', "+M"),
    new Conversion('n', "+N"),
    new Conversion('o', "+O"),
    new Conversion('p', "+P"),
    new Conversion('q', "+Q"),
    new Conversion('r', "+R"),
    new Conversion('s', "+S"),
    new Conversion('t', "+T"),
    new Conversion('u', "+U"),
    new Conversion('v', "+V"),
    new Conversion('w', "+W"),
    new Conversion('x', "+X"),
    new Conversion('y', "+Y"),
    new Conversion('z', "+Z"),
    new Conversion('{', "%P"),
    new Conversion('|', "%Q"),
    new Conversion('}', "%R"),
    new Conversion('~', "%S"),
    new Conversion('\u007F', "%T")
  };

  /**
   * This implementation of <tt>getCodes</tt> returns {@link BaseCode39#codes}.
   */
  protected CharacterCode[] getCodes() {
    return BaseCode39.codes;
  }

  /**
   * Converts ASCII to Code 39 characters.
   */
  protected String preprocess(String text) {
    // Convert ASCII to extended Code 39 codes.
    String toText = "";
    int len = text.length();
    for (int i = 0; i < len; i++) {
      char ch = text.charAt(i);
      boolean found = false;
      for (int j = 0; j < conversions.length; j++) {
        if (ch == conversions[j].from) {
          toText += conversions[j].to;
          found = true;
          break;
        }
      }
      if (!found) {
        toText += ch;
      }
    }
    return toText;
  }

  /**
   * Returns the <tt>text</tt> parameter unmodified.
   */
  protected String getBarcodeLabelText(String text) {
    return text;
  }

  private static class Conversion {
    char from;
    String to;
    public Conversion(char from, String to) {
      this.from = from;
      this.to = to;
    }
  }
}
