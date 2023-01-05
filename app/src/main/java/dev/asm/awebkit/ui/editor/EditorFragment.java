package dev.asm.awebkit.ui.editor;

import android.graphics.Typeface;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import dev.asm.awebkit.databinding.FragEditorBinding;
import dev.asm.awebkit.ui.base.BaseFragment;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import java.io.InputStreamReader;
import org.eclipse.tm4e.core.internal.theme.reader.ThemeReader;
import org.eclipse.tm4e.core.theme.IRawTheme;

public class EditorFragment extends BaseFragment {
    
    public static final String TAG = "EditorFragment";
    
    private static FragEditorBinding binding;
    private EditorColorScheme editorColorScheme;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requireActivity().getOnBackPressedDispatcher().addCallback(this,mOnBackPressedCallback);
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragEditorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModels();
        
        binding.codeEditor.setTypefaceText(Typeface.createFromAsset(requireActivity().getAssets(),"fonts/JetBrainsMono-Regular.ttf"));
        binding.codeEditor.setLineSpacing(2f, 1.1f);
        setEditorLanguageAndTheme("darcula.json","html","html.tmLanguage.json");
        binding.codeEditor.setEditable(false);
        
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    
    private void setEditorLanguageAndTheme(@NonNull String themeName,@NonNull String langExtension, @NonNull String langName){
        try {
            editorColorScheme = binding.codeEditor.getColorScheme();
            if (!(editorColorScheme instanceof TextMateColorScheme)) {
                IRawTheme iRawTheme =
                	ThemeReader.readThemeSync(
                    themeName, requireActivity().getAssets().open("textmate/themes/"+themeName));
                editorColorScheme = TextMateColorScheme.create(iRawTheme);
                binding.codeEditor.setColorScheme(editorColorScheme);
            }
            Language language =
            	TextMateLanguage.create(
                langName,
                requireActivity()
                	.getAssets()
                    .open("textmate/languages/"+langExtension+"/syntaxes/"+langName),
                new InputStreamReader(
                	requireActivity()
                    	.getAssets()
                        .open("textmate/languages/"+langExtension+"/language-configuration.json")),
                ((TextMateColorScheme) editorColorScheme).getRawTheme());
            binding.codeEditor.setEditorLanguage(language);
        } catch (Exception e) {
        	Log.e(TAG,e.getMessage());
        }
    }
    
    public static CodeEditor getCodeEditor(){
        return binding.codeEditor;
    }
    
    @Override
    public void initViewModels() {
        super.initViewModels();
    }
}
