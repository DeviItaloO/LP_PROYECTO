package com.example.notasrecordatorio.ui.notas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotasViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}