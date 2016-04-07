package com.dos.md;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*DOS      */
public abstract class F_Base extends Fragment {
    protected View rootView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//可见否的操作
        super.setUserVisibleHint(isVisibleToUser);
    }

/*其子类实现
    public static F_Base newF(String param1, String param2) {
        F_Base fragment = new F_Else();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //数据初始化

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) rootView = inflater.inflate(getLayoutId(), container, false);
        rootView.setFocusable(true);
        rootView.setFocusableInTouchMode(true);
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //不一定是要触发返回栈，可以做一些其他的事情，我只是举个栗子。
                    getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });
        return rootView;
    }


    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);


    @Override
    public void onAttach(Context context) {//实现与A交互的接口
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        //定义与A交互的接口
        void onFragmentInteraction(Uri uri);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    protected void toAty(Class<? extends Activity> clazz) {
        startActivity(new Intent(getActivity(), clazz));
    }

}
