package com.example.easyteamup.ui.manage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ManageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is manage fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}