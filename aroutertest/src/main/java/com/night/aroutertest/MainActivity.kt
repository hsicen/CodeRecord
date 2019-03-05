package com.night.aroutertest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_jump.setOnClickListener {

            ARouter.getInstance()
                    .build("/kotlin/activity")
                    .withString("date", "Today")
                    .withInt("day", 1)
                    .navigation(this, object : NavigationCallback {
                        override fun onLost(postcard: Postcard?) {

                            Log.d("Navigation", "没有找到路径")
                        }

                        override fun onFound(postcard: Postcard?) {

                            Log.d("Navigation", "找到路径")
                        }

                        override fun onInterrupt(postcard: Postcard?) {

                            Log.d("Navigation", "被拦截")
                        }

                        override fun onArrival(postcard: Postcard?) {

                            Log.d("Navigation", "到达指定路径")
                        }
                    })
        }
    }
}
