package com.mobiloby.filter.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.mobiloby.filter.models.QuestionObject;
import com.mobiloby.filter.R;

import java.util.ArrayList;

public class MyQuestionListAdapter extends ArrayAdapter<QuestionObject> {

    private Activity context;
    private ArrayList<QuestionObject> list;

    public MyQuestionListAdapter(Activity context, ArrayList<QuestionObject> list) {
        super(context, R.layout.item_list_questions, list);

        this.context = context;
        this.list = list;
    }

    static  class ViewHolder
    {
        EditText e_question, e_answer1, e_answer2, e_answer3;
        RadioGroup rg_answers;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            LayoutInflater inflator = context.getLayoutInflater();
            convertView = inflator.inflate(R.layout.item_list_questions, null);
            viewHolder = new ViewHolder();

            viewHolder.e_question = convertView.findViewById(R.id.e_question);
            viewHolder.e_answer1 = convertView.findViewById(R.id.e_answer1);
            viewHolder.e_answer2 = convertView.findViewById(R.id.e_answer2);
            viewHolder.e_answer3 = convertView.findViewById(R.id.e_answer3);
            viewHolder.rg_answers = convertView.findViewById(R.id.rg_answers);

            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list != null)
        {
            final QuestionObject questionObject = list.get(position);

            if(questionObject!=null){
                viewHolder.e_question.setText(list.get(position).getQuestion());
                viewHolder.e_answer1.setText(list.get(position).getAnswer1());
                viewHolder.e_answer2.setText(list.get(position).getAnswer2());
                viewHolder.e_answer3.setText(list.get(position).getAnswer3());

                if(list.get(position).getCorrectAnswer().equals("1")){
                    viewHolder.rg_answers.check(R.id.rb_answer1);
                }
                else if(list.get(position).getCorrectAnswer().equals("2")){
                    viewHolder.rg_answers.check(R.id.rb_answer2);
                }
                else if(list.get(position).getCorrectAnswer().equals("3")){
                    viewHolder.rg_answers.check(R.id.rb_answer3);
                }
            }


        }

        viewHolder.rg_answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                ActivityCategory2_Detail2.questionObjects.get(position).setCorrectAnswer(""+checkedId);
            }
        });

        return convertView;
    }

}


