package com.night.aroutertest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_jump.setOnClickListener {

            ARouter.getInstance()
                    .build("/test/activity")
                    .withString("date", "Today")
                    .navigation()
        }
    }
}
