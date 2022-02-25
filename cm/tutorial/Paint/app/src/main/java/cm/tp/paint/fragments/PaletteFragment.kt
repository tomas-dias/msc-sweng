package cm.tp.paint.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import cm.tp.paint.R

class PaletteFragment : Fragment(), View.OnClickListener
{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_palette, container, false)

        val btnred = view.findViewById<Button>(R.id.button_color_red)
        btnred.setOnClickListener { onClick(btnred) }

        val btnorange = view.findViewById<Button>(R.id.button_color_orange)
        btnorange.setOnClickListener { onClick(btnorange) }

        val btnyellow = view.findViewById<Button>(R.id.button_color_yellow)
        btnyellow.setOnClickListener { onClick(btnyellow) }

        val btngreen = view.findViewById<Button>(R.id.button_color_green)
        btngreen.setOnClickListener { onClick(btngreen) }

        val btnblue = view.findViewById<Button>(R.id.button_color_blue)
        btnblue.setOnClickListener { onClick(btnblue) }

        val btnpurple = view.findViewById<Button>(R.id.button_color_purple)
        btnpurple.setOnClickListener { onClick(btnpurple) }

        val btnpink = view.findViewById<Button>(R.id.button_color_pink)
        btnpink.setOnClickListener { onClick(btnpink) }

        val btnbrown = view.findViewById<Button>(R.id.button_color_brown)
        btnbrown.setOnClickListener { onClick(btnbrown) }

        val btnblack = view.findViewById<Button>(R.id.button_color_black)
        btnblack.setOnClickListener { onClick(btnblack) }

        return view
    }

    override fun onClick(view: View)
    {
        when (view.id)
        {
            R.id.button_color_red -> {
                CanvasFragment.paintCanvas.changeStroke(Color.parseColor("#F44336"))
            }
            R.id.button_color_orange -> {
                CanvasFragment.paintCanvas.changeStroke(Color.parseColor("#FF9800"))
            }
            R.id.button_color_yellow -> {
                CanvasFragment.paintCanvas.changeStroke(Color.parseColor("#FFEB3B"))
            }
            R.id.button_color_green -> {
                CanvasFragment.paintCanvas.changeStroke(Color.parseColor("#4CAF50"))
            }
            R.id.button_color_blue -> {
                CanvasFragment.paintCanvas.changeStroke(Color.parseColor("#2196F3"))
            }
            R.id.button_color_purple -> {
                CanvasFragment.paintCanvas.changeStroke(Color.parseColor("#FF6200EE"))
            }
            R.id.button_color_pink -> {
                CanvasFragment.paintCanvas.changeStroke(Color.parseColor("#E91E63"))
            }
            R.id.button_color_brown -> {
                CanvasFragment.paintCanvas.changeStroke(Color.parseColor("#513D37"))
            }
            else -> {
                CanvasFragment.paintCanvas.changeStroke(Color.parseColor("#FF000000"))
            }
        }
    }
}