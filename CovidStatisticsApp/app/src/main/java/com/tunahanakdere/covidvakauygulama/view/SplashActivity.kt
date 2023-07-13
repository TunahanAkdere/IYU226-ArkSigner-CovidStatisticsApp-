package com.tunahanakdere.covidvakauygulama.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tunahanakdere.covidvakauygulama.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Handler sınıfı kullanılarak ne kadar süre geçeceğini belirledik
        Handler().postDelayed({
            //Main activity'e geçiş yapmak için intent oluşturduk ve başlattık
            startActivity(Intent(this, kullaniciGiris::class.java))
            //splashActivity'i sonlandırdık
            finish()
        },3000)

    }
}