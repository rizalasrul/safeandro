package id.co.ultrajaya.safeandro.model.response.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ItemMenuAT : Serializable {
    @SerializedName("AT1")
    @Expose
    var at1: String? = null
    @SerializedName("AT2")
    @Expose
    var at2: String? = null
    @SerializedName("AT3")
    @Expose
    var at3: String? = null
    @SerializedName("AT4")
    @Expose
    var at4: String? = null
}