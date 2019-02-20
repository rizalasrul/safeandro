package id.co.ultrajaya.safeandro.module.contract

import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import java.util.*

interface PindahBeakerContract {
    interface View : _MainContract.MainView{
        fun setDataInfoBarcode(itemMaterial: ItemMaterial)
        fun onClickButtonBarcode()
        fun onRefreshList()
        fun showErrorRV()
        fun hideErrorRV()
        fun showAlertDialogWithOptions(imsg : String, position : Int)
    }

    interface Presenter : _MainContract.MainAdapterContract.MainAdapterPresenter{
        fun onGetInfoBarcode(ibarcode : String)
        fun onAddBeaker(ibarcode : String, ibarcodeList : ArrayList<String>)
        fun onSaveDataBarcode()
    }

    interface Data{
        fun onPostGetInfoBarcode(response : MainResp<ItemMaterial>)
        fun onFailureGetInfoBarcode(t : Throwable)
        fun onPostSaveDataBarcode(response : MainResp<Objects>)
        fun onFailureSaveDataBarcode(t : Throwable)
    }
}