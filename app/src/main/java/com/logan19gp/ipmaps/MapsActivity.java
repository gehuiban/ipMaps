package com.logan19gp.ipmaps;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.logan19gp.ipmaps.serverAPI.GetIpDetailsFromServer;
import com.logan19gp.ipmaps.serverAPI.IpResponse;
import com.logan19gp.ipmaps.serverAPI.OnResponseListener;
import com.logan19gp.ipmaps.serverAPI.ServerAPIClient;
import com.logan19gp.ipmaps.util.DialogUtil;
import com.logan19gp.ipmaps.util.Utilities;

import java.util.ArrayList;

/**
 * Created by george on 11/19/2015.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DialogUtil.MyAlertDialogFragment.DialogUtilActivity
{
    private TextView ipFirst, ipSecond, ipThird, ipFourth, ipFirstB, ipSecondB, ipThirdB, ipFourthB;
    private GoogleMap mMap;
    private int buttonPressedIdGlobal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ServerAPIClient.initializeRequestQueue(this);
        setContentView(R.layout.activity_layout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ipFirst = (TextView) findViewById(R.id.ip_first);
        ipSecond = (TextView) findViewById(R.id.ip_second);
        ipThird = (TextView) findViewById(R.id.ip_third);
        ipFourth = (TextView) findViewById(R.id.ip_fourth);
        ipFirstB = (TextView) findViewById(R.id.ip_first_b);
        ipSecondB = (TextView) findViewById(R.id.ip_second_b);
        ipThirdB = (TextView) findViewById(R.id.ip_third_b);
        ipFourthB = (TextView) findViewById(R.id.ip_fourth_b);

        ipFirst.setOnClickListener(setListener(ipFirst, null, ipFirstB));
        ipSecond.setOnClickListener(setListener(ipSecond, null, ipSecondB));
        ipThird.setOnClickListener(setListener(ipThird, null, ipThirdB));
        ipFourth.setOnClickListener(setListener(ipFourth, null, ipFourthB));
        ipFirstB.setOnClickListener(setListener(ipFirstB, ipFirst, null));
        ipSecondB.setOnClickListener(setListener(ipSecondB, ipSecond, null));
        ipThirdB.setOnClickListener(setListener(ipThirdB, ipThird, null));
        ipFourthB.setOnClickListener(setListener(ipFourthB, ipFourth, null));

        findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                buttonPressedIdGlobal++;
                String first = ipFirst.getText().toString().trim();
                String second = ipSecond.getText().toString().trim();
                String third = ipThird.getText().toString().trim();
                String fourth = ipFourth.getText().toString().trim();
                String firstB = ipFirstB.getText().toString().trim();
                String secondB = ipSecondB.getText().toString().trim();
                String thirdB = ipThirdB.getText().toString().trim();
                String fourthB = ipFourthB.getText().toString().trim();
                Integer firstInt = Utilities.getStringAsInt(first);
                Integer secondInt = Utilities.getStringAsInt(second);
                Integer thirdInt = Utilities.getStringAsInt(third);
                Integer fourthInt = Utilities.getStringAsInt(fourth);
                Integer firstBInt = Utilities.getStringAsInt(firstB);
                Integer secondBInt = Utilities.getStringAsInt(secondB);
                Integer thirdBInt = Utilities.getStringAsInt(thirdB);
                Integer fourthBInt = Utilities.getStringAsInt(fourthB);

                if(first.equals("10") || (first.equals("192") && second.equals("168"))
                        || (first.equals("172") && secondInt > 15 && secondInt < 32) )
                {
                    DialogUtil.showErrorDialog(MapsActivity.this, 23432, "IP Issue", "First IP needs to be public IP address.");
                }
                else if(firstB.equals("10") || (firstB.equals("192") && secondB.equals("168"))
                        || (firstB.equals("172") && secondBInt > 15 && secondBInt < 32) )
                {
                    DialogUtil.showErrorDialog(MapsActivity.this, 23432, "IP Issue", "Second IP needs to be public IP address.");
                }
                else
                {
                    mMap.clear();
                    int id = 0;
                    ArrayList<IpResponse> listOfIps = new ArrayList<IpResponse>();
                    for(int firstId = firstInt; firstId <= firstBInt; firstId++)
                    {
                        for (int secondId = firstId <= firstBInt ? 255 : secondInt; secondId <= ( firstId <= firstBInt ? 255 : secondBInt); secondId++)
                        {
                            for (int thirdId = thirdInt; thirdId <= thirdBInt; thirdId++)
                            {
                                for (int fourthId = fourthInt; fourthId <= fourthBInt; fourthId++)
                                {
                                    id++;
                                    String ipAddress = firstId + "." + secondId + "." + thirdId + "." + fourthId;
                                    // delay calls to avoid 403
                                    int delay = id * 500;
                                    Utilities.logMsg("delay:" + delay);
                                    view.postDelayed(runServerQuery(ipAddress, buttonPressedIdGlobal, listOfIps), delay);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private Runnable runServerQuery(final String ipAddress, final int buttonPressId, final ArrayList<IpResponse> listOfIps)
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                if(buttonPressId == buttonPressedIdGlobal)
                {
                    GetIpDetailsFromServer.getIpFromServer(MapsActivity.this, ipAddress, new OnResponseListener()
                    {
                        @Override
                        public void onResponseReceived(String ipAddress, IpResponse ipResponse)
                        {
                            Double lat = Utilities.getStrinAsDbl(ipResponse.getLatitude());
                            Double lng = Utilities.getStrinAsDbl(ipResponse.getLongitude());
                            if(lat > 0 || lng > 0)
                            {
                                listOfIps.add(ipResponse);
                                if (mMap != null)
                                {
                                    LatLng latLng = new LatLng(lat, lng);
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(ipAddress));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                }
                            }
                        }
                    });
                }
            }
        };
    }

    @Override
    protected void onResume()
    {
        SharedPreferences fis = getSharedPreferences(Constants.PREFS_FILE, MODE_PRIVATE);
        ipFirst.setText(fis.getString(Constants.IP_FIRST, "40"));
        ipSecond.setText(fis.getString(Constants.IP_SECOND, "4"));
        ipThird.setText(fis.getString(Constants.IP_THIRD, "4"));
        ipFourth.setText(fis.getString(Constants.IP_FOURTH, "2"));
        ipFirstB.setText(fis.getString(Constants.IP_FIRSTB, "45"));
        ipSecondB.setText(fis.getString(Constants.IP_SECONDB, "5"));
        ipThirdB.setText(fis.getString(Constants.IP_THIRDB, "4"));
        ipFourthB.setText(fis.getString(Constants.IP_FOURTHB, "2"));
        super.onResume();
    }

    @Override
    protected void onPause()
    {

        super.onPause();
        if (!writeInstanceState(this))
        {
            Toast.makeText(this, "Failed to write state!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean writeInstanceState(Activity c)
    {
        SharedPreferences p = c.getSharedPreferences(Constants.PREFS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor e = p.edit();
        e.putString(Constants.IP_FIRST, ipFirst.getText().toString().trim());
        e.putString(Constants.IP_SECOND, ipSecond.getText().toString().trim());
        e.putString(Constants.IP_THIRD, ipThird.getText().toString().trim());
        e.putString(Constants.IP_FOURTH, ipFourth.getText().toString().trim());
        e.putString(Constants.IP_FIRSTB, ipFirstB.getText().toString().trim());
        e.putString(Constants.IP_SECONDB, ipSecondB.getText().toString().trim());
        e.putString(Constants.IP_THIRDB, ipThirdB.getText().toString().trim());
        e.putString(Constants.IP_FOURTHB, ipFourthB.getText().toString().trim());
        return (e.commit());
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
                    limitationMin = Utilities.getStringAsInt(limitationViewMin.getText().toString());
                }
                Integer limitationMax = 255;
                if (limitationViewMax != null)
                {
                    limitationMax = Utilities.getStringAsInt(limitationViewMax.getText().toString());
                }
                Utilities.editIP(MapsActivity.this, limitationMin, limitationMax,
                        Utilities.getStringAsInt(textView.getText().toString().trim()), interfaceEdit);
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
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void negativeActionDialogClick(int dialogId, String[] extras)
    {

    }

    @Override
    public void positiveActionDialogClick(int dialogId, String[] extras)
    {

    }
}
