package cm.tp.paint.listeners

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import cm.tp.paint.views.PaintCanvas
import kotlin.math.sqrt

class SensorListener : SensorEventListener
{
    private var mAccel = 0.00f
    private var mAccelCurrent = SensorManager.GRAVITY_EARTH
    private var mAccelLast = SensorManager.GRAVITY_EARTH
    private lateinit var canvas: PaintCanvas

    fun setCanvas(canvas: PaintCanvas) {
        this.canvas = canvas
    }

    override fun onSensorChanged(p0: SensorEvent) {

        if(p0.sensor.type == Sensor.TYPE_ACCELEROMETER)
        {
            val x = p0.values[0]
            val y = p0.values[1]
            val z = p0.values[2]

            mAccelLast = mAccelCurrent

            mAccelCurrent = sqrt((x*x + y*y + z*z))

            val delta = mAccelCurrent - mAccelLast
            mAccel = mAccel * 0.9f + delta

            if (mAccel > 12) {
                canvas.erase()
            }
        }
        else if (p0.sensor.type == Sensor.TYPE_GYROSCOPE)
        {
            when {
                p0.values[0] > 1.0f -> canvas.changeBackground(Color.parseColor("#F44336"))
                p0.values[0] < -1.0f -> canvas.changeBackground(Color.parseColor("#2196F3"))
                p0.values[1] > 1.0f -> canvas.changeBackground(Color.parseColor("#4CAF50"))
                p0.values[1] < -1.0f -> canvas.changeBackground(Color.parseColor("#FFEB3B"))
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
}