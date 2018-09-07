package com.umengshared.lwc.myumengshared.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyToast;
import com.umengshared.lwc.myumengshared.activity.JavaCallJsActivity;
import com.umengshared.lwc.myumengshared.activity.ZTNHMainActivity;
import com.umengshared.lwc.myumengshared.activity.definedview.ChangeTextView;
import com.umengshared.lwc.myumengshared.activity.definedview.CircleView;
import com.umengshared.lwc.myumengshared.activity.definedview.MyTextViewTest;
import com.umengshared.lwc.myumengshared.activity.definedview.QQstepView;
import com.umengshared.lwc.myumengshared.base.BaseFragment;
import com.umengshared.lwc.myumengshared.http.HttpUrlConnectionTestActivity;
import com.umengshared.lwc.myumengshared.permissions.permission.Acp;
import com.umengshared.lwc.myumengshared.permissions.permission.AcpListener;
import com.umengshared.lwc.myumengshared.permissions.permission.AcpOptions;
import com.umengshared.lwc.myumengshared.service.MyIntentService;
import com.umengshared.lwc.myumengshared.service.MyService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Unbinder unbinder;
    @BindView(R.id.tv_permission)
    TextView tvPermission;
    @BindView(R.id.bt_start_service)
    Button btStartService;
    @BindView(R.id.bt_stop_service)
    Button btStopService;
    @BindView(R.id.bt_exit)
    Button btExit;
    @BindView(R.id.step_view)
    QQstepView stepView;
    @BindView(R.id.bt_start_file)
    Button btStartFile;
    @BindView(R.id.bt_stop_file)
    Button btStopFile;
    @BindView(R.id.bt_cancel_file)
    Button btCancelFile;
    Unbinder unbinder1;
    @BindView(R.id.iv_circleview)
    CircleView ivCircleview;
    @BindView(R.id.tv_changertext)
    ChangeTextView tvChangertext;
    @BindView(R.id.bt_left_to_right)
    Button btLeftToRight;
    @BindView(R.id.bt_rigth_to_left)
    Button btRigthToLeft;
    @BindView(R.id.bt_intent_service)
    Button btIntentService;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ZTNHMainActivity mZTNHMainActivity;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    /**
     * 获取绑定的acticity
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mZTNHMainActivity = (ZTNHMainActivity) context;

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
        return R.layout.fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

        MyTextViewTest myTextViewTest = new MyTextViewTest(this.getActivity());

        stepView.setStepMax(4000);
        //属性动画
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 3000);
        animator.setDuration(1000);
        //设置差值器
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (float) animation.getAnimatedValue();
                stepView.setCurrentStepMax((int) current);
            }
        });
        animator.start();
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.tv_permission,
            R.id.bt_start_service, R.id.bt_stop_service, R.id.bt_intent_service, R.id.bt_cancel_file,
            R.id.bt_start_file, R.id.bt_stop_file, R.id.bt_exit, R.id.step_view,R.id.iv_circleview, R.id.bt_left_to_right, R.id.bt_rigth_to_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_circleview://android js 调用
                startActivity(new Intent(this.getActivity(), JavaCallJsActivity.class));
                break;
            case R.id.tv_permission://动态请求权限
//                startActivity(new Intent(this.getActivity(), TestPermissionsActivity.class));
                Acp.getInstance(this.getActivity()).request(new AcpOptions.Builder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_PHONE_STATE).build(), new AcpListener() {
                    @Override
                    public void onGranted() {//成功处理
                        MyToast.showToast("请求动态权限成功！");
                        startActivity(new Intent(HomeFragment.this.getActivity(), HttpUrlConnectionTestActivity.class));

                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });
                break;
            case R.id.bt_start_service: //启动服务
                Intent intent = new Intent(this.getActivity(), MyService.class);
                this.getActivity().startService(intent);
                break;
            case R.id.bt_stop_service://停止服务
                Intent stopIntent = new Intent(this.getActivity(), MyService.class);
                this.getActivity().stopService(stopIntent);
                break;
            case R.id.bt_intent_service://intentService
                Intent intentService = new Intent(this.getActivity(), MyIntentService.class);
                this.getActivity().startService(intentService);
                break;
            case R.id.bt_start_file://开始下载
                //http://csyj-img.oss-cn-shenzhen.aliyuncs.com/support/96691e9b06245b0a8dcf768f0d21b800.mp4
                //https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe
                if (mZTNHMainActivity.mDownloadBinder != null)
                    mZTNHMainActivity.mDownloadBinder.startDownLoad("http://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe");
                break;
            case R.id.bt_stop_file://暂停
                if (mZTNHMainActivity.mDownloadBinder != null)
                    mZTNHMainActivity.mDownloadBinder.pauseDownLoad();
                break;
            case R.id.bt_cancel_file://取消下载
                if (mZTNHMainActivity.mDownloadBinder != null)
                    mZTNHMainActivity.mDownloadBinder.cancelDownLoad();
                break;
            case R.id.bt_exit:
                //发送强制下线广播
                Intent localIntent = new Intent("com.umengshared.lwc.braodcast.Local_Broadcast");
                if (mZTNHMainActivity.localBroadcast != null)
                    mZTNHMainActivity.localBroadcast.sendBroadcast(localIntent);
                break;
            case R.id.bt_left_to_right:
                onLeftToRight();
                break;
            case R.id.bt_rigth_to_left:
                onRightToLeft();
                break;
        }
    }

    private void onRightToLeft() {
        tvChangertext.setmDirection(ChangeTextView.Direction.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (float)animation.getAnimatedValue();
                tvChangertext.setmCurrentProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }

    private void onLeftToRight() {
        tvChangertext.setmDirection(ChangeTextView.Direction.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (float)animation.getAnimatedValue();
                tvChangertext.setmCurrentProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }
}
