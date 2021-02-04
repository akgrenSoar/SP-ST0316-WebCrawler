/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Yap Jie Xiang
 */
public class WebBrowser {

    /**
     * Opens the link in your default browser
     *
     * @param link the html link to open
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static void openOnline(String link) {
        // Opens a the default browser of the link
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Opens the webpage in your default browser
     *
     * @param webpageSource the source code of the webpage you want to viewF
     */
    @SuppressWarnings({"ConvertToTryWithResources", "CallToPrintStackTrace"})
    public static void openOffline(String webpageSource) {

        File file = new File("tmp.html");

        // Write the webpage into a file
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(webpageSource);

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Open the file with the browser
        openOnline(file.toURI().toString());
    }

}
