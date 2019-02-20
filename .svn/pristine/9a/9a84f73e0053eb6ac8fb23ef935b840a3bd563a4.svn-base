package id.co.ultrajaya.safeandro.model.database

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import id.co.ultrajaya.safeandro.model.util.Config
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGenerator {
    companion object {
        private val aokHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic ZGVtbzpkZW1v")
                    .header("Cache-control", "no-cache")
                    .header("Postman-Token", "50654c37-ef22-49be-a984-ca23595731fb")
                    .build()

                chain.proceed(request)
            }
            .build()

        //untuk @serialized in case sensitive
        //ini belum berhasil pake yang alternative saja dulu, seharusnya bisa upper otomatis yang huruf awal
        //semial item_sample --> Item_sample --> UPPER CAMEL CASE //https://futurestud.io/tutorials/gson-builder-basics-naming-policies
        var gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()
        private val aretrofitBuilder = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))

        fun <S> createService(serviceClass: Class<S>): S {
            val aretrofit = aretrofitBuilder.client(aokHttpClient).build()
            return aretrofit.create(serviceClass)
        }
    }
}
