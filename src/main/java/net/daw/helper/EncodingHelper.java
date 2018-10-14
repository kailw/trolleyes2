/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.helper;

/**
 *
 * @author kevin
 */
public class EncodingHelper {

    public static String escapeQuotes(String str) {
        char[] chars = str.toCharArray();
        String strFinal = "";
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '"') {
                strFinal += String.valueOf("'");
            } else {
                strFinal += String.valueOf(chars[i]);
            }
        }
        return String.valueOf(strFinal);
    }

}
