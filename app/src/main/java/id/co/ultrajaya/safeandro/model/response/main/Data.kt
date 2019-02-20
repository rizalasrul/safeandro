package id.co.ultrajaya.safeandro.model.response.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data<T> {
    @SerializedName("response_code")
    @Expose
    private var responseCode: String? = null
    @SerializedName("disp_msg")
    @Expose
    private var dispMsg: String? = null
    @SerializedName("nilai")
    @Expose
    private var nilai: Nilai<T>? = null

    fun getResponseCode(): String? {
        return responseCode
    }

    fun setResponseCode(responseCode: String) {
        this.responseCode = responseCode
    }

    fun getDispMsg(): String? {
        return dispMsg
    }

    fun setDispMsg(dispMsg: String) {
        this.dispMsg = dispMsg
    }

    fun getNilai(): Nilai<T>? {
        return nilai
    }

    fun setNilai(nilai: Nilai<T>) {
        this.nilai = nilai
    }
}