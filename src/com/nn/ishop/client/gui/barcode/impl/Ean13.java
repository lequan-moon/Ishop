/*
 *  This library is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License (LGPL) as
 *  published by the Free Software Foundation; either version 3.0 of the
 *  License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY of FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details. 
 *
 *  This file was contributed to JBarcodeBean by Jose Gaonac'h.
 *  Copyright (C) 2004 Jose Gaonac'h.
 */

package com.nn.ishop.client.gui.barcode.impl;

import com.nn.ishop.client.gui.barcode.AbstractBarcodeStrategy;
import com.nn.ishop.client.gui.barcode.BarcodeException;
import com.nn.ishop.client.gui.barcode.AbstractBarcodeStrategy.CharacterCode;


/**
 * EAN-13 barcode implementation.
 * If less than 12 digits are supplied, the symbol is invalid
 * Only the first 12 digits are considered: the checksum (13th digit) is
 * always generated (MANDATORY_CHECKSUM).
 *
 * @author  Jose Gaonac'h
 */
public class Ean13 extends AbstractBarcodeStrategy implements java.io.Serializable {
    
    private static CharacterCode[] codes = {
        // Left side A values and right side C values
        new CharacterCode('0', new byte[] {3,2,1,1}, 0),
        new CharacterCode('1', new byte[] {2,2,2,1}, 1),
        new CharacterCode('2', new byte[] {2,1,2,2}, 2),
        new CharacterCode('3', new byte[] {1,4,1,1}, 3),
        new CharacterCode('4', new byte[] {1,1,3,2}, 4),
        new CharacterCode('5', new byte[] {1,2,3,1}, 5),
        new CharacterCode('6', new byte[] {1,1,1,4}, 6),
        new CharacterCode('7', new byte[] {1,3,1,2}, 7),
        new CharacterCode('8', new byte[] {1,2,1,3}, 8),
        new CharacterCode('9', new byte[] {3,1,1,2}, 9),
        // Left side B values
        new CharacterCode('a', new byte[] {1,1,2,3}, 0),
        new CharacterCode('b', new byte[] {1,2,2,2}, 1),
        new CharacterCode('c', new byte[] {2,2,1,2}, 2),
        new CharacterCode('d', new byte[] {1,1,4,1}, 3),
        new CharacterCode('e', new byte[] {2,3,1,1}, 4),
        new CharacterCode('f', new byte[] {1,3,2,1}, 5),
        new CharacterCode('g', new byte[] {4,1,1,1}, 6),
        new CharacterCode('h', new byte[] {2,1,3,1}, 7),
        new CharacterCode('i', new byte[] {3,1,2,1}, 8),
        new CharacterCode('j', new byte[] {2,1,1,3}, 9),
        
        new CharacterCode('A', new byte[] {1,1,1},     -1),     // Start
        new CharacterCode('B', new byte[] {1,1,1},     -1),     // Stop
        new CharacterCode('C', new byte[] {1,1,1,1,1}, -1)      // Center
    };
    
    private static char[] bPattern = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
    
    protected String checkSum;
    
    public int requiresChecksum() {
        return MANDATORY_CHECKSUM;
    }
    
    protected CharacterCode[] getCodes() {
        return codes;
    }
    
    // Compute checksum and store it locally.
    protected void computeChecksum(String text, int len) {
        int even = 0;
        int odd = 0;
        int tot;
        
        checkSum = "";
        
        if (text.length() == len) {
            
            for (int i=len-1; i>=0; i-=2) {
                even += Integer.parseInt(text.substring(i, i+1));
                if (i <= 0) break;
                odd += Integer.parseInt(text.substring(i-1, i));
            }
            even *= 3;
            tot = even + odd;
            even = tot/10;
            odd = tot%10;
            if (odd != 0) even++;
            
            checkSum = new Integer(even*10 - tot).toString();
        }
    }
    
    protected String augmentWithChecksum(String text) throws BarcodeException {
        // Checksum has been computed before inserting B codes and center bars
        return text + checkSum;
    }
    
    protected String postprocess(String text) {
        return text;
    }
    
    /*
      There are 3 representations of digits in a EAN/UPC symbol: A, B, and C
      A or B representations are used for the 6 left digits, while C
      representations are used on the 6 right digits. The C representation is the 
      opposite of A representations: just exchange white anf black bars. This is
      done automatically, thanks to the insertion of the center bars code (odd number of bars)
      which reverses the process: the first module in left-side digits is a space, but it is
      a bar in right-side digits... Therefore we can use the same 'CharacterCode' for left
      side 'A's and right side 'C's.
      The combinations of A and B representations in the left side determines the
      system digit (SD): this digit, which is the first in the symbol, is not encoded
      as bar / spaces.
      The following table list the possible layout of A and B codes in the left
      part of symbol and the coresponding SD:
      
      Layout               --> SD
      A  A  A  A  A  A         0
      A  A  B  A  B  B         1
      A  A  B  B  A  B         2
      A  A  B  B  B  A         3
      A  B  A  A  B  B         4
      A  B  B  A  A  B         5
      A  B  B  B  A  A         6
      A  B  A  B  A  B         7
      A  B  A  B  B  A         8
      A  B  B  A  B  A         9
     
     */
    protected String preprocess(String text) {
        if (text.length() < 12) return text;
        text = text.substring(0, 12);
        
        computeChecksum(text, 12);
        char[] t = (text.substring(1, 7) + "C" + text.substring(7)).toCharArray();
        switch (text.charAt(0)) {
            case '0':
                // A A A A A A
                break;
            case '1':
                // A A B A B B
                t[2] = bPattern[getCharacterCode(t[2]).check];
                t[4] = bPattern[getCharacterCode(t[4]).check];
                t[5] = bPattern[getCharacterCode(t[5]).check];
                break;
            case '2':
                // A A B B A B
                t[2] = bPattern[getCharacterCode(t[2]).check];
                t[3] = bPattern[getCharacterCode(t[3]).check];
                t[5] = bPattern[getCharacterCode(t[5]).check];
                break;
            case '3':
                // A A B B B A
                t[2] = bPattern[getCharacterCode(t[2]).check];
                t[3] = bPattern[getCharacterCode(t[3]).check];
                t[4] = bPattern[getCharacterCode(t[4]).check];
                break;
            case '4':
                // A B A A B B
                t[1] = bPattern[getCharacterCode(t[1]).check];
                t[4] = bPattern[getCharacterCode(t[4]).check];
                t[5] = bPattern[getCharacterCode(t[5]).check];
                break;
            case '5':
                // A B B A A B
                t[1] = bPattern[getCharacterCode(t[1]).check];
                t[2] = bPattern[getCharacterCode(t[2]).check];
                t[5] = bPattern[getCharacterCode(t[5]).check];
                break;
            case '6':
                // A B B B A A
                t[1] = bPattern[getCharacterCode(t[1]).check];
                t[2] = bPattern[getCharacterCode(t[2]).check];
                t[3] = bPattern[getCharacterCode(t[3]).check];
                break;
            case '7':
                // A B A B A B
                t[1] = bPattern[getCharacterCode(t[1]).check];
                t[3] = bPattern[getCharacterCode(t[3]).check];
                t[5] = bPattern[getCharacterCode(t[5]).check];
                break;
            case '8':
                // A B A B B A
                t[1] = bPattern[getCharacterCode(t[1]).check];
                t[3] = bPattern[getCharacterCode(t[3]).check];
                t[4] = bPattern[getCharacterCode(t[4]).check];
                break;
            case '9':
                // A B B A B A
                t[1] = bPattern[getCharacterCode(t[1]).check];
                t[2] = bPattern[getCharacterCode(t[2]).check];
                t[4] = bPattern[getCharacterCode(t[4]).check];
                break;
        }
        return new String(t);
    }
    
    protected boolean isInterleaved() {
        return false;
    }
    
    protected char getStartSentinel() {
        return 'A';
    }
    
    protected char getStopSentinel() {
        return 'B';
    }
    
    /**
     * Always returns 11 (eleven).
     */
    protected byte getMarginWidth() {
        return 11;
    }
    
    protected String getBarcodeLabelText(String text) {
        if (text.length() < 12) return text;
        return text.substring(0, 12) + checkSum;
    }
    
}
