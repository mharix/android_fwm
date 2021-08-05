package com.example.fwm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fwm.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import customfonts.EditText_Poppins_Regular;


public class FoodDrinks extends Fragment {

    EditText_Poppins_Regular ed_name;
    String str_name;
    String namePattern= "[ABC]\\s[ABC]";
    String url = "https://mharix.000webhostapp.com/FWM/add_food.php";
    String createdEventId="no id";

    public FoodDrinks() {
        // Required empty public constructor
    }



    LinearLayout linearlist,save, storeToDb;
    CheckBox checkBox;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            createdEventId=bundle.getString("createdEventId", createdEventId);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_food_drinks, container, false);
        ed_name = root.findViewById(R.id.ed_name);
        linearlist=(LinearLayout)root.findViewById(R.id.checkboxList);
        save=(LinearLayout)root.findViewById(R.id.saveDish);
        storeToDb =(LinearLayout)root.findViewById(R.id.storeeventfood);


        fetchDetails();
        LinearLayout clickButton = (LinearLayout) root.findViewById(R.id.nextToHome);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gotoHome();

            }
        });

        storeToDb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("inside next","0");
                final int childCount = linearlist.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View vw = linearlist.getChildAt(i);
                    // Do something with v.
                    if (vw instanceof CheckBox)
                    {
                        CheckBox c = (CheckBox) vw;
                        if(c.isChecked())
                        {
                            Log.e("check box=",c.getText().toString());
                            c.setEnabled(false);
                        } }}
                if(selecteditem!=null)
                {
                    for (int j=0;j<selecteditem.size();j++) {
                        Log.e("list item in loop=",selecteditem.get(j).toString());
                        //add this entries to db here
                        additemdb(createdEventId,selecteditem.get(j));

                        // Toast.makeText(getActivity(), createdEventId.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                else if(selecteditem==null){
                    Toast.makeText(getActivity(),"Select Food !" ,Toast.LENGTH_SHORT).show();
                }

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please Wait..");


                if (  ed_name.getText().toString().matches("") ||
                        ed_name.getText().toString().matches(" ") ||
                        ed_name.getText().toString().matches("  ") ||
                        ed_name.getText().toString().matches("    ") ||
                        ed_name.getText().toString().matches("     ") ||
                        ed_name.getText().toString().matches("      ") ||
                        ed_name.getText().toString().matches("       ") ||
                        ed_name.getText().toString().matches("        ") ||
                        ed_name.getText().toString().matches("         ") ||
                        ed_name.getText().toString().matches("           ") ||

                        ed_name.getText().toString().trim().matches(namePattern)   )

                {    ed_name.setError("enter food dish name !"); }

                else {

                    progressDialog.show();
                    str_name =   ed_name.getText().toString().trim();

                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressDialog.dismiss();

                            if (response.trim().equals("Food Already Exists !")){ Toast.makeText(getActivity(), "food already exist in database !", Toast.LENGTH_SHORT).show();}
                            else{
                                //Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                //refresh fetching details  activity here
                                fetchDetails();
                            }

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

                            params.put("name", str_name);
                            params.put("type", "drink");

                            return params;

                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(request);


                }








            }
        });



        return root;
    }

    private void additemdb(String createdEventId, Integer integer) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        final String  eventid=   createdEventId;
        final String food=integer.toString();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mharix.000webhostapp.com/FWM/add_foodfor_event.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();


                // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
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

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    private void gotoHome( )
    {
    Intent i=new Intent(getActivity(),SendSms.class);
    startActivity(i);
    }


    //to get json response
    private void showJSON(String response) {
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
                checkBox=new CheckBox(getActivity());
                checkBox.setId(Integer.parseInt(iid));
                checkBox.setText(name1);
                checkBox.setOnClickListener(getOnClick());
                linearlist.addView(checkBox);
                //Toast.makeText(getActivity(), jsonObj1.getString("name"), Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    CheckBox cb;
    ArrayList<Integer> selecteditem=new ArrayList<Integer>();
    private View.OnClickListener getOnClick() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //here we get checked check boxes values
                cb =(CheckBox)view;
                if(cb.isChecked())
                {
                    selecteditem.add(cb.getId());
                    Log.e("items in add=", selecteditem.toString());
                    Toast.makeText(getActivity(),"Add="+cb.getId()+"-"+cb.getText().toString(),Toast.LENGTH_LONG).show();
                }
                else if(!cb.isChecked()){
                    selecteditem.remove(new Integer(cb.getId()));
                    Log.e("items in remove=", selecteditem.toString());
                    Toast.makeText(getActivity(),"Removed ="+cb.getId()+"-"+cb.getText().toString(),Toast.LENGTH_LONG).show();

                }




            }
        };
    }

    public void fetchDetails()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("fecthing details..");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mharix.000webhostapp.com/FWM/get_food.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                showJSON(response);


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


                params.put("type", "drink");

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }
}//clas end