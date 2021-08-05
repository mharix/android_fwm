package com.example.fwm;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import customfonts.EditText_Poppins_Regular;
import io.paperdb.Paper;

public class login extends AppCompatActivity {

    LinearLayout signin;
    EditText_Poppins_Regular ed_email,  ed_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

            }
        };
        login.this.getOnBackPressedDispatcher().addCallback(this, callback);

        Paper.init(login.this);
        if(Paper.book().read("user_id")!=null)
        {
            Intent intent = new Intent(login.this, FWMNavigation.class);
            String id=Paper.book().read("user_id");
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            Log.e("User id =", id);
        }






        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ed_email = findViewById(R.id.email);
        ed_password = findViewById(R.id.password);
        signin=(LinearLayout)findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //1st validate email and password
                if (ed_email.getText().toString().matches("") || !Patterns.EMAIL_ADDRESS.matcher(ed_email.getText().toString()).matches()   )  {  ed_email.setError("enter correct email !"); }
                else if (ed_password.getText().toString().matches("") || ed_password.getText().toString().length()<3  )  {  ed_password.setError("enter 3 to 8 digit password !"); }
                else{
                    //then mysql login and fetch user details if any

                    fetchDetails(ed_email.getText().toString(),ed_password.getText().toString());
                }



                //then paperdb

            }
        });


    }

    public void opensignup(View view) {
        Intent intent = new Intent(view.getContext(), signup.class);
        startActivity(intent);

        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }


    public void fetchDetails(final String email, final String pass)
    {
        final ProgressDialog progressDialog = new ProgressDialog(login.this);
        progressDialog.setMessage("Checking Login details..");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://mharix.000webhostapp.com/FWM/get_login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Log.e("before id",".");
                if(response.trim().equals("login failed!")){
                    Log.e("inside if response=",response);
                    Toast.makeText(login.this, "Wrong Login Credentials!", Toast.LENGTH_SHORT).show();}
                else{showJSON(response);}


               //Toast.makeText(login.this, "out of if else reseponse="+response, Toast.LENGTH_SHORT).show();



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(login.this, "Erorr"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

//here put entered email and  pass word
                params.put("email", email);
                params.put("pass", pass);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(login.this);
        requestQueue.add(request);

    }

    //in this showjson we set  user info to db

    private void showJSON(String response) {
        ArrayList<Integer> list = new ArrayList<>();
        try {

            JSONObject jsonObj = new JSONObject(response);

            JSONArray ja_data = jsonObj.getJSONArray("result");
            int length = ja_data.length();

                JSONObject jsonObj1 = ja_data.getJSONObject(0);
                //put checkboxes data
            Paper.init(login.this);
                String iid = jsonObj1.getString("id");
                String name1 = jsonObj1.getString("name");
                Paper.book().write("user_id",iid);
            Log.e("usrid=",iid);
            Paper.book().write("user_name",name1);
            Log.e("name=",name1);
            moveToHome();

                //Toast.makeText(login.this, "id="+iid+"  name="+name1, Toast.LENGTH_LONG).show();



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void moveToHome() {
        Intent i= new Intent(login.this,FWMNavigation.class);
        startActivity(i);
    }
}//class end