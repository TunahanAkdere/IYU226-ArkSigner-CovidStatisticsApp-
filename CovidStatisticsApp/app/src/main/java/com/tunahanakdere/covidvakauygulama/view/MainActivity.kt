package com.tunahanakdere.covidvakauygulama.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tunahanakdere.covidvakauygulama.R
import com.tunahanakdere.covidvakauygulama.adapter.MainAdapter
import com.tunahanakdere.covidvakauygulama.databinding.ActivityDetayEkranBinding
import com.tunahanakdere.covidvakauygulama.databinding.ActivityMainBinding
import com.tunahanakdere.covidvakauygulama.model.DataModelItem
import com.tunahanakdere.covidvakauygulama.repository.CovidRepository
import com.tunahanakdere.covidvakauygulama.service.CovidAPI
import com.tunahanakdere.covidvakauygulama.viewmodel.CovidViewModel
import com.tunahanakdere.covidvakauygulama.viewmodel.CovidViewModelFactory

class MainActivity : AppCompatActivity(), MainAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: CovidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //ViewBinding uyguladık
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        auth = Firebase.auth

        //RecyclerView ve Adapter oluştrma
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this)
        recyclerView.adapter = adapter

        // CovidAPI, CovidRepository ve CovidViewModel sınıflarını oluşturduk
        val covidAPI = CovidAPI.create()
        val repository = CovidRepository(covidAPI)
        val factory = CovidViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CovidViewModel::class.java)

        // Covid verilerini alma isteği gönderme
        viewModel.getCovidData()

        // Covid verilerini dinleme ve RecyclerView adapterine verileri ayarlama
        viewModel.covidData.observe(this, Observer { data ->
            adapter.setData(data) //RecyclerView adapterine verieler atanır
        })



        //val searchEditText: EditText = findViewById(R.id.searchEditText)
        // Arama metin kutusunun değişikliklerini dinleme ve RecyclerView adapterini filtreleme
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // RecyclerView öğelerine tıklama işlemini gerçekleştirme
    override fun onItemClick(dataModelItem: DataModelItem) {
        val intent = Intent(this, DetayEkran::class.java)
        intent.putExtra("Country", dataModelItem)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.signOut -> {
                auth.signOut()
                val intent = Intent(this, kullaniciGiris::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
