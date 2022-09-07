package homeWork;


import java.util.*;

public class ThirdTask {
            /*
        Task3
            Реализовать функцию нечеткого поиска

                    fuzzySearch("car", "ca6$$#_rtwheel"); // true
                    fuzzySearch("cwhl", "cartwheel"); // true
                    fuzzySearch("cwhee", "cartwheel"); // true
                    fuzzySearch("cartwheel", "cartwheel"); // true
                    fuzzySearch("cwheeel", "cartwheel"); // false
                    fuzzySearch("lw", "cartwheel"); // false
         */

    public static void main(String[] args) {
        System.out.println(fuzzySearch("car", "ca6$$#_rtwheel", Locale.getDefault()));
        System.out.println(fuzzySearch("cwhl", "cartwheel", Locale.getDefault()));
        System.out.println(fuzzySearch("cwhee", "cartwheel", Locale.getDefault()));
        System.out.println(fuzzySearch("cartwheel", "cartwheel", Locale.getDefault()));
        System.out.println(fuzzySearch("cwheeel", "cartwheel", Locale.getDefault()));
        System.out.println(fuzzySearch("lw", "cartwheel", Locale.getDefault()));

        //My examples
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");

        System.out.println(fuzzySearch("кот", "краснота", Locale.getDefault()));
        System.out.println(fuzzySearch("ееееп", "ееекКшкафП", Locale.getDefault()));
        System.out.println(fuzzySearch("жы-шы", "жыпышы", Locale.getDefault()));
        System.out.println(fuzzySearch("што?", "неШто?кай!", Locale.getDefault()));
        System.out.println(fuzzySearch("шашаша", "шашаааа", Locale.getDefault()));
        System.out.println(fuzzySearch("й", "йй", Locale.getDefault()));

    }

    // I guess it is the simplest implementation
    // the condition of this task was kinda "fuzzy" and we can only guess what we have to do with
    // spase symbols and such as dash "-"... but it also special detail that won't change the main algorithm
    // that's why mind out about it =).
    public static boolean fuzzySearch(String subString, String string, Locale locale) {
        if (string == null || subString == null || subString.length() > string.length()) {
            throw new IllegalArgumentException("None of arguments should be null or subString has to has length less then main");
        }
        char[] stringArray = string.toLowerCase(locale).toCharArray();
        char[] subStringArray = subString.toLowerCase(locale).toCharArray();
        char[] allMatches = new char[subStringArray.length];

        int subStrIndex = 0;

        for (char c : stringArray) {
            if (c == subStringArray[subStrIndex] && subStrIndex <= subStringArray.length - 1) {
                allMatches[subStrIndex] = c;
                if (subStrIndex == subStringArray.length - 1) break;
                subStrIndex++;
            }
        }
        return String.copyValueOf(allMatches).equalsIgnoreCase(subString);
    }
}