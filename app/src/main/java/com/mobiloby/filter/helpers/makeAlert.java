package com.mobiloby.filter.helpers;


import android.content.Context;

import com.mobiloby.filter.R;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class makeAlert {

    public static void uyarıVer(String title, String messages, Context context, Boolean hata)
    {
        if (hata)
        {
            final PrettyDialog pDialog = new PrettyDialog(context);
            pDialog.setTitle(title)
                    .setIcon(R.drawable.pdlg_icon_close)
                    .setIconTint(R.color.secondtextcolor)
                    .setMessage(messages)
                    .addButton( "Tamam",
                            R.color.pdlg_color_white,
                            R.color.secondtextcolor,
                            new PrettyDialogCallback() {
                                @Override
                                public void onClick() {
                                    pDialog.dismiss();
                                }
                            })
                    .show();
        }
        else
        {
            final PrettyDialog pDialog = new PrettyDialog(context);
            pDialog.setTitle(title)
                    .setIcon(R.drawable.pdlg_icon_success)
                    .setIconTint(R.color.secondtextcolor)
                    .setMessage(messages)
                    .addButton( "Tamam",
                            R.color.pdlg_color_white,
                            R.color.secondtextcolor,
                            new PrettyDialogCallback() {
                                @Override
                                public void onClick() {
                                    pDialog.dismiss();
                                }
                            })
                    .show();
        }
    }
}
