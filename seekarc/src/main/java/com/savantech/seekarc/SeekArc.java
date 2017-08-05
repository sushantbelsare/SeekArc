package com.savantech.seekarc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Sushant on 12/1/2016.
 */

public class SeekArc extends View {
    int arcWidth,maxRadius,minRadius,startAngle,sweepAngle,arcColor,width,height,progressColor,thumbColor;
    float thumbRadius,progress,maxProgress,progressAngle=0f;
    float[] coordinates;
    public static String CLOCKWISE = "clockwise",ANTICLOCKWISE = "anticlockwise",seekDirection;
    Paint innerPaint,outerPaint,seekPaint,thumbPaint,elevationPaint;
    Canvas tempCanvas;
    Bitmap bitmap = null;
    RectF outerRect = new RectF();
    boolean isAntiClockwise =false,roundCorners = false;
    OnSeekArcChangeListener mListener;

    public SeekArc(Context context) {
        super(context);
        initUI(context,null);
    }

    public SeekArc(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context,attrs);
    }

    public SeekArc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context,attrs);
    }

    public void initUI(Context context, AttributeSet attrs)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.SeekArc);

        arcWidth = array.getDimensionPixelOffset(R.styleable.SeekArc_arcWidth,2);
        startAngle = array.getInteger(R.styleable.SeekArc_startAngle,0);
        sweepAngle = array.getInteger(R.styleable.SeekArc_sweepAngle,360);
        arcColor = array.getColor(R.styleable.SeekArc_arcColor, Color.DKGRAY);
        progress = array.getInteger(R.styleable.SeekArc_progress,0);
        maxProgress = array.getInteger(R.styleable.SeekArc_maxProgress,100);
        progressColor = array.getColor(R.styleable.SeekArc_progressColor, Color.GRAY);
        thumbColor = array.getColor(R.styleable.SeekArc_thumbColor,Color.LTGRAY);
        thumbRadius = array.getDimensionPixelOffset(R.styleable.SeekArc_thumbRadius,8);
        roundCorners = array.getBoolean(R.styleable.SeekArc_roundCorner,false);
        seekDirection = array.getString(R.styleable.SeekArc_seekDirection);

        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        outerPaint = new Paint();
        outerPaint.setAntiAlias(true);
        outerPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        outerPaint.setColor(arcColor);


        seekPaint = new Paint();
        seekPaint.setAntiAlias(true);
        seekPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        seekPaint.setColor(progressColor);

        thumbPaint = new Paint();
        thumbPaint.setAntiAlias(true);
        thumbPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        thumbPaint.setColor(thumbColor);

        elevationPaint = new Paint();
        elevationPaint.setAntiAlias(true);
        elevationPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        elevationPaint.setColor(0x01FF0000);


        seekDirection = seekDirection==null?CLOCKWISE:seekDirection;

        isAntiClockwise = seekDirection.toLowerCase().equals(ANTICLOCKWISE);

        arcWidth = arcWidth>75?75:arcWidth;

        thumbRadius = (arcWidth+8)>=thumbRadius?arcWidth+8:thumbRadius;

        sweepAngle = sweepAngle>360?360:sweepAngle;

        if(progress>100)
            progress%=maxProgress;

        progressAngle = sweepAngle*Math.abs(progress/maxProgress);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int tempHeight,tempWidth;
        tempWidth = getMeasuredWidth();
        tempHeight = getMeasuredHeight();
        width = tempWidth<tempHeight?tempWidth:tempHeight;
        height = tempWidth<tempHeight?tempWidth:tempHeight;
        int padding = Math.round(thumbRadius-(arcWidth/2));
        maxRadius = (width/2)-padding;
        minRadius = maxRadius-arcWidth;

        outerRect.set(padding,padding,maxRadius+(width/2),maxRadius+(height/2));
        setMeasuredDimension(width,height);
        getLayoutParams().height = height;
        getLayoutParams().width = width;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        try {
            bitmap = Bitmap.createBitmap(width,width, Bitmap.Config.ARGB_8888);
            tempCanvas = new Canvas(bitmap);

            tempCanvas.drawArc(outerRect,startAngle,sweepAngle,true,outerPaint);
            tempCanvas.drawArc(outerRect,isAntiClockwise?((startAngle+sweepAngle)%360):startAngle,isAntiClockwise?-progressAngle
                    :progressAngle,true,seekPaint);
            tempCanvas.drawCircle(width/2,height/2,minRadius,innerPaint);

            if(roundCorners)
            {
                coordinates = cartesianCoordinates(maxRadius-(arcWidth/2),sweepAngle);
                if (progressAngle > 0 && isAntiClockwise) {
                    tempCanvas.drawCircle(coordinates[0], coordinates[1], arcWidth / 2, seekPaint);
                }else {
                    if(sweepAngle-progressAngle!=0)
                        tempCanvas.drawCircle(coordinates[0], coordinates[1], arcWidth / 2, (sweepAngle>=360 && progressAngle>0)?
                            seekPaint:outerPaint);
                }

                 coordinates = cartesianCoordinates(maxRadius-(arcWidth/2),0);
                if((sweepAngle-progressAngle)!=0)
                    if(progressAngle==0 || isAntiClockwise)
                        tempCanvas.drawCircle(coordinates[0], coordinates[1], arcWidth / 2, outerPaint);
                    if(!isAntiClockwise)
                        tempCanvas.drawCircle(coordinates[0], coordinates[1], arcWidth / 2, seekPaint);

                if(progressAngle>0) {
                    coordinates = cartesianCoordinates(maxRadius - (arcWidth / 2), isAntiClockwise?(sweepAngle-progressAngle)
                            :progressAngle);
                    tempCanvas.drawCircle(coordinates[0],coordinates[1],arcWidth/2,seekPaint);
                }
            }

            coordinates = cartesianCoordinates(maxRadius - (arcWidth / 2), isAntiClockwise?
                    (sweepAngle-progressAngle):progressAngle);
            tempCanvas.drawCircle(coordinates[0],coordinates[1],thumbRadius,thumbPaint);

            canvas.drawBitmap(bitmap,0,0,null);
        }catch (Exception e)
        {

        }
    }

    public void setArcWidth(int arcWidth)
    {
        this.arcWidth = arcWidth;
        invalidate();
    }
    
    public int getArcWidth()
    {
        return arcWidth;
    }
    
    public void setStartAngle(int startAngle)
    {
        this.startAngle = startAngle;
        invalidate();
    }
    
    public int getStartAngle()
    {
        return startAngle;
    }
    
    public void setSweepAngle(int sweepAngle)
    {
        this.sweepAngle = sweepAngle>360?360:sweepAngle;;
        invalidate();
    }
    
    public int getSweepAngle()
    {
        return sweepAngle;
    }
    
    public void setArcColor(int arcColor)
    {
     outerPaint.setColor(arcColor);
        invalidate();
    }
    
    public void setProgressColor(int progressColor)
    {
        innerPaint.setColor(progressColor);
        invalidate();
    }

    public void setThumbColor(int thumbColor)
    {
        thumbPaint.setColor(thumbColor);
        invalidate();
    }

    public void setThumbRadius(int thumbRadius)
    {
        this.thumbRadius = thumbRadius;
        invalidate();
    }

    public void setProgress(float progress)
    {
        float tempProgress = sweepAngle*(progress/maxProgress);
        progressAngle = tempProgress;
        if(progressAngle<=sweepAngle && progressAngle>=0)
        {
            invalidate();
        }
    }

    public void setMaxProgress(float maxProgress)
    {
        this.maxProgress = maxProgress;
    }

    public void setRoundCorners(boolean roundCorners)
    {
        this.roundCorners = roundCorners;
        invalidate();
    }

    public void setSeekDirection(String direction)
    {
        isAntiClockwise = direction.toLowerCase().equals(ANTICLOCKWISE)?true:false;
        invalidate();
    }

    float[] cartesianCoordinates(float r,float inscribedAngle)
    {
        float angle = (float) ((((startAngle+inscribedAngle)%360))*Math.PI)/180;
        float cxx = (float) ((width/2)+(r*(Math.cos(angle))));
        float cyy = (float) ((height/2)+(r*(Math.sin(angle))));
        return new float[]{cxx,cyy};
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = MotionEventCompat.getActionMasked(event);
        float x = event.getX();
        float y= event.getY();

        float angle = (float) (360+Math.toDegrees(Math.atan2(y-height/2,x-width/2)))%360;

        angle = angle >= startAngle ? angle - startAngle : (360 + angle) - startAngle;

        angle = isAntiClockwise ? sweepAngle - angle : angle;

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    if(isTouchinRange(angle,x,y,false))
                    {
                        if (mListener != null)
                            mListener.onStartTrackingTouch(this);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    x = event.getX();
                    y = event.getY();
                    if(!isTouchinRange(angle,x,y,true)) {
                        return false;
                    }
                    progressAngle = angle;
                    invalidate();
                    if (mListener != null)
                        mListener.onProgressChanged(this,getProgress());
                    break;

                case MotionEvent.ACTION_UP:
                    if(!isTouchinRange(angle,x,y,false)) {
                        if (mListener != null)
                            mListener.onStopTrackingTouch(this);
                      return false;
                    }
                    invalidate();
                    progressAngle = angle;
                    if (mListener != null)
                        mListener.onStopTrackingTouch(this);
                    break;

                default:
                    return true;
            }
            return true;
    }

    boolean isTouchinRange(float progressAngle,float x,float y,boolean isMoving)
    {
        double radius = Math.sqrt(Math.pow(x-(width/2),2)+Math.pow(y-(height/2),2));
        boolean boundary = isMoving ? true && radius >= (0.7*minRadius+10)
                :radius >= (minRadius-thumbRadius-10) && radius <= (maxRadius+thumbRadius+10);
        return (progressAngle <= (sweepAngle) && progressAngle>0 && boundary);
    }

    public float getProgress()
    {
        return maxProgress*(progressAngle/sweepAngle);
    }

    public void setOnSeekArcChangeListener(OnSeekArcChangeListener mListener)
    {
        this.mListener = mListener;
    }

    public interface OnSeekArcChangeListener
    {
        public void onStartTrackingTouch(SeekArc seekArc);
        public void onStopTrackingTouch(SeekArc seekArc);
        public void onProgressChanged(SeekArc seekArc,float progress);
    }

}
