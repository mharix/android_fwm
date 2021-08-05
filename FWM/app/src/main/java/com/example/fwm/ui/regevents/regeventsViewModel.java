package com.example.fwm.ui.regevents;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class regeventsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public regeventsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is reg events fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}