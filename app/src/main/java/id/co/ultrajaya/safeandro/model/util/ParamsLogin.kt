package com.example

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class ParamsLogin {

    constructor(iduser : String, passwd : String){
        this.iduser = iduser;
        this.passwd = passwd;
    }

    @SerializedName("iduser")
    @Expose
    var iduser: String? = null
    @SerializedName("passwd")
    @Expose
    var passwd: String? = null
}