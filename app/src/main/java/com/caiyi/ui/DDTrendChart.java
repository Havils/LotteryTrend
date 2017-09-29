package com.caiyi.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;

import com.caiyi.data.TrendData;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 */
public class DDTrendChart extends ATrendChart {
    private static final boolean DEBUG = false;
    private static final String TAG = "DDTrendChart";
    private int blueCount = 16;
    final int mDefDLTBlueCount = 12;
    final int mDefDLTRedCount = 35;
    final int mDefSSQBlueCount = 16;
    final int mDefSSQRedCount = 33;
    private boolean mDrawLine = true;
    private String mLotteryType;
    final int mMaxSignleNum = 9;
    private Path mPathPoint = new Path();
    private TreeSet<Integer> mSelectedBlue = new TreeSet();
    private ISelectedChangeListener mSelectedChangeListener;
    private TreeSet<Integer> mSelectedRed = new TreeSet();
    private boolean mShowYilou = true;
    private ArrayList<TrendData> mTrendData;
    private int redCount = 33;

    public interface ISelectedChangeListener {
        void onSelectedChange(TreeSet<Integer> treeSet, TreeSet<Integer> treeSet2);
    }

    public DDTrendChart(Context context, LottoTrendView lottoTrendView) {
        super(context, lottoTrendView);
        this.mPaint.setTextAlign(Align.CENTER);
    }

    /**
     * @param str 种类（大乐透或者双色球）
     * @param arrayList 数据
     */
    public void updateData(String str, ArrayList<TrendData> arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            if ("01".equals(str) || "50".equals(str)) {
                this.mLotteryType = str;
            } else {
                this.mLotteryType = "01";
            }
            this.mTrendData = arrayList;
            this.mSelectedRed.clear();
            this.mSelectedBlue.clear();
            this.mPathPoint.reset();
            this.mScaleRange = new float[]{0.0f, 2.0f};
            if ("01".equals(this.mLotteryType)) {
                this.redCount = 33;
                this.blueCount = 16;
                for (int i = 0; i < arrayList.size(); i++) {
                    TrendData trendData = (TrendData) arrayList.get(i);
                    if ("row".equals(trendData.getType())) {
                        String[] split = trendData.getBlue().split(",");
                        for (int i2 = 0; i2 < split.length; i2++) {
                            if (split[i2].equals("0")) {
                                float f = (((((float) this.redCount) + 0.5f) + ((float) i2)) * ((float) this.mXItemWidth)) + ((float) this.mDivWidth);
                                float f2 = (((float) i) + 0.5f) * ((float) this.mXItemHeight);
                                if (i == 0) {
                                    this.mPathPoint.moveTo(f, f2);
                                } else {
                                    this.mPathPoint.lineTo(f, f2);
                                }
                            }
                        }
                    }
                }
            } else if ("50".equals(this.mLotteryType)) {
                this.redCount = 35;
                this.blueCount = 12;
            }
            if (this.mTrendView != null) {
                initChart(this.mTrendView.getContext(), this.mTrendView.getWidth(), this.mTrendView.getHeight(), this.mTrendView.getScale());
                this.mTrendView.invalidate();
            }
            if (this.mSelectedChangeListener != null) {
                this.mSelectedChangeListener.onSelectedChange(this.mSelectedRed, this.mSelectedBlue);
            }
        }
    }

    public void initChart(Context context, int i, int i2, float f) {
        if (i != 0 && i2 != 0 && this.mTrendData != null && this.mTrendData.size() >= 4) {
            super.initChart(context, i, i2, f);
            if (this.mTrendView != null) {
                this.mTrendView.setNowY((float) (-this.mPicY.getHeight()));
            }
        }
    }

    public void setSelectedChangeListener(ISelectedChangeListener iSelectedChangeListener) {
        this.mSelectedChangeListener = iSelectedChangeListener;
    }

    /**画线
     * @param z
     */
    public void setDrawLine(boolean z) {
        if ((this.mDrawLine != z ? 1 : null) != null) {
            this.mDrawLine = z;
            if (this.mTrendData != null) {
                drawContent();
            }
            if (this.mTrendView != null) {
                this.mTrendView.invalidate();
            }
        }
    }

    /**遗漏
     * @param z
     */
    public void setShowYilou(boolean z) {
        if ((this.mShowYilou != z ? 1 : null) != null) {
            this.mShowYilou = z;
            if (this.mTrendData != null) {
                drawContent();
            }
            if (this.mTrendView != null) {
                this.mTrendView.invalidate();
            }
        }
    }


    public boolean onClick(MotionEvent motionEvent, float f, float f2, int i, int i2, float f3) {
        if (motionEvent.getY() <= ((float) i2) - (((float) this.mXItemHeight) * f3) || motionEvent.getX() <= ((float) this.mYItemWidth) * f3) {
            return false;
        }
        int x = (int) ((motionEvent.getX() - f) / (((float) this.mXItemWidth) * f3));
        if (x >= this.redCount) {
            x = ((int) (((motionEvent.getX() - f) - ((float) this.mDivWidth)) / (((float) this.mXItemWidth) * f3))) - this.redCount;
            if (this.mSelectedBlue.contains(Integer.valueOf(x))) {
                this.mSelectedBlue.remove(Integer.valueOf(x));
            } else {
                this.mSelectedBlue.add(Integer.valueOf(x));
            }
            if (this.mSelectedChangeListener != null) {
                this.mSelectedChangeListener.onSelectedChange(this.mSelectedRed, this.mSelectedBlue);
            }
        } else {
            if (this.mSelectedRed.contains(Integer.valueOf(x))) {
                this.mSelectedRed.remove(Integer.valueOf(x));
            } else {
                this.mSelectedRed.add(Integer.valueOf(x));
            }
            if (this.mSelectedChangeListener != null) {
                this.mSelectedChangeListener.onSelectedChange(this.mSelectedRed, this.mSelectedBlue);
            }
        }
        drawXBottom();
        return true;
    }

    /**
     * 画y轴
     */
    public void drawY() {
        if (this.mTrendData != null && this.mTrendData.size() >= 4) {
            Canvas beginRecording = this.mPicY.beginRecording(this.mYItemWidth, (this.mYItemHeight * this.mTrendData.size()) + this.mDivHeight);
            this.mPaint.setStyle(Style.FILL);
            int size = this.mTrendData.size();
            for (int i = 0; i < size; i++) {
                int i2 = i * this.mYItemHeight;
                if (i == size - 4) {
                    this.mRect.set(0, this.mYItemHeight * i, this.mYItemWidth, (this.mYItemHeight * i) + this.mDivHeight);
                    this.mPaint.setColor(-1);
                    beginRecording.drawRect(this.mRect, this.mPaint);
                    this.mPaint.setColor(this.mCDiv);
                    beginRecording.drawLine(0.0f, (float) i2, (float) this.mYItemWidth, (float) i2, this.mPaint);
                    beginRecording.drawLine(0.0f, (float) ((this.mDivHeight + i2) - 1), (float) this.mYItemWidth, (float) ((this.mDivHeight + i2) - 1), this.mPaint);
                    this.mRect.set(0, this.mDivHeight + i2, this.mYItemWidth, (this.mYItemHeight + i2) + this.mDivHeight);
                } else if (i > size - 4) {
                    this.mRect.set(0, this.mDivHeight + i2, this.mYItemWidth, (this.mYItemHeight + i2) + this.mDivHeight);
                } else {
                    this.mRect.set(0, i2, this.mYItemWidth, this.mYItemHeight + i2);
                }
                String type = ((TrendData) this.mTrendData.get(i)).getType();
                if (type.equals("row")) {
                    type = ((TrendData) this.mTrendData.get(i)).getPid();
                    if (i % 2 == 0) {
                        this.mPaint.setColor(this.mCOddY);
                    } else {
                        this.mPaint.setColor(this.mCEvenY);
                    }
                    beginRecording.drawRect(this.mRect, this.mPaint);
                    this.mPaint.setColor(this.mCYText);
                } else if (type.equals("dis")) {
                    type = "出现次数";
                    this.mPaint.setColor(-1);
                    beginRecording.drawRect(this.mRect, this.mPaint);
                    this.mPaint.setColor(this.mCApCount);
                } else if (type.equals("avg")) {
                    type = "平均遗漏";
                    this.mPaint.setColor(this.mCAvgYilouBg);
                    beginRecording.drawRect(this.mRect, this.mPaint);
                    this.mPaint.setColor(this.mCAvgYilou);
                } else if (type.equals("mmv")) {
                    type = "最大遗漏";
                    this.mPaint.setColor(-1);
                    beginRecording.drawRect(this.mRect, this.mPaint);
                    this.mPaint.setColor(this.mCMaxYilou);
                } else if (type.equals("mlv")) {
                    type = "最大连出";
                    this.mPaint.setColor(this.mCLianchuBg);
                    beginRecording.drawRect(this.mRect, this.mPaint);
                    this.mPaint.setColor(this.mCLianchu);
                } else {
                    type = "??";
                }
                this.mPaint.setTextSize((float) this.mYTextSize);
                drawText2Rect(type, beginRecording, this.mRect, this.mPaint);
            }
            this.mPicY.endRecording();
        }
    }

    /**
     * 左下角
     */
    public void drawLeftBottom() {
        int i = this.mXItemHeight + this.mBottomMargin;
        Canvas beginRecording = this.mPicLeftBottom.beginRecording(this.mYItemWidth, i);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(-1);
        this.mRect.set(0, 0, this.mYItemWidth, i);
        beginRecording.drawRect(this.mRect, this.mPaint);
        this.mPaint.setColor(this.mCDiv);
        beginRecording.drawLine(0.0f, 2.0f, (float) this.mYItemWidth, 2.0f, this.mPaint);
        this.mPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mPaint.setTextSize((float) this.mLcTextSize);
        this.mRect.set(0, this.mBottomMargin, this.mYItemWidth, i);
        drawText2Rect("预选区", beginRecording, this.mRect, this.mPaint);
        this.mPicLeftBottom.endRecording();
    }

    /**
     * 画左上角
     */
    public void drawLeftTop() {
        Canvas beginRecording = this.mPicLeftTop.beginRecording(this.mYItemWidth, this.mXItemHeight);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(this.mCQihaoText);
        this.mRect.set(0, 0, this.mYItemWidth, this.mXItemHeight);
        beginRecording.drawRect(this.mRect, this.mPaint);
        this.mPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mPaint.setTextSize((float) this.mLcTextSize);
        drawText2Rect("期号", beginRecording, this.mRect, this.mPaint);
        this.mPicLeftTop.endRecording();
    }

    /**
     * 画上边
     */
    public void drawXTop() {
        int i;
        int i2 = this.mDivWidth + (this.mXItemWidth * (this.redCount + this.blueCount));
        int i3 = this.mXItemHeight;
        Canvas beginRecording = this.mPicXTop.beginRecording(i2, i3);
        this.mRect.set(0, 0, i2, i3);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(this.mCXTitleBg);
        beginRecording.drawRect(this.mRect, this.mPaint);
        this.mPaint.setColor(-1);
        this.mRect.set(this.redCount * this.mXItemWidth, 0, (this.redCount * this.mXItemWidth) + this.mDivWidth, i3);
        beginRecording.drawRect(this.mRect, this.mPaint);
        this.mPaint.setTextSize((float) this.mLcTextSize);
        for (i = 1; i <= this.redCount; i++) {
            String str;
            int i4 = i * this.mXItemWidth;
            this.mPaint.setColor(this.mCDiv);
            beginRecording.drawLine((float) i4, 0.0f, (float) i4, (float) i3, this.mPaint);
            this.mRect.set(i4 - this.mXItemWidth, 0, i4, i3);
            this.mPaint.setColor(-1);
            if (i <= 9) {
                str = "0" + i;
            } else {
                str = "" + i;
            }
            drawText2Rect(str, beginRecording, this.mRect, this.mPaint);
        }
        for (i = 0; i < this.blueCount; i++) {
            String str;
            int i4 = ((this.redCount + i) * this.mXItemWidth) + this.mDivWidth;
            beginRecording.drawLine((float) i4, 0.0f, (float) i4, (float) i3, this.mPaint);
            this.mRect.set(i4, 0, this.mXItemWidth + i4, i3);
            this.mPaint.setColor(-1);
            if (i < 9) {
                str = "0" + (i + 1);
            } else {
                str = "" + (i + 1);
            }
            drawText2Rect(str, beginRecording, this.mRect, this.mPaint);
        }
        this.mPicXTop.endRecording();
    }

    /**
     * 画下面
     */
    public void drawXBottom() {
        int i = 1;
        int i2 = (this.mXItemWidth * (this.redCount + this.blueCount)) + this.mDivWidth;
        int i3 = this.mXItemHeight + this.mBottomMargin;
        Canvas beginRecording = this.mPicXBottom.beginRecording(i2, i3);
        this.mRect.set(0, 0, i2, i3);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(-1);
        beginRecording.drawRect(this.mRect, this.mPaint);
        this.mPaint.setColor(this.mCDiv);
        beginRecording.drawLine(0.0f, 2.0f, (float) i2, 2.0f, this.mPaint);
        this.mPaint.setTextSize((float) this.mXTextSize);
        for (int i4 = 1; i4 <= this.redCount; i4++) {
            String str;
            this.mRect.set((i4 - 1) * this.mXItemWidth, this.mBottomMargin, this.mXItemWidth * i4, i3);
            if (this.mSelectedRed.contains(Integer.valueOf(i4 - 1))) {
                this.mPaint.setStyle(Style.FILL);
                this.mPaint.setColor(this.mCBallSelectedRed);
                beginRecording.drawCircle(this.mRect.exactCenterX(), (float) this.mRect.centerY(), (float) this.mDefBallSize, this.mPaint);
                this.mPaint.setColor(-1);
            } else {
                this.mPaint.setStyle(Style.STROKE);
                this.mPaint.setColor(this.mCBallSelectedStroke);
                beginRecording.drawCircle(this.mRect.exactCenterX(), (float) this.mRect.centerY(), (float) this.mDefBallSize, this.mPaint);
                this.mPaint.setColor(this.mCXbottomTextRed);
                this.mPaint.setStyle(Style.FILL);
            }
            if (i4 <= 9) {
                str = "0" + i4;
            } else {
                str = "" + i4;
            }
            drawText2Rect(str, beginRecording, this.mRect, this.mPaint);
        }
        while (i <= this.blueCount) {
            String str2;
            this.mRect.set((((this.redCount + i) - 1) * this.mXItemWidth) + this.mDivWidth, this.mBottomMargin, ((this.redCount + i) * this.mXItemWidth) + this.mDivWidth, i3);
            if (this.mSelectedBlue.contains(Integer.valueOf(i - 1))) {
                this.mPaint.setStyle(Style.FILL);
                this.mPaint.setColor(this.mCBallSelectedBlue);
                beginRecording.drawCircle(this.mRect.exactCenterX(), (float) this.mRect.centerY(), (float) this.mDefBallSize, this.mPaint);
                this.mPaint.setColor(-1);
            } else {
                this.mPaint.setStyle(Style.STROKE);
                this.mPaint.setColor(this.mCBallSelectedStroke);
                beginRecording.drawCircle(this.mRect.exactCenterX(), (float) this.mRect.centerY(), (float) this.mDefBallSize, this.mPaint);
                this.mPaint.setColor(this.mCXbottomTextBlue);
                this.mPaint.setStyle(Style.FILL);
            }
            if (i <= 9) {
                str2 = "0" + i;
            } else {
                str2 = "" + i;
            }
            drawText2Rect(str2, beginRecording, this.mRect, this.mPaint);
            i++;
        }
        this.mPicXBottom.endRecording();
    }

    /**
     * 画球
     */
    public void drawContent() {
        int i;
        int i2 = (this.mXItemWidth * (this.redCount + this.blueCount)) + this.mDivWidth;
        Canvas beginRecording = this.mPicContent.beginRecording(i2, (this.mYItemHeight * this.mTrendData.size()) + this.mDivHeight);
        this.mPaint.setTextSize((float) this.mCTextSize);
        this.mPaint.setStyle(Style.FILL);
        int i3 = this.redCount + this.blueCount;
        int size = this.mTrendData.size();
        for (i = 0; i <= size; i++) {
            int i4 = i * this.mXItemHeight;
            if (i != size) {
                if (i < size - 4) {
                    this.mRect.set(0, i4, i2, this.mXItemHeight + i4);
                    if (i % 2 == 0) {
                        this.mPaint.setColor(-1);
                    } else {
                        this.mPaint.setColor(this.mCOddContent);
                    }
                    beginRecording.drawRect(this.mRect, this.mPaint);
                    this.mPaint.setColor(this.mCDiv);
                    beginRecording.drawLine(0.0f, (float) i4, (float) i2, (float) i4, this.mPaint);
                } else {
                    this.mRect.set(0, this.mDivHeight + i4, i2, (i4 + this.mDivHeight) + this.mXItemHeight);
                    if (i == (size - 4) + 1) {
                        this.mPaint.setColor(this.mCAvgYilouBg);
                    } else if (i == size - 1) {
                        this.mPaint.setColor(this.mCLianchuBg);
                    } else {
                        this.mPaint.setColor(-1);
                    }
                    beginRecording.drawRect(this.mRect, this.mPaint);
                }
            }
        }
        int size2 = this.mTrendData.size() * this.mXItemWidth;
        for (i = 0; i <= i3; i++) {
            int i5 = i * this.mXItemWidth;
            if (i == this.redCount) {
                this.mPaint.setColor(-1);
                this.mRect.set(i5, 0, this.mDivWidth + i5, size2);
                beginRecording.drawRect(this.mRect, this.mPaint);
                this.mPaint.setColor(this.mCDiv);
                beginRecording.drawLine((float) i5, 0.0f, (float) i5, (float) size2, this.mPaint);
                beginRecording.drawLine((float) (this.mDivWidth + i5), 0.0f, (float) (this.mDivWidth + i5), (float) size2, this.mPaint);
            } else if (i < this.redCount) {
                this.mPaint.setColor(this.mCDiv);
                beginRecording.drawLine((float) i5, 0.0f, (float) i5, (float) size2, this.mPaint);
            } else {
                this.mPaint.setColor(this.mCDiv);
                beginRecording.drawLine((float) (this.mDivWidth + i5), 0.0f, (float) (this.mDivWidth + i5), (float) size2, this.mPaint);
            }
        }
        i = (size - 4) * this.mXItemHeight;
        this.mPaint.setColor(-1);
        this.mRect.set(0, i, i2, this.mDivHeight + i);
        beginRecording.drawRect(this.mRect, this.mPaint);
        this.mPaint.setColor(this.mCDiv);
        beginRecording.drawLine(0.0f, (float) i, (float) i2, (float) i, this.mPaint);
        beginRecording.drawLine(0.0f, (float) ((this.mDivHeight + i) - 1), (float) i2, (float) ((this.mDivHeight + i) - 1), this.mPaint);
        if ("01".equals(this.mLotteryType) && this.mDrawLine) {
            this.mPaint.setStyle(Style.STROKE);
            this.mPaint.setColor(this.mCBallBlue);
            beginRecording.drawPath(this.mPathPoint, this.mPaint);
            this.mPaint.setStyle(Style.FILL);
        }
        i = this.mTrendData.size();
        this.mPaint.setStyle(Style.FILL);
        for (int i6 = 0; i6 < i; i6++) {
            int i7;
            String str;
            TrendData trendData = (TrendData) this.mTrendData.get(i6);
            String[] split = trendData.getRed().split(",");
            String[] split2 = trendData.getBlue().split(",");
            int i8 = this.mXItemHeight * i6;
            if (i6 >= i - 4) {
                i8 += this.mDivHeight;
            }
            for (i7 = 0; i7 < split.length; i7++) {
                this.mRect.set(this.mXItemWidth * i7, i8, (i7 + 1) * this.mXItemWidth, this.mXItemHeight + i8);
                if (!"row".equals(trendData.getType())) {
                    this.mPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
                    drawText2Rect(split[i7], beginRecording, this.mRect, this.mPaint);
                } else if (split[i7].equals("0")) {
                    this.mPaint.setColor(this.mCBallRed);
                    beginRecording.drawCircle(this.mRect.exactCenterX(), (float) this.mRect.centerY(), (float) this.mDefBallSize, this.mPaint);
                    this.mPaint.setColor(-1);
                    if (i7 < 9) {
                        str = "0" + (i7 + 1);
                    } else {
                        str = "" + (i7 + 1);
                    }
                    drawText2Rect(str, beginRecording, this.mRect, this.mPaint);
                } else if (this.mShowYilou) {
                    this.mPaint.setColor(this.mCYilou);
                    drawText2Rect(split[i7], beginRecording, this.mRect, this.mPaint);
                }
            }
            for (i7 = 0; i7 < split2.length; i7++) {
                this.mRect.set(((split.length + i7) * this.mXItemWidth) + this.mDivWidth, i8, (((split.length + i7) + 1) * this.mXItemWidth) + this.mDivWidth, this.mXItemHeight + i8);
                if (!"row".equals(trendData.getType())) {
                    this.mPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
                    drawText2Rect(split2[i7], beginRecording, this.mRect, this.mPaint);
                } else if (split2[i7].equals("0")) {
                    this.mPaint.setColor(this.mCBallBlue);
                    beginRecording.drawCircle(this.mRect.exactCenterX(), (float) this.mRect.centerY(), (float) this.mDefBallSize, this.mPaint);
                    this.mPaint.setColor(-1);
                    if (i7 < 9) {
                        str = "0" + (i7 + 1);
                    } else {
                        str = "" + (i7 + 1);
                    }
                    drawText2Rect(str, beginRecording, this.mRect, this.mPaint);
                } else if (this.mShowYilou) {
                    this.mPaint.setColor(this.mCYilou);
                    drawText2Rect(split2[i7], beginRecording, this.mRect, this.mPaint);
                }
            }
        }
        this.mPicContent.endRecording();
    }

    protected CharSequence getKuaiPingLeftTime() {
        return null;
    }
}
