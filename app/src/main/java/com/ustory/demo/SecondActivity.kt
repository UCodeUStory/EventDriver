package com.ustory.demo

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ustory.easybus.R
import com.ustory.eventdriver.EventDriver
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        EventDriver.find<String>("tom").observe(this, Observer {
          Log.i("qiyue","SecondActivity - receiver=$it")
        })

        val loopThread = object:Thread(){
            override fun run() {
                super.run()
                for (i in 0 until  1000){
                    sleep(500)
                    runOnUiThread {
                        EventDriver.notify<String>("tom") of "goodMorning"
                    }
                }
            }
        }

        button.setOnClickListener {
            loopThread.start()
        }

        btn_start.setOnClickListener {

            startActivity(Intent(this, ThirdActivity::class.java))
        }



    }
}
