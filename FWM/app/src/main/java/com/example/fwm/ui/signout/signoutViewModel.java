package com.example.fwm.ui.signout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class signoutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public signoutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is sing out fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}