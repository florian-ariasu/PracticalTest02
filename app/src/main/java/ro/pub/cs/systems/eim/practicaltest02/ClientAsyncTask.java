package ro.pub.cs.systems.eim.practicaltest02;

import android.os.AsyncTask;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ClientAsyncTask extends AsyncTask<String, String, Void> {
    private String address, city, informationType;
    private int port;
    private TextView resultTextView;

    public ClientAsyncTask(String address, int port, String city, String informationType, TextView resultTextView) {
        this.address = address;
        this.city = city;
        this.port = port;
        this.informationType = informationType;
        this.resultTextView = resultTextView;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            Socket socket = new Socket(address, port);
            if (socket == null) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println(city);
            writer.println(informationType);

            String line;
            while ((line = reader.readLine()) != null) {
                publishProgress(line);
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(String... values) {
        resultTextView.append(values[0] + "\n");
    }
}
