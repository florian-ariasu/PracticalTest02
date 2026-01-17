package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
public class CommunicationThread extends Thread {
    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread (ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String city = reader.readLine();
            String informationType = reader.readLine();

            WeatherInfo weatherInfo = serverThread.getData().get(city);

            if (weatherInfo == null) {
                HttpClient httpClient = new DefaultHttpClient();
                String url =  "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=e03c3b32cfb5a6f7069f2ef29237d87e";
                HttpGet httpGet = new HttpGet(url);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String content = httpClient.execute(httpGet, responseHandler);

                JSONObject jsonObject = new JSONObject(content);
                JSONObject main = jsonObject.getJSONObject("main");
                JSONObject wind = jsonObject.getJSONObject("wind");
                String temp = main.getString("temp");
                String windSpeed = wind.getString("speed");
                String humidity = main.getString("humidity");
                String pressure = main.getString("pressure");
                String cond = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");

                weatherInfo = new WeatherInfo(temp, windSpeed, cond, pressure, humidity);
                serverThread.setData(city, weatherInfo);
            }

            String result = "";
            if (informationType.equals("all")) {
                result = weatherInfo.toString();
            } else if (informationType.equals("temperature")) {
                result = weatherInfo.temperature;
            } else if (informationType.equals("wind")) {
                result = weatherInfo.windSpeed;
            } else if (informationType.equals("condition")) {
                result = weatherInfo.condition;
            } else if (informationType.equals("pressure")) {
                result = weatherInfo.pressure;
            } else if (informationType.equals("humidity")) {
                result = weatherInfo.humidity;
            }

            writer.println(result);
            writer.flush();

            socket.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

