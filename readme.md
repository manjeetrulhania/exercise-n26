Transaction Statistics Service

We would like to have a restful API for our statistics. The main use case for our API is to calculate realtime statistic from the last 60 seconds. 
There will be two APIs, one of them is called every time a transaction is made. It is also the sole input of this rest API. The other one
returns the statistic based of the transactions of the last 60 seconds.

Setup PreRequisite 
----------------------------------------------------------
1. Requires Maven 3.X
2. Requires java 1.8

Setup/Installation
-----------------------------------------------------------
Spring boot

1. Go to project directory (exercise-n26 directory)
2. Run mvn clean package command (Integration test cases will be executed, please change server port if 8080 is already in use .i.e. mvn clean package -Dserver.port=8787 )
3. Run java -jar target\exercise-26.jar (Application will be running in embedded tomcat container by spring boot)
   By default tomcat will run on 8080 port but you can change port or context path if required as given below.
4. java -jar -Dserver.port=8787 -Dserver.contextPath=/exercise-n26 target\exercise-n26.jar
5. Logs would be generated in message.log file.
7. URL for Create transaction API would be like - http://localhost:8080/transactions and http://localhost:8080/statistics (we can configure context path as well if needed)
8. To run in Eclipse, run main class TransactionApplication (run as java application) 
NOTE - please change server port or context path in step 2 as well if required.

Tomcat
1. Go to project directory (exercise-n26 directory).
2. mvn clean package -P tomcat (please change server port if 8080 is already in use, as spring boot integration test will run on embedded tomcat)
3. deploy exercise-n26.war in your tomcat instance.
4. Post a transaction to URL - http://localhost:8080/transactions


Why Springboot
-------------------------------------------------------------	
1. Springboot provides quick way to develop application without any boiler plate configuration code.
2. Provides quick and fast way to test and deploy application in embedded container or databases if required.

What We can do to Improve or needed for production 
--------------------------------------------------------------
1. We should secure our resources to avoid mis-use of our API. we can use spring security to secure resources and service layer methods (need more time to add spring security).
2. If API failure alerts are based on logs, then logs should be printed in a defined format. (In WU we use splunk graph and alerts for 
monitoring API health. Splunk read logs file and generate graph/alert.)
3. Caching is vital to any scalable application, so we should cache API responses if we can. By using proxy servers to cache response based on 
cache header can boost performance by many fold (if user calls same API multiple times and data is not stale), But this use case is not a good fit for caching.
4. We should use REST API documentation library such as swagger to help consumers of the API.we are using swagger in current project, its pretty helpful for consumer of the API.
6. We should configure GC logging in production to monitor Heap.
7. We can build error handling interceptor to customize error response message before sending back to FE (we won't want to expose our class structure in production to FE)
