package dev.asm.awebkit.pojos;

import android.net.Uri;

public class TabModel {
    
    public String tabName;
    public Uri uri;
    
    public TabModel(String tabName, Uri uri) {
        this.tabName = tabName;
        this.uri = uri;
    }
    
    public String getTabName() {
        return this.tabName;
    }
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
    public Uri getUri() {
        return this.uri;
    }
    public void setUri(Uri uri) {
        this.uri = uri;
    }
}