package cn.bs.zjzc.dialog;

import android.content.Context;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;

import cn.bs.zjzc.util.DateTimeUtils;

/**
 * 时间弹框
 * Created by GGG on 2016/7/14.
 */
public class TimeDialog {

    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private OptionsPickerView pvOptionsTime;
    private TimeSelectListener listener;

    public TimeDialog(Context cxt, TimeSelectListener listener) {
        this.listener = listener;
        initTimeDialog(cxt);
    }

    private void initTimeDialog(Context cxt) {
        ArrayList<String> items2 = new ArrayList<>();
        ArrayList<String> items3 = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i <= 9) {
                items2.add("0" + i + "点");
            } else {
                items2.add("" + i + "点");
            }
        }
        for (int i = 0; i < 60; i++) {
            if (i <= 9) {
                items3.add("0" + i + "分");
            } else {
                items3.add("" + i + "分");
            }
        }
        //选项选择器
        pvOptionsTime = new OptionsPickerView(cxt);
        //选项1
        options1Items.add("现在");
        options1Items.add("今天");
        options1Items.add("明天");
        options1Items.add("后天");
        //选项2
        for (int i = 0; i < 4; i++) {
            options2Items.add(items2);
        }
        //选项3
        for (int i = 0; i < 24; i++) {
            ArrayList<ArrayList<String>> items = new ArrayList<>();
            for (int j = 0; j < 60; j++) {
                items.add(items3);
            }
            options3Items.add(items);
        }
        //三级联动效果
        pvOptionsTime.setPicker(options1Items, options2Items, options3Items, true);
        //循环滚动
        pvOptionsTime.setCyclic(false, true, true);
        //设置默认选中的三级项目
        pvOptionsTime.setSelectOptions(1, 1, 1);
        //监听确定选择按钮
        pvOptionsTime.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                //日期
                StringBuffer sb = new StringBuffer();
                if (options1 == 0) {
                    sb.append(DateTimeUtils.getDate(options1));
                } else {
                    sb.append(DateTimeUtils.getDate(options1 - 1));
                }
                //时间
                sb.append(" " + options2Items.get(options1).get(option2).replaceAll("点", "") + ":" + options3Items.get(options1).get(option2).get(options3).replaceAll("分", "") + ":00");
                listener.timeSelect(sb.toString());
            }
        });
    }

    public void show() {
        pvOptionsTime.show();
    }

    public void dismiss() {
        pvOptionsTime.dismiss();
    }

    public interface TimeSelectListener {
        void timeSelect(String time);
    }
}
