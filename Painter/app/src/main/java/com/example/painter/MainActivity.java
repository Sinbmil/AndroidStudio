package com.example.painter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button line, square, circle, curveline, eraser, clear, emboss, blur;
    MyView view;
    boolean use_emboss, use_blur;
    int line_color = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Painter");

        line = (Button) findViewById(R.id.line);
        square = (Button) findViewById(R.id.square);
        circle = (Button) findViewById(R.id.circle);
        curveline = (Button) findViewById(R.id.curveline);
        eraser = (Button) findViewById(R.id.eraser);
        clear = (Button) findViewById(R.id.clear);
        emboss = (Button) findViewById(R.id.emboss);
        blur = (Button) findViewById(R.id.blur);

        view = (MyView) findViewById(R.id.view);

        // 선택된 버튼 글자 빨간색으로 변경
        ((Button) findViewById(view.getType())).setTextColor(Color.RED);
    }

    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.line:
            case R.id.square:
            case R.id.circle:
            case R.id.curveline:
            case R.id.eraser:
                // 누른 버튼만 빨간색으로 변경하고 나머지는 검정색으로 변경
                ((Button) findViewById(R.id.line)).setTextColor(Color.BLACK);
                ((Button) findViewById(R.id.square)).setTextColor(Color.BLACK);
                ((Button) findViewById(R.id.circle)).setTextColor(Color.BLACK);
                ((Button) findViewById(R.id.curveline)).setTextColor(Color.BLACK);
                ((Button) findViewById(R.id.eraser)).setTextColor(Color.BLACK);
                ((Button) v).setTextColor(Color.RED);
                view.setType(v.getId());
                return;
            case R.id.clear:
                view.clear();
                return;
            case R.id.emboss:
                use_emboss = !use_emboss;
                if (use_emboss)
                    use_blur = false;   // 도드라짐과 흐리게 둘 중 하나만 사용해야 되서
                view.filter(use_emboss, use_blur);
                break;
            case R.id.blur:
                use_blur = !use_blur;
                if (use_blur)
                    use_emboss = false; // 도드라짐과 흐리게 둘 중 하나만 사용해야 되서
                view.filter(use_emboss, use_blur);
                break;
        }

        // 도드라짐과 흐려짐이 선택 될때는 글자가 빨간색으로 변경하고 그렇지 않으면 검정색으로 변경
        emboss.setTextColor(use_emboss ? Color.RED : Color.BLACK);
        blur.setTextColor(use_blur ? Color.RED : Color.BLACK);
    }

    public static class MyView extends View {
        int shape = R.id.line;            // 기본값 line 설정
        int line_width = 5;               // 선 넓이 설정
        int line_color = Color.BLACK;     // 선 색깔
        int eraser_size = 20;             // 지우개 크기
        boolean use_emboss = false;       // 도드라짐 판단 변수
        boolean use_blur = false;         // 흐리게 판단 변수

        Paint paint;
        Bitmap bitmap1, bitmap2;
        Canvas canvas1,canvas2;
        MaskFilter emboss;
        MaskFilter blur;
        Path path;
        float StartX, StartY;
        float OldX, OldY;
        PorterDuffXfermode Mode_XOR;
        PorterDuffXfermode Mode_CLEAR;

        // 초기값 설정
        private void init() {
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(line_width);
            paint.setColor(line_color);
            path = new Path();
            emboss = new EmbossMaskFilter(new float[]{ 1, 1, 1 }, 0.5f, 5, 10);
            blur = new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL);
            Mode_XOR = new PorterDuffXfermode(PorterDuff.Mode.XOR);
            Mode_CLEAR = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        }

        // 버튼 선택했을 때 값을 가져오는 함수
        public int getType() {
            return shape;
        }

        // 값을 지정하는 함수
        public void setType(int type) {
            shape = type;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            bitmap1 = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap2 = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            canvas1 = new Canvas(bitmap1);
            canvas2 = new Canvas(bitmap2);
        }

        // draw 함수
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(bitmap2, 0, 0, null);
            canvas.drawBitmap(bitmap1, 0, 0, null);
        }

        // 화면 지우는 함수
        public void clear() {
            bitmap1.eraseColor(0);
            bitmap2.eraseColor(0);
            invalidate();
        }

        // 도드라짐, 흐리게 필터 함수
        public void filter(boolean isEmboss, boolean isBlur) {
            use_emboss = isEmboss;
            use_blur = isBlur;
        }

        public MyView(Context context) {
            super(context);
            init();
        }

        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        // 터치 이벤트 함수
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    action_Down(x, y);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    action_Move(x, y);
                    return true;
                case MotionEvent.ACTION_UP:
                    action_Up(x, y);
                    return true;
            }
            return super.onTouchEvent(event);
        }

        // 마우스를 처음 눌렀을 때
        private void action_Down(float x, float y) {
            StartX = x;
            StartY = y;
            OldX = x;
            OldY = y;

            switch (shape) {
                case R.id.curveline:
                    path.moveTo(StartX,StartY);
                    break;
            }
        }

        // 마우스를 누르고 움직였을 때
        private void action_Move(float x, float y) {
            paint.setAntiAlias(false);
            paint.setMaskFilter(null);

            switch (shape) {
                case R.id.line: // 선
                    paint.setXfermode(Mode_XOR);
                    canvas1.drawLine(StartX,StartY, OldX, OldY, paint);
                    canvas1.drawLine(StartX,StartY, x, y, paint);
                    OldX = x;
                    OldY = y;
                    break;

                case R.id.square: // 사각형
                    paint.setXfermode(Mode_XOR);
                    canvas1.drawRect(StartX, StartY, OldX, OldY, paint);
                    canvas1.drawRect(StartX, StartY, x, y, paint);
                    OldX = x;
                    OldY = y;
                    break;
                case R.id.circle: // 원
                    paint.setXfermode(Mode_XOR);
                    canvas1.drawOval(new RectF(StartX, StartY, OldX, OldY), paint);
                    canvas1.drawOval(new RectF(StartX, StartY, x, y), paint);
                    OldX = x;
                    OldY = y;
                    break;
                case R.id.curveline: // 곡선
                    paint.setXfermode(null);
                    if (use_emboss)
                        paint.setMaskFilter(emboss);
                    else if (use_blur)
                        paint.setMaskFilter(blur);
                    path.lineTo(x, y);
                    canvas1.drawPath(path, paint);
                    break;
                case R.id.eraser: // 지우개
                    paint.setXfermode(Mode_CLEAR);
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                    canvas2.drawCircle(x, y, eraser_size, paint);
                    paint.setStyle(Paint.Style.STROKE);
                    break;
            }
            invalidate();
        }

        // 마우스를 뗐을 때
        private void action_Up(float x, float y) {
            paint.setAntiAlias(true);
            paint.setMaskFilter(null);
            if (use_emboss)
                paint.setMaskFilter(emboss);
            else if (use_blur)
                paint.setMaskFilter(blur);
            paint.setXfermode(null);

            bitmap1.eraseColor(0);

            switch (shape) {
                case R.id.line:      // 선
                    canvas2.drawLine(StartX, StartY, x, y, paint);
                    break;
                case R.id.square:    // 사각형
                    canvas2.drawRect(StartX, StartY, x, y, paint);
                    break;
                case R.id.circle:    // 원
                    canvas2.drawOval(new RectF(StartX,StartY, x, y), paint);
                    break;
                case R.id.curveline: // 곡선
                    path.lineTo(x, y);
                    canvas2.drawPath(path, paint);
                    path.reset();
                    break;
            }
            invalidate();
        }
    }
}