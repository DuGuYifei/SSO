package lsea.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lsea.utils.MetricsIndex;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.*;

/**
 * Configuration class for metrics of Micrometer.
 */
@Configuration
public class MetricsConfig {

        /**
         * Create a jvm meterRegistry bean.
         *
         * @return A MeterRegistry bean.
         */
        @Bean
        public MeterRegistry jvmMeterRegistry() {
                MeterRegistry meterRegistry = new SimpleMeterRegistry();
                Gauge.builder("memory.total", Runtime.getRuntime().totalMemory(), value -> (double) value)
                                .description("The total memory of the application")
                                .register(meterRegistry);
                Gauge.builder("memory.usage", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(),
                                value -> (double) value)
                                .description("The memory usage of the application")
                                .register(meterRegistry);
                Gauge.builder("thread.count", Thread.activeCount(), value -> (double) value)
                                .description("The number of active threads")
                                .register(meterRegistry);
                return meterRegistry;
        }

        /**
         * Create a request meterRegistry bean.
         * 
         * @return A MeterRegistry bean.
         */
        @Bean
        public MeterRegistry requestMeterRegistry() {
                MeterRegistry meterRegistry = new SimpleMeterRegistry();

                Counter.builder("request.count")
                                .description("The number of requests")
                                .register(meterRegistry);

                Counter.builder("request.count")
                                .tags("controller", "IndexController")
                                .description("The number of to the IndexController")
                                .register(meterRegistry);

                Counter.builder("request.count")
                                .tags("controller", "LogController")
                                .description("The number of requests to the LogController")
                                .register(meterRegistry);

                Counter.builder("request.count")
                                .tags("controller", "ManagementController")
                                .description("The number of requests to the ManagementController")
                                .register(meterRegistry);

                Counter.builder("request.count")
                                .tags("controller", "UserController")
                                .description("The number of requests to the UserController")
                                .register(meterRegistry);

                Counter.builder("request.count")
                                .tags("controller", "WebsiteController")
                                .description("The number of requests to the WebsiteController")
                                .register(meterRegistry);

                Counter.builder("request.count")
                                .tags("method", "GET")
                                .description("The number of GET requests")
                                .register(meterRegistry);

                Counter.builder("request.count")
                                .tags("method", "POST")
                                .description("The number of POST requests")
                                .register(meterRegistry);

                return meterRegistry;
        }

        /**
         * Create a metricsIndex bean.
         *
         * @return A MetricsIndex bean.
         */
        @Bean
        public MetricsIndex metricsIndex() {
                return new MetricsIndex();
        }

        /**
         * Create a metrics datagram packet bean.
         *
         * @return A DatagramPacket bean.
         * @throws UnknownHostException If the host is unknown.
         */
        @Bean
        public DatagramPacket metricsDatagramPacket() throws UnknownHostException {
                InetAddress inetAddress = InetAddress.getByName("localhost");
                return new DatagramPacket(new byte[0], 0, inetAddress, 3001);
        }

        /**
         * Create a metrics datagram socket bean.
         *
         * @return A DatagramSocket bean.
         * @throws SocketException If the socket is not created.
         */
        @Bean
        public DatagramSocket metricsDatagramSocket() throws SocketException {
                return new DatagramSocket();
        }
}
