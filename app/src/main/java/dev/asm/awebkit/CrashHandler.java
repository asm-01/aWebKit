package dev.asm.awebkit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import dev.asm.awebkit.ui.base.BaseActivity;

public class CrashHandler extends BaseActivity {

    public static final String TAG = "CrashHandler";

    String[] exceptionType = {
        "",
        "",
        "",
        "",
        ""
    };
    String[] errMessage= new String[]{"","","","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String errMsg = "";
        String madeErrMsg = "";
        if(intent != null){
            errMsg = intent.getStringExtra("error");
            String[] spilt = errMsg.split("\n");
            //errMsg = spilt[0];
            try {
                for (int j = 0; j < exceptionType.length; j++) {
                    if (spilt[0].contains(exceptionType[j])) {
                        madeErrMsg = errMessage[j];
                        int addIndex = spilt[0].indexOf(exceptionType[j]) + exceptionType[j].length();
                        madeErrMsg += spilt[0].substring(addIndex, spilt[0].length());
                        break;
                    }
                }
                if(madeErrMsg.isEmpty()) madeErrMsg = errMsg;
            }catch(Exception e){}
        }

		new MaterialAlertDialogBuilder(this)
			.setTitle("Unexpected error occured!")
			.setMessage(madeErrMsg)
			.setPositiveButton("Report", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			})
			.setNegativeButton("Exit", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					finishAffinity();
				}
			})
			.setCancelable(false)
			.show();
    }

}
