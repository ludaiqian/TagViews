package com.flqy.tagviews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lu on 2017/4/5.
 */

public class TagLayout extends ViewGroup {
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String TAGS = "tags";
    private static final String SELECTED_TAG_POSITIONS = "selected_tag_positions";
    private static final int SELECT_MODE_NONE = 0;
    private static final int SELECT_MODE_SINGLE = 1;
    private static final int SELECT_MODE_MULTIPLE = 2;
    private static final int CACHE_MODE_AUTO = 0;
    private static final int CACHE_MODE_LAZY = 1;
    private static final int CACHE_MODE_NONE = 2;
    private static final int LINES_CACHE_COUNT = 10;
    public static final int MAX_PRE_CACHE_COUNT = 30;
    private static final String DEFAULT_TEXT = "";
    private int mTagSelectMode = SELECT_MODE_NONE;
    private Integer mTagTextSize;
    private Integer mTagResId;
    private List<TagLine> mTagLines = new ArrayList<>();
    private int mHorizontalSpace = 0;
    private int mVerticalSpace = 0;
    private int maxLines = Integer.MAX_VALUE;
    private int mMaximumSelectionCount = Integer.MAX_VALUE;
    private ArrayList<String> mTags;
    private LayoutInflater mInflater;
    private Integer mTagBackground;
    private Integer mTagMinWidth;
    private ColorStateList mTagTextColor;
    private Integer mTagTextHorizontalPadding;
    private Integer mTagTextVerticalPadding;
    //
    private int mCacheMode;
    private int mMaxTags;
    private boolean mUsePreCache = false;
    private TextView mCurrentSelected;
    private boolean mHasMeasured = false;
    private int mLastWidthMeasureSpec;
    private int mLastHeightMeasureSpec;
    private int mLastMeasureWidth;
    private int mLstMeasureHeight;

    public interface OnSelectChangeListener {
        void onSelectChange(TextView child, int index, boolean isSelected);
    }

    public interface OnItemClickListener {
        void onItemClick(TextView child, int index);
    }

    private OnSelectChangeListener onSelectChangeListener;
    private OnItemClickListener onItemClickListener;

    public void setOnSelectChangeListener(OnSelectChangeListener onSelectChangeListener) {
        this.onSelectChangeListener = onSelectChangeListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TagLayout(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagLayout);
        if (a != null) {
            mHorizontalSpace = a.getDimensionPixelOffset(R.styleable.TagLayout_horizontalSpace, 0);
            mVerticalSpace = a.getDimensionPixelOffset(R.styleable.TagLayout_verticalSpace, 0);
            maxLines = a.getInteger(R.styleable.TagLayout_maxLines, Integer.MAX_VALUE);
            mUsePreCache = a.getBoolean(R.styleable.TagLayout_preCache, false);
            mMaximumSelectionCount = a.getInteger(R.styleable.TagLayout_maximumSelectionCount, Integer.MAX_VALUE);
            mCacheMode = a.getInt(R.styleable.TagLayout_cacheMode, CACHE_MODE_AUTO);
            mMaxTags = a.getInteger(R.styleable.TagLayout_maxLines, Integer.MAX_VALUE);
            if (a.hasValue(R.styleable.TagLayout_tagResId)) {
                mTagResId = a.getResourceId(R.styleable.TagLayout_tagResId, -1);
            }
            if (a.hasValue(R.styleable.TagLayout_tagTextSize)) {
                mTagTextSize = a.getDimensionPixelSize(R.styleable.TagLayout_tagTextSize, 0);
            }
            if (a.hasValue(R.styleable.TagLayout_tagBackground)) {
                mTagBackground = a.getResourceId(R.styleable.TagLayout_tagBackground, -1);
            }
            if (a.hasValue(R.styleable.TagLayout_tagTextColor)) {
                mTagTextColor = a.getColorStateList(R.styleable.TagLayout_tagTextColor);
            }
            if (a.hasValue(R.styleable.TagLayout_tagTextHorizontalPadding)) {
                mTagTextHorizontalPadding = a.getDimensionPixelOffset(R.styleable.TagLayout_tagTextHorizontalPadding, 0);
            }
            if (a.hasValue(R.styleable.TagLayout_tagTextVerticalPadding)) {
                mTagTextVerticalPadding = a.getDimensionPixelOffset(R.styleable.TagLayout_tagTextVerticalPadding, 0);
            }
            if (a.hasValue(R.styleable.TagLayout_tagSelectMode)) {
                mTagSelectMode = a.getInt(R.styleable.TagLayout_tagSelectMode, SELECT_MODE_NONE);
            }
            if (a.hasValue(R.styleable.TagLayout_tagMinWidth)) {
                mTagMinWidth = a.getDimensionPixelOffset(R.styleable.TagLayout_tagMinWidth, 0);
            }
            a.recycle();
        }
        if (mUsePreCache) {
            int count = Math.min(MAX_PRE_CACHE_COUNT, Math.min(mMaxTags, maxLines * LINES_CACHE_COUNT));
            for (int i = 0; i < count; i++) {
                TextView tagView = addTag(DEFAULT_TEXT);
                tagView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private int selectedChildCount;

    private TextView addTag(final String tagText) {
        TextView addedTag;
        if (mTagResId != null) {
            addedTag = (TextView) mInflater.inflate(mTagResId, null);
        } else {
            addedTag = new TextView(getContext());
            addedTag.setGravity(Gravity.CENTER);
        }
        final TextView tagView = addedTag;
        if (hasValue(mTagBackground)) {
            tagView.setBackgroundResource(mTagBackground);
        }
        if (hasValue(mTagTextSize)) {
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTagTextSize);
        }
        if (hasValue(mTagTextColor)) {
            tagView.setTextColor(mTagTextColor);
        }
        if (hasValue(mTagMinWidth)) {
            tagView.setMinWidth(mTagMinWidth);
        }
        int paddingLeft = tagView.getPaddingLeft();
        int paddingRight = tagView.getPaddingRight();
        int paddingTop = tagView.getPaddingTop();
        int paddingBottom = tagView.getPaddingBottom();

        if (hasValue(mTagTextHorizontalPadding)) {
            paddingLeft = mTagTextHorizontalPadding;
            paddingRight = mTagTextHorizontalPadding;
        }
        if (hasValue(mTagTextVerticalPadding)) {
            paddingTop = mTagTextVerticalPadding;
            paddingBottom = mTagTextVerticalPadding;
        }
        tagView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        tagView.setText(tagText);
        final int position = getChildCount();
        tagView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tagView.getVisibility() != View.VISIBLE)
                    return;
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(tagView, position);
                }
                if (mTagSelectMode == SELECT_MODE_MULTIPLE) {
                    if (selectedChildCount >= mMaximumSelectionCount && !tagView.isSelected()) {
                        return;
                    }
                    tagView.setSelected(!tagView.isSelected());
                    if (tagView.isSelected()) {
                        selectedChildCount++;
                    } else {
                        selectedChildCount--;
                    }
                    if (onSelectChangeListener != null) {
                        onSelectChangeListener.onSelectChange(tagView, position, tagView.isSelected());
                    }
                } else if (mTagSelectMode == SELECT_MODE_SINGLE) {
                    if (mCurrentSelected != null) {
                        mCurrentSelected.setSelected(false);
                    }
                    tagView.setSelected(true);
                    mCurrentSelected = tagView;
                    if (onSelectChangeListener != null) {
                        onSelectChangeListener.onSelectChange(tagView, position, tagView.isSelected());
                    }

                }
            }
        });
        addView(tagView);
        return tagView;
    }

    private boolean hasValue(Object value) {
        return value != null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHasMeasured && mLastWidthMeasureSpec == widthMeasureSpec && mLastHeightMeasureSpec == heightMeasureSpec) {
            setMeasuredDimension(mLastMeasureWidth, mLstMeasureHeight);
            return;
        }
        mTagLines.clear();
        int parentSuggestWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        TagLine tagLine = null;
        int maxWidth = parentSuggestWidth - getPaddingLeft() - getPaddingRight();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            if (tagLine == null) {
                tagLine = newTagLine(maxWidth);
                tagLine.addView(child);
            } else {
                if (tagLine.accept(child)) {
                    tagLine.addView(child);
                } else {
                    if (mTagLines.size() == maxLines) {
                        for (int hiddenIndex = i; hiddenIndex < getChildCount(); hiddenIndex++) {
                            getChildAt(hiddenIndex).setVisibility(View.GONE);
                        }
                        break;
                    }
                    tagLine = newTagLine(maxWidth);
                    tagLine.addView(child);
                }
            }
        }
        int measureHeight = getPaddingTop() + getPaddingBottom();
        for (int i = 0; i < mTagLines.size(); i++) {
            measureHeight += mTagLines.get(i).height;
            if (i != mTagLines.size() - 1) {
                measureHeight += mVerticalSpace;
            }
        }
        setMeasuredDimension(mLastMeasureWidth = parentSuggestWidth, mLstMeasureHeight = resolveSize(measureHeight, heightMeasureSpec));
        mLastWidthMeasureSpec = widthMeasureSpec;
        mLastHeightMeasureSpec = heightMeasureSpec;
        mHasMeasured = true;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putStringArrayList(TAGS, mTags);
        bundle.putIntegerArrayList(SELECTED_TAG_POSITIONS, (ArrayList<Integer>) getSelectedTagPositions());
        return bundle;

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mTags = bundle.getStringArrayList(TAGS);
            List<Integer> selectedTagPositions = bundle.getIntegerArrayList(SELECTED_TAG_POSITIONS);
            if (selectedTagPositions != null && selectedTagPositions.size() > 0) {
                selectTagPositions(selectedTagPositions);
            }
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
        } else {
            super.onRestoreInstanceState(state);
        }

    }

    public void setTags(String... tagsList) {
        setTags(Arrays.asList(tagsList));
    }

    public void setTags(List<String> tagsList) {

        mHasMeasured = false;
        if (this.mTags == tagsList) {
            return;
        }
        if (mCacheMode != CACHE_MODE_NONE) {//使用缓存
            int maxDisplayTags = Math.min(tagsList == null ? 0 : tagsList.size(), mMaxTags);
            int childCount = getChildCount();
            int availableCount = Math.min(maxDisplayTags, childCount);
            if (availableCount > 0) {
                for (int i = 0; i < availableCount; i++) {
                    TextView tagView = (TextView) getChildAt(i);
                    tagView.setVisibility(View.VISIBLE);
                    String tagText = tagsList.get(i);
                    tagView.setText(tagText);
                }
            }

            if (childCount > maxDisplayTags) {
                List<View> removedViews = null;
                for (int i = availableCount; i < childCount; i++) {
                    TextView tagView = (TextView) getChildAt(i);
                    if (mCacheMode == CACHE_MODE_LAZY) {
                        tagView.setVisibility(View.GONE);
                    } else {
                        if (removedViews == null) {
                            removedViews = new ArrayList<>();
                        }
                        removedViews.add(tagView);
                    }
                }
                if (removedViews != null) {
                    for (View child : removedViews) {
                        removeView(child);
                    }
                    removedViews.clear();
                }
            } else if (childCount < maxDisplayTags) {
                int addedCount = maxDisplayTags - childCount;
                for (int i = 0; i < addedCount; i++) {
                    String tagText = tagsList.get(i + childCount);
                    addTag(tagText);
                }
            }


        } else {//删除 再添加
            removeAllViews();
            if (tagsList != null && tagsList.size() > 0) {
                for (int i = 0; i < tagsList.size(); i++) {
                    addTag(tagsList.get(i));
                }
            }
        }

        this.mTags = wrap(tagsList);
    }

    private ArrayList<String> wrap(List<String> tagsList) {
        if (tagsList instanceof ArrayList) {
            return (ArrayList<String>) tagsList;
        } else {
            return new ArrayList<>(tagsList);
        }
    }

    public void selectTagPositions(Integer... selectedPos) {
        selectTagPositions(Arrays.asList(selectedPos));
    }

    public void selectTagPositions(List<Integer> selectedPos) {
        for (int i = 0; i < selectedPos.size(); i++) {
            selectTagPosition(selectedPos.get(i));
        }
        selectedChildCount = selectedPos.size();
    }

    public void selectTagPosition(int index) {
        getChildAt(index).setSelected(true);
        selectedChildCount = 1;
    }

    public void setMaximumSelectionCount(int maximumSelectionCount) {
        this.mMaximumSelectionCount = maximumSelectionCount;
    }

    public List<Integer> getSelectedTagPositions() {
        List<Integer> selectedList = new ArrayList<>();
        if (mTagSelectMode == SELECT_MODE_SINGLE && mCurrentSelected != null) {
            selectedList.add(indexOfChild(mCurrentSelected));
        } else {
            for (int i = 0; i < getChildCount(); i++) {
                if (getChildAt(i).getVisibility() == View.VISIBLE && getChildAt(i).isSelected()) {
                    selectedList.add(i);
                }
            }
        }
        return selectedList;
    }

    private TagLine newTagLine(int maxWidth) {
        TagLine tagLine = new TagLine(maxWidth, mHorizontalSpace);
        mTagLines.add(tagLine);
        return tagLine;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int marginTop = getPaddingTop();
        int marginLeft = getPaddingLeft();
        for (int i = 0; i < mTagLines.size(); i++) {
            TagLine line = mTagLines.get(i);
            line.layout(marginLeft, marginTop);
            marginTop += mVerticalSpace + line.height;
        }
    }


    public class TagLine {
        private List<View> mTagViews = new ArrayList<>();
        private int totalWidth;
        private int space;
        private int usedWidth;
        private int height;

        public TagLine(int totalWidth, int horizontalSpace) {
            this.totalWidth = totalWidth;
            this.space = horizontalSpace;
        }

        public void addView(View child) {
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (mTagViews.size() == 0) {
                if (childWidth > totalWidth) {
                    usedWidth = totalWidth;
                    height = childHeight;
                } else {
                    usedWidth = childWidth;
                    height = childHeight;
                }
            } else {
                usedWidth = usedWidth + space + childWidth;
                height = (childHeight > height) ? childHeight : height;
            }
            mTagViews.add(child);
        }

        public boolean accept(View child) {
            int width = child.getMeasuredWidth();
            if (mTagViews.size() == 0) {
                return true;
            }
            if (usedWidth + width + space > totalWidth) {
                return false;
            }
            return true;
        }

        public void layout(int marginLeft, int marginTop) {
            for (int i = 0; i < mTagViews.size(); i++) {
                View child = mTagViews.get(i);
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                int extraTop = (int) ((height - childHeight) / 2f + 0.5f);
                int left = marginLeft;
                int top = marginTop + extraTop;
                int right = left + childWidth;
                int bottom = top + childHeight;
                child.layout(left, top, right, bottom);
                marginLeft += childWidth + space;
            }
        }
    }
}
