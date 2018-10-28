package com.example.guilhermeeyng.projetomobile.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class DiagonalView extends View{

    private Paint paint;
    private Path path;


    public DiagonalView(Context context) {
        this(context, null);
    }
    public DiagonalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w = getWidth();
        float h = getHeight();

        //paint.setStrokeWidth(10F);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(0F, (h/3));
        path.lineTo(0F, h);
        path.lineTo(w, h);
        path.lineTo(w, (h/3)*2);
        path.lineTo(0F, (h/3));
        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        Display display = ((WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        params.height = point.x + 300;
        super.setLayoutParams(params);
    }
}
