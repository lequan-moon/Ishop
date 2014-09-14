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

import com.nn.ishop.client.gui.barcode.BarcodeException;

public class Code93Extended extends Code93 {
    
    static class MappedCode{
        char character;
        String encoded;
        MappedCode(char character, String encoded){
            this.character=character;
            this.encoded=encoded;
        }
    }
    private String getEncoded(char character){
        for(int i=0;i<CODES_EXTENDED.length;i++){
            if(CODES_EXTENDED[i].character==character){
                return CODES_EXTENDED[i].encoded;
            }
        }
        return "";
    }
    
    protected String preprocess(String text) throws BarcodeException {
        StringBuffer buffer=new StringBuffer();
        for(int i=0;i<text.length();i++){
            buffer.append(getEncoded(text.charAt(i)));
        }
        return buffer.toString();
    }
    

    private static MappedCode[] CODES_EXTENDED={
        new MappedCode((char)0    , SHIFT_DOLLAR+"U"),
        new MappedCode((char)1    , SHIFT_PERCENT+"A"),
        new MappedCode((char)2    , SHIFT_PERCENT+"B"),
        new MappedCode((char)3    , SHIFT_PERCENT+"C"),
        new MappedCode((char)4    , SHIFT_PERCENT+"D"),
        new MappedCode((char)5    , SHIFT_PERCENT+"E"),
        new MappedCode((char)6    , SHIFT_PERCENT+"F"),
        new MappedCode((char)7    , SHIFT_PERCENT+"G"),
        new MappedCode((char)8     , SHIFT_PERCENT+"H"),
        new MappedCode((char)9     , SHIFT_PERCENT+"I"),
        new MappedCode((char)10     , SHIFT_PERCENT+"J"),
        new MappedCode((char)11     , SHIFT_PERCENT+"K"),
        new MappedCode((char)12     , SHIFT_PERCENT+"L"),
        new MappedCode((char)13     , SHIFT_PERCENT+"M"),
        new MappedCode((char)14     , SHIFT_PERCENT+"N"),
        new MappedCode((char)15     , SHIFT_PERCENT+"O"),
        new MappedCode((char)16    , SHIFT_PERCENT+"P"),
        new MappedCode((char)17    , SHIFT_PERCENT+"Q"),
        new MappedCode((char)18    , SHIFT_PERCENT+"R"),
        new MappedCode((char)19    , SHIFT_PERCENT+"S"),
        new MappedCode((char)20    , SHIFT_PERCENT+"T"),
        new MappedCode((char)21    , SHIFT_PERCENT+"U"),
        new MappedCode((char)22    , SHIFT_PERCENT+"V"),
        new MappedCode((char)23    , SHIFT_PERCENT+"W"),
        new MappedCode((char)24    , SHIFT_PERCENT+"X"),
        new MappedCode((char)25     , SHIFT_PERCENT+"Y"),
        new MappedCode((char)26    , SHIFT_PERCENT+"Z"),
        new MappedCode((char)27    , SHIFT_DOLLAR+"A"),
        new MappedCode((char)28     , SHIFT_DOLLAR+"B"),
        new MappedCode((char)29     , SHIFT_DOLLAR+"C"),
        new MappedCode((char)30     , SHIFT_DOLLAR+"D"),
        new MappedCode((char)31     , SHIFT_DOLLAR+"E"),
        new MappedCode(' ' , " "),
        new MappedCode('!'      , SHIFT_SLASH+"A"),
        new MappedCode('"'      , SHIFT_SLASH+"B"),
        new MappedCode('#'      , SHIFT_SLASH+"C"),
        new MappedCode('$'      , SHIFT_SLASH+"D"),
        new MappedCode('%'      , SHIFT_SLASH+"E"),
        new MappedCode('&'      , SHIFT_SLASH+"F"),
        new MappedCode('\''      , SHIFT_SLASH+"G"),
        new MappedCode('('      , SHIFT_SLASH+"H"),
        new MappedCode(')'      , SHIFT_SLASH+"I"),
        new MappedCode('*'      , SHIFT_SLASH+"J"),
        new MappedCode('+'      , SHIFT_SLASH+"K"),
        new MappedCode(','      , SHIFT_SLASH+"L"),
        new MappedCode('-'    ,  "-"),
        new MappedCode('.'   ,   "."),
        new MappedCode('/'      , SHIFT_SLASH+"O"),
        new MappedCode('0'   ,   "0"),
        new MappedCode('1'   ,   "1"),
        new MappedCode('2'   ,   "2"),
        new MappedCode('3'   ,   "3"),
        new MappedCode('4'   ,   "4"),
        new MappedCode('5'   ,   "5"),
        new MappedCode('6'   ,   "6"),
        new MappedCode('7'   ,   "7"),
        new MappedCode('8'   ,   "8"),
        new MappedCode('9'   ,   "9"),
        new MappedCode(':'      , SHIFT_SLASH+"Z"),
        new MappedCode(';'      , SHIFT_DOLLAR+"F"),
        new MappedCode('<'      , SHIFT_DOLLAR+"G"),
        new MappedCode('='      , SHIFT_DOLLAR+"H"),
        new MappedCode('>'      , SHIFT_DOLLAR+"I"),
        new MappedCode('?'      , SHIFT_DOLLAR+"J"),
        new MappedCode('@'      , SHIFT_DOLLAR+"V"),
        new MappedCode('A'  ,    "A"),
        new MappedCode('B'  ,    "B"),
        new MappedCode('C'  ,    "C"),
        new MappedCode('D'  ,    "D"),
        new MappedCode('E'  ,    "E"),
        new MappedCode('F'  ,    "F"),
        new MappedCode('G'  ,    "G"),
        new MappedCode('H'  ,    "H"),
        new MappedCode('I'  ,    "I"),
        new MappedCode('J'  ,    "J"),
        new MappedCode('K'  ,    "K"),
        new MappedCode('L'  ,    "L"),
        new MappedCode('M'  ,    "M"),
        new MappedCode('N'  ,    "N"),
        new MappedCode('O'  ,    "O"),
        new MappedCode('P'  ,    "P"),
        new MappedCode('Q' ,     "Q"),
        new MappedCode('R'  ,    "R"),
        new MappedCode('S'  ,    "S"),
        new MappedCode('T'  ,    "T"),
        new MappedCode('U'  ,    "U"),
        new MappedCode('V'  ,    "V"),
        new MappedCode('W'  ,    "W"),
        new MappedCode('X'  ,    "X"),
        new MappedCode('Y'  ,    "Y"),
        new MappedCode('Z'  ,    "Z"),
        new MappedCode('['      , SHIFT_DOLLAR+"K"),
        new MappedCode('\\'      , SHIFT_DOLLAR+"L"),
        new MappedCode(']'      , SHIFT_DOLLAR+"M"),
        new MappedCode('^'      , SHIFT_DOLLAR+"N"),
        new MappedCode('_'      , SHIFT_DOLLAR+"O"),
        new MappedCode('`'      , SHIFT_DOLLAR+"W"),
        new MappedCode('a'      , SHIFT_PLUS+"A"),
        new MappedCode('b'      , SHIFT_PLUS+"B"),
        new MappedCode('c'      , SHIFT_PLUS+"C"),
        new MappedCode('d'      , SHIFT_PLUS+"D"),
        new MappedCode('e'      , SHIFT_PLUS+"E"),
        new MappedCode('f'      , SHIFT_PLUS+"F"),
        new MappedCode('g'      , SHIFT_PLUS+"G"),
        new MappedCode('h'      , SHIFT_PLUS+"H"),
        new MappedCode('i'      , SHIFT_PLUS+"I"),
        new MappedCode('j'      , SHIFT_PLUS+"J"),
        new MappedCode('k'      , SHIFT_PLUS+"K"),
        new MappedCode('l'      , SHIFT_PLUS+"L"),
        new MappedCode('m'      , SHIFT_PLUS+"M"),
        new MappedCode('n'      , SHIFT_PLUS+"N"),
        new MappedCode('o'      , SHIFT_PLUS+"O"),
        new MappedCode('p'      , SHIFT_PLUS+"P"),
        new MappedCode('q'      , SHIFT_PLUS+"Q"),
        new MappedCode('r'      , SHIFT_PLUS+"R"),
        new MappedCode('s'      , SHIFT_PLUS+"S"),
        new MappedCode('t'     , SHIFT_PLUS+"T"),
        new MappedCode('u'      , SHIFT_PLUS+"U"),
        new MappedCode('v'      , SHIFT_PLUS+"V"),
        new MappedCode('w'      , SHIFT_PLUS+"W"),
        new MappedCode('x'      , SHIFT_PLUS+"X"),
        new MappedCode('y'      , SHIFT_PLUS+"Y"),
        new MappedCode('z'      , SHIFT_PLUS+"Z"),
        new MappedCode('{'      , SHIFT_DOLLAR+"P"),
        new MappedCode('|'      , SHIFT_DOLLAR+"Q"),
        new MappedCode('}'      , SHIFT_DOLLAR+"R"),
        new MappedCode('~'      , SHIFT_DOLLAR+"S"),
        new MappedCode((char)127    , SHIFT_DOLLAR+"T")
    };
 
}
