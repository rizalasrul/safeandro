package id.co.ultrajaya.safeandro.model.util.components

import android.graphics.Color
import android.view.View
import id.co.ultrajaya.safeandro.R
import kotlinx.android.synthetic.main.component_button.view.*

class LayoutButton {
    constructor(ibutton: View, ilabel: String, ilistener: LayoutButtonContract, itag: String) {
        ibutton.button.text = ilabel
        ibutton.button.tag = itag

        ibutton.button.setOnClickListener {
            ilistener.onClickButton(itag)
        }
    }

    companion object {
        //if no image input parameter 0, image R.drawabale.xxxx is int type
        fun setEnable(ibutton: View, isEnable: Boolean, icon: Int) {
            ibutton.button.isEnabled = isEnable
            if (!isEnable) {
                ibutton.button.setBackgroundColor(Color.rgb(236, 154, 91)) //color accent light
                ibutton.button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stop, 0, 0, 0)
                ibutton.button.setPadding(16, 0, 0, 0)
            } else {
                ibutton.button.setBackgroundColor(Color.rgb(249, 109, 2)) //color accent
                ibutton.button.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
                if (icon != 0)
                    ibutton.button.setPadding(16, 0, 0, 0)
                else
                    ibutton.button.setPadding(0, 0, 0, 0)
            }
        }
    }
}