package com.linxinzhe.android.codebaseapp.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.linxinzhe.android.codebaseapp.R;


/**
 * @author linxinzhe on 4/3/16.
 */
public class CustomDialog extends Dialog {

    private CustomDialog(Context context) {
        super(context);
    }

    /**
     * 生成二选一Dialog
     *
     * @return dialog
     */
    public static Dialog selectWayDialog(Context context, String way1TextId, String way2TextId, final ButtonClick way1Click, final ButtonClick way2Click) {
        final CustomDialog dialog = new CustomDialog(context);
        dialog.setTitle("Select way:");
        dialog.setContentView(R.layout.dialog_select_way);

        Button way1 = (Button) dialog.findViewById(R.id.tv_way1_desc);
        way1.setText(way1TextId);

        Button way2 = (Button) dialog.findViewById(R.id.tv_way2_desc);
        way2.setText(way2TextId);

        way1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                way1Click.act();
                dialog.dismiss();
            }
        });
        way2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                way2Click.act();
                dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 按钮回调接口
     */
    public interface ButtonClick {
        void act();
    }
}
