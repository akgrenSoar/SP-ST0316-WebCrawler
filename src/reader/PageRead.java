package reader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class PageRead {

    @SuppressWarnings("CallToPrintStackTrace")
    public static StringBuilder readPage(String pageAddr) {

        StringBuilder sb = null;

        try {
            URL url = new URL(pageAddr);
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                reader.close();
            } catch (IOException e) {
                System.out.println(Thread.currentThread().getName());
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            System.out.println(Thread.currentThread().getName());
            e.printStackTrace();
        }

        return sb;
    }

}
