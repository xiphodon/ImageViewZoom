package com.qiyun.cyt.imageviewzoom;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //图片数组
    int[] images = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e};

    //默认显示图片
    int currentImg = 0;

    //图片的初始透明度
    private int alpha = 255;
    private ImageView image1;
    private ImageView image2;
    private Button plus;
    private Button minus;
    private Button next;
    int size = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示下一张图片
                image1.setImageResource(images[++currentImg % images.length]);
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                if (view == plus) {
                    alpha += 20;
                }
                if (view == minus) {
                    alpha -= 20;
                }
                if (alpha <= 0) {
                    alpha = 0;
                }
                if (alpha >= 255) {
                    alpha = 255;
                }
                image1.setImageAlpha(alpha);
            }
        };

        plus.setOnClickListener(listener);
        minus.setOnClickListener(listener);

        image1.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) image1.getDrawable();
                //获取成位图
                final Bitmap bitmap = bitmapDrawable.getBitmap();

                //获取bitmap与drawable对应的比例
                final double scaleX = 1.0 * bitmap.getWidth() / image1.getWidth();
                final double scaleY = 1.0 * bitmap.getHeight() / image1.getHeight();

                //获得位图在Drawable图中对应的触摸点
                int x = (int) (motionEvent.getX() * scaleX);
                int y = (int) (motionEvent.getY() * scaleY);

                if (x + size / 2 > bitmap.getWidth()) {
                    x = bitmap.getWidth() - size / 2;
                }
                if (y + size / 2 > bitmap.getHeight()) {
                    y = bitmap.getHeight() - size / 2;
                }
                if (x - size / 2 < 0) {
                    x = size / 2;
                }
                if (y - size / 2 < 0) {
                    y = size / 2;
                }

                //创建相应位图显示在指定区域
                image2.setImageBitmap(Bitmap.createBitmap(bitmap, x - size / 2, y - size / 2, size / 2, size / 2));
                image2.setImageAlpha(alpha);

                return false;
            }
        });
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        next = (Button) findViewById(R.id.next);
    }
}
