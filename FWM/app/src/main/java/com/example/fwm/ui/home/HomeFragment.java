package com.example.fwm.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.fwm.R;
import com.example.fwm.ui.allevents.alleventsFragment;
import com.example.fwm.ui.createevents.createeventsFragment;
import com.example.fwm.ui.history.historyFragment;
import com.example.fwm.ui.info.infoFragment;
import com.example.fwm.ui.regevents.regeventsFragment;
import com.example.fwm.ui.reports.reportsFragment;

import io.paperdb.Paper;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.e("Home =", "0");

        View root = inflater.inflate(R.layout.fragment_home, container, false);





        CardView Ccreate=(CardView)root.findViewById(R.id.Ccreate);
        Ccreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createeventsFragment fragment= new createeventsFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
            }
        });

        CardView Callevents=(CardView)root.findViewById(R.id.Callevents);
        Callevents.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alleventsFragment fragment= new alleventsFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
            }
        });

        CardView Creport=(CardView)root.findViewById(R.id.Creport);
        Creport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reportsFragment fragment= new reportsFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
            }
        });

        CardView Cregevents=(CardView)root.findViewById(R.id.Cregevents);
        Cregevents.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                regeventsFragment fragment= new regeventsFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
            }
        });

        CardView Chistory=(CardView)root.findViewById(R.id.Chistory);
        Chistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                historyFragment fragment= new historyFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
            }
        });

        CardView Cinfo=(CardView)root.findViewById(R.id.Cinfo);
        Cinfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                infoFragment fragment= new infoFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
            }
        });



        TextView topName=(TextView) root.findViewById(R.id.topusername);Log.e("Home pepr initn=", "0");

        Paper.init(getActivity());

       topName.setText(Paper.book().read("user_name").toString());
        Log.e("Home paper after=", "0");
        return root;
    }



}//CLASS END