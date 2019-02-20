package id.co.ultrajaya.safeandro.model.util.components

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.component_spinner.view.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.view.ViewGroup
import android.widget.Spinner
import id.co.ultrajaya.safeandro.R
import id.co.ultrajaya.safeandro.model.response.general.ItemSpinner
import id.co.ultrajaya.safeandro.model.util.Config
import kotlin.collections.ArrayList

class LayoutSpinner {
    constructor(ispinner: View, ilabel: String, ilistener: LayoutSpinnerContract, itag : String) {
        ispinner.labelSpinner.text = ilabel

        ispinner.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == Config.HEADER_ITEM_SPINNER)
                    (ispinner.spinner.getChildAt(0) as TextView).setTextColor(Color.DKGRAY)
                else
                    ilistener.onChangeItemSpinnerListener(selectedItem, itag)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //do nothing
            }
        }
    }

    companion object {
        fun setListItemSpinner(icontext: Context, ispinner: View, ilistItem : ArrayList<ItemSpinner>) {
            val aitemSpinnerHeader = ItemSpinner()
            aitemSpinnerHeader.spinner = Config.HEADER_ITEM_SPINNER
            ilistItem.add(0, aitemSpinnerHeader)
            val spinnerArrayAdapter = object : ArrayAdapter<ItemSpinner>(icontext, R.layout.util_spinner_item, ilistItem) {
                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }

                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    val tv = view as TextView
                    if (position == 0) {
                        tv.setTextColor(Color.DKGRAY)
                    } else {
                        tv.setTextColor(ContextCompat.getColor(icontext, R.color.black))
                    }
                    return view
                }
            }

            spinnerArrayAdapter.setDropDownViewResource(R.layout.util_spinner_item)
            ispinner.spinner.adapter = spinnerArrayAdapter
        }

        fun setFocus(ispinner : View){
            ispinner.spinner.requestFocus()
        }

        fun setEnable(ispinner: View, isEnable: Boolean) {
            ispinner.spinner.isEnabled = isEnable
            if(!isEnable){
                ispinner.subParentSpinner.setBackgroundColor(Color.LTGRAY)
            }else{
                ispinner.subParentSpinner.setBackgroundColor(Color.WHITE)
                ispinner.subParentSpinner.setBackgroundResource(R.drawable.shape_edittext_unfocus)
            }
        }

        fun setEmpty(ispinner: View){
            ispinner.spinner.adapter = null
            setEnable(ispinner, false)
        }

        fun getText(ispinner: View) : String{
            return ispinner.spinner.selectedItem.toString()
        }

        fun setVisibility(ispinner : View, isVisible : Boolean){
            if(isVisible){
                ispinner.visibility = View.VISIBLE
            }else{
                ispinner.visibility = View.GONE
            }
        }
    }
}