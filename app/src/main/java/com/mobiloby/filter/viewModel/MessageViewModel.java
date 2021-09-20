package com.mobiloby.filter.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MessageViewModel extends ViewModel {
    MutableLiveData<Boolean> isFriends = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsFriends() {
        return isFriends;
    }

    public void setIsFriends(Boolean isFriends) {
        this.isFriends.postValue(isFriends);
    }
}
