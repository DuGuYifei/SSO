package lsea.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lsea.utils.MetricsIndex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * This service is responsible for handling service for the metrics
 * which are used for the monitoring the sso project.
 */
@Service
public class MetricsService {

    /**
     * The metrics index of the application.
     */
    private final MetricsIndex metricsIndex;

    /**
     * The jvm meter registry of the application.
     */
    @Resource
    private MeterRegistry jvmMeterRegistry;

    /**
     * The request meter registry of the application.
     */
    @Resource
    private MeterRegistry requestMeterRegistry;

    /**
     * The constructor of the MetricsService class.
     *
     */
    public MetricsService(MetricsIndex metricsIndex) {
        this.metricsIndex = metricsIndex;
    }

    /**
     * It returns the metrics index of the application.
     *
     * @return the metrics index of the application.
     */
    public MetricsIndex getMetricsIndex() {
        return metricsIndex;
    }

    /**
     * Update metricsIndex
     */
    public void updateMetricsIndex() {
        updateMemoryMetrics();
        updateThreadMetrics();
        updateRequestMetrics();
        metricsIndex.setSerialVersionUID(System.currentTimeMillis());
    }

    /**
     * Update memory metricsIndex.
     */
    public void updateMemoryMetrics() {
        if(Double.isNaN(jvmMeterRegistry.get("memory.total").gauge().value())){
            jvmMeterRegistry.remove(jvmMeterRegistry.get("memory.total").gauge());
            jvmMeterRegistry.remove(jvmMeterRegistry.get("memory.usage").gauge());
            Gauge.builder("memory.usage",  Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), value -> (double)value)
                    .description("The memory usage of the application")
                    .register(jvmMeterRegistry);
            Gauge.builder("memory.total",  Runtime.getRuntime().totalMemory(), value -> (double)value)
                    .description("The total memory of the application")
                    .register(jvmMeterRegistry);
        }

        this.metricsIndex.setMemoryUsage((long)jvmMeterRegistry.get("memory.usage").gauge().value());
        this.metricsIndex.setTotalMemory((long)jvmMeterRegistry.get("memory.total").gauge().value());
        this.metricsIndex.setMemoryUsagePercent((double)this.metricsIndex.getMemoryUsage() / this.metricsIndex.getTotalMemory());
    }

    /**
     * Update thread metricsIndex.
     */
    public void updateThreadMetrics() {
        this.metricsIndex.setThreadCount((int) jvmMeterRegistry.get("thread.count").gauge().value());
    }

    /**
     * Update request metricsIndex.
     */
    public void updateRequestMetrics() {
        this.metricsIndex.setRequestCount((int) requestMeterRegistry.counter("request.count").count());
        this.metricsIndex.setIndexRequestCount((int) requestMeterRegistry.counter("request.count", "controller", "IndexController").count());
        this.metricsIndex.setLogRequestCount((int) requestMeterRegistry.counter("request.count", "controller", "LogController").count());
        this.metricsIndex.setManagementRequestCount((int) requestMeterRegistry.counter("request.count", "controller", "ManagementController").count());
        this.metricsIndex.setUserRequestCount((int) requestMeterRegistry.counter("request.count", "controller", "UserController").count());
        this.metricsIndex.setWebsiteRequestCount((int) requestMeterRegistry.counter("request.count", "controller", "WebsiteController").count());
        this.metricsIndex.setGetRequestCount((int) requestMeterRegistry.counter("request.count", "method", "GET").count());
        this.metricsIndex.setPostRequestCount((int) requestMeterRegistry.counter("request.count", "method", "POST").count());
    }

}
