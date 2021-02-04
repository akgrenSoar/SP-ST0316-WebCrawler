/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawl;

import java.util.ArrayList;
import main.Interface;
import main.SearchEngine;
import reader.LinkExtract;
import reader.PageRead;

/**
 *
 * @author Yap Jie Xiang
 */
public class FetchQueryThread extends Thread {

    private final Interface myInterface;
    private final String searchString;
    private final SearchEngine searchEngine;
    private final CrawlResult crawlResult;

    public FetchQueryThread(String name, Interface myInterface, String searchString, SearchEngine searchEngine, CrawlResult crawlResult) {
        super(name);
        this.myInterface = myInterface;
        this.searchString = searchString;
        this.searchEngine = searchEngine;
        this.crawlResult = crawlResult;
    }

    @Override
    public void run() {
        class NextPage {

            private int pageNum = 0;

            public ArrayList<String> get() {
                StringBuilder html = PageRead.readPage(searchEngine.getURL(searchString, pageNum++));
                return LinkExtract.extractLink(html, 0);
            }
        }
        myInterface.reportStatus(Thread.currentThread().getName(), "Started");
        
        // Fetch result
        NextPage nextPage = new NextPage();
        ArrayList<String> links = nextPage.get();
        int index = 0;

        // Repeat until queue is full
        while (crawlResult.size() < 12) {
            // Go to next page if all links are used
            if (index >= links.size()) {
                links = nextPage.get();
                index = 0;
            }

            // Add webpage to queue
            Webpage webpage = new Webpage(links.get(index++));
            synchronized (crawlResult) {
                // End if queue size is 12
                if (crawlResult.size() == 12) {
                    break;
                }

                // Check if queue contains the webpage
                if (crawlResult.contains(webpage)) {
                    // Increment index and proceed to next result
                    index++;
                    continue;
                }

                // Add webpage to queue
                crawlResult.offer(webpage);
                //myInterface.reportStatus(Thread.currentThread().getName(), "Added to Queue: " + webpage.toString());
                System.out.println(Thread.currentThread().getName() + " Added to Queue: " + webpage.toString());
            }
        }

        myInterface.reportStatus(Thread.currentThread().getName(), "---Ended---");
    }

}
