// Copyright 2019 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/material.dart';

// This is on alternate entrypoint for this module to display Flutter UI in
// a (multi-)view integration scenario.
void main() {
  runApp(const Cell());
}

class Cell extends StatefulWidget {
  const Cell({super.key});

  @override
  State<StatefulWidget> createState() => _CellState();
}

class _CellState extends State<Cell> with WidgetsBindingObserver {
  AppLifecycleState? appLifecycleState;

  @override
  void initState() {
    // Keep track of what the current platform lifecycle state is.
    WidgetsBinding.instance.addObserver(this);
    super.initState();
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    setState(() {
      appLifecycleState = state;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      // The Flutter cells will be noticeably different (due to background color
      // and the Flutter logo). The banner breaks immersion.
      debugShowCheckedModeBanner: false,
      home: Container(
        color: Colors.white,
        constraints: BoxConstraints.tight(const Size(300, 300)),
        child: Builder(
          builder: (context) {
            return Card(
              // Mimic the platform Material look.
              margin: const EdgeInsets.symmetric(horizontal: 36, vertical: 24),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(16),
              ),
              elevation: 16,
              color: Theme.of(context).primaryColor,
              child: Stack(
                children: [
                  Center(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text(
                          // Show a number provided by the platform based on
                          // the cell's index.
                          "Flutter says,",
                          style: Theme.of(context).textTheme.displayMedium,
                        ),
                        Text(
                          // Show a number provided by the platform based on
                          // the cell's index.
                          "\"WTF\"",
                          style: Theme.of(context).textTheme.displaySmall,
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            );
          },
        ),
      ),
    );
  }
}
