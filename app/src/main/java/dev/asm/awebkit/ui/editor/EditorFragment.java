package dev.asm.awebkit.ui.editor;

import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import dev.asm.awebkit.databinding.FragEditorBinding;
import dev.asm.awebkit.ui.base.BaseFragment;
import dev.asm.awebkit.viewmodels.tabs.TabItemViewModel;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import io.github.rosemoe.sora.text.ContentCreator;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import org.eclipse.tm4e.core.internal.theme.reader.ThemeReader;
import org.eclipse.tm4e.core.theme.IRawTheme;

public class EditorFragment extends BaseFragment {
    
    public static final String TAG = "EditorFragment";
    
    private static FragEditorBinding binding;
    private EditorColorScheme editorColorScheme;
    
    private TabItemViewModel tabitemVM;
    
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
        
        tabitemVM.getRequestedUri().observe(getViewLifecycleOwner(), uri -> {
            if(uri == null){
                binding.codeEditor.setText("");
                binding.codeEditor.setEditable(false);
                return;
            }
            
            setupEditorThemeAndLanguage("Dracula.tmTheme",uri.getLastPathSegment());
            if(!binding.codeEditor.isEditable()){
                binding.codeEditor.setEditable(true);
            }
            binding.codeEditor.setText("");
            binding.codeEditor.setText(getContentFromUri(uri));
        });
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    
    private void setEditorLanguageAndTheme(@Nullable String themeName,@Nullable String langExtension, @Nullable String langName){
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
        tabitemVM = new ViewModelProvider(requireActivity()).get(TabItemViewModel.class);
    }
    
    private String getContentFromUri(Uri uri){
        var content = "";
        try{
            var inputStream = requireActivity().getContentResolver().openInputStream(uri);
            content = ContentCreator.fromStream(inputStream).toString();
        }catch(Exception e){
            content = e.getMessage();
        }
        return content;
    }
    
    private void setupEditorThemeAndLanguage(String themeName, String extension){
        if(extension.endsWith(".html")){//d
            setEditorLanguageAndTheme(themeName,"html","html.tmLanguage.json");
        }else if(extension.endsWith(".css")){//d
            setEditorLanguageAndTheme(themeName,"css","css.tmLanguage.json");
        }else if(extension.endsWith(".js")){//d
            setEditorLanguageAndTheme(themeName,"javascript","javascript.tmLanguage.json");
        }else if(extension.endsWith(".php")){
            setEditorLanguageAndTheme(themeName,"php","php.tmLanguage.json");
        }else if(extension.endsWith(".json")){
            setEditorLanguageAndTheme(themeName,"json","json.tmLanguage.json");
        }else if(extension.endsWith(".yaml")){
            setEditorLanguageAndTheme(themeName,"yaml","yaml.tmLanguage.json");
        }else if(extension.endsWith(".md")){//d
            setEditorLanguageAndTheme(themeName,"markdown","markdown.tmLanguage.json");
        }else if(extension.endsWith(".xml")){//d
            setEditorLanguageAndTheme(themeName,"xml","xml.tmLanguage.json");
        }else if(extension.endsWith(".scss")){//d
            setEditorLanguageAndTheme(themeName,"scss","scss.tmLanguage.json");
        }else{
            setEditorLanguageAndTheme(null,null,null);
        }
    }
}
