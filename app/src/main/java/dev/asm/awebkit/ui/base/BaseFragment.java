package dev.asm.awebkit.ui.base;

import androidx.fragment.app.Fragment;
import org.greenrobot.eventbus.EventBus;

public class BaseFragment extends Fragment {
    
    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }
    
    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }
    
    public void initViewModels(){}
    public void initEvents(){}
    
}
