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
 * Company:      Matthias Hanisch
 */
package com.nn.ishop.client.gui.barcode.impl;

import java.util.ArrayList;

import com.nn.ishop.client.gui.barcode.AbstractBarcodeStrategy;
import com.nn.ishop.client.gui.barcode.BarcodeElement;
import com.nn.ishop.client.gui.barcode.BarcodeException;
import com.nn.ishop.client.gui.barcode.EncodedBarcode;
import com.nn.ishop.client.gui.barcode.AbstractBarcodeStrategy.CharacterCode;


public class Code93 extends AbstractBarcodeStrategy {
    
    protected static final char SHIFT_DOLLAR  =(char)1043;
    protected static final char SHIFT_PERCENT =(char)1044;
    protected static final char SHIFT_SLASH   =(char)1045;
    protected static final char SHIFT_PLUS    =(char)1046;    
    
    static final CharacterCode[] codes={
        new CharacterCode('0',new byte[]{1,3,1,1,1,2},0),
        new CharacterCode('1',new byte[]{1,1,1,2,1,3},1),
        new CharacterCode('2',new byte[]{1,1,1,3,1,2},2),
        new CharacterCode('3',new byte[]{1,1,1,4,1,1},3),
        new CharacterCode('4',new byte[]{1,2,1,1,1,3},4),
        new CharacterCode('5',new byte[]{1,2,1,2,1,2},5),
        new CharacterCode('6',new byte[]{1,2,1,3,1,1},6),
        new CharacterCode('7',new byte[]{1,1,1,1,1,4},7),
        new CharacterCode('8',new byte[]{1,3,1,2,1,1},8),
        new CharacterCode('9',new byte[]{1,4,1,1,1,1},9),
        new CharacterCode('A',new byte[]{2,1,1,1,1,3},10),
        new CharacterCode('B',new byte[]{2,1,1,2,1,2},11),
        new CharacterCode('C',new byte[]{2,1,1,3,1,1},12),
        new CharacterCode('D',new byte[]{2,2,1,1,1,2},13),
        new CharacterCode('E',new byte[]{2,2,1,2,1,1},14),
        new CharacterCode('F',new byte[]{2,3,1,1,1,1},15),
        new CharacterCode('G',new byte[]{1,1,2,1,1,3},16),
        new CharacterCode('H',new byte[]{1,1,2,2,1,2},17),
        new CharacterCode('I',new byte[]{1,1,2,3,1,1},18),
        new CharacterCode('J',new byte[]{1,2,2,1,1,2},19),
        new CharacterCode('K',new byte[]{1,3,2,1,1,1},20),
        new CharacterCode('L',new byte[]{1,1,1,1,2,3},21),
        new CharacterCode('M',new byte[]{1,1,1,2,2,2},22),
        new CharacterCode('N',new byte[]{1,1,1,3,2,1},23),
        new CharacterCode('O',new byte[]{1,2,1,1,2,2},24),
        new CharacterCode('P',new byte[]{1,3,1,1,2,1},25),
        new CharacterCode('Q',new byte[]{2,1,2,1,1,2},26),
        new CharacterCode('R',new byte[]{2,1,2,2,1,1},27),
        new CharacterCode('S',new byte[]{2,1,1,1,2,2},28),
        new CharacterCode('T',new byte[]{2,1,1,2,2,1},29),
        new CharacterCode('U',new byte[]{2,2,1,1,2,1},30),
        new CharacterCode('V',new byte[]{2,2,2,1,1,1},31),
        new CharacterCode('W',new byte[]{1,1,2,1,2,2},32),
        new CharacterCode('X',new byte[]{1,1,2,2,2,1},33),
        new CharacterCode('Y',new byte[]{1,2,2,1,2,1},34),
        new CharacterCode('Z',new byte[]{1,2,3,1,1,1},35),
        new CharacterCode('-',new byte[]{1,2,1,1,3,1},36),
        new CharacterCode('.',new byte[]{3,1,1,1,1,2},37),
        new CharacterCode(' ',new byte[]{3,1,1,2,1,1},38),
        new CharacterCode('$',new byte[]{3,2,1,1,1,1},39),
        new CharacterCode('/',new byte[]{1,1,2,1,3,1},40),
        new CharacterCode('+',new byte[]{1,1,3,1,2,1},41),
        new CharacterCode('%',new byte[]{2,1,1,1,3,1},42),
        new CharacterCode(SHIFT_DOLLAR,new byte[]{1,2,1,2,2,1},43),
        new CharacterCode(SHIFT_PERCENT,new byte[]{3,1,2,1,1,1},44),
        new CharacterCode(SHIFT_SLASH,new byte[]{3,1,1,1,2,1},45),
        new CharacterCode(SHIFT_PLUS,new byte[]{1,2,2,2,1,1},46),
        new CharacterCode('*',new byte[]{1,1,1,1,4,1},-1)
    };

    protected String augmentWithChecksum(String text) throws BarcodeException {
        int checksumC=0;
        int checksumK=0;
        int weightC=1;
        int weightK=1;
        for(int i=text.length()-1;i>=0;i--){
            char c=text.charAt(i);
            CharacterCode cc=getCharacterCode(c);
            checksumC+=cc.check*weightC;
            weightC++;
            if(weightC>20){
                weightC=1;
            }
        }
        checksumC=checksumC%47;
        CharacterCode codeC=getCharacterCode(checksumC);
        text+=codeC.character;
        for(int i=text.length()-1;i>=0;i--){
            char c=text.charAt(i);
            CharacterCode cc=getCharacterCode(c);
            checksumK+=cc.check*weightK;
            weightK++;
            if(weightK>15){
                weightK=1;
            }
        }
        checksumK=checksumK%47;
        CharacterCode codeK=getCharacterCode(checksumK);
        text+=codeK.character;
        return text;
    }

    protected String getBarcodeLabelText(String text) {
        return text;
    }

    protected CharacterCode[] getCodes() {
        return codes;
    }

    protected byte getMarginWidth() {
        return 11;
    }

    protected char getStartSentinel() {
        return '*';
    }

    protected char getStopSentinel() {
        return '*';
    }

    protected boolean isInterleaved() {
        return false;
    }

    protected String postprocess(String text) {
        return text;
    }

    protected String preprocess(String text) throws BarcodeException {
        return text;
    }

    public int requiresChecksum() {
        return MANDATORY_CHECKSUM;
    }

    public EncodedBarcode encode(String textToEncode, boolean checked) throws BarcodeException {
        EncodedBarcode ebc=super.encode(textToEncode, checked);
        BarcodeElement[] elements = ebc.elements;
        String barcodeLabelText = ebc.barcodeLabelText;
        BarcodeElement terminationBar=new BarcodeElement();
        terminationBar.width=1;
        terminationBar.bar=true;
        ArrayList list=new ArrayList();
        for(int i=0;i<elements.length;i++){
            list.add(elements[i]);
        }
        list.add(list.size()-1,terminationBar);
        BarcodeElement[] allElements=(BarcodeElement[]) list.toArray(new BarcodeElement[list.size()]);
        return new EncodedBarcode(allElements, barcodeLabelText);
    }
    
}
