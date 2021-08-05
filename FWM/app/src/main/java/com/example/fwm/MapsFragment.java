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

public class MapsFragment extends Fragment {
    GoogleMap gMap;
    String createdEventId="no id";
    LinearLayout  storeToDb;
    String lg="24.8630439",lt="67.0746475";
    private OnMapReadyCallback callback = new OnMapReadyCallback() {



        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gMap=googleMap;
            LatLng karachi = new LatLng(24.8630439, 67.0746475);
            MapsFragment.this.gMap.clear();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(karachi, 15.0f));
            googleMap.addMarker(new MarkerOptions().position(karachi).title("Marker Default"));
            String title=googleMap.getCameraPosition().toString();

            Log.e("title==",title);




            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                public void onMapClick(LatLng latLng) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                    MapsFragment.this.gMap.clear();
                    MapsFragment.this.gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                    MapsFragment.this.gMap.addMarker(markerOptions);
                    lg= String.valueOf(latLng.longitude);
                    lt= String.valueOf(latLng.latitude);

                }
            });


        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            createdEventId=bundle.getString("createdEventId", createdEventId);
        }





        View root=inflater.inflate(R.layout.fragment_maps, container, false);

        storeToDb =(LinearLayout)root.findViewById(R.id.storeeventfood);
        LinearLayout clickButton = (LinearLayout) root.findViewById(R.id.nextToDish);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gotodish();

            }
        });
        LinearLayout save=(LinearLayout) root.findViewById(R.id.saveMap);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

       //here we save map location
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please Wait..");


                progressDialog.show();
                StringRequest request=new StringRequest(Request.Method.POST, "https://mharix.000webhostapp.com/FWM/update_location.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("lt",lt);
                        map.put("lg",lg);
                        map.put("eid",createdEventId);

                        return map;
                    }
                };


                RequestQueue queue= Volley.newRequestQueue(getActivity());
                queue.add(request);

            }
        });

        return root;
    }

    private void gotodish() {

            Bundle passingValues = new Bundle();
            passingValues.putString("createdEventId", createdEventId);
            FoodDish fragment= new FoodDish();
            fragment.setArguments(passingValues);
        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();

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