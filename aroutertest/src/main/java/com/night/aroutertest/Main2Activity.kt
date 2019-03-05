package com.night.aroutertest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter


@Route(path = "/test/activity")
class Main2Activity : AppCompatActivity() {


    @Autowired(name = "date")
    lateinit var mDate: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        ARouter.getInstance().inject(this)
        Log.d(TAG, mDate)
    }


    companion object {
        const val TAG = "Main2Activity"

    }
}
