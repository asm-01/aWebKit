package dev.asm.awebkit;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.google.android.material.color.DynamicColors;
import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseApp extends Application {
    
    public static final String TAG = "App";
    
    @SuppressLint("StaticFieldLeak")
    public static volatile Context applicationContext;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public BaseApp(){
        super();
    }

    @Override
    public void onCreate() {
        try {
            applicationContext = getApplicationContext();
        } catch (Throwable ignore) {

        }

        this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    Intent intent = new Intent(getApplicationContext(), CrashHandler.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("error", getStackTrace(ex));
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 11111, intent, PendingIntent.FLAG_ONE_SHOT);
                    AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(2);
                    uncaughtExceptionHandler.uncaughtException(thread, ex);
                }
            });
        super.onCreate();

        if (applicationContext == null) {
            applicationContext = getApplicationContext();
        }
        DynamicColors.applyToActivitiesIfAvailable(this);
    
    }

    private String getStackTrace(Throwable th){
        Exception e = new Exception(th);
        StringWriter result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        while(th != null){
            th.printStackTrace(printWriter);
            th = th.getCause();
        }
        String r = result.toString();
        return r;
    }
    
    public static void showToast(String msg){
        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show();
    }
}
