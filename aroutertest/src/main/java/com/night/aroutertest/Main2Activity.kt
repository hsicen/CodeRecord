package com.night.aroutertest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main2.*


@SuppressLint("SetTextI18n")
@Route(path = "/kotlin/activity")
class Main2Activity : AppCompatActivity() {


    @Autowired
    @JvmField
    var date: String = ""

    @Autowired
    @JvmField
    var day: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        ARouter.getInstance().inject(this)

        tv_receive.text = "I Received: $date, $day"

        tv_receive.setOnClickListener {

            ARouter.getInstance()
                    .build("/kotlin/filter")
                    .navigation()
        }
    }


    companion object {
        const val TAG = "Main2Activity"

    }
}
