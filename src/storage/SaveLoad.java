/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import crawl.CrawlResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JOptionPane;
import main.Interface;
import misc.NameFile;

/**
 *
 * @author Yap Jie Xiang
 */
public class SaveLoad {

    private static HashMap<String, String> map = new HashMap<>();

    public static Set<String> list() {
        return map.keySet();
    }

    public static void save(CrawlResult result) {
        // End if the given parameter is empty
        if (result == null || result.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Nothing to save");
            return;
        }

        // Get a unique identifier as the KEY in the hashmap
        String uniqueID = "";
        int index = 0;
        while (++index > 0) {
            // End if a unique name is found
            uniqueID = result.toString() + " #" + index;
            if (!map.containsKey(uniqueID)) {
                break;
            }
        }

        // Get a filename as the VALUE in the hashmap
        String fileName = NameFile.getNewSaveName();

        // Update and save the map
        map.put(uniqueID, fileName);
        saveMap();

        // Serialize the CrawlResult as fileName
        ResultSerializer serializer = new ResultSerializer(result, fileName);
        serializer.start();
    }

    public static void load(String uniqueID, Interface myInterface) {
        // End if given parameter is empty
        if (uniqueID == null || uniqueID.equals("")) {
            JOptionPane.showMessageDialog(null, "Error: Nothing to load");
            return;
        }

        // End if uniqueID does not exist in the map
        if (!map.containsKey(uniqueID)) {
            JOptionPane.showMessageDialog(null, "Error: Requested item is not registered");
            return;
        }

        // Retrieve CrawlResult
        ResultDeserializer deserializer = new ResultDeserializer(myInterface, map.get(uniqueID));
        deserializer.start();
    }

    public static void delete(String uniqueID) {
        // End if given parameter is empty
        if (uniqueID == null || uniqueID.equals("")) {
            JOptionPane.showMessageDialog(null, "Error: Nothing to delete");
            return;
        }

        // End if uniqueID does not exist in the map
        if (!map.containsKey(uniqueID)) {
            JOptionPane.showMessageDialog(null, "Error: Requested item is not registered");
            return;
        }

        // Delete the file from the system
        Path path = new File(map.get(uniqueID)).toPath();
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.out.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.out.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.out.println(x);
        }

        // Remove the KEY-VALUE pair from the hashmap and save the map
        map.remove(uniqueID);
        saveMap();

    }

    private static void saveMap() {
        try {
            FileOutputStream fos = new FileOutputStream(NameFile.getMapName());
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Serialize the Map
            oos.writeObject(map);

            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMap() {
        try (FileInputStream fis = new FileInputStream(NameFile.getMapName())) {
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Deserialize the Map
            map = (HashMap<String, String>) ois.readObject();

            ois.close();
            fis.close();

        } catch (FileNotFoundException e) {
            System.out.println("Serialized map not found");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "The file is corrupted");
        }
    }

}
