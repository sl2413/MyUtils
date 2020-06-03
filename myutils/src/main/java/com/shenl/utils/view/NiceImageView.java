package com.shenl.utils.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.shenl.utils.R;
import static org.xutils.common.util.DensityUtil.dip2px;

/*
 *参考文章（Android Paint Xfermode 学习小结）https://www.cnblogs.com/libertycode/p/6290497.html
 * (saveLayer图层详解)https://blog.csdn.net/u012221046/article/details/53261606
 */
@SuppressLint("AppCompatCustomView")
public class NiceImageView extends ImageView {
    private Context context;

    private boolean isCircle; // 是否显示为圆形，如果为圆形则设置的corner无效
    private boolean isCoverSrc; // border、inner_border是否覆盖图片
    private int borderWidth; // 边框宽度
    private int borderColor = Color.WHITE; // 边框颜色
    private int innerBorderWidth; // 内层边框宽度
    private int innerBorderColor = Color.WHITE; // 内层边框充色

    private int cornerRadius; // 统一设置圆角半径，优先级高于单独设置每个角的半径
    private int cornerTopLeftRadius; // 左上角圆角半径
    private int cornerTopRightRadius; // 右上角圆角半径
    private int cornerBottomLeftRadius; // 左下角圆角半径
    private int cornerBottomRightRadius; // 右下角圆角半径

    private int maskColor; // 遮罩颜色

    private Xfermode xfermode;

    private int width;
    private int height;
    private float radius;

    private float[] borderRadii;
    private float[] srcRadii;

    private RectF srcRectF; // 图片占的矩形区域
    private RectF borderRectF; // 边框的矩形区域

    private Path path = new Path();
    private Paint paint = new Paint();

    public NiceImageView(Context context) {
        this(context, null);
    }

    public NiceImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NiceImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NiceImageView, 0, 0);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.NiceImageView_is_cover_src) {
                isCoverSrc = ta.getBoolean(attr, isCoverSrc);
            } else if (attr == R.styleable.NiceImageView_is_circle) {
                isCircle = ta.getBoolean(attr, isCircle);
            } else if (attr == R.styleable.NiceImageView_border_width) {
                borderWidth = ta.getDimensionPixelSize(attr, borderWidth);
            } else if (attr == R.styleable.NiceImageView_border_color) {
                borderColor = ta.getColor(attr, borderColor);
            } else if (attr == R.styleable.NiceImageView_inner_border_width) {
                innerBorderWidth = ta.getDimensionPixelSize(attr, innerBorderWidth);
            } else if (attr == R.styleable.NiceImageView_inner_border_color) {
                innerBorderColor = ta.getColor(attr, innerBorderColor);
            } else if (attr == R.styleable.NiceImageView_corner_radius) {
                cornerRadius = ta.getDimensionPixelSize(attr, cornerRadius);
            } else if (attr == R.styleable.NiceImageView_corner_top_left_radius) {
                cornerTopLeftRadius = ta.getDimensionPixelSize(attr, cornerTopLeftRadius);
            } else if (attr == R.styleable.NiceImageView_corner_top_right_radius) {
                cornerTopRightRadius = ta.getDimensionPixelSize(attr, cornerTopRightRadius);
            } else if (attr == R.styleable.NiceImageView_corner_bottom_left_radius) {
                cornerBottomLeftRadius = ta.getDimensionPixelSize(attr, cornerBottomLeftRadius);
            } else if (attr == R.styleable.NiceImageView_corner_bottom_right_radius) {
                cornerBottomRightRadius = ta.getDimensionPixelSize(attr, cornerBottomRightRadius);
            } else if (attr == R.styleable.NiceImageView_mask_color) {
                maskColor = ta.getColor(attr, maskColor);
            }
        }
        ta.recycle();

        borderRadii = new float[8];
        srcRadii = new float[8];

        borderRectF = new RectF();
        srcRectF = new RectF();

        setScaleType(ScaleType.FIT_XY);
        /*
         *ADD:饱和相加,对图像饱和度进行相加,不常用
         *CLEAR:清除图像
         *DARKEN:变暗,较深的颜色覆盖较浅的颜色，若两者深浅程度相同则混合
         *DST:只显示目标图像
         *DST_ATOP:在源图像和目标图像相交的地方绘制【目标图像】，在不相交的地方绘制【源图像】，相交处的效果受到源图像和目标图像alpha的影响
         *DST_IN:只在源图像和目标图像相交的地方绘制【目标图像】，绘制效果受到源图像对应地方透明度影响
         *DST_OUT:只在源图像和目标图像不相交的地方绘制【目标图像】，在相交的地方根据源图像的alpha进行过滤，源图像完全不透明则完全过滤，完全透明则不过滤
         *DST_OVER:将目标图像放在源图像上方
         *LIGHTEN:变亮，与DARKEN相反，DARKEN和LIGHTEN生成的图像结果与Android对颜色值深浅的定义有关
         *MULTIPLY:正片叠底，源图像素颜色值乘以目标图像素颜色值除以255得到混合后图像像素颜色值
         *OVERLAY:叠加
         *SCREEN:滤色，色调均和,保留两个图层中较白的部分，较暗的部分被遮盖
         *SRC:只显示源图像
         *SRC_ATOP:在源图像和目标图像相交的地方绘制【源图像】，在不相交的地方绘制【目标图像】，相交处的效果受到源图像和目标图像alpha的影响
         *SRC_IN:只在源图像和目标图像相交的地方绘制【源图像】
         *SRC_OUT:只在源图像和目标图像不相交的地方绘制【源图像】，相交的地方根据目标图像的对应地方的alpha进行过滤，目标图像完全不透明则完全过滤，完全透明则不过滤
         *SRC_OVER:将源图像放在目标图像上方
         *XOR:在源图像和目标图像相交的地方之外绘制它们，在相交的地方受到对应alpha和色值影响，如果完全不透明则相交处完全不绘制
         */
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        calculateRadii();
        clearInnerBorderWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        initBorderRectF();
        initSrcRectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 使用图形混合模式来显示指定区域的图片
        canvas.saveLayer(srcRectF, null, Canvas.ALL_SAVE_FLAG);
        if (!isCoverSrc) {
            float sx = 1.0f * (width - 2 * borderWidth - 2 * innerBorderWidth) / width;
            float sy = 1.0f * (height - 2 * borderWidth - 2 * innerBorderWidth) / height;
            // 缩小画布，使图片内容不被border、padding覆盖
            canvas.scale(sx, sy, width / 2.0f, height / 2.0f);
        }
        super.onDraw(canvas);//绘制目标图片dst
        paint.reset();
        path.reset();
        if (isCircle) {
            path.addCircle(width / 2.0f, height / 2.0f, radius, Path.Direction.CCW);
        } else {
            path.addRoundRect(srcRectF, srcRadii, Path.Direction.CCW);
        }

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(xfermode);
        canvas.drawPath(path, paint);//绘制源图src
        paint.setXfermode(null);

        // 绘制遮罩
        if (maskColor != 0) {
            paint.setColor(maskColor);
            canvas.drawPath(path, paint);
        }
        // 恢复画布
        canvas.restore();
        // 绘制边框
        drawBorders(canvas);
    }

    private void drawBorders(Canvas canvas) {
        if (isCircle) {
            if (borderWidth > 0) {
                drawCircleBorder(canvas, borderWidth, borderColor, radius - borderWidth / 2.0f);
            }
            if (innerBorderWidth > 0) {
                drawCircleBorder(canvas, innerBorderWidth, innerBorderColor, radius - borderWidth - innerBorderWidth / 2.0f);
            }
        } else {
            if (borderWidth > 0) {
                drawRectFBorder(canvas, borderWidth, borderColor, borderRectF, borderRadii);
            }
        }
    }

    private void drawCircleBorder(Canvas canvas, int borderWidth, int borderColor, float radius) {
        initBorderPaint(borderWidth, borderColor);
        path.addCircle(width / 2.0f, height / 2.0f, radius, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }

    private void drawRectFBorder(Canvas canvas, int borderWidth, int borderColor, RectF rectF, float[] radii) {
        initBorderPaint(borderWidth, borderColor);
        path.addRoundRect(rectF, radii, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }

    private void initBorderPaint(int borderWidth, int borderColor) {
        path.reset();
        paint.setStrokeWidth(borderWidth);
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 计算外边框的RectF
     */
    private void initBorderRectF() {
        if (!isCircle) {
            borderRectF.set(borderWidth / 2.0f, borderWidth / 2.0f, width - borderWidth / 2.0f, height - borderWidth / 2.0f);
        }
    }

    /**
     * 计算图片原始区域的RectF
     */
    private void initSrcRectF() {
        if (isCircle) {
            radius = Math.min(width, height) / 2.0f;
            srcRectF.set(width / 2.0f - radius, height / 2.0f - radius, width / 2.0f + radius, height / 2.0f + radius);
        } else {
            srcRectF.set(0, 0, width, height);
            if (isCoverSrc) {
                srcRectF = borderRectF;
            }
        }
    }

    /**
     * 计算RectF的圆角半径
     */
    private void calculateRadii() {
        if (isCircle) {
            return;
        }
        if (cornerRadius > 0) {
            for (int i = 0; i < borderRadii.length; i++) {
                borderRadii[i] = cornerRadius;
                srcRadii[i] = cornerRadius - borderWidth / 2.0f;
            }
        } else {
            borderRadii[0] = borderRadii[1] = cornerTopLeftRadius;
            borderRadii[2] = borderRadii[3] = cornerTopRightRadius;
            borderRadii[4] = borderRadii[5] = cornerBottomRightRadius;
            borderRadii[6] = borderRadii[7] = cornerBottomLeftRadius;

            srcRadii[0] = srcRadii[1] = cornerTopLeftRadius - borderWidth / 2.0f;
            srcRadii[2] = srcRadii[3] = cornerTopRightRadius - borderWidth / 2.0f;
            srcRadii[4] = srcRadii[5] = cornerBottomRightRadius - borderWidth / 2.0f;
            srcRadii[6] = srcRadii[7] = cornerBottomLeftRadius - borderWidth / 2.0f;
        }
    }

    private void calculateRadiiAndRectF(boolean reset) {
        if (reset) {
            cornerRadius = 0;
        }
        calculateRadii();
        initBorderRectF();
        invalidate();
    }

    /**
     * 目前圆角矩形情况下不支持inner_border，需要将其置0
     */
    private void clearInnerBorderWidth() {
        if (!isCircle) {
            this.innerBorderWidth = 0;
        }
    }

    public void isCoverSrc(boolean isCoverSrc) {
        this.isCoverSrc = isCoverSrc;
        initSrcRectF();
        invalidate();
    }

    public void isCircle(boolean isCircle) {
        this.isCircle = isCircle;
        clearInnerBorderWidth();
        initSrcRectF();
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = dip2px(Float.parseFloat(borderWidth+""));
        calculateRadiiAndRectF(false);
    }

    public void setBorderColor(@ColorInt int borderColor) {
        this.borderColor = borderColor;
        invalidate();
    }

    public void setInnerBorderWidth(int innerBorderWidth) {
        this.innerBorderWidth = dip2px(Float.parseFloat(innerBorderWidth+""));
        clearInnerBorderWidth();
        invalidate();
    }

    public void setInnerBorderColor(@ColorInt int innerBorderColor) {
        this.innerBorderColor = innerBorderColor;
        invalidate();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = dip2px(Float.parseFloat(cornerRadius+""));;
        calculateRadiiAndRectF(false);
    }

    public void setCornerTopLeftRadius(int cornerTopLeftRadius) {
        this.cornerTopLeftRadius = dip2px(Float.parseFloat(cornerTopLeftRadius+""));;
        calculateRadiiAndRectF(true);
    }

    public void setCornerTopRightRadius(int cornerTopRightRadius) {
        this.cornerTopRightRadius = dip2px(dip2px(Float.parseFloat(cornerTopRightRadius+"")));
        calculateRadiiAndRectF(true);
    }

    public void setCornerBottomLeftRadius(int cornerBottomLeftRadius) {
        this.cornerTopRightRadius = dip2px(dip2px(Float.parseFloat(cornerBottomLeftRadius+"")));
        calculateRadiiAndRectF(true);
    }

    public void setCornerBottomRightRadius(int cornerBottomRightRadius) {
        this.cornerBottomRightRadius = dip2px(Float.parseFloat(innerBorderWidth+""));
        calculateRadiiAndRectF(true);
    }

    public void setMaskColor(@ColorInt int maskColor) {
        this.maskColor = maskColor;
        invalidate();
    }
}
