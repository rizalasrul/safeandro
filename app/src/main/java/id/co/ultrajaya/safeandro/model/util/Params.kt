package com.example

import android.content.Context
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dmax.dialog.SpotsDialog

class Params {
    constructor(key : String, serverdb : String, sid : String, action : String, othval : String, sp : String, mvitem : String){
        this.key = key;
        this.serverdb = serverdb;
        this.sid = sid;
        this.action = action;
        this.othval = othval;
        this.sp = sp;
        this.mvitem = mvitem;
    }

    @SerializedName("key")
    @Expose
    var key: String? = null
    @SerializedName("serverdb")
    @Expose
    var serverdb: String? = null
    @SerializedName("sid")
    @Expose
    var sid: String? = null
    @SerializedName("action")
    @Expose
    var action: String? = null
    @SerializedName("othval")
    @Expose
    var othval: String? = null
    @SerializedName("sp")
    @Expose
    var sp: String? = null
    @SerializedName("mvitem")
    @Expose
    var mvitem: String? = null
}