package dev.asm.awebkit.ui.editor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class BottomSheetViewModel extends ViewModel{
    
    private MutableLiveData<Integer> currentState = new MutableLiveData<>(BottomSheetBehavior.STATE_COLLAPSED);
    private MutableLiveData<Boolean> isEditorAlive = new MutableLiveData<>(false);
    
    public LiveData<Integer> getCurrentState() {
        if (currentState == null) {
            currentState = new MutableLiveData<>(0);
        }
        return currentState;
    }

    public void setCurrentState(@BottomSheetBehavior.State int state) {
        currentState.setValue(state);
    }
    
    public LiveData<Boolean> isEditorAlive() {
        if (isEditorAlive == null) {
            isEditorAlive = new MutableLiveData<>(false);
        }
        return isEditorAlive;
    }

    public void setEditorAlive(boolean state) {
        isEditorAlive.setValue(state);
    }
    
}
