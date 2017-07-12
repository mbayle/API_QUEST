package sport.fr.wcs.quest_api;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.GsonGoogleHttpClientSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private double latitude;
    private double longitude;
    private String apiKey;
    private TextView textViewCurrent;
    private ListView listView;


    protected SpiceManager spiceManager = new SpiceManager(GsonGoogleHttpClientSpiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mLocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        textViewCurrent = (TextView) findViewById(R.id.textViewCurrent);
        listView = (ListView) findViewById(R.id.listViewForecast);
        apiKey = getString(R.string.apiKey);

        downloadWeatherData();

        // Define a listener that responds to location updates
        mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(MainActivity.this, "Latitude " + location.getLatitude() + " - Longitude" + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
    }

    public static final int MY_REQUEST_FOR_LOCATION = 1;

    @Override
    protected void onStart() {
        super.onStart();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 20, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 20, mLocationListener);

        spiceManager.start(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationManager.removeUpdates(mLocationListener);

        spiceManager.shouldStop();
    }

    public void downloadWeatherData() {
        ForecastWeatherRequest requestCurrent = new ForecastWeatherRequest(latitude, longitude, apiKey);
        spiceManager.execute(requestCurrent, new ForecastWeatherRequestListener());

        CurrentWeatherRequest requestForecast = new CurrentWeatherRequest(latitude, longitude, apiKey);
        spiceManager.execute(requestForecast, new CurrentWeatherRequestListener());
    }

    private class ForecastWeatherRequestListener implements RequestListener<ForecastWeatherModel> {

        @Override
        public void onRequestFailure(SpiceException e) {
            Log.d("SPICE EXCEPTION", "Forecast Listener ko");
        }

        @Override
        public void onRequestSuccess(ForecastWeatherModel forecastWeatherModel) {
            final ArrayList<List> weatherList = (ArrayList<List>) forecastWeatherModel.getList();
            final WeatherAdapter weatherAdapter = new WeatherAdapter(MainActivity.this, weatherList);
            listView.setAdapter(weatherAdapter);
        }
    }

    private class CurrentWeatherRequestListener implements RequestListener<CurrentWeatherModel> {

        private String city;

        @Override
        public void onRequestFailure(SpiceException e) {
            Log.d("SPICE EXCEPTION", "Current Listener ko");        }

        @Override
        public void onRequestSuccess(CurrentWeatherModel currentWeatherModel) {
            city = currentWeatherModel.getName();
            textViewCurrent.setText("Ville : " + city + "\n");
        }
    }
}

