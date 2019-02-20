package id.co.ultrajaya.safeandro.model.util.components

import android.view.View
import kotlinx.android.synthetic.main.component_recycleview.view.*

class LayoutRecyclerview {
    constructor(irecyclerview: View, ilabelHeader: String) {
        irecyclerview.labelRV.text = ilabelHeader
    }

    companion object {
        fun setError(irecyclerview: View, isVisible : Boolean){
            if(isVisible){
                irecyclerview.labelError.visibility = View.VISIBLE
                irecyclerview.recycleView.visibility = View.GONE
            }else{
                //GONE berarti textviewnya hilang sehingga space untuk textview tersebut juga hilang
                //INVISIBLE masih ada tapi tidak terlihat jadi masih ada spacenya
                irecyclerview.labelError.visibility = View.GONE
                irecyclerview.recycleView.visibility = View.VISIBLE
            }
        }
    }
}