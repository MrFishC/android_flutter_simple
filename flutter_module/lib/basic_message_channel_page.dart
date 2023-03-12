import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BasicMessageChannelPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _MyHomePageState();
  }
}

class _MyHomePageState extends State<BasicMessageChannelPage> {
  // int _counter = 0;
  _MyHomePageState();

  void _incrementCounter() {
    // setState(() {
    //   _counter++;
    // });
    // 主动向Android发送消息
    _basicMessageChannel!.send("Flutter--->Android的消息2，这次是Flutter主动的。");
  }
  String _basicMessage = '';
  BasicMessageChannel<String>? _basicMessageChannel;

  @override
  void initState() {
    super.initState();
    _basicMessageChannel =
        BasicMessageChannel('BasicMessageChannelManager', StringCodec());

    //设置setMessageHandler，目的：为了能够接收来自Android的消息
    //Future：向Android回传消息
    _basicMessageChannel!.setMessageHandler((String? message) => Future<String>(() {
      setState(() {
        //Android --> Flutter
        _basicMessage = 'Android--->Flutter的消息：' + message!;
      });
      return "Flutter--->Android的消息1：收到了。";
    }));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("native与flutter通信之BasicMessageChannel"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_basicMessage',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
