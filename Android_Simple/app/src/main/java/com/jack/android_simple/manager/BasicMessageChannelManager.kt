package com.jack.android_simple.manager

import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StringCodec

/**
 * @创建者 Jack
 * @创建时间 2023/3/12 0012 14:28
 * @描述
 */
class BasicMessageChannelManager private constructor(
    messenger: BinaryMessenger
) : BasicMessageChannel.MessageHandler<String> {

    private var messageChannel: BasicMessageChannel<String>

    init {
        messageChannel =
            //参数1：消息信使
            //参数2：channel的名字，唯一标识
            //参数3：消息编码器，有多种不同类型的实现，这里通信只选择StringCodec
            BasicMessageChannel(messenger, "BasicMessageChannelManager", StringCodec.INSTANCE)
        // 注册处理的Handler，处理来自Flutter的消息
        messageChannel.setMessageHandler(this)
    }

    //重写函数：接收Flutter Module传递过来的消息 并可以回应给Flutter Module
    override fun onMessage(s: String?, reply: BasicMessageChannel.Reply<String>) {
        println("Flutter回复给Android的消息：$s")
        reply.reply("Android收到了 Flutter--->Android的消息，这次是回复操作") //可以通过reply进行回复
    }

    companion object {
        fun register(
            messenger: BinaryMessenger,
        ): BasicMessageChannelManager = BasicMessageChannelManager(messenger) //创建BasicMessageChannelManager实例
    }

    //native主动向Flutter Module发送消息  方式一    带回调
    fun send(message: String, callback: BasicMessageChannel.Reply<String>) {
        messageChannel.send(message, callback)
    }

    //native主动向Flutter Module发送消息  方式二    不带回调
    fun send(message: String) {
        messageChannel.send(message)
    }

}