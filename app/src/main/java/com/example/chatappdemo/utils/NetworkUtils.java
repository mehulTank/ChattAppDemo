package com.example.chatappdemo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import com.example.chatappdemo.R;


/**
 * @purpose commonly used functions
 * @purpose
 */
public class NetworkUtils {


    public static boolean isNetworkConnected(final Context context, boolean settingDialog) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        if (mConnectivityManager != null) {


            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null) {
                    if (netInfo.isConnectedOrConnecting()) {
                        return true;
                    }
                }
            } else {

                NetworkCapabilities capabilities = mConnectivityManager.getNetworkCapabilities(mConnectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    }
                }
            }

        }


        if (settingDialog) {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);


            dialogBuilder.setTitle(context.getString(R.string.app_name));
            dialogBuilder.setCancelable(false);
            dialogBuilder.setMessage(context.getString(R.string.check_connection));
            dialogBuilder.setPositiveButton(context.getString(R.string.settings), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));


                }
            });
            dialogBuilder.setNegativeButton(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = dialogBuilder.create();
            if (!alertDialog.isShowing()) {
                alertDialog.show();
            }


            return false;
        }
        return false;
    }


}
