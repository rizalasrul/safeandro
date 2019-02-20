package id.co.ultrajaya.safeandro.module.activity

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import id.co.ultrajaya.safeandro.R
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.util.AlertDialogCustom
import id.co.ultrajaya.safeandro.model.util.LoadingDialog
import id.co.ultrajaya.safeandro.model.util.QRScanner
import id.co.ultrajaya.safeandro.model.util.components.*
import id.co.ultrajaya.safeandro.module.contract.SerahTerimaQCContract
import id.co.ultrajaya.safeandro.module.impl.SerahTerimaQCImpl
import kotlinx.android.synthetic.main.activity_serah_terima_qc.*
import id.co.ultrajaya.safeandro.model.util.ConfigComponents
import java.util.*

class SerahTerimaQCFragment : Fragment(), SerahTerimaQCContract.View, LayoutButtonContract, LayoutEditTextBarcodeContract {
    lateinit var _serahTerimaQCImpl : SerahTerimaQCImpl

    lateinit var _loadingDialog: LoadingDialog;
    lateinit var _alertDialog: AlertDialogCustom;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_serah_terima_qc, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setComponent()

        _alertDialog = AlertDialogCustom(activity!!)
        _loadingDialog = LoadingDialog(activity!!)
        _serahTerimaQCImpl = SerahTerimaQCImpl(this)
    }

    fun setComponent() {
        LayoutEditTextBarcode(context, etBarcode, "Barcode", false, this)
        LayoutEditTextBarcode.setFocus(etBarcode)
        LayoutEditText(context, etProdBatch, "Prodbatch", true)
        LayoutEditText(context, etIdMat, "ID Mat.", true)
        LayoutEditText(context, etNamaMat, "Nama Mat.", true)
        LayoutEditText(context, etLotNum, "Lotnum", true)
        LayoutEditText(context, etVol, "Volume", true)
        LayoutButton(btSave, "SAVE", this, "savebarcode")
        LayoutButton.setEnable(btSave, false, 0)
    }

    override fun onClickButtonBarcode() {
        val aintent = Intent(activity, QRScanner::class.java)
        startActivityForResult(aintent, 1)
    }

    override fun onEnterKeyBarcode(icode: String) {
        _serahTerimaQCImpl.onGetInfoBarcode(icode)
    }

    override fun onClickButton(itag: String) {
        when (itag) {
            "savebarcode" -> {
                _serahTerimaQCImpl.onSaveDataBarcode()
            }
            else -> showToast("Tidak tag id button tersebut")
        }
    }

    override fun showLoading() {
        _loadingDialog.show()
    }

    override fun hideLoading() {
        _loadingDialog.hide()
    }

    override fun showAlertDialog(imsg: String, itipe: Int) {
        _alertDialog.showAlertDialog(imsg, itipe)
    }

    override fun showToast(imsg: String?) {
        Toast.makeText(activity, imsg, Toast.LENGTH_LONG).show()
    }

    override fun setDataInfoBarcode(itemMaterial : ItemMaterial) {
        LayoutEditText.setText(etProdBatch, itemMaterial.prodbatch)
        LayoutEditText.setText(etIdMat, itemMaterial.produk)
        LayoutEditText.setText(etNamaMat, itemMaterial.nama)
        LayoutEditText.setText(etLotNum, itemMaterial.lotmanual)
        LayoutEditText.setText(etVol, itemMaterial.vollt.toString())
        LayoutButton.setEnable(btSave, true, R.drawable.ic_save)
    }

    override fun clearForm() {
        val alistOfView = Arrays.asList(etProdBatch , etIdMat, etNamaMat, etLotNum, etVol)
        ConfigComponents.clearForm(alistOfView)
        LayoutEditTextBarcode.setFocus(etBarcode)
        LayoutEditTextBarcode.setText(etBarcode,  "")
        LayoutButton.setEnable(btSave, false, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val code = data!!.getStringExtra("code")
                LayoutEditTextBarcode.setText(etBarcode, code)
                _serahTerimaQCImpl.onGetInfoBarcode(code)
            }
        }
    }
}