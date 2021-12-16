package com.jnu.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapFragment;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapViewFragment extends Fragment {

    private View inflate;
    MapView mMapView;
    private BaiduMap mBaiduMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_map_view, container, false);
        initView();
        return inflate;
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public void initView() {
        mMapView = (MapView) inflate.findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //设置logo
        mMapView.setLogoPosition(LogoPosition.logoPostionCenterTop);


//定义Maker坐标点
        LatLng point = new LatLng(22.255925, 113.541112);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.iv_mark);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .title("珠海暨南大学")
                .position(point) //必传参数
                .icon(bitmap) //必传参数
                .draggable(true);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

//对 marker 添加点击相应事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                Toast.makeText(getActivity(), "Marker被点击了！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //设定中心点坐标
        LatLng cenpt = new LatLng(22.255925, 113.541112);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(cenpt)
                //放大地图到20倍
                .zoom(20)
                .build();

        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        String url = "http://file.nidama.net/class/mobile_develop/data/bookstore.json";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject json = null;
                try {
                    json = new JSONObject(response.body().string());
                    JSONArray data = json.getJSONArray("shops");
                    for (int i = 0; i < 1; i++) {
                        JSONObject value = data.getJSONObject(1);
                        //获取到title值
                        final String title = value.getString("name");
                        final String latitude = value.getString("latitude");
                        final String longitude = value.getString("longitude");

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //定义Maker坐标点
                                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
//构建Marker图标
                                BitmapDescriptor bitmap = BitmapDescriptorFactory
                                        .fromResource(R.drawable.iv_mark);
//构建MarkerOption，用于在地图上添加Marker
                                OverlayOptions option = new MarkerOptions()
                                        .title(title)
                                        .position(point) //必传参数
                                        .icon(bitmap) //必传参数
                                        .draggable(true);
//在地图上添加Marker，并显示
                                mBaiduMap.addOverlay(option);

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, 200, 200);
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }
}
