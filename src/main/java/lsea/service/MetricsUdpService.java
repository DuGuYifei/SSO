package lsea.service;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * This service is responsible for handling udp service for the metrics
 * which are used for the monitoring the sso project by client.
 */
/* Requirement 6 */
@Service
public class MetricsUdpService {

    /**
     * The metrics service of the application.
     */
    private final MetricsService metricsService;

    /**
     * The metrics datagram socket of the application.
     */
    private final DatagramSocket metricsDatagramSocket;

    /**
     * The metrics datagram packet of the application.
     */
    private final DatagramPacket metricsDatagramPacket;

    /**
     * The constructor of the MetricsUdpService class.
     * 
     * @param metricsService        the metrics service of the application
     * @param metricsDatagramSocket the metrics datagram socket of the client app
     * @param metricsDatagramPacket the metrics datagram packet used to send the
     *                              metrics to the client
     */
    public MetricsUdpService(MetricsService metricsService, DatagramSocket metricsDatagramSocket,
            DatagramPacket metricsDatagramPacket) {
        this.metricsService = metricsService;
        this.metricsDatagramSocket = metricsDatagramSocket;
        this.metricsDatagramPacket = metricsDatagramPacket;
        sendMetrics();
    }

    /**
     * Start a new thread to always send the packet to the client.
     */
    /* Requirement 6.UDP */
    public void sendMetrics() {
        new Thread(() -> {

            byte[] data;
            try {
                while (true) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream out = new ObjectOutputStream(bos);
                    metricsService.updateMetricsIndex();
                    out.writeObject(metricsService.getMetricsIndex());
                    data = bos.toByteArray();
                    out.flush();
                    metricsDatagramPacket.setData(data);
                    metricsDatagramSocket.send(metricsDatagramPacket);
                    bos.close();
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                metricsDatagramSocket.close();
            }
        }).start();
    }

}
