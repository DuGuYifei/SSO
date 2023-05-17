package client.metrics;

import lsea.utils.MetricsIndex;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class is used to show the metrics of the server in JFrame.
 */
/* Requirement 6 */
public class MetricsFrame {

    /**
     * The monitor frame of the metrics.
     * 
     * @param metricsUdpReceiver The metrics udp receiver.
     */
    public static void showDynamicDataWindow(MetricsUdpReceiver metricsUdpReceiver) {
        // create a frame
        JFrame frame = new JFrame("server monitor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // create label to show the metrics index
        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        Font monospaceFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        label.setFont(monospaceFont);
        frame.getContentPane().add(label);

        AtomicReference<MetricsIndex> metricsIndex = new AtomicReference<>();

        // timer, update the data every second
        Timer timer = new Timer(1000, e -> {
            // update the data in monitor
            metricsIndex.set(metricsUdpReceiver.getMetricsIndex());
            label.setText(
                    "<html>" +
                            "Usage percent of memory: " + metricsIndex.get().getMemoryUsagePercent() + "<br>" +
                            "-------[Total of memory: " + metricsIndex.get().getTotalMemory() + "]<br>" +
                            "Thread using: " + metricsIndex.get().getThreadCount() + "<br>" +
                            "Request count: " + metricsIndex.get().getRequestCount() + "<br>" +
                            "-------IndexController: " + metricsIndex.get().getIndexRequestCount() + "<br>" +
                            "---------LogController: " + metricsIndex.get().getLogRequestCount() + "<br>" +
                            "--ManagementController: " + metricsIndex.get().getManagementRequestCount() + "<br>" +
                            "--------UserController: " + metricsIndex.get().getUserRequestCount() + "<br>" +
                            "-----WebsiteController: " + metricsIndex.get().getWebsiteRequestCount() + "<br>" +
                            "-------------------GET: " + metricsIndex.get().getGetRequestCount() + "<br>" +
                            "------------------POST: " + metricsIndex.get().getPostRequestCount()
                            + "</html>");

            // refresh the frame
            frame.revalidate();
        });

        // start the timer
        timer.start();

        // show the frame
        frame.setVisible(true);
    }
}