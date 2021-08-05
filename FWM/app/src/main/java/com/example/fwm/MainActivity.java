package com.example.fwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();









        //splash screen fadeout and in or sleep work down

        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 2 seconds
                    sleep(2*1000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),login.class);

                    startActivity(i);



                    Paper.init(MainActivity.this);
                    if(Paper.book().read("user_id")!=null)
                    {
                        Intent intent = new Intent(MainActivity.this, FWMNavigation.class);


                        String id=Paper.book().read("user_id");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        Log.e("User id =", id);
                    }
                    else {

                        Intent intent = new Intent(MainActivity.this, login.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);                        Log.e("no id found=", "0");
                    }








                    //Remove activity

                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();

    }
}