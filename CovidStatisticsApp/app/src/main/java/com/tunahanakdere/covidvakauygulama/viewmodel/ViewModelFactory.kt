package com.tunahanakdere.covidvakauygulama.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tunahanakdere.covidvakauygulama.repository.CovidRepository

class CovidViewModelFactory(private val repository: CovidRepository) : ViewModelProvider.Factory {
                                    //oluşturulacak ViewModel Sınıfını temsil eder
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Oluşturulacak ViewModel sınıfı CovidViewModel sınıfı ile uyumlu ise
        if (modelClass.isAssignableFrom(CovidViewModel::class.java)) {
            // CovidViewModel sınıfının bir örneğini oluşturur ve döndürür
            return CovidViewModel(repository) as T
        }
        // Eğer uyumlu bir ViewModel sınıfı bulunamazsa IllegalArgumentException fırlatılır
        throw IllegalArgumentException("ViewModel Not Found")
    }
}
