package ro.pub.cs.systems.eim.practicaltest02;

public class WeatherInfo {
    public String temperature;
    public String windSpeed;
    public String condition;
    public String pressure;
    public String humidity;

    public WeatherInfo(String temperature, String windSpeed, String condition, String pressure, String humidity) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.condition = condition;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "temperature='" + temperature + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", condition='" + condition + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}

