package id.co.ultrajaya.safeandro.model.response.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Errors {
    @SerializedName("message")
    @Expose
    private var message: String? = null

    fun getErrors() : String?{
        return message
    }

    fun setErrors(message : String){
        this.message = message
    }
}