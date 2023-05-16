package lsea.utils;

import lombok.*;

import java.io.Serializable;

/**
 * It is used to store the metrics of the application.
 */
@NoArgsConstructor
@Getter
@Setter
public class MetricsIndex implements Serializable {

    /**
     * Use timestamp as serial version UID
     */
    private long serialVersionUID = System.currentTimeMillis();

    /**
     * The memory usage percent of the application.
     */
    private double memoryUsagePercent;

    /**
     * The memory usage of the application.
     */
    private long memoryUsage;

    /**
     * The total memory of the application.
     */
    private long totalMemory;

    /**
     * The thread count of the application.
     */
    private int threadCount;

    /**
     * The request count of the application.
     */
    private int requestCount;

    /**
     * The request count of IndexController
     */
    private int indexRequestCount;

    /**
     * The request count of LogController
     */
    private int logRequestCount;

    /**
     * The request count of ManagementController
     */
    private int managementRequestCount;

    /**
     * The request count of UserController
     */
    private int userRequestCount;

    /**
     * The request count of WebsiteController
     */
    private int websiteRequestCount;

    /**
     * The request count of GET method count
     */
    private int getRequestCount;

    /**
     * The request count of POST method count
     */
    private int postRequestCount;

}
