package com.namita.Common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.namita.medlifeapplication.R;

public class Constants {

    public static final String OTP = "1234";
    private static AlertDialog alertDialog;

    public static void showAlertDialog(Context mContext, String msg){
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        } else {
            alertDialog = new android.app.AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle)
                    .setTitle(mContext.getString(R.string.app_name))
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }
}
