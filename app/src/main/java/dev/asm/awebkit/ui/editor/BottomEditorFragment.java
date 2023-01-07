package dev.asm.awebkit.ui.editor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import dev.asm.awebkit.BaseApp;
import dev.asm.awebkit.databinding.FragBottomEditorBinding;
import dev.asm.awebkit.ui.base.BaseFragment;
import dev.asm.awebkit.ui.editor.EditorFragment;
import dev.asm.awebkit.viewmodels.bottomsheets.BottomSheetViewModel;

public class BottomEditorFragment extends BaseFragment {
    
    public static final String TAG = "BottomEditorFragment";
    private FragBottomEditorBinding binding;
    private BottomSheetViewModel bottomsheetVM;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragBottomEditorBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModels();
        
        var inputView = binding.symbolInput;
        try{
            inputView.bindEditor(EditorFragment.getCodeEditor());
        }catch(Exception e){
            BaseApp.showToast(e.getMessage());
        }
        inputView.addSymbols(
        	new String[]{
                "->","<",">","(",")","{","}",",",".",";","\"","?","+","-","*","/"
            },
            new String[]{
                "\t","<>",">","()",")","{}","}",",",".",";","\"","?","+","-","*","/"
            }
        );
        
        bottomsheetVM.getCurrentState().observe(getViewLifecycleOwner(), state -> {
            if(state == BottomSheetBehavior.STATE_EXPANDED){
                binding.symbolInput.setVisibility(View.INVISIBLE);
            }else{
                binding.symbolInput.setVisibility(View.VISIBLE);
            }
        });
    }
    
    @Override
    public void initViewModels() {
        super.initViewModels();
        bottomsheetVM = new ViewModelProvider(requireActivity()).get(BottomSheetViewModel.class);
    }
    
}
