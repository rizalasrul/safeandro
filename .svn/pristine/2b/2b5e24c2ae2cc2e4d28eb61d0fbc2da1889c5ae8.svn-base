package id.co.ultrajaya.safeandro.model.response.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import android.R.attr.name



class ItemSpinner {
    @SerializedName("SPINNER", alternate = ["LOV_VALUE"])
    @Expose
    var spinner : String? = null
    @SerializedName("isTrue")
    @Expose
    var isTrue : Boolean? = null
    @SerializedName("keterangan")
    @Expose
    var keterangan : String? = null

    //fungsi ini ngaruh banget untuk menampilkan di spinner karena kalau tidak ada ini yang muncul alamat address kode tidak jelas
    override fun toString(): String {
        return this.spinner!!
    }


}