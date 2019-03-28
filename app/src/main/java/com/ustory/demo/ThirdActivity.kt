package com.ustory.demo

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ustory.eventdriver.EventDriver
import com.ustory.easybus.R
import kotlinx.android.synthetic.main.activity_third.*

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EventDriver.find<String>("tom").observe(this, Observer {
            Log.i("qiyue","ThirdActivity - receiver=$it")
        })
        setContentView(R.layout.activity_third)

        button.setOnClickListener {
            EventDriver.notify<String>("tom") of "I am ThirdActivity"
        }



    }
}
