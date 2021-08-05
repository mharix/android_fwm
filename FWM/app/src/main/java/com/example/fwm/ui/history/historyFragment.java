package com.example.fwm.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fwm.CustomAdapter;
import com.example.fwm.CustomAdapterHistory;
import com.example.fwm.CustomAdapterReg;
import com.example.fwm.FWMNavigation;
import com.example.fwm.R;
import com.example.fwm.SubjectData;
import com.example.fwm.SubjectDataHistory;
import com.example.fwm.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class historyFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent i= new Intent(getActivity(), FWMNavigation.class);
                startActivity(i);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }



    ImageView imageView;
    TextView eventText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_history, container, false);

        fetchImages(root);

        return root;

    }


    public void fetchImages(final View root){

        StringRequest request = new StringRequest(Request.Method.POST, "https://mharix.000webhostapp.com/FWM/get_allmyHistoryevents.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(succes.equals("1")){
                                ListView list =root.findViewById(R.id.list);
                                ArrayList<SubjectDataHistory> arrayList = new ArrayList<SubjectDataHistory>();

                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String imageurl = object.getString("image");
                                    String name= object.getString("event_name");
                                    String date= object.getString("datetime");

                                    String url = "https://mharix.000webhostapp.com/FWM/event_images/"+imageurl;

                                    Log.e("id=",id.toString());
                                    Log.e("name=",name.toString());
                                    Log.e("image=",url.toString());
                                    Log.e("date=",date.toString());
                                    arrayList.add(new SubjectDataHistory(name, id, url,date));

                                }//loop end
                                if(arrayList.size()!=0){CustomAdapterHistory customAdapter = new CustomAdapterHistory(getActivity(), arrayList);
                                    Log.e("array size", String.valueOf(arrayList.size()));

                                list.setAdapter(customAdapter);}
                                else {
                                    Toast.makeText(getActivity(),"No record Found! ",Toast.LENGTH_LONG).show();
                                    HomeFragment fragment= new HomeFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
                                    }






                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

//here put entered email and  pass word
                params.put("uid", (String) Paper.book().read("user_id"));


                return params;

            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);


    }
}//class end