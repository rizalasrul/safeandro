package id.co.ultrajaya.safeandro.model.util

import id.co.ultrajaya.safeandro.model.database.ApiGenerator
import id.co.ultrajaya.safeandro.model.database.DatabaseClient


class Config {
    companion object {
        var BASE_URL = "http://b2b.ultrajaya.co.id/safeapi/"
        var BASE_URL_2 = "http://safeweb.ultrajaya.co.id/safeandro/"
        lateinit var DATABASE_CLIENT: DatabaseClient
        fun CREATE_CLIENT(): DatabaseClient = ApiGenerator.createService(DatabaseClient::class.java)
        var HEADER_ITEM_SPINNER = "-- Pilih Salah Satu --"

        /*LIST SERVER GENERAL VARIABEL ACCESS*/
        val SERVER_1000PRODUKSI = "1000PRODUKSI"
        val SERVER_1000ORC = "1000ORC"
        val SERVER_QA3300ORC = "QA3300ORC"
        val SERVER_SAFEANDRO = "SAFEANDRO"

        /*LIST STRINGS*/
        val RESP_ERR = "Gagal Mendapatkan Data !"

        //var cuser and login
        var myuser: cuser? = null
        var version = ""
    }
}