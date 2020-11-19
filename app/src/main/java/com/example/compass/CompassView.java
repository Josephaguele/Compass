package com.example.compass;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CompassView extends View
{
    /*These three are default constructors that must be because we extend the View superclass*/
    public CompassView(Context context)
    {
        super(context);
    }

    public CompassView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CompassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        // This allows a user using a D-pad to select and focus the compass
        // - this will allow them to receive accessibility events from the view
        setFocusable(true);

        // reads the bearing from XML attribute
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CompassView, defStyleAttr, 0);
        if(a.hasValue(R.styleable.CompassView_bearing))
        {
            setBearing(a.getFloat(R.styleable.CompassView_bearing,0));
        }

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // The compass is a circle that fills as much space as possible.
        //Set the measured dimensions by figuring out the shortest boundary,
        // height or width

        int measuredWidth = measure(widthMeasureSpec);
        int measuredHeight = measure(heightMeasureSpec);

        int d = Math.min(measuredWidth, measuredHeight);

        setMeasuredDimension(d,d);
    }

    private int measure(int measureSpec)
    {
        int result = 0;

        // Decode the measurement specifications.
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED)
        {
            // Return a deafault size of 200 if no bounds are specified.
            result = 200;
        } else {
            // As you want to fill the available space
            // always return the full available bounds.
            result = specSize;
        }
        return result;
    }

    private float mBearing; // new property to store the displayed bearing

    public void setBearing (float bearing) // setter method for displayed bearing
    {
        mBearing = bearing;
        invalidate(); // invalidate is called to ensure that the View is
        // repainted when the bearing changes.
    }

    public float getBearing() // getter method for displayed bearing
    {
        return mBearing;
    }
}
