/*
 * Copyright (C) 2013 Piotr Wójcik
 * 
 * This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.comcraft.src;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

/**
 *
 * @author Piotr Wójcik (Queader)
 */
public class LangBundle {

    private Hashtable wordMap;
    private Hashtable defaultMap;

    public LangBundle() {
        wordMap = new Hashtable(100);
        defaultMap = new Hashtable(100);
    }

    private void loadMap(String fileName, Hashtable map) {
        map.clear();
        
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(LangBundle.class.getResourceAsStream(fileName), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            //#debug
//#             ex.printStackTrace();
            bufferedReader = new BufferedReader(new InputStreamReader(LangBundle.class.getResourceAsStream(fileName)));
        }

        while (!bufferedReader.getReachedTheEnd()) {
            String word = bufferedReader.readLine();

            if (word != null) {
                String key;
                String text;

                int firstIndex = word.indexOf("=");

                if (firstIndex != -1) {
                    key = word.substring(0, firstIndex);
                    text = word.substring(firstIndex + 1);

                    map.put(key, text);
                }
            }
        }
    }
    
    public void setDefaultMap(String fileName) {
        loadMap(fileName, defaultMap);
    }
    
    public void loadBundle(String fileName) {
        loadMap(fileName, wordMap);
    }

    public String getText(String key) {
        String word = (String) wordMap.get(key);

        if (word == null) {
            word = (String) defaultMap.get(key);
            
            if (word == null) {
                return "???";
            }
        }

        return word;
    }
}