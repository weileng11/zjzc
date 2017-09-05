package cn.bs.zjzc.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.zhy.http.okhttp.utils.L;

import java.util.Date;
import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.NewsListAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.items.SwipeMenuBuilder;
import cn.bs.zjzc.model.response.NewsListResponse;
import cn.bs.zjzc.presenter.NewsCenterPresenter;
import cn.bs.zjzc.pulltorefresh.PullToRefreshBase;
import cn.bs.zjzc.pulltorefresh.impl.PullToRefreshSwipeListView;
import cn.bs.zjzc.ui.view.INewsCenterView;
import cn.bs.zjzc.util.T;
import cn.bs.zjzc.util.TimeFormatUtils;

/**
 * Created by Ming on 2016/5/21.
 */
public class AvNewsCenter extends BaseActivity implements INewsCenterView, PullToRefreshBase.OnRefreshListener<SwipeMenuListView> {
    public Context mContext = this;
    private NewsCenterPresenter mNewsCenterPresenter;
    private TopBarView topBar;
    private SwipeMenuListView lvNewList;
    private NewsListAdapter adapter;
    private PullToRefreshSwipeListView pullToRefreshSwipeListView;
    private int currentPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_news_center);
        mNewsCenterPresenter = new NewsCenterPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        topBar.setRightClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopuMenu(v);
            }
        });
    }

    private void showPopuMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        getMenuInflater().inflate(R.menu.menu_news_center, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_read_all:
                        mNewsCenterPresenter.setNewsRead("");
                        adapter.setReadAll();
                        break;
                    case R.id.action_clear_all:
                        mNewsCenterPresenter.deleteNews("", -1);
                        adapter.deleteAll();
                        break;
                }
                return false;
            }
        });
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.news_top_bar));
//        lvNewList = ((SwipeMenuListView) findViewById(R.id.news_list));
        pullToRefreshSwipeListView = ((PullToRefreshSwipeListView) findViewById(R.id.pullToRefreshSwipeListView));
        lvNewList = pullToRefreshSwipeListView.getRefreshableView();

        initListView();
//
        mNewsCenterPresenter.getNewsList(currentPage + "", "8");

    }

    private void initListView() {
        pullToRefreshSwipeListView.setOnRefreshListener(this);
        pullToRefreshSwipeListView.setCanLoadEnabled(true);
        adapter = new NewsListAdapter(mContext);
        lvNewList.setAdapter(adapter);

        final SwipeMenuCreator menuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {
                swipeMenu.addMenuItem(SwipeMenuBuilder.createSwipeMenu(R.layout.item_swipe_menu_delete, 90));
            }
        };
        lvNewList.setMenuCreator(menuCreator);

        lvNewList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu swipeMenu, int index) {
                if (index == 0) {
                    mNewsCenterPresenter.deleteNews(adapter.getItem(position).id, position);
                }
                return false;
            }
        });
    }

    @Override
    public void showNewsList(NewsListResponse.DataBean data) {
        if (currentPage == 1) {
            adapter.getData().clear();
            adapter.setData(data.list);
            String text = TimeFormatUtils.parseDate(new Date(System.currentTimeMillis()), null);
            pullToRefreshSwipeListView.setLastUpdatedLabel(text);
            pullToRefreshSwipeListView.onPullDownRefreshComplete();
        } else { //加载更多
            adapter.getData().addAll(data.list);
            adapter.notifyDataSetChanged();
            pullToRefreshSwipeListView.onPullUpRefreshComplete();
        }
    }

    @Override
    public void deleteNews(int position) {
        if (position == -1) {
            adapter.deleteAll();
        } else {
            adapter.delete(position);
        }
    }

    @Override
    public void refreshFail() {
        pullToRefreshSwipeListView.onPullDownRefreshComplete();
        pullToRefreshSwipeListView.onPullUpRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<SwipeMenuListView> refreshView) {
        currentPage = 1;
        mNewsCenterPresenter.getNewsList(currentPage + "", "8");
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<SwipeMenuListView> refreshView) {
        currentPage++;
        mNewsCenterPresenter.getNewsList(currentPage + "", "8");
    }
}
