package id.co.ultrajaya.safeandro.model.response.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItemAnalisaQC {
    @SerializedName("ID_REF")
    @Expose
    var idRef: String? = null
    @SerializedName("ITEM_SAMPLE")
    @Expose
    var item_sample: String? = null
    @SerializedName("RESULT")
    @Expose
    var result: String? = null
    @SerializedName("NOTE")
    @Expose
    var note: String? = null
}