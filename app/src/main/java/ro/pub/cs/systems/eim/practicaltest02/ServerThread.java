package ro.pub.cs.systems.eim.practicaltest02;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
public class ServerThread extends Thread {
    private int port;
    private ServerSocket serverSocket;
    private HashMap<String, WeatherInfo> data;

    public ServerThread(int port) {
        this.port = port;
    }

    public synchronized void setData(String city, WeatherInfo weatherInfo) {
        this.data.put(city, weatherInfo);
    }

    public synchronized HashMap<String, WeatherInfo> getData() {
        return this.data;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();
                CommunicationThread communicationThread = new CommunicationThread(this, socket);
                communicationThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        interrupt();
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

