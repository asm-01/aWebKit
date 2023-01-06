package dev.asm.awebkit.viewmodels.tabs;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TabItemViewModel extends ViewModel {
    
    private MutableLiveData<Uri> requestedUri = new MutableLiveData<>(null);
    
    public LiveData<Uri> getRequestedUri(){
        if(requestedUri == null){
            requestedUri = new MutableLiveData<>(null);
        }
        return requestedUri;
    }
    
    public void setRequestedUri(Uri data){
        requestedUri.setValue(data);
    }
    
}
