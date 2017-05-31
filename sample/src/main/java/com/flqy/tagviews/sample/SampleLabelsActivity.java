package com.flqy.tagviews.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.flqy.tagviews.TagLayout;

/**
 * Created by lu on 2017/5/31.
 */

public class SampleLabelsActivity extends AppCompatActivity {
    private TagLayout tagLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_labels);
        tagLayout = (TagLayout) findViewById(R.id.tagLayout);
        tagLayout.setTags("幽默风趣","诚实可靠","人脉广","健身控","有才华","为人大方","老司机","爱摄影","文艺范","怪蜀黍","高富帅","矮穷挫");
        //添加click事件
        tagLayout.setOnItemClickListener(new TagLayout.OnItemClickListener() {
            @Override
            public void onItemClick(TextView child, int index) {

            }
        });
        //添加select事件
        tagLayout.setOnSelectChangeListener(new TagLayout.OnSelectChangeListener() {
            @Override
            public void onSelectChange(TextView child, int index, boolean isSelected) {

            }
        });
        //设置选中的child位置
        tagLayout.selectTagPositions(0,1,2);
        //获取选中的child位置
        tagLayout.getSelectedTagPositions();

    }
}
