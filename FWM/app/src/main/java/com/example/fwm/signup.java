package com.example.fwm;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import customfonts.EditText_Poppins_Regular;

public class signup extends AppCompatActivity {
    private static final int SELECT_FILE = 0;


    EditText_Poppins_Regular ed_username, ed_email, ed_password, ed_phone;
    String str_name, str_email, str_password, str_phone;
    String namePattern= "[ABC]\\s[ABC]";

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{4,}$";
    String url = "https://mharix.000webhostapp.com/FWM/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();


        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent i= new Intent(signup.this, login.class);
                startActivity(i);
            }
        };
        signup.this.getOnBackPressedDispatcher().addCallback(this, callback);

        ed_email = findViewById(R.id.email);
        ed_username = findViewById(R.id.name);
        ed_password = findViewById(R.id.password);
        ed_phone = findViewById(R.id.phone);

    }

    public void moveToLogin( ) {

        startActivity(new Intent(getApplicationContext(), login.class));
        finish();
    }

    public void Register(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");


        if (ed_username.getText().toString().matches("") ||
                ed_username.getText().toString().matches(" ") ||
                ed_username.getText().toString().matches("  ") ||
                ed_username.getText().toString().matches("    ") ||
                ed_username.getText().toString().matches("     ") ||
                ed_username.getText().toString().matches("      ") ||
                ed_username.getText().toString().matches("       ") ||
                ed_username.getText().toString().matches("        ") ||
                ed_username.getText().toString().matches("         ") ||
                ed_username.getText().toString().matches("           ") ||



                ed_username.getText().toString().trim().matches(namePattern)   )  {  ed_username.setError("enter your name !"); }
        else if (ed_phone.getText().toString().matches("") || ed_phone.getText().toString().trim().length()<11 )  {  ed_phone.setError("enter your phone number!"); }
        else if (ed_email.getText().toString().matches("") || !Patterns.EMAIL_ADDRESS.matcher(ed_email.getText().toString()).matches()   )  {  ed_email.setError("enter correct email !"); }
        else if (ed_password.getText().toString().matches("") || ed_password.getText().toString().length()<3  )  {  ed_password.setError("enter 3 to 8 digit password !"); }




        else {

            progressDialog.show();
            str_name = ed_username.getText().toString().trim();
            str_email = ed_email.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();
            str_phone = ed_phone.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if (response.trim().equals("Email Already Exists !")){ Toast.makeText(signup.this, "Email already exist in database !", Toast.LENGTH_SHORT).show();}
                    else{
                        Toast.makeText(signup.this, response, Toast.LENGTH_SHORT).show();
                         moveToLogin();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(signup.this, "Erorr"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("uname", str_name);
                    params.put("uemail", str_email);
                    params.put("upass", str_password);
                    params.put("uphone", str_phone);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(signup.this);
            requestQueue.add(request);


        }






    }






}