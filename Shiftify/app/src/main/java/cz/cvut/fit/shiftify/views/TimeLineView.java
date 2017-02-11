package cz.cvut.fit.shiftify.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.Utilities;
import cz.cvut.fit.shiftify.data.WorkDay;
import cz.cvut.fit.shiftify.data.models.Shift;

/**
 * View class for showing time intervals during day
 * <p>
 * Default colors:
 * - Free intervals - Color.WHITE
 * - Shift intervals - Color.RED
 * <p>
 * Example:
 * <p>
 * in xml:
 * <cz.cvut.fit.shiftify.views.TimeLineView
 * android:id="@+id/super_genial_id"
 * android:layout_width="match_parent"
 * android:layout_height="5dp"
 * app:colorFree="@color/colorTimeLineFree"
 * app:colorShift="@color/colorTimeLineShift" />
 * <p>
 * In code:
 * TimeLineView timeline = (TimeLineView) findViewById(R.id.super_genial_id);
 * timeline.addInterval(8 * 3600,16 * 3600);
 */
public class TimeLineView extends View {

    private List<Pair<Integer, Integer>> mIntervals;
    private Paint mPaint;
    private static final int SECONDS_PER_DAY = 86400;
    private int mColorFree = Color.WHITE;
    private int mColorShift = Color.RED;

    public TimeLineView(Context context) {
        super(context);
        init(context, null);
    }

    public TimeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * Init and set default variables
     *
     * @param context context of activity
     * @param attrs   Attributes date xml
     */
    private void init(Context context, AttributeSet attrs) {
        mIntervals = new ArrayList<>();
        mPaint = new Paint();

        if (attrs != null) {
            TypedArray array = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.TimeLineView, 0, 0);
            try {
                mColorShift = array.getColor(R.styleable.TimeLineView_colorShift, Color.RED);
                mColorFree = array.getColor(R.styleable.TimeLineView_colorFree, Color.WHITE);
            } finally {
                array.recycle();
            }
        }
    }

    /**
     * Add interval into timeline view
     *
     * @param startSecond  Time when interval start
     * @param finishSecond Time when interval finish
     */
    public void addInterval(int startSecond, int finishSecond) {
        mIntervals.add(new Pair(startSecond, finishSecond));
    }

    /**
     * Add intervals from WorkDay's shifts
     *
     * @param wDay WorkDay
     */
    public void addIntervalsFromWorkDay(WorkDay wDay) {
        if (wDay.hasShifts()) {
            for (Shift shift : wDay.getShifts()) {
                int toSec = SECONDS_PER_DAY - 1;
                if (shift.getToInSeconds() != 0){
                    toSec = shift.getToInSeconds();
                }
                addInterval(shift.getFromInSeconds(), toSec);
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int top = 0;
        int bottom = getHeight();
        mPaint.setColor(mColorFree);
        canvas.drawRect(0, top, getWidth(), bottom, mPaint);

        mPaint.setColor(mColorShift);
        for (Pair<Integer, Integer> p : mIntervals) {
            int start = getPosition(p.first);
            int finish = getPosition(p.second);
            canvas.drawRect(start, top, finish, bottom, mPaint);
        }
    }


    /**
     * Get horizontal position of seconds count
     *
     * @param posSeconds Seconds
     * @return position ( for drawRect....)
     */
    private int getPosition(int posSeconds) {
        if (posSeconds == 0)
            return 0;
        return (int) (((float) getWidth() / SECONDS_PER_DAY) * posSeconds);
    }

    public int getmColorFree() {
        return mColorFree;
    }

    public void setmColorFree(int mColorFree) {
        this.mColorFree = mColorFree;
    }

    public int getmColorShift() {
        return mColorShift;
    }

    public void setmColorShift(int mColorShift) {
        this.mColorShift = mColorShift;
    }
}
