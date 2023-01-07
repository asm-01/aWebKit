package dev.asm.awebkit.viewmodels.files;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TreeClickViewModel extends ViewModel {
    
    private MutableLiveData<Uri> clickedFile = new MutableLiveData<>();
    
    public LiveData<Uri> getClickedFile() {
        if (clickedFile == null) {
            clickedFile = new MutableLiveData<>(null);
        }
        return clickedFile;
    }

    public void setClickededFile(@NonNull Uri uri) {
        clickedFile.setValue(uri);
    }
}
