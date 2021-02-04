/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Yap Jie Xiang
 */
public class LinkExtract {

    // Must be enclosed in href="..."
    // First character must be an alphabet (Cannot start with slash)
    // [\\w\\/\\.\\,\\:%?=&-_]+     ->  All possible characters in a url
    // (?<!\\.(css))                    ->    Does not end with .css
    private static final String LINKPATTERN = "href *= *\\\"([a-zA-Z][\\w\\/\\.\\,\\:%?=&-_]+(?<!\\.(css)))\\\"";
    private static final Pattern PATTERN = Pattern.compile(LINKPATTERN);

    // Any url that contains these Strings will be discarded
    private static final String[] blackList = {
        "http://about.ask.com/jobs",
        "http://help.ask.com",
        "javascript:",
        "http://choice.microsoft.com",
        ".r.msn.com/?ld=",
        "http://go.microsoft.com/",
        "http://www.bing.com/toolbox/bing-site-safety?url=",
        "https://sg.search.yahoo.com/",
        "https://sg.images.search.yahoo.com/search/images?",
        "http://help.yahoo.com/mkb/product.php?",
        "http://sg.m.yahoo.com/w/web/privacy?"};

    /**
     *
     * @param html
     * @param count The maximum number of links. Unlimited links if value is
     * lesser or equal to 0
     * @return
     */
    public static ArrayList<String> extractLink(StringBuilder html, int count) {

        int current = 0;
        Matcher matcher = PATTERN.matcher(html);

        // Add all matches to ArrayList<String>
        ArrayList<String> results = new ArrayList<>();
        while (matcher.find()) {
            // Get the result
            String result = matcher.group(1);

            // Check the result is not in blacklist
            boolean isBlackListed = false;
            for (String s : blackList) {
                if (result.contains(s)) {
                    isBlackListed = true;
                    break;
                }
            }
            // Skip current result is in blacklist
            if (isBlackListed) {
                continue;
            }

            // Add result to ArrayList
            results.add(removeEndingSlash(result));

            // If reach max, exit prematurely
            if (++current == count) {
                return results;
            }
        }

        // Return results
        return results;
    }

    private static String removeEndingSlash(String url) {
        int size = url.length();
        if (url.charAt(size - 1) == '/') {
            return url.substring(0, size - 1);
        }
        return url;
    }
}
