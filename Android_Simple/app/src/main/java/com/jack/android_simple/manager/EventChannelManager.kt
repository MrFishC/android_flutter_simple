package com.jack.android_simple.manager

import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.EventChannel.StreamHandler


/**
 * @创建者 Jack
 * @创建时间 2023/3/12 0012 16:29
 * @描述
 */
class EventChannelManager private constructor(
    private val messenger: BinaryMessenger
) : StreamHandler {
    private var eventSink: EventSink? = null

    // 4.1.Android端开始发送数据
    fun send(params: Any) {
        if (eventSink != null) {
            eventSink!!.success(params)
            println("sink success")
        }
    }

    // 4.2.Android端停止发送数据
    fun cancel() {
        if (eventSink != null) {
            eventSink!!.endOfStream()
        }
    }

    // 4.3.Android端发送数据失败
    fun sendError(str1: String, str2: String, params: Any) {
        if (eventSink != null) {
            eventSink!!.error(str1, str2, params)
        }
    }

    // 3.回调时机：Flutter端开始监听该channel时
    // 说明通道已经建立好，Android可以开始发送数据了
    // 参数1：Flutter端初始化EventChannel时返回的值，仅此一次
    // 参数2：传数据的载体
    override fun onListen(o: Any?, eventSink: EventSink?) {
        //此处注意时序，必须得该方法回调后，Android端才允许发送数据
        this.eventSink = eventSink
        println("onListen()：eventSink = $eventSink")
    }

    // Flutter端不再接收数据时回调
    override fun onCancel(o: Any) {
        println("onCancel()")
        eventSink = null
    }

    init {
        //1.创建EventChannel    参数2的格式：包名/标识符
        val channel = EventChannel(messenger, "com.jack.android_simple/EventChannelManager")
        //2.设置对应Handler
        channel.setStreamHandler(this)
    }

    companion object {
        fun register(
            messenger: BinaryMessenger
        ): EventChannelManager = EventChannelManager(messenger)
    }
}