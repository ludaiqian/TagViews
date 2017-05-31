# TagViews


#项目描述

一个简单易用的标签控件，对性能做了细致的优化，在Listview/RecycleView中使用时，低端机型依然能够滑动流畅。



#效果图

 ![image](https://github.com/ludaiqian/TagViews/blob/master/sample/screenshot/phone_screen.gif)
 
#导入

目前无法导入，add to jcenter仍在审核，library仅有一个类，开发人员可自行下载代码进行使用。


#使用方式

##xml
<pre><code>
   &lt;com.flqy.tagviews.TagLayout
        android:id="@+id/skillTags"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:paddingRight="16dp"
        android:paddingLeft="16dp"
        android:paddingTop="16dp"
        android:paddingBottom="16dp"
        app:horizontalSpace="16dp"
        app:verticalSpace="16dp"
        app:tagTextSize="14sp"
        app:tagTextColor="@color/selector_label_text"
        app:tagBackground="@drawable/selector_label_tag"
        app:tagTextHorizontalPadding="8dp"
        app:tagTextVerticalPadding="4dp"
        app:tagMinWidth="70dp"
        app:tagSelectMode="multiple"&gt;
</code></pre>
##activity
<pre><code>
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
</code></pre>
#attrs定义属性及属性描述如下：
<pre><code>
 &lt;declare-styleable name="TagLayout">
        &lt;attr name="horizontalSpace" format="dimension" "&gt;&lt;!-- tag之间的横向间距-->
        &lt;attr name="verticalSpace" format="dimension" "&gt;&lt;!-- tag之间的纵向间距-->
        &lt;attr name="maxLines" format="integer" "&gt;&lt;!-- 最大行数-->
        &lt;attr name="tagResId" format="reference" "&gt;&lt;!-- tag TextView资源id，可将tag文字大小、颜色、背景、padding等单独配置提升复用性-->
        &lt;attr name="tagTextSize" format="dimension" "&gt;&lt;!-- tag文字大小-->
        &lt;attr name="tagBackground" format="reference" "&gt;&lt;!-- tag背景-->
        &lt;attr name="tagMinWidth" format="dimension" "&gt;&lt;!-- tag最小宽度-->
        &lt;attr name="tagTextColor" format="color" "&gt;&lt;!-- tag文字颜色-->
        &lt;attr name="tagTextHorizontalPadding" format="dimension" "&gt;&lt;!-- tag 内部横向padding-->
        &lt;attr name="tagTextVerticalPadding" format="dimension" "&gt;&lt;!-- tag 内部纵向padding-->
        &lt;attr name="maximumSelectionCount" format="integer" "&gt;&lt;!-- 设置最多能够选择的个数-->
        &lt;attr name="tagSelectMode" format="enum">&lt;!-- 单选 多选-->
            &lt;enum name="single" value="1">&lt;/enum>&lt;!--单选-->
            &lt;enum name="multiple" value="2">&lt;/enum>&lt;!--多选-->
            &lt;enum name="none" value="0">&lt;/enum>&lt;!--不可选-->
        &lt;/attr>
        &lt;!--以下配置建议在列表中提高性能使用-->
        &lt;attr name="cacheMode" format="enum">&lt;!-- 缓存方式，常用方式下没有影响，当tag需要在RecycleView或者ListView中显示，设置为lazy能显著提高性能-->
            &lt;enum name="auto" value="0">&lt;/enum>&lt;!--自动根据当前tag数量来增删childView，默认方式-->
            &lt;enum name="lazy" value="1">&lt;/enum>&lt;!--tag数量会根据最大tag数量来决定，大于会等于tag数量的childView自动隐藏，而不是删除，性能最佳-->
            &lt;enum name="none" value="2">&lt;/enum>&lt;!--不使用缓存-->
        &lt;/attr>
        &lt;attr name="maxTags" format="integer" "&gt;&lt;!--tags最大显示数量-->
        &lt;attr name="preCache" format="boolean" "&gt;&lt;!-- 预缓存，初始化时预先添加一定数量的childView-->

    &lt;/declare-styleable>
 
</code></pre>
v1.0.0


