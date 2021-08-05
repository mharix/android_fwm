package com.example.fwm;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.LimitColumn;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.util.ArrayList;
import java.util.List;

public class SendSms extends AppCompatActivity {
    private static final int CONTACT_PICKER_REQUEST = 202;
    private EditText txt_message;
    private EditText txt_number;

    private Button btn_sms,home;

    private Button btn_choose;
    List<ContactResult> results=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_send_sms);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent i= new Intent(SendSms.this, FWMNavigation.class);
                startActivity(i);
            }
        };
        SendSms.this.getOnBackPressedDispatcher().addCallback(this, callback);
            txt_message = findViewById(R.id.txt_message);

            txt_message.setText("Hi! Your Are invited for the event :   !");



        txt_number = findViewById(R.id.txt_mobile_number);



        btn_sms = findViewById(R.id.btn_sms);
        home = findViewById(R.id.btn_home);
        btn_choose = findViewById(R.id.button_choose_contacts);


        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_CONTACTS
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();

//        if(!isAccessibilityOn(getApplicationContext())){
//            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent i= new Intent(SendSms.this,FWMNavigation.class);
             txt_message.setText("");
             txt_number.setText("");

             startActivity(i);
            }
        });
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MultiContactPicker.Builder(SendSms.this)
                        .hideScrollbar(false)
                        .showTrack(true)
                        .searchIconColor(Color.WHITE)
                        .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE)
                        .handleColor(ContextCompat.getColor(SendSms.this, R.color.colorPrimary))
                        .bubbleColor(ContextCompat.getColor(SendSms.this, R.color.colorPrimary))
                        .bubbleTextColor(Color.WHITE)
                        .setTitleText("Select Contacts")
                        .setLoadingType(MultiContactPicker.LOAD_ASYNC)
                        .limitToColumn(LimitColumn.NONE)
                        .setActivityAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                android.R.anim.fade_in,
                                android.R.anim.fade_out)
                        .showPickerForResult(CONTACT_PICKER_REQUEST);
            }
        });

        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Button clciked before","0");
                MySMSservice.startActionSMS(getApplicationContext(),txt_message.getText().toString(),
                        1,results);
                Toast.makeText(SendSms.this, "Invitation Has been Sent", Toast.LENGTH_SHORT).show();
                Log.e("Button clciked after","0");
            }
        });





        IntentFilter intent = new IntentFilter("my.own.broadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalBroadcastReceiver,intent);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CONTACT_PICKER_REQUEST){
            if(resultCode == RESULT_OK) {
                results = MultiContactPicker.obtainResult(data);
                StringBuilder names = new StringBuilder(results.get(0).getDisplayName());
                for (int j=0;j<results.size();j++){
                    if(j!=0)
                        names.append(", ").append(results.get(j).getDisplayName());
                }
                txt_number.setText(names);
                Log.d("MyTag", results.get(0).getDisplayName());
            } else if(resultCode == RESULT_CANCELED){
                System.out.println("User closed the picker without selecting items.");
            }
        }
    }

    private BroadcastReceiver myLocalBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result= intent.getStringExtra("result");
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    };



    private boolean isAccessibilityOn(Context context) {
        int accessibilityEnabled = 0;
        final String service = context.getPackageName () + "/" + WhatAppAccessibilityService.class.getCanonicalName ();
        try {
            accessibilityEnabled = Settings.Secure.getInt (context.getApplicationContext ().getContentResolver (), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ignored) {  }

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter (':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString (context.getApplicationContext ().getContentResolver (), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                colonSplitter.setString (settingValue);
                while (colonSplitter.hasNext ()) {
                    String accessibilityService = colonSplitter.next ();

                    if (accessibilityService.equalsIgnoreCase (service)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
