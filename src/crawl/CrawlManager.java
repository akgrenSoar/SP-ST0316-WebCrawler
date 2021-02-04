/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawl;

import main.Interface;
import main.SearchEngine;
import misc.StopWatch;

/**
 *
 * @author Yap Jie Xiang
 */
public class CrawlManager extends Thread {

    private final Interface myInterface;
    private final String keyword;
    private final SearchEngine se1;
    private final SearchEngine se2;
    private final int numThreads;

    public CrawlManager(Interface myInterface, String keyword, SearchEngine se1, SearchEngine se2, int numThreads) {
        this.myInterface = myInterface;
        this.keyword = keyword;
        this.se1 = se1;
        this.se2 = se2;
        this.numThreads = numThreads;
    }

    @Override
    public void run() {
        myInterface.reportStatus("Searching "+ keyword, se1.toString() + " & " + se2.toString());
        StopWatch sw = new StopWatch(myInterface);
        sw.start();
        
        // Get twelve unique webpage links
        CrawlResult emptyResults = new CrawlResult(keyword, se1, se2);

        FetchQueryThread q1 = new FetchQueryThread("Query1", myInterface, keyword, se1, emptyResults);
        FetchQueryThread q2 = new FetchQueryThread("Query2", myInterface, keyword, se2, emptyResults);

        q1.start();
        q2.start();

        try {
            if (q1.isAlive()) {
                q1.join();
            }
            if (q2.isAlive()) {
                q2.join();
            }
        } catch (InterruptedException e) {
            // Do nothing
        }

        // Get the html and wordcount of the twelve unique webpage links
        CrawlResult populatedResults = new CrawlResult(keyword, se1, se2);

        FetchWebpageThread[] w = new FetchWebpageThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            w[i] = new FetchWebpageThread("Fetcher" + i, myInterface, emptyResults, populatedResults);
            w[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                if (w[i].isAlive()) {
                    w[i].join();
                }
            } catch (InterruptedException e) {
                // Do nothing
            }
        }

        myInterface.reportStatus("Crawl Ended", sw.stop() + "\n");
        myInterface.reportResult(populatedResults);
    }

}
