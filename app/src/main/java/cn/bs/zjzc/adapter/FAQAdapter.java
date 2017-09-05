package cn.bs.zjzc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;
import java.util.Map;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.div.NormalOptionView;
import cn.bs.zjzc.model.response.FAQListResponse;

import static cn.bs.zjzc.R.id.answer;

/**
 * Created by Ming on 2016/5/25.
 */
public class FAQAdapter extends BaseNormalAdapter<FAQListResponse.DataBean.ListBean> {


    public FAQAdapter(Context context) {
        super(context);
    }

    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_common_problem_list, parent, false);
            holder.questionFrame = ((AutoRelativeLayout) convertView.findViewById(R.id.question_frame));
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
            holder.question = (TextView) convertView.findViewById(R.id.question);
            holder.answer = (TextView) convertView.findViewById(R.id.answer);
            holder.answerFrame = (AutoFrameLayout) convertView.findViewById(R.id.answer_frame);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final AutoFrameLayout answerFrame = holder.answerFrame;
        final ImageView arrow = holder.arrow;

        FAQListResponse.DataBean.ListBean listBean = getData().get(position);
        holder.answer.setText(listBean.answer);
        holder.question.setText((position + 1) + "." + listBean.question);

        holder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerFrame.isShown()) {
                    answerFrame.setVisibility(View.GONE);
                    arrow.setImageResource(R.mipmap.zjzc_arrow_down);
                } else {
                    answerFrame.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.mipmap.zjzc_arrow_up);
                }

            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView number;
        TextView question;
        TextView answer;
        ImageView arrow;
        AutoRelativeLayout questionFrame;
        AutoFrameLayout answerFrame;
    }
}
