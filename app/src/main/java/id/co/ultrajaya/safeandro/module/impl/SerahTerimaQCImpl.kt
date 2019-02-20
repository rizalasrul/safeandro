package id.co.ultrajaya.safeandro.module.impl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.ultrajaya.safeandro.model.database.Database
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import id.co.ultrajaya.safeandro.module.contract.SerahTerimaQCContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SerahTerimaQCImpl : SerahTerimaQCContract.Presenter, SerahTerimaQCContract.DataInteractor {

    var _SerahTerimaQCView: SerahTerimaQCContract.View
    var _DataInteractor: SerahTerimaQCContract.DataInteractor
    var _Barcode: String = "";

    constructor(iSerahTerimaQCView: SerahTerimaQCContract.View) {
        this._SerahTerimaQCView = iSerahTerimaQCView
        this._DataInteractor = this
    }

    override fun onGetInfoBarcode(ibarcode: String) {
        _SerahTerimaQCView.showLoading()
        _Barcode = ibarcode
        val acall: Call<MainResp<ItemMaterial>> = Database.getInfoBarcodeSerahTerimaQC(ibarcode)

        acall.enqueue(object : Callback<MainResp<ItemMaterial>> {
            override fun onResponse(call: Call<MainResp<ItemMaterial>>, response: Response<MainResp<ItemMaterial>>) {
                val arawjson: String = Gson().toJson(response.body())

                val dataType = object : TypeToken<MainResp<ItemMaterial>>() {}.type
                val mainResp: MainResp<ItemMaterial> = Gson().fromJson<MainResp<ItemMaterial>>(arawjson, dataType)

                _DataInteractor.onPostGetInfoBarcode(mainResp)
            }

            override fun onFailure(call: Call<MainResp<ItemMaterial>>, t: Throwable) {
                _DataInteractor.onFailureGetInfoBarcode(t)
            }
        })
    }

    override fun onSaveDataBarcode() {
        _SerahTerimaQCView.showLoading()
        val acall: Call<MainResp<Any>> = Database.saveDataBarcodeSerahTerimaQC(_Barcode)

        acall.enqueue(object : Callback<MainResp<Any>> {
            override fun onResponse(call: Call<MainResp<Any>>, response: Response<MainResp<Any>>) {
                val arawjson: String = Gson().toJson(response.body())

                val dataType = object : TypeToken<MainResp<ItemMaterial>>() {}.type
                val mainResp: MainResp<Objects> = Gson().fromJson<MainResp<Objects>>(arawjson, dataType)

                _DataInteractor.onPostSaveDataBarcode(mainResp)
            }

            override fun onFailure(call: Call<MainResp<Any>>, t: Throwable) {
                _DataInteractor.onFailureGetInfoBarcode(t)
            }
        })
    }

    override fun onPostGetInfoBarcode(response: MainResp<ItemMaterial>) {
        if (response.getMeta()!!.getHttpStatus() == 200) {
            if (response.getData()!!.getNilai()!!.getTable()!!.isNotEmpty()) {
                _SerahTerimaQCView.setDataInfoBarcode(response.getData()!!.getNilai()!!.getTable()!![0])
            } else {
                _SerahTerimaQCView.showAlertDialog("Tidak data dengan barcode : " + _Barcode, 1)
                _SerahTerimaQCView.clearForm()
            }
        } else {
            _SerahTerimaQCView.showAlertDialog("Gagal mengambil data !", 1)
            _SerahTerimaQCView.clearForm()
        }
        _SerahTerimaQCView.hideLoading()
    }

    override fun onPostSaveDataBarcode(response: MainResp<Objects>) {
        if (response.getMeta()!!.getHttpStatus() == 200) {
            _SerahTerimaQCView.showAlertDialog(response.getData()!!.getDispMsg()!!, 2)
            _SerahTerimaQCView.clearForm()
        } else {
            _SerahTerimaQCView.showAlertDialog("Gagal menyimpan !", 1)
        }
        _SerahTerimaQCView.hideLoading()
    }


    override fun onFailureGetInfoBarcode(t: Throwable) {
        _SerahTerimaQCView.showAlertDialog(t.toString(), 1)
        _SerahTerimaQCView.hideLoading()
    }

    override fun onFailureSaveDataBarcode(t: Throwable) {
        _SerahTerimaQCView.showAlertDialog(t.toString(), 1)
        _SerahTerimaQCView.hideLoading()
    }
}