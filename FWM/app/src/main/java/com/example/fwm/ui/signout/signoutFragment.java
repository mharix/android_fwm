package com.example.fwm.ui.signout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.fwm.MainActivity;
import com.example.fwm.R;
import com.example.fwm.login;

import io.paperdb.Paper;

public class signoutFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_signout, container, false);
signOut();
        return root;
    }

    private void signOut( ) {
        Paper.init(getActivity());
        Paper.book().destroy();
        Intent i= new Intent(getActivity(), login.class);
        Log.e("sign outpaper destroyed","0");
        startActivity(i);
    }
}