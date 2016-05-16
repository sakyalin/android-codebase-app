package com.linxinzhe.android.codebaseapp.feature;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.linxinzhe.android.codebaseapp.R;
import com.linxinzhe.android.codebaseapp.base.BaseActivity;
import com.linxinzhe.android.codebaseapp.model.BizModel;
import com.linxinzhe.android.codebaseapp.network.NetworkService;
import com.linxinzhe.android.codebaseapp.util.OkHttpUtil;
import com.linxinzhe.android.codebaseapp.util.RetrofitUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RefreshAndLoadMoreActivity extends BaseActivity {

    private static final int DATABASE_START = 0;
    private static final int PAGE_LENGTH = 10;

    @BindView(R.id.load_more_list_view_container)
    LoadMoreListViewContainer mLoadMoreListViewContainer;
    @BindView(R.id.ptr_frame_refresh_list_view)
    PtrClassicFrameLayout mPtrFrameRefreshListView;
    @BindView(R.id.lv_list)
    ListView mLvList;

    private int mStart = DATABASE_START;
    private List<BizModel> mBizModelList = new ArrayList<>();
    private BizModelAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_and_load_more);
        ButterKnife.bind(this);

        mAdapter = new BizModelAdapter(this, mBizModelList);
        mLvList.setAdapter(mAdapter);

        initRefreshAndLoadMoreListView();
    }

    private void initRefreshAndLoadMoreListView() {
        //set Material style refresh header
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = {getResources().getColor(R.color.colorPrimary), this.getResources().getColor(R.color.colorPrimaryDark), this.getResources().getColor(R.color.colorAccent)};
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrameRefreshListView);
        //set to layout
        mPtrFrameRefreshListView.setLoadingMinTime(1000);
        mPtrFrameRefreshListView.setHeaderView(header);
        mPtrFrameRefreshListView.addPtrUIHandler(header);
        mPtrFrameRefreshListView.setPinContent(true);


        mPtrFrameRefreshListView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mLvList, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestForClear(frame);
                    }
                }, 10);
            }
        });

        // set load more
        mLoadMoreListViewContainer.useDefaultHeader();
        mLoadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                int start = mStart + PAGE_LENGTH;
                requestForLoadMore(start, loadMoreContainer);
            }
        });

        // init data
        mPtrFrameRefreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameRefreshListView.autoRefresh(true);
            }
        }, 150);
    }

    @OnItemClick(R.id.lv_list)
    public void onLvListItemClick(AdapterView<?> parent, View view, int position, long id) {
        BizModel model = mBizModelList.get(position);
        // do business
    }

    private void requestForLoadMore(final int start, final LoadMoreContainer loadMoreContainer) {
        OkHttpUtil.isNetworkConnected(this, new OkHttpUtil.OkhttpNetwork() {
            @Override
            public void connected() {
                final Map<String, String> mapPost = new HashMap<>();
                mapPost.put("start", String.valueOf(start));
                mapPost.put("length", String.valueOf(PAGE_LENGTH));

                Retrofit retrofit = RetrofitUtil.getInstance();
                NetworkService service = retrofit.create(NetworkService.class);
                Observable<List<BizModel>> observable = service.queryBizModel(mapPost);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<BizModel>>() {
                            @Override
                            public void onCompleted() {
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Throwable e) {
                                showToast(getString(R.string.toast_request_error) + e.getMessage());
                                Logger.d(e.getMessage());
                                if (loadMoreContainer != null) {
                                    loadMoreContainer.loadMoreFinish(false, true);
                                }
                            }

                            @Override
                            public void onNext(final List<BizModel> list) {
                                mStart = start;
                                mBizModelList.addAll(list);
                                if (loadMoreContainer != null) {
                                    loadMoreContainer.loadMoreFinish(false, !(list.size() < PAGE_LENGTH));
                                }
                            }
                        });
            }
        });
    }

    private void requestForClear(final PtrFrameLayout frame) {
        OkHttpUtil.isNetworkConnected(this, new OkHttpUtil.OkhttpNetwork() {
            @Override
            public void connected() {
                final int start = DATABASE_START;

                final Map<String, String> mapPost = new HashMap<>();
                mapPost.put("start", String.valueOf(start));
                mapPost.put("length", String.valueOf(PAGE_LENGTH));

                Retrofit retrofit = RetrofitUtil.getInstance();
                NetworkService service = retrofit.create(NetworkService.class);
                Observable<List<BizModel>> observable = service.queryBizModel(mapPost);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<BizModel>>() {
                            @Override
                            public void onCompleted() {
                                mAdapter.notifyDataSetChanged();
                                if (frame != null) {
                                    frame.refreshComplete();
                                }
                                if (mLoadMoreListViewContainer != null) {
                                    mLoadMoreListViewContainer.loadMoreFinish(false, true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                showToast(getString(R.string.toast_request_error) + e.getMessage());
                                Logger.d(e.getMessage());
                                if (frame != null) {
                                    frame.refreshComplete();
                                }
                                if (mLoadMoreListViewContainer != null) {
                                    mLoadMoreListViewContainer.loadMoreFinish(false, true);
                                }
                            }

                            @Override
                            public void onNext(final List<BizModel> bizModelList) {
                                mStart = start;
                                mBizModelList.clear();
                                mBizModelList.addAll(bizModelList);
                            }
                        });
            }
        });
    }

    static class BizModelAdapter extends BaseAdapter {

        private final Context mContext;

        private List<BizModel> mBizModelList;

        public BizModelAdapter(Context context, List<BizModel> chgInfoList) {
            this.mContext = context;
            this.mBizModelList = chgInfoList;
        }

        @Override
        public int getCount() {
            return mBizModelList.size();
        }

        @Override
        public BizModel getItem(int position) {
            return mBizModelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BizModel info = mBizModelList.get(position);

            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(android.R.layout.simple_list_item_1, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            holder.tvText.setText(info.getClass().getSimpleName());

            return convertView;
        }

        static class ViewHolder {
            @BindView(android.R.id.text1)
            TextView tvText;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
