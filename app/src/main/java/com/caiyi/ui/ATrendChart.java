package com.caiyi.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Picture;
import android.graphics.Rect;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.caiyi.interfaces.ITrendChart;
import com.lottery9188.Activity.R;

public abstract class ATrendChart implements ITrendChart {
    private static final boolean DEBUG = false;
    private static final String TAG = "ASixPartTrendChart";
    protected int mBottomMargin;
    protected int mCApCount;
    protected int mCAvgYilou;
    protected int mCAvgYilouBg;
    protected int mCBallBlue;
    protected int mCBallRed;
    protected int mCBallSelectedBlue;
    protected int mCBallSelectedRed;
    protected int mCBallSelectedStroke;
    protected int mCDiv;
    protected int mCEvenY;
    protected int mCLianchu;
    protected int mCLianchuBg;
    protected int mCMaxYilou;
    protected int mCOddContent;
    protected int mCOddY;
    protected int mCQihaoText;
    protected int mCTextSize;
    protected int mCXTitleBg;
    protected int mCXbottomTextBlue;
    protected int mCXbottomTextRed;
    protected int mCYText;
    protected int mCYilou;
    protected boolean mCanScale = true;
    protected boolean[] mCanScroll = new boolean[]{false, false};
    protected int mDefBallSize;
    final float mDefaultMaxScale = 2.0f;
    final float mDefaultMinScale = 0.1f;
    protected int mDivHeight;
    protected int mDivWidth;
    final float mHalfSize = 0.5f;
    protected boolean mInitOk = false;
    protected int mLcTextSize;
    protected Paint mPaint = new Paint(1);
    protected final Picture mPicContent = new Picture();
    protected final Picture mPicLeftBottom = new Picture();
    protected final Picture mPicLeftTop = new Picture();
    protected final Picture mPicXBottom = new Picture();
    protected final Picture mPicXTop = new Picture();
    protected final Picture mPicY = new Picture();
    protected Rect mRect = new Rect();
    final int mScaleCount = 4;
    protected float[] mScaleRange = new float[]{0.1f, 2.0f};
    final int mScaleRangeMaxY = 3;
    final int mScaleRangeMinY = 2;
    protected int[] mScrollRange = new int[4];
    final int mStatCount = 4;
    private int mTimeHeight;
    protected LottoTrendView mTrendView;
    protected int mXItemHeight;
    protected int mXItemNameWidth;
    protected int mXItemWidth;
    protected int mXTextSize;
    protected int mYItemHeight;
    protected int mYItemWidth;
    protected int mYTextSize;
    protected TextPaint tp = new TextPaint(1);

    protected abstract void drawContent();

    protected abstract void drawLeftBottom();

    protected abstract void drawLeftTop();

    protected abstract void drawXBottom();

    protected abstract void drawXTop();

    protected abstract void drawY();

    protected abstract CharSequence getKuaiPingLeftTime();

    public ATrendChart(Context context, LottoTrendView lottoTrendView) {
        this.mTrendView = lottoTrendView;
        LottoTrendView.setHardwareAccelerated(lottoTrendView, false);
        Resources resources = context.getResources();
        this.mCYText = resources.getColor(R.color.lottery_title_color);
        this.mCOddY = resources.getColor(R.color.trend_list_odd);
        this.mCEvenY = resources.getColor(R.color.trend_list_even);
        this.mCOddContent = resources.getColor(R.color.trend_line_odd_bg);
        this.mCBallRed = resources.getColor(R.color.trend_ball_red);
        this.mCBallBlue = resources.getColor(R.color.trend_ball_blue);
        this.mCBallSelectedRed = resources.getColor(R.color.trend_x_ball_red);
        this.mCBallSelectedBlue = resources.getColor(R.color.trend_x_ball_blue);
        this.mCBallSelectedStroke = resources.getColor(R.color.trend_x_ball_stroke);
        this.mCXbottomTextRed = resources.getColor(R.color.trend_x_red_text);
        this.mCXbottomTextBlue = resources.getColor(R.color.trend_x_blue_text);
        this.mCApCount = resources.getColor(R.color.trend_max_count_color);
        this.mCAvgYilou = resources.getColor(R.color.trend_avg_yilou_color);
        this.mCAvgYilouBg = resources.getColor(R.color.trend_avg_yilou_bg);
        this.mCMaxYilou = resources.getColor(R.color.trend_max_yilou_color);
        this.mCLianchu = resources.getColor(R.color.trend_max_lianchu_color);
        this.mCLianchuBg = resources.getColor(R.color.trend_max_lianchu_bg);
        this.mCDiv = resources.getColor(R.color.trend_divider);
        this.mCXTitleBg = resources.getColor(R.color.trend_xtitle_bg);
        this.mCQihaoText = resources.getColor(R.color.trend_qihao_text);
        this.mCYilou = resources.getColor(R.color.trend_content_yilou);
        this.mXItemWidth = resources.getDimensionPixelSize(R.dimen.trend_x_item_width);
        this.mXItemNameWidth = resources.getDimensionPixelSize(R.dimen.trend_x_item_name_width);
        this.mXItemHeight = resources.getDimensionPixelSize(R.dimen.trend_x_item_height);
        this.mYItemWidth = resources.getDimensionPixelSize(R.dimen.trend_y_item_width);
        this.mYItemHeight = resources.getDimensionPixelSize(R.dimen.trend_y_item_height);
        this.mXTextSize = resources.getDimensionPixelSize(R.dimen.trend_x_text_size);
        this.mYTextSize = resources.getDimensionPixelSize(R.dimen.trend_y_text_size);
        this.mCTextSize = resources.getDimensionPixelSize(R.dimen.trend_content_text_size);
        this.mLcTextSize = resources.getDimensionPixelSize(R.dimen.trend_corner_text_size);
        this.mDefBallSize = resources.getDimensionPixelSize(R.dimen.trend_ball_radius);
        this.mDivWidth = resources.getDimensionPixelSize(R.dimen.trend_sulcus_width);
        this.mDivHeight = resources.getDimensionPixelSize(R.dimen.trend_sulcus_height);
        this.mBottomMargin = resources.getDimensionPixelSize(R.dimen.trend_sulcus_bottom);
        this.mTimeHeight = resources.getDimensionPixelSize(R.dimen.trend_time_height);
    }

    public void initChart(Context context, int i, int i2, float f) {
        if (i != 0 && i2 != 0) {
            System.currentTimeMillis();
            drawXTop();
            drawLeftTop();
            drawY();
            drawXBottom();
            drawContent();
            drawLeftBottom();
            this.mInitOk = true;
        }
    }

    public void setInitOk(boolean z) {
        this.mInitOk = z;
    }

    public void draw(Canvas canvas, int i, int i2, int i3, int i4, float f) {
        if (i3 > 0 && i4 > 0 && initOk()) {
            CharSequence kuaiPingLeftTime = getKuaiPingLeftTime();
            int width = (int) (((float) this.mPicY.getWidth()) * f);
            int height = (int) (((float) this.mPicY.getHeight()) * f);
            int width2 = (int) (((float) this.mPicXTop.getWidth()) * f);
            int height2 = (int) (((float) this.mPicXTop.getHeight()) * f);
            int width3 = (int) (((float) this.mPicXBottom.getWidth()) * f);
            int height3 = (int) (((float) this.mPicXBottom.getHeight()) * f);
            int width4 = (int) (((float) this.mPicContent.getWidth()) * f);
            int height4 = (int) (((float) this.mPicContent.getHeight()) * f);
            canvas.save();
            this.mRect.set(width, height2, i3, i4 - height3);
            canvas.clipRect(this.mRect);
            this.mRect.set(i, i2, width4 + i, height4 + i2);
            canvas.drawPicture(this.mPicContent, this.mRect);
            canvas.restore();
            this.mRect.set(0, i2, width, height + i2);
            canvas.drawPicture(this.mPicY, this.mRect);
            this.mRect.set(i, 0, width2 + i, height2);
            canvas.drawPicture(this.mPicXTop, this.mRect);
            this.mRect.set(i, i4 - height3, width3 + i, i4);
            canvas.drawPicture(this.mPicXBottom, this.mRect);
            this.mRect.set(0, 0, width, height2);
            canvas.drawPicture(this.mPicLeftTop, this.mRect);
            this.mRect.set(0, i4 - height3, width, i4);
            canvas.drawPicture(this.mPicLeftBottom, this.mRect);
            if (kuaiPingLeftTime != null) {
                this.mRect.set(0, (i4 - height3) - ((int) (((float) this.mTimeHeight) * f)), i3, i4 - height3);
                drawKuaiPingTime(canvas, this.mRect, f);
            }
        }
    }

    private void drawKuaiPingTime(Canvas canvas, Rect rect, float f) {
        this.mPaint.setColor(-1);
        this.mPaint.setStyle(Style.FILL);
        canvas.drawRect(rect, this.mPaint);
        this.mPaint.setColor(this.mCDiv);
        canvas.drawLine(0.0f, (float) (rect.top + 1), (float) rect.right, (float) (rect.top + 1), this.mPaint);
        this.mPaint.setColor(this.mCYText);
        this.mPaint.setTextSize(((float) this.mCTextSize) * f);
        drawSpanText2Rect(getKuaiPingLeftTime(), canvas, rect, this.mPaint);
    }

    /**
     * @param f
     * @param i
     * @param i2
     */
    public void reCalcScroll(float f, int i, int i2) {
        if (this.mPicY != null && this.mPicXTop != null && this.mPicXBottom != null) {
            int width = this.mPicY.getWidth();
            int height = this.mPicY.getHeight();
            int width2 = this.mPicXTop.getWidth();
            int height2 = this.mPicXTop.getHeight();
            int height3 = this.mPicXBottom.getHeight();
            Object obj = getKuaiPingLeftTime() != null ? 1 : null;
            this.mCanScroll[1] = ((float) ((height2 + height3) + height)) * f > ((float) i2);
            this.mScrollRange[2] = (int) (((float) i2) - (((float) ((obj != null ? this.mTimeHeight : 0) + (height3 + height))) * f));
            this.mScrollRange[3] = (int) (((float) height2) * f);
            float f2 = (((float) i2) * 1.0f) / ((float) ((height2 + height3) + height));
            if (f2 > this.mScaleRange[0]) {
                this.mScaleRange[0] = f2;
            }
            this.mCanScroll[0] = ((float) (width + width2)) * f > ((float) i);
            this.mScrollRange[0] = (int) (((float) i) - (((float) width2) * f));
            this.mScrollRange[1] = (int) (((float) width) * f);
            f2 = (((float) i) * 1.0f) / ((float) (width + width2));
            if (f2 > this.mScaleRange[0]) {
                this.mScaleRange[0] = f2;
            }
        }
    }

    public boolean[] getCanScroll() {
        return this.mCanScroll;
    }

    public int[] getScrollRange() {
        return this.mScrollRange;
    }

    public float[] getScaleRange() {
        return this.mScaleRange;
    }

    public boolean getCanScale() {
        return this.mCanScale;
    }

    public boolean onClick(MotionEvent motionEvent, float f, float f2, int i, int i2, float f3) {
        return false;
    }

    public boolean onLongClick(MotionEvent motionEvent, float f, float f2, int i, int i2, float f3) {
        return false;
    }

    protected int dp2px(float f, int i) {
        return (int) ((((float) i) * f) + 0.5f);
    }

    protected void drawText2Rect(String str, Canvas canvas, Rect rect, Paint paint) {
        if (!TextUtils.isEmpty(str)) {
            FontMetrics fontMetrics = paint.getFontMetrics();
            canvas.drawText(str, (float) rect.centerX(), (((float) rect.top) + (((((float) (rect.bottom - rect.top)) - fontMetrics.bottom) + fontMetrics.top) / 2.0f)) - fontMetrics.top, paint);
        }
    }

    protected void drawSpanText2Rect(CharSequence charSequence, Canvas canvas, Rect rect, Paint paint) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (charSequence instanceof String) {
                drawText2Rect(charSequence.toString(), canvas, rect, paint);
                return;
            }
            this.tp.setTextSize(paint.getTextSize());
            StaticLayout staticLayout = new StaticLayout(charSequence, this.tp, rect.width(), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            FontMetrics fontMetrics = paint.getFontMetrics();
            float centerX = ((float) rect.centerX()) - (paint.measureText(charSequence, 0, charSequence.length()) / 2.0f);
            float f = ((fontMetrics.top + (((float) (rect.bottom - rect.top)) - fontMetrics.bottom)) / 2.0f) + ((float) rect.top);
            canvas.save();
            canvas.translate(centerX, f);
            staticLayout.draw(canvas);
            canvas.restore();
        }
    }

    protected void drawBitmap2Rect(Bitmap bitmap, Canvas canvas, Rect rect, Paint paint) {
        canvas.drawBitmap(bitmap, (float) (rect.centerX() - (bitmap.getWidth() / 2)), (float) (rect.centerY() - (bitmap.getHeight() / 2)), paint);
    }

    protected void drawBall2Rect(int i, Canvas canvas, Rect rect, float f, Paint paint) {
        if (i < 0) {
            paint.setStyle(Style.STROKE);
            paint.setColor(-i);
        } else {
            paint.setStyle(Style.FILL);
            paint.setColor(i);
        }
        canvas.drawCircle(rect.exactCenterX(), (float) rect.centerY(), f, paint);
    }

    public boolean initOk() {
        return this.mInitOk;
    }
}
