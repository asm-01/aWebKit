package dev.asm.awebkit.utils.assets;

import android.content.Context;
import dev.asm.awebkit.R;

public class IconMaping {
    
    
    private static String common_ext_Dir = "svg/common/ext/";
    private static String dark_common_Dir = "svg/dark/common/";
    private static String dark_ext_Dir = "svg/dark/ext/";
    private static String light_common_Dir = "svg/light/common/";
    private static String light_ext_Dir = "svg/light/ext/";
    
    private static String file_Name = "file.svg";
    private static String file_media_Name = "file-media.svg";
    private static String file_pdf_Name = "file-pdf.svg";
    private static String file_zip_Name = "file-zip.svg";
    private static String folder_Name = "folder.svg";
    
    public static interface getDarkCommon{
        String file = dark_common_Dir + file_Name ;
        String media = dark_common_Dir + file_media_Name ;
        String pdf = dark_common_Dir + file_pdf_Name ;
        String zip = dark_common_Dir + file_zip_Name ;
        String folder = dark_common_Dir + folder_Name ;
    }
    
    public static interface getLightCommon{
        String file = light_common_Dir + file_Name ;
        String media = light_common_Dir + file_media_Name ;
        String pdf = light_common_Dir + file_pdf_Name ;
        String zip = light_common_Dir + file_zip_Name ;
        String folder = light_common_Dir + folder_Name ;
    }
    
    private static String font_Name = "file_type_font.svg";
    private static String js_Name = "file_type_js.svg";
    private static String jsconfig_Name = "file_type_jsconfig.svg";
    private static String jsmap_Name = "file_type_jsmap.svg";
    private static String json_Name = "file_type_json.svg";
    private static String yaml_Name = "file_type_yaml.svg";
    
    public static interface getDarkExt{
        String font = dark_ext_Dir + font_Name ;
        String js = dark_ext_Dir + js_Name ;
        String jsconfig = dark_ext_Dir + jsconfig_Name ;
        String jsmap = dark_ext_Dir + jsmap_Name ;
        String json = dark_ext_Dir + json_Name ;
        String yaml = dark_ext_Dir + yaml_Name ;
    }
    
    public static interface getLightExt{
        String font = light_ext_Dir + font_Name ;
        String js = light_ext_Dir + js_Name ;
        String jsconfig = light_ext_Dir + jsconfig_Name ;
        String jsmap = light_ext_Dir + jsmap_Name ;
        String json = light_ext_Dir + json_Name ;
        String yaml = light_ext_Dir + yaml_Name ;
    }
    
    private static String cssName = "css";
    private static String fontName = "font";
    private static String gitName = "git";
    private static String htmlName = "html";
    private static String javascriptName = "js";
    private static String jsonName = "json";
    private static String laravelName = "laravel";
    private static String lessName = "less";
    private static String licenseName = "license";
    private static String markdownName = "markdown";
    private static String npmName = "npm";
    private static String pdfName = "pdf";
    private static String phpName = "php";
    private static String sassName = "sass";
    private static String scssName = "scss";
    private static String tuneName = "tune";
    private static String typescriptName = "typescript";
    private static String xmlName = "xml";
    private static String yamlName = "yaml";
    
    /*public static interface getLangIcons{
        String css = svgDir + langDir + cssName + iconsExt;
        String font = svgDir + langDir + fontName + iconsExt;
        String git = svgDir + langDir + gitName + iconsExt;
        String html = svgDir + langDir + htmlName + iconsExt;
        String javascript = svgDir + langDir + javascriptName + iconsExt;
        String json = svgDir + langDir + jsonName + iconsExt;
        String laravel = svgDir + langDir + laravelName + iconsExt;
        String less = svgDir + langDir + lessName + iconsExt;
        String license = svgDir + langDir + licenseName + iconsExt;
        String markdown = svgDir + langDir + markdownName + iconsExt;
        String npm = svgDir + langDir + npmName + iconsExt;
        String pdf = svgDir + langDir + pdfName + iconsExt;
        String php = svgDir + langDir + phpName + iconsExt;
        String sass = svgDir + langDir + sassName + iconsExt;
        String scss = svgDir + langDir + scssName + iconsExt;
        String tune = svgDir + langDir + tuneName + iconsExt;
        String typescript = svgDir + langDir + typescriptName + iconsExt;
        String xml = svgDir + langDir + xmlName + iconsExt;
        String yaml = svgDir + langDir + yamlName + iconsExt;
    }*/
}
