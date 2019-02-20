package id.co.ultrajaya.safeandro.model.response.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MainResp<T> {
    @SerializedName("data")
    @Expose
    private var data: Data<T>? = null
    @SerializedName("meta")
    @Expose
    private var meta: Meta? = null
    @SerializedName("errors")
    @Expose
    private var errorsList: List<Errors>? = null

    fun getData(): Data<T>? {
        return data
    }

    fun setData(data: Data<T>) {
        this.data = data
    }

    fun getMeta(): Meta? {
        return meta
    }

    fun setMeta(meta: Meta) {
        this.meta = meta
    }

    fun getErrors() : List<Errors>?{
        return errorsList
    }

    fun setErrors(errors : List<Errors>?){
        this.errorsList = errors
    }
}