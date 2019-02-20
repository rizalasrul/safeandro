package id.co.ultrajaya.safeandro.module.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import id.co.ultrajaya.safeandro.R
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.util.AlertDialogCustom
import id.co.ultrajaya.safeandro.model.util.LoadingDialog
import id.co.ultrajaya.safeandro.model.util.components.*
import id.co.ultrajaya.safeandro.module.contract.PindahBeakerContract
import id.co.ultrajaya.safeandro.module.impl.PindahBeakerImpl
import kotlinx.android.synthetic.main.activity_pindah_beaker.*
import id.co.ultrajaya.safeandro.model.util.QRScanner
import id.co.ultrajaya.safeandro.model.util.ConfigComponents
import id.co.ultrajaya.safeandro.module.adapter.PindahBeakerAdapter
import kotlinx.android.synthetic.main.component_recycleview.view.*
import java.util.*
import kotlin.collections.ArrayList

class PindahBeakerFragment : Fragment(), PindahBeakerContract.View, LayoutButtonContract,
    LayoutEditTextBarcodeContract {
    lateinit var _pindahBeakerImpl: PindahBeakerImpl

    //coba pake singleton class instance
    lateinit var _loadingDialog: LoadingDialog
    lateinit var _alertDialog: AlertDialogCustom

    var _barcodeList: ArrayList<String> = ArrayList()
    lateinit var _pindahBeakerAdapter: PindahBeakerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_pindah_beaker, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _alertDialog = AlertDialogCustom(activity!!)
        _loadingDialog = LoadingDialog(activity!!)
        _pindahBeakerImpl = PindahBeakerImpl(this)

        setComponent()
    }

    fun setComponent() {
        rvListBarcode.recycleView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _pindahBeakerAdapter = PindahBeakerAdapter(_barcodeList, this)
        rvListBarcode.recycleView.adapter = _pindahBeakerAdapter

        LayoutEditTextBarcode(context, etBarcode, "Barcode", false, this)
        LayoutEditTextBarcode.setFocus(etBarcode)
        LayoutEditText(context, etProdBatch, "Prodbatch", true)
        LayoutEditText(context, etIdMat, "ID Mat.", true)
        LayoutEditText(context, etNamaMat, "Nama Mat.", true)
        LayoutEditText(context, etLotNum, "Lotnum", true)
        LayoutEditText(context, etVol, "Volume", true, "number")
        LayoutButton(btAddBarcode, "ADD", this, "addbarcode")
        LayoutButton.setEnable(btAddBarcode, false, 0)
        LayoutRecyclerview(rvListBarcode, "List Barcode Beaker")
        LayoutButton(btSaveBarcode, "SAVE", this, "savebarcode")
        LayoutButton.setEnable(btSaveBarcode, false, 0)
    }

    fun callScanner(itag: String) {
        val aintent = Intent(activity, QRScanner::class.java)
        aintent.putExtra("tag", itag)
        startActivityForResult(aintent, 1)
    }

    override fun onClickButtonBarcode() {
        callScanner("qr_utama")
        _barcodeList.clear()
        _pindahBeakerAdapter.notifyDataSetChanged()
        //ConfigComponents.callScanner(activity!!, "qr_utama") * /
    }

    override fun onEnterKeyBarcode(icode: String) {
        _pindahBeakerImpl.onGetInfoBarcode(icode)
    }

    override fun setDataInfoBarcode(itemMaterial: ItemMaterial) {
        LayoutEditText.setText(etProdBatch, itemMaterial.prodbatch)
        LayoutEditText.setText(etIdMat, itemMaterial.produk)
        LayoutEditText.setText(etNamaMat, itemMaterial.nama)
        LayoutEditText.setText(etLotNum, itemMaterial.lotmanual)
        LayoutEditText.setText(etVol, ConfigComponents.convertStringToCurr(itemMaterial.vollt.toString()))
        LayoutButton.setEnable(btAddBarcode, true, R.drawable.ic_add)
        LayoutButton.setEnable(btSaveBarcode, true, R.drawable.ic_save)
    }

    override fun onRefreshList() {
        _pindahBeakerAdapter.notifyDataSetChanged()
    }

    override fun showErrorRV() {
        rvListBarcode.labelError.visibility = View.VISIBLE
    }

    override fun hideErrorRV() {
        rvListBarcode.labelError.visibility = View.GONE
    }

    //coba pake abstrack class dengan body yang otomatis implement
    override fun showLoading() {
        _loadingDialog.show()
    }

    override fun hideLoading() {
        _loadingDialog.hide()
    }

    override fun showAlertDialog(imsg: String, itipe: Int) {
        _alertDialog.showAlertDialog(imsg, itipe)
    }

    override fun showAlertDialogWithOptions(imsg: String, position: Int) {
        var alertDialog = AlertDialogCustom(activity!!, true, position, _pindahBeakerImpl._Adapter)
        alertDialog.showAlertDialog(imsg, 3)
    }

    override fun showToast(imsg: String?) {
        Toast.makeText(activity, imsg, Toast.LENGTH_SHORT).show()
    }

    override fun showAlertDialogAction(imsg: String, itipe: Int, itag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPostAlertDialogAction(itag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearForm() {
        val alistOfView = Arrays.asList(etProdBatch, etIdMat, etNamaMat, etLotNum, etVol)
        ConfigComponents.clearForm(alistOfView)
        LayoutEditTextBarcode.setFocus(etBarcode)
        LayoutEditTextBarcode.setText(etBarcode, "")
        LayoutButton.setEnable(btAddBarcode, false, 0)
        LayoutButton.setEnable(btSaveBarcode, false, 0)
    }

    override fun onClickButton(itag: String) {
        when (itag) {
            "savebarcode" -> {
                _pindahBeakerImpl.onSaveDataBarcode()
            }
            "addbarcode" -> {
                callScanner("qr_beaker")
                //ConfigComponents.callScanner(activity!!, "qr_beaker")
            }
            else -> showToast("Tidak tag id button tersebut")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val atag = data!!.getStringExtra("tag")
                val code = data.getStringExtra("code")
                when (atag) {
                    "qr_utama" -> {
                        LayoutEditTextBarcode.setText(etBarcode, code)
                        _pindahBeakerImpl.onGetInfoBarcode(code)
                    }
                    "qr_beaker" -> {
                        _pindahBeakerImpl.onAddBeaker(code, _barcodeList)
                    }
                }
            }
        }
    }
}