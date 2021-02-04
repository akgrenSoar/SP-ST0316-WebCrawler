/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import java.time.Duration;
import java.time.Instant;
import main.Interface;

/**
 *
 * @author Yap Jie Xiang
 */
public class StopWatch {

    private static final boolean showMessage = false;

    private final Interface myInterface;
    private Instant start;
    private Instant end;

    public StopWatch(Interface myInterface) {
        this.myInterface = myInterface;
    }

    public void start() {
        if (showMessage) {
            myInterface.reportStatus(Thread.currentThread().getName(), "---StopWatch Started---");
        }
        start = Instant.now();
    }

    public String stop() {
        // Get end timing
        end = Instant.now();
        if (showMessage) {
            myInterface.reportStatus(Thread.currentThread().getName(), "---StopWatch Stopped---");
        }

        // Calculate time elapsed
        Duration duration = Duration.between(start, end);

        // Display time elapsed
        String sec = duration.getSeconds() + ".";
        String nanoSec = "" + duration.getNano();
        String microSec = nanoSec.substring(0, 3);

        // return the duration
        return sec + microSec;
    }

}
