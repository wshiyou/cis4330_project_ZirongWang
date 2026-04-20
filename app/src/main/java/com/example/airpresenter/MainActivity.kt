package com.example.airpresenter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private var xValue by mutableStateOf(0f)
    private var yValue by mutableStateOf(0f)
    private var zValue by mutableStateOf(0f)
    private var gestureText by mutableStateOf("No gesture")

    private var currentSlide by mutableIntStateOf(1)
    private var gestureStatus by mutableStateOf("ON")
    private var isGestureEnabled by mutableStateOf(true)

    private var lastGestureTime = 0L
    private val cooldown = 1000L
    private var gestureLocked = false
    private val neutralZone = 4f
    private val maxSlides = 50
    private var lastY = 0f



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        setContent {
            MaterialTheme {
                SensorScreen(
                    x = xValue,
                    y = yValue,
                    z = zValue,
                    gesture = gestureText,
                    slide = currentSlide,
                    gestureStatus = gestureStatus,
                    onRightTest = {
                        gestureText = "RIGHT SWING"
                        nextSlide()
                        sendCommand("next")
                        vibratePhone()
                    },
                    onLeftTest = {
                        gestureText = "LEFT SWING"
                        previousSlide()
                        sendCommand("prev")
                        vibratePhone()
                    },
                    onUpTest = {
                        toggleGesture()
                        gestureText = if (isGestureEnabled) "GESTURE ON" else "GESTURE OFF"
                        vibratePhone()
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            xValue = x
            yValue = y
            zValue = z

            detectGesture(x, y, z)
        }
    }

    private fun detectGesture(x: Float, y: Float, z: Float) {
        if (!isGestureEnabled) return
        val currentTime = System.currentTimeMillis()


        // 回到中间区域后才允许下一次左右手势
        if (gestureLocked && x > -neutralZone && x < neutralZone) {
            gestureLocked = false
        }

        if (currentTime - lastGestureTime < cooldown || gestureLocked) return

        when {
            x > 7 -> {
                gestureText = "RIGHT SWING"
                nextSlide()
                sendCommand("next")
                vibratePhone()
                lastGestureTime = currentTime
                gestureLocked = true
            }
            x < -7 -> {
                gestureText = "LEFT SWING"
                previousSlide()
                sendCommand("prev")
                vibratePhone()
                lastGestureTime = currentTime
                gestureLocked = true
            }

        }
    }

    private fun nextSlide() {
        if (currentSlide < maxSlides) {
            currentSlide++
        }
    }

    private fun previousSlide() {
        if (currentSlide > 1) {
            currentSlide--
        }
    }

    private fun toggleGesture() {
        isGestureEnabled = !isGestureEnabled
        gestureStatus = if (isGestureEnabled) "ON" else "OFF"
    }

    private fun vibratePhone() {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator
        vibrator.vibrate(100)
    }


    private fun sendCommand(command: String) {
        val database = Firebase.database
        val commandRef = database.getReference("airpresenter/command")
        commandRef.setValue(command)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}



@androidx.compose.runtime.Composable
fun SensorScreen(
    x: Float,
    y: Float,
    z: Float,
    gesture: String,
    slide: Int,
    gestureStatus: String,
    onRightTest: () -> Unit,
    onLeftTest: () -> Unit,
    onUpTest: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "AirPresenter",
            fontSize = 30.sp
        )

        Text(
            text = "Gesture-based presentation controller",
            fontSize = 16.sp
        )

        androidx.compose.material3.Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ){
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Current Gesture",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = gesture,
                    fontSize = 32.sp,
                    color = androidx.compose.ui.graphics.Color(0xFF5E4AE3)
                )
            }
        }

        androidx.compose.material3.Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ){
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Presentation Status",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Current Slide: $slide",
                    fontSize = 22.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                val statusColor = if (gestureStatus == "ON")
                    androidx.compose.ui.graphics.Color(0xFF2E7D32)
                else
                    androidx.compose.ui.graphics.Color(0xFFC62828)

                Text(
                    text = "Gesture: $gestureStatus",
                    fontSize = 22.sp,
                    color = statusColor
                )
            }
        }

        androidx.compose.material3.Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Sensor Data",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "X: %.2f".format(x), fontSize = 18.sp)
                Text(text = "Y: %.2f".format(y), fontSize = 18.sp)
                Text(text = "Z: %.2f".format(z), fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Manual Controls",
            fontSize = 20.sp
        )

        Button(
            onClick = onRightTest,
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
        ) {
            Text("Next Slide")
        }

        Button(
            onClick = onLeftTest,
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
        ) {
            Text("Previous Slide")
        }

        Button(
            onClick = onUpTest,
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
        ) {
            Text("Gesture ON/OFF")
        }
    }

}
