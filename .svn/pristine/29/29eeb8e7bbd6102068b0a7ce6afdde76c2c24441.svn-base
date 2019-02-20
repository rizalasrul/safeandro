package id.co.ultrajaya.safeandro.model.database

import com.example.Params
import com.example.ParamsLogin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.ultrajaya.safeandro.model.response.general.*
import id.co.ultrajaya.safeandro.model.util.Config
import id.co.ultrajaya.safeandro.model.response.main.MainResp
import retrofit2.Call
import id.co.ultrajaya.safeandro.model.util.Convert
import id.co.ultrajaya.safeandro.module.contract.AnalisaSampleContract
import id.co.ultrajaya.safeandro.module.contract.PindahBeakerContract
import id.co.ultrajaya.safeandro.module.contract.ReportAnalisaContract
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

/*class convertRespByType<T>{
    *//*fun convertRespByType(iresponseBody: MainResp<T>) {
        val arawjson: String = Gson().toJson(iresponseBody)

        val dataType = object : TypeToken<MainResp<ItemMaterial>>() {}.type
        val mainResp: MainResp<T> = Gson().fromJson<MainResp<T>>(arawjson, dataType)
    }*//*
}*/

class Database {
    companion object {

        /*LOGIN START*/
        fun getLogin(idUser : String, passwd : String): Call<MainResp<ItemLogin>> {
            //bagian ini static jadi pertama saja di inisialisasi di activity utama/login
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()
            var atestResp = ParamsLogin(
                idUser, passwd
            )
            return Config.DATABASE_CLIENT.getLoginSafe(atestResp)
        }

        fun getMenuUser(idUser : String): Call<MainResp<ItemMenuNav>> {
            //bagian ini static jadi pertama saja di inisialisasi di activity utama/login
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, "")
            amvitem.SetData(2, "")

            var aparam: Params = Params(
                idUser, "1000PRODUKSI", "0", "0", "MENUUSER", "[IT].[DEVICE_USER_MENU]", amvitem.Contents()
            )
            return Config.DATABASE_CLIENT.getMenuNav(aparam)
        }

        fun getVersi(): Call<MainResp<ItemMenuAT>> {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, "")
            amvitem.SetData(2, "")

            var aparam: Params = Params(
                "SAFEANDRO", "MSAFE", "0", "0", "", "[dbo].[GETVERSI]", amvitem.Contents()
            )
            return Config.DATABASE_CLIENT.getVersi(aparam)
        }
        /*LOGIN END*/

        fun getInfoBarcodeSerahTerimaQC(ibarcode: String): Call<MainResp<ItemMaterial>> {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()
            val amvitem = mvitem()

            amvitem.SetData(1, ibarcode)
            amvitem.SetData(2, "")

            var aparam: Params = Params(
                "", "1000PRODUKSI", "0", "0", "RCV", "PP.Plan_WIP_Read", amvitem.Contents()
            )
            return Config.DATABASE_CLIENT.getInfoBarcodeSerahTerimaQC(aparam)
        }

        fun saveDataBarcodeSerahTerimaQC(ibarcode: String): Call<MainResp<Any>> {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, ibarcode)
            amvitem.SetData(2, "06276")

            var aparam: Params = Params(
                "", "1000PRODUKSI", "0", "1", "RCV", "PP.Plan_WIP_Save", amvitem.Contents()
            )
            return Config.DATABASE_CLIENT.saveDataBarodeSerahTerimaQC(aparam)
        }

        //ini yang improve kedua berbeda dengan yang atas menggunakan fungsi global convertRespByType
        /*PROGRAM PINDAH BEAKER Start*/
        fun getInfoBarcodePindahBeaker(ibarcode: String, ilistener: PindahBeakerContract.Data) {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, ibarcode)
            amvitem.SetData(2, "")

            var aparam: Params = Params(
                "", Config.SERVER_1000PRODUKSI, "0", "0", "BKR", "PP.Plan_WIP_Read", amvitem.Contents()
            )

            val acall: Call<MainResp<ItemMaterial>> = Config.DATABASE_CLIENT.getInfoBarcodePindahBeaker(aparam)
            acall.enqueue(object : Callback<MainResp<ItemMaterial>> {
                override fun onResponse(
                    call: Call<MainResp<ItemMaterial>>,
                    response: Response<MainResp<ItemMaterial>>
                ) {
                    val arawjson: String = Gson().toJson(response.body())

                    val dataType = object : TypeToken<MainResp<ItemMaterial>>() {}.type
                    val mainResp: MainResp<ItemMaterial> = Gson().fromJson<MainResp<ItemMaterial>>(arawjson, dataType)
                    ilistener.onPostGetInfoBarcode(mainResp)
                }

                override fun onFailure(call: Call<MainResp<ItemMaterial>>, t: Throwable) {
                    ilistener.onFailureGetInfoBarcode(t)
                }
            })
        }

        fun saveInfoBarcodePindahBeaker(
            ibarcodeMain: String,
            ilistBarcode: ArrayList<String>,
            ilistener: PindahBeakerContract.Data
        ) {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, ibarcodeMain)

            var i = 1
            for (barcode: String in ilistBarcode) {
                amvitem.SetData(2, i, barcode)
                i++
            }

            var aparam: Params = Params(
                "", Config.SERVER_1000PRODUKSI, "0", "1", "BKR", "PP.Plan_WIP_Save", amvitem.Contents()
            )

            val acall: Call<MainResp<Objects>> = Config.DATABASE_CLIENT.saveDataBarcodePindahBeaker(aparam)
            acall.enqueue(object : Callback<MainResp<Objects>> {
                override fun onResponse(call: Call<MainResp<Objects>>, response: Response<MainResp<Objects>>) {
                    val arawjson: String = Gson().toJson(response.body())

                    val dataType = object : TypeToken<MainResp<ItemMaterial>>() {}.type
                    val mainResp: MainResp<Objects> = Gson().fromJson<MainResp<Objects>>(arawjson, dataType)
                    ilistener.onPostSaveDataBarcode(mainResp)
                }

                override fun onFailure(call: Call<MainResp<Objects>>, t: Throwable) {
                    ilistener.onFailureGetInfoBarcode(t)
                }
            })
        }
        /*PROGRAM PINDAH BEAKER End*/

        /*PROGRAM ANALISA SAMPLE Start*/
        var aserverAnalisaORC = Config.SERVER_QA3300ORC

        fun getInfoBarcodeAnalisaSample(ibarcode: String, ilistener: AnalisaSampleContract.Data) {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, ibarcode)
            amvitem.SetData(2, "")

            var aparam: Params = Params(
                "", Config.SERVER_1000PRODUKSI, "0", "0", "analisa", "PP.Plan_WIP_Read", amvitem.Contents()
            )

            val acall: Call<MainResp<ItemMaterial>> = Config.DATABASE_CLIENT.getInfoBarcodeAnalisaSample(aparam)
            acall.enqueue(object : Callback<MainResp<ItemMaterial>> {
                override fun onResponse(
                    call: Call<MainResp<ItemMaterial>>,
                    response: Response<MainResp<ItemMaterial>>
                ) {
                    //val mainResp = convertRespByType<ItemMaterial>(response)

                    val arawjson: String = Gson().toJson(response.body())

                    val dataType = object : TypeToken<MainResp<ItemMaterial>>() {}.type
                    val mainResp: MainResp<ItemMaterial> = Gson().fromJson<MainResp<ItemMaterial>>(arawjson, dataType)
                    ilistener.onPostGetInfoBarcode(mainResp)
                }

                override fun onFailure(call: Call<MainResp<ItemMaterial>>, t: Throwable) {
                    ilistener.onFailureGetInfoBarcode(t)
                }
            })
        }

        fun getInfoListTipeAnalisaORC(item_sample: String, ilistener: AnalisaSampleContract.Data) {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, item_sample)
            amvitem.SetData(2, "")

            var aparam: Params = Params(
                "", aserverResumeORC, "0", "0", "", "APPS.XITQC_READ_INPUT_SAMPLE", amvitem.Contents()
            )

            val acall: Call<MainResp<ItemAnalisaQCORC>> = Config.DATABASE_CLIENT.getListTipeAnalisaSampleORC(aparam)
            acall.enqueue(object : Callback<MainResp<ItemAnalisaQCORC>> {
                override fun onResponse(
                    call: Call<MainResp<ItemAnalisaQCORC>>,
                    response: Response<MainResp<ItemAnalisaQCORC>>
                ) {
                    val arawjson: String = Gson().toJson(response.body())

                    val dataType = object : TypeToken<MainResp<ItemAnalisaQCORC>>() {}.type
                    val mainResp: MainResp<ItemAnalisaQCORC> =
                        Gson().fromJson<MainResp<ItemAnalisaQCORC>>(arawjson, dataType)
                    ilistener.onPostGetListTipeAnalisaSample(mainResp)
                }

                override fun onFailure(call: Call<MainResp<ItemAnalisaQCORC>>, t: Throwable) {
                    ilistener.onFailureGetListTipeAnalisaSample( t)
                }
            })
        }

        fun getInfoLOVListResultAnalisa(itest_id: String, ilistener: AnalisaSampleContract.Data) {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            var aquery : String = "select gqtv.value_char as LOV_VALUE \n" +
                    "from GMD_QC_TESTS  gqt\n" +
                    ", GMD_QC_TEST_VALUES gqtv\n" +
                    " where gqt.test_id = gqtv.test_id(+) \n" +
                    "and gqt.test_id = '#AT1#'\n" +
                    "order by 1\n"
            aquery = aquery.replace("#AT1#", itest_id)

            val amvitem = mvitem()

            amvitem.SetData(1, aquery)

            var aparam: Params = Params(
                "", aserverResumeORC, "", "", "", "", amvitem.Contents()
            )

            val acall: Call<MainResp<ItemSpinner>> = Config.DATABASE_CLIENT.getLOVResultAnalisaSampleORC(aparam)
            acall.enqueue(object : Callback<MainResp<ItemSpinner>> {
                override fun onResponse(
                    call: Call<MainResp<ItemSpinner>>,
                    response: Response<MainResp<ItemSpinner>>
                ) {
                    val arawjson: String = Gson().toJson(response.body())

                    val dataType = object : TypeToken<MainResp<ItemSpinner>>() {}.type
                    val mainResp: MainResp<ItemSpinner> =
                        Gson().fromJson<MainResp<ItemSpinner>>(arawjson, dataType)
                    ilistener.onPostGetLOVResultAnalisaSampleORC(mainResp)
                }

                override fun onFailure(call: Call<MainResp<ItemSpinner>>, t: Throwable) {
                    ilistener.onFailureGetListTipeAnalisaSample( t)
                }
            })
        }

        //ini sudah tidak digunakan lagi karena mendapatkan list drop down itemnya dari SP oracle nya pak kris
        //dropdown untuk list tipe analisa semial aroma, brix dll
        fun getTipeAnalisaSample(ibarcode: String, ilistener: AnalisaSampleContract.Data) {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, ibarcode)
            amvitem.SetData(2, "")

            var aparam: Params = Params(
                "", Config.SERVER_1000PRODUKSI, "0", "0", "drop", "PP.Plan_WIP_Read", amvitem.Contents()
            )

            val acall: Call<MainResp<ItemSpinner>> = Config.DATABASE_CLIENT.getListTipeAnalisaSample(aparam)
            acall.enqueue(object : Callback<MainResp<ItemSpinner>> {
                override fun onResponse(
                    call: Call<MainResp<ItemSpinner>>,
                    response: Response<MainResp<ItemSpinner>>
                ) {

                    val arawjson: String = Gson().toJson(response.body())

                    val dataType = object : TypeToken<MainResp<ItemSpinner>>() {}.type
                    val mainResp: MainResp<ItemSpinner> = Gson().fromJson<MainResp<ItemSpinner>>(arawjson, dataType)
                    //lihat note diatasnyas
                    //ilistener.onPostGetListTipeAnalisaSample(mainResp)
                }

                override fun onFailure(call: Call<MainResp<ItemSpinner>>, t: Throwable) {
                    ilistener.onFailureGetListTipeAnalisaSample(t)
                }
            })
        }

        fun saveInfoBarcodeAnalisaSample(
            ibarcode: String,
            itipe: String,
            ihasil: String,
            isAccept : Boolean,
            ilistener: AnalisaSampleContract.Data,
            itemAnalisaQCORC: ItemAnalisaQCORC
        ) {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            var aisAccept : Int
            if(isAccept){
                aisAccept = 1
            }else{
                aisAccept = 0
            }

            amvitem.SetData(1, ibarcode)
            amvitem.SetData(11, itipe)
            amvitem.SetData(12, ihasil)
            amvitem.SetData(13, "06276") //masih hardcode
            amvitem.SetData(14, itemAnalisaQCORC.testid.toString())
            amvitem.SetData(15, itemAnalisaQCORC.seq.toString())
            amvitem.SetData(16, itemAnalisaQCORC.target.toString())
            amvitem.SetData(17, aisAccept.toString())

            var aparam: Params = Params(
                "", Config.SERVER_1000PRODUKSI, "0", "1", "analisa", "PP.Plan_WIP_Save", amvitem.Contents()
            )

            val acall: Call<MainResp<Objects>> = Config.DATABASE_CLIENT.saveDataBarcodeAnalisaSample(aparam)
            acall.enqueue(object : Callback<MainResp<Objects>> {
                override fun onResponse(call: Call<MainResp<Objects>>, response: Response<MainResp<Objects>>) {
                    val arawjson: String = Gson().toJson(response.body())

                    val dataType = object : TypeToken<MainResp<ItemMaterial>>() {}.type
                    val mainResp: MainResp<Objects> = Gson().fromJson<MainResp<Objects>>(arawjson, dataType)
                    ilistener.onPostSaveDataBarcode(mainResp)
                }

                override fun onFailure(call: Call<MainResp<Objects>>, t: Throwable) {
                    ilistener.onFailureGetInfoBarcode(t)
                }
            })
        }
        /*PROGRAM ANALISA SAMPLE End*/

        /*PROGRAM RESUME/REPORT ANALISA Start*/
        var aserverResumeORC = Config.SERVER_QA3300ORC

        fun getInfoBarcodeReportAnalisa(ibarcode: String, ilistener: ReportAnalisaContract.Data) {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, ibarcode)
            amvitem.SetData(2, "")

            var aparam: Params = Params(
                "", Config.SERVER_1000PRODUKSI, "0", "0", "resume", "PP.Plan_WIP_Read", amvitem.Contents()
            )

            val acall: Call<MainResp<ItemMaterial>> = Config.DATABASE_CLIENT.getInfoBarcodeReportAnalisa(aparam)
            acall.enqueue(object : Callback<MainResp<ItemMaterial>> {
                override fun onResponse(
                    call: Call<MainResp<ItemMaterial>>,
                    response: Response<MainResp<ItemMaterial>>
                ) {
                    val arawjson: String = Gson().toJson(response.body())

                    val dataType = object : TypeToken<MainResp<ItemMaterial>>() {}.type
                    val mainResp: MainResp<ItemMaterial> = Gson().fromJson<MainResp<ItemMaterial>>(arawjson, dataType)
                    ilistener.onPostGetInfoBarcode(mainResp)
                }

                override fun onFailure(call: Call<MainResp<ItemMaterial>>, t: Throwable) {
                    ilistener.onFailureResponse("getInfoBarcodeReportAnalisa", t)
                }
            })
        }

        //tidak jadi digunakan karena sudah diambil di bagian analisa
        fun getInfoSampleORC(item_sample: String, ilistener: ReportAnalisaContract.Data) {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, item_sample)
            amvitem.SetData(2, "")

            var aparam: Params = Params(
                "", aserverResumeORC, "0", "0", "", "APPS.XITQC_READ_INPUT_SAMPLE", amvitem.Contents()
            )

            val acall: Call<MainResp<ItemAnalisaQCORC>> = Config.DATABASE_CLIENT.getInfoResumeORC(aparam)
            acall.enqueue(object : Callback<MainResp<ItemAnalisaQCORC>> {
                override fun onResponse(
                    call: Call<MainResp<ItemAnalisaQCORC>>,
                    response: Response<MainResp<ItemAnalisaQCORC>>
                ) {
                    val arawjson: String = Gson().toJson(response.body())

                    val dataType = object : TypeToken<MainResp<ItemAnalisaQCORC>>() {}.type
                    val mainResp: MainResp<ItemAnalisaQCORC> =
                        Gson().fromJson<MainResp<ItemAnalisaQCORC>>(arawjson, dataType)
                    ilistener.onPostGetInfoBarcodeORC(mainResp)
                }

                override fun onFailure(call: Call<MainResp<ItemAnalisaQCORC>>, t: Throwable) {
                    ilistener.onFailureResponse("getInfoSampleORC", t)
                }
            })
        }

        fun saveInfoSampleORC(
            item_sample: String,
            ireportAnalisaList: ArrayList<ItemMaterial>,
            ilistener: ReportAnalisaContract.Data
        ) {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, item_sample)

            var i = 1
            for(itemMaterial in ireportAnalisaList){
                var aevaluation : String
                if (itemMaterial.isAcc!!){
                    aevaluation = "Accept"
                }else{
                    aevaluation = "Reject"
                }

                amvitem.SetData(10,i, itemMaterial.seq.toString())
                amvitem.SetData(18,i, aevaluation)
                amvitem.SetData(20,i, itemMaterial.nilai)
                amvitem.SetData(30,i, itemMaterial.testID.toString())
                i++
            }

            var aparam: Params = Params(
                "", aserverResumeORC, "0", "0", "06276", "APPS.XITQC_WRITE_INPUT_SAMPLE", amvitem.Contents()
            )

            val acall: Call<MainResp<Objects>> = Config.DATABASE_CLIENT.saveInfoResumeORC(aparam)
            acall.enqueue(object : Callback<MainResp<Objects>> {
                override fun onResponse(
                    call: Call<MainResp<Objects>>,
                    response: Response<MainResp<Objects>>
                ) {
                    val arawjson: String = Gson().toJson(response.body())

                    val dataType = object : TypeToken<MainResp<Objects>>() {}.type
                    val mainResp: MainResp<Objects> = Gson().fromJson<MainResp<Objects>>(arawjson, dataType)
                    ilistener.onPostSaveInfoSampleORC(mainResp)
                }

                override fun onFailure(call: Call<MainResp<Objects>>, t: Throwable) {
                    ilistener.onFailureResponse("getInfoSampleORC", t)
                }
            })
        }

        fun saveReportAnalisa(itemAnalisaQC: ItemAnalisaQC, ilistener: ReportAnalisaContract.Data) {
            Config.DATABASE_CLIENT = Config.CREATE_CLIENT()

            val amvitem = mvitem()

            amvitem.SetData(1, itemAnalisaQC.idRef)
            amvitem.SetData(2, itemAnalisaQC.result)
            amvitem.SetData(3, itemAnalisaQC.note)
            amvitem.SetData(4, itemAnalisaQC.item_sample)
            amvitem.SetData(30, "06276")

            var aparam: Params = Params(
                "", Config.SERVER_1000PRODUKSI, "0", "1", "acc", "PP.Plan_WIP_Save", amvitem.Contents()
            )

            val acall: Call<MainResp<Objects>> = Config.DATABASE_CLIENT.saveInfoBarcodeReportAnalisa(aparam)
            acall.enqueue(object : Callback<MainResp<Objects>> {
                override fun onResponse(
                    call: Call<MainResp<Objects>>,
                    response: Response<MainResp<Objects>>
                ) {
                    val arawjson: String = Gson().toJson(response.body())

                    val dataType = object : TypeToken<MainResp<Objects>>() {}.type
                    val mainResp: MainResp<Objects> = Gson().fromJson<MainResp<Objects>>(arawjson, dataType)
                    ilistener.onPostSaveInfoBarcode(mainResp)
                }

                override fun onFailure(call: Call<MainResp<Objects>>, t: Throwable) {
                    ilistener.onFailureResponse("saveReportAnalisa", t)
                }
            })
        }
        /*PROGRAM RESUME/REPORT End*/
    }
}
