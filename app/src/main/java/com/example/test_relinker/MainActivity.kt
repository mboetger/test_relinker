package com.composenativehelloworld

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding // Added for spacing
import androidx.compose.foundation.lazy.LazyColumn // Added for the list
import androidx.compose.foundation.lazy.items // Added for iterating in LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp // Added for padding units
import androidx.compose.ui.viewinterop.AndroidView
import com.example.test_relinker.ui.theme.Test_relinkerTheme
import com.getkeepsafe.relinker.ReLinker
import io.flutter.FlutterInjector
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor

class MainActivity : ComponentActivity() {

    private lateinit var flutterViewEngine: FlutterViewEngine
    private lateinit var flutterView: FlutterView

    companion object {
        private const val LIBRARY_NAME = "native-lib";
        private var loaded = false;

        @JvmStatic
        fun loadLibrary(context: Context) {
            if (loaded) return;
            ReLinker.log { s -> Log.d("native-lib", s) }.loadLibrary(context, LIBRARY_NAME)
            loaded = true;
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        flutterViewEngine.detachActivity()
    }

    override fun onUserLeaveHint() {
        flutterViewEngine.onUserLeaveHint()
        super.onUserLeaveHint()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLibrary(this)
        // TODO: create a multi-engine version after
        // https://github.com/flutter/flutter/issues/72009 is built.
        val engine = FlutterEngine(applicationContext)
        engine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint(
                FlutterInjector.instance().flutterLoader().findAppBundlePath(),
                "showCell"))

        flutterViewEngine = FlutterViewEngine(engine)
        // The activity and FlutterView have different lifecycles.
        // Attach the activity right away but only start rendering when the
        // view is also scrolled into the screen.
        flutterViewEngine.attachToActivity(this)
        flutterView = FlutterView(this)
        flutterView.attachToFlutterEngine(engine);

        setContent {
            Test_relinkerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().padding(vertical = 100.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingList(flutterView = flutterView)
                }
            }
        }
    }

    external fun stringFromJNI(): String
}

@Composable
fun GreetingList(flutterView: FlutterView, modifier: Modifier = Modifier) {
    // Create a list of 6 items (you can make this dynamic)
    val greetingItems = (1..6).toList()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp), // Adds space between items
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(greetingItems) { itemNumber ->
            if (itemNumber == 3) {
                AndroidView(
                    factory = { context ->
                        flutterView.apply {}
                    },
                    modifier = Modifier.padding(16.dp).height(200.dp),
                )
            } else {
                Greeting(
                    modifier = Modifier.padding(16.dp)
                        .border(3.dp, MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    // We'll keep the original Greeting's Column structure for each item
    Column(
        modifier = modifier, // Use the passed modifier
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        if (context is MainActivity) {
            Text(text = "Message from Native C++: ${context.stringFromJNI()}", modifier = Modifier.padding(10.dp))
        }

    }
}