package dev.asm.awebkit.ui.editor;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import dev.asm.awebkit.BaseApp;
import dev.asm.awebkit.databinding.FragEditorBinding;
import dev.asm.awebkit.ui.base.BaseFragment;
import dev.asm.awebkit.utils.files.ExtensionsValidator;
import dev.asm.awebkit.viewmodels.tabs.TabItemViewModel;
import io.github.rosemoe.sora.lang.EmptyLanguage;
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel;
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver;
import io.github.rosemoe.sora.text.ContentCreator;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
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
        loadDefaultThemes();
        loadDefaultLanguages();
        ensureTextmateTheme();
        
        tabitemVM.getRequestedUri().observe(getViewLifecycleOwner(), uri -> {
            if(uri == null){
                binding.codeEditor.setText("");
                binding.codeEditor.setEditable(false);
                return;
            }
            
            if(!binding.codeEditor.isEditable()){
                binding.codeEditor.setEditable(true);
            }
            setCurrentLanguage(uri);
            binding.codeEditor.setText("");
            binding.codeEditor.setText(getContentFromUri(uri));
        });
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
    
    private void setCurrentLanguage(Uri uri){
        var path = uri.getLastPathSegment();
        if(ExtensionsValidator.isCss(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.css",true));
        }else if(ExtensionsValidator.isHtml(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("text.html.basic",true));
        }else if(ExtensionsValidator.isJava(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.java",false));
        }else if(ExtensionsValidator.isJavaScript(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.js",true));
        }else if(ExtensionsValidator.isJavaScriptReact(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.js.jsx",true));
        }else if(ExtensionsValidator.isJson(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.json",false));
        }else if(ExtensionsValidator.isJsonC(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.json.comments",false));
        }else if(ExtensionsValidator.isKotlin(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.kotlin",false));
        }else if(ExtensionsValidator.isLess(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.css.less",false));
        }else if(ExtensionsValidator.isLua(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.lua",false));
        }else if(ExtensionsValidator.isMarkDown(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("text.html.markdown",false));
        }else if(ExtensionsValidator.isPhp(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.php",true));
        }else if(ExtensionsValidator.isPython(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.python",false));
        }else if(ExtensionsValidator.isScss(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.css.scss",false));
        }else if(ExtensionsValidator.isTypeScript(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.ts",false));
        }else if(ExtensionsValidator.isTypeScriptReact(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.tsx",false));
        }else if(ExtensionsValidator.isXml(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("text.xml",false));
        }else if(ExtensionsValidator.isXsl(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("text.xml.xsl",false));
        }else if(ExtensionsValidator.isYaml(path)){
            binding.codeEditor.setEditorLanguage(TextMateLanguage.create("source.yaml",false));
        }else{
            binding.codeEditor.setEditorLanguage(new EmptyLanguage());
        }
    }
    
    private void loadDefaultThemes(){
        FileProviderRegistry.getInstance().addFileProvider(new AssetsFileResolver(requireActivity().getAssets()));
        
        String[] themes = {"Dracula.tmTheme","QuietLight.tmTheme"};
        var themeRegistry = ThemeRegistry.getInstance();
        
        for(String name : themes){
            var path = "textmate/themes/" + name;
            try{
                themeRegistry.loadTheme(new ThemeModel(IThemeSource.fromInputStream(FileProviderRegistry.getInstance().tryGetInputStream(path),path,null),name));
            }catch(Exception e){
                Log.e(TAG,e.getMessage());
            }
        }
        themeRegistry.setTheme("Dracula.tmTheme");
    }
    
    private void loadDefaultLanguages(){
        try{
            GrammarRegistry.getInstance().loadGrammars("textmate/languages.json");
        }catch(Exception e){
            BaseApp.showToast(e.getMessage());
        }
    }
    
    private void ensureTextmateTheme(){
        var editor = binding.codeEditor;
        var editorColorScheme = editor.getColorScheme();
        if(!(editorColorScheme instanceof TextMateColorScheme)){
            try{
                editor.setColorScheme(TextMateColorScheme.create(ThemeRegistry.getInstance()));
            }catch(Exception e){
                Log.e(TAG,e.getMessage());
            }
        }
    }
}
