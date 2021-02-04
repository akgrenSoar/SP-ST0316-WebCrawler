/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.Serializable;

/**
 *
 * @author Yap Jie Xiang
 */
public enum SearchEngine implements Serializable{

    //Duck("https://duckduckgo.com/?q=", "", 0),
    //Google("https://www.google.com.sg/#q=", "", 0),
    Yahoo("https://sg.search.yahoo.com/search?p=", "&b=", 10),
    Bing("https://www.bing.com/search?scope=web&q=", "&first=", 10),
    Ask("http://www.ask.com/web?q=", "&page=", 1);

    private final String url;
    private final String page;
    private final int increment;

    SearchEngine(String url, String page, int increment) {
        this.url = url;
        this.page = page;
        this.increment = increment;
    }

    /**
     *
     * @param searchString
     * @param pageNumber
     * @return
     */
    public String getURL(String searchString, int pageNumber) {
        searchString = searchString.replaceAll(" ", "+");
        pageNumber = 1 + ((pageNumber - 1) * increment);

        return url + searchString + page + pageNumber;
    }
    

}
