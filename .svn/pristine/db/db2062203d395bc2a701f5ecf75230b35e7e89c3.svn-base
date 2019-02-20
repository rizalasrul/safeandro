package id.co.ultrajaya.safeandro.model.util.components

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.icu.lang.UCharacter
import android.text.InputType
import android.text.Layout
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.component_edittext.view.*
import android.view.View.OnFocusChangeListener
import android.view.View.TEXT_ALIGNMENT_TEXT_END
import id.co.ultrajaya.safeandro.R
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.support.v4.content.ContextCompat.getSystemService
import android.view.inputmethod.InputMethodManager
import android.support.v4.content.ContextCompat.getSystemService
import android.widget.EditText

class LayoutEditText {
    private var mEditText: View
    private var mLabelEditText: String
    private var mContext: Context
    private var mIsReadOnly: Boolean
    private var mInputType: String? = null

    constructor(icontext: Context?, ieditText: View, ilabel: String, isReadOnly: Boolean) {
        this.mEditText = ieditText
        this.mLabelEditText = ilabel
        this.mContext = icontext!!
        this.mIsReadOnly = isReadOnly
        setLayout()
    }

    //ada parameter untuk input type nya
    //dibuat contructor baru agar yang sudah ada tidak error dan merubah satu satu
    constructor(icontext: Context?, ieditText: View, ilabel: String, isReadOnly: Boolean, inputType: String) {
        this.mEditText = ieditText
        this.mLabelEditText = ilabel
        this.mContext = icontext!!
        this.mIsReadOnly = isReadOnly
        this.mInputType = inputType
        setLayout()
    }

    fun setLayout() {
        mEditText.labelEditText.text = mLabelEditText
        mEditText.editText.setGravity(Gravity.LEFT or Gravity.TOP)

        mEditText.editText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mEditText.subParentEditText.setBackgroundResource(R.drawable.shape_edittext_focus)
            } else {
                mEditText.subParentEditText.setBackgroundResource(R.drawable.shape_edittext_unfocus)
            }
        }

        setType(mEditText.editText, mInputType)
        setEnable(mEditText, !mIsReadOnly) //karena readonly dan enable bertolak belakang
    }

    companion object {
        fun setEnable(ieditText: View, isEnable: Boolean) {
            ieditText.editText.isEnabled = isEnable
            if (!isEnable) {
                ieditText.subParentEditText.setBackgroundColor(Color.LTGRAY)
            }else{
                ieditText.subParentEditText.setBackgroundColor(Color.WHITE)
            }
        }

        fun setText(ieditText: View, iText: String?) {
            ieditText.editText.setText(iText)
        }

        fun getText(ieditText: View): String {
            return ieditText.editText.text.toString()
        }

        fun setFocus(ieditText: View, icontext: Context?, isFocus: Boolean) {
            if (isFocus) {
                ieditText.editText.requestFocus()
                ieditText.subParentEditText.setBackgroundResource(R.drawable.shape_edittext_focus)
                val inputMethodManager = icontext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            }else{
                ieditText.editText.clearFocus()
                ieditText.subParentEditText.setBackgroundResource(R.drawable.shape_edittext_unfocus)
                val inputMethodManager = icontext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0)
            }
        }

        fun setType(ieditText: EditText, inputType: String?){
            when (inputType) {
                "number" -> {
                    ieditText.inputType = InputType.TYPE_CLASS_NUMBER
                    ieditText.gravity = Gravity.RIGHT
                }
                "text" -> {
                    //do nothing karena default sudah text
                    //kalau di tambah syntax di bawah ini jadinya malah tidak bisa enter multi line wrap content
                    //ieditText.inputType = InputType.TYPE_CLASS_TEXT
                }
                "decimal" -> {
                    ieditText.gravity = Gravity.RIGHT
                    ieditText.inputType = (InputType.TYPE_CLASS_NUMBER
                            or InputType.TYPE_NUMBER_FLAG_DECIMAL
                            or InputType.TYPE_NUMBER_FLAG_SIGNED)
                }
                else -> {
                    //do nothing karena default sudah text
                    //kalau di tambah syntax di bawah ini jadinya malah tidak bisa enter multi line wrap content
                    //ieditText.inputType = InputType.TYPE_CLASS_TEXT
                }
            }
        }

        fun setLines(ieditText: View, ilines : Int){
            ieditText.editText.setLines(ilines)
        }

        fun setVisibility(ieditText: View , isVisible : Boolean){
            if(isVisible){
                ieditText.visibility = View.VISIBLE
            }else{
                ieditText.visibility = View.GONE
            }
        }
    }
}