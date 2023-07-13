package com.tunahanakdere.covidvakauygulama.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tunahanakdere.covidvakauygulama.R
import com.tunahanakdere.covidvakauygulama.databinding.ActivityDetayEkranBinding
import com.tunahanakdere.covidvakauygulama.databinding.ActivityKullaniciGirisBinding
import java.lang.RuntimeException

class kullaniciGiris : AppCompatActivity() {

    private lateinit var binding: ActivityKullaniciGirisBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKullaniciGirisBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun signInClick(view: View){

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")){
            Toast.makeText(this,"Enter Email and Password!",Toast.LENGTH_LONG).show()
        }
        else{
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                //success
                val intent = Intent(this@kullaniciGiris, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                //failure
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }


    }

    fun signUpClick(view: View){

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")){
            Toast.makeText(this,"Enter Email and Password!",Toast.LENGTH_LONG).show()
        }
        else{
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                //succes
                val intent = Intent(this@kullaniciGiris, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }


}