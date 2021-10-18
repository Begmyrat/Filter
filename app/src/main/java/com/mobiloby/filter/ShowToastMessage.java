package com.mobiloby.filter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ShowToastMessage {
    static Toast toast;
    static TextView alerttext;
    static View toastlayout;

    public static void show(Activity activity, String message){

        LayoutInflater inflater = activity.getLayoutInflater();
        toastlayout = inflater.inflate(R.layout.alertbox, (ViewGroup)activity.findViewById(R.id.alertlayout));
        toast = new Toast(activity);
        toast.setGravity(Gravity.TOP,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        alerttext = (TextView) toastlayout.findViewById(R.id.alerttext);

        alerttext.setText(message);
        toast.setView(toastlayout);
        toast.show();
    }
}
