package tn.ppp.gl3.e_learning.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.text.TextPaint;
import android.view.View;

import java.util.Calendar;

import tn.ppp.gl3.e_learning.activities.ExamActivity;

/**
 * Created by S4M37 on 31/05/2016.
 */
public class CircularCountDown extends View {

    private final Paint backgroundPaint;
    private final Paint progressPaint;
    private final Paint textPaint;
    private final Paint textPaint_time;
    private long startTime;
    private long currentTime;
    private final long maxTime;

    private long progressMillisecond;
    private double progress = 1;

    private RectF circleBounds;
    private float radius;
    private float handleRadius;
    private float textHeight;
    private float textOffset;
    private float textHeight_time;
    private float textOffset_time;

    private final Handler viewHandler;
    private final Runnable updateView;
    private Calendar calinit;
    private Calendar calfin;
    private long periode;
    public boolean isExam;
    private Context context;
    private boolean stopped;

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public CircularCountDown(final Context context, final boolean test, float radius,
                             Calendar calinit, Calendar calfin, boolean isExam) {
        super(context);
        this.context = context;
        this.isExam = isExam;
        // used to fit the circle into
        circleBounds = new RectF();
        this.calinit = calinit;
        this.calfin = calfin;
        this.radius = radius;

        // size of circle and handle

        handleRadius = 15;

        // limit the counter to go up to maxTime ms
        this.maxTime = calfin.getTimeInMillis();

        // start and current time
        this.startTime = calinit.getTimeInMillis();

        currentTime = System.currentTimeMillis();

        // the style of the background
        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStrokeWidth(15);
        backgroundPaint.setStrokeCap(Paint.Cap.SQUARE);
        backgroundPaint.setColor(Color.parseColor("#fafafa"));  // grey lighten-5

        // the style of the 'progress'
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(15);
        progressPaint.setStrokeCap(Paint.Cap.SQUARE);
        progressPaint.setColor(Color.parseColor("#ef6c00"));    // orange darken-3

        // the style for the text in the middle
        textPaint = new TextPaint();
        textPaint.setTextSize(radius / 2);
        textPaint.setColor(Color.parseColor("#c62828")); //red darken-3
        textPaint.setTextAlign(Paint.Align.CENTER);


        textPaint_time = new TextPaint();
        textPaint_time.setTextSize(radius / 7);
        textPaint_time.setColor(Color.parseColor("#fff3e0")); // orange lighten-5
        textPaint_time.setTextAlign(Paint.Align.CENTER);


        // text attributes
        textHeight = textPaint.descent() - textPaint.ascent();
        textOffset = (textHeight / 2) - textPaint.descent();

        textHeight_time = textPaint_time.descent() - textPaint_time.ascent();
        textOffset_time = (textHeight_time / 2) - textPaint_time.descent();

        // This will ensure the animation will run periodically
        viewHandler = new Handler();
        updateView = new Runnable() {
            @Override
            public void run() {
                // update current time
                periode = maxTime - startTime;
                currentTime = System.currentTimeMillis();
                // get elapsed time in milliseconds and clamp between <0, maxTime>
                if (currentTime < maxTime) {
                    if (currentTime - startTime < 0) {
                        progressMillisecond = 0;
                        progress = 0;
                    } else {
                        progressMillisecond = currentTime - startTime;
                        // get current progress on a range <0, 1>
                        progress = (double) progressMillisecond / periode;
                    }
                } else {
                    progressMillisecond = 0;
                    progress = 0;
                }
                CircularCountDown.this.invalidate();
                viewHandler.postDelayed(updateView, 1000 / 60);
            }
        };
        viewHandler.post(updateView);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // get the center of the view
        float centerWidth = canvas.getWidth() / 2;
        float centerHeight = canvas.getHeight() / 2;

        // set bound of our circle in the middle of the view
        circleBounds.set(centerWidth - radius,
                centerHeight - radius,
                centerWidth + radius,
                centerHeight + radius);

        // draw background circle
        canvas.drawCircle(centerWidth, centerHeight, radius, backgroundPaint);

        // we want to start at -90°, 0° is pointing to the right
        canvas.drawArc(circleBounds, -90, (float) (progress * 360), false, progressPaint);

        if (progress > 0) {
            // display text inside the circle
            String text_start = "Training Starts : " + fullTime(calinit.get(Calendar.HOUR_OF_DAY)) + ":" + fullTime(calinit.get(Calendar.MINUTE));
            String text_end = "Training ends : " + fullTime(calfin.get(Calendar.HOUR_OF_DAY)) + ":" + fullTime(calfin.get(Calendar.MINUTE));

            canvas.drawText(text_end,
                    centerWidth,
                    centerHeight + textOffset_time + 3 * textHeight_time,
                    textPaint_time);
            int hours = (int) ((periode - progressMillisecond) / 3600000);
            int minutes = (int) (((periode - progressMillisecond) % 3600000) / 60000);
            int seconds = (int) (((periode - progressMillisecond) % (3600000 * 60000)) / 1000) % 60;
            canvas.drawText(fullTime(hours) + ":" + fullTime(minutes) + ":" + fullTime(seconds),
                    centerWidth,
                    centerHeight + textOffset_time,
                    textPaint_time);
            canvas.drawText(text_start,
                    centerWidth,
                    centerHeight + textOffset_time - 3 * textHeight_time,
                    textPaint_time);

            // draw handle or the circle
            canvas.drawCircle((float) (centerWidth + (Math.sin(progress * 2 * Math.PI) * radius)),
                    (float) (centerHeight - (Math.cos(progress * 2 * Math.PI) * radius)),
                    handleRadius,
                    progressPaint);
        } else {
            canvas.drawText("STOP", centerWidth, centerHeight + textOffset, textPaint);
            if (!stopped) {
                final ExamActivity examActivity = ((ExamActivity) context);
                examActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        examActivity.stopTest(CircularCountDown.this.isExam);
                    }
                });
                stopped = true;
            }
        }
    }

    private String fullTime(int c) {
        if (c < 10) {
            return "0" + c;
        } else return "" + c;
    }
}

