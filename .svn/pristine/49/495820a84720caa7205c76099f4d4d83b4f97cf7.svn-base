package id.co.ultrajaya.safeandro.module.impl

import id.co.ultrajaya.safeandro.model.database.Database
import id.co.ultrajaya.safeandro.model.response.general.ItemAnalisaQC
import id.co.ultrajaya.safeandro.model.response.general.ItemAnalisaQCORC
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import id.co.ultrajaya.safeandro.model.util.Config
import id.co.ultrajaya.safeandro.module.contract.ReportAnalisaContract
import java.util.*

class ReportAnalisaImpl : ReportAnalisaContract.Presenter, ReportAnalisaContract.Data {
    val _View: ReportAnalisaContract.View
    private lateinit var _InfoSampleORCList: List<ItemAnalisaQCORC>
    var _BarcodeUtama: String = ""
    var _ItemSample: String = ""

    var _ErrMsg : String = ""
    var _DispMsg : String = ""

    constructor(ianalisaSampleView: ReportAnalisaContract.View) {
        _View = ianalisaSampleView
    }

    override fun onGetInfoBarcode(ibarcode: String) {
        _View.showLoading()
        _BarcodeUtama = ibarcode
        Database.getInfoBarcodeReportAnalisa(ibarcode, this)
    }

    override fun onPostGetInfoBarcode(response: MainResp<ItemMaterial>) {
        if (response.getMeta()!!.getHttpStatus() == 200) {
            if (response.getData()!!.getNilai()!!.getTable()!!.isNotEmpty()) {
                _View.setDataInfoBarcode(response.getData()!!.getNilai()!!.getTable()!!)
            } else {
                _View.showAlertDialog("Tidak ada data barcode : " + _BarcodeUtama, 1)
                _View.clearForm()
            }
        } else {
            _View.showAlertDialog(Config.RESP_ERR, 1)
            _View.clearForm()
        }
        _View.hideLoading()
    }

    //tidak jadi digunakan karena sudah diambil di bagian analisa
    override fun onGetInfoBarcodeORC(itemSample: String) {
        _ItemSample = itemSample
        Database.getInfoSampleORC(itemSample, this)
    }

    override fun onPostGetInfoBarcodeORC(response: MainResp<ItemAnalisaQCORC>) {
        if (response.getMeta()!!.getHttpStatus() == 200) {
            if (response.getData()!!.getNilai()!!.getTable()!!.isNotEmpty()) {
                _InfoSampleORCList = response.getData()!!.getNilai()!!.getTable()!!
            } else {
                _View.showAlertDialog("Tidak ada data ORC untuk sample item : " + _ItemSample, 1)
                _View.clearForm()
            }
        } else {
            _View.showAlertDialog(Config.RESP_ERR, 1)
            _View.clearForm()
        }
        _View.hideLoading()
    }

    override fun onFailureResponse(itag: String, t: Throwable) {
        _View.showAlertDialog(itag + " - " + t.toString(), 1)
        _View.hideLoading()
    }

    override fun onDeleteItemRV(iposition: Int) {
        //tidak berfungsi karena tidak menggunakan fitur delete di adapter
    }

    override fun onSaveInfoBarcode(itemAnalisaQC: ItemAnalisaQC) {
        if (!_BarcodeUtama.equals("")) {
            _View.showLoading()
            Database.saveReportAnalisa(itemAnalisaQC, this)
        } else {
            _View.showAlertDialog("Input barcode terlebih dahulu", 1)
        }
    }

    override fun onPostSaveInfoBarcode(response: MainResp<Objects>) {
        if (response.getMeta()!!.getHttpStatus() == 200) {
            _DispMsg = "[SQL] " + response.getData()!!.getDispMsg()!!
            _View.showAlertDialog(_DispMsg, 2)
        } else {
            _ErrMsg = "[SQL] " + response.getData()!!.getDispMsg()!!
            _View.showAlertDialog(_ErrMsg, 1)
        }
        _View.hideLoading()
    }

    override fun onSaveInfoBarcodeORC(itemSample: String, ireportAnalisaList: ArrayList<ItemMaterial>) {
        if(ireportAnalisaList.size > 0){
            _View.showLoading()
            Database.saveInfoSampleORC(itemSample, ireportAnalisaList, this)
        }else{
            _View.showAlertDialog("Tidak ada list data yang akan disimpan",1)
        }
    }

    override fun onPostSaveInfoSampleORC(response: MainResp<Objects>) {
        if (response.getMeta()!!.getHttpStatus() == 200) {
            _View.showAlertDialog(_DispMsg + "\n[ORC] " + response.getData()!!.getDispMsg()!!, 2)
            _View.clearForm()
        } else {
            _View.showAlertDialog(_ErrMsg + "\n[ORC] " + response.getErrors()!![0].getErrors()!!, 1)
        }
        _View.hideLoading()
    }
}