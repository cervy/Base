

public class ZoomImgV extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {


    //--------------自由缩放,边界控制
    boolean mOnce = false;

    /**
     * 初始化时图片缩放的值,也是缩小的极限
     */
    private float mInitScale;
    /**
     * 双击放大时图片放大的值
     */
    private float mMidScale;
    /**
     * 放大的极限
     */
    private float mMaxScale;

    /**
     * 通过矩阵实现缩放
     */
    private Matrix mScaleMatrix;

    /**
     * 通过系统处理多点触控的类实现多点触控
     */
    private ScaleGestureDetector mScaleGestureDetector;

    //------------自由移动,边界控制

    /**
     * 记录上一次多点触控的数量
     */
    private int mLastPonterCoutn;

    private float mLastX;
    private float mLastY;

    private int mTouchSlop;

    private boolean isCanDrag;
    /**
     * 判断移动的时候是否进行边界检查
     */
    private boolean isCheckLeftAndrRight;
    private boolean isCheckTopAndButtom;

    //---------------双击放大或缩小,边界控制
    private GestureDetector mGrstureDector;

    //判断若正在缩放,忽略再次双击
    private boolean isAutoScale;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
     
        setOnTouchListener(this);
        //对角线的标准值
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //双击缩放:使用线程让图片有节奏缩放
        mGrstureDector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            //实现即时缩放
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                //得到当前点击的xy坐标
//                float x=e.getX();
//                float y=e.getY();
//                if (getScale()<mMidScale){
//                    mScaleMatrix.postScale(mMidScale/getScale(),mMidScale/getScale(),x,y);
//                    setImageMatrix(mScaleMatrix);
//                }else {
//                    mScaleMatrix.postScale(mInitScale/getScale(),mInitScale/getScale(),x,y);
//                    setImageMatrix(mScaleMatrix);
//                }
            //有节奏缩放
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                if (isAutoScale) {
                    return true;
                }
                //得到当前点击的xy坐标
                float x = e.getX();
                float y = e.getY();
                if (getScale() < mMidScale) {
                    postDelayed(new AutoScaleRunnable(mMidScale, x, y), 16);
                    isAutoScale = true;
                } else {
                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                    isAutoScale = true;
                }
                return true;
            }
        });
    }

   
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {

        //获取ImageView加载完成后的图片

        if (!mOnce) {
            //得到控件的宽和高
            int width = getWidth();
            int height = getHeight();
            //得到图片以及宽和高
            Drawable d = getDrawable();
            if (d == null) {
                return;
            }
            float scale = 1.0f;//缩放比例
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            /**
             * 如果图片的宽大于控件的宽,但是高度小于控件的高度,将其缩小
             */
            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }
            /**
             * 如果图片的宽小于控件的宽,但是高度大于控件的高度,将其缩小
             */
            if (dh > height && dw < width) {
                scale = height * 1.0f / dh;
            }
            /**
             * 如果图片的宽和高都大于或小于控件的宽和高将其缩小或放大
             */
            if ((dw > width && dh > height) || (dw < width && dh < height)) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 2;

            //图片缩放完成后将图片移动至控件的中心:控件二分之一的宽度或高度减去图片二分之一的宽度高度
            int dx = getWidth() / 2 - dw / 2;
            int dy = getHeight() / 2 - dh / 2;

            /**
             * 通过Matrix实现缩放逻辑
             */
            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }

    }

    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 缩放区间:mInitScale-mMaxScale
     
     */
    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {

        float scale = getScale();
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        if (getDrawable() == null) {
            return true;
        }
        //scaleFactor>1.0f 想要放大,反之想要缩小
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {

            //如果缩放的结果比我们最小的要小,要重置为mInitScale
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;

            }
            if (scale * scaleFactor > mMaxScale) {
                scale = mMaxScale / scale;
            }
            //实现缩放,双指中心的缩放
            mScaleMatrix.postScale(scaleFactor, scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            //检测图片边缘
            checkBorderAndCenterWhenScale();

            setImageMatrix(mScaleMatrix);
        }

        return true;
    }

    /**
     * 在缩放的同时进行边界控制和位置控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        //缩放完成后进行缩放,防止出现白边
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.top;
            }
        }
        //如果宽度或者高度小于控件的宽或搞,让其居中
        if (rect.width() < width) {
            deltaX = width / 2f - rect.right + rect.width() / 2f;
        }
        if (rect.height() < height) {
            deltaY = height / 2f - rect.bottom + rect.height() / 2f;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    /**
     * 获得图片缩放后的宽和高,以及左右宽高

     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectf = new RectF();//矩形2x2
        Drawable d = getDrawable();
        if (d != null) {
            rectf.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectf);
        }
        return rectf;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //双击的时候不允许放大或者缩小
        if (mGrstureDector.onTouchEvent(motionEvent)) {
            return true;
        }

        //将手指触摸屏幕的事件传递给系统处理多点触控的类ScaleGestureDetector
        mScaleGestureDetector.onTouchEvent(motionEvent);
        float x = 0;
        float y = 0;
        //拿到多点触控的数量
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += motionEvent.getX();
            y += motionEvent.getY();
        }
        //中心的点的位置是
        x /= pointerCount;
        y /= pointerCount;
        if (mLastPonterCoutn != pointerCount) {
            mLastX = x;
            mLastY = y;
        }
        mLastPonterCoutn = pointerCount;
        RectF rectF = getMatrixRectF();
        switch (motionEvent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (rectF.width() > getWidth() + 0.01f || rectF.height() > getHeight() + 0.01f) {
                    //当图片被放大后请求不被父布局拦截触摸事件
                    if (getParent() instanceof ViewPager) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() + 0.01f || rectF.height() > getHeight() + 0.01f) {
                    //当图片被放大后请求不被父布局拦截触摸事件
                    if (getParent() instanceof ViewPager) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                isCanDrag = false;
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }

                if (isCanDrag) {
                    if (getDrawable() != null) {
                        //默认可以检测
                        isCheckLeftAndrRight = isCheckTopAndButtom = true;
                        //如果宽度小于控件宽度不允许横向移动
                        if (rectF.width() < getWidth()) {
                            isCheckLeftAndrRight = false;
                            dx = 0;
                        }
                        //如果高度小于控件高度不允许纵向移动
                        if (rectF.height() < getHeight()) {
                            isCheckTopAndButtom = false;
                            dy = 0;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorderWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }

                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPonterCoutn = 0;
                break;

        }
        return true;
    }

    /**
     * 当图片移动的时候进行边界检查
     */
    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float delatY = 0;
        int width = getWidth();
        int height = getHeight();
        if (rectF.top > 0 && isCheckTopAndButtom) {
            delatY = -rectF.top;

        }
        if (rectF.bottom < height && isCheckTopAndButtom) {
            delatY = height - rectF.bottom;
        }
        if (rectF.left > 0 && isCheckLeftAndrRight) {
            deltaX = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndrRight) {
            deltaX = width - rectF.right;
        }
        mScaleMatrix.postTranslate(deltaX, delatY);
    }

    private boolean isMoveAction(float dx, float dy) {

        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }

    /**
     * 开启线程实现有节奏缩放
     */
    private class AutoScaleRunnable implements Runnable {

        //单次缩放比例
        private final float BIGGER = 1.07f;
        private final float SMALLER = 0.93f;
        /**
         * 缩放的目标值
         */
        private float mTargetScale;
        //缩放的中心点
        private float x;
        private float y;
        private float tmpScale;

        public AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;

            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;//想放大
            }
            //不用else预防两者=
            if (getScale() > mTargetScale) {
                tmpScale = SMALLER;//想缩小
            }

        }

        @Override
        public void run() {
            //实现单次缩放
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
            float currentScale = getScale();
            if ((tmpScale > 1.0f && currentScale < mTargetScale) || (tmpScale < 1.0f && currentScale > mTargetScale)) {
                postDelayed(this, 16);
            } else {
                //设置为我们的目标值
                float scale = mTargetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;

            }
        }
    }
}
