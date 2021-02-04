/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Yap Jie Xiang
 */
public class WordCount {

    /**
     * Count the number of word occurrence in a word document. Includes words
     * found within tags i.e. '<' and '>'
     *
     * @param keyword
     * @param document
     * @return Number of occurrences
     */
    public static int countOccurrence(String keyword, StringBuilder document) {

        // Compile pattern
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(document);

        // Iterate all matches, adding one to result
        int result = 0;
        while (matcher.find()) {
            result++;
        }

        // return result
        return result;
    }

}
