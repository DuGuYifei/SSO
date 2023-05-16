package client.metrics;

import lsea.utils.MetricsIndex;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * This class is used to receive the metrics of the server by udp transference.
 */
public class MetricsUdpReceiver {

    /**
     * The metrics index of the SSO server.
     */
    private MetricsIndex metricsIndex;

    /**
     * The metrics datagram socket to receive packet from server.
     */
    private final DatagramSocket metricsDatagramSocket;

    /**
     * The metrics datagram packet to fill the data from server.
     */
    private final DatagramPacket metricsDatagramPacket;

    /**
     * The constructor of the class.
     *
     * @param port The port of the udp client receiver.
     */
    public MetricsUdpReceiver(int port) {
        try {
            this.metricsDatagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        this.metricsDatagramPacket = new DatagramPacket(new byte[1024], 1024);
        this.metricsIndex = new MetricsIndex();
        receiveMetrics();
    }

    /**
     * The getter of the metrics index.
     *
     * @return The metrics index of the SSO server.
     */
    public MetricsIndex getMetricsIndex() {
        return metricsIndex;
    }

    /**
     * Start a new thread to always receive the packet from the server.
     */
    public void receiveMetrics() {
        new Thread(() -> {
            byte[] myObj;
            while (true) {
                try {
                    metricsDatagramSocket.receive(metricsDatagramPacket);
                    myObj = new byte[metricsDatagramPacket.getLength()];
                    System.arraycopy(metricsDatagramPacket.getData(), 0, myObj, 0, metricsDatagramPacket.getLength());
                    ByteArrayInputStream bis = new ByteArrayInputStream(myObj);
                    ObjectInput in = new ObjectInputStream(bis);
                    metricsIndex = (MetricsIndex) in.readObject();
                    in.close();
                    bis.close();
                    metricsDatagramPacket.setLength(1024);
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
            metricsDatagramSocket.close();
        }).start();
    }

}
