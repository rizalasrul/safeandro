package id.co.ultrajaya.safeandro.module.contract

import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import java.util.*

interface SerahTerimaQCContract {
    interface View{
        fun showLoading()
        fun hideLoading()
        fun showAlertDialog(imsg: String, itipe: Int)
        fun showToast(imsg : String?)
        fun setDataInfoBarcode(itemMaterial: ItemMaterial)
        fun clearForm()
    }

    interface Presenter {
        fun onGetInfoBarcode(ibarcode : String)
        fun onSaveDataBarcode()
    }

    interface DataInteractor{
        fun onPostGetInfoBarcode(response : MainResp<ItemMaterial>)
        fun onFailureGetInfoBarcode(t : Throwable)
        fun onPostSaveDataBarcode(response : MainResp<Objects>)
        fun onFailureSaveDataBarcode(t : Throwable)
    }
}