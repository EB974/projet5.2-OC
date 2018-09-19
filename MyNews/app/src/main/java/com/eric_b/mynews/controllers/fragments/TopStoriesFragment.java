package com.eric_b.mynews.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.eric_b.mynews.R;
import com.eric_b.mynews.models.Result;
import com.eric_b.mynews.models.TopStoriePojo;
import com.eric_b.mynews.utils.ApiUtils;
import com.eric_b.mynews.utils.NyTimesService;
import com.eric_b.mynews.views.NewsAdapter;
import com.eric_b.mynews.views.NewsWebView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopStoriesFragment extends BaseFragment implements NewsAdapter.Listeners {


    @BindView(R.id.topstories_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.topstories_fragment_swipe_container)

    SwipeRefreshLayout swipeRefreshLayout;

    @State
    int memory;

    private Disposable disposable;
    private NyTimesService mService;
    private View view;
    //private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private List<Result> newsList;
    final String NEWS_URL = "News_URL";
    private RecyclerView.LayoutManager mLayoutManager;
    private int recoverPosition;

    public TopStoriesFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!= null) {
            recoverPosition = savedInstanceState.getInt("POSITION");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int lastFirstVisiblePosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        outState.putInt("POSITION",lastFirstVisiblePosition);

    }

// --------------
    // BASE METHODS
    // --------------

    @Override
    protected BaseFragment newInstance() {
        return null;
    }

    @Override
    protected int getFragmentLayout() {
        return 0;
    }

    @Override
    protected void configureDesign() { }

    @Override
    protected void updateDesign() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        this.configureSwipeRefreshLayout();
        mService = ApiUtils.getNyTimesService();
        configureRecyclerView();
        loadAnswers();
        return view;
    }

    private void configureRecyclerView(){
        this.mAdapter = new NewsAdapter( getActivity(), new ArrayList<Result>(0), Glide.with(this), new NewsAdapter.PostItemListener() {

            @Override
            public void onPostClick(String url) {
                Intent intent = new Intent(getActivity(),NewsWebView.class);
                intent = intent.putExtra(NEWS_URL, url);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                loadAnswers();
            }

        });

    }

    public void loadAnswers() {
        mService.getNews("home").enqueue(new Callback<TopStoriePojo>() {

            @Override
            public void onResponse(@NonNull Call<TopStoriePojo> call, @NonNull Response<TopStoriePojo> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    mAdapter.updateAnswers(response.body().getResults());
                    updateUI();
                    Log.d("repro", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    Log.d("repro", "status Code"+statusCode);
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopStoriePojo> call, @NonNull Throwable t) {
                //showErrorMessage();
                Log.d("repro", "error loading from API");

            }
        });
    }

    private void updateUI(){
        swipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.getLayoutManager().scrollToPosition(recoverPosition);
        recoverPosition = 0;
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }
}
