package lsea.service;

import lsea.entity.Log;
import lsea.entity.User;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.repository.LogRepository;
import lsea.repository.UserRepository;
import lsea.utils.GlobalPermissions;
import lsea.utils.ListResult;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service layer for all business actions regarding management
 * which includes statistics and analysis.
 */
/* Requirement 4_5 */
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
     * @param logRepository LogRepository
     * @param userRepository UserRepository
     */
    public ManagementService(LogRepository logRepository, UserRepository userRepository) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    /**
     * This method is used to get the longest five logs.
     * @return ListResult object
     * @throws InterruptedException if the thread is interrupted
     */
    public ListResult longestFiveLogs(String token) throws InterruptedException, GenericNotFoundError, GenericForbiddenError {

        UUID userId = User.verifyToken(token);

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new GenericNotFoundError("User not found");
        }

        if(user.get().getGlobalPermission() <= GlobalPermissions.MODERATOR){
            throw new GenericForbiddenError("Permission denied");
        }

        int resultNum = 5;

        List<Log> logs = logRepository.findAll();

        ListResult response = new ListResult();
        if(logs.size() <= 5){
            response.setCount(logs.size());
            response.setData(Arrays.asList(logs.toArray()));
            response.setMeta(new HashMap<String, Object>(){{
                put("startTime", System.currentTimeMillis());
                put("duration", 0);
                put("endTime", System.currentTimeMillis());
            }});
            return response;
        }

        response.setCount(resultNum);
        List<List<Log>> subLogs = new ArrayList<>();
        int numThreads = 2;
        for (int i = 0; i < numThreads; i++) {
            subLogs.add(new ArrayList<>());
        }
        int n = logs.size();
        for(int i = 0; i < n; i++){
            subLogs.get(i % numThreads).add(logs.get(i));
        }
        List<Thread> threads = new ArrayList<>();
        PriorityQueue<Log> pqLogs = new PriorityQueue<>(resultNum + 1, (a, b) -> a.getData().length() - b.getData().length());
        long start = System.currentTimeMillis();
        for (int i = 0; i < numThreads; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> subLongestFiveLogs(subLogs.get(finalI), pqLogs, resultNum));
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        long end = System.currentTimeMillis();
        long duration = end-start;
        response.setMeta(new HashMap<String, Object>(){{
            put("startTime", start);
            put("duration", duration);
            put("endTime", end);
        }});
        List<Log> result = new ArrayList<>();
        // this loop is to add the element from the priority queue to the result list
        // if use pqLogs.toArray(), the order of the elements in the array is not guaranteed
        while (!pqLogs.isEmpty()) {
            result.add(pqLogs.poll());
        }
        response.setData(Arrays.asList(result.toArray()));
        return response;
    }

    private void subLongestFiveLogs(List<Log> logs, PriorityQueue<Log> response, int resultNum) {
        if (logs.size() == 0) {
            return;
        }
        PriorityQueue<Log> pq = new PriorityQueue<>(resultNum, (a, b) -> a.getData().length() - b.getData().length());
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
        synchronized (response){
            while (!pq.isEmpty()) {
                response.add(pq.poll());
                if(response.size() > resultNum){
                    response.poll();
                }
            }
        }
    }

}
