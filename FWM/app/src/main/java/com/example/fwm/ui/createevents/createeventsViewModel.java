package com.example.fwm.ui.createevents;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class createeventsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public createeventsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is create events fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}