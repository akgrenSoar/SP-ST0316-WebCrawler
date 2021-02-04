/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import crawl.CrawlResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Yap Jie Xiang
 */
/**
 * Serializes CrawlResult
 */
public class ResultSerializer extends Thread {

    private final String fileName;
    private final CrawlResult crawlResult;

    public ResultSerializer(CrawlResult crawlResult, String fileName) {
        super("Serializer");
        this.fileName = fileName;
        this.crawlResult = crawlResult;
    }

    @Override
    @SuppressWarnings({"ConvertToTryWithResources", "CallToPrintStackTrace"})
    public void run() {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Serialize the crawl result
            oos.writeObject(crawlResult);

            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}// End of ResultSerializable class
