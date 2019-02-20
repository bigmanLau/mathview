package cn.izouxiang.bezierdemo.bezier;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 二次函数曲线
 */
public class MathView extends View {

    private Context mContext;
    private Paint mPaint;//主画笔
    private Paint mShadowPaint;//
    private Paint mPoint;
    private Paint mProgress;
    private TextPaint mTextPaint;
    private TextPaint mTitlePaint;
    private TreeSet<Float> Df = new TreeSet<>();//定义域
    private Map<Float, Float> funMap = new HashMap<>();//映射表
    private float firstX;
    private float secondX;
    private float progress = 0.0f;
    private int m40;
    private int m80;
    private float m20;
    private float m5;

    //标题
    private String mTitle;



    //节点文字1
    private String mText1;
    //节点文字2
    private String mText2;
    private int mShadowAlpha=40;

    public MathView(Context context) {
        this(context, null);
        init(context);
    }

    public MathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);//初始化
    }

    /**
     * 初始化定义域
     */
    private void initDf() {
        Df.clear();
        for (float i = 0; i <= getMeasuredWidth(); i++) {
            Df.add(i);//初始化定义域
        }
    }

    /**
     * 对应法则
     *
     * @param x 原像(自变量)
     * @return 像(因变量)
     */
    private float f(Float x) {
        float h = getMeasuredHeight();
        float w = getMeasuredWidth();

        float y = h - m40 - (float) (Math.pow(x, 2) * (h - m80) / Math.pow(w, 2));
        return y;
    }

    /**
     * 遍历定义域,将原像x和像f(x)加入映射表
     */
    private void map() {
        funMap.clear();
        for (Float x : Df) {
            funMap.put(x, f(x));
        }
        //添加所有点
    }

    private void init(Context context) {
        this.mContext = context;
        //初始化主画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#6C7792"));
        mPaint.setStrokeWidth(DensityUtils.dp2px(mContext, 2));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPoint.setColor(Color.WHITE);
        mPoint.setStrokeWidth(DensityUtils.dp2px(mContext, 6));
        mPoint.setStyle(Paint.Style.FILL);
        mPoint.setStrokeCap(Paint.Cap.ROUND);

        mProgress = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgress.setColor(Color.WHITE);
        mProgress.setStrokeWidth(DensityUtils.dp2px(mContext, 2));
        mProgress.setStyle(Paint.Style.FILL);
        mProgress.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(DensityUtils.dp2px(mContext, 12));
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeCap(Paint.Cap.ROUND);

        mTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setColor(Color.WHITE);
        mTitlePaint.setTextSize(DensityUtils.dp2px(mContext, 20));
        mTitlePaint.setStyle(Paint.Style.FILL);
        mTitlePaint.setStrokeCap(Paint.Cap.ROUND);


        mShadowPaint= new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(View.LAYER_TYPE_SOFTWARE, mShadowPaint);
//        mShadowPaint.setColor(Color.rgb(91,100,126));
        mShadowPaint.setARGB(mShadowAlpha,91,100,126);
        mShadowPaint.setStrokeWidth(DensityUtils.dp2px(mContext, 2));
        BlurMaskFilter  maskFilter = new BlurMaskFilter(DensityUtils.dp2px(mContext, 6), BlurMaskFilter.Blur.NORMAL);
        mShadowPaint.setMaskFilter(maskFilter);

        m5 = DensityUtils.dp2px(mContext, 5);
        m20 = DensityUtils.dp2px(mContext, 20);
        m40 = DensityUtils.dp2px(mContext, 40);
        m80 = DensityUtils.dp2px(mContext, 80);

    }

    /**
     * 绘制映射表
     *
     * @param canvas 画笔
     */
    private void drawMap(Canvas canvas) {

        for (Float key : funMap.keySet()) {
            canvas.drawPoint(key, funMap.get(key).floatValue(), mPaint);
        }
    }

    /**
     * 绘制阴影
     *
     * @param canvas 画笔
     */
    private void drawshadowMap(Canvas canvas) {

        float percent;
        int count=0;
        for (Float key : funMap.keySet()) {
            count++;
            percent=count/funMap.keySet().size();
//            mShadowPaint.setColor(Color.rgb((int)(137-(137-91)*percent),
//                    (int)(149-(149-100)*percent),
//                    (int)(178-(178-126)*percent)));
            mShadowPaint.setARGB(mShadowAlpha,(int)(137-(137-91)*percent),
                    (int)(149-(149-100)*percent),
                    (int)(178-(178-126)*percent));
            canvas.drawPoint(key, funMap.get(key).floatValue() + 15, mShadowPaint);
        }
        mShadowPaint.setAlpha(255);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        firstX = Float.valueOf(getMeasuredWidth() / 8);
        secondX = Float.valueOf(getMeasuredWidth() * 6 / 8);
        initDf();
        map();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawMap(canvas);
        drawshadowMap(canvas);
//        drawTitle(canvas);

        drawProgress( canvas);
        drawPoints(canvas);
        drawTexts(canvas);
    }

    /**
     * 绘制标题
     * @param canvas
     */
    private void drawTitle(Canvas canvas) {
        if(!TextUtils.isEmpty(mTitle)){
            StaticLayout layout = new StaticLayout(mTitle, mTitlePaint, (int) (getMeasuredWidth() - firstX),
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            canvas.save();
            canvas.translate(DensityUtils.dp2px(mContext,12), DensityUtils.dp2px(mContext,13));
            layout.draw(canvas);
            canvas.restore();
        }
    }

    /**
     * 绘制进度
     * @param canvas
     */
    public void drawProgress( Canvas canvas) {
        int count = 0;
        float length = firstX + progress * (secondX - firstX);
        for (Float key : funMap.keySet()) {
            Log.d("坐标","x="+key+"  y="+funMap.get(key).floatValue());
            if (key <= length) {
                canvas.drawPoint(key, funMap.get(key).floatValue(), mProgress);
            }
        }

    }

    /**
     * 绘制两个点
     * @param canvas
     */
    private void drawPoints(Canvas canvas) {
        if (progress > 0) {
            mPoint.setColor(Color.WHITE);
        }
        canvas.drawPoint(firstX, funMap.get(firstX).floatValue(), mPoint);
        if(!TextUtils.isEmpty(mText2)) {
            mPoint.setColor(Color.parseColor("#6C7792"));
            canvas.drawPoint(secondX, funMap.get(secondX).floatValue(), mPoint);
        }
    }

    /**
     * 绘制两个点的对应文本
     * @param canvas
     */
    private void drawTexts(Canvas canvas) {
        if(!TextUtils.isEmpty(mText1)) {
            canvas.drawText(mText1, firstX, funMap.get(firstX).floatValue() + m20, mTextPaint);
        }
        if(!TextUtils.isEmpty(mText2)){
            StaticLayout layout = new StaticLayout(mText2, mTextPaint, (int) (getMeasuredWidth() - secondX-m5),
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            canvas.save();
            canvas.translate(secondX, funMap.get(secondX).floatValue()+m5 );//从20，20开始画
            layout.draw(canvas);
            canvas.restore();
        }

    }

    /**设置进度*/
    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    /**
     * 设置左上角标题
     * @param title
     */
    public void setTitle(String title) {
        this.mTitle = title;
        invalidate();
    }

    /**
     * 设置文字1
     * @param text1
     */
    public void setText1(String text1) {
        this.mText1 = text1;
        invalidate();
    }

    /**
     * 设置文字2
     * @param text2
     */
    public void setText2(String text2) {
        this.mText2 = text2;
        invalidate();
    }
}