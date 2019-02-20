package id.co.ultrajaya.safeandro.model.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.response.general.ItemSpinner
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import retrofit2.Response

class Convert{
    //fungsi umum untuk konversi gson sesuai dengan output datatype sebagai parameter yakni T
    /*fun convertRespByType(response: Response<MainResp<T>>, aclass : Class<ItemSpinner>): MainResp<T> {
        val arawjson: String = Gson().toJson(response.body())

        val dataType = object : TypeToken<MainResp<T>>() {}.type
        val mainResp: MainResp<T> = Gson().fromJson<MainResp<T>>(arawjson, dataType)
        return mainResp
    }*/

    /*private fun <T> T getObjectFromData(clazz : Class<T> , String data) {

    }*/

    inline fun <reified T> convertRespByType(response: Response<MainResp<T>>): MainResp<T> {
        val arawjson: String = Gson().toJson(response.body())

        val dataType = object : TypeToken<MainResp<T>>() {}.type
        val mainResp: MainResp<T> = Gson().fromJson<MainResp<T>>(arawjson, dataType)
        return mainResp
    }
}