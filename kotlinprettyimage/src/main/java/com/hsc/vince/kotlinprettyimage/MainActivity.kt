package com.hsc.vince.kotlinprettyimage

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val imageUrl1 = "http://www.kinyu-z.net/data/wallpapers/35/823942.jpg"
    private val imageUrl2 = "https://www.codeproject.com/KB/GDI-plus/ImageProcessing2/img.jpg"
    private val imageUrl3 = "https://images.pexels.com/photos/658687/pexels-photo-658687.jpeg?auto=compress&cs=tinysrgb&h=350";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add_image.setOnClickListener {
            loadImage()
        }
    }

    private fun loadImage() {
        iv_pretty_one.loadImage(this, imageUrl3)
    }

    /*** add a property for imageView*/
    private fun ImageView.loadImage(mContext: Context, url: String) {
        Glide.with(mContext)
                .load(url)
                .into(this)
    }
}
