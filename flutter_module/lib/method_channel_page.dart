import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MethodChannelPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _MyHomePageState();
  }
}

class _MyHomePageState extends State<MethodChannelPage> {
  _MyHomePageState();

  void _incrementCounter() {
    // setState(() {
    //   _counter++;
    // });

    _methodChannel!
        .invokeMethod("callAndroidMethod", "Flutter--->Android的消息3，这次是Flutter主动的。") // 参数1:告诉Android要调用的方法名，参数2:传递的参数
        .then((result) { // invokeMethod().then() 来处理正常结束的逻辑（获得返回值）
      print('Android 回复 ---> Flutter的消息  $result');
      // 成功：通过result.success 返回值
      // 异常：通过 result.error 返回异常信息，可通过catchError 处理异常
    });
  }

  MethodChannel? _methodChannel;
  String _methodMessage = '';

  @override
  void initState() {
    super.initState();
    // 1.创建MethodChannel
    _methodChannel = new MethodChannel("MethodChannelManager");

    // 2. 根据Android的要求，调用对应方法
    _methodChannel!.setMethodCallHandler((handler) => Future<String>(() {
      print("Android端要调用的方法和参数是：${handler}");
      setState(() {
        _methodMessage = "Android端要调用Flutter的方法名为${handler.method},方法参数是${handler.arguments}";
      });
      switch (handler.method) {
        case "AndroidCallFlutterMethod":
          //handler.arguments表示native传递的方法参数
          testFun(handler.method, handler.arguments);
          break;
      }
      return "Flutter--->Android的消息3：收到了。";
    }));
  }

  void testFun(method, params) {
    print('Android要调用Flutter的方式名为$method，参数是$params');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("native与flutter通信之MethodChannelPage"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),Text(
              '$_methodMessage',
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
