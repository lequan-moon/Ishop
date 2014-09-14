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

import com.nn.ishop.client.gui.barcode.AbstractBarcodeStrategy;
import com.nn.ishop.client.gui.barcode.BarcodeException;
import com.nn.ishop.client.gui.barcode.AbstractBarcodeStrategy.CharacterCode;


public class Code11 extends AbstractBarcodeStrategy {
    
    static final CharacterCode[] codes={
        new CharacterCode('0',new byte[]{1,1,1,1,2,1},0),    
        new CharacterCode('1',new byte[]{2,1,1,1,2,1},1),    
        new CharacterCode('2',new byte[]{1,2,1,1,2,1},2),    
        new CharacterCode('3',new byte[]{2,2,1,1,1,1},3),    
        new CharacterCode('4',new byte[]{1,1,2,1,2,1},4),    
        new CharacterCode('5',new byte[]{2,1,2,1,1,1},5),    
        new CharacterCode('6',new byte[]{1,2,2,1,1,1},6),    
        new CharacterCode('7',new byte[]{1,1,1,2,2,1},7),    
        new CharacterCode('8',new byte[]{2,1,1,2,1,1},8),    
        new CharacterCode('9',new byte[]{2,1,1,1,1,1},9),    
        new CharacterCode('-',new byte[]{1,1,2,1,1,1},10),    
        new CharacterCode('*',new byte[]{1,1,2,2,1,1},11),    
        new CharacterCode('$',new byte[]{0,1},12)};    

    protected String augmentWithChecksum(String text) throws BarcodeException {
        int checksumC=0;
        int checksumK=0;
        int weightC=1;
        int weightK=1;
        for(int i=text.length()-1;i>=0;i--){
            CharacterCode cc = getCharacterCode(text.charAt(i));
            checksumC+=cc.check*weightC;
            weightC++;
            if(weightC>10){
                weightC=1;
            }
        }
        checksumC=checksumC%11;
        CharacterCode codeC=getCharacterCode(checksumC);
        text+=codeC.character;
        for(int i=text.length()-1;i>=0;i--){
            CharacterCode cc = getCharacterCode(text.charAt(i));
            checksumK+=cc.check*weightK;
            weightK++;
            if(weightK>9){
                weightK=1;
            }
        }
        checksumK=checksumK%11;
        CharacterCode codeK=getCharacterCode(checksumK);
        if(text.length()>=10){
            text+=codeK.character;
        }
        return text;
    }

    protected String getBarcodeLabelText(String text) {
        return text;
    }

    protected CharacterCode[] getCodes() {
        return codes;
    }

    protected byte getMarginWidth() {
        return 0;
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
        return OPTIONAL_CHECKSUM;
    }

}
