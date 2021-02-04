/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import crawl.CrawlResult;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JOptionPane;
import main.Interface;

/**
 *
 * @author Yap Jie Xiang
 */
/**
 * Deserializes CrawlResult and updates the Interface
 */
public class ResultDeserializer extends Thread {

    private final String fileName;
    private final Interface myInterface;

    public ResultDeserializer(Interface myInterface, String fileName) {
        super("Deserializer");
        this.fileName = fileName;
        this.myInterface = myInterface;
    }

    @Override
    @SuppressWarnings({"ConvertToTryWithResources", "CallToPrintStackTrace"})
    public void run() {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Deserialize the CrawlSnapshot object
            CrawlResult crawlResult = (CrawlResult) ois.readObject();

            ois.close();
            fis.close();

            // Load results to GUI
            myInterface.reportResult(crawlResult);

        } catch (FileNotFoundException e) {
            System.out.println("Serialized objects not found");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(myInterface, "The file is corrupted");
        }

    } // End of run()

}// End of ResultDeserializer class
