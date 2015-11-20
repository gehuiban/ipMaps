package com.logan19gp.ipmaps.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.logan19gp.ipmaps.MapsActivity;
import com.logan19gp.ipmaps.R;


public class DialogUtil
{
    public static class MyAlertDialogFragment extends DialogFragment
    {
        public interface DialogUtilActivity {
            public void positiveActionDialogClick(int dialogId, String... extras);
            public void negativeActionDialogClick(int dialogId, String... extras);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            if(!(activity instanceof DialogUtilActivity)){
                throw new IllegalAccessError("Activities using MyAlertDialogFragment must implement DialogUtilActivity");
            }
        }

        public static MyAlertDialogFragment newInstance(int dialogId, String title, String message,
                boolean positiveButton, boolean negativeButton, String... extras)
        {
            return newInstance(dialogId,title,message,positiveButton,negativeButton, R.string.ok_st, R.string.cancel, extras);
        }

        public static MyAlertDialogFragment newInstance(int dialogId, String title, String message,
                                                        boolean positiveButton, boolean negativeButton, int res_id_positive_action_string,
                                                        int res_id_of_negative_action_string, String... extras)
        {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("dialog_id", dialogId);
            args.putString("title", title);
            args.putString("message", message);
            args.putBoolean("positiveButton", positiveButton);
            args.putBoolean("negativeButton", negativeButton);
            args.putSerializable("extras", extras);
            args.putInt("positive_action",res_id_positive_action_string);
            args.putInt("negative_action",res_id_of_negative_action_string);
            frag.setArguments(args);
            return frag;
        }

        public static MyAlertDialogFragment newInstance(int dialogId, String title, String message,
                                                        boolean positiveButton, boolean negativeButton, boolean cancelButton, int res_id_positive_action_string,
                                                        int res_id_of_negative_action_string, int res_id_of_cancel_action_string, String... extras)
        {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("dialog_id", dialogId);
            args.putString("title", title);
            args.putString("message", message);
            args.putBoolean("positiveButton", positiveButton);
            args.putBoolean("negativeButton", negativeButton);
            args.putBoolean("cancelButton", cancelButton);
            args.putSerializable("extras", extras);
            args.putInt("positive_action", res_id_positive_action_string);
            args.putInt("negative_action",res_id_of_negative_action_string);
            args.putInt("cancel_action",res_id_of_cancel_action_string);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            final int dialogId = getArguments().getInt("dialog_id");
            String title = getArguments().getString("title");
            String message = getArguments().getString("message");

            boolean positiveButton = getArguments().getBoolean("positiveButton");
            boolean negativeButton = getArguments().getBoolean("negativeButton");
            Boolean cancelButton = null;

            if (getArguments().containsKey("cancelButton"))
            {
                cancelButton = getArguments().getBoolean("cancelButton");
            }

            final String[] extras = (String[]) getArguments().getSerializable("extras");
            final int res_string_id_for_positive_action = getArguments().getInt("positive_action");
            final int res_string_id_for_negative_action = getArguments().getInt("negative_action");

            Integer res_string_id_for_Cancel_action = null;

            if (getArguments().containsKey("cancel_action"))
            {
                res_string_id_for_Cancel_action = getArguments().getInt("cancel_action");
            }


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(title);
            builder.setMessage(message);

            if (positiveButton)
            {
                builder.setPositiveButton(res_string_id_for_positive_action, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        Activity activity = getActivity();

                        if (activity instanceof DialogUtilActivity)
                        {
                            ((DialogUtilActivity)activity).positiveActionDialogClick(dialogId, extras);
                        }
                        else
                        {
                            ((MapsActivity) activity).positiveActionDialogClick(dialogId, extras);
                        }
                    }
                });

            }

            if (negativeButton)
            {
                builder.setNegativeButton(res_string_id_for_negative_action, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        Activity activity = getActivity();

                        if (activity instanceof DialogUtilActivity)
                        {
                            ((DialogUtilActivity) activity).negativeActionDialogClick(dialogId, extras);
                        }
                        else
                        {
                            ((MapsActivity) activity).negativeActionDialogClick(dialogId, extras);
                        }
                    }
                });
            }

            if (cancelButton != null && cancelButton && res_string_id_for_Cancel_action != null)
            {
                builder.setNeutralButton(res_string_id_for_Cancel_action, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        dialog.dismiss();
                    }
                });
            }

            return builder.create();

        }
    }

    public static void showDialog(Activity activity, int dialogId, String title, String message, boolean usePositive,
            boolean useNegative, String... extras)
    {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(dialogId, title, message, usePositive, useNegative, extras);
        newFragment.show(activity.getFragmentManager(), "dialog:" + dialogId);
    }

    public static void showDialog(Activity activity, int dialogId, String title, String message,  int res_id_positive_action_string, int res_id_negative_action_str, String... extras)
    {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(dialogId, title, message, true,
                true, res_id_positive_action_string, res_id_negative_action_str, extras);
        newFragment.show(activity.getFragmentManager(), "dialog:" + dialogId);
    }

    public static void showDialog(Activity activity, int dialogId, String title, String message,  int res_id_positive_action_string, int res_id_negative_action_str, int res_id_cancal_action_str, String... extras)
    {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(dialogId, title, message, true,
                true, true, res_id_positive_action_string, res_id_negative_action_str, res_id_cancal_action_str, extras);
        newFragment.show(activity.getFragmentManager(), "dialog:" + dialogId);
    }

    public static void showErrorDialog(Activity activity, int dialogId, String title, String message)
    {
        try
        {
            DialogFragment newFragment = MyAlertDialogFragment.newInstance(dialogId, title, message, true, false);
            newFragment.show(activity.getFragmentManager(), "dialog:" + dialogId);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
