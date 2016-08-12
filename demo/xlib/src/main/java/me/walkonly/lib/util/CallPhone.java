package me.walkonly.lib.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CallPhone {
    public static void call(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(("tel:" + phoneNum)));
        //context.startActivity(intent);
    }
}
