package com.tunahanakdere.covidvakauygulama.service

import com.google.gson.GsonBuilder
import com.tunahanakdere.covidvakauygulama.model.DataModelItem
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CovidAPI {

    @GET("tVaYRsPHLjNdNBu7S/records/LATEST?disableRedirect=true")
    //Bu fonksiyon, Observable<List<DataModelItem>> tipinde bir değer döndürür.
    fun getData(): Observable<List<DataModelItem>>

    //Retrofit.Builder() sınıfını kullanarak Retrofit nesnesini yapılandırır ve CovidAPI arayüzünü gerçekleştiren bir nesne döndürür.
    companion object {
        fun create(): CovidAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.apify.com/v2/key-value-stores/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) //Gson dönüştürücüsünü kullanarak JSON verilerini model sınıflara dönüştürü
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(CovidAPI::class.java)
        }
    }
}
