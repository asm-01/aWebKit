package dev.asm.awebkit.utils.files;

import androidx.annotation.NonNull;

public class DocumentFileValidator {
    
    public static boolean isEditableType(@NonNull String mimeType){
        return mimeType.equals("text/plain")||mimeType.equals("text/html")||mimeType.equals("text/xml")
        ||mimeType.equals("text/css")||mimeType.equals("text/x-java")||mimeType.equals("application/octet-stream")
        ||mimeType.equals("image/svg+xml");
    }
    
    public static boolean isOpenableType(@NonNull String mimeType){
        return mimeType.contains("audio")||mimeType.contains("video")||imageButNotSvg(mimeType)
        ||mimeType.contains("zip")||mimeType.contains("rar")
        ||mimeType.contains("epub")||mimeType.contains("pdf")
        ||mimeType.contains("vnd.android.package-archive");
    }
    
    public static boolean imageButNotSvg(@NonNull String mimeType){
        return mimeType.contains("image") && !mimeType.equals("image/svg+xml");
    }
    
    public static boolean isRestrictedExtension(@NonNull String ext){
        return ext.equals("7z")||ext.equals("tar")||ext.equals("gz")||ext.equals("xz")||
        ext.equals("aar")||ext.equals("jar")||ext.equals("apk")||ext.equals("dex")||ext.equals("arsc")||ext.equals("swb");
    }
    
    /*
    //https://android.googlesource.com/platform/external/mime-support/+/9817b71a54a2ee8b691c1dfa937c0f9b16b3473c/mime.types
    //https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
    //https://stuff.mit.edu/afs/sipb/project/android/docs/guide/appendix/media-formats.html
    
    private boolean isArchiveTypes(@NonNull String ext){
        return ext.equals("zip")||ext.equals("7z")||ext.equals("tar")||ext.equals("gz")||ext.equals("xz")||
        ext.equals("rar")||ext.equals("aar")||ext.equals("jar")||ext.equals("apk")||ext.equals("dex")||ext.equals("arsc")||ext.equals("swb");
    }
    
    private boolean isDocumentTypes(@NonNull String ext){
        return ext.equals("pdf");
    }
    
    private boolean isOthersTypes(@NonNull String ext){
        return ext.equals("torrent");
    }
    private boolean isMediaTypes(@NonNull String ext){
        return ext.equals("3gp")||ext.equals("mp4")||ext.equals("webm")||ext.equals("mkv")//video
        ||ext.equals("jpg")||ext.equals("jpeg")||ext.equals("png")||ext.equals("gif")||
        ext.equals("webp")||ext.equals("bmp")//image
        ||ext.equals("wav")||ext.equals("imy")||ext.equals("ota")||ext.equals("rtttl")||
        ext.equals("rtx")||ext.equals("xmf")||ext.equals("mxmf")||ext.equals("mid")||
        ext.equals("mp3")||ext.equals("flac")||ext.equals("aac")||ext.equals("m4a");//audio
    }
    */
}
