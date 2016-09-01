package com.hhxc.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.hhxc.demo.R;

import butterknife.Bind;
import butterknife.OnClick;
import me.walkonly.lib.activity.BaseActivity;
import me.walkonly.lib.annotation.C_Activity;

/**
 * 地图页面
 */
@C_Activity(R.layout.activity_map)
public class MapActivity extends BaseActivity {

    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String NAME = "name";
    public static final String ADDR = "address";

    @Bind(R.id.map)
    MapView mapView;
    @Bind(R.id.name)
    TextView nameTV;
    @Bind(R.id.address)
    TextView addressTV;

    @Bind(R.id.view_empty_common)
    LinearLayout emptyView;
    @Bind(R.id.empty_img)
    ImageView emptyImg;
    @Bind(R.id.empty_text)
    TextView emptyText;

    private AMap aMap;

    private double latitude;
    private double longitude;

    private String strLat;
    private String strLng;
    private String name;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapView.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        strLat = bundle.getString(LAT);
        strLng = bundle.getString(LNG);
        name = bundle.getString(NAME);
        address = bundle.getString(ADDR);

        // 测试代码
//        strLat = "39.973373";
//        strLng = "116.492752";
//        name = "欢乐谷训练场地";
//        address = "朝阳区酒仙桥东路10号";

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void initView() {
        if (isValidLatLng(strLat, strLng)) {
            latitude = Double.parseDouble(strLat);
            longitude = Double.parseDouble(strLng);
            initMap();
        } else {
            showView(emptyView);
            emptyText.setText("无效的经纬度");
        }

        nameTV.setText(name);
        addressTV.setText(address);
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        if (aMap == null)
            aMap = mapView.getMap();

        LatLng latLng = new LatLng(latitude, longitude);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15)); // 缩放级别
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng)); // 中心点

        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
        aMap.addMarker(markerOptions);
    }

    @OnClick(R.id.back)
    void goBack() {
        finish();
    }

    private boolean isValidLatLng(String lat, String lng) {
        if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) return false;

        if ("0".equals(lat) || "0.0".equals(lat)) return false;

        if ("0".equals(lng) || "0.0".equals(lng)) return false;

        return true;
    }

}
