package com.linxinzhe.android.codebaseapp.view;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author linxinzhe on 2015/10/13.
 */
public class TextViewCountTimer extends CountDownTimer {

    private TextView mTextView;
    private String mEndText;
    private String mCountDownText;

    /**
     * @param countDownMillisecond 倒计时总时间（如60S，120s等）
     * @param countDownInterval    渐变时间（每次倒计1s）
     * @param textView             点击的按钮(为了通用我的参数设置为TextView）
     * @param endText              倒计时结束后，按钮对应显示的文字
     */
    public TextViewCountTimer(TextView textView, long countDownMillisecond, long countDownInterval, String countDownText, String endText) {
        super(countDownMillisecond, countDownInterval);
        this.mTextView = textView;
        this.mCountDownText = countDownText;
        this.mEndText = endText;
    }

    /**
     * @param countDownMillisecond 倒计时总时间（如60S，120s等）
     * @param textView             点击的按钮(为了通用我的参数设置为TextView）
     * @param endText              倒计时结束后，按钮对应显示的文字
     */
    public TextViewCountTimer(TextView textView, long countDownMillisecond, String countDownText, String endText) {
        super(countDownMillisecond, 1000);
        this.mTextView = textView;
        this.mCountDownText = countDownText;
        this.mEndText = endText;
    }

    /**
     * 计时完毕时触发
     */
    @Override
    public void onFinish() {
        mTextView.setText(mEndText);
        mTextView.setEnabled(true);
    }

    /**
     * 计时过程显示
     *
     * @param millisUntilFinished 倒计时毫秒
     */
    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setText(mCountDownText + "(" + millisUntilFinished / 1000 + ")");
        mTextView.setEnabled(false);
    }
}
