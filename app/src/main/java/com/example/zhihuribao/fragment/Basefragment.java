package com.example.zhihuribao.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhihuribao.MainActivity;

/**
 * Created by 67698 on 2018/2/25.
 */

public abstract class Basefragment extends Fragment{
    protected Activity mActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        return initView(inflater, container, savedInstanceState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected void initData() {
    }
    protected void hint(View view, String content, int color) {
        Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(color);
        snackbar.show();
    }
    public MainActivity getRootActivity() {
        return (MainActivity) mActivity;
    }
}
