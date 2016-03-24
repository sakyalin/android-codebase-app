package com.linxinzhe.android.codebaseapp.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * @author linxinzhe on 3/24/16.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
