package dev.asm.awebkit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.PopupMenu;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.lazygeniouz.filecompat.file.DocumentFileCompat;
import dev.asm.awebkit.BaseApp;
import dev.asm.awebkit.databinding.ActivityMainBinding;
import dev.asm.awebkit.pojos.TabModel;
import dev.asm.awebkit.ui.base.BaseActivity;
import dev.asm.awebkit.viewmodels.bottomsheets.BottomSheetViewModel;
import dev.asm.awebkit.viewmodels.files.DocumentFilePickerViewModel;
import dev.asm.awebkit.viewmodels.files.TreeClickViewModel;
import dev.asm.awebkit.viewmodels.tabs.TabItemViewModel;
import java.util.ArrayList;

public class MainActivity extends BaseActivity
    implements NavController.OnDestinationChangedListener, TabLayout.OnTabSelectedListener {
        
	private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    
    private BottomSheetBehavior<View> mBehavior;
    
    private BottomSheetViewModel bottomsheetVM;
    private DocumentFilePickerViewModel documentfilepickerVM;
    private TabItemViewModel tabitemVM;
    private TreeClickViewModel treeclickVM;
    
    //private HashMap<String,Uri> tabMap = new HashMap<String,Uri>();
    private ArrayList<TabModel> uniqueTabItems = new ArrayList<>();
    
    ActivityResultLauncher<Uri> mStartForResult = registerForActivityResult(
    	new ActivityResultContracts.OpenDocumentTree(),uri -> {
            if (uri != null) {
                int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                BaseApp.applicationContext.getContentResolver().takePersistableUriPermission(uri,takeFlags);
                documentfilepickerVM.setTreeDataUri(uri);
                binding.drawerLayout.openDrawer(GravityCompat.START);
                binding.tabLayout.removeAllTabs();
                //tabMap.clear();
                uniqueTabItems.clear();
                treeclickVM.setClickededFile(null);
            }
        }
    );
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
    	NavigationUI.setupWithNavController(binding.navigationView, navController);
    	navController.addOnDestinationChangedListener(this);
        
        initViewModels();
        setupToolBar();
        setupBottomSheet();
        setupTabs();
    }
    
    private void setupTabs(){
        binding.tabLayout.addOnTabSelectedListener(this);
        binding.tabLayout.setVisibility(View.GONE);
        addSelectedFileAsTabs();
        uniqueTabItems.clear();
    }
    
    private void addSelectedFileAsTabs(){
        treeclickVM.getClickedFile().observe(this, uri -> {
            if(uri!=null){
                var tabPath = uri.getLastPathSegment();
                var tabName = tabPath.replaceFirst(".*/([^/?]+).*","$1");
                var documentFile = DocumentFile.fromSingleUri(this,uri);
                if(!documentFile.exists()){
                    return;
                }
                if(!isReadableMimeType(documentFile.getType()) && isOpenableExtension(MimeTypeMap.getSingleton().getExtensionFromMimeType(documentFile.getType()))){
                    //do open for specific file
                    BaseApp.showToast(documentFile.getName());
                    return;
                }else{
                    if(binding.tabLayout.getVisibility() == View.GONE){
                        binding.tabLayout.setVisibility(View.VISIBLE);
                    }
                    
                    var isExistTab = false;
                    var exitTabIndex = 0;
                    
                    //make sure do not add existing items
                    for(int i = 0 ; i < binding.tabLayout.getTabCount() ; i++){
                        if(binding.tabLayout.getTabAt(i).getText().toString().equals(tabName)){
                            isExistTab = true;
                            exitTabIndex = i;
                        }
                    }
                    if(!isExistTab){
                        uniqueTabItems.add(new TabModel(tabName,uri));
                        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabName),true);
                    }else{
                        if(tabPath.equals(uniqueTabItems.get(exitTabIndex).getUri().getLastPathSegment())){
                            //existing tab with the same filepath
                            if(binding.tabLayout.getSelectedTabPosition() != exitTabIndex){
                                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(exitTabIndex),true);
                             }
                        }else{
                            //existing tab but filepath is different
                            //so add as new one
                            uniqueTabItems.add(new TabModel(tabName,uri));
                            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabName),true);
                        }
                    }
                }
                 
                //close drawer after item click
                if(binding.drawerLayout.isOpen()){
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
            }else{
                if(binding.tabLayout.getTabCount()==0){
                    binding.tabLayout.setVisibility(View.GONE);
                    tabitemVM.setRequestedUri(null);
                }
            }
        });
    }
    
    private boolean isReadableMimeType(@NonNull String mimeType){
        return mimeType.equals("text/plain")||mimeType.equals("text/html")||mimeType.equals("text/xml")
        ||mimeType.equals("text/css")||mimeType.equals("text/x-java");
    }
    
    //https://android.googlesource.com/platform/external/mime-support/+/9817b71a54a2ee8b691c1dfa937c0f9b16b3473c/mime.types
    //https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
    //https://stuff.mit.edu/afs/sipb/project/android/docs/guide/appendix/media-formats.html
    
    private boolean isOpenableExtension(@NonNull String ext){
        return isArchiveTypes(ext) || isMediaTypes(ext);
    }
    
    private boolean isArchiveTypes(@NonNull String ext){
        return ext.equals("zip")||ext.equals("7z")||ext.equals("tar")||ext.equals("gz")||ext.equals("xz")||
        ext.equals("rar")||ext.equals("jar")||ext.equals("apk")||ext.equals("dex")||ext.equals("arsc");
    }
    
    private boolean isMediaTypes(@NonNull String ext){
        return ext.equals("3gp")||ext.equals("mp4")||ext.equals("webm")||ext.equals("mkv")//video
        ||ext.equals("jpg")||ext.equals("jpeg")||ext.equals("png")||ext.equals("gif")||
        ext.equals("webp")||ext.equals("bmp")//image
        ||ext.equals("wav")||ext.equals("imy")||ext.equals("ota")||ext.equals("rtttl")||
        ext.equals("rtx")||ext.equals("xmf")||ext.equals("mxmf")||ext.equals("mid")||
        ext.equals("mp3")||ext.equals("flac")||ext.equals("aac")||ext.equals("m4a");//audio
    }
    
    private void setupToolBar(){
        binding.materialToolbar.setNavigationOnClickListener((v)->{
            if(!binding.drawerLayout.isOpen()){
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        binding.materialToolbar.setOnMenuItemClickListener((menu)->{
            switch(menu.getItemId()){
                case R.id.action_pickfolder:
                    openTreePicker();
                    break;
            }
            return false;
        });
    }
    
    public void openTreePicker(){
        mStartForResult.launch(null);
	}
    
    public void setupBottomSheet(){
        View bottomSheet = binding.bottomEditorContainer;
        mBehavior = BottomSheetBehavior.from(bottomSheet);
        mBehavior.setGestureInsetBottomIgnored(true);
        mBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int state) {
                bottomsheetVM.setCurrentState(state);
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                
            }
        });
        mBehavior.setHalfExpandedRatio(0.3f);
        mBehavior.setFitToContents(false);
    }
  
	@Override
	public void onDestinationChanged(NavController controller, NavDestination destination, Bundle args) {
		boolean enableDrawer = false;
    	if (args != null) {
    		enableDrawer = args.getBoolean("enableDrawer", false);
    	}
    	if (enableDrawer) {
    		binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    	} else {
    		if (binding.drawerLayout.isOpen()) {
        		binding.drawerLayout.closeDrawer(GravityCompat.START);
    		}
    		binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
	}
    
    @Override
    public void initViewModels() {
        super.initViewModels();
        bottomsheetVM = new ViewModelProvider(MainActivity.this).get(BottomSheetViewModel.class);
        documentfilepickerVM = new ViewModelProvider(MainActivity.this).get(DocumentFilePickerViewModel.class);
        tabitemVM = new ViewModelProvider(MainActivity.this).get(TabItemViewModel.class);
        treeclickVM = new ViewModelProvider(MainActivity.this).get(TreeClickViewModel.class);
    }
    
    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isOpen()){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }else if(mBehavior.getState() == BottomSheetBehavior.STATE_HALF_EXPANDED || mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
        	mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else{
            BaseApp.showToast("Back");
        }
    }
    
    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        TabLayout.Tab mTab = binding.tabLayout.getTabAt(tab.getPosition());
        var position = mTab.getPosition();
        
        PopupMenu pm = new PopupMenu(this,tab.view);
        pm.inflate(R.menu.tab_popup_menu);
        pm.setGravity(GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK);
        pm.setOnMenuItemClickListener((menuItem)->{
            switch(menuItem.getItemId()){
                case R.id.menu_tab_close:
                    uniqueTabItems.remove(position);
                    binding.tabLayout.removeTab(mTab);
                    treeclickVM.setClickededFile(null);
                    if(binding.tabLayout.getTabCount() == 0){
                        tabitemVM.setRequestedUri(null);
                    }
                    return true;
                case R.id.menu_tab_closeothers:
                    for(int i = 0 ; i <= binding.tabLayout.getTabCount() ; i++ ){
                        if(i != position){
                            uniqueTabItems.remove(position);
                            TabLayout.Tab rTab = binding.tabLayout.getTabAt(i);
                            binding.tabLayout.removeTab(rTab);
                        }
                    }
                    treeclickVM.setClickededFile(null);
                    return true;
                case R.id.menu_tab_closeall:
                    uniqueTabItems.clear();
                    binding.tabLayout.removeAllTabs();
                    treeclickVM.setClickededFile(null);
                    return true;
                default:
                return false;
            }
        });
        pm.show();
    }
    
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        TabLayout.Tab mTab = binding.tabLayout.getTabAt(tab.getPosition());
        var position = mTab.getPosition();
        if(position<uniqueTabItems.size() && !uniqueTabItems.isEmpty()){
            tabitemVM.setRequestedUri(uniqueTabItems.get(position).getUri());
        }
    }
    
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        TabLayout.Tab mTab = binding.tabLayout.getTabAt(tab.getPosition());
    }
    
}
