package dev.asm.awebkit.ui.editor;

import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import dev.asm.awebkit.BaseApp;
import dev.asm.awebkit.databinding.FragEditorBinding;
import dev.asm.awebkit.ui.base.BaseFragment;
import dev.asm.awebkit.viewmodels.tabs.TabItemViewModel;
import io.github.rosemoe.sora.lang.EmptyLanguage;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.dsl.GrammarDefinitionDSLKt;
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel;
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver;
import io.github.rosemoe.sora.text.ContentCreator;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import org.eclipse.tm4e.core.registry.IThemeSource;

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
            
            if(!binding.codeEditor.isEditable()){
                binding.codeEditor.setEditable(true);
            }
            binding.codeEditor.setText("");
            binding.codeEditor.setText(getContentFromUri(uri));
        });
        setEditorTheme(THEME_TEXTMATE,"Dracula.tmTheme");
        
        try{
            var language = TextMateLanguage.create("source.java",true);
            binding.codeEditor.setEditorLanguage(language);
        }catch (Exception e){
            e.printStackTrace();
            binding.codeEditor.setEditorLanguage(new EmptyLanguage());
        }
        
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
    
    /*private void setupEditorThemeAndLanguage(String themeName, String extension){
        if(extension.endsWith(".css")){//d
            setEditorLanguageAndTheme(themeName,"css","css.tmLanguage.json");
        }else if(extension.endsWith(".html")){//d
            setEditorLanguageAndTheme(themeName,"html","html.tmLanguage.json");
        }if(extension.endsWith(".java")){//d
            setEditorLanguageAndTheme(themeName,"java","java.tmLanguage.json");
        }else if(extension.endsWith(".js")){//d
            setEditorLanguageAndTheme(themeName,"javascript","javascript.tmLanguage.json");
        }else if(extension.endsWith(".json")){
            setEditorLanguageAndTheme(themeName,"json","json.tmLanguage.json");
        }else if(extension.endsWith(".kt")){
            setEditorLanguageAndTheme(themeName,"kotlin","kotlin.tmLanguage");
        }else if(extension.endsWith(".less")){
            setEditorLanguageAndTheme(themeName,"less","less.tmLanguage.json");
        }else if(extension.endsWith(".lua")){
            setEditorLanguageAndTheme(themeName,"lua","lua.tmLanguage.json");
        }else if(extension.endsWith(".md")){//d
            setEditorLanguageAndTheme(themeName,"markdown","markdown.tmLanguage.json");
        }else if(extension.endsWith(".php")){
            setEditorLanguageAndTheme(themeName,"php","php.tmLanguage.json");
        }else if(extension.endsWith(".py")){//d
            setEditorLanguageAndTheme(themeName,"python","python.tmLanguage.json");
        }else if(extension.endsWith(".scss")){//d
            setEditorLanguageAndTheme(themeName,"scss","scss.tmLanguage.json");
        }else if(extension.endsWith(".ts")){//d
            setEditorLanguageAndTheme(themeName,"typescript","typescript.tmLanguage.json");
        }else if(extension.endsWith(".xml")){//d
            setEditorLanguageAndTheme(themeName,"xml","xml.tmLanguage.json");
        }else if(extension.endsWith(".yaml")){
            setEditorLanguageAndTheme(themeName,"yaml","yaml.tmLanguage.json");
        }else{
            setEditorLanguageAndTheme(null,null,null);
        }
    }*/
    
    //theme types
    public static int THEME_DEFAULT = 0;
    public static int THEME_TEXTMATE = 1;
    
    private void setEditorTheme(@IntegerRes int themeType, @Nullable String themeName){
        if(themeType == THEME_TEXTMATE && themeName!=null){
            var path = "textmate/themes/" + themeName;
            var editorColorScheme = binding.codeEditor.getColorScheme();
            if (!(editorColorScheme instanceof TextMateColorScheme)) {
                try{
                    FileProviderRegistry.getInstance().addFileProvider(new AssetsFileResolver(requireActivity().getAssets()));
                    var themeRegistry = ThemeRegistry.getInstance();
                    themeRegistry.loadTheme(new ThemeModel(IThemeSource.fromInputStream(FileProviderRegistry.getInstance().tryGetInputStream(path),path,null),themeName));
                    themeRegistry.setTheme(themeName);
                    binding.codeEditor.setColorScheme(TextMateColorScheme.create(themeRegistry));
                }catch (Exception e){
                    BaseApp.showToast("setEditorTheme=> " + e.getMessage());
                }
            }
        }
    }
    
    //language types
    public static int LANG_DEFAULT = 0;
    public static int LANG_TEXTMATE = 1;
    public static int LANG_TREESITTER = 2;
    
    public static String EXT_CSS = ".css";
    
    private void setEditorLanguage(@IntegerRes int langType, @Nullable String langName, @Nullable String scopeName){
        if(langType == LANG_TEXTMATE && langName != null){
            var path = "textmate/languages/" + langName;
            /*try{
                var language = TextMateLanguage.create(
                    scopeName,
                    GrammarRegistry.getInstance().loadGrammars(),
                    true);
                binding.codeEditor.setEditorLanguage(language);
            }catch (Exception e){
                binding.codeEditor.setEditorLanguage(new EmptyLanguage());
            }*/
        }
    }
    
}
