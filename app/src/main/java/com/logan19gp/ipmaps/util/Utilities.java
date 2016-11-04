package com.logan19gp.ipmaps.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.logan19gp.ipmaps.BuildConfig;
import com.logan19gp.ipmaps.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by george on 11/19/2015.
 */
public class Utilities {
    public interface EditDraw {
        void updateIp(int ip);
    }

    /**
     * @param activity
     * @param minBall
     * @param maxBall
     * @param selectedVal
     * @param editIpInterface
     */
    public static void editIP(Activity activity, int minBall, int maxBall, int selectedVal, final EditDraw editIpInterface) {
        final Dialog dialog = new Dialog(activity);
        dialog.setTitle("Select a new value");
        dialog.setContentView(R.layout.dialog_ip_picker);
        Button b1 = (Button) dialog.findViewById(R.id.button1);
        Button b2 = (Button) dialog.findViewById(R.id.button2);
        final NumberPicker nPicker = (NumberPicker) dialog.findViewById(R.id.numberPicker1);
        nPicker.setMaxValue(maxBall);
        nPicker.setMinValue(minBall);
        nPicker.setValue(selectedVal > maxBall ? 1 : selectedVal);
        nPicker.setWrapSelectorWheel(false);
        nPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editIpInterface != null) {
                    editIpInterface.updateIp(nPicker.getValue());
                }
                dialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * @param valueToParse
     * @return
     */
    public static Integer getStringAsInt(String valueToParse) {
        return getStringAsInt(valueToParse, 0);
    }

    /**
     * @param valueToParse
     * @param retValDefault
     * @return
     */
    public static Integer getStringAsInt(String valueToParse, Integer retValDefault) {
        try {
            if (isNumeric(valueToParse)) {
                retValDefault = Integer.parseInt(valueToParse);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return retValDefault;
        }
    }

    /**
     * @param valueToParse
     * @return
     */
    public static Double getStrinAsDbl(String valueToParse) {
        return getStringAsDbl(valueToParse, 0.0);
    }

    /**
     * @param valueToParse
     * @param retValDefault
     * @return
     */
    public static Double getStringAsDbl(String valueToParse, Double retValDefault) {
        try {
            if (isNumericDouble(valueToParse)) {
                retValDefault = Double.parseDouble(valueToParse);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return retValDefault;
        }
    }

    /**
     * @param stringValue
     * @return
     */
    public static boolean isNumeric(String stringValue) {
        if (TextUtils.isEmpty(stringValue)) {
            return false;
        }
        Pattern p = Pattern.compile("[-+]?[0-9]*");
        Matcher m = p.matcher(stringValue);
        return m.matches();
    }

    /**
     * @param stringValue
     * @return
     */
    public static boolean isNumericDouble(String stringValue) {
        if (TextUtils.isEmpty(stringValue)) {
            return false;
        }
        Pattern p = Pattern.compile("[-+]?[0-9]*[.]?[0-9]*");
        Matcher m = p.matcher(stringValue);
        return m.matches();
    }

    /**
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        }
    }

    /**
     * Log only if is a debug build
     *
     * @param tag
     * @param textToLog
     */
    public static void logMsg(String tag, String textToLog) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, textToLog);
        }
    }

    /**
     * Log only if is a debug build
     *
     * @param textToLog
     */
    public static void logMsg(String textToLog) {
        logMsg("LogMsg:", textToLog);
    }
}
