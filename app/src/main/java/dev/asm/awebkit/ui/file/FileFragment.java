package dev.asm.awebkit.ui.file;

import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import com.lazygeniouz.filecompat.file.DocumentFileCompat;
import dev.asm.awebkit.databinding.FragFileBinding;
import dev.asm.awebkit.ui.main.DocumentFilePickerViewModel;

public class FileFragment extends Fragment {
    
    public static final String TAG = "FileFragment";

	private FragFileBinding binding;
    private DocumentFilePickerViewModel dfpVM;
    private DocumentFileTreeAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = FragFileBinding.inflate(inflater,container,false);
		return binding.getRoot();
	}
    
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.treeView.setHasFixedSize(false);
        binding.treeView.setNestedScrollingEnabled(false);
        observeFromDocumentPicker();
    }
    
    private void observeFromDocumentPicker(){
        dfpVM = new ViewModelProvider(requireActivity()).get(DocumentFilePickerViewModel.class);
        dfpVM.getTreeDataUri()
        	.observe(getViewLifecycleOwner(),uri->{
                if(uri!=null){
                    DocumentFileCompat documentFile = DocumentFileCompat.Companion.fromTreeUri(requireActivity(), uri);
                    adapter = new DocumentFileTreeAdapter(requireActivity(), documentFile);
                	binding.treeView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
    }
}
