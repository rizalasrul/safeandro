package id.co.ultrajaya.safeandro.module.contract

import id.co.ultrajaya.safeandro.model.response.general.ItemAnalisaQCORC
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.response.general.ItemSpinner
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import java.util.*
import kotlin.collections.ArrayList

interface AnalisaSampleContract {
    interface View : _MainContract.MainView{
        fun setDataInfoBarcode(itemMaterial: ItemMaterial)
        fun setListDataTipeAnalisaSample(itipeAnalisaSampleList : ArrayList<ItemSpinner>)
        fun setListHasilAnalisaSample(ihasilAnalisaSampleList : List<ItemSpinner>)
        fun setDetailLOV(itemAnalisaQCORC: ItemAnalisaQCORC)
        fun clearFormPostSave()
    }

    interface Presenter {
        fun onGetInfoBarcode(ibarcode : String)
        //ini yang di pake karena ambil dari orc
        fun onGetListTipeAnalisaSample(itemSample : String)
        fun onGetDetailLOV(itestDesc : String)
        //fun onGetListHasilAnalisaSample(itipe : String)
        fun onGetLOVResultAnalisaSampleORC(itestid : String)
        fun onSaveDataBarcode(itipe : String, ihasil : String, isAccept : Boolean, itemAnalisaQCORC: ItemAnalisaQCORC)
    }

    interface Data{
        fun onPostGetInfoBarcode(response : MainResp<ItemMaterial>)
        fun onFailureGetInfoBarcode(t : Throwable)
        fun onPostGetListTipeAnalisaSample(response : MainResp<ItemAnalisaQCORC>)
        fun onPostGetLOVResultAnalisaSampleORC(response : MainResp<ItemSpinner>)
        fun onFailureGetListTipeAnalisaSample(t : Throwable)
        fun onPostSaveDataBarcode(response : MainResp<Objects>)
        fun onFailureSaveDataBarcode(t : Throwable)
        //failure untuk next bisa dibuat menjadi satu saja dengan message dari fungsi yang berbeda2
    }
}