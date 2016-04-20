package com.juns.wechat.moments.adapter;


import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

import com.juns.wechat.App;
import com.juns.wechat.R;
import com.juns.wechat.moments.bean.FavortItem;
import com.juns.wechat.moments.spannable.CircleMovementMethod;
import com.juns.wechat.moments.spannable.NameClickable;
import com.juns.wechat.moments.widgets.FavortListView;

import java.util.List;

/**
 * @author yiw
 * @Description:
 * @date 16/1/2 18:51
 */
public class FavortListAdapter {

    private FavortListView mListView;
    private List<FavortItem> datas;

    public List<FavortItem> getDatas() {
        return datas;
    }

    public void setDatas(List<FavortItem> datas) {
        this.datas = datas;
    }

    @NonNull
    public void bindListView(FavortListView listview){
        if(listview == null){
            throw new IllegalArgumentException("FavortListView is null ....");
        }
        mListView = listview;
    }


    public int getCount() {
        if(datas != null && datas.size() > 0){
            return datas.size();
        }
        return 0;
    }

    public Object getItem(int position) {
        if(datas != null && datas.size() > position){
            return datas.get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public void notifyDataSetChanged(){
        if(mListView == null){
            throw new NullPointerException("listview is null, please bindListView first...");
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if(datas != null && datas.size() > 0){
            //添加点赞图标
            builder.append(setImageSpan());
            //builder.append("  ");
            FavortItem item = null;
            for (int i=0; i<datas.size(); i++){
                item = datas.get(i);
                if(item != null){
                    builder.append(setClickableSpan(item.getUser().getName(), i));
                    if(i != datas.size()-1){
                        builder.append(", ");
                    }
                }
            }
        }
        mListView.setText(builder);
        mListView.setMovementMethod(new CircleMovementMethod(R.color.name_selector_color));
    }

    @NonNull
    private SpannableString setClickableSpan(String textStr, int position) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new NameClickable(mListView.getSpanClickListener(), position), 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    private SpannableString setImageSpan(){
        String text = "  ";
        SpannableString imgSpanText = new SpannableString(text);
        imgSpanText.setSpan(new ImageSpan(App.getInstance(), R.drawable.im_ic_dig_tips, DynamicDrawableSpan.ALIGN_BASELINE),
                0 , 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imgSpanText;
    }
}
