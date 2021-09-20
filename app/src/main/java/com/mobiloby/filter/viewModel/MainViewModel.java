package com.mobiloby.filter.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {


    MutableLiveData<Integer> indexFragment = new MutableLiveData<>();

    public MutableLiveData<Integer> getIndexFragment() {
        return indexFragment;
    }

    public void setIndexFragment(Integer indexFragment) {
        this.indexFragment.postValue(indexFragment);
    }
}
