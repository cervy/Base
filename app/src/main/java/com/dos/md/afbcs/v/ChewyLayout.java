import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.RelativeLayout;

public class ChewyLayout extends RelativeLayout {

    /**
     * ͷ��Բ
     */
    private Circle mHeaderCircle = new Circle();

    /**
     * β��Բ
     */
    private Circle mFooterCircle = new Circle();

    /**
     * ����
     */
    private Paint mPaint = new Paint();

    /**
     * �����������ߵ�Path����
     */
    private Path mPath = new Path();

    /**
     * Ĭ��ճ������󳤶�
     */
    private float mMaxChewyLength = 100;

    /**
     * ͷ��Բ��Сʱ����С�������С�뾶
     */
    private float mMinHeaderCircleRadius = 4;

    /**
     * �û���ӵ���ͼ�����Բ���ӣ�
     */
    private View mView;

    /**
     * ����ճ���Ƿ�ϵ��ļ�����
     */
    private OnChewyListener mOnChewyListener;

    /**
     * �Ƿ�ճ����
     */
    private boolean isChewy = true;

    /**
     * ճ������ɫ
     */
    private int mColor;

    /**
     * ͷ��Բ�ĵ�ǰ�뾶
     */
    private float mCurrentRadius;

    /**
     * �Ƿ��һ��onSizeChanged
     */
    private boolean isFirst = true;

    /**
     * �Ƿ����ڽ��ж�����
     */
    private boolean isAnim = false;

    /**
     * �Ƿ�������Գ���
     */
    private boolean isDismissed = true;

    /**
     * ��View��ȡ��߶�
     */
    private int mWidth;
    private int mHeight;

    /**
     * ��View�����Ͻ�x��y
     */
    private float mX;
    private float mY;

    /**
     * ��¼���µ�x��y
     */
    float mDownX;
    float mDownY;

    /**
     * ���µ�ʱ�򣬼�¼�˿�β��Բ��Բ��x��y
     */
    float mDownFooterCircleX;
    float mDownFooterCircleY;

    /**
     * ���캯��
     *
     * @param context
     */
    public ChewyLayout(Context context) {
        super(context);
        init();
    }

    /**
     * ���캯��
     *
     * @param context
     * @param attrs
     */
    public ChewyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * ���캯��
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ChewyLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * ��ʼ��
     */
    private void init() {

        // ͸������
        setBackgroundColor(Color.TRANSPARENT);

        // ������ɫ
        mColor = Color.rgb(247, 82, 49);

        // ���û���
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isFirst && w > 0 && h > 0) {
            isFirst = false;
            mView = getChildAt(0);
            mWidth = w;
            mHeight = h;
            mX = getX();
            mY = getY();
            reset();
        }
    }

    /**
     * �������в���
     */
    public void reset() {
        setWidthAndHeight(mWidth, mHeight);
        mCurrentRadius = mHeaderCircle.radius = mFooterCircle.radius = (float) (Math.min(mWidth, mHeight) / 2) - 2;
        mHeaderCircle.x = mFooterCircle.x = mWidth / 2;
        mHeaderCircle.y = mFooterCircle.y = mHeight / 2;
        if (mView != null) {
            mView.setX(0);
            mView.setY(0);
        }
        isAnim = false;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawCircle(mHeaderCircle.x, mHeaderCircle.y, mCurrentRadius, mPaint);
        canvas.drawCircle(mFooterCircle.x, mFooterCircle.y, mFooterCircle.radius, mPaint);
        if (isChewy)
            drawBezier(canvas);
    }

    /**
     * ������������
     * 
     * @param canvas
     */
    private void drawBezier(Canvas canvas) {

        /* �����Ǻ��� */
        float atan = (float) Math.atan((mFooterCircle.y - mHeaderCircle.y) / (mFooterCircle.x - mHeaderCircle.x));
        float sin = (float) Math.sin(atan);
        float cos = (float) Math.cos(atan);

        /* �ĸ��� */
        float headerX1 = mHeaderCircle.x - mCurrentRadius * sin;
        float headerY1 = mHeaderCircle.y + mCurrentRadius * cos;

        float headerX2 = mHeaderCircle.x + mCurrentRadius * sin;
        float headerY2 = mHeaderCircle.y - mCurrentRadius * cos;

        float footerX1 = mFooterCircle.x - mFooterCircle.radius * sin;
        float footerY1 = mFooterCircle.y + mFooterCircle.radius * cos;

        float footerX2 = mFooterCircle.x + mFooterCircle.radius * sin;
        float footerY2 = mFooterCircle.y - mFooterCircle.radius * cos;

        float anchorX = (mHeaderCircle.x + mFooterCircle.x) / 2;
        float anchorY = (mHeaderCircle.y + mFooterCircle.y) / 2;

        /* ������������ */
        mPath.reset();
        mPath.moveTo(headerX1, headerY1);
        mPath.quadTo(anchorX, anchorY, footerX1, footerY1);
        mPath.lineTo(footerX2, footerY2);
        mPath.quadTo(anchorX, anchorY, headerX2, headerY2);
        mPath.lineTo(headerX1, headerY1);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (isAnim)
            return true;

        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            setWidthAndHeight(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mHeaderCircle.x = mX + mHeaderCircle.x;
            mHeaderCircle.y = mY + mHeaderCircle.y;
            mFooterCircle.x = mX + mFooterCircle.x;
            mFooterCircle.y = mY + mFooterCircle.y;
            mDownX = mX + event.getX();
            mDownY = mY + event.getY();
            mDownFooterCircleX = mFooterCircle.x;
            mDownFooterCircleY = mFooterCircle.y;
            if (mView != null) {
                mView.setX(mFooterCircle.x - mWidth / 2);
                mView.setY(mFooterCircle.y - mHeight / 2);
            }
            break;
        case MotionEvent.ACTION_MOVE:
            mFooterCircle.x = mDownFooterCircleX + (event.getX() - mDownX);
            mFooterCircle.y = mDownFooterCircleY + (event.getY() - mDownY);
            if (mView != null) {
                mView.setX(mFooterCircle.x - mWidth / 2);
                mView.setY(mFooterCircle.y - mHeight / 2);
            }
            doAdhere();
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            isAnim = true;
            if (isChewy)
                startAnim();
            else if (mOnChewyListener != null) {
                mFooterCircle.radius = 0;
                mOnChListener.onDismiss();
            }
            break;
        }
        invalidate();
        return true;
    }

    /**
     * ����ճ��Ч���߼�
     */
    private void doAdhere() {
        float distance = (float) Math.sqrt(Math.pow(mFooterCircle.x - mHeaderCircle.x, 2)
                + Math.pow(mFooterCircle.y - mHeaderCircle.y, 2));
        float scale = 1 - distance / mMaxChewyLength;
        mCurrentRadius = Math.max(mHeaderCircle.radius * scale, mMinHeaderCircleRadius);
        if (distance > mMaxChewyLength && isDismissed) {
            isChewy = false;
            mCurrentRadius = 0;
        } else
            isChewy = true;
    }

    /**
     * ��ʼճ������
     */
    private void startAnim() {

        /* x���� */
        ValueAnimator xValueAnimator = ValueAnimator.ofFloat(mFooterCircle.x, mX + mWidth / 2);
        xValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFooterCircle.x = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });

        /* y���� */
        ValueAnimator yValueAnimator = ValueAnimator.ofFloat(mFooterCircle.y, mY + mHeight / 2);
        yValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFooterCircle.y = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });

        /* �û���ӵ���ͼx��y���� */
        ObjectAnimator objectAnimator = null;
        if (mView != null) {
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("X", mFooterCircle.x - mWidth / 2, mX);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("Y", mFooterCircle.y - mHeight / 2, mY);
            objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mView, pvhX, pvhY);
        }

        /* �������� */
        AnimatorSet animSet = new AnimatorSet();
        if (mView != null)
            animSet.play(xValueAnimator).with(yValueAnimator).with(objectAnimator);
        else
            animSet.play(xValueAnimator).with(yValueAnimator);
        animSet.setInterpolator(new BounceInterpolator());
        animSet.setDuration(400);
        animSet.start();
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                reset();
            }
        });

    }

    /**
     * ���ÿ�͸�
     * 
     * @param width
     * @param height
     */
    private void setWidthAndHeight(int width, int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        setLayoutParams(layoutParams);

    }

    /**
     * ����ӿڣ�������ɫ
     *
     * @param color
     */
    public void setColor(int color) {
        mColor = color;
        mPaint.setColor(mColor);
    }

    /**
     * ����ӿڣ������Ƿ���Գ���
     */
    public void setDismissedEnable(boolean isDismissed) {
        this.isDismissed = isDismissed;
    }

    
    public void setMaxChewyLength(int maxChewyLength) {
        mMaxChewyLength = maxChewyLength;
    }

    /**
     * ����ӿڣ�����ͷ������С�뾶
     * 
     * @param minHeaderCircleRadius
     */
    public void setMinHeaderCircleRadius(int minHeaderCircleRadius) {
        mMinHeaderCircleRadius = minHeaderCircleRadius;
    }

     
    public void setOnChewyListener(OnChewyListener onChewyListener) {
        mOnChewyListener = onChewyListener;
    }

    /**
     * Բ��
     */
    private class Circle {
        public float x;
        public float y;
        public float radius;
    }

    
    public interface OnChewyListener {
        public void onDismiss();
    }
}