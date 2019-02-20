package id.co.ultrajaya.safeandro.model.util.components

import android.graphics.Color
import android.view.View
import android.widget.Button
import id.co.ultrajaya.safeandro.R
import kotlinx.android.synthetic.main.component_button.view.*

class LayoutButtonMulti {
    constructor(ibutton: Button, ilabel: String, ilistener: LayoutButtonContract, itag: String) {
        ibutton.text = ilabel
        ibutton.tag = itag

        ibutton.setOnClickListener {
            ilistener.onClickButton(itag)
        }
    }

    companion object {
        //if no image input parameter 0, image R.drawabale.xxxx is int type
        fun setEnable(ibutton: Button, isEnable: Boolean, icon: Int, color : Int) {
            ibutton.isEnabled = isEnable
            if (!isEnable) {
                ibutton.setBackgroundColor(Color.rgb(236, 154, 91)) //color accent light
                ibutton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stop, 0, 0, 0)
                ibutton.setPadding(16, 0, 0, 0)
            } else {
                ibutton.setBackgroundColor(color) //color accent
                ibutton.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
                if (icon != 0)
                    ibutton.setPadding(16, 0, 0, 0)
                else
                    ibutton.setPadding(0, 0, 0, 0)
            }
        }
    }
}