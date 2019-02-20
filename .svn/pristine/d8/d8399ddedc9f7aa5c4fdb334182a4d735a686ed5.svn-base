package id.co.ultrajaya.safeandro.module.contract

import id.co.ultrajaya.safeandro.model.response.general.ItemLogin
import id.co.ultrajaya.safeandro.model.response.general.ItemMenuAT
import id.co.ultrajaya.safeandro.model.response.general.ItemMenuNav
import id.co.ultrajaya.safeandro.model.response.general.Table
import id.co.ultrajaya.safeandro.model.response.main.MainResp

interface LoginContract {
    interface LoginView {
        fun showLoading()
        fun hideLoading()
        fun showAlertDialog(imsg: String, itipe: Int)
        fun showToast(imsg : String?)
        fun goMainActivity(listMenuNav : List<ItemMenuNav>)
        fun getMenuUser()
        fun getVersi(itemLogin: ItemLogin)
        fun checkVersi(itemMenuAT: ItemMenuAT)
    }

    interface Presenter{
        fun onClickButton(iduser : String, passwd : String)
        fun onGetMenuUser()
        fun onGetVersi()
        fun onCheckVersi()
    }

    interface DataInteractor{
        fun onPostGetLogin(response : MainResp<ItemLogin>?)
        fun onPostGetMenuUser(response : MainResp<ItemMenuNav>?)
        fun onPostGetVersi(response: MainResp<ItemMenuAT>?)
        fun onFailure(t: Throwable)
    }
}