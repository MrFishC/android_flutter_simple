package com.jack.android_simple.manager

import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * @创建者 Jack
 * @创建时间 2023/3/12 0012 15:50
 * @描述
 */
class MethodChannelManager private constructor(
    private val messenger: BinaryMessenger
) : MethodChannel.MethodCallHandler {

    // 3. 用于调用Flutter端方法 方式一 无返回值
    // method为需调用的方法名
    fun callMethod(method: String, o: Any) {
        methodChannel.invokeMethod(method, o)
    }

    //用于调用Flutter端方法 方式一 有返回值
    // method为需调用的方法名、返回值在result内
    fun callMethod(method: String, o: Any, result: MethodChannel.Result) {
        methodChannel.invokeMethod(method, o, result)
    }

    // 4. 复写onMethodCall（）：根据Flutter的要求，调用Android方法
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "callAndroidMethod" -> {
                println("Flutter--->Android的消息 ${call.arguments}")
                //返回结果给Dart
                result.success("Android收到了 Flutter--->Android的消息，这次是回复操作")
            }

            else -> result.notImplemented()
        }
    }

    private var methodChannel: MethodChannel

    init {
        // 1. 创建MethodChannel实例（传入channel name）
        methodChannel = MethodChannel(messenger, "MethodChannelManager")
        // 2. 注册处理的Handler
        methodChannel.setMethodCallHandler(this)
    }

    companion object {
        fun register(messenger: BinaryMessenger): MethodChannelManager =
            MethodChannelManager(messenger)
    }
}

