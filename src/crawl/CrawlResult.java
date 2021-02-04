/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawl;

import java.io.Serializable;
import java.util.PriorityQueue;
import main.SearchEngine;

/**
 *
 * @author Yap Jie Xiang
 */
public class CrawlResult extends PriorityQueue implements Serializable {

    private final String searchString;
    private final SearchEngine se1;
    private final SearchEngine se2;

    public CrawlResult(String searchString, SearchEngine se1, SearchEngine se2) {
        super(12);
        this.searchString = searchString;
        this.se1 = se1;
        this.se2 = se2;
    }

    public String getSearchString() {
        return searchString;
    }

    public SearchEngine getSe1() {
        return se1;
    }

    public SearchEngine getSe2() {
        return se2;
    }

    @Override
    public String toString() {
        return searchString + "{" + se1 + " & " + se2 + '}';
    }

}
