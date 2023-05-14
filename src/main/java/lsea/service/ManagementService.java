package lsea.service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lsea.dto.GenerateReportDto;
import lsea.entity.Log;
import lsea.entity.User;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.repository.LogRepository;
import lsea.repository.UserRepository;
import lsea.utils.GlobalPermissions;
import lsea.utils.ListResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

/**
 * Service layer for all business actions regarding management
 * which includes statistics and analysis.
 */
@Service
public class ManagementService {

  /**
   * The LogRepository attribute is used to access the LogRepository methods.
   */
  private final LogRepository logRepository;

  /**
   * The UserRepository attribute is used to access the UserRepository methods.
   */
  private final UserRepository userRepository;

  /**
   * The poolSize attribute is used to set the number of threads to use.
   */
  private final int poolSize = 40;

  /**
   * The executor attribute is used to create a thread pool.
   */
  private final ExecutorService executor = Executors.newFixedThreadPool(poolSize);

  /**
   * The constructor of the ManagementService class.
   *
   * @param logRepository  LogRepository
   * @param userRepository UserRepository
   */
  public ManagementService(
      LogRepository logRepository,
      UserRepository userRepository) {
    this.logRepository = logRepository;
    this.userRepository = userRepository;
  }

  /**
   * The analysis method is used to get the five longest logs.
   *
   * @param numThreads number of threads to use
   * @param token      String containing the token
   * @param logs       List of logs, if null will get all logs from the database
   * @return ListResult object containing the five longest logs
   * @throws GenericForbiddenError if the user does not have the permission
   * @throws InterruptedException  if the thread is interrupted
   * @throws GenericNotFoundError  if the user is not found
   */
  /* Requirement 4.1 */
  /* Requirement 4.2 */
  public ListResult longestFiveLogs(String token, int numThreads, List<Log> logs)
      throws InterruptedException, GenericNotFoundError, GenericForbiddenError {
    if (logs == null) {
      UUID userId = User.verifyToken(token);

      /* Requirement 4.3 */
      Optional<User> user = userRepository.findById(userId);

      if (!user.isPresent()) {
        throw new GenericNotFoundError("User not found");
      }

      if (user.get().getGlobalPermission() <= GlobalPermissions.MODERATOR) {
        throw new GenericForbiddenError("Permission denied");
      }

      logs = logRepository.findAll();
    }

    int resultNum = 5;

    ListResult response = new ListResult();
    if (logs.size() <= resultNum) {
      response.setCount(logs.size());
      response.setData(Arrays.asList(logs.toArray()));
      response.setMeta(
          new HashMap<String, Object>() {
            {
              put("startTime", System.currentTimeMillis());
              put("duration", 0);
              put("endTime", System.currentTimeMillis());
            }
          });
      return response;
    }

    response.setCount(resultNum);
    List<List<Log>> subLogs = new ArrayList<>();
    for (int i = 0; i < numThreads; i++) {
      subLogs.add(new ArrayList<>());
    }
    int n = logs.size();
    for (int i = 0; i < n; i++) {
      subLogs.get(i % numThreads).add(logs.get(i));
    }

    PriorityQueue<Log> pqLogs = new PriorityQueue<>(
        resultNum + 1,
        (a, b) -> a.getData().length() - b.getData().length());

    /* Requirement 4.1.2 */
    CountDownLatch latch = new CountDownLatch(numThreads);
    long start = System.currentTimeMillis();
    for (int i = 0; i < numThreads; i++) {
      int finalI = i;
      List<List<Log>> finalSubLogs = subLogs;
      executor.execute(() -> subLongestFiveLogs(finalSubLogs.get(finalI), pqLogs, resultNum, latch));
    }
    latch.await();
    long end = System.currentTimeMillis();
    long duration = end - start;
    /* Requirement 4.1.2 */
    response.setMeta(
        new HashMap<String, Object>() {
          {
            put("startTime", start);
            put("duration", duration);
            put("endTime", end);
          }
        });

    List<Log> result = new ArrayList<>();
    // this loop is to add the element from the priority queue to the result list
    // if use pqLogs.toArray(), the order of the elements in the array is not
    // guaranteed
    while (!pqLogs.isEmpty()) {
      result.add(pqLogs.poll());
    }
    response.setData(Arrays.asList(result.toArray()));

    return response;
  }

  /**
   * The shortestFiveLogs method is used to get the five shortest logs.
   *
   * @param token      to authenticate the user
   * @param numThreads int
   * @param logs       List of logs, if null will get all logs from the database
   * @return ListResult object containing the five shortest logs
   * @throws InterruptedException  if the thread is interrupted
   * @throws GenericNotFoundError  if the user is not found
   * @throws GenericForbiddenError if the user does not have the permission
   */
  public ListResult shortestFiveLogs(String token, int numThreads, List<Log> logs)
      throws InterruptedException, GenericNotFoundError, GenericForbiddenError {
    if (logs == null) {
      UUID userId = User.verifyToken(token);

      /* Requirement 4.3 */
      Optional<User> user = userRepository.findById(userId);

      if (!user.isPresent()) {
        throw new GenericNotFoundError("User not found");
      }

      if (user.get().getGlobalPermission() <= GlobalPermissions.MODERATOR) {
        throw new GenericForbiddenError("Permission denied");
      }

      logs = logRepository.findAll();
    }

    int resultNum = 5;

    ListResult response = new ListResult();
    if (logs.size() <= resultNum) {
      response.setCount(logs.size());
      response.setData(Arrays.asList(logs.toArray()));
      response.setMeta(
          new HashMap<String, Object>() {
            {
              put("startTime", System.currentTimeMillis());
              put("duration", 0);
              put("endTime", System.currentTimeMillis());
            }
          });
      return response;
    }

    response.setCount(resultNum);
    List<List<Log>> subLogs = new ArrayList<>();
    for (int i = 0; i < numThreads; i++) {
      subLogs.add(new ArrayList<>());
    }
    int n = logs.size();
    for (int i = 0; i < n; i++) {
      subLogs.get(i % numThreads).add(logs.get(i));
    }

    PriorityQueue<Log> pqLogs = new PriorityQueue<>(
        resultNum + 1,
        (a, b) -> b.getData().length() - a.getData().length());

    /* Requirement 4.1.2 */
    CountDownLatch latch = new CountDownLatch(numThreads);
    long start = System.currentTimeMillis();
    for (int i = 0; i < numThreads; i++) {
      int finalI = i;
      List<List<Log>> finalSubLogs = subLogs;
      executor.execute(() -> subShortestFiveLogs(finalSubLogs.get(finalI), pqLogs, resultNum, latch));
    }
    latch.await();
    long end = System.currentTimeMillis();
    long duration = end - start;
    /* Requirement 4.1.2 */
    response.setMeta(
        new HashMap<String, Object>() {
          {
            put("startTime", start);
            put("duration", duration);
            put("endTime", end);
          }
        });

    List<Log> result = new ArrayList<>();
    // this loop is to add the element from the priority queue to the result list
    // if use pqLogs.toArray(), the order of the elements in the array is not
    // guaranteed
    while (!pqLogs.isEmpty()) {
      result.add(pqLogs.poll());
    }
    response.setData(Arrays.asList(result.toArray()));

    return response;
  }

  /**
   * The subLongestFiveLogs method is used to get the five longest logs.
   *
   * @param logs      List<Log> - the list of logs
   * @param response  PriorityQueue<Log> - the priority queue of logs
   * @param resultNum int
   */
  /* Requirement 4.1 */
  /* Requirement 4.2 */
  private void subLongestFiveLogs(
      List<Log> logs,
      PriorityQueue<Log> response,
      int resultNum,
      CountDownLatch latch) {
    /* Requirement 4.1.1 */
    System.out.println(
        "Thread " +
            Thread.currentThread().getId() +
            " is running for " +
            logs.size() +
            " logs");
    if (logs.size() == 0) {
      return;
    }
    PriorityQueue<Log> pq = new PriorityQueue<>(
        resultNum,
        (a, b) -> a.getData().length() - b.getData().length());
    for (Log log : logs) {
      if (pq.size() < resultNum) {
        pq.add(log);
      } else {
        if (log.getData().length() > pq.peek().getData().length()) {
          pq.poll();
          pq.add(log);
        }
      }
    }

    /* Requirement 4.2 */
    synchronized (response) {
      /* Requirement 4.1.1 */
      System.out.println(
          "Thread " +
              Thread.currentThread().getId() +
              " is trying to access the public asset - final PQ for 5 longest logs");
      while (!pq.isEmpty()) {
        response.add(pq.poll());
        if (response.size() > resultNum) {
          response.poll();
        }
      }
      latch.countDown();
    }
  }

  /**
   * The subShortestFiveLogs method is used to get the five shortest logs.
   *
   * @param logs      List of logs
   * @param response  PriorityQueue of logs
   * @param resultNum number of results
   */
  private void subShortestFiveLogs(
      List<Log> logs,
      PriorityQueue<Log> response,
      int resultNum,
      CountDownLatch latch) {
    /* Requirement 4.1.1 */
    System.out.println(
        "Thread " +
            Thread.currentThread().getId() +
            " is running for " +
            logs.size() +
            " logs");
    if (logs.size() == 0) {
      return;
    }
    PriorityQueue<Log> pq = new PriorityQueue<>(
        resultNum,
        (a, b) -> b.getData().length() - a.getData().length());
    for (Log log : logs) {
      if (pq.size() < resultNum) {
        pq.add(log);
      } else {
        if (log.getData().length() < pq.peek().getData().length()) {
          pq.poll();
          pq.add(log);
        }
      }
    }

    /* Requirement 4.2 */
    synchronized (response) {
      /* Requirement 4.1.1 */
      System.out.println(
          "Thread " +
              Thread.currentThread().getId() +
              " is trying to access the public asset - final PQ for 5 shortest logs");
      while (!pq.isEmpty()) {
        response.add(pq.poll());
        if (response.size() > resultNum) {
          response.poll();
        }
      }
    }
    latch.countDown();
  }

  /**
   * The reportCalculator method is used to calculate the average time of the
   * longestFiveLogs method or the shortestFiveLogs method.
   *
   * @param maximumNumberOfThreads the maximum number of threads
   * @param iterations             the number of iterations
   * @param token                  the token of the user
   * @return a list contains two maps of the average time of
   *         the longestFiveLogs method and the shortestFiveLogs method
   * @throws GenericForbiddenError if the token is invalid
   * @throws GenericNotFoundError  if the token is not found
   * @throws InterruptedException  if the thread is interrupted
   */
  public List<Map<Integer, String>> reportCalculator(int maximumNumberOfThreads, int iterations, String token)
      throws GenericForbiddenError, GenericNotFoundError, InterruptedException {
    UUID userId = User.verifyToken(token);

    /* Requirement 4.3 */
    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      throw new GenericNotFoundError("User not found");
    }

    if (user.get().getGlobalPermission() <= GlobalPermissions.MODERATOR) {
      throw new GenericForbiddenError("Permission denied");
    }

    List<Log> logs = logRepository.findAll();

    List<Map<Integer, String>> result = new ArrayList<>();
    result.add(new HashMap<>());
    result.add(new HashMap<>());

    ListResult listResult;

    for (int numThreads = 1; numThreads <= maximumNumberOfThreads; numThreads++) {
      int sumOfDurationsLongest = 0;
      int sumOfDurationsShortest = 0;
      for (int i = 0; i < iterations; i++) {
        listResult = longestFiveLogs(token, numThreads, logs);
        String duration = listResult.getMeta().get("duration").toString();
        sumOfDurationsLongest += Integer.parseInt(duration);

        listResult = shortestFiveLogs(token, numThreads, logs);
        duration = listResult.getMeta().get("duration").toString();
        sumOfDurationsShortest += Integer.parseInt(duration);
      }

      int average = sumOfDurationsLongest / iterations;
      result.get(0).put(numThreads, String.valueOf(average));
      average = sumOfDurationsShortest / iterations;
      result.get(1).put(numThreads, String.valueOf(average));
    }
    return result;
  }

  /**
   * The generateReport method is used to generate a report.
   *
   * @param dto            GenerateReportDto
   * @param resultLongest  Number of a thread to the time it takes to run
   * @param resultShortest Result Number of a thread to the time it takes to run
   * @param iterations     Number of iterations per thread
   * @return an excel file with all necessary information
   */
  /* Requirement 4.4 */
  public Workbook generateReport(
      GenerateReportDto dto,
      Map<Integer, String> resultLongest,
      Map<Integer, String> resultShortest,
      int iterations) {
    int numThreads = dto.getNumThreads();
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet longestSpreadsheet = workbook.createSheet(
        "Analysis - Longest Logs");

    Row rowLongest = longestSpreadsheet.createRow((short) 0);
    Cell cellLongest = rowLongest.createCell((short) 0);
    cellLongest.setCellValue("Threads number");
    cellLongest = rowLongest.createCell((short) 1);
    cellLongest.setCellValue("Time(ms) - Average, measured for: " + iterations + " iterations");

    for (int i = 1; i <= numThreads; i++) {
      rowLongest = longestSpreadsheet.createRow((short) i);
      cellLongest = rowLongest.createCell((short) 0);
      cellLongest.setCellValue(i);
      cellLongest = rowLongest.createCell((short) 1);
      cellLongest.setCellValue(Integer.parseInt(resultLongest.get(i)));
    }

    XSSFDrawing drawingLongest = longestSpreadsheet.createDrawingPatriarch();
    XSSFClientAnchor anchorLongest = drawingLongest.createAnchor(0, 0, 0, 0, 4, 0, 18, 25);

    XSSFChart chartLongest = drawingLongest.createChart(anchorLongest);
    chartLongest.setTitleText("Analysis of threads number performance (longest)");
    chartLongest.setTitleOverlay(false);

    XDDFChartLegend legend = chartLongest.getOrAddLegend();
    legend.setPosition(LegendPosition.TOP_RIGHT);

    XDDFCategoryAxis bottomAxisLongest = chartLongest.createCategoryAxis(AxisPosition.BOTTOM);
    bottomAxisLongest.setTitle("Threads number");
    XDDFValueAxis leftAxisLongest = chartLongest.createValueAxis(AxisPosition.LEFT);
    leftAxisLongest.setTitle("Duration time(ms) - average");

    XDDFDataSource<String> threadsLongest = XDDFDataSourcesFactory.fromStringCellRange(
        longestSpreadsheet,
        new CellRangeAddress(1, numThreads, 0, 0));

    XDDFNumericalDataSource<Double> timeLongest = XDDFDataSourcesFactory.fromNumericCellRange(
        longestSpreadsheet,
        new CellRangeAddress(1, numThreads, 1, 1));

    XDDFLineChartData data = (XDDFLineChartData) chartLongest.createData(
        ChartTypes.LINE,
        bottomAxisLongest,
        leftAxisLongest);

    XDDFLineChartData.Series series1Longest = (XDDFLineChartData.Series) data.addSeries(
        threadsLongest,
        timeLongest);
    series1Longest.setTitle("Duration", null);
    series1Longest.setSmooth(false);
    series1Longest.setMarkerStyle(MarkerStyle.STAR);

    chartLongest.plot(data);

    XSSFSheet shortestSpreadsheet = workbook.createSheet(
        " Analysis - Shortest Logs ");

    Row rowShortest = shortestSpreadsheet.createRow((short) 0);
    Cell cellShortest = rowShortest.createCell((short) 0);
    cellShortest.setCellValue("Threads number");
    cellShortest = rowShortest.createCell((short) 1);
    cellShortest.setCellValue("Time(ms) - Average, measured for: " + iterations + " iterations");
    for (int i = 1; i <= numThreads; i++) {
      rowShortest = shortestSpreadsheet.createRow((short) i);
      cellShortest = rowShortest.createCell((short) 0);
      cellShortest.setCellValue(i);
      cellShortest = rowShortest.createCell((short) 1);
      cellShortest.setCellValue(Integer.parseInt(resultShortest.get(i)));
    }

    XSSFDrawing drawingShortest = shortestSpreadsheet.createDrawingPatriarch();
    XSSFClientAnchor anchorShortest = drawingShortest.createAnchor(
        0,
        0,
        0,
        0,
        4,
        0,
        18,
        25);

    XSSFChart chartShortest = drawingShortest.createChart(anchorShortest);
    chartShortest.setTitleText(
        "Analysis of threads number performance (shortest)");
    chartShortest.setTitleOverlay(false);

    XDDFChartLegend legendShortest = chartShortest.getOrAddLegend();
    legendShortest.setPosition(LegendPosition.TOP_RIGHT);

    XDDFCategoryAxis bottomAxisShortest = chartShortest.createCategoryAxis(
        AxisPosition.BOTTOM);
    bottomAxisShortest.setTitle("Threads number");
    XDDFValueAxis leftAxisShortest = chartShortest.createValueAxis(
        AxisPosition.LEFT);
    leftAxisShortest.setTitle("Duration time(ms) - average");

    XDDFDataSource<String> threadsShortest = XDDFDataSourcesFactory.fromStringCellRange(
        shortestSpreadsheet,
        new CellRangeAddress(1, numThreads, 0, 0));

    XDDFNumericalDataSource<Double> timeShortest = XDDFDataSourcesFactory.fromNumericCellRange(
        shortestSpreadsheet,
        new CellRangeAddress(1, numThreads, 1, 1));

    XDDFLineChartData dataShortest = (XDDFLineChartData) chartShortest.createData(
        ChartTypes.LINE,
        bottomAxisShortest,
        leftAxisShortest);

    XDDFLineChartData.Series series1Shortest = (XDDFLineChartData.Series) dataShortest.addSeries(
        threadsShortest,
        timeShortest);
    series1Shortest.setTitle("Duration", null);
    series1Shortest.setSmooth(false);
    series1Shortest.setMarkerStyle(MarkerStyle.STAR);

    chartShortest.plot(dataShortest);

    return workbook;
  }
}
