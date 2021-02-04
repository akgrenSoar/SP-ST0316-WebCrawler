/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawl;

import main.Interface;
import reader.PageRead;
import reader.WordCount;

/**
 *
 * @author Yap Jie Xiang
 */
public class FetchWebpageThread extends Thread {

    private final Interface myInterface;
    private final CrawlResult emptyResults;
    private final CrawlResult populatedResults;

    public FetchWebpageThread(String name, Interface myInterface, CrawlResult emptyResults, CrawlResult populatedResults) {
        super(name);
        this.myInterface = myInterface;
        this.emptyResults = emptyResults;
        this.populatedResults = populatedResults;
    }

    @Override
    public void run() {
        myInterface.reportStatus(Thread.currentThread().getName(), "Started");

        while (true) {

            // Get an empty webpage
            Webpage webpage;
            synchronized (emptyResults) {
                webpage = (Webpage) emptyResults.poll();
            }

            // Exit if webpage is null
            if (webpage == null) {
                break;
            }

            // Set html
            StringBuilder html = PageRead.readPage(webpage.getUrl());
            if (html == null) {
                html = new StringBuilder("");
            }
            webpage.setHtml(html.toString());

            // Set word count
            String searchString = populatedResults.getSearchString();
            int wordCount = WordCount.countOccurrence(searchString, html);
            webpage.setWordCount(wordCount);

            // Add populated webpage to populatedResults
            populatedResults.add(webpage);
            //myInterface.reportStatus(Thread.currentThread().getName(), "Fetched HTML of: " + webpage.toString());
            System.out.println(Thread.currentThread().getName() + " Fetched HTML of: " + webpage.toString());
        }
        myInterface.reportStatus(Thread.currentThread().getName(), "---Ended---");
    }

}
