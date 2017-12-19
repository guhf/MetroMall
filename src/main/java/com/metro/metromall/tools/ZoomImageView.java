package com.metro.metromall.tools;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

/**
 * 自定义可缩放的图片
 * Created by guhf on 2017/12/12.
 */

public class ZoomImageView extends ImageView implements OnScaleGestureListener,
        OnTouchListener, OnGlobalLayoutListener {
        private static final String TAG = ZoomImageView.class.getName();

        /**
         * 最大的比例
         */
        private static final float SCALE_MAX = 4.0f;

        private static final float SCALE_MID = 2.0f;

        /**
         * 初始化时的缩放比例，如果图片的宽和高，此值将小于0
         */
        private float initScale = 1.0f;

        /**
         * 用于存放矩阵的9个值
         */
        private final float[] matrixValues = new float[9];

        private boolean once = true;

        /**
         * 缩放的手势检测
         */
        private ScaleGestureDetector mScaleGestureDetector = null;

        private GestureDetector mGestureDetector;

        /**
         * 定义3*3的矩阵
         */
        private final Matrix mScaleMatrix = new Matrix();

        public ZoomImageView(Context context) {
            super(context, null);
            super.setScaleType(ImageView.ScaleType.MATRIX);
            mScaleGestureDetector = new ScaleGestureDetector(context, this);
            mGestureDetector = new GestureDetector(context, mSimpleOnGestureListener);

            mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
            super.setScaleType(ScaleType.MATRIX);
            this.setOnTouchListener(this);
        }

        public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            super.setScaleType(ImageView.ScaleType.MATRIX);
            mScaleGestureDetector = new ScaleGestureDetector(context, this);
            mGestureDetector = new GestureDetector(context, mSimpleOnGestureListener);

            mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
            super.setScaleType(ScaleType.MATRIX);
            this.setOnTouchListener(this);
        }

        private boolean isAutoScale = false;
        /**
         * 双击图片变大，或者变小
         */
        private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale) {
                    return true;
                }

                float x = e.getX();
                float y = e.getY();

                if (getScale() < SCALE_MID) {
                    ZoomImageView.this.postDelayed(
                            new AutoScaleRunnable(SCALE_MID, x, y), 16);
                    isAutoScale = true;
                } else {
                    ZoomImageView.this.postDelayed(
                            new AutoScaleRunnable(initScale, x, y), 16);
                    isAutoScale = true;
                }

                return true;
            }
        };

        private class AutoScaleRunnable implements Runnable {
            private static final float BIGGER = 1.07f;
            private static final float SMALLER = 0.93f;
            private float mTargetScale;
            private float tmpScale;

            //缩放中心
            private float x;
            private float y;

            //传入目标缩放值，根据目标值和当前值，判断应该放大还是缩小
            public AutoScaleRunnable(float targetScale, float x, float y) {
                this.mTargetScale = targetScale;
                this.x = x;
                this.y = y;

                if (getScale() < mTargetScale) {
                    tmpScale = BIGGER;
                } else {
                    tmpScale = SMALLER;
                }
            }

            @Override
            public void run() {
                //进行缩放
                mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
                checkBorderAndCenterScale();
                setImageMatrix(mScaleMatrix);

                final float currentScale = getScale();
                //如果在合法的范围内继续缩放
                if (((tmpScale > 1f) && (currentScale < mTargetScale)) ||
                        ((tmpScale < 1f) && (mTargetScale < currentScale))) {
                    ZoomImageView.this.postDelayed(this, 16);
                } else { //设置目标的缩放比例
                    final float deltaScale = mTargetScale / currentScale;
                    mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
                    checkBorderAndCenterScale();
                    setImageMatrix(mScaleMatrix);
                    isAutoScale = false;
                }
            }
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scale = getScale();
            float scaleFactor = detector.getScaleFactor();    // 返回前一个伸缩事件至当前伸缩事件的比率

            if (getDrawable() == null) {
                return true;
            }

            /**
             * 缩放范围的控制
             */
            if ((scale < SCALE_MAX && scaleFactor > 1.0f) || (scale > initScale && scaleFactor < 1.0f)) {
                /**
                 * 最大值最小值判断
                 */
                if (scaleFactor * scale < initScale) {
                    scaleFactor = initScale / scale;
                }
                if (scaleFactor * scale > SCALE_MAX) {
                    scaleFactor = SCALE_MAX / scale;
                }
                /**
                 * 设置缩放比例
                 */
            /*mScaleMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mScaleMatrix);*/

                //单纯的设置缩放中心
                mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                checkBorderAndCenterScale();
                setImageMatrix(mScaleMatrix);
            }

            return true;
        }

        /**
         * 获得当前的缩放比例
         *
         * @return
         */
        private final float getScale() {
            mScaleMatrix.getValues(matrixValues);
            return matrixValues[Matrix.MSCALE_X];
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

        }

        private int lastPointerCount;
        private float mLastX, mLastY;
        private boolean isCanDrag;
        private float mTouchSlop = 0;
        private boolean isCheckLeftAndRight;
        private boolean isCheckTopAndBottom;

        /**
         * 我们让OnTouchListener的MotionEvent交给ScaleGestureDetector进行处理
         */
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            //2017.12.4 增加是图片自由的移动
            if (mGestureDetector.onTouchEvent(motionEvent)) return true;

            mScaleGestureDetector.onTouchEvent(motionEvent);
            float x = 0, y = 0;
            //拿到触摸点的个数
            final int pointerCount = motionEvent.getPointerCount();
            //得到多个触摸点的X与Y的均值
            for (int i = 0; i < pointerCount; i++) {
                x += motionEvent.getX(i);
                y += motionEvent.getY(i);
            }
            x = x / pointerCount;
            y = y / pointerCount;

            //每当触摸点发生变化时,重置mLastX,mLastY
            if (pointerCount != lastPointerCount) {
                isCanDrag = false;
                mLastX = x;
                mLastY = y;
            }

            lastPointerCount = pointerCount;
            RectF rectF = getMatrixRectF();

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //使用分发机制
                    if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }

                    float dx = x - mLastX;
                    float dy = y - mLastY;

                    if (!isCanDrag) {
                        isCanDrag = isCanDrag(dx, dy);
                    }

                    if (isCanDrag) {
                        if (getDrawable() != null) {
                            //当图片到达边界时，把事件在传给ViewPager
                            if (getMatrixRectF().left == 0 && dx > 0) {
                                getParent().requestDisallowInterceptTouchEvent(false);
                            }

                            if (getMatrixRectF().right == getWidth() && dx < 0) {
                                getParent().requestDisallowInterceptTouchEvent(false);
                            }

                            isCheckLeftAndRight = isCheckTopAndBottom = true;
                            // 如果宽度小于屏幕宽度，则禁止左右移动
                            if (rectF.width() < getWidth()) {
                                dx = 0;
                                isCheckLeftAndRight = false;
                            }
                            // 如果高度小雨屏幕高度，则禁止上下移动
                            if (rectF.height() < getHeight()) {
                                dy = 0;
                                isCheckTopAndBottom = false;
                            }
                            mScaleMatrix.postTranslate(dx, dy);
                            checkMatrixBounds();
                            setImageMatrix(mScaleMatrix);
                        }
                    }
                    mLastX = x;
                    mLastY = y;
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    Log.e(TAG, "ACTION_UP");
                    lastPointerCount = 0;
                    break;
            }

            return true;
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

        @Override
        public void onGlobalLayout() {
            if (once) {
                Drawable drawable = getDrawable();

                if (drawable == null) return;
                Log.e(TAG, drawable.getIntrinsicWidth() + " , " + drawable.getIntrinsicHeight());
                int width = getWidth();
                int height = getHeight();
                //拿到图片的宽和高
                int dw = drawable.getIntrinsicWidth();
                int dh = drawable.getIntrinsicHeight();
                float scale = 1.0f;
                //如果图片的宽或高大于屏幕，则缩放至屏幕的宽或高
                if (dw > width && dh <= height) {
                    scale = width * 1.0f / dw;
                }
                if (dh > height && dw <= width) {
                    scale = height * 1.0f / dh;
                }
                //如果宽和高都大于屏幕，则让其按比例适应屏幕大小
                if (dw > width && dh > height) {
                    scale = Math.min(dw * 1.0f / width, dh * 1.0f / height);
                }

                initScale = scale;
                //图片移动至屏幕中心
                mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
                mScaleMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
                setImageMatrix(mScaleMatrix);
                once = false;
            }
        }

        /**
         * 在缩放时，进行图片显示范围的控制
         */
        private void checkBorderAndCenterScale() {
            RectF rectF = getMatrixRectF();
            float deltaX = 0;
            float deltaY = 0;

            int width = getWidth();
            int height = getHeight();

            //如果宽或高大于屏幕，则控制范围
            if (rectF.width() > width) {
                if (rectF.left > 0) {
                    deltaX = -rectF.left;
                }
                if (rectF.right < width) {
                    deltaX = width - rectF.right;
                }
            }
            if (rectF.height() > height) {
                if (rectF.top > 0) {
                    deltaY = -rectF.top;
                }
                if (rectF.bottom < height) {
                    deltaY = height - rectF.bottom;
                }
            }
            //如果宽或高小于屏幕，则让其居中
            if (rectF.width() < width) {
                deltaX = width * 0.5f - rectF.right + rectF.width() * 0.5f;
            }
            if (rectF.height() < height) {
                deltaY = height * 0.5f - rectF.bottom + 0.5f * rectF.height();
            }
            Log.e(TAG, "deltaX = " + deltaX + " , deltaY = " + deltaY);
            mScaleMatrix.postTranslate(deltaX, deltaY);
        }

        /**
         * 根据当前图片的Matrix获得图片的范围
         */
        private RectF getMatrixRectF() {
            Matrix matrix = mScaleMatrix;
            RectF rectF = new RectF();
            Drawable drawable = getDrawable();
            if (null != drawable) {
                rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                matrix.mapRect(rectF);
            }
            return rectF;
        }

        /**
         * 移动时，进行边界判断，主要判断宽或高大于屏幕的
         */
        private void checkMatrixBounds() {
            RectF rect = getMatrixRectF();

            float deltaX = 0, deltaY = 0;
            final float viewWidth = getWidth();
            final float viewHeight = getHeight();
            // 判断移动或缩放后，图片显示是否超出屏幕边界
            if (rect.top > 0 && isCheckTopAndBottom) {
                deltaY = -rect.top;
            }
            if (rect.bottom < viewHeight && isCheckTopAndBottom) {
                deltaY = viewHeight - rect.bottom;
            }
            if (rect.left > 0 && isCheckLeftAndRight) {
                deltaX = -rect.left;
            }
            if (rect.right < viewWidth && isCheckLeftAndRight) {
                deltaX = viewWidth - rect.right;
            }
            mScaleMatrix.postTranslate(deltaX, deltaY);
        }

        /**
         * 判断是否是推动行动
         */
        private boolean isCanDrag(float dx, float dy) {
            return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
        }
}
