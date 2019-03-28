package com.ustory.demo

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ustory.eventdriver.EventDriver
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ustory.easybus.R.layout.activity_main)

        EventDriver.find<String>("tom").observe(this, Observer {
            Log.i("qiyue","MainActivity - receiver=$it")
        })

        button.setOnClickListener {
            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
        }


    }


}
