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
 * layout_constraintXxx_toXxxOf
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
 * 属性5：设置bias(拉力大小)
 *
 *
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
