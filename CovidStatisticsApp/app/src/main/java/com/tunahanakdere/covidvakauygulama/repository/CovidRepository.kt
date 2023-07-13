package com.tunahanakdere.covidvakauygulama.repository

import com.tunahanakdere.covidvakauygulama.model.DataModelItem
import com.tunahanakdere.covidvakauygulama.service.CovidAPI
import io.reactivex.Observable

class CovidRepository(private val covidAPI: CovidAPI) {


    //CovidApi'dan veri almamızı sağlar. CovidApi servisten gelen verileri uygulamada kullanmak için arayüz sağlar.
    fun getCovidData(): Observable<List<DataModelItem>> {
        return covidAPI.getData()
    }
}
