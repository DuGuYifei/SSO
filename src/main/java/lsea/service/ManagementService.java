package lsea.service;

import java.util.*;
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
   * The constructor of the ManagementService class.
   *
   * @param logRepository  LogRepository
   * @param userRepository UserRepository
   */
  public ManagementService(
    LogRepository logRepository,
    UserRepository userRepository
  ) {
    this.logRepository = logRepository;
    this.userRepository = userRepository;
  }

  /**
   * The analysis method is used to get the five longest logs.
   *
   * @param numThreads number of threads to use
   * @param token      String containing the token
   * @return ListResult object containing the five longest logs
   * @throws GenericForbiddenError if the user does not have the permission
   * @throws InterruptedException  if the thread is interrupted
   * @throws GenericNotFoundError  if the user is not found
   */
  /* Requirement 4.1 */
  /* Requirement 4.2 */
  public ListResult longestFiveLogs(String token, int numThreads)
    throws InterruptedException, GenericNotFoundError, GenericForbiddenError {
    UUID userId = User.verifyToken(token);

    /* Requirement 4.3 */
    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      throw new GenericNotFoundError("User not found");
    }

    if (user.get().getGlobalPermission() <= GlobalPermissions.MODERATOR) {
      throw new GenericForbiddenError("Permission denied");
    }

    int resultNum = 5;

    List<Log> logs = logRepository.findAll();

    ListResult response = new ListResult();
    if (logs.size() <= 5) {
      response.setCount(logs.size());
      response.setData(Arrays.asList(logs.toArray()));
      response.setMeta(
        new HashMap<String, Object>() {
          {
            put("startTime", System.currentTimeMillis());
            put("duration", 0);
            put("endTime", System.currentTimeMillis());
          }
        }
      );
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
    List<Thread> threads = new ArrayList<>();
    PriorityQueue<Log> pqLogs = new PriorityQueue<>(
      resultNum + 1,
      (a, b) -> a.getData().length() - b.getData().length()
    );

    /* Requirement 4.1.2 */
    long start = System.currentTimeMillis();
    for (int i = 0; i < numThreads; i++) {
      int finalI = i;
      Thread thread = new Thread(() ->
        subLongestFiveLogs(subLogs.get(finalI), pqLogs, resultNum)
      );
      thread.start();
      threads.add(thread);
    }
    for (Thread thread : threads) {
      thread.join();
    }
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
      }
    );
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
   * @param token    to authenticate the user
   * @param numThreads int
   * @return ListResult object containing the five shortest logs
   * @throws InterruptedException if the thread is interrupted
   * @throws GenericNotFoundError if the user is not found
   * @throws GenericForbiddenError if the user does not have the permission
   */
  public ListResult shortestFiveLogs(String token, int numThreads)
    throws InterruptedException, GenericNotFoundError, GenericForbiddenError {
    UUID userId = User.verifyToken(token);

    /* Requirement 4.3 */
    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      throw new GenericNotFoundError("User not found");
    }

    if (user.get().getGlobalPermission() <= GlobalPermissions.MODERATOR) {
      throw new GenericForbiddenError("Permission denied");
    }

    int resultNum = 5;

    List<Log> logs = logRepository.findAll();

    ListResult response = new ListResult();
    if (logs.size() <= 5) {
      response.setCount(logs.size());
      response.setData(Arrays.asList(logs.toArray()));
      response.setMeta(
        new HashMap<String, Object>() {
          {
            put("startTime", System.currentTimeMillis());
            put("duration", 0);
            put("endTime", System.currentTimeMillis());
          }
        }
      );
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
    List<Thread> threads = new ArrayList<>();
    PriorityQueue<Log> pqLogs = new PriorityQueue<>(
      resultNum + 1,
      (a, b) -> a.getData().length() - b.getData().length()
    );

    /* Requirement 4.1.2 */
    long start = System.currentTimeMillis();
    for (int i = 0; i < numThreads; i++) {
      int finalI = i;
      Thread thread = new Thread(() ->
        subShortestFiveLogs(subLogs.get(finalI), pqLogs, resultNum)
      );
      thread.start();
      threads.add(thread);
    }
    for (Thread thread : threads) {
      thread.join();
    }
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
      }
    );
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
   * @param logs      List<Log>
   * @param response  PriorityQueue<Log>
   * @param resultNum int
   */
  /* Requirement 4.1 */
  /* Requirement 4.2 */
  private void subLongestFiveLogs(
    List<Log> logs,
    PriorityQueue<Log> response,
    int resultNum
  ) {
    /* Requirement 4.1.1 */
    System.out.println(
      "Thread " +
      Thread.currentThread().getId() +
      " is running for " +
      logs.size() +
      " logs"
    );
    if (logs.size() == 0) {
      return;
    }
    PriorityQueue<Log> pq = new PriorityQueue<>(
      resultNum,
      (a, b) -> a.getData().length() - b.getData().length()
    );
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
        " is try to access the public asset - final PQ for 5 longest logs"
      );
      while (!pq.isEmpty()) {
        response.add(pq.poll());
        if (response.size() > resultNum) {
          response.poll();
        }
      }
    }
  }

  /**
   * The subShortestFiveLogs method is used to get the five shortest logs.
   *
   * @param logs List of logs
   * @param response PriorityQueue of logs
   * @param resultNum number of results
   */
  private void subShortestFiveLogs(
    List<Log> logs,
    PriorityQueue<Log> response,
    int resultNum
  ) {
    /* Requirement 4.1.1 */
    System.out.println(
      "Thread " +
      Thread.currentThread().getId() +
      " is running for " +
      logs.size() +
      " logs"
    );
    if (logs.size() == 0) {
      return;
    }
    PriorityQueue<Log> pq = new PriorityQueue<>(
      resultNum,
      (a, b) -> b.getData().length() - a.getData().length()
    );
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
        " is try to access the public asset - final PQ for 5 shortest logs"
      );
      while (!pq.isEmpty()) {
        response.add(pq.poll());
        if (response.size() > resultNum) {
          response.poll();
        }
      }
    }
  }

  /**
   * The generateReport method is used to generate a report.
   *
   * @param dto   GenerateReportDto
   * @param longestResult Number of a thread to the time it takes to run
   * @param shortestResult Result Number of a thread to the time it takes to run
   * @return an excel file with all necessary information
   */
  /* Requirement 4.4 */
  public Workbook generateReport(
    GenerateReportDto dto,
    Map<Integer, String> longestResult,
    Map<Integer, String> shortestResult
  ) {
    int numThreads = dto.getNumThreads();
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet longestSpreadsheet = workbook.createSheet(
      " Analysis - Longest Logs "
    );

    Row row = longestSpreadsheet.createRow((short) 0);
    Cell cell = row.createCell((short) 0);
    cell.setCellValue("Threads number");
    cell = row.createCell((short) 1);
    cell.setCellValue("Time(ms)");

    for (int i = 1; i <= numThreads; i++) {
      row = longestSpreadsheet.createRow((short) i);
      cell = row.createCell((short) 0);
      cell.setCellValue(i);
      cell = row.createCell((short) 1);
      cell.setCellValue(Integer.parseInt(longestResult.get(i)));
    }

    XSSFDrawing drawing = longestSpreadsheet.createDrawingPatriarch();
    XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 4, 0, 18, 25);

    XSSFChart chart = drawing.createChart(anchor);
    chart.setTitleText("Analysis of threads number performance (longest)");
    chart.setTitleOverlay(false);

    XDDFChartLegend legend = chart.getOrAddLegend();
    legend.setPosition(LegendPosition.TOP_RIGHT);

    XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
    bottomAxis.setTitle("Threads number");
    XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
    leftAxis.setTitle("Duration time(ms)");

    XDDFDataSource<String> threads = XDDFDataSourcesFactory.fromStringCellRange(
      longestSpreadsheet,
      new CellRangeAddress(1, numThreads, 0, 0)
    );

    XDDFNumericalDataSource<Double> time = XDDFDataSourcesFactory.fromNumericCellRange(
      longestSpreadsheet,
      new CellRangeAddress(1, numThreads, 1, 1)
    );

    XDDFLineChartData data = (XDDFLineChartData) chart.createData(
      ChartTypes.LINE,
      bottomAxis,
      leftAxis
    );

    XDDFLineChartData.Series series1 = (XDDFLineChartData.Series) data.addSeries(
      threads,
      time
    );
    series1.setTitle("Duration", null);
    series1.setSmooth(false);
    series1.setMarkerStyle(MarkerStyle.STAR);

    chart.plot(data);

    XSSFSheet shortestSpreadsheet = workbook.createSheet(
      " Analysis - Shortest Logs "
    );

    Row rowShortest = shortestSpreadsheet.createRow((short) 0);
    Cell cellShortest = rowShortest.createCell((short) 0);
    cellShortest.setCellValue("Threads number");
    cellShortest = rowShortest.createCell((short) 1);
    cellShortest.setCellValue("Time(ms)");

    for (int i = 1; i <= numThreads; i++) {
      rowShortest = longestSpreadsheet.createRow((short) i);
      cellShortest = rowShortest.createCell((short) 0);
      cellShortest.setCellValue(i);
      cellShortest = rowShortest.createCell((short) 1);
      cellShortest.setCellValue(Integer.parseInt(shortestResult.get(i)));
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
      25
    );

    XSSFChart chartShortest = drawingShortest.createChart(anchorShortest);
    chartShortest.setTitleText(
      "Analysis of threads number performance (shortest)"
    );
    chartShortest.setTitleOverlay(false);

    XDDFChartLegend legendShortest = chartShortest.getOrAddLegend();
    legendShortest.setPosition(LegendPosition.TOP_RIGHT);

    XDDFCategoryAxis bottomAxisShortest = chartShortest.createCategoryAxis(
      AxisPosition.BOTTOM
    );
    bottomAxisShortest.setTitle("Threads number");
    XDDFValueAxis leftAxisShortest = chartShortest.createValueAxis(
      AxisPosition.LEFT
    );
    leftAxisShortest.setTitle("Duration time(ms)");

    XDDFDataSource<String> threadsShortest = XDDFDataSourcesFactory.fromStringCellRange(
      shortestSpreadsheet,
      new CellRangeAddress(1, numThreads, 0, 0)
    );

    XDDFNumericalDataSource<Double> timeShortest = XDDFDataSourcesFactory.fromNumericCellRange(
      shortestSpreadsheet,
      new CellRangeAddress(1, numThreads, 1, 1)
    );

    XDDFLineChartData dataShortest = (XDDFLineChartData) chartShortest.createData(
      ChartTypes.LINE,
      bottomAxisShortest,
      leftAxisShortest
    );

    XDDFLineChartData.Series series1Shortest = (XDDFLineChartData.Series) dataShortest.addSeries(
      threadsShortest,
      timeShortest
    );
    series1Shortest.setTitle("Duration", null);
    series1Shortest.setSmooth(false);
    series1Shortest.setMarkerStyle(MarkerStyle.STAR);

    chartShortest.plot(dataShortest);

    return workbook;
  }
}
