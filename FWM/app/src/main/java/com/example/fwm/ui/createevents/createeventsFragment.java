package com.example.fwm.ui.createevents;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fwm.FWMNavigation;
import com.example.fwm.FoodDessert;
import com.example.fwm.FoodDish;
import com.example.fwm.MapsFragment;
import com.example.fwm.SendSms;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.fwm.R;

import io.paperdb.Paper;

import static android.app.Activity.RESULT_OK;

public class createeventsFragment extends Fragment {
    String namePattern= "[ABC]\\s[ABC]";
    String createdEventId;
    EditText ed_name, ed_address,ed_dress;
    DatePicker datepicker;
    TimePicker timepicker;
    LinearLayout upload;
    ImageView img;
    Bitmap bitmap;
    String encodeImageString;
    private static final String url="https://mharix.000webhostapp.com/FWM/create_event.php";

    private com.example.fwm.ui.createevents.createeventsViewModel createeventsViewModel;

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


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createeventsViewModel =
                ViewModelProviders.of(this).get(com.example.fwm.ui.createevents.createeventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_createevents, container, false);
        //db work start here
        img=(ImageView)root.findViewById(R.id.imageView);
        upload=(LinearLayout)root.findViewById(R.id.nextToMap);
        ed_name = (EditText) root.findViewById(R.id.ed_name);
        ed_address = (EditText) root.findViewById(R.id.ed_address);
        ed_dress = (EditText) root.findViewById(R.id.ed_dress);
        datepicker=(DatePicker)root.findViewById(R.id.date_picker);
        timepicker=(TimePicker)root.findViewById(R.id.time_picker);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(getActivity())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                //here we done validation
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

                {    ed_name.setError("enter event name !"); }

                else if (  ed_address.getText().toString().matches("") ||
                        ed_address.getText().toString().matches(" ") ||
                        ed_address.getText().toString().matches("  ") ||
                        ed_address.getText().toString().matches("    ") ||
                        ed_address.getText().toString().matches("     ") ||
                        ed_address.getText().toString().matches("      ") ||
                        ed_address.getText().toString().matches("       ") ||
                        ed_address.getText().toString().matches("        ") ||
                        ed_address.getText().toString().matches("         ") ||
                        ed_address.getText().toString().matches("           ")    )

                {    ed_address.setError("enter address name !"); }
                else if (  ed_dress.getText().toString().matches("") ||
                        ed_dress.getText().toString().matches(" ") ||
                        ed_dress.getText().toString().matches("  ") ||
                        ed_dress.getText().toString().matches("    ") ||
                        ed_dress.getText().toString().matches("     ") ||
                        ed_dress.getText().toString().matches("      ") ||
                        ed_dress.getText().toString().matches("       ") ||
                        ed_dress.getText().toString().matches("        ") ||
                        ed_dress.getText().toString().matches("         ") ||
                        ed_dress.getText().toString().matches("           ")    )

                {    ed_dress.setError("enter dress code name !"); }
                else if(img.getDrawable()==null){Toast.makeText(getActivity(),"select image",Toast.LENGTH_LONG).show();}
                else{uploaddatatodb();}



            }
        });

        //db work end here
        return root;
    }//on create view end



    //other functions of class

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Uri filepath=data.getData();
            try
            {
                InputStream inputStream=getActivity().getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void uploaddatatodb()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait..");

        final String name = ed_name.getText().toString().trim();
        final String address = ed_address.getText().toString().trim();
        final String dress = ed_dress.getText().toString().trim();
        final String datetime=datepicker.getYear()+"-"+((int)(datepicker.getMonth())+1)+"-"+datepicker.getDayOfMonth()+" "+
                timepicker.getHour()+":"+timepicker.getMinute();
        Log.e("date time valuse =", datepicker.getYear()+"-"+((int)(datepicker.getMonth())+1)+"-"+datepicker.getDayOfMonth()+" "+
                timepicker.getHour()+":"+timepicker.getMinute());

        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                ed_name.setText("");
                ed_address.setText("");
                ed_dress.setText("");
                img.setImageResource(R.drawable.ic_baseline_home_24);
                createdEventId=response.toString();

                Bundle passingValues = new Bundle();
                passingValues.putString("createdEventId", response.toString());
               Toast.makeText(getActivity(),createdEventId,Toast.LENGTH_LONG).show();
                MapsFragment fragment= new MapsFragment();
                fragment.setArguments(passingValues);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
                //here we get created event id for food entries

//               FoodDish fragment= new FoodDish();
//                fragment.setArguments(passingValues);
//               getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();




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
                map.put("name",name);
                map.put("date",datetime);
                map.put("address",address);
                map.put("dress",dress);
                map.put("user_id", Paper.book().read("user_id").toString());
                map.put("imageupload",encodeImageString);
                return map;
            }
        };


        RequestQueue queue= Volley.newRequestQueue(getActivity());
        queue.add(request);
    }





}//class end