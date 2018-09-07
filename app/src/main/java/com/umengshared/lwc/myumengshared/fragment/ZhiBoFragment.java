package com.umengshared.lwc.myumengshared.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyToast;
import com.umengshared.lwc.myumengshared.activity.ZhiBoDetailsActivity;
import com.umengshared.lwc.myumengshared.base.BaseFragment;
import com.umengshared.lwc.myumengshared.http.HttpUrlConnectionTestActivity;
import com.umengshared.lwc.myumengshared.permissions.permission.Acp;
import com.umengshared.lwc.myumengshared.permissions.permission.AcpListener;
import com.umengshared.lwc.myumengshared.permissions.permission.AcpOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Description: ZhiBoFragment 直播主页
 * @Author: lingwancai
 * @Time: 2018/8/3 14:41
 */

public class ZhiBoFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Unbinder unbinder;
    @BindView(R.id.bt_zhibo)
    Button btZhibo;
    @BindView(R.id.listView)
    ListView listView;

    String textNull ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> contactsList = new ArrayList<>();


    public ZhiBoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZhiBoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZhiBoFragment newInstance(String param1, String param2) {
        ZhiBoFragment fragment = new ZhiBoFragment();
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
        return R.layout.fragment_zhi_bo;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        contactsList.clear();
        //读取联系人
        arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,contactsList);
        listView.setAdapter(arrayAdapter);

        //动态请求联系人权限
        Acp.getInstance(this.getActivity()).request(new AcpOptions.Builder().setPermissions(Manifest.permission.READ_CONTACTS).build(), new AcpListener() {
            @Override
            public void onGranted() {//成功处理
                MyToast.showToast("请求动态权限成功！");
                readContacts();

            }

            @Override
            public void onDenied(List<String> permissions) {

            }
        });

    }

    /**
     * 读取联系人
     */
    private void readContacts() {
        Cursor cursor = this.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor!=null) {
            while (cursor.moveToNext()){
                String stringName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String stringNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactsList.add(stringName+"\n"+stringNumber);
            }
            arrayAdapter.notifyDataSetChanged();
            cursor.close();
        }
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @OnClick(R.id.bt_zhibo)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_zhibo://直播详情
                startActivity(new Intent(ZhiBoFragment.this.getActivity(), ZhiBoDetailsActivity.class));
//                MyToast.showMyDialog();

                //抛出异常
//                textNull.equals("");
                // 在这里模拟异常抛出情况，人为抛出一个运行时异常
//                throw new RuntimeException("自定义异常：这是自己抛出的异常");
        }
    }
}
