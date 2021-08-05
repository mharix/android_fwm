package com.example.fwm.ui;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fwm.R;
import com.example.fwm.showMapsFragment;
import com.example.fwm.ui.home.HomeFragment;
import com.example.fwm.ui.regevents.regeventsFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;


public class myeventRegDetails extends Fragment {
    LinearLayout linearlist;
    TextView itemlist;
    TextView ename,eTime,eAddress,eDress;
    LinearLayout saveFooPref, viewMap;
    String eventId,lt,lg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                regeventsFragment fragment= new regeventsFragment();

                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            eventId=bundle.getString("eventId", eventId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_my_event_reg_details, container, false);
        saveFooPref=(LinearLayout)root.findViewById(R.id.saveFooPref);
        viewMap =(LinearLayout)root.findViewById(R.id.viewMap);
        ename=(TextView)root.findViewById(R.id.eventName);
        eTime=(TextView)root.findViewById(R.id.eventTime);
        eAddress=(TextView)root.findViewById(R.id.eventAddress);
        eDress=(TextView)root.findViewById(R.id.eventDress);
        linearlist=(LinearLayout)root.findViewById(R.id.checkboxList);


        //this fetch event details
        fetchDetails("https://mharix.000webhostapp.com/FWM/get_eventDetails.php",0);
        //this fetch event food items
        Log.e("detail fetch run","0");
        fetchDetails("https://mharix.000webhostapp.com/FWM/get_enter_eventfood.php",1);

        Log.e("check fetch run","0");


        viewMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Bundle passingValues = new Bundle();
                passingValues.putString("latitude", lt);
                passingValues.putString("longitude", lg);
                showMapsFragment fragment= new showMapsFragment();
                fragment.setArguments(passingValues);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();

            }
        });


        return root;
    }


    //to get json response
    private void showJSON(String response) {
        ArrayList<Integer> list = new ArrayList<>();
        try {

            JSONObject jsonObj = new JSONObject(response);

            JSONArray ja_data = jsonObj.getJSONArray("result");
            int length = ja_data.length();
            for(int i=0; i<=length; i++) {
                JSONObject jsonObj1 = ja_data.getJSONObject(i);
                //put checkboxes data


                ename.setText(jsonObj1.getString("name"));
                eAddress.setText("Address : "+jsonObj1.getString("address"));
                eTime.setText("Time : "+jsonObj1.getString("date"));
                eDress.setText("Dress Code : "+jsonObj1.getString("dress"));

                lt=jsonObj1.getString("lt");
                lg=jsonObj1.getString("lg");

                String url = "https://mharix.000webhostapp.com/FWM/event_images/"+jsonObj1.getString("image");


                Picasso.get().load(url).into(new Target(){

                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        ename.setBackground(new BitmapDrawable(getActivity().getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }




                });

                // String name1 = jsonObj1.getString("name");

                //Toast.makeText(getActivity(), jsonObj1.getString("name"), Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    //this show json is for check boxes
    //to get json response
    private void showJSONcheckbox(String response) {
        Log.e("called showJSONcheckbox",response);
        ArrayList<Integer> list = new ArrayList<>();
        try {
            linearlist.removeAllViews();//to clear all check boxes
            JSONObject jsonObj = new JSONObject(response);

            JSONArray ja_data = jsonObj.getJSONArray("result");
            int length = ja_data.length();
            for(int i=0; i<=length; i++) {
                JSONObject jsonObj1 = ja_data.getJSONObject(i);
                //put checkboxes data

                String iid = jsonObj1.getString("id");
                String name1 = jsonObj1.getString("name");

                itemlist =new TextView(getActivity());
                itemlist.setId(Integer.parseInt(iid));
                itemlist.setText(name1);
                itemlist.setTypeface(itemlist.getTypeface(), Typeface.ITALIC);
                Log.e("inside Loop=",name1);

                linearlist.addView(itemlist);
                //Toast.makeText(getActivity(), jsonObj1.getString("name"), Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public void fetchDetails(String url, final int flag)
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("fecthing details..");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                if(flag==0) {
                    Log.e("event details json","0");
                    showJSON(response);}

                else if(flag==1) {
                    Log.e("check box json","0");
                    showJSONcheckbox(response);}



                Toast.makeText(getActivity(), "All Records fetched!", Toast.LENGTH_SHORT).show();
                //refresh activity here


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Erorr"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put("eid", eventId);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }



    //this will store data to db

    private void additemdb(String createdEventId, Integer integer) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        final String  eventid=   createdEventId;
        final String food=integer.toString();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mharix.000webhostapp.com/FWM/add_foodforpref_event.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();


                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                //refresh fetching details  activity here
                //goto next dessert for there entries here
                // gotodessert(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Erorr"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("sid", eventid);
                params.put("fid", food);
                params.put("uid", (String) Paper.book().read("user_id"));

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}//class end