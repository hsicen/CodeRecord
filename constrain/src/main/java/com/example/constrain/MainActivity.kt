package com.example.constrain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * <p>作者：黄思程  2019/3/21 17:08
 * <p>邮箱：codinghuang@163.com
 * <p>功能：
 * <p>描述：约束布局
 *
 * 属性1(RelativeLayout)： 控制一个控件在另一个控件的左边还是右边，上边还是下边
 * layout_constraintXxx_toXxxOf(我的左/右/上/下 和你的左/右/上/下 对齐)
 * layout_constraintBaseline_toBaselineOf
 *
 * 属性2：设置控件显示宽高比例
 * layout_constraintDimensionRatio="H,16:9"
 * 要设置宽高为0,充满父布局
 * android:layout_width="0dp"
 * android:layout_height="0dp"
 *
 * 属性3(LinearLayout)：设置控件水平和垂直比例
 * layout_constraintHorizontal_weight="1"
 * layout_constraintVertical_weight="3"
 *
 * 属性4：两两约束(chainStyle)
 * 控件两两依赖，组成一个链，可以设置链头的style
 * layout_constraintHorizontal_chainStyle="packed"
 * layout_constraintVertical_chainStyle="spread_inside"
 * packed，spread，spread_inside
 * spread + 宽度非0：平分居中
 * spread + 宽度为0 + 属性3设置权重占比
 * spread_inside + 宽度非0：起始平分
 * packed + 宽度非0：集中居中
 *
 * 属性5：设置bias(控制两边拉力大小)
 * layout_constraintHorizontal_bias="0.9"(左边0.1，右边0.9)
 * layout_constraintVertical_bias="0.9"(上边0.1，下边0.9)
 *
 * 属性6：设置Guideline(布局辅助线)
 * orientation="horizontal" (设置水平或垂直的辅助线)
 * 指定辅助线的位置
 * layout_constraintGuide_begin="50dp"
 * layout_constraintGuide_end="50dp"
 * layout_constraintGuide_percent="50dp"
 *
 * 属性7：圆形定位
 * app:layout_constraintCircle="@id/tv_float"
 * app:layout_constraintCircleAngle="145"
 * app:layout_constraintCircleRadius="18dp"
 *
 * 属性8：Barrier(整体屏障)
 *
 * 属性9：Group(组，控制组内控件整体的状态)
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
