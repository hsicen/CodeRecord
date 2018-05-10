package com.hsc.vince.kotlinprettyimage

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Author ：NightYoung  2018/5/10 10:52
 * Email    ：codinghuang@163.com
 * Func     ：
 * Desc     ：ImageView with circle or round shape
 */
class PrettyImageView @JvmOverloads constructor(
        context: Context, attributeSet: AttributeSet? = null, defAttrStyle: Int = 0) :
        ImageView(context, attributeSet, defAttrStyle) {

    enum class ShapeType {
        SHAPE_CIRCLE,
        SHAPE_ROUND
    }

    var mShapeType: ShapeType = ShapeType.SHAPE_CIRCLE
        set(value) {
            field = value
            invalidate()
        }

    var mBorderWidth: Float = 20f
        set(value) {
            field = value
            invalidate()
        }

    var mBorderColor: Int = Color.parseColor("#ff8237")
        set(value) {
            field = value
            invalidate()
        }

    var mLeftTopRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var mLeftTopRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var mLeftBottomRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var mLeftBottomRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var mRightTopRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mRightTopRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var mRightBottomRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var mRightBottomRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var mShowBorder: Boolean = true
        set(value) {
            field = value
            invalidate()
        }

    var mShowCircleDot: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    var mCircleDotColor: Int = Color.parseColor("#00cd66")
        set(value) {
            field = value
            invalidate()
        }

    var mCircleDotRadius: Float = 100f
        set(value) {
            field = value
            invalidate()
        }

    var mCircleDotPosition: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    //draw tools
    private lateinit var mShapePath: Path
    private lateinit var mBorderPath: Path
    private lateinit var mBitmapPaint: Paint
    private lateinit var mBorderPaint: Paint
    private lateinit var mCircleDotPaint: Paint
    private lateinit var mMatrix: Matrix

    //temp var
    private var mWidth: Int = 200          // the width of view
    private var mHeight: Int = 200        // the height of view
    private var mRadius: Float = 100f // the radius of circle

    init {
        initAttrs(context, attributeSet, defAttrStyle)  // get the value of define attribute
        initDrawTools() //init the tools to paint
    }

    // get the value of define attribute
    private fun initAttrs(context: Context, attributeSet: AttributeSet?, defAttrStyle: Int) {
        val array = context.obtainStyledAttributes(attributeSet,
                R.styleable.PrettyImageView, defAttrStyle, 0)

        //Iterator
        (0..array.indexCount)
                .asSequence()
                .map { array.getIndex(it) }
                .forEach {
                    when (it) {
                        R.styleable.PrettyImageView_shape_type ->
                            mShapeType = when {
                                array.getInt(it, 0) == 0 -> ShapeType.SHAPE_CIRCLE
                                array.getInt(it, 0) == 1 -> ShapeType.SHAPE_ROUND
                                else -> ShapeType.SHAPE_CIRCLE
                            }

                        R.styleable.PrettyImageView_border_width ->
                            mBorderWidth = array.getDimension(it, dp2px(context, 2f))
                        R.styleable.PrettyImageView_border_color ->
                            mBorderColor = array.getColor(it, mBorderColor)
                        R.styleable.PrettyImageView_left_top_radiusX ->
                            mLeftTopRadiusX = array.getDimension(it, dp2px(context, 2f))
                        R.styleable.PrettyImageView_left_top_radiusY ->
                            mLeftTopRadiusY = array.getDimension(it, dp2px(context, 2f))
                        R.styleable.PrettyImageView_left_bottom_radiusX ->
                            mLeftBottomRadiusX = array.getDimension(it, dp2px(context, 2f))
                        R.styleable.PrettyImageView_left_bottom_radiusY ->
                            mLeftBottomRadiusY = array.getDimension(it, dp2px(context, 2f))
                        R.styleable.PrettyImageView_right_top_radiusX ->
                            mRightTopRadiusX = array.getDimension(it, dp2px(context, 2f))
                        R.styleable.PrettyImageView_right_top_radiusY ->
                            mRightTopRadiusY = array.getDimension(it, dp2px(context, 2f))
                        R.styleable.PrettyImageView_right_bottom_radiusX ->
                            mRightBottomRadiusX = array.getDimension(it, dp2px(context, 2f))
                        R.styleable.PrettyImageView_right_bottom_radiusY ->
                            mRightBottomRadiusY = array.getDimension(it, dp2px(context, 2f))
                        R.styleable.PrettyImageView_show_border ->
                            mShowBorder = array.getBoolean(it, mShowBorder)
                        R.styleable.PrettyImageView_show_circle_dot ->
                            mShowCircleDot = array.getBoolean(it, mShowCircleDot)
                        R.styleable.PrettyImageView_circle_dot_color ->
                            mCircleDotColor = array.getColor(it, mCircleDotColor)
                        R.styleable.PrettyImageView_circle_dot_radius ->
                            mCircleDotRadius = array.getDimension(it, dp2px(context, 5f))
                        R.styleable.PrettyImageView_circle_dot_position ->
                            mCircleDotPosition = array.getInt(it, mCircleDotPosition)
                    }
                }

        array.recycle()
    }

    //init the tools to paint
    private fun initDrawTools() {
        mBitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }

        mBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = mBorderColor
            strokeCap = Paint.Cap.ROUND
            strokeWidth = mBorderWidth
        }

        mCircleDotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = mCircleDotColor
        }

        mShapePath = Path()
        mBorderPath = Path()
        mMatrix = Matrix()
        scaleType = ScaleType.CENTER_INSIDE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (mShapeType == ShapeType.SHAPE_CIRCLE) {
            mWidth = Math.min(measuredWidth, measuredHeight)
            mRadius = mWidth / 2.0f
            mWidth = (mWidth + mCircleDotRadius * 2).toInt()
            setMeasuredDimension(mWidth, mWidth)
        } else {
            mWidth = measuredWidth
            mHeight = measuredHeight
            setMeasuredDimension(mWidth, mHeight)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mBorderPath.reset()
        mShapePath.reset()

        when (mShapeType) {
            ShapeType.SHAPE_ROUND -> {
                mWidth = w
                mHeight = h
                buildRoundPath()
            }

            ShapeType.SHAPE_CIRCLE -> {
                buildCirclePath()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        drawable ?: return
        mBitmapPaint.shader = getBitmapShader()
        when (mShapeType) {
            ShapeType.SHAPE_CIRCLE -> {
                if (mShowBorder) {
                    canvas?.drawPath(mBorderPath, mBorderPaint)
                }
                canvas?.drawPath(mShapePath, mBitmapPaint)
                if (mShowCircleDot) {
                    drawCircleDot(canvas)
                }
            }

            ShapeType.SHAPE_ROUND -> {
                if (mShowBorder) {
                    canvas?.drawPath(mBorderPath, mBorderPaint)
                }
                canvas?.drawPath(mShapePath, mBitmapPaint)
            }
        }
    }

    private fun drawCircleDot(canvas: Canvas?) {
        canvas?.run {
            canvas.translate(mRadius + mCircleDotRadius, mRadius + mCircleDotRadius)
            val x = mRadius * Math.sin(Math.PI * mCircleDotPosition / 180)
            val y = mRadius * Math.cos(Math.PI * mCircleDotPosition / 180)
            drawCircle(x.toFloat(), y.toFloat(), mCircleDotRadius, mCircleDotPaint)
        }
    }

    private fun getBitmapShader(): BitmapShader {
        val bitmap = drawableToBitmap(drawable)
        return BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP).apply {
            var scale = 1.0f
            if (mShapeType == ShapeType.SHAPE_CIRCLE) {
                scale = (mWidth * 1.0f / Math.min(bitmap.width, bitmap.height))
            } else if (mShapeType == ShapeType.SHAPE_ROUND) {
                if (!(width == bitmap.width && width == bitmap.height)) {
                    scale = Math.max(width * 1.0f / bitmap.width, height * 1.0f / bitmap.height)
                }
            }

            mMatrix.setScale(scale, scale)
            setLocalMatrix(mMatrix)
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        return Bitmap.createBitmap(drawable.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888).apply {
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable.draw(Canvas(this@apply))
        }
    }

    private fun buildCirclePath() {
        val x = mRadius + mCircleDotRadius
        val y = mRadius + mCircleDotRadius

        if (!mShowBorder) {
            mShapePath.addCircle(x, y, mRadius, Path.Direction.CW)
        } else {
            mShapePath.addCircle(x, y, mRadius, Path.Direction.CW)
            mBorderPath.addCircle(x, y, mRadius + mBorderWidth / 2.0f, Path.Direction.CW)
        }
    }

    private fun buildRoundPath() {
        if (!mShowBorder) {
            floatArrayOf(mLeftTopRadiusX, mLeftTopRadiusY,
                    mRightTopRadiusX, mRightTopRadiusY,
                    mRightBottomRadiusX, mRightBottomRadiusY,
                    mLeftBottomRadiusX, mLeftBottomRadiusY).run {
                mShapePath.addRoundRect(RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()), this, Path.Direction.CW)
            }
        } else {
            floatArrayOf(mLeftTopRadiusX - mBorderWidth / 2.0f, mLeftTopRadiusY - mBorderWidth / 2.0f,
                    mRightTopRadiusX - mBorderWidth / 2.0f, mRightTopRadiusY - mBorderWidth / 2.0f,
                    mRightBottomRadiusX - mBorderWidth / 2.0f, mRightBottomRadiusY - mBorderWidth / 2.0f,
                    mLeftBottomRadiusX - mBorderWidth / 2.0f, mLeftBottomRadiusY - mBorderWidth / 2.0f).run {
                mBorderPath.addRoundRect(RectF(mBorderWidth / 2.0f, mBorderWidth / 2.0f,
                        mWidth.toFloat() - mBorderWidth / 2.0f, mHeight.toFloat() - mBorderWidth / 2.0f), this, Path.Direction.CW)
            }

            floatArrayOf(mLeftTopRadiusX - mBorderWidth, mLeftTopRadiusY - mBorderWidth,
                    mRightTopRadiusX - mBorderWidth, mRightTopRadiusY - mBorderWidth,
                    mRightBottomRadiusX - mBorderWidth, mRightBottomRadiusY - mBorderWidth,
                    mLeftBottomRadiusX - mBorderWidth, mLeftBottomRadiusY - mBorderWidth).run {
                mShapePath.addRoundRect(RectF(mBorderWidth, mBorderWidth,
                        mWidth.toFloat() - mBorderWidth, mHeight.toFloat() - mBorderWidth),
                        this, Path.Direction.CW)
            }
        }
    }

    companion object {
        private const val STATE_INSTANCE = "state_instance"
        private const val STATE_INSTANCE_SHAPE_TYPE = "state_shape_type"
        private const val STATE_INSTANCE_BORDER_WIDTH = "state_border_width"
        private const val STATE_INSTANCE_BORDER_COLOR = "state_border_color"
        private const val STATE_INSTANCE_RADIUS_LEFT_TOP_X = "state_radius_left_top_x"
        private const val STATE_INSTANCE_RADIUS_LEFT_TOP_Y = "state_radius_left_top_y"
        private const val STATE_INSTANCE_RADIUS_LEFT_BOTTOM_X = "state_radius_left_bottom_x"
        private const val STATE_INSTANCE_RADIUS_LEFT_BOTTOM_Y = "state_radius_left_bottom_y"
        private const val STATE_INSTANCE_RADIUS_RIGHT_TOP_X = "state_radius_right_top_x"
        private const val STATE_INSTANCE_RADIUS_RIGHT_TOP_Y = "state_radius_right_top_y"
        private const val STATE_INSTANCE_RADIUS_RIGHT_BOTTOM_X = "state_radius_right_bottom_x"
        private const val STATE_INSTANCE_RADIUS_RIGHT_BOTTOM_Y = "state_radius_right_bottom_y"
        private const val STATE_INSTANCE_RADIUS = "state_radius"
        private const val STATE_INSTANCE_SHOW_BORDER = "state_radius_show_border"
    }

    override fun onSaveInstanceState(): Parcelable = Bundle().apply {
        putParcelable(STATE_INSTANCE, super.onSaveInstanceState())
        putInt(STATE_INSTANCE_SHAPE_TYPE, when (mShapeType) {
            ShapeType.SHAPE_CIRCLE -> 0
            ShapeType.SHAPE_ROUND -> 1
        })

        putFloat(STATE_INSTANCE_BORDER_WIDTH, mBorderWidth)
        putInt(STATE_INSTANCE_BORDER_COLOR, mBorderColor)
        putFloat(STATE_INSTANCE_RADIUS_LEFT_TOP_X, mLeftTopRadiusX)
        putFloat(STATE_INSTANCE_RADIUS_LEFT_TOP_Y, mLeftTopRadiusY)
        putFloat(STATE_INSTANCE_RADIUS_LEFT_BOTTOM_X, mLeftBottomRadiusX)
        putFloat(STATE_INSTANCE_RADIUS_LEFT_BOTTOM_Y, mLeftBottomRadiusY)
        putFloat(STATE_INSTANCE_RADIUS_RIGHT_TOP_X, mRightTopRadiusX)
        putFloat(STATE_INSTANCE_RADIUS_RIGHT_TOP_Y, mRightTopRadiusY)
        putFloat(STATE_INSTANCE_RADIUS_RIGHT_BOTTOM_X, mRightBottomRadiusX)
        putFloat(STATE_INSTANCE_RADIUS_RIGHT_BOTTOM_Y, mRightBottomRadiusY)
        putFloat(STATE_INSTANCE_RADIUS, mRadius)
        putBoolean(STATE_INSTANCE_SHOW_BORDER, mShowBorder)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is Bundle) {
            super.onRestoreInstanceState(state)
            return
        }

        with(state) {
            super.onRestoreInstanceState(getParcelable(STATE_INSTANCE))
            mShapeType = when {
                getInt(STATE_INSTANCE_SHAPE_TYPE) == 0 -> ShapeType.SHAPE_CIRCLE
                getInt(STATE_INSTANCE_SHAPE_TYPE) == 1 -> ShapeType.SHAPE_ROUND
                else -> ShapeType.SHAPE_CIRCLE
            }

            mBorderWidth = getFloat(STATE_INSTANCE_BORDER_WIDTH)
            mBorderColor = getInt(STATE_INSTANCE_BORDER_COLOR)
            mLeftTopRadiusX = getFloat(STATE_INSTANCE_RADIUS_LEFT_TOP_X)
            mLeftTopRadiusY = getFloat(STATE_INSTANCE_RADIUS_LEFT_TOP_Y)
            mLeftBottomRadiusX = getFloat(STATE_INSTANCE_RADIUS_LEFT_BOTTOM_X)
            mLeftBottomRadiusY = getFloat(STATE_INSTANCE_RADIUS_LEFT_BOTTOM_Y)
            mRightTopRadiusX = getFloat(STATE_INSTANCE_RADIUS_RIGHT_TOP_X)
            mRightTopRadiusY = getFloat(STATE_INSTANCE_RADIUS_RIGHT_TOP_Y)
            mRightBottomRadiusX = getFloat(STATE_INSTANCE_RADIUS_RIGHT_BOTTOM_X)
            mRightBottomRadiusY = getFloat(STATE_INSTANCE_RADIUS_RIGHT_BOTTOM_Y)
            mRadius = getFloat(STATE_INSTANCE_RADIUS)
            mShowBorder = getBoolean(STATE_INSTANCE_SHOW_BORDER)
        }
    }

    /**
     * @param context context
     * @param dpValue dpValue
     * @return dp value to sp value
     */
    private fun dp2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f)
    }

    /**
     * @param context context
     * @param spValue sp value
     * @return sp value to px value
     */
    private fun sp2px(context: Context, spValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f)
    }
}