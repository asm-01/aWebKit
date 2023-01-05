package dev.asm.awebkit.ui.base;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import org.greenrobot.eventbus.EventBus;
import dev.asm.awebkit.R;

public class BaseActivity extends AppCompatActivity {
    
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Tachiyomi_GreenApple);
    }
        
    
    public void initViewModels(){}
    public void initEvents(){}
    
}
