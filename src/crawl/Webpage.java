/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawl;

import java.io.Serializable;
import java.util.Objects;

/**
 * Holds a webpage url and the webpage html. Two webpages are equivalent if it
 * has the same url.
 *
 * @author Yap Jie Xiang
 */
public class Webpage implements Serializable, Comparable {

    private final String url;
    private String html = "";
    private int wordCount = -1;

    public Webpage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    @Override
    public String toString() {
        return url;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.url);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Webpage other = (Webpage) obj;
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        Webpage w = (Webpage) o;
        return this.url.compareTo(w.url);
    }

}
