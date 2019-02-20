package id.co.ultrajaya.safeandro.model.util

import android.app.AlertDialog
import android.content.Context
import dmax.dialog.SpotsDialog

class LoadingDialog {
    private var _Context : Context
    private var _Dialog: AlertDialog

    constructor(icontext : Context){
        this._Context = icontext

        _Dialog = SpotsDialog.Builder().setContext(_Context).build()
        _Dialog.setMessage("Please wait...")
    }

    fun show(){
        _Dialog.show()
    }

    fun hide(){
        _Dialog.hide()
    }
}