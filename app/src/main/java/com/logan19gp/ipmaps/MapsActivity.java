package com.logan19gp.ipmaps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by george on 11/19/2015.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private TextView ipFirst, ipSecond, ipThird, ipFourth, ipFifth;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ipFirst = (TextView) findViewById(R.id.ip_first);
        ipSecond = (TextView) findViewById(R.id.ip_second);
        ipThird = (TextView) findViewById(R.id.ip_third);
        ipFourth = (TextView) findViewById(R.id.ip_fourth);
        ipFifth = (TextView) findViewById(R.id.ip_fifth);


        ipFirst.setOnClickListener(setListener(ipFirst, null, null));
        ipSecond.setOnClickListener(setListener(ipSecond, null, null));
        ipThird.setOnClickListener(setListener(ipThird, null, null));
        ipFourth.setOnClickListener(setListener(ipFourth, null, ipFifth));
        ipFifth.setOnClickListener(setListener(ipFifth, ipFourth, null));

        findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    private View.OnClickListener setListener(final TextView textView, final TextView limitationViewMin, final TextView limitationViewMax)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                Utilities.EditDraw interfaceEdit = new Utilities.EditDraw()
                {
                    @Override
                    public void updateIp(int ipValue)
                    {
                        textView.setText(String.valueOf(ipValue));
                    }
                };
                Integer limitationMin = 0;
                if (limitationViewMin != null)
                {
                    limitationMin = Utilities.getStrinAsInt(limitationViewMin.getText().toString());
                }
                Integer limitationMax = 255;
                if (limitationViewMax != null)
                {
                    limitationMax = Utilities.getStrinAsInt(limitationViewMax.getText().toString());
                }
                Utilities.editIP(MapsActivity.this, limitationMin, limitationMax,
                        Utilities.getStrinAsInt(textView.getText().toString().trim()), interfaceEdit);
            }
        };
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
