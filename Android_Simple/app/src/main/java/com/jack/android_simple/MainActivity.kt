package com.jack.android_simple

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jack.android_simple.flutter.FlutterAppActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.normal).setOnClickListener {
            FlutterAppActivity.start(this@MainActivity,"打开普通页面的参数信息", type = 1)
        }
        findViewById<View>(R.id.btn1).setOnClickListener {
            FlutterAppActivity.start(this@MainActivity, "BasicMessageChannel", 2)
        }

        findViewById<View>(R.id.btn2).setOnClickListener {
            FlutterAppActivity.start(this@MainActivity, "MethodChannel", 3)
        }

        findViewById<View>(R.id.btn3).setOnClickListener {
            FlutterAppActivity.start(this@MainActivity, "EventChannel", 4)
        }
    }
}