package com.mobiloby.filter;


import android.content.Context;

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
                    .setIconTint(R.color.pdlg_color_red)
                    .setMessage(messages)
                    .addButton( "Ok",
                            R.color.pdlg_color_white,
                            R.color.pdlg_color_red,
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
                    .setIconTint(R.color.pdlg_color_green)
                    .setMessage(messages)
                    .addButton( "Ok",
                            R.color.pdlg_color_white,
                            R.color.pdlg_color_green,
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
