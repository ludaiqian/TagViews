# TagViews


#项目描述

一个简单易用的标签控件库，针对ListView或者RecycleView中使用做了特别的优化。在列表中使用依然流畅。


#效果图

 ![image](https://github.com/ludaiqian/TagViews/blob/master/sample/screenshot/phone_screen.gif)
 
#导入

目前jcenter正在审核
#使用方式
<blockquote>
   <com.flqy.tagviews.TagLayout
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
        app:tagSelectMode="multiple"/>
        <blockquote>
        
#attrs属性如下：
<blockquote>

 <declare-styleable name="TagLayout">
        <attr name="horizontalSpace" format="dimension" /><!-- tag之间的横向间距-->
        <attr name="verticalSpace" format="dimension" /><!-- tag之间的纵向间距-->
        <attr name="maxLines" format="integer" /><!-- 最大行数-->
        <attr name="tagResId" format="reference" /><!-- tag TextView资源id，可将tag文字大小、颜色、背景、padding等单独配置提升复用性-->
        <attr name="tagTextSize" format="dimension" /><!-- tag文字大小-->
        <attr name="tagBackground" format="reference" /><!-- tag背景-->
        <attr name="tagMinWidth" format="dimension" /><!-- tag最小宽度-->
        <attr name="tagTextColor" format="color" /><!-- tag文字颜色-->
        <attr name="tagTextHorizontalPadding" format="dimension" /><!-- tag 内部横向padding-->
        <attr name="tagTextVerticalPadding" format="dimension" /><!-- tag 内部纵向padding-->
        <attr name="maximumSelectionCount" format="integer" /><!-- 设置最多能够选择的个数-->
        <attr name="tagSelectMode" format="enum"><!-- 单选 多选-->
            <enum name="single" value="1"></enum><!--单选-->
            <enum name="multiple" value="2"></enum><!--多选-->
            <enum name="none" value="0"></enum><!--不可选-->
        </attr>
        <!--以下配置建议在列表中提高性能使用-->
        <attr name="cacheMode" format="enum"><!-- 缓存方式，常用方式下没有影响，当tag需要在RecycleView或者ListView中显示，设置为lazy能显著提高性能-->
            <enum name="auto" value="0"></enum><!--自动根据当前tag数量来增删childView，默认方式-->
            <enum name="lazy" value="1"></enum><!--tag数量会根据最大tag数量来决定，大于会等于tag数量的childView自动隐藏，而不是删除，性能最佳-->
            <enum name="none" value="2"></enum><!--不使用缓存-->
        </attr>
        <attr name="maxTags" format="integer" /><!--tags最大显示数量-->
        <attr name="preCache" format="boolean" /><!-- 预缓存，初始化时预先添加一定数量的childView-->

    </declare-styleable>
 
</blockquote>
v1.0.0


