import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class EventChannelPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _MyHomePageState();
  }
}

class _MyHomePageState extends State<EventChannelPage> {
  EventChannel? _eventChannelManager;
  String _eventMessage = '';

  _MyHomePageState();

  @override
  void initState() {
    super.initState();
    // 1.创建EventChannel
    _eventChannelManager =
        EventChannel("com.jack.android_simple/EventChannelManager");

    // 2.初始化一个广播流从channel中接收数据
    _eventChannelManager!
        .receiveBroadcastStream() //dynamic arguments： 对应Android端onListen（）的第一个参数，可不传
        // 开启监听
        .listen((event) {
      // 注意：listen可以设置 监听数据流其它状态时 的方法 Function? onError, void onDone()?, bool? cancelOnError
      print("接收Android发送过来的数据 --- $event");
      setState(() {
        _eventMessage = event;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("native与flutter通信之EventChannel"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_eventMessage',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
          ],
        ),
      ),
      // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
