package it.ac.riqsudev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GetCurrentLocationActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String provider;
    Button btnLocaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_current_location);

        btnLocaction = findViewById(R.id.btnLocations);

        btnLocaction.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(GetCurrentLocationActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(GetCurrentLocationActivity.this,
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        0);
            }
            statusCheck();

            locationManager = (LocationManager) getSystemService(
                    Context.LOCATION_SERVICE);


            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);

            if (provider != null && !provider.equals("")) {
                if (!provider.contains("gps")) {
                    final Intent poke = new Intent();
                    poke.setClassName("com.android.settings",
                            "com.android.settings.widget.SettingsAppWidgetProvider");
                    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    poke.setData(Uri.parse("3"));
                    sendBroadcast(poke);
                }

                Location location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 500, 0, GetCurrentLocationActivity.this);

                if (location != null)
                    onLocationChanged(location);
                else
                    location = locationManager.getLastKnownLocation(provider);
                if (location != null)
                    onLocationChanged(location);
                else

                    Toast.makeText(getBaseContext(), "Lokasi tidak dapat diakses",
                            Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getBaseContext(), "Device tidak ditemukan",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMsgNoGps();
        }
    }

    private void buildAlertMsgNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                        "Apakah anda ingin mengaktifkan lokasi?")
                .setCancelable(false).setPositiveButton("Ya",
                        (dialog, id) -> startActivity(new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        TextView tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        TextView tvLatitude = (TextView) findViewById(R.id.tvLatitude);

        // Set Text Longitude and Latitude
        tvLongitude.setText("Longitude : "  + location.getLongitude());
        tvLatitude.setText("Latitude : "  + location.getLatitude());
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    0);
        }
    }
}