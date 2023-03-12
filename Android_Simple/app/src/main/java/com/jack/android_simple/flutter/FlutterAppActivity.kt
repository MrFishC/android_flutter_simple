package com.jack.android_simple.flutter

import android.content.Context
import android.content.Intent
import android.os.Handler
import com.jack.android_simple.manager.BasicMessageChannelManager
import com.jack.android_simple.manager.EventChannelManager
import com.jack.android_simple.manager.MethodChannelManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

/**
 * @创建者 Jack
 * @创建时间 2023/3/12 0012 11:59
 * @描述
 */
class FlutterAppActivity : FlutterActivity() {
    private var mInitParams: String? = null

    private var mBasicMessageChannelManager: BasicMessageChannelManager? = null
    private var mMethodChannelManager: MethodChannelManager? = null
    private var mEventChannelManager: EventChannelManager? = null

    companion object {
        const val INIT_PARAMS = "initParams"

        private var mtype = 0
        fun start(context: Context, initParams: String = "", type: Int) {
            mtype = type
            val intent = Intent(context, FlutterAppActivity::class.java)
            intent.putExtra(INIT_PARAMS, initParams)
            context.startActivity(intent)
        }
    }

    //优先级高于onCreate
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        mInitParams = intent.getStringExtra(INIT_PARAMS);

        if (mtype == 2) {
            mBasicMessageChannelManager =
                BasicMessageChannelManager.register(flutterEngine.dartExecutor.binaryMessenger)
        } else if (mtype == 3) {
            mMethodChannelManager =
                MethodChannelManager.register(flutterEngine.dartExecutor.binaryMessenger)
        } else if (mtype == 4) {
            mEventChannelManager =
                EventChannelManager.register(flutterEngine.dartExecutor.binaryMessenger)
        }
    }

    //重载该方法来传递初始化参数
    override fun getInitialRoute(): String? {
        return if (mInitParams == null) super.getInitialRoute() else mInitParams
    }

//    在Application中预加载flutterEngine引擎会导致getInitialRoute不被调用
//    override fun getCachedEngineId(): String? {
//        return "cache_engine"
//    }

    override fun onStart() {
        super.onStart()
        sendMessageToFlutter()
    }

    private fun sendMessageToFlutter() {
        if (mtype == 2) {
            // Android给Flutter发送消息
            mBasicMessageChannelManager!!.send("通过方式一BasicMessageChannel传递参数") { message: String? ->
                println("message mtype == 2 $message")
            }
        } else if (mtype == 3) {
            // Android调用Flutter的send方法
            mMethodChannelManager!!.callMethod(
                "AndroidCallFlutterMethod",
                "Android传递给Flutter的消息"
            )
        } else if (mtype == 4) {
            //使用定时器模拟更佳
            Handler().postDelayed({ mEventChannelManager!!.send("发送流信息") }, 5000)
        }
    }
}