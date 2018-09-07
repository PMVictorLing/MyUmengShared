package com.umengshared.lwc.myumengshared.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.activity.definedview.LetterSideBar;
import com.umengshared.lwc.myumengshared.activity.definedview.LoadingCircleView;
import com.umengshared.lwc.myumengshared.activity.definedview.LoadingView;
import com.umengshared.lwc.myumengshared.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 */
public class ChangeTextViewFragment extends BaseFragment implements LetterSideBar.LetterTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.view_LoadingView)
    LoadingView viewLoadingView;
    @BindView(R.id.lb_letterbar)
    LetterSideBar lbLetterbar;
    @BindView(R.id.vw_circle_loading_view)
    LoadingCircleView vwCircleLoadingView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangeTextViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeTextViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeTextViewFragment newInstance(String param1, String param2) {
        ChangeTextViewFragment fragment = new ChangeTextViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_change_text_view;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        //设值
        Bundle bundle = getArguments();
        tvText.setText(bundle.getString(ARG_PARAM1) + "");

        lbLetterbar.setOnLetterTouchListener(this);

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }


    @OnClick({R.id.view_LoadingView,R.id.vw_circle_loading_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_LoadingView:
                //模拟回收 loadingView
                viewLoadingView.setVisibility(View.GONE);
                break;
            case R.id.vw_circle_loading_view:
                vwCircleLoadingView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void touch(String letter, boolean isUp) {
        if (isUp) {
            tvText.setVisibility(View.VISIBLE);
            tvText.setText(letter + "");
        } else {
            tvText.setVisibility(View.GONE);
            tvText.setText(letter + "");
        }
    }

}
