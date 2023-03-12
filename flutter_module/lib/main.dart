import 'package:flutter/material.dart';
import 'dart:ui';
import 'basic_message_channel_page.dart';
import 'event_channel.dart';
import 'method_channel_page.dart';

// 必须要使用window.defaultRouteName来获取从native层传递过来的参数
void main() => runApp(MyApp(window.defaultRouteName));

class MyApp extends StatelessWidget {
  String initParam;

  MyApp(this.initParam, {super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: selectPage(),
    );
  }

  Widget selectPage() {
    switch (initParam) {
      case "BasicMessageChannel":
        return BasicMessageChannelPage();
      case "MethodChannel":
        return MethodChannelPage();
      case "EventChannel":
        return EventChannelPage();
      default:
        return MyHomePage();
    }
  }
}

class MyHomePage extends StatefulWidget {

  MyHomePage();

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  _MyHomePageState();

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("普通的flutter页面"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              '啥也不操作:',
            ),
            Text(
              '$_counter',
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
