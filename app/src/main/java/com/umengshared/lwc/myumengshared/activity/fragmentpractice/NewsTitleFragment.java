package com.umengshared.lwc.myumengshared.activity.fragmentpractice;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsTitleFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView_title)
    RecyclerView recyclerViewTitle;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean isTwoPane = false;
    private NewsAdapter mNewsAdapter;

    public NewsTitleFragment() {
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
    public static NewsTitleFragment newInstance(String param1, String param2) {
        NewsTitleFragment fragment = new NewsTitleFragment();
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
        return R.layout.fragment_news_title;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        recyclerViewTitle.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerViewTitle.setAdapter(mNewsAdapter = new NewsAdapter(getNewsList()));

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
        if (this.getActivity().findViewById(R.id.fl_content_layout) != null) {
            isTwoPane = true;//如果不为null，说明为双页模式
        } else {
            isTwoPane = false;
        }
    }

    /**
     * 测试数据
     *
     * @return
     */
    public List<News> getNewsList() {
        List newsList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            News news = new News();
            news.setNewsTitle("news is title" + i);
            news.setNewsContent("news content " + i);
            newsList.add(news);
        }
        return newsList;
    }


    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

        private List<News> mList;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitles;

            public ViewHolder(View itemView) {
                super(itemView);
                tvTitles = (TextView) itemView.findViewById(R.id.tv_title);
            }
        }

        public NewsAdapter(List<News> list) {
            this.mList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.news_item_list, parent, false);
            final ViewHolder viewHolder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = mList.get(viewHolder.getAdapterPosition());
                    if (isTwoPane) {
                        NewsContentFragment contentFragment = (NewsContentFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
                        contentFragment.onfresh(news.getNewsTitle(), news.getNewsContent());
                    } else {
                        NewContentActivity.actionStartActivit(getActivity(), news.getNewsTitle(), news.getNewsContent());
                    }

                }
            });
            return viewHolder;
        }

           /*@Override --注意是NewsAdapter.ViewHolder
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            News news = mList.get(position);
            holder.tvTitles.setText(news.getNewsTitle() + "");

        }*/

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news = mList.get(position);
            holder.tvTitles.setText(news.getNewsTitle() + "");
        }


        @Override
        public int getItemCount() {
            return mList.size();
        }


    }


}
