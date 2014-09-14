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

/**
 * EAN-8 barcode implementation.
 * If less than 7 digits are supplied, the symbol is invalid
 * Only the first 7 digits are considered: the checksum (8th digit) is
 * always generated (MANDATORY_CHECKSUM).
 *
 * @author  Jose Gaonac'h
 */
public class Ean8 extends Ean13 {
    
    protected String preprocess(String text) {
        if (text.length() < 7) return text;
        text = text.substring(0, 7);

        computeChecksum(text, 7);
        char[] t = (text.substring(0, 4) + "C" + text.substring(4)).toCharArray();
        return new String(t);
    }    

    protected String getBarcodeLabelText(String text) {
        if (text.length() < 7) return text;
        return text.substring(0, 7) + checkSum;
    }
}
