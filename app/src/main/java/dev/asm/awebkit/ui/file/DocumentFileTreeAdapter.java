package dev.asm.awebkit.ui.file;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.CoordinatorPopupMenu;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVGParseException;
import com.lazygeniouz.filecompat.file.DocumentFileCompat;
import dev.asm.awebkit.R;
import dev.asm.awebkit.ui.file.TreeClickViewModel;
import io.github.ikws4.treeview.TreeItem;
import io.github.ikws4.treeview.TreeView;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DocumentFileTreeAdapter extends TreeView.Adapter<DocumentFileTreeAdapter.ViewHolder, DocumentFileCompat>
implements TreeView.Adapter.OnTreeItemClickListener<DocumentFileCompat> {
    
    private Activity app;
    private TreeClickViewModel treeclickVM;
    
    public DocumentFileTreeAdapter(Activity activity,DocumentFileCompat rootFile) {
        this.app = activity;
        setTreeItemClickListener(this);
        TreeItem<DocumentFileCompat> root = new TreeItem<>(rootFile, true);
        expand(root);
        setRoot(root);
        treeclickVM = new ViewModelProvider((ViewModelStoreOwner)activity).get(TreeClickViewModel.class);
    }
  
  @Override
  public int getLayoutRes() {
      return R.layout.item_tree;
  }
  
  @Override
  public ViewHolder onCreateViewHolder(View view) {
      return new ViewHolder(view);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      super.onBindViewHolder(holder, position);
      
      TreeItem<DocumentFileCompat> item = items.get(position);
        if (item.isExpandable()) {
            try{
                holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.common_folder)));
            }catch (IOException e){
            
            }catch(SVGParseException e){
            	
            }
        } else {
        	try{
                if(item.getValue().getName().endsWith(".as")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_actionscript)));
                }else if(item.getValue().getName().equals("artisan")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_blade)));
                }else if(item.getValue().getName().endsWith(".css")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_css)));
                }else if(item.getValue().getName().endsWith(".css.map")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_cssmap)));
                }else if(item.getValue().getName().equals("favicon.ico")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_favicon)));
                }else if(item.getValue().getName().endsWith(".ttf") || 
                		item.getValue().getName().endsWith(".woff") || 
                        item.getValue().getName().endsWith(".woff2") || 
                        item.getValue().getName().endsWith(".eot")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_font)));
                }else if(item.getValue().getName().endsWith(".gitignore")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_git)));
                }else if(item.getValue().getName().endsWith(".html")){
                    holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_html)));
                }else if(item.getValue().getName().endsWith(".js")){
                	if(item.getValue().getName().endsWith(".config.js")){
                        holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_javascriptconfig)));
                    }else{
                    	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_javascript)));
                    }
                }else if(item.getValue().getName().endsWith(".js.map")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_javascriptmap)));
                }else if(item.getValue().getName().endsWith(".json")){
                	if(item.getValue().getName().equals("package-lock.json") || item.getValue().getName().equals("package.json")){
                        holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_npm)));
                    }else{
                    	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_json)));
                    }
                }else if(item.getValue().getName().endsWith(".less")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_less)));
                }else if(item.getValue().getName().equals("LICENSE")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_license)));
                }else if(item.getValue().getName().endsWith(".md")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_markdown)));
                }else if(item.getValue().getName().endsWith(".php")){
                	if(item.getValue().getName().endsWith(".blade.php")){
                        holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_blade)));
                    } else {
                    	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_php)));
                    }
                }else if(item.getValue().getName().endsWith(".scss")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_scss)));
                }else if(item.getValue().getName().endsWith(".svg")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_svg)));
                }else if(item.getValue().getName().endsWith(".txt")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_text)));
                }else if(item.getValue().getName().contains(".env")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_tune)));
                }else if(item.getValue().getName().endsWith(".ts")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_typescript)));
                }else if(item.getValue().getName().endsWith(".xml")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_xml)));
                }else if(item.getValue().getName().endsWith(".yml")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.ext_yaml)));
                }else if(item.getValue().getName().endsWith(".png") || 
                		item.getValue().getName().endsWith(".jpg") || 
                        item.getValue().getName().endsWith(".mp4") || 
                        item.getValue().getName().endsWith(".mp3")){
                	// TODO : Add more conditions for other media extension
                    holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.common_file_media)));
                }else if(item.getValue().getName().endsWith(".pdf")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.common_file_pdf)));
                }else if(item.getValue().getName().endsWith(".zip")){
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.common_file_zip)));
                }else{
                	holder.svgIcon.setSVG(SVG.getFromAsset(app.getAssets(),app.getResources().getString(R.string.common_file)));
                }
            }catch (IOException e){
            
            }catch(SVGParseException e){
            	
            }
        }

        holder.name.setText(item.getValue().getName());
      holder.itemView.setOnLongClickListener(v->{
          if(item.isExpandable()){
              showFolderPopup(v,item.getValue().getUri());
              //Toast.makeText(app,"LongClicked Folder : " + item.getValue().getName(),Toast.LENGTH_SHORT).show();
          }else{
          	showFilePopup(v,item.getValue().getUri());
          	//Toast.makeText(app,"LongClicked File : " + item.getValue().getName(),Toast.LENGTH_SHORT).show();
          }
          return true;
      });
  }
  
  @Override
  public void onClick(TreeItem<DocumentFileCompat> item) {
      if(item.isExpandable()){
          if (!item.isExpanded()) {
              expand(item);
          }/*else{
          	//collapse
          }*/
      } else {
      	treeclickVM.setClickededFile(item.getValue().getUri());
      }
  }
  
  static class ViewHolder extends TreeView.ViewHolder {
      SVGImageView svgIcon;
      TextView name;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      svgIcon = itemView.findViewById(R.id.svgIcon);
      name = itemView.findViewById(R.id.name);
    }
  }
  
  private void expand(TreeItem<DocumentFileCompat> root){
      if (!root.getChildren().isEmpty()) return;
      
      for (DocumentFileCompat child : getChildren(root)) {
            TreeItem<DocumentFileCompat> item = new TreeItem<>(child, !child.isFile());
            root.getChildren().add(item);
        }
        Collections.sort(root.getChildren(), (a, b) -> {
            /*if (a.isExpandable() && b.isExpandable()) {
                return a.getValue().getName().compareTo(b.getValue().getName());
            } else if (b.isExpandable()) {
                return 1;
            } else {
                return -1;
            }*/
            int res = Boolean.compare(b.isExpandable(), a.isExpandable());
            if (res == 0) return a.getValue().getName().compareToIgnoreCase(b.getValue().getName());
            return res;
        });
  }
  
  private List<DocumentFileCompat> getChildren(TreeItem<DocumentFileCompat> file) {
  	return file.getValue().listFiles();
  }
  
  private void showFilePopup(View view,Uri uri){
      CoordinatorPopupMenu pm = new CoordinatorPopupMenu(view.getContext(),view);
      pm.inflate(R.menu.tree_file_menu);
      pm.setGravity(GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK);
      pm.setOnMenuItemClickListener((menuItem)->{
          switch(menuItem.getItemId()){
              case R.id.menu_tfile_copy:
              	Toast.makeText(view.getContext(),"Copy Path Click",Toast.LENGTH_SHORT).show();
                  return true;
              default:
              	return false;
          }
      });
      
      MenuPopupHelper mph = new MenuPopupHelper(view.getContext(),(MenuBuilder)pm.getMenu(),view);
      mph.setForceShowIcon(true);
      mph.show();
  }
  
  private void showFolderPopup(View view,Uri uri){
      CoordinatorPopupMenu pm = new CoordinatorPopupMenu(view.getContext(),view);
      pm.inflate(R.menu.tree_folder_menu);
      pm.setGravity(GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK);
      pm.setOnMenuItemClickListener((menuItem)->{
          switch(menuItem.getItemId()){
              default:
              	return false;
          }
      });
      MenuPopupHelper mph = new MenuPopupHelper(view.getContext(),(MenuBuilder)pm.getMenu(),view);
      mph.setForceShowIcon(true);
      mph.show();
  }
  
}
