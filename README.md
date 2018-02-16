#Java RMI Chat Application

Group Members :
Kadir Korkmaz
Lyubomyr Polyuha

How to build : 
We created a maven project so to compile our application you need to install maven to your system. 

To install maven use following command : 
sudo apt-get install maven

In the root of the project folder run following command to build project : 
mvn install


How to run Applications : 
Successful build process will create a “target” folder inside the root of the project folder.
Enter target folder and there must be two jar files. Which are “server.jar” and “guiclient.jar”.

Our server application automatically running  a rmiregistry from port 2020.
If this port is used by any other application please close your application.

To run server application use following command : 
java -jar server.jar &

To run client application use following command : 
java -jar guiclient.jar &

For persistency we used an append only file. Every message is written to append only file by server application. If server application restarts, this file is loaded inside memory during the startup.

If you close the server, all the client applications which are open  will be unresponsive. In this case, you have to close one by one.

GitHub Repository  : 
https://github.com/korkmazkadir/java-rmi-chat
