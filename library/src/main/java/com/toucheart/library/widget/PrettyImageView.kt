package com.toucheart.library.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.ImageView
import com.toucheart.library.R

/**
 * <p>作者：黄思程  2018/5/9 16:29
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：自定义图片控件
 */
class PrettyImageView @JvmOverloads constructor(
        context: Context, attributeSet: AttributeSet? = null, defAttrStyle: Int = 0) :
        ImageView(context, attributeSet, defAttrStyle) {

    enum class ShapeType {
        SHAPE_CIRCLE,
        SHAPE_ROUND
    }

    private var mShapeType: ShapeType = ShapeType.SHAPE_CIRCLE
        set(value) {
            field = value
            invalidate()
        }

    private var mBorderWidth: Float = 20f
        set(value) {
            field = value
            invalidate()
        }

    private var mBorderColor: Int = Color.parseColor("#ff8237")
        set(value) {
            field = value
            invalidate()
        }

    private var mLeftTopRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mLeftTopRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mLeftBottomRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mLeftBottomRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mRightTopRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mRightTopRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mRightBottomRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mRightBottomRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mShowBorder: Boolean = true
        set(value) {
            field = value
            invalidate()
        }

    private var mShowCircleDot: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    private var mCircleDotColor: Int = Color.parseColor("#00cd66")
        set(value) {
            field = value
            invalidate()
        }

    private var mCircleDotRadius: Float = 20f
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
                    }
                }

        array.recycle()
    }

    //init the tools to paint
    private fun initDrawTools() {


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
    fun sp2px(context: Context, spValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f)
    }

}


















