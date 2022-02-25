package cm.tp.paint.listeners

import android.view.GestureDetector.SimpleOnGestureListener
import android.view.GestureDetector
import android.view.MotionEvent
import cm.tp.paint.views.PaintCanvas

class GestureListener : SimpleOnGestureListener(), GestureDetector.OnDoubleTapListener
{
    private lateinit var canvas: PaintCanvas

    fun setCanvas(canvas: PaintCanvas) {
        this.canvas = canvas
    }

    ////////SimpleOnGestureListener
    /*
    override fun onLongPress(motionEvent: MotionEvent) {
        canvas?.changeBackground()
    }
    */

    /////////OnDoubleTapListener
    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
        canvas.erase()
        return false
    }
}