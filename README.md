# TagViews


#项目描述

一个简单易用的标签控件，对性能做了细致的优化，在Listview/RecycleView中使用时，低端机型依然能够滑动流畅。



#效果图

 ![image](https://github.com/ludaiqian/TagViews/blob/master/sample/screenshot/phone_screen.gif)
 
#导入

目前无法导入，add to jcenter一直未被审核，开发人员可自行下载代码进行使用。


#使用方式

##xml
<pre><code>
     &lt;com.flqy.tagviews.TagLayout
        android:id=&quot;@+id/skillTags&quot;
        android:layout_width=&quot;match_parent&quot;
        android:layout_height=&quot;wrap_content&quot;
        android:paddingRight=&quot;16dp&quot;
        android:paddingLeft=&quot;16dp&quot;
        android:paddingTop=&quot;16dp&quot;
        android:paddingBottom=&quot;16dp&quot;
        app:horizontalSpace=&quot;16dp&quot;
        app:verticalSpace=&quot;16dp&quot;
        app:tagTextSize=&quot;14sp&quot;
        app:tagTextColor=&quot;@color/selector_label_text&quot;
        app:tagBackground=&quot;@drawable/selector_label_tag&quot;
        app:tagTextHorizontalPadding=&quot;8dp&quot;
        app:tagTextVerticalPadding=&quot;4dp&quot;
        app:tagMinWidth=&quot;70dp&quot;
        app:tagSelectMode=&quot;multiple&quot;&gt;
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
 &lt;declare-styleable name=&quot;TagLayout&quot;&gt;
        &lt;attr name=&quot;horizontalSpace&quot; format=&quot;dimension&quot; &quot;&gt;&lt;!-- tag之间的横向间距--&gt;
        &lt;attr name=&quot;verticalSpace&quot; format=&quot;dimension&quot; &quot;&gt;&lt;!-- tag之间的纵向间距--&gt;
        &lt;attr name=&quot;maxLines&quot; format=&quot;integer&quot; &quot;&gt;&lt;!-- 最大行数--&gt;
        &lt;attr name=&quot;tagResId&quot; format=&quot;reference&quot; &quot;&gt;&lt;!-- tag TextView资源id，可将tag文字大小、颜色、背景、padding等单独配置提升复用性--&gt;
        &lt;attr name=&quot;tagTextSize&quot; format=&quot;dimension&quot; &quot;&gt;&lt;!-- tag文字大小--&gt;
        &lt;attr name=&quot;tagBackground&quot; format=&quot;reference&quot; &quot;&gt;&lt;!-- tag背景--&gt;
        &lt;attr name=&quot;tagMinWidth&quot; format=&quot;dimension&quot; &quot;&gt;&lt;!-- tag最小宽度--&gt;
        &lt;attr name=&quot;tagTextColor&quot; format=&quot;color&quot; &quot;&gt;&lt;!-- tag文字颜色--&gt;
        &lt;attr name=&quot;tagTextHorizontalPadding&quot; format=&quot;dimension&quot; &quot;&gt;&lt;!-- tag 内部横向padding--&gt;
        &lt;attr name=&quot;tagTextVerticalPadding&quot; format=&quot;dimension&quot; &quot;&gt;&lt;!-- tag 内部纵向padding--&gt;
        &lt;attr name=&quot;maximumSelectionCount&quot; format=&quot;integer&quot; &quot;&gt;&lt;!-- 设置最多能够选择的个数--&gt;
        &lt;attr name=&quot;tagSelectMode&quot; format=&quot;enum&quot;&gt;&lt;!-- 单选 多选--&gt;
            &lt;enum name=&quot;single&quot; value=&quot;1&quot;&gt;&lt;/enum&gt;&lt;!--单选--&gt;
            &lt;enum name=&quot;multiple&quot; value=&quot;2&quot;&gt;&lt;/enum&gt;&lt;!--多选--&gt;
            &lt;enum name=&quot;none&quot; value=&quot;0&quot;&gt;&lt;/enum&gt;&lt;!--不可选--&gt;
        &lt;/attr&gt;
        &lt;!--以下配置建议在列表中提高性能使用--&gt;
        &lt;attr name=&quot;cacheMode&quot; format=&quot;enum&quot;&gt;&lt;!-- 缓存方式，常用方式下没有影响，当tag需要在RecycleView或者ListView中显示，设置为lazy能显著提高性能--&gt;
            &lt;enum name=&quot;auto&quot; value=&quot;0&quot;&gt;&lt;/enum&gt;&lt;!--自动根据当前tag数量来增删childView，默认方式--&gt;
            &lt;enum name=&quot;lazy&quot; value=&quot;1&quot;&gt;&lt;/enum&gt;&lt;!--tag数量会根据最大tag数量来决定，大于会等于tag数量的childView自动隐藏，而不是删除，性能最佳--&gt;
            &lt;enum name=&quot;none&quot; value=&quot;2&quot;&gt;&lt;/enum&gt;&lt;!--不使用缓存--&gt;
        &lt;/attr&gt;
        &lt;attr name=&quot;maxTags&quot; format=&quot;integer&quot; &quot;&gt;&lt;!--tags最大显示数量--&gt;
        &lt;attr name=&quot;preCache&quot; format=&quot;boolean&quot; &quot;&gt;&lt;!-- 预缓存，初始化时预先添加一定数量的childView--&gt;

    &lt;/declare-styleable&gt;
 
</code></pre>
v1.0.0


