package cm.tp.paint.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.collections.LinkedHashMap

class PaintCanvas : View, OnTouchListener
{
    private var paint = Paint()
    private var path = Path()
    private val pathList = LinkedHashMap<Path, Paint>()
    private var backGroundColor = Color.WHITE
    private var mGestureDetector: GestureDetector? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setOnTouchListener(this)
        setBackgroundColor(backGroundColor)
        isSaveEnabled = true
        initPaint()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        mGestureDetector: GestureDetector?,
    ) : super(context, attrs) {
        this.mGestureDetector = mGestureDetector
        setOnTouchListener(this)
        setBackgroundColor(backGroundColor)
        isSaveEnabled = true
        initPaint()
    }

    override fun onDraw(canvas: Canvas) {

        // draws the path with the pain
        pathList.forEach { (k, v) -> canvas.drawPath(k, v) }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        mGestureDetector?.onTouchEvent(event)
        return false // let the event go to the rest of the listeners
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventX = event.x
        val eventY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(eventX, eventY) // updates the path initial point
                return true
            }
            MotionEvent.ACTION_MOVE -> path.lineTo(
                eventX,
                eventY
            ) // makes a line to the point each time this event is fired
            MotionEvent.ACTION_UP -> performClick()
            else -> return false
        }

        // Schedules a repaint.
        invalidate()
        return true
    }

    fun changeBackground(color: Int) {
        backGroundColor = color
        setBackgroundColor(backGroundColor)
    }

    fun changeStroke(color : Int) {
        path = Path()
        paint = Paint()
        initPaint()
        paint.color = color
    }

    fun erase() {
        val saveColor = paint.color
        pathList.clear()
        path = Path()
        paint = Paint()
        initPaint()
        paint.color = saveColor

        invalidate()
    }

    private fun initPaint() {
        paint.isAntiAlias = true
        paint.strokeWidth = 20f
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        pathList[path] = paint
    }
}
