package id.co.ultrajaya.safeandro.module.impl

import id.co.ultrajaya.safeandro.model.database.Database
import id.co.ultrajaya.safeandro.model.response.general.ItemAnalisaQCORC
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.response.general.ItemSpinner
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import id.co.ultrajaya.safeandro.model.util.Config
import id.co.ultrajaya.safeandro.module.contract.AnalisaSampleContract
import java.util.*
import kotlin.collections.ArrayList

class AnalisaSampleImpl : AnalisaSampleContract.Presenter, AnalisaSampleContract.Data {
    val _View: AnalisaSampleContract.View
    val _Data: AnalisaSampleContract.Data

    var _BarcodeUtama: String = ""
    var _TestID : String = ""

    lateinit var _tipeAnalisaSampleListORC: List<ItemAnalisaQCORC>
    lateinit var _tipeAnalisaSampleListSpinner: ArrayList<ItemSpinner>

    lateinit var _lovHasilList: List<ItemSpinner>

    constructor(ianalisaSampleView: AnalisaSampleContract.View) {
        _View = ianalisaSampleView
        _Data = this
    }

    override fun onGetInfoBarcode(ibarcode: String) {
        _View.showLoading()
        _BarcodeUtama = ibarcode
        Database.getInfoBarcodeAnalisaSample(ibarcode, this)
    }

    override fun onPostGetInfoBarcode(response: MainResp<ItemMaterial>) {
        if (response.getMeta()!!.getHttpStatus() == 200) {
            if (response.getData()!!.getNilai()!!.getTable()!!.isNotEmpty()) {
                _View.setDataInfoBarcode(response.getData()!!.getNilai()!!.getTable()!![0])
            } else {
                _View.showAlertDialog("Tidak ada data barcode : " + _BarcodeUtama, 1)
                _View.clearForm()
                _View.hideLoading()
            }
        } else {
            _View.showAlertDialog(Config.RESP_ERR, 1)
            _View.clearForm()
            _View.hideLoading()
            // tidak di taruh di luar kaerna setDataInfoBarcode memangggil onGetListTipeAnalisaSample langsung loading hide di bagian tsb
        }
    }

    override fun onFailureGetInfoBarcode(t: Throwable) {
        _View.showAlertDialog(t.toString(), 1)
        _View.hideLoading()
    }

    override fun onGetListTipeAnalisaSample(itemSample : String) {
        if (_BarcodeUtama != "") {
            Database.getInfoListTipeAnalisaORC(itemSample, this)
        } else {
            _View.showAlertDialog("Belum scan barcode", 1)
        }
    }

    //dicomment karena gak jadi pakai oracle
    /*override fun onGetListHasilAnalisaSample(itipe: String) {
        var alistSpinner = ArrayList<ItemSpinner>()
        var aitemSpinner : ItemSpinner

        if(itipe.equals("Warna")){
            var arrayWarna = arrayOf("Coklat","Coklat Kemerahan")
            arrayWarna.forEach {
                aitemSpinner = ItemSpinner()
                aitemSpinner.spinner = it
                alistSpinner.add(aitemSpinner)
            }
        }else{
            var arrayItem = arrayOf("Normal","Tidak Normal")
            arrayItem.forEach {
                aitemSpinner = ItemSpinner()
                aitemSpinner.spinner = it
                alistSpinner.add(aitemSpinner)
            }
        }
        _View.setListHasilAnalisaSample(alistSpinner)
    }*/

    override fun onPostGetListTipeAnalisaSample(response: MainResp<ItemAnalisaQCORC>) {
        if (response.getMeta()!!.getHttpStatus() == 200) {
            if (response.getData()!!.getNilai()!!.getTable()!!.isNotEmpty()) {
                _tipeAnalisaSampleListORC = response.getData()!!.getNilai()!!.getTable()!!

                _tipeAnalisaSampleListSpinner = ArrayList()
                for (ItemAnalisaQCORC in _tipeAnalisaSampleListORC) {
                    var itemSpinner = ItemSpinner()
                    itemSpinner.spinner = ItemAnalisaQCORC.testdesc
                    _tipeAnalisaSampleListSpinner.add(itemSpinner)
                }
                _View.setListDataTipeAnalisaSample(_tipeAnalisaSampleListSpinner)
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

    override fun onGetLOVResultAnalisaSampleORC(itestid: String) {
        _TestID = itestid
        if(!itestid.equals("")){
            Database.getInfoLOVListResultAnalisa(itestid, this)
        }else{
            _View.showAlertDialog("Test ID tersebut masih kosong !", 1)
        }
    }

    override fun onGetDetailLOV(itestDesc: String) {
        _View.showLoading()
        //untuk mendapatkan test id dan seq
        val aitemAnalisaORC = _tipeAnalisaSampleListORC.filter { it.testdesc.equals(itestDesc) }
        _View.setDetailLOV(aitemAnalisaORC[0])
    }

    override fun onPostGetLOVResultAnalisaSampleORC(response: MainResp<ItemSpinner>) {
        if (response.getMeta()!!.getHttpStatus() == 200) {
            if (response.getData()!!.getNilai()!!.getTable()!!.isNotEmpty()) {
                _lovHasilList = response.getData()!!.getNilai()!!.getTable()!!
                if(_lovHasilList.size > 0){
                    _View.setListHasilAnalisaSample(_lovHasilList)
                }else{
                    _View.showAlertDialog("List data LOV kosong !", 1)
                }
            } else {
                _View.showAlertDialog("Tidak ada data dengan Test ID : " + _TestID, 1)
            }
        } else {
            _View.showAlertDialog(Config.RESP_ERR, 1)
        }
        _View.hideLoading()
    }

    override fun onFailureGetListTipeAnalisaSample(t: Throwable) {
        _View.showAlertDialog(t.toString(), 1)
        _View.hideLoading()
    }

    override fun onSaveDataBarcode(itipe: String, ihasil: String, isAccept : Boolean, itemAnalisaQCORC: ItemAnalisaQCORC) {
        _View.showLoading()
        Database.saveInfoBarcodeAnalisaSample(_BarcodeUtama, itipe, ihasil, isAccept, this, itemAnalisaQCORC)
    }

    override fun onPostSaveDataBarcode(response: MainResp<Objects>) {
        if (response.getMeta()!!.getHttpStatus() == 200) {
            _View.showAlertDialog(response.getData()!!.getDispMsg()!!, 2)
            //_View.clearForm() //karena masih bisa input dalam satu beaker tipe yang lain maka tidak clear
        } else {
            _View.showAlertDialog(response.getErrors()!![0].getErrors()!!, 1)
        }
        _View.clearFormPostSave()
        _View.hideLoading()
    }

    override fun onFailureSaveDataBarcode(t: Throwable) {
        _View.showAlertDialog(t.toString(), 1)
        _View.hideLoading()
    }
}