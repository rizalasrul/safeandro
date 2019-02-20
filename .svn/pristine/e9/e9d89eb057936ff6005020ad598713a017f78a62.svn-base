package id.co.ultrajaya.safeandro.module.contract

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import id.co.ultrajaya.safeandro.MainActivity
import id.co.ultrajaya.safeandro.R
import id.co.ultrajaya.safeandro.model.response.general.ItemLogin
import id.co.ultrajaya.safeandro.model.response.general.ItemMenuAT
import id.co.ultrajaya.safeandro.model.response.general.ItemMenuNav
import id.co.ultrajaya.safeandro.model.util.AlertDialogCustom
import id.co.ultrajaya.safeandro.model.util.Config
import id.co.ultrajaya.safeandro.model.util.LoadingDialog
import id.co.ultrajaya.safetv.module.impl.LoginImpl
import kotlinx.android.synthetic.main.activity_login.*
import java.io.Serializable




class LoginActivity : AppCompatActivity(), LoginContract.LoginView {
    lateinit var _LoginImpl: LoginImpl
    lateinit var _loadingDialog: LoadingDialog;
    lateinit var _alertDialog: AlertDialogCustom;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        _alertDialog = AlertDialogCustom(this@LoginActivity)
        _loadingDialog = LoadingDialog(this@LoginActivity)
        _LoginImpl = LoginImpl(this, this@LoginActivity)

        btn_login.setOnClickListener {
            var aiduser = txt_username.text.toString()
            var apasswd = txt_password.text.toString()
            _LoginImpl.onClickButton(aiduser, apasswd)
        }
    }

    override fun showLoading() {
        _loadingDialog.show()
    }

    override fun hideLoading() {
        _loadingDialog.hide()
    }

    override fun showAlertDialog(imsg: String, itipe: Int) {
        _alertDialog.showAlertDialog(imsg, itipe)
    }

    override fun showToast(imsg: String?) {
        Toast.makeText(this, imsg, Toast.LENGTH_LONG).show()
    }

    override fun getVersi(itemLogin: ItemLogin) {
        Config.myuser!!._IdUser = txt_username.text.toString()
        Config.myuser!!._Passwd = txt_password.text.toString()
        Config.myuser!!._NamaUser = itemLogin.nama

        _LoginImpl.onGetVersi()
    }

    override fun checkVersi(itemMenuAT: ItemMenuAT) {
        Config.version = itemMenuAT.at2!!
        _LoginImpl.onCheckVersi()
    }

    override fun getMenuUser() {
        _LoginImpl.onGetMenuUser()
    }

    override fun goMainActivity(listMenuNav : List<ItemMenuNav>) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("list_menu_nav", listMenuNav as Serializable)
        startActivity(intent)
    }
}