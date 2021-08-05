package com.example.fwm.ui.reports;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class reportsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public reportsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is repots fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}