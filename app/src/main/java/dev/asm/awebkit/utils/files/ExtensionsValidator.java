package dev.asm.awebkit.utils.files;

public class ExtensionsValidator {
    
    //*.css
    public static boolean isCss(String path){
        return path.endsWith(".css");
    }
    
    //*.html, *.htm, *.shtml, *.xhtml, *.xht, *.mdoc, *.jsp, *.asp, *.aspx, *.jshtm, *.volt, *.ejs, *.rhtml
    //*.mhtml => downloaded webpage from chrome
    public static boolean isHtml(String path){
        return path.endsWith(".html")||path.endsWith(".htm")||path.endsWith(".shtml")
        ||path.endsWith(".xhtml")||path.endsWith(".xht")||path.endsWith(".mdoc")
        ||path.endsWith(".jsp")||path.endsWith(".asp")||path.endsWith(".aspx")
        ||path.endsWith(".jshtm")||path.endsWith(".volt")||path.endsWith(".ejs")
        ||path.endsWith(".rhtml")||path.endsWith(".mhtml");
    }
    
    //*.java, *.jav
    public static boolean isJava(String path){
        return path.endsWith(".java")||path.endsWith(".jav");
    }
    
    
    //*.js, *.es6, *.mjs, *.cjs, *.pac, jakefile
    public static boolean isJavaScript(String path){
        return path.endsWith(".js")||path.endsWith(".es6")||path.endsWith(".mjs")
        ||path.endsWith(".cjs")||path.endsWith(".pac");
    }
    
    //*.jsx
    public static boolean isJavaScriptReact(String path){
        return path.endsWith(".jsx");
    }
    
    //*.json, *.bowerrc, *.jscsrc, *.webmanifest,->> *.js.map, *.css.map,*.ts.map,<<- *.har, *.jslintrc, *.jsonld, *.geojson, *.ipynb, composer.lock, .watchmanconfig
    public static boolean isJson(String path){
        return path.endsWith(".json")||path.endsWith(".bowerrc")||path.endsWith(".jscsrc")
        ||path.endsWith(".webmanifest")||path.endsWith(".har")||path.endsWith(".jslintrc")
        ||path.endsWith(".jsonld")||path.endsWith(".geojson")||path.endsWith(".ipynb");
    }
    
    //*.jsonc, *.eslintrc, *.eslintrc.json, *.jsfmtrc, *.jshintrc, *.swcrc, *.hintrc, *.babelrc, babel.config.json, .babelrc.json, .ember-cli
    public static boolean isJsonC(String path){
        return path.endsWith(".jsonc")||path.endsWith(".eslintrc")||path.endsWith(".eslintrc.json")
        ||path.endsWith(".jsfmtrc")||path.endsWith(".jshintrc")||path.endsWith(".swcrc")
        ||path.endsWith(".hintrc")||path.endsWith(".babelrc");
    }
    
    //*.kt
    public static boolean isKotlin(String path){
        return path.endsWith(".");
    }
    
    //*.less
    public static boolean isLess(String path){
        return path.endsWith(".");
    }
    
    //*.lua
    public static boolean isLua(String path){
        return path.endsWith(".");
    }
    
    //*.md
    public static boolean isMarkDown(String path){
        return path.endsWith(".");
    }
    
    //*.php
    public static boolean isPhp(String path){
        return path.endsWith(".php");
    }
    
    //*.py, *.rpy, *.pyw, *.cpy, *.gyp, *.gypi, *.pyi, *.ipy, *.pyt, Snakefile, SConstruct, SConscript
    public static boolean isPython(String path){
        return path.endsWith(".py")||path.endsWith(".rpy")||path.endsWith(".pyw")
        ||path.endsWith(".cpy")||path.endsWith(".gyp")||path.endsWith(".gypi")
        ||path.endsWith(".pyi")||path.endsWith(".ipy")||path.endsWith(".pyt");
    }
    
    //*.scss
    public static boolean isScss(String path){
        return path.endsWith(".scss");
    }
    
    //*.sql, *.dsql
    public static boolean isSql(String path){
        return path.endsWith(".sql")||path.endsWith(".dsql");
    }
    
    
    //*.ts, *.cts, *.mts
    public static boolean isTypeScript(String path){
        return path.endsWith(".ts")||path.endsWith(".cts")||path.endsWith(".mts");
    }
    
    //*.tsx
    public static boolean isTypeScriptReact(String path){
        return path.endsWith(".tsx");
    }
    
    //*.xml
    public static boolean isXml(String path){
        return path.endsWith(".xml");
    }
    
    //*.xsl
    public static boolean isXsl(String path){
        return path.endsWith(".xsl");
    }
    //*.yml, *.yaml
    public static boolean isYaml(String path){
        return path.endsWith(".yml")||path.endsWith(".yaml");
    }
}
