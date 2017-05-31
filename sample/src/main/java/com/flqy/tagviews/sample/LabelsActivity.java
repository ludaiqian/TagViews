package com.flqy.tagviews.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.flqy.tagviews.TagLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu on 2017/4/8.
 */

public class LabelsActivity extends AppCompatActivity {
    public static final char SPLIT = ',';
    private EditText inputView;
    private TagLayout tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labels);
        this.tags = (TagLayout) findViewById(R.id.skillTags);
        this.inputView = (EditText) findViewById(R.id.skillsInput);

        List<Integer> selectedPos = new ArrayList<>();
        selectedPos.add(2);
        selectedPos.add(3);
        selectedPos.add(4);
        this.tags.setTags(generateTags());
        this.tags.selectChildren(selectedPos);
        this.tags.setMaximumSelectionCount(10);

        this.tags.setOnSelectChangeListener(new TagLayout.OnSelectChangeListener() {
            @Override
            public void onSelectChange(TextView child, int index, boolean isSelected) {
                String text = child.getText().toString();
                String textWithSplit = SPLIT + text;
                String input = inputView.getText().toString();
                StringBuilder buffer = new StringBuilder(input);
                if (!isSelected) {
                    if (buffer.toString().startsWith(text)) {
                        buffer.delete(0, text.length());
                        while (buffer.length() > 0 && buffer.charAt(0) == SPLIT) {
                            buffer.delete(0, 1);
                        }
                    } else {
                        int offset = buffer.indexOf(textWithSplit);
                        if (offset >= 0) {
                            buffer.replace(offset, offset + textWithSplit.length(), "");
                        }
                    }

                } else if (buffer.indexOf(text) == -1) {
                    if (buffer.length() > 0 && buffer.charAt(buffer.length() - 1) != SPLIT) {
                        buffer.append(SPLIT);
                    }
                    buffer.append(text);
                }
                inputView.setText(buffer.toString());
                inputView.setSelection(inputView.getText().length());
            }
        });
    }

    private List<String> generateTags() {
        String hobbies = "篮球、羽毛球、兵乓球、足球、滑板、滑旱冰、跑步、跳绳、举重、听音乐、" +
                "看电影、绘画、写小说、看书、旅游、象棋、围棋、dota2、穿越火线、英雄联盟、王者荣耀";
        String[] hobbyArray = hobbies.split("、");
        ArrayList<String> tags = new ArrayList<>();
        for (String hobby : hobbyArray) {
            tags.add(hobby);
        }
        return tags;
    }


}
