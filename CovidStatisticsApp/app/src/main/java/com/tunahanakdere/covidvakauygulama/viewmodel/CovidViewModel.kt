package com.tunahanakdere.covidvakauygulama.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tunahanakdere.covidvakauygulama.model.DataModelItem
import com.tunahanakdere.covidvakauygulama.repository.CovidRepository
import com.tunahanakdere.covidvakauygulama.service.CovidAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CovidViewModel (private val repository: CovidRepository) : ViewModel() {

    //Disposable, RxJava’da bir veri akışına abone olmayı temsil eden bir arayüzdür.
    private var compositeDisposable: CompositeDisposable? = null

    //MutableLiveData, LiveData sınıfından türetilen ve değerini değiştirebilen bir sınıftır.
    //List<DataModelItem>, DataModelItem adlı bir veri sınıfının bir listesidir.
    val covidData: MutableLiveData<List<DataModelItem>> = MutableLiveData()

    init {
        compositeDisposable = CompositeDisposable()
    }

    //Covid Verilerini almak için API'yı çağırdık >> Retrofit API çağrılarını yönetmek için
    fun getCovidData() {
        val retrofit = CovidRepository(CovidAPI.create())
        retrofit.getCovidData()

        //Rxjava kullanarak asenkron veri akışını işledik
        compositeDisposable?.add(retrofit.getCovidData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse)
        )
    }

    //API'den gelen verileri işleyerek LiveDataya verileri ayarlar
    private fun handleResponse(covidList: List<DataModelItem>) {
        // LiveData'ya yani covidData'ya veriyi atar, böylece veriler gözlemleyen bileşenler tarafından kullanılabilir
        covidData.value = covidList
    }

    //ViewModel bittiğinde kullanılan kaynakları temizledik
    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.clear()
    }
}
