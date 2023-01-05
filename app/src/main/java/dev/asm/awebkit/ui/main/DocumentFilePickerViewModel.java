package dev.asm.awebkit.ui.main;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DocumentFilePickerViewModel extends ViewModel {
    
    private MutableLiveData<Boolean> isPicked = new MutableLiveData<>();
    private MutableLiveData<Uri> treeDataUri = new MutableLiveData<>();
    
    public LiveData<Boolean> getDocumentFilePicked() {
        if (isPicked == null) {
            isPicked = new MutableLiveData<>(false);
        }
        return isPicked;
    }

    public void setDocumentFilePicked(boolean state) {
        isPicked.setValue(state);
    }
    
    public LiveData<Uri> getTreeDataUri(){
        if(treeDataUri == null){
            treeDataUri = new MutableLiveData<>(null);
        }
        return treeDataUri;
    }
    
    public void setTreeDataUri(Uri data){
        treeDataUri.setValue(data);
    }
}
