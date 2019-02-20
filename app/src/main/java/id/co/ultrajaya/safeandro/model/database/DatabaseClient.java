package id.co.ultrajaya.safeandro.model.database;

import com.example.Params;
import com.example.ParamsLogin;
import id.co.ultrajaya.safeandro.model.response.general.*;
import id.co.ultrajaya.safeandro.model.response.main.MainResp;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Objects;


//ini seharusnya dijadikan ke kotlin
public interface DatabaseClient {
    @POST("andro/loginsql")
    Call<MainResp<ItemLogin>> getLoginSafe(
            @Body ParamsLogin jsonReq
    );

    @POST("andro/ambil")
    Call<MainResp<ItemMenuNav>> getMenuNav(
            @Body Params jsonReq
    );

    @POST("andro/ambil")
    Call<MainResp<ItemMenuAT>> getVersi(
            @Body Params jsonReq
    );

    /*PROGRAM SERAH TERIMA QC Start*/
    @POST("andro/ambil")
    Call<MainResp<ItemMaterial>> getInfoBarcodeSerahTerimaQC(
            @Body Params jsonReq
    );

    @POST("andro/ambil")
    Call<MainResp<Object>> saveDataBarodeSerahTerimaQC(
            @Body Params jsonReq
    );
    /*PROGRAM SERAH TERIMA QC End*/


    /*PROGRAM PINDAH BEAKER Start*/
    @POST("andro/ambil")
    Call<MainResp<ItemMaterial>> getInfoBarcodePindahBeaker(
            @Body Params jsonReq
    );

    @POST("andro/ambil")
    Call<MainResp<Objects>> saveDataBarcodePindahBeaker(
            @Body Params jsonReq
    );
    /*PROGRAM PINDAH BEAKER Start*/

    /*PROGRAM ANALISA SAMPLE Start*/
    @POST("andro/ambil")
    Call<MainResp<ItemMaterial>> getInfoBarcodeAnalisaSample(
            @Body Params jsonReq
    );

    @POST("andro/ambilorc")
    Call<MainResp<ItemAnalisaQCORC>> getListTipeAnalisaSampleORC(
            @Body Params jsonReq
    );

    @POST("andro/getplsql")
    Call<MainResp<ItemSpinner>> getLOVResultAnalisaSampleORC(
            @Body Params jsonReq
    );

    @POST("andro/ambil")
    Call<MainResp<ItemSpinner>> getListTipeAnalisaSample(
            @Body Params jsonReq
    );

    @POST("andro/ambil")
    Call<MainResp<Objects>> saveDataBarcodeAnalisaSample(
            @Body Params jsonReq
    );
    /*PROGRAM ANALISA SAMPLE End*/

    /*PROGRAM REPORT ANALISA Start*/
    @POST("andro/ambil")
    Call<MainResp<ItemMaterial>> getInfoBarcodeReportAnalisa(
            @Body Params jsonReq
    );

    @POST("andro/ambilorc")
    Call<MainResp<ItemAnalisaQCORC>> getInfoResumeORC(
            @Body Params jsonReq
    );

    @POST("andro/simpanorc")
    Call<MainResp<Objects>> saveInfoResumeORC(
            @Body Params jsonReq
    );

    @POST("andro/ambil")
    Call<MainResp<Objects>> saveInfoBarcodeReportAnalisa(
            @Body Params jsonReq
    );
    /*PROGRAM REPORT ANALISA End*/
}
