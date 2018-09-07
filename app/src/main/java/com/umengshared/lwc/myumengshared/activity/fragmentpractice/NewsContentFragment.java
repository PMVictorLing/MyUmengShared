package com.umengshared.lwc.myumengshared.activity.fragmentpractice;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.base.BaseFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NewsContentFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_news_title)
    TextView tvNewsTitle;
    @BindView(R.id.tv_news_content)
    TextView tvNewsContent;
    Unbinder unbinder;
    @BindView(R.id.tv_save_content)
    TextView tvSaveContent;
    Unbinder unbinder1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsTitleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsContentFragment newInstance(String param1, String param2) {
        NewsContentFragment fragment = new NewsContentFragment();
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
        return R.layout.fragment_news_content;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onfresh(String title, String content) {
        tvNewsTitle.setText(title);
        tvNewsContent.setText(content);

    }

    @Override
    public void onStop() {
        super.onStop();
        saveFiledata(tvNewsTitle.getText().toString());
    }

    /**
     * 文件保存数据
     *
     * @param s
     */
    private void saveFiledata(String s) {
        FileOutputStream out = null;
        OutputStreamWriter writer = null;
        BufferedWriter bufferWriter = null;
        try {
            out = this.getActivity().openFileOutput("data", Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferWriter != null) {
                try {
                    bufferWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick(R.id.tv_save_content)
    public void onClick() {
        tvSaveContent.setText(loadFileData());
    }

    /**
     * 加载取出文件数据
     */
    private String loadFileData() {
        FileInputStream in = null;
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            in = this.getActivity().openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
}
