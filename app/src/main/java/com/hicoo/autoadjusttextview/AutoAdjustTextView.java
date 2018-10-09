package com.hicoo.autoadjusttextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by ZhouZi on 2018/10/8.
 * time:11:32
 * ----------Dragon be here!----------/
 * 　　　┏┓　　 ┏┓
 * 　　┏┛┻━━━┛┻┓━━━
 * 　　┃　　　　　 ┃
 * 　　┃　　　━　  ┃
 * 　　┃　┳┛　┗┳
 * 　　┃　　　　　 ┃
 * 　　┃　　　┻　  ┃
 * 　　┃　　　　   ┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛━━━━━
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━━━━━━神兽出没━━━━━━━━━━━━━━
 */
public class AutoAdjustTextView extends AppCompatTextView {

    private Paint textPaint;

    /**
     * 控件的宽度，不包括边距
     */
    private int viewWidth;

    private int textSize;
    private int maxSize;
//    private int minSize;

    public AutoAdjustTextView(Context context) {
        this(context, null);
    }

    public AutoAdjustTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoAdjustTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new Paint(getPaint());
        /**
         * textSize 默认为最大size
         */
        maxSize = (int) getTextSize();
        textSize = (int) getTextSize();
    }

    private void refitTextSize(String t) {
        if (t == null || viewWidth <= 0)
            return;

        /**
         * 最大行数
         */
        int line = getMaxLines();
        Log.e("AutoAdjustTextView", "文字行数" + line);

        /**
         * 文字的宽度
         */
        float textWidth = textPaint.measureText(t);
        Log.e("AutoAdjustTextView", "文字长度" + textWidth);

        while (textPaint.measureText(t) / line > viewWidth) {
            textSize--;
            setTextSize(textSize);
            textPaint.setTextSize(textSize);
        }
        while (textPaint.measureText(t) / line < viewWidth) {
            if (textSize >= maxSize) {
                break;
            }
            textSize++;
            setTextSize(textSize);
            textPaint.setTextSize(textSize);
        }
        Log.e("AutoAdjustTextView", "当前文字大小" + getTextSize());

        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        refitTextSize(getText().toString());
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        refitTextSize(getText().toString());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("AutoAdjustTextView", "获取控件宽度");
        viewWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();

    }
}
