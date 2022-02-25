package cm.tp.paint.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cm.tp.paint.R
import cm.tp.paint.listeners.GestureListener
import cm.tp.paint.listeners.SensorListener
import cm.tp.paint.views.PaintCanvas

class CanvasFragment : Fragment()
{
    companion object {
        lateinit var paintCanvas : PaintCanvas
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null)
        {
            val mGestureListener = GestureListener()
            val mGestureDetector = GestureDetector(context, mGestureListener)
            mGestureDetector.setIsLongpressEnabled(true)
            mGestureDetector.setOnDoubleTapListener(mGestureListener)

            paintCanvas = PaintCanvas(context, null, mGestureDetector)
            mGestureListener.setCanvas(paintCanvas)

            val mSensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

            val mSensorListener = SensorListener()
            mSensorListener.setCanvas(paintCanvas)

            mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
                mSensorManager.registerListener(
                    mSensorListener,
                    accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL,
                    SensorManager.SENSOR_DELAY_UI
                )
            }

            mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.also { gyroscope ->
                mSensorManager.registerListener(
                    mSensorListener,
                    gyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL,
                    SensorManager.SENSOR_DELAY_UI
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return paintCanvas

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_canvas, container, false)
    }
}