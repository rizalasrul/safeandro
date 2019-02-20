package id.co.ultrajaya.safeandro.model.util.components

import android.content.Context
import android.graphics.Color
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import id.co.ultrajaya.safeandro.R
import kotlinx.android.synthetic.main.component_edittext_barcode.view.*

class LayoutEditTextBarcode {
    private var mEditText  : View
    private var mLabelEditText : String
    private var mContext : Context
    private var mIsReadOnly : Boolean
    private var mListener : LayoutEditTextBarcodeContract

    constructor(icontext : Context? , ieditTextBarcode : View, ilabel : String, isReadOnly : Boolean, ilistener : LayoutEditTextBarcodeContract) {
        this.mEditText  = ieditTextBarcode
        this.mLabelEditText = ilabel
        this.mContext = icontext!!
        this.mIsReadOnly = isReadOnly
        this.mListener = ilistener

        ieditTextBarcode.imageButton.setOnClickListener {
            mListener.onClickButtonBarcode()
        }
        setLayout()
    }

    fun setLayout(){
        mEditText.labelEditText.text = mLabelEditText

        mEditText.editText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mEditText.editText.setText("")
                mEditText.subParentEditText.setBackgroundResource(R.drawable.shape_edittext_focus)
            }else{
                mEditText.subParentEditText.setBackgroundResource(R.drawable.shape_edittext_unfocus)
            }
        }

        mEditText.editText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                var abarcode = mEditText.editText.text.trim().toString()
                mEditText.editText.setText(abarcode)
                mListener.onEnterKeyBarcode(abarcode)
                hideSoftKeyboard()
                return@OnKeyListener true
            }
            false
        })
        setEnable(mEditText, !mIsReadOnly) //karena readonly dan enable bertolak belakang
    }

    fun hideSoftKeyboard() {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.editText.applicationWindowToken, 0)
    }

    companion object {
        fun setEnable(ieditTextBarcode : View, isEnableBarcode : Boolean){
            ieditTextBarcode.editText.isEnabled = isEnableBarcode
            if(isEnableBarcode){
                ieditTextBarcode.subParentEditText.setBackgroundColor(Color.WHITE)
            }else{
                ieditTextBarcode.editText.clearFocus()
                ieditTextBarcode.subParentEditText.setBackgroundColor(Color.LTGRAY)
            }
        }

        fun setText(ieditTextBarcode : View, iTextBarcode : String){
            ieditTextBarcode.editText.setText(iTextBarcode)
        }

        fun setFocus(ieditTextBarcode : View){
            ieditTextBarcode.editText.requestFocus()
        }
    }
}