# TEST RES

## n=100 c=10

This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/logs
Document Length:        56 bytes

Concurrency Level:      10
Time taken for tests:   0.602 seconds
Complete requests:      100
Failed requests:        0
Non-2xx responses:      100
Total transferred:      16100 bytes
Total body sent:        44200
HTML transferred:       5600 bytes
Requests per second:    166.22 [#/sec] (mean)
Time per request:       60.160 [ms] (mean)
Time per request:       6.016 [ms] (mean, across all concurrent requests)
Transfer rate:          26.13 [Kbytes/sec] received
71.75 kb/s sent
97.88 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    0   0.0      0       0
Processing:     4   24  39.8     18     400
Waiting:        4   21  38.6     14     388
Total:          4   24  39.8     18     400

Percentage of the requests served within a certain time (ms)
50%     18
66%     24
75%     30
80%     34
90%     40
95%     45
98%     52
99%    400
100%    400 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users
Document Length:        41 bytes

Concurrency Level:      10
Time taken for tests:   0.545 seconds
Complete requests:      100
Failed requests:        99
(Connect: 0, Receive: 0, Length: 99, Exceptions: 0)
Non-2xx responses:      99
Total transferred:      19550 bytes
Total body sent:        23800
HTML transferred:       9050 bytes
Requests per second:    183.37 [#/sec] (mean)
Time per request:       54.535 [ms] (mean)
Time per request:       5.453 [ms] (mean, across all concurrent requests)
Transfer rate:          35.01 [Kbytes/sec] received
42.62 kb/s sent
77.63 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    0   0.0      0       0
Processing:     4   25  36.8     20     354
Waiting:        4   22  33.8     17     317
Total:          4   25  36.8     20     354

Percentage of the requests served within a certain time (ms)
50%     20
66%     25
75%     28
80%     30
90%     51
95%     67
98%     73
99%    354
100%    354 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/authorize
Document Length:        63 bytes

Concurrency Level:      10
Time taken for tests:   0.508 seconds
Complete requests:      100
Failed requests:        0
Non-2xx responses:      100
Total transferred:      16800 bytes
Total body sent:        47500
HTML transferred:       6300 bytes
Requests per second:    196.92 [#/sec] (mean)
Time per request:       50.781 [ms] (mean)
Time per request:       5.078 [ms] (mean, across all concurrent requests)
Transfer rate:          32.31 [Kbytes/sec] received
91.35 kb/s sent
123.65 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    0   0.0      0       0
Processing:     3   50  33.4     48     137
Waiting:        3   48  33.4     47     137
Total:          3   50  33.4     48     137

Percentage of the requests served within a certain time (ms)
50%     48
66%     68
75%     72
80%     76
90%     98
95%    117
98%    128
99%    137
100%    137 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/ban
Document Length:        63 bytes

Concurrency Level:      10
Time taken for tests:   0.569 seconds
Complete requests:      100
Failed requests:        0
Non-2xx responses:      100
Total transferred:      16800 bytes
Total body sent:        47300
HTML transferred:       6300 bytes
Requests per second:    175.85 [#/sec] (mean)
Time per request:       56.865 [ms] (mean)
Time per request:       5.687 [ms] (mean, across all concurrent requests)
Transfer rate:          28.85 [Kbytes/sec] received
81.23 kb/s sent
110.08 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    0   0.2      0       1
Processing:     2   55  30.1     57     138
Waiting:        2   54  30.1     56     138
Total:          3   55  30.1     57     138

Percentage of the requests served within a certain time (ms)
50%     57
66%     65
75%     72
80%     74
90%     87
95%    119
98%    132
99%    138
100%    138 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/unban
Document Length:        56 bytes

Concurrency Level:      10
Time taken for tests:   0.181 seconds
Complete requests:      100
Failed requests:        0
Non-2xx responses:      100
Total transferred:      16100 bytes
Total body sent:        44500
HTML transferred:       5600 bytes
Requests per second:    553.11 [#/sec] (mean)
Time per request:       18.080 [ms] (mean)
Time per request:       1.808 [ms] (mean, across all concurrent requests)
Transfer rate:          86.96 [Kbytes/sec] received
240.37 kb/s sent
327.33 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    0   0.0      0       0
Processing:     2   15  13.3     13      76
Waiting:        2   12  10.2      8      53
Total:          2   15  13.3     13      76

Percentage of the requests served within a certain time (ms)
50%     13
66%     16
75%     19
80%     24
90%     34
95%     42
98%     53
99%     76
100%     76 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/websites
Document Length:        63 bytes

Concurrency Level:      10
Time taken for tests:   0.522 seconds
Complete requests:      100
Failed requests:        0
Non-2xx responses:      100
Total transferred:      16800 bytes
Total body sent:        46800
HTML transferred:       6300 bytes
Requests per second:    191.47 [#/sec] (mean)
Time per request:       52.227 [ms] (mean)
Time per request:       5.223 [ms] (mean, across all concurrent requests)
Transfer rate:          31.41 [Kbytes/sec] received
87.51 kb/s sent
118.92 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    0   0.1      0       1
Processing:     6   47  24.8     51     109
Waiting:        6   47  24.9     51     109
Total:          6   47  24.8     51     109

Percentage of the requests served within a certain time (ms)
50%     51
66%     61
75%     65
80%     67
90%     77
95%     84
98%     98
99%    109
100%    109 (longest request)

## n=1000 c=100
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/logs
Document Length:        56 bytes

Concurrency Level:      100
Time taken for tests:   0.896 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      161000 bytes
Total body sent:        442000
HTML transferred:       56000 bytes
Requests per second:    1115.84 [#/sec] (mean)
Time per request:       89.618 [ms] (mean)
Time per request:       0.896 [ms] (mean, across all concurrent requests)
Transfer rate:          175.44 [Kbytes/sec] received
481.64 kb/s sent
657.08 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    1   2.2      0      10
Processing:     8   84  34.9     83     190
Waiting:        5   83  34.7     82     190
Total:          8   85  34.5     83     190

Percentage of the requests served within a certain time (ms)
50%     83
66%     97
75%    107
80%    112
90%    131
95%    147
98%    166
99%    173
100%    190 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users
Document Length:        91 bytes

Concurrency Level:      100
Time taken for tests:   0.952 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      196000 bytes
Total body sent:        238000
HTML transferred:       91000 bytes
Requests per second:    1050.35 [#/sec] (mean)
Time per request:       95.206 [ms] (mean)
Time per request:       0.952 [ms] (mean, across all concurrent requests)
Transfer rate:          201.04 [Kbytes/sec] received
244.12 kb/s sent
445.17 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    3   8.2      0      52
Processing:     8   88  39.2     82     268
Waiting:        5   85  38.3     80     251
Total:          8   91  38.1     87     268

Percentage of the requests served within a certain time (ms)
50%     87
66%    102
75%    113
80%    120
90%    140
95%    163
98%    184
99%    207
100%    268 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/authorize
Document Length:        63 bytes

Concurrency Level:      100
Time taken for tests:   5.582 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      168000 bytes
Total body sent:        475000
HTML transferred:       63000 bytes
Requests per second:    179.14 [#/sec] (mean)
Time per request:       558.238 [ms] (mean)
Time per request:       5.582 [ms] (mean, across all concurrent requests)
Transfer rate:          29.39 [Kbytes/sec] received
83.09 kb/s sent
112.48 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    5  14.3      0      52
Processing:     2  527 374.7    514    1547
Waiting:        2  527 374.7    511    1547
Total:          2  532 373.3    519    1547

Percentage of the requests served within a certain time (ms)
50%    519
66%    717
75%    811
80%    870
90%   1010
95%   1146
98%   1407
99%   1500
100%   1547 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/ban
Document Length:        63 bytes

Concurrency Level:      100
Time taken for tests:   4.909 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      168000 bytes
Total body sent:        473000
HTML transferred:       63000 bytes
Requests per second:    203.71 [#/sec] (mean)
Time per request:       490.896 [ms] (mean)
Time per request:       4.909 [ms] (mean, across all concurrent requests)
Transfer rate:          33.42 [Kbytes/sec] received
94.10 kb/s sent
127.52 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    1   2.8      0      52
Processing:     1  468 298.8    453    1118
Waiting:        1  467 298.9    453    1118
Total:          1  468 298.5    453    1119

Percentage of the requests served within a certain time (ms)
50%    453
66%    623
75%    697
80%    766
90%    889
95%    973
98%   1007
99%   1035
100%   1119 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/unban
Document Length:        56 bytes

Concurrency Level:      100
Time taken for tests:   0.675 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      161000 bytes
Total body sent:        445000
HTML transferred:       56000 bytes
Requests per second:    1480.94 [#/sec] (mean)
Time per request:       67.525 [ms] (mean)
Time per request:       0.675 [ms] (mean, across all concurrent requests)
Transfer rate:          232.84 [Kbytes/sec] received
643.57 kb/s sent
876.42 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   11  13.9      4      46
Processing:     3   53  25.6     53     216
Waiting:        2   50  23.7     50     150
Total:          7   64  25.7     65     245

Percentage of the requests served within a certain time (ms)
50%     65
66%     71
75%     74
80%     76
90%    107
95%    114
98%    130
99%    136
100%    245 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/websites
Document Length:        63 bytes

Concurrency Level:      100
Time taken for tests:   5.390 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      168000 bytes
Total body sent:        468000
HTML transferred:       63000 bytes
Requests per second:    185.54 [#/sec] (mean)
Time per request:       538.973 [ms] (mean)
Time per request:       5.390 [ms] (mean, across all concurrent requests)
Transfer rate:          30.44 [Kbytes/sec] received
84.80 kb/s sent
115.24 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    1   4.2      0      20
Processing:     1  514 353.9    488    1323
Waiting:        1  514 353.9    488    1323
Total:          1  516 353.2    488    1323

Percentage of the requests served within a certain time (ms)
50%    488
66%    688
75%    788
80%    853
90%   1008
95%   1103
98%   1227
99%   1296
100%   1323 (longest request)

## n=1000 c=200
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/logs
Document Length:        56 bytes

Concurrency Level:      200
Time taken for tests:   0.716 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      161000 bytes
Total body sent:        442000
HTML transferred:       56000 bytes
Requests per second:    1395.76 [#/sec] (mean)
Time per request:       143.291 [ms] (mean)
Time per request:       0.716 [ms] (mean, across all concurrent requests)
Transfer rate:          219.45 [Kbytes/sec] received
602.47 kb/s sent
821.92 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    1   1.9      0       8
Processing:     2  134  92.7    106     478
Waiting:        2  133  92.4    106     478
Total:          9  135  92.0    106     479

Percentage of the requests served within a certain time (ms)
50%    106
66%    150
75%    189
80%    212
90%    275
95%    335
98%    363
99%    379
100%    479 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users
Document Length:        91 bytes

Concurrency Level:      200
Time taken for tests:   0.546 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      196000 bytes
Total body sent:        238000
HTML transferred:       91000 bytes
Requests per second:    1832.86 [#/sec] (mean)
Time per request:       109.119 [ms] (mean)
Time per request:       0.546 [ms] (mean, across all concurrent requests)
Transfer rate:          350.82 [Kbytes/sec] received
426.00 kb/s sent
776.82 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    7   9.3      1      35
Processing:     4   90  38.0     87     298
Waiting:        2   88  38.4     85     298
Total:          8   97  36.2     97     299

Percentage of the requests served within a certain time (ms)
50%     97
66%    109
75%    120
80%    127
90%    144
95%    157
98%    175
99%    197
100%    299 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/authorize
Document Length:        63 bytes

Concurrency Level:      200
Time taken for tests:   5.068 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      168000 bytes
Total body sent:        475000
HTML transferred:       63000 bytes
Requests per second:    197.31 [#/sec] (mean)
Time per request:       1013.623 [ms] (mean)
Time per request:       5.068 [ms] (mean, across all concurrent requests)
Transfer rate:          32.37 [Kbytes/sec] received
91.53 kb/s sent
123.90 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   10  20.1      0      55
Processing:     1  913 664.2    848    2484
Waiting:        1  913 664.3    848    2484
Total:          1  923 659.8    879    2484

Percentage of the requests served within a certain time (ms)
50%    879
66%   1164
75%   1351
80%   1498
90%   1926
95%   2215
98%   2323
99%   2360
100%   2484 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/ban
Document Length:        63 bytes

Concurrency Level:      200
Time taken for tests:   5.024 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      168000 bytes
Total body sent:        473000
HTML transferred:       63000 bytes
Requests per second:    199.06 [#/sec] (mean)
Time per request:       1004.728 [ms] (mean)
Time per request:       5.024 [ms] (mean, across all concurrent requests)
Transfer rate:          32.66 [Kbytes/sec] received
91.95 kb/s sent
124.61 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    1   1.6      0       6
Processing:     1  921 618.2    876    2322
Waiting:        1  921 618.3    872    2322
Total:          1  922 618.0    876    2322

Percentage of the requests served within a certain time (ms)
50%    876
66%   1213
75%   1368
80%   1498
90%   1826
95%   1982
98%   2159
99%   2215
100%   2322 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/unban
Document Length:        56 bytes

Concurrency Level:      200
Time taken for tests:   0.396 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      161000 bytes
Total body sent:        445000
HTML transferred:       56000 bytes
Requests per second:    2527.83 [#/sec] (mean)
Time per request:       79.119 [ms] (mean)
Time per request:       0.396 [ms] (mean, across all concurrent requests)
Transfer rate:          397.44 [Kbytes/sec] received
1098.52 kb/s sent
1495.96 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   19  24.0      9      93
Processing:     1   55  35.2     55     218
Waiting:        1   44  31.1     43     217
Total:         10   75  34.7     72     224

Percentage of the requests served within a certain time (ms)
50%     72
66%     86
75%     92
80%    102
90%    116
95%    121
98%    178
99%    205
100%    224 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/websites
Document Length:        63 bytes

Concurrency Level:      200
Time taken for tests:   4.500 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      168000 bytes
Total body sent:        468000
HTML transferred:       63000 bytes
Requests per second:    222.21 [#/sec] (mean)
Time per request:       900.033 [ms] (mean)
Time per request:       4.500 [ms] (mean, across all concurrent requests)
Transfer rate:          36.46 [Kbytes/sec] received
101.56 kb/s sent
138.02 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    1   1.8      0       6
Processing:     1  812 565.8    729    1961
Waiting:        1  812 565.8    729    1961
Total:          1  813 565.5    731    1961

Percentage of the requests served within a certain time (ms)
50%    731
66%   1097
75%   1297
80%   1412
90%   1645
95%   1749
98%   1819
99%   1861
100%   1961 (longest request)

## n=1200 c=300
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 120 requests
Completed 240 requests
Completed 360 requests
Completed 480 requests
Completed 600 requests
Completed 720 requests
Completed 840 requests
Completed 960 requests
Completed 1080 requests
Completed 1200 requests
Finished 1200 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/logs
Document Length:        56 bytes

Concurrency Level:      300
Time taken for tests:   0.498 seconds
Complete requests:      1200
Failed requests:        0
Non-2xx responses:      1200
Total transferred:      193200 bytes
Total body sent:        530400
HTML transferred:       67200 bytes
Requests per second:    2412.00 [#/sec] (mean)
Time per request:       124.378 [ms] (mean)
Time per request:       0.415 [ms] (mean, across all concurrent requests)
Transfer rate:          379.23 [Kbytes/sec] received
1041.12 kb/s sent
1420.35 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    2   3.2      0      11
Processing:     4  105  47.8    106     224
Waiting:        2  104  47.7    105     223
Total:          8  107  48.5    106     224

Percentage of the requests served within a certain time (ms)
50%    106
66%    124
75%    138
80%    153
90%    177
95%    190
98%    197
99%    204
100%    224 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 120 requests
Completed 240 requests
Completed 360 requests
Completed 480 requests
Completed 600 requests
Completed 720 requests
Completed 840 requests
Completed 960 requests
Completed 1080 requests
Completed 1200 requests
Finished 1200 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users
Document Length:        91 bytes

Concurrency Level:      300
Time taken for tests:   0.776 seconds
Complete requests:      1200
Failed requests:        0
Non-2xx responses:      1200
Total transferred:      235200 bytes
Total body sent:        285600
HTML transferred:       109200 bytes
Requests per second:    1545.80 [#/sec] (mean)
Time per request:       194.074 [ms] (mean)
Time per request:       0.647 [ms] (mean, across all concurrent requests)
Transfer rate:          295.88 [Kbytes/sec] received
359.28 kb/s sent
655.15 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   12  19.5      1      64
Processing:    11  149  71.2    144     399
Waiting:        0  148  71.6    142     399
Total:         25  161  62.3    159     399

Percentage of the requests served within a certain time (ms)
50%    159
66%    190
75%    210
80%    221
90%    244
95%    263
98%    279
99%    290
100%    399 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 120 requests
Completed 240 requests
Completed 360 requests
Completed 480 requests
Completed 600 requests
Completed 720 requests
Completed 840 requests
Completed 960 requests
Completed 1080 requests
Completed 1200 requests
Finished 1200 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/authorize
Document Length:        63 bytes

Concurrency Level:      300
Time taken for tests:   6.107 seconds
Complete requests:      1200
Failed requests:        0
Non-2xx responses:      1200
Total transferred:      201600 bytes
Total body sent:        570000
HTML transferred:       75600 bytes
Requests per second:    196.50 [#/sec] (mean)
Time per request:       1526.755 [ms] (mean)
Time per request:       5.089 [ms] (mean, across all concurrent requests)
Transfer rate:          32.24 [Kbytes/sec] received
91.15 kb/s sent
123.39 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    4   7.3      0      31
Processing:    11 1313 787.0   1172    2859
Waiting:        3 1313 787.1   1172    2859
Total:         32 1317 786.1   1183    2859

Percentage of the requests served within a certain time (ms)
50%   1183
66%   1741
75%   2013
80%   2175
90%   2450
95%   2611
98%   2723
99%   2790
100%   2859 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 120 requests
Completed 240 requests
Completed 360 requests
Completed 480 requests
Completed 600 requests
Completed 720 requests
Completed 840 requests
Completed 960 requests
Completed 1080 requests
Completed 1200 requests
Finished 1200 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/ban
Document Length:        63 bytes

Concurrency Level:      300
Time taken for tests:   5.434 seconds
Complete requests:      1200
Failed requests:        0
Non-2xx responses:      1200
Total transferred:      201600 bytes
Total body sent:        567600
HTML transferred:       75600 bytes
Requests per second:    220.85 [#/sec] (mean)
Time per request:       1358.400 [ms] (mean)
Time per request:       4.528 [ms] (mean, across all concurrent requests)
Transfer rate:          36.23 [Kbytes/sec] received
102.01 kb/s sent
138.25 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    2   3.3      0       9
Processing:    10 1197 701.0   1089    2782
Waiting:        2 1196 701.0   1089    2782
Total:         10 1198 700.1   1089    2782

Percentage of the requests served within a certain time (ms)
50%   1089
66%   1436
75%   1764
80%   1918
90%   2260
95%   2422
98%   2617
99%   2723
100%   2782 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 120 requests
Completed 240 requests
Completed 360 requests
Completed 480 requests
Completed 600 requests
Completed 720 requests
Completed 840 requests
Completed 960 requests
Completed 1080 requests
Completed 1200 requests
Finished 1200 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/unban
Document Length:        56 bytes

Concurrency Level:      300
Time taken for tests:   0.453 seconds
Complete requests:      1200
Failed requests:        0
Non-2xx responses:      1200
Total transferred:      193200 bytes
Total body sent:        534000
HTML transferred:       67200 bytes
Requests per second:    2649.04 [#/sec] (mean)
Time per request:       113.249 [ms] (mean)
Time per request:       0.377 [ms] (mean, across all concurrent requests)
Transfer rate:          416.50 [Kbytes/sec] received
1151.19 kb/s sent
1567.69 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   28  42.3      9     174
Processing:     3   75  56.0     56     263
Waiting:        2   59  48.7     46     258
Total:         12  103  65.5     77     274

Percentage of the requests served within a certain time (ms)
50%     77
66%     95
75%    157
80%    164
90%    215
95%    233
98%    249
99%    255
100%    274 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 120 requests
Completed 240 requests
Completed 360 requests
Completed 480 requests
Completed 600 requests
Completed 720 requests
Completed 840 requests
Completed 960 requests
Completed 1080 requests
Completed 1200 requests
Finished 1200 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/websites
Document Length:        63 bytes

Concurrency Level:      300
Time taken for tests:   5.278 seconds
Complete requests:      1200
Failed requests:        0
Non-2xx responses:      1200
Total transferred:      201600 bytes
Total body sent:        561600
HTML transferred:       75600 bytes
Requests per second:    227.35 [#/sec] (mean)
Time per request:       1319.555 [ms] (mean)
Time per request:       4.399 [ms] (mean, across all concurrent requests)
Transfer rate:          37.30 [Kbytes/sec] received
103.91 kb/s sent
141.21 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    3   5.2      0      16
Processing:    11 1154 686.2   1100    2678
Waiting:        5 1154 686.3   1100    2678
Total:         21 1157 684.9   1100    2678

Percentage of the requests served within a certain time (ms)
50%   1100
66%   1472
75%   1659
80%   1787
90%   2206
95%   2413
98%   2545
99%   2587
100%   2678 (longest request)

## n=2400 c=800
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 240 requests
Completed 480 requests
Completed 720 requests
Completed 960 requests
Completed 1200 requests
Completed 1440 requests
Completed 1680 requests
Completed 1920 requests
Completed 2160 requests
Completed 2400 requests
Finished 2400 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/logs
Document Length:        56 bytes

Concurrency Level:      800
Time taken for tests:   0.881 seconds
Complete requests:      2400
Failed requests:        0
Non-2xx responses:      2400
Total transferred:      386400 bytes
Total body sent:        1060800
HTML transferred:       134400 bytes
Requests per second:    2723.54 [#/sec] (mean)
Time per request:       293.735 [ms] (mean)
Time per request:       0.367 [ms] (mean, across all concurrent requests)
Transfer rate:          428.21 [Kbytes/sec] received
1175.59 kb/s sent
1603.80 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    8  13.6      1     110
Processing:     6  253 149.1    213     669
Waiting:        1  251 148.5    212     656
Total:         16  261 146.8    220     678

Percentage of the requests served within a certain time (ms)
50%    220
66%    312
75%    388
80%    424
90%    479
95%    514
98%    564
99%    595
100%    678 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 240 requests
Completed 480 requests
Completed 720 requests
Completed 960 requests
Completed 1200 requests
Completed 1440 requests
Completed 1680 requests
Completed 1920 requests
Completed 2160 requests
Completed 2400 requests
Finished 2400 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users
Document Length:        91 bytes

Concurrency Level:      800
Time taken for tests:   0.813 seconds
Complete requests:      2400
Failed requests:        0
Non-2xx responses:      2400
Total transferred:      470400 bytes
Total body sent:        571200
HTML transferred:       218400 bytes
Requests per second:    2950.61 [#/sec] (mean)
Time per request:       271.130 [ms] (mean)
Time per request:       0.339 [ms] (mean, across all concurrent requests)
Transfer rate:          564.77 [Kbytes/sec] received
685.79 kb/s sent
1250.55 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    9   9.2      3      37
Processing:     7  198  88.3    207     511
Waiting:        2  195  87.9    195     511
Total:         23  207  85.7    212     511

Percentage of the requests served within a certain time (ms)
50%    212
66%    243
75%    263
80%    279
90%    312
95%    345
98%    397
99%    418
100%    511 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 240 requests
Completed 480 requests
Completed 720 requests
Completed 960 requests
Completed 1200 requests
Completed 1440 requests
Completed 1680 requests
Completed 1920 requests
Completed 2160 requests
Completed 2400 requests
Finished 2400 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/authorize
Document Length:        63 bytes

Concurrency Level:      800
Time taken for tests:   13.489 seconds
Complete requests:      2400
Failed requests:        0
Non-2xx responses:      2400
Total transferred:      403200 bytes
Total body sent:        1140000
HTML transferred:       151200 bytes
Requests per second:    177.92 [#/sec] (mean)
Time per request:       4496.427 [ms] (mean)
Time per request:       5.621 [ms] (mean, across all concurrent requests)
Transfer rate:          29.19 [Kbytes/sec] received
82.53 kb/s sent
111.72 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   29 122.3      0    1007
Processing:    15 3844 1555.0   4017    6871
Waiting:        4 3844 1555.0   4017    6871
Total:         37 3872 1562.2   4018    6872

Percentage of the requests served within a certain time (ms)
50%   4018
66%   4616
75%   5014
80%   5191
90%   5832
95%   6469
98%   6651
99%   6720
100%   6872 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 240 requests
Completed 480 requests
Completed 720 requests
Completed 960 requests
Completed 1200 requests
Completed 1440 requests
Completed 1680 requests
Completed 1920 requests
Completed 2160 requests
Completed 2400 requests
Finished 2400 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/ban
Document Length:        63 bytes

Concurrency Level:      800
Time taken for tests:   12.548 seconds
Complete requests:      2400
Failed requests:        0
Non-2xx responses:      2400
Total transferred:      403200 bytes
Total body sent:        1135200
HTML transferred:       151200 bytes
Requests per second:    191.27 [#/sec] (mean)
Time per request:       4182.584 [ms] (mean)
Time per request:       5.228 [ms] (mean, across all concurrent requests)
Transfer rate:          31.38 [Kbytes/sec] received
88.35 kb/s sent
119.73 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   80 266.3      0    1033
Processing:    12 3279 1411.0   3232    6217
Waiting:        1 3279 1411.1   3232    6217
Total:         22 3359 1445.7   3340    6217

Percentage of the requests served within a certain time (ms)
50%   3340
66%   3942
75%   4405
80%   4730
90%   5343
95%   5608
98%   5828
99%   5905
100%   6217 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 240 requests
Completed 480 requests
Completed 720 requests
Completed 960 requests
Completed 1200 requests
Completed 1440 requests
Completed 1680 requests
Completed 1920 requests
Completed 2160 requests
Completed 2400 requests
Finished 2400 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/unban
Document Length:        56 bytes

Concurrency Level:      800
Time taken for tests:   0.743 seconds
Complete requests:      2400
Failed requests:        0
Non-2xx responses:      2400
Total transferred:      386400 bytes
Total body sent:        1068000
HTML transferred:       134400 bytes
Requests per second:    3231.25 [#/sec] (mean)
Time per request:       247.582 [ms] (mean)
Time per request:       0.309 [ms] (mean, across all concurrent requests)
Transfer rate:          508.04 [Kbytes/sec] received
1404.21 kb/s sent
1912.25 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   50  45.8     23     177
Processing:    13  127  73.0    112     352
Waiting:        2  112  73.2     95     323
Total:         26  177  74.3    177     370

Percentage of the requests served within a certain time (ms)
50%    177
66%    196
75%    203
80%    213
90%    332
95%    334
98%    337
99%    339
100%    370 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 240 requests
Completed 480 requests
Completed 720 requests
Completed 960 requests
Completed 1200 requests
Completed 1440 requests
Completed 1680 requests
Completed 1920 requests
Completed 2160 requests
Completed 2400 requests
Finished 2400 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/websites
Document Length:        63 bytes

Concurrency Level:      800
Time taken for tests:   14.373 seconds
Complete requests:      2400
Failed requests:        0
Non-2xx responses:      2400
Total transferred:      403200 bytes
Total body sent:        1123200
HTML transferred:       151200 bytes
Requests per second:    166.97 [#/sec] (mean)
Time per request:       4791.138 [ms] (mean)
Time per request:       5.989 [ms] (mean, across all concurrent requests)
Transfer rate:          27.39 [Kbytes/sec] received
76.31 kb/s sent
103.71 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   19  26.3      0      59
Processing:    39 3954 1658.0   4214    7168
Waiting:        2 3954 1658.0   4214    7168
Total:         39 3973 1640.5   4218    7168

Percentage of the requests served within a certain time (ms)
50%   4218
66%   4763
75%   5117
80%   5317
90%   5830
95%   6166
98%   6869
99%   7010
100%   7168 (longest request)

## n=10000 c=1000
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 1000 requests
Completed 2000 requests
Completed 3000 requests
Completed 4000 requests
Completed 5000 requests
Completed 6000 requests
Completed 7000 requests
Completed 8000 requests
Completed 9000 requests
Completed 10000 requests
Finished 10000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/logs
Document Length:        56 bytes

Concurrency Level:      1000
Time taken for tests:   2.436 seconds
Complete requests:      10000
Failed requests:        0
Non-2xx responses:      10000
Total transferred:      1610000 bytes
Total body sent:        4420000
HTML transferred:       560000 bytes
Requests per second:    4104.87 [#/sec] (mean)
Time per request:       243.613 [ms] (mean)
Time per request:       0.244 [ms] (mean, across all concurrent requests)
Transfer rate:          645.39 [Kbytes/sec] received
1771.83 kb/s sent
2417.22 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   50 164.0     17    1073
Processing:     3  176  84.2    174     463
Waiting:        2  160  79.6    157     437
Total:         11  226 189.4    197    1378

Percentage of the requests served within a certain time (ms)
50%    197
66%    235
75%    257
80%    274
90%    318
95%    374
98%   1280
99%   1301
100%   1378 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 1000 requests
Completed 2000 requests
Completed 3000 requests
Completed 4000 requests
Completed 5000 requests
Completed 6000 requests
Completed 7000 requests
Completed 8000 requests
Completed 9000 requests
Completed 10000 requests
Finished 10000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users
Document Length:        91 bytes

Concurrency Level:      1000
Time taken for tests:   2.966 seconds
Complete requests:      10000
Failed requests:        0
Non-2xx responses:      10000
Total transferred:      1960000 bytes
Total body sent:        2380000
HTML transferred:       910000 bytes
Requests per second:    3371.69 [#/sec] (mean)
Time per request:       296.587 [ms] (mean)
Time per request:       0.297 [ms] (mean, across all concurrent requests)
Transfer rate:          645.36 [Kbytes/sec] received
783.65 kb/s sent
1429.02 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0  109 229.2     48    1110
Processing:    11  159  89.7    140     450
Waiting:        2  134  82.2    117     420
Total:         25  268 251.3    196    1402

Percentage of the requests served within a certain time (ms)
50%    196
66%    237
75%    288
80%    325
90%    399
95%   1197
98%   1251
99%   1257
100%   1402 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 1000 requests
Completed 2000 requests
Completed 3000 requests
Completed 4000 requests
Completed 5000 requests
Completed 6000 requests
Completed 7000 requests
Completed 8000 requests
Completed 9000 requests
Completed 10000 requests
Finished 10000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/authorize
Document Length:        63 bytes

Concurrency Level:      1000
Time taken for tests:   58.884 seconds
Complete requests:      10000
Failed requests:        0
Non-2xx responses:      10000
Total transferred:      1680000 bytes
Total body sent:        4750000
HTML transferred:       630000 bytes
Requests per second:    169.83 [#/sec] (mean)
Time per request:       5888.406 [ms] (mean)
Time per request:       5.888 [ms] (mean, across all concurrent requests)
Transfer rate:          27.86 [Kbytes/sec] received
78.78 kb/s sent
106.64 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   88 287.8      0    1055
Processing:     1 5466 1621.9   5472    9560
Waiting:        1 5466 1621.9   5472    9560
Total:          1 5554 1563.0   5567    9560

Percentage of the requests served within a certain time (ms)
50%   5567
66%   6199
75%   6548
80%   6740
90%   7362
95%   7957
98%   8844
99%   9138
100%   9560 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 1000 requests
Completed 2000 requests
Completed 3000 requests
Completed 4000 requests
Completed 5000 requests
Completed 6000 requests
Completed 7000 requests
Completed 8000 requests
Completed 9000 requests
Completed 10000 requests
Finished 10000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/ban
Document Length:        63 bytes

Concurrency Level:      1000
Time taken for tests:   50.587 seconds
Complete requests:      10000
Failed requests:        0
Non-2xx responses:      10000
Total transferred:      1680000 bytes
Total body sent:        4730000
HTML transferred:       630000 bytes
Requests per second:    197.68 [#/sec] (mean)
Time per request:       5058.725 [ms] (mean)
Time per request:       5.059 [ms] (mean, across all concurrent requests)
Transfer rate:          32.43 [Kbytes/sec] received
91.31 kb/s sent
123.74 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   10  92.5      0    1015
Processing:    29 4820 1214.9   4874    7952
Waiting:        1 4820 1214.9   4874    7952
Total:         29 4831 1221.3   4876    7952

Percentage of the requests served within a certain time (ms)
50%   4876
66%   5384
75%   5662
80%   5823
90%   6236
95%   6605
98%   6878
99%   7038
100%   7952 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 1000 requests
Completed 2000 requests
Completed 3000 requests
Completed 4000 requests
Completed 5000 requests
Completed 6000 requests
Completed 7000 requests
Completed 8000 requests
Completed 9000 requests
Completed 10000 requests
Finished 10000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/users/unban
Document Length:        56 bytes

Concurrency Level:      1000
Time taken for tests:   2.277 seconds
Complete requests:      10000
Failed requests:        0
Non-2xx responses:      10000
Total transferred:      1610000 bytes
Total body sent:        4450000
HTML transferred:       560000 bytes
Requests per second:    4391.15 [#/sec] (mean)
Time per request:       227.731 [ms] (mean)
Time per request:       0.228 [ms] (mean, across all concurrent requests)
Transfer rate:          690.40 [Kbytes/sec] received
1908.26 kb/s sent
2598.67 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0  111 304.7      9    1190
Processing:     2   68  55.4     44     298
Waiting:        1   59  48.6     38     291
Total:          7  180 329.2     60    1297

Percentage of the requests served within a certain time (ms)
50%     60
66%    107
75%    152
80%    170
90%    266
95%   1232
98%   1276
99%   1282
100%   1297 (longest request)
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 1000 requests
Completed 2000 requests
Completed 3000 requests
Completed 4000 requests
Completed 5000 requests
Completed 6000 requests
Completed 7000 requests
Completed 8000 requests
Completed 9000 requests
Completed 10000 requests
Finished 10000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /api/v1/websites
Document Length:        63 bytes

Concurrency Level:      1000
Time taken for tests:   52.230 seconds
Complete requests:      10000
Failed requests:        0
Non-2xx responses:      10000
Total transferred:      1680000 bytes
Total body sent:        4680000
HTML transferred:       630000 bytes
Requests per second:    191.46 [#/sec] (mean)
Time per request:       5222.962 [ms] (mean)
Time per request:       5.223 [ms] (mean, across all concurrent requests)
Transfer rate:          31.41 [Kbytes/sec] received
87.50 kb/s sent
118.92 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0   26 152.6      0    1013
Processing:    14 4882 1349.8   4934    8579
Waiting:        2 4882 1349.8   4933    8579
Total:         39 4907 1341.9   4946    8579

Percentage of the requests served within a certain time (ms)
50%   4946
66%   5484
75%   5805
80%   5994
90%   6449
95%   6927
98%   7403
99%   7884
100%   8579 (longest request)