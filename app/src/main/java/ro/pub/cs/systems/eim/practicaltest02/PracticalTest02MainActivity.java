package ro.pub.cs.systems.eim.practicaltest02;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest02MainActivity extends AppCompatActivity {
    private EditText serverPortEditText;
    private Button startServerButton;
    private ServerThread serverThread = null;

    private EditText clientAddressEditText;
    private EditText clientPortEditText;
    private EditText cityEditText;
    private Spinner informationTypeSpinner;
    private Button getWeatherButton;
    private TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = findViewById(R.id.server_port_edit_text);
        startServerButton = findViewById(R.id.start_server_button);

        clientAddressEditText = findViewById(R.id.client_address_edit_text);
        clientPortEditText = findViewById(R.id.client_port_edit_text);
        cityEditText = findViewById(R.id.city_edit_text);
        informationTypeSpinner = findViewById(R.id.information_type_spinner);
        getWeatherButton = findViewById(R.id.get_weather_button);
        resultTextView = findViewById(R.id.result_text_view);

        startServerButton.setOnClickListener(view -> {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            serverThread = new ServerThread(Integer.parseInt(serverPort));
            serverThread.start();
            Toast.makeText(getApplicationContext(), "Server started on port " + serverPort, Toast.LENGTH_SHORT).show();
        });

        getWeatherButton.setOnClickListener(view -> {
            String address = clientAddressEditText.getText().toString();
            String port = clientPortEditText.getText().toString();
            String city = cityEditText.getText().toString();
            String informationType = informationTypeSpinner.getSelectedItem().toString();

            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "Server is not started!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (address.isEmpty() || port.isEmpty() || city.isEmpty()) {
                Toast.makeText(getApplicationContext(), "All fields should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            resultTextView.setText("");
            new ClientAsyncTask(address, Integer.parseInt(port), city, informationType, resultTextView).execute();
        });
    }

    @Override
    protected void onDestroy() {
        if (serverThread != null) {
            serverThread.stopServer();
        }
        super.onDestroy();
    }
}
