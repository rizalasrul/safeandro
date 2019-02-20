package id.co.ultrajaya.safeandro.module.contract

interface _MainContract {
    interface MainView{
        fun showLoading()
        fun hideLoading()
        fun showAlertDialog(imsg: String, itipe: Int)
        fun showAlertDialogAction(imsg : String, itipe : Int, itag: String)
        fun onPostAlertDialogAction(itag: String)
        fun showToast(imsg : String?)
        fun clearForm()
    }

    interface MainAdapterContract {
        interface MainAdapterView{
            fun onRefreshList()
            fun showErrorRV()
            fun hideErrorRV()
            fun showAlertDialogWithOptions(imsg : String, position : Int)
        }
        interface MainAdapterPresenter{
            fun onDeleteItemRV(iposition : Int)
        }
    }

    interface MainData{
        fun onFailureResponse(itag : String, t : Throwable)
    }
}