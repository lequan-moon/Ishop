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

import java.beans.PropertyEditorSupport;

import com.nn.ishop.client.gui.barcode.impl.Codabar;
import com.nn.ishop.client.gui.barcode.impl.Codabar_2to1;
import com.nn.ishop.client.gui.barcode.impl.Code11;
import com.nn.ishop.client.gui.barcode.impl.Code128;
import com.nn.ishop.client.gui.barcode.impl.Code39;
import com.nn.ishop.client.gui.barcode.impl.Code39_2to1;
import com.nn.ishop.client.gui.barcode.impl.Code93;
import com.nn.ishop.client.gui.barcode.impl.Code93Extended;
import com.nn.ishop.client.gui.barcode.impl.Ean13;
import com.nn.ishop.client.gui.barcode.impl.Ean8;
import com.nn.ishop.client.gui.barcode.impl.Interleaved25;
import com.nn.ishop.client.gui.barcode.impl.Interleaved25_2to1;




/**
 * A property editor for the {@link BarcodeStrategy} type.
 * GUI Builders use this class for the
 * {@link JBarcodeBean#setCodeType(BarcodeStrategy) codeType} property of
 * the {@link JBarcodeBean} Javabean component.
 */
public class BarcodeStrategyEditor extends PropertyEditorSupport {

    private static final String EAN_8               = "EAN-8";
    private static final String EAN_13              = "EAN-13";
    private static final String CODABAR_2_1         = "Codabar 2:1";
    private static final String CODABAR_3_1         = "Codabar 3:1";
    private static final String MSI_MOD_10_CHECK    = "MSI (mod 10 check)";
    private static final String INTERLEAVED_25_2_1  = "Interleaved 25 2:1";
    private static final String INTERLEAVED_25_3_1  = "Interleaved 25 3:1";
    private static final String EXT_CODE_39_2_1     = "Ext Code 39 2:1";
    private static final String EXT_CODE_39_3_1     = "Ext Code 39 3:1";
    private static final String CODE_39_2_1         = "Code 39 2:1";
    private static final String CODE_39_3_1         = "Code 39 3:1";
    private static final String CODE_128            = "Code 128";
    private static final String CODE_93             = "Code 93";
    private static final String CODE_93_EXTENDED    = "Code 93 Extended";
    private static final String CODE_11             = "Code 11";

    public String[] getTags() {
        return new String[] {
                        CODE_11,
                        CODE_128,
                        CODE_39_3_1,
                        CODE_39_2_1,
                        EXT_CODE_39_3_1,
                        EXT_CODE_39_2_1,
                        CODE_93,
                        CODE_93_EXTENDED,
                        INTERLEAVED_25_3_1,
                        INTERLEAVED_25_2_1,
                        MSI_MOD_10_CHECK,
                        CODABAR_3_1,
                        CODABAR_2_1,
                        EAN_13,
                        EAN_8
        };
    }

    public void setAsText(String s) {
        if (s.equals(CODE_128)) {
            setValue(new Code128());
        } else if (s.equals(CODE_39_3_1)) {
            setValue(new Code39());
        } else if (s.equals(CODE_39_2_1)) {
            setValue(new Code39_2to1());
        } else if (s.equals(EXT_CODE_39_3_1)) {
            setValue(new ExtendedCode39());
        } else if (s.equals(EXT_CODE_39_2_1)) {
            setValue(new ExtendedCode39_2to1());
        } else if (s.equals(INTERLEAVED_25_3_1)) {
            setValue(new Interleaved25());
        } else if (s.equals(INTERLEAVED_25_2_1)) {
            setValue(new Interleaved25_2to1());
        } else if (s.equals(MSI_MOD_10_CHECK)) {
            setValue(new MSI());
        } else if (s.equals(CODABAR_3_1)) {
            setValue(new Codabar());
        } else if (s.equals(CODABAR_2_1)) {
            setValue(new Codabar_2to1());
        } else if (s.equals(EAN_13)) {
            setValue(new Ean13());
        } else if (s.equals(EAN_8)) {
            setValue(new Ean8());
        } else if (s.equals(CODE_93)) {
            setValue(new Code93());
        } else if (s.equals(CODE_93_EXTENDED)) {
            setValue(new Code93Extended());
        } else if (s.equals(CODE_11)) {
            setValue(new Code11());
        } else {
            // Default to Code 39
            setValue(new Code39());
        }
    }

    public String getAsText() {
        BarcodeStrategy s = (BarcodeStrategy)getValue();
        if (s.getClass().equals(Code128.class)) {
            // Code 128
            return CODE_128;
        } else if (s.getClass().equals(Code39_2to1.class)) {
            // Code 3 of 9 2:1
            return CODE_39_2_1;
        } else if (s.getClass().equals(Code39.class)) {
            // Code 3 of 9 3:1
            return CODE_39_3_1;
        } else if (s.getClass().equals(ExtendedCode39_2to1.class)) {
            // Extended Code 3 of 9 2:1
            return EXT_CODE_39_2_1;
        } else if (s.getClass().equals(ExtendedCode39.class)) {
            // Extended Code 3 of 9 3:1
            return EXT_CODE_39_3_1;
        } else if (s.getClass().equals(Interleaved25_2to1.class)) {
            // Interleaved 25 2:1
            return INTERLEAVED_25_2_1;
        } else if (s.getClass().equals(Interleaved25.class)) {
            // Interleaved 25 3:1
            return INTERLEAVED_25_3_1;
        } else if (s.getClass().equals(MSI.class)) {
            // MSI
            return MSI_MOD_10_CHECK;
        } else if (s.getClass().equals(Codabar_2to1.class)) {
            // Codabar 2:1
            return CODABAR_2_1;
        } else if (s.getClass().equals(Codabar.class)) {
            // Codabar 3:1
            return CODABAR_3_1;
        } else if (s.getClass().equals(Ean13.class)) {
            // EAN-13
            return EAN_13;
        } else if (s.getClass().equals(Ean8.class)) {
            // EAN-8
            return EAN_8;
        } else if (s.getClass().equals(Code93.class)) {
            // EAN-8
            return CODE_93;
        } else if (s.getClass().equals(Code93Extended.class)) {
            // EAN-8
            return CODE_93_EXTENDED;
        } else if (s.getClass().equals(Code11.class)) {
            // EAN-8
            return CODE_11;
        } else {
            // Must set to something, so default to Code 39
            return "Code 39";
        }
    }

    public String getJavaInitializationString() {
        BarcodeStrategy s = (BarcodeStrategy)getValue();
        if (s.getClass().equals(Code128.class)) {
            // Code 128
            return "new jbarcodebean.Code128()";
        } else if (s.getClass().equals(Code39_2to1.class)) {
            // Code 3 of 9 2:1
            return "new jbarcodebean.Code39_2to1()";
        } else if (s.getClass().equals(Code39.class)) {
            // Code 3 of 9 3:1
            return "new jbarcodebean.Code39()";
        } else if (s.getClass().equals(ExtendedCode39_2to1.class)) {
            // Extended Code 3 of 9 2:1
            return "new jbarcodebean.ExtendedCode39_2to1()";
        } else if (s.getClass().equals(ExtendedCode39.class)) {
            // Extended Code 3 of 9 3:1
            return "new jbarcodebean.ExtendedCode39()";
        } else if (s.getClass().equals(Interleaved25_2to1.class)) {
            // Interleaved 25 2:1
            return "new jbarcodebean.Interleaved25_2to1()";
        } else if (s.getClass().equals(Interleaved25.class)) {
            // Interleaved 25 3:1
            return "new jbarcodebean.Interleaved25()";
        } else if (s.getClass().equals(MSI.class)) {
            // MSI
            return "new jbarcodebean.MSI()";
        } else if (s.getClass().equals(Codabar_2to1.class)) {
            // Codabar 2:1
            return "new jbarcodebean.Codabar_2to1()";
        } else if (s.getClass().equals(Codabar.class)) {
            // Codabar 3:1
            return "new jbarcodebean.Codabar()";
        } else if (s.getClass().equals(Ean13.class)) {
            // EAN-13
            return "new jbarcodebean.Ean13()";
        } else if (s.getClass().equals(Ean8.class)) {
            // EAN-8
            return "new jbarcodebean.Ean8()";
        } else if (s.getClass().equals(Code93.class)) {
            // EAN-8
            return "new jbarcodebean.Code93()";
        } else if (s.getClass().equals(Code93Extended.class)) {
            // EAN-8
            return "new jbarcodebean.Code93Extended()";
        } else if (s.getClass().equals(Code11.class)) {
            // EAN-8
            return "new jbarcodebean.Code11()";
        } else {
            // Must set to something, so default to Code 39
            return "new jbarcodebean.Code39()";
        }
    }
}
