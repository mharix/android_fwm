package com.example.fwm;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fwm.ui.myeventRegDetails;
import com.example.fwm.ui.regevents.regeventsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Line;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class showMapsFragment extends Fragment {
    GoogleMap gMap;
    String createdEventId="no id";
    LinearLayout  storeToDb;
    Double lg,lt;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {



        @Override
        public void onMapReady(GoogleMap googleMap) {
            gMap=googleMap;
            LatLng karachi = new LatLng(lt, lg);
            showMapsFragment.this.gMap.clear();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(karachi, 15.0f));
            googleMap.addMarker(new MarkerOptions().position(karachi).title("Event Location"));
            String title=googleMap.getCameraPosition().toString();









        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            lt= Double.valueOf(bundle.getString("latitude", String.valueOf(lt)));
            lg= Double.valueOf(bundle.getString("longitude", String.valueOf(lg)));
        }


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                myeventRegDetails fragment= new myeventRegDetails();

                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();

            }
        };



        View root=inflater.inflate(R.layout.fragment_show_maps, container, false);


        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


}//class end