package com.logan19gp.ipmaps;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by george on 11/19/2015.
 */
public class Utilities
{
    public interface EditDraw
    {
        void updateIp(int ip);
    }

    /**
     *
     * @param activity
     * @param minBall
     * @param maxBall
     * @param selectedVal
     * @param editIpInterface
     */
    public static void editIP(Activity activity, int minBall, int maxBall, int selectedVal, final EditDraw editIpInterface)
    {
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
        nPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
            }
        });
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (editIpInterface != null)
                {
                    editIpInterface.updateIp(nPicker.getValue());
                }
                dialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * @param valueToParse
     * @return
     */
    public static Integer getStrinAsInt(String valueToParse)
    {
        return getStrinAsInt(valueToParse, 0);
    }

    /**
     * @param valueToParse
     * @param retValDefault
     * @return
     */
    public static Integer getStrinAsInt(String valueToParse, Integer retValDefault)
    {
//        Integer retVal = 0;
        try
        {
            if (isNumeric(valueToParse))
            {
                retValDefault = Integer.parseInt(valueToParse);
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            return retValDefault;
        }
    }

    /**
     * @param stringValue
     * @return
     */
    public static boolean isNumeric(String stringValue)
    {
        if (TextUtils.isEmpty(stringValue))
        {
            return false;
        }
        Pattern p = Pattern.compile("[-+]?[0-9]*");
        Matcher m = p.matcher(stringValue);
        return m.matches();
    }

}
