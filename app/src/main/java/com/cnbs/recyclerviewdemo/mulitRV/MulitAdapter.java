package com.cnbs.recyclerviewdemo.mulitRV;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cnbs.recyclerviewdemo.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class MulitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MulitBean> data;
    private final int choiceType= 0;
    private final int gapFillingType= 1;

    public MulitAdapter(Context context, List<MulitBean> data) {
        this.context = context;
        this.data = data;
    }

    public void refresh(List<MulitBean>  data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==gapFillingType){
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_mulit0, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ItemViewHolderGapFilling(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_mulit1, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ItemViewHolderChoice(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolderChoice) {
            final MulitBean bean = data.get(position);
            ((ItemViewHolderChoice) holder).surveyTitle.setText(position + 1 + "." + data.get(position).getTitle());
            ((ItemViewHolderChoice) holder).button1.setText(bean.getChoiceContenta());
            ((ItemViewHolderChoice) holder).button2.setText(bean.getChoiceContentb());
            ((ItemViewHolderChoice) holder).button3.setText(bean.getChoiceContentc());
            ((ItemViewHolderChoice) holder).button4.setText(bean.getChoiceContentd());
            ((ItemViewHolderChoice) holder).button5.setText(bean.getChoiceContente());
            if (!TextUtils.isEmpty(bean.getMyAnswer())){
                switch (bean.getMyAnswer()) {
                    case "A":
                        ((ItemViewHolderChoice) holder).button1.setChecked(true);
                        ((ItemViewHolderChoice) holder).interview.setVisibility(View.GONE);
                        break;
                    case "B":
                        ((ItemViewHolderChoice) holder).button2.setChecked(true);
                        ((ItemViewHolderChoice) holder).interview.setVisibility(View.GONE);
                        break;
                    case "C":
                        ((ItemViewHolderChoice) holder).button3.setChecked(true);
                        ((ItemViewHolderChoice) holder).interview.setVisibility(View.GONE);
                        break;
                    case "D":
                        ((ItemViewHolderChoice) holder).button4.setChecked(true);
                        ((ItemViewHolderChoice) holder).interview.setVisibility(View.VISIBLE);
                        break;
                    case "E":
                        ((ItemViewHolderChoice) holder).button5.setChecked(true);
                        ((ItemViewHolderChoice) holder).interview.setVisibility(View.VISIBLE);
                        break;
                }
            }else {
                ((ItemViewHolderChoice) holder).button1.setChecked(false);
                ((ItemViewHolderChoice) holder).button2.setChecked(false);
                ((ItemViewHolderChoice) holder).button3.setChecked(false);
                ((ItemViewHolderChoice) holder).button4.setChecked(false);
                ((ItemViewHolderChoice) holder).button5.setChecked(false);
                ((ItemViewHolderChoice) holder).interview.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(bean.getMyInterviewRecord())){
                ((ItemViewHolderChoice) holder).interviewRecord.setText(bean.getMyInterviewRecord());
            }else {
                ((ItemViewHolderChoice) holder).interviewRecord.setText("");
            }

            ((ItemViewHolderChoice) holder).button1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        bean.setMyAnswer("A");
                        bean.setMyInterviewRecord("");
                        ((ItemViewHolderChoice) holder).interview.setVisibility(View.GONE);
                    }
                }
            });
            ((ItemViewHolderChoice) holder).button2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        bean.setMyAnswer("B");
                        bean.setMyInterviewRecord("");
                        ((ItemViewHolderChoice) holder).interview.setVisibility(View.GONE);
                    }
                }
            });
            ((ItemViewHolderChoice) holder).button3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        bean.setMyAnswer("C");
                        bean.setMyInterviewRecord("");
                        ((ItemViewHolderChoice) holder).interview.setVisibility(View.GONE);
                    }
                }
            });
            ((ItemViewHolderChoice) holder).button4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        bean.setMyAnswer("D");
                        bean.setMyInterviewRecord(((ItemViewHolderChoice) holder).interviewRecord.getText().toString().trim());
                        ((ItemViewHolderChoice) holder).interview.setVisibility(View.VISIBLE);
                    }
                }
            });
            ((ItemViewHolderChoice) holder).button5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        bean.setMyAnswer("E");
                        bean.setMyInterviewRecord(((ItemViewHolderChoice) holder).interviewRecord.getText().toString().trim());
                        ((ItemViewHolderChoice) holder).interview.setVisibility(View.VISIBLE);
                    }
                }
            });
            ((ItemViewHolderChoice) holder).interviewRecord.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String interviewRecord = s.toString();
                    bean.setMyInterviewRecord(interviewRecord);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        if (holder instanceof ItemViewHolderGapFilling) {
            final MulitBean bean = data.get(position);
            ((ItemViewHolderGapFilling) holder).surveyTitle.setText(position + 1 + "." + data.get(position).getTitle());
            ((ItemViewHolderGapFilling) holder).surveyAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String answer = s.toString();
                    bean.setMyAnswer(answer);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }

    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    public class ItemViewHolderChoice extends RecyclerView.ViewHolder {
        private LinearLayout interview;
        private TextView surveyTitle,interviewRecord;
        private RadioButton button1, button2, button3, button4, button5;

        public ItemViewHolderChoice(View itemView) {
            super(itemView);
            surveyTitle = (TextView) itemView.findViewById(R.id.survey_title);
            button1 = (RadioButton) itemView.findViewById(R.id.surver_button1);
            button2 = (RadioButton) itemView.findViewById(R.id.surver_button2);
            button3 = (RadioButton) itemView.findViewById(R.id.surver_button3);
            button4 = (RadioButton) itemView.findViewById(R.id.surver_button4);
            button5 = (RadioButton) itemView.findViewById(R.id.surver_button5);
            interviewRecord = (EditText) itemView.findViewById(R.id.survey_content_item0);
            interview = (LinearLayout) itemView.findViewById(R.id.survey_content);
        }
    }

    public class ItemViewHolderGapFilling extends RecyclerView.ViewHolder {
        private TextView surveyTitle,surveyAnswer;

        public ItemViewHolderGapFilling(View itemView) {
            super(itemView);
            surveyTitle = (TextView) itemView.findViewById(R.id.mulit_title_item0);
            surveyAnswer = (EditText) itemView.findViewById(R.id.mulit_content_item0);
        }
    }

}
