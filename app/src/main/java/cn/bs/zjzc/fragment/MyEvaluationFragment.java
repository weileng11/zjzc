package cn.bs.zjzc.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.MyEvaluationAdapter;
import cn.bs.zjzc.model.response.EvaluationListResponse;
import cn.bs.zjzc.presenter.MyEvaluationPresenter;
import cn.bs.zjzc.util.T;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Ming on 2016/7/5.
 */
public class MyEvaluationFragment extends Fragment implements IMyEvaluationView {

    private String mType;
    private int currentPage = 1;
    private View view;
    private ListView listView;
    private PtrClassicFrameLayout ptrFrameLayout;
    private MyEvaluationAdapter evaluationListAdapter;
    private MyEvaluationPresenter myEvaluationPresenter;
    private TextView tvEvaluationCount;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myEvaluationPresenter = new MyEvaluationPresenter(this);
        Bundle arguments = getArguments();
        mType = arguments.getString("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_evaluation_list, container, false);
            initViews();
            initEvents();
            myEvaluationPresenter.getEvaluationList(mType, "1");
        }
        return view;
    }

    private void initEvents() {
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);

        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                myEvaluationPresenter.getEvaluationList(mType, String.valueOf(++currentPage));
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                myEvaluationPresenter.getEvaluationList(mType, "1");
            }
        });
    }

    private void initViews() {
        ptrFrameLayout = ((PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame_layout));
        listView = ((ListView) view.findViewById(R.id.list_view));
        tvEvaluationCount = ((TextView) view.findViewById(R.id.evaluation_count));
        evaluationListAdapter = new MyEvaluationAdapter(getContext());
        listView.setAdapter(evaluationListAdapter);
    }

    @Override
    public void showEvaluationList(EvaluationListResponse.DataBean data) {
        if (TextUtils.equals(data.page.page, "1")) {
            evaluationListAdapter.setData(data.list);
            tvEvaluationCount.setText("全部评价（" + data.page.record_count + "）次");
            return;
        }
        evaluationListAdapter.addDatas(data.list);
        ptrFrameLayout.refreshComplete();
    }


    @Override
    public void showMsg(String msg) {
        T.showShort(getContext(), msg);
    }

    @Override
    public void completeFresh() {
        ptrFrameLayout.refreshComplete();
    }

}
