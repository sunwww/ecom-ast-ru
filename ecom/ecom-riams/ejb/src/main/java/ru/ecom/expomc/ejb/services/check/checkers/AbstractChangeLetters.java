package ru.ecom.expomc.ejb.services.check.checkers;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Замена букв из хэша
 */
public class AbstractChangeLetters extends AbstractChangeStringProperty {

    public AbstractChangeLetters(Map<Character, Character> aMap) {
        map = aMap;
    }

    public AbstractChangeLetters() {
        map = new HashMap<>();
    }

    void put(char aSource, char aDest) {
        map.put(aSource, aDest);
    }

    void revert() {
        TreeMap<Character, Character> revert = new TreeMap<>();
        for (Map.Entry<Character, Character> entry : map.entrySet()) {
            revert.put(entry.getKey(), entry.getValue());
        }
        map.clear();
        for (Map.Entry<Character, Character> entry : revert.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
    }

    public String transform(String aStr) {
        int len = aStr.length();
        StringBuilder sb = new StringBuilder(aStr);
        for (int i = 0; i < len; i++) {
            char ch = sb.charAt(i);
            Character repChar = map.get(ch);
            if (repChar != null) {
                sb.setCharAt(i, repChar);
            }
        }
        return sb.toString();

    }

    private final Map<Character, Character> map;
}
