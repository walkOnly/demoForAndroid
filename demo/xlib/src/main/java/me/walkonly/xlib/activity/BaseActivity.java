package me.walkonly.xlib.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.walkonly.xlib.http.GsonResponseHandler;

public class BaseActivity extends AppCompatActivity {

    private List<GsonResponseHandler> httpResponseHandler = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        for (GsonResponseHandler handler : httpResponseHandler) {
            handler.cancel();
        }
        httpResponseHandler.clear();

        super.onDestroy();
    }

    public void addHttpResponseHandler(GsonResponseHandler handler) {
        httpResponseHandler.add(handler);
    }

}
