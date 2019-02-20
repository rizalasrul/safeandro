package id.co.ultrajaya.safeandro.model.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.EditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.ultrajaya.safeandro.R
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import id.co.ultrajaya.safeandro.module.activity.AnalisaSampleFragment
import kotlinx.android.synthetic.main.component_edittext.view.*
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class ConfigComponents {
    companion object {
        fun clearForm(listOfView : List<View>){
            for (view : View in listOfView){
                view.editText.setText("")
            }
        }

        fun clearFormEditText(listOfView : List<EditText>){
            for (ieditText : EditText in listOfView){
                ieditText.setText("")
            }
        }

        fun <T> refreshFragment(fragmentManager: FragmentManager, aclass : Class<T>){
            lateinit var afragment: Fragment
            val afragmentClass: Class<*> = aclass::class.java
            try {
                afragment = afragmentClass.newInstance() as Fragment
            } catch (e: Exception) {
                e.printStackTrace()
            }

            fragmentManager.beginTransaction().replace(R.id.content_frame, afragment).commit()
        }

        fun convertStringToCurr(inumber: String): String {
            val ahasil: String

            val anumber = java.lang.Double.valueOf(inumber)
            val aformat = NumberFormat.getInstance(Locale.GERMANY)
            ahasil = aformat.format(anumber)

            return ahasil
        }

        inline fun <reified T> convertRespByType(response: Response<MainResp<T>>): MainResp<T> {
            val arawjson: String = Gson().toJson(response.body())

            val dataType = object : TypeToken<MainResp<T>>() {}.type
            val mainResp: MainResp<T> = Gson().fromJson<MainResp<T>>(arawjson, dataType)
            return mainResp
        }
    }
}