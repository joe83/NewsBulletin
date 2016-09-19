package com.mrym.newsbulletion.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mrym.newsbulletion.R;
import com.mrym.newsbulletion.adapter.NewsAdapter2;
import com.mrym.newsbulletion.adapter.RecyclerViewAdapter;
import com.mrym.newsbulletion.db.entity.HomeCateGory;
import com.mrym.newsbulletion.db.utils.HomeCateGoryUtils;
import com.mrym.newsbulletion.domain.constans.GlobalVariable;
import com.mrym.newsbulletion.domain.modle.NewsEntity;
import com.mrym.newsbulletion.mvp.MvpFragment;
import com.mrym.newsbulletion.mvp.fragment.category.GateGoryPresenter;
import com.mrym.newsbulletion.mvp.fragment.category.GateGoryView;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jian on 2016/9/14.
 * Email: 798774875@qq.com
 * Github: https://github.com/moruoyiming
 */
public class GateGoryFragment extends MvpFragment<GateGoryPresenter> implements GateGoryView {
    @Bind(R.id.category_list)
    XRecyclerView categoryList;
    protected int mCurrentAction = GlobalVariable.ACTION_REFRESH;
    protected int mCurrentPageIndex = 1;
    private NewsAdapter2 ma;
    private RecyclerViewAdapter adapter;
    private List<NewsEntity> mNews;
    private String mCurrentCate = null;

    @Override
    protected GateGoryPresenter createPresenter() {
        return new GateGoryPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = View.inflate(getActivity(), R.layout.fragment_category, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNews = new ArrayList<>();
        ma = new NewsAdapter2(mNews, getActivity());
        adapter=new RecyclerViewAdapter(getActivity());
        categoryList.setAdapter(adapter);
        int position = FragmentPagerItem.getPosition(getArguments());
        ArrayList<HomeCateGory> homeCateGories = HomeCateGoryUtils.getInstance(getContext()).getHomeCateGory();
        mCurrentCate = homeCateGories.get(position).getField();
        categoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryList.setEmptyView(View.inflate(getContext(), R.layout.item_empty_view, null));
        categoryList.setRefreshProgressStyle(ProgressStyle.BallBeat);
        categoryList.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        categoryList.setLoadingMoreEnabled(true);
        categoryList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchActionAndLoadData(GlobalVariable.ACTION_REFRESH);
            }

            @Override
            public void onLoadMore() {
                switchActionAndLoadData(GlobalVariable.ACTION_LOAD_MORE);
            }
        });
        categoryList.setRefreshing(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void switchActionAndLoadData(int action) {
        mCurrentAction = action;
        switch (mCurrentAction) {
            case GlobalVariable.ACTION_REFRESH:
                mNews.clear();
                mCurrentPageIndex = 1;
                mvpPresenter.getGategoryData(mCurrentCate, mCurrentPageIndex);
                break;
            case GlobalVariable.ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                mvpPresenter.getGategoryData(mCurrentCate, mCurrentPageIndex);
                break;
        }
    }

    @Override
    public void loadingError(String errormsg) {

    }

    @Override
    public void loadingSuccess(List<NewsEntity> news) {
//        ma.addAll(news);
        adapter.setDatas(news);
    }


    @Override
    public void loadComplete() {
        try {
            if (mCurrentAction == GlobalVariable.ACTION_REFRESH) {
                categoryList.refreshComplete();
            }
            if (mCurrentAction == GlobalVariable.ACTION_LOAD_MORE) {
                categoryList.loadMoreComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNews = null;
        mvpPresenter = null;
        categoryList = null;
        ButterKnife.unbind(this);
    }
}
