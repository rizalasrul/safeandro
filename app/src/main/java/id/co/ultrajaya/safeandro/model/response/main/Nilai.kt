package id.co.ultrajaya.safeandro.model.response.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import id.co.ultrajaya.safeandro.model.response.general.Table
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class Nilai<T>{
    @SerializedName("Table")
    @Expose
    private var table: List<T>? = null

    fun getTable(): List<T>? {
        return table
    }

    fun setTable(table: List<T>) {
        this.table = table
    }

    @SerializedName("Table1")
    @Expose
    private var table1: List<T>? = null

    fun getTable1(): List<T>? {
        return table1
    }

    fun setTable1(table: List<T>) {
        this.table1 = table
    }

    @SerializedName("Table2")
    @Expose
    private var table2: List<T>? = null

    fun getTable2(): List<T>? {
        return table2
    }

    fun setTable2(table: List<T>) {
        this.table2 = table
    }

    @SerializedName("Table3")
    @Expose
    private var table3: List<T>? = null

    fun getTable3(): List<T>? {
        return table3
    }

    fun setTable3(table: List<T>) {
        this.table3 = table
    }
}