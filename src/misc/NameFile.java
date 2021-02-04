/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Yap Jie Xiang
 */
public class NameFile {

    private static final String DIRECTORY_NAME = "./savedata";
    private static final String SERIALIZED_NAME = "data";
    private static final String NAME_EXTENSION = ".ser";
    private static int count = 0;

    public static String getSessionName(){
        return DIRECTORY_NAME + "/" + "session" + NAME_EXTENSION;
    }
    
    public static String getMapName() {
        return DIRECTORY_NAME + "/" + SERIALIZED_NAME + NAME_EXTENSION;
    }

    public static String getNewSaveName() {

        // Make sure the directory exist
        if (!new File(DIRECTORY_NAME).exists()) {
            boolean success = (new File(DIRECTORY_NAME)).mkdirs();
            if (!success) {
                JOptionPane.showMessageDialog(null, "Failed to create directory '" + DIRECTORY_NAME + "'");
                return null;
            }
        }

        File f;
        String firstPart = DIRECTORY_NAME + "/" + SERIALIZED_NAME;
        String s;

        // Keep running until an unused name is found
        while (true) {
            // Construct another file name
            s = firstPart + ++count + NAME_EXTENSION;

            // Check if file exist
            f = new File(s);
            if (!f.exists()) {
                return s; // return the name if exist
            }
        }

    }

}
