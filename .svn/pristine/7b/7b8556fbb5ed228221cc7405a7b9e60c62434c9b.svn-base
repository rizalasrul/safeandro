package id.co.ultrajaya.safetv.module.impl

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import id.co.ultrajaya.safeandro.model.database.Database
import id.co.ultrajaya.safeandro.model.response.general.ItemLogin
import id.co.ultrajaya.safeandro.model.response.general.ItemMenuAT
import id.co.ultrajaya.safeandro.model.response.general.ItemMenuNav
import id.co.ultrajaya.safeandro.model.response.general.Table
import id.co.ultrajaya.safeandro.model.util.Config
import id.co.ultrajaya.safeandro.model.util.UpdateAPK
import id.co.ultrajaya.safeandro.model.util.cuser
import id.co.ultrajaya.safeandro.module.contract.LoginContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginImpl : LoginContract.Presenter, LoginContract.DataInteractor {
    private var _loginView: LoginContract.LoginView
    private var _dataInteractor: LoginContract.DataInteractor
    private var _activity: Context

    constructor(iloginView: LoginContract.LoginView, iactivity: Context) {
        this._loginView = iloginView
        this._activity = iactivity
        this._dataInteractor = this
    }

    override fun onClickButton(iduser : String, passwd : String) {
        if(iduser.equals("") || passwd.equals("")){
            _loginView.showAlertDialog("UserID atau Password masih kosong", 1)
            return
        }

        Config.myuser = cuser()
        _loginView.showLoading()
        val acall: Call<MainResp<ItemLogin>> = Database.getLogin(iduser, passwd)

        acall.enqueue(object : Callback<MainResp<ItemLogin>> {
            override fun onResponse(call: Call<MainResp<ItemLogin>>, response: Response<MainResp<ItemLogin>>) {
                val arawjson: String = Gson().toJson(response.body())

                val dataType = object : TypeToken<MainResp<ItemLogin>>() {}.type
                val mainResp: MainResp<ItemLogin> = Gson().fromJson<MainResp<ItemLogin>>(arawjson, dataType)

                _dataInteractor.onPostGetLogin(mainResp)
            }

            override fun onFailure(call: Call<MainResp<ItemLogin>>, t: Throwable) {
                _dataInteractor.onFailure(t)
            }
        })
    }

    override fun onPostGetLogin(response: MainResp<ItemLogin>?) {
        if (response!!.getMeta()!!.getHttpStatus() == 200) {
            if (response.getData()!!.getNilai()!!.getTable()!!.isNotEmpty()) {
                _loginView.getVersi(response.getData()!!.getNilai()!!.getTable()!![0])
            }else{
                _loginView.showAlertDialog("Username atau password salah !", 1)
            }
        }else{
            _loginView.showAlertDialog("Username atau password salah !", 1)
        }
        _loginView.hideLoading()
    }

    override fun onGetMenuUser() {
        _loginView.showLoading()
        val acall: Call<MainResp<ItemMenuNav>> = Database.getMenuUser(Config.myuser!!._IdUser)

        acall.enqueue(object : Callback<MainResp<ItemMenuNav>> {
            override fun onResponse(call: Call<MainResp<ItemMenuNav>>, response: Response<MainResp<ItemMenuNav>>) {
                val arawjson: String = Gson().toJson(response.body())

                val dataType = object : TypeToken<MainResp<ItemMenuNav>>() {}.type
                val mainResp: MainResp<ItemMenuNav> = Gson().fromJson<MainResp<ItemMenuNav>>(arawjson, dataType)

                _dataInteractor.onPostGetMenuUser(mainResp)
            }

            override fun onFailure(call: Call<MainResp<ItemMenuNav>>, t: Throwable) {
                _dataInteractor.onFailure(t)
            }
        })
    }

    override fun onPostGetMenuUser(response: MainResp<ItemMenuNav>?) {
        if (response!!.getMeta()!!.getHttpStatus() == 200) {
            if (response.getData()!!.getNilai()!!.getTable3()!!.isNotEmpty()) {
                _loginView.goMainActivity(response.getData()!!.getNilai()!!.getTable3()!!)
            }else{
                _loginView.showAlertDialog("Menu nav masih kosong !", 1)
            }
        }else{
            _loginView.showAlertDialog("Tidak mendapatkan menu nav !", 1)
        }
        _loginView.hideLoading()
    }

    override fun onGetVersi() {
        _loginView.showLoading()
        val acall: Call<MainResp<ItemMenuAT>> = Database.getVersi()

        acall.enqueue(object : Callback<MainResp<ItemMenuAT>> {
            override fun onResponse(call: Call<MainResp<ItemMenuAT>>, response: Response<MainResp<ItemMenuAT>>) {
                val arawjson: String = Gson().toJson(response.body())

                val dataType = object : TypeToken<MainResp<ItemMenuAT>>() {}.type
                val mainResp: MainResp<ItemMenuAT> = Gson().fromJson<MainResp<ItemMenuAT>>(arawjson, dataType)

                _dataInteractor.onPostGetVersi(mainResp)
            }

            override fun onFailure(call: Call<MainResp<ItemMenuAT>>, t: Throwable) {
                _dataInteractor.onFailure(t)
            }
        })
    }

    override fun onPostGetVersi(response: MainResp<ItemMenuAT>?) {
        if (response!!.getMeta()!!.getHttpStatus() == 200) {
            if (response.getData()!!.getNilai()!!.getTable()!!.isNotEmpty()) {
                _loginView.checkVersi(response.getData()!!.getNilai()!!.getTable()!![0])
            }else{
                _loginView.showAlertDialog("Tidak mendapatkan versi dari database !", 1)
            }
        }else{
            _loginView.showAlertDialog("Error Saat Mengambil Data Versi !", 1)
        }
        _loginView.hideLoading()
    }

    override fun onCheckVersi() {
        var aapkName = "SAFEANDRO"
        val versionName =  _activity.getPackageManager().getPackageInfo(_activity.getPackageName(), 0).versionName
        if(!Config.version.equals("") && !Config.version.equals(versionName)){
            UpdateAPK.update(_activity, aapkName)
        }else{
            _loginView.getMenuUser()
            //_loginView.showAlertDialog("Aplikasi anda sudah versi terbaru !", 3)
        }
    }

    override fun onFailure(t: Throwable) {
        _loginView.showToast(t.message.toString())
        _loginView.hideLoading()
    }
}