package id.co.ultrajaya.safeandro.model.util.components

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import id.co.ultrajaya.safeandro.R
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.component_edittext_multi.view.*

class LayoutEditTextMulti {
    private var mEditText: View
    private var mContext: Context
    private var mLabelEditText1: String
    private var mLabelEditText2: String
    private var mIsReadOnly1: Boolean
    private var mIsReadOnly2: Boolean
    private var mInputType1: String? = null
    private var mInputType2: String? = null

    constructor(icontext: Context?, ieditText: View, ilabel1: String, ilabel2 : String, isReadOnly1: Boolean, isReadOnly2: Boolean) {
        this.mContext = icontext!!
        this.mEditText = ieditText
        this.mLabelEditText1 = ilabel1
        this.mLabelEditText2 = ilabel2
        this.mIsReadOnly1 = isReadOnly1
        this.mIsReadOnly2 = isReadOnly2
        setLayout()
    }

    //ada parameter untuk input type nya
    //dibuat contructor baru agar yang sudah ada tidak error dan merubah satu satu
    constructor(icontext: Context?, ieditText: View, ilabel1: String, ilabel2 : String, isReadOnly1: Boolean, isReadOnly2: Boolean, inputType1: String, inputType2: String) {
        this.mContext = icontext!!
        this.mEditText = ieditText
        this.mLabelEditText1 = ilabel1
        this.mLabelEditText2 = ilabel2
        this.mIsReadOnly1 = isReadOnly1
        this.mIsReadOnly2 = isReadOnly2
        this.mInputType1 = inputType1
        this.mInputType2 = inputType2
        setLayout()
    }

    fun setLayout() {
        mEditText.labelEditText1.text = mLabelEditText1
        mEditText.labelEditText2.text = mLabelEditText2

        mEditText.editText1.setGravity(Gravity.LEFT or Gravity.TOP)
        mEditText.editText2.setGravity(Gravity.LEFT or Gravity.TOP)

        mEditText.editText1.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mEditText.subParentEditText1.setBackgroundResource(R.drawable.shape_edittext_focus)
            } else {
                mEditText.subParentEditText1.setBackgroundResource(R.drawable.shape_edittext_unfocus)
            }
        }

        mEditText.editText2.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mEditText.subParentEditText2.setBackgroundResource(R.drawable.shape_edittext_focus)
            } else {
                mEditText.subParentEditText2.setBackgroundResource(R.drawable.shape_edittext_unfocus)
            }
        }

        //mEditText.editText2.inputType = InputType.TYPE_CLASS_TEXT
        setType(mEditText.editText1, mInputType1)
        setType(mEditText.editText2, mInputType2)
        setEnable(mEditText.editText1, mEditText.subParentEditText1, !mIsReadOnly1) //karena readonly dan enable bertolak belakang
        setEnable(mEditText.editText2, mEditText.subParentEditText2, !mIsReadOnly2) //karena readonly dan enable bertolak belakang
    }

    companion object {
        fun setEnable(ieditText: EditText, isubParentEditText: RelativeLayout, isEnable: Boolean) {
            ieditText.isEnabled = isEnable
            if (!isEnable) {
                isubParentEditText.setBackgroundColor(Color.LTGRAY)
            }
        }

        fun setText(ieditText: EditText, iText: String?) {
            ieditText.setText(iText)
        }

        fun getText(ieditText: EditText): String {
            return ieditText.text.toString()
        }

        fun setFocus(ieditText: EditText, icontext: Context?, isFocus: Boolean) {
            if (isFocus) {
                ieditText.requestFocus()
                val inputMethodManager = icontext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            }else{
                ieditText.clearFocus()
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
                    //do nothing karena sesuai dengan default
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

        fun setLines(ieditText: EditText, ilines : Int){
            ieditText.setLines(ilines)
        }
    }
}