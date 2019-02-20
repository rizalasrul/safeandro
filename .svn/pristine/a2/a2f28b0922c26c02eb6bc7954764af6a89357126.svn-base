package id.co.ultrajaya.safeandro.module.contract

import id.co.ultrajaya.safeandro.model.response.general.ItemAnalisaQC
import id.co.ultrajaya.safeandro.model.response.general.ItemAnalisaQCORC
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import java.util.*

interface ReportAnalisaContract {
    interface View : _MainContract.MainView, _MainContract.MainAdapterContract.MainAdapterView{
        fun setDataInfoBarcode(itemMaterialList: List<ItemMaterial>)
    }

    interface Presenter : _MainContract.MainAdapterContract.MainAdapterPresenter{
        fun onGetInfoBarcode(ibarcode : String)
        fun onGetInfoBarcodeORC(itemSample : String)
        fun onSaveInfoBarcode(itemAnalisaQC: ItemAnalisaQC)
        fun onSaveInfoBarcodeORC(itemSample: String, ireportAnalisaList: ArrayList<ItemMaterial>)
    }

    interface Data : _MainContract.MainData{
        fun onPostGetInfoBarcode(response : MainResp<ItemMaterial>)
        fun onPostGetInfoBarcodeORC(response : MainResp<ItemAnalisaQCORC>)

        fun onPostSaveInfoSampleORC(response: MainResp<Objects>)
        fun onPostSaveInfoBarcode(response: MainResp<Objects>)
    }
}