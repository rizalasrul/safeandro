package id.co.ultrajaya.safeandro.module.impl

import id.co.ultrajaya.safeandro.model.database.Database
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import id.co.ultrajaya.safeandro.model.util.Config
import id.co.ultrajaya.safeandro.module.contract.PindahBeakerContract
import id.co.ultrajaya.safeandro.module.contract._MainContract
import java.util.*
import kotlin.collections.ArrayList

class PindahBeakerImpl : PindahBeakerContract.Presenter, PindahBeakerContract.Data {
    val _View : PindahBeakerContract.View
    val _Data : PindahBeakerContract.Data

    //ini untuk bagian two option alert dialog misal delete dan cancel
    //_Adapter ini di paggil dibagian pindah beaker fragment untuk di kirim ke alert dialog custom class
    //
    val _Adapter : _MainContract.MainAdapterContract.MainAdapterPresenter

    var _BarcodeUtama : String = ""
    lateinit var _BarcodeList : ArrayList<String>

    constructor(ipindahBeaker : PindahBeakerContract.View){
        _View = ipindahBeaker
        _Data  = this
        _Adapter = this
    }
    override fun onGetInfoBarcode(ibarcode: String) {
        _View.showLoading()
        _BarcodeUtama = ibarcode
        Database.getInfoBarcodePindahBeaker(ibarcode, this)
    }

    override fun onPostGetInfoBarcode(response: MainResp<ItemMaterial>) {
        if(response.getMeta()!!.getHttpStatus() == 200){
            if(response.getData()!!.getNilai()!!.getTable()!!.isNotEmpty()){
                _View.setDataInfoBarcode(response.getData()!!.getNilai()!!.getTable()!![0])
            }else{
                _View.showAlertDialog("Tidak ada data barcode : " + _BarcodeUtama, 1)
                _View.clearForm()
            }
        }else{
            _View.showAlertDialog(Config.RESP_ERR, 1)
            _View.clearForm()
        }
        _View.hideLoading()
    }

    override fun onFailureGetInfoBarcode(t: Throwable) {
        _View.showAlertDialog(t.toString(), 1)
        _View.hideLoading()
    }

    override fun onAddBeaker(ibarcode: String, ibarcodeList : ArrayList<String>) {
        _BarcodeList = ibarcodeList
        _BarcodeList.add(ibarcode)
        _View.onRefreshList()
        _View.hideErrorRV()
    }

    override fun onSaveDataBarcode() {
        if(_BarcodeList.size > 0) {
            _View.showLoading()
            Database.saveInfoBarcodePindahBeaker(_BarcodeUtama, _BarcodeList, this)
        }else{
            _View.showAlertDialog("List barcode masih kosong !", 1)
        }
    }

    override fun onPostSaveDataBarcode(response: MainResp<Objects>) {
        if (response.getMeta()!!.getHttpStatus() == 200) {
            _View.showAlertDialog(response.getData()!!.getDispMsg()!!, 2)
            _View.clearForm()
        } else {
            _View.showAlertDialog(response.getErrors()!![0].getErrors()!!, 1)
        }
        _View.hideLoading()
    }

    override fun onFailureSaveDataBarcode(t: Throwable) {
        _View.showAlertDialog(t.toString(), 1)
        _View.hideLoading()
    }

    override fun onDeleteItemRV(iposition : Int) {
        _BarcodeList.removeAt(iposition)
        _View.onRefreshList()
        if(_BarcodeList.size == 0){
            _View.showErrorRV()
        }
    }
}