package com.example.notasrecordatorio.ui.categoria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoryModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CategoryModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is category fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}