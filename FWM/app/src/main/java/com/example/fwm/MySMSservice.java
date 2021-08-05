package com.example.fwm;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.wafflecopter.multicontactpicker.ContactResult;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import javax.security.auth.callback.PasswordCallback;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MySMSservice extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SMS = "com.geniobits.autosmssender.action.SMS";
    private static final String ACTION_WHATSAPP = "com.geniobits.autosmssender.action.WHATSAPP";

    // TODO: Rename parameters
    private static final String MESSAGE = "com.geniobits.autosmssender.extra.PARAM1";
    private static final String COUNT = "com.geniobits.autosmssender.extra.PARAM2";
    private static final String MOBILE_NUMBER = "com.geniobits.autosmssender.extra.PARAM3";
    private static final String IS_EACH_WORD = "com.geniobits.autosmssender.extra.PARAM4";

    public MySMSservice() {
        super("MySMSservice");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSMS(Context context, String message, int count, List<ContactResult> mobile_numbers) {
        Log.e("in sms action top","0");

        List<String> numbers =new ArrayList<String>();
        for(int i = 0;i<mobile_numbers.size();i++){
            numbers.add(mobile_numbers.get(i).getPhoneNumbers().get(0).getNumber());
        }
        String[] numbersArray = numbers.toArray(new String[0]);

        Intent intent = new Intent(context, MySMSservice.class);
        intent.setAction(ACTION_SMS);
        intent.putExtra(MESSAGE, message);
        intent.putExtra(COUNT, count);
        intent.putExtra(MOBILE_NUMBER,numbersArray);
        context.startService(intent);


        if(numbersArray.length!=0) {
            for(int j=0;j<numbersArray.length;j++) {


                    SmsManager smsManager = SmsManager.getDefault();
                    Log.e("before send sms","0");
                    smsManager.sendTextMessage(numbersArray[j], null, message, null, null);
                    //sendBroadcastMessage("Result:"+ (i+1) + " "+ numbersArray[j]);


            }
        }
        Log.e("in sms action bottom","0");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
