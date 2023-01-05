package dev.asm.awebkit.widgets;

import android.content.Context;
import android.util.AttributeSet;
import io.github.rosemoe.sora.widget.CodeEditor;

public class AWKEditor extends CodeEditor {
    
    public static final String TAG = "AWKEditor";
    
    public AWKEditor(Context context) {
		this(context, null);
	}

	public AWKEditor(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AWKEditor(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public AWKEditor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
    }
}
