package me.walkonly.lib.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Button;

import me.walkonly.xlib.R;

public class DialogUtils {

    private static ProgressDialog progressDialog;

    private static DialogCallback dialogCallback;

    private DialogUtils() {
    }

    public static void showDialog(Context context, String title, String message, String positive, String negative, DialogCallback callback) {
        dialogCallback = callback;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        if (!TextUtils.isEmpty(positive)) {
            builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (dialogCallback != null) dialogCallback.onPositiveClicked();
                }
            });
        }
        if (!TextUtils.isEmpty(negative)) {
            builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (dialogCallback != null) dialogCallback.onNegativeClicked();
                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.show();

        Button negButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (negButton != null)
            negButton.setTextColor(context.getResources().getColor(R.color.blue_500));
        Button posButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (posButton != null)
            posButton.setTextColor(context.getResources().getColor(R.color.blue_500));
    }

    public static void showDialog(Context context, String title, String message, String positive, DialogCallback callback) {
        showDialog(context, title, message, positive, "取消", callback);
    }

    public static void showDialog(Context context, String title, String message) {
        showDialog(context, title, message, "好的", null, null);
    }

    public static void showDialog(Context context, String title, String message, DialogCallback callback) {
        showDialog(context, title, message, "好的", null, callback);
    }

    public static void showProgressDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (progressDialog == null) return;
        progressDialog.dismiss();
    }

    public static class DialogCallback {
        public void onPositiveClicked() {

        }

        public void onNegativeClicked() {

        }
    }

}
