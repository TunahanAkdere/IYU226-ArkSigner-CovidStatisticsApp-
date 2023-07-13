package com.tunahanakdere.covidvakauygulama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tunahanakdere.covidvakauygulama.R
import com.tunahanakdere.covidvakauygulama.databinding.RowLayoutBinding
import com.tunahanakdere.covidvakauygulama.model.DataModelItem
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class MainAdapter(private val listener: Listener) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var covidList: List<DataModelItem> = emptyList()
    private var filteredList: List<DataModelItem> = emptyList()

    //RecyclerView verilerine tıklama olaylarını dinlemek için kullanılır.
    interface Listener {
        //tıklanan verinin DataModelItem nesnesini parametre alır
        fun onItemClick(dataModelItem: DataModelItem)
    }

    //VeriModellerini alarak adaptörü günceller
    fun setData(data: List<DataModelItem>) {
        covidList = data
        filteredList = data
        //RecyclerView'i günceller
        notifyDataSetChanged()
    }

    //Arama sorgusuna göre filtreler
    fun filter(query: String) {
        filteredList = if (query.isNotEmpty()) {
            // Arama sorgusu boş değilse, covidList üzerinde filtreleme yapılır
            covidList.filter { it.country.contains(query, ignoreCase = true) }
        } else {
            // Arama sorgusu boş ise, tüm covidList öğeleri filteredList'e atanır
            covidList
        }
        //Güncellenir
        notifyDataSetChanged()
    }

    //RecyclerView'ın görünüm öğeleri için yeni bir ViewHolder oluşturur.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //Pozisyondaki veri öğesini alır ve görünüm öğelerini bu veriyle doldurur
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flagImageView : ImageView = holder.binding.flagImageView
        val infecteImageView : ImageView = holder.binding.infecteImageView

        //Verileri günceller
        val dataModelItem = filteredList[position]
        holder.bind(dataModelItem)

        //Bayrak ve enfekte olan kişi resimleri Glide kütüphanesi kullanılarak yüklenmiştir.
        Glide.with(holder.itemView)
            .load(R.drawable.flag)
            .into(flagImageView)

        Glide.with(holder.itemView)
            .load(R.drawable.user)
            .into(infecteImageView)
    }

    //Adaptörün içindeki verilerin sayısını döndürür.
    override fun getItemCount(): Int {
        return filteredList.size
    }

    //RecyclerView'da her bir öğenin görünümünü gösterir
    inner class ViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            //Tıklanınca gerçekleşecek işlemler
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    //Konum geçerliyse filteredList listesindeki ilgili konumdaki öğe alınır ve
                    // -> listener arayüzünün onItemClick fonksiyonu çağrılır.
                    val dataModelItem = filteredList[position]
                    listener.onItemClick(dataModelItem)
                }
            }
        }

        //Veri modelini alır ve doldurur. Ülke adını ve enfekte olan kişi sayısını görüntüler.
        fun bind(dataModelItem: DataModelItem) {
            binding.countryName.text = dataModelItem.country

            //Varsayılan yerel ayarlara göre bir DecimalFormatSymbols nesnesi oluşturur
            val decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault())
            //Gruplama ayırıcısını nokta olarak belirler. Gruplama ayırıcısı, sayının binlik basamaklarını ayırmak için kullanılır.
            decimalFormatSymbols.groupingSeparator = '.'
            //DecimalFormat nesnesi oluşturur ve parametre olarak bir şablon ("#,###") ve bir DecimalFormatSymbols nesnesi alır.
            val decimalFormat = DecimalFormat("#,###",decimalFormatSymbols)

            binding.infectedText.text = "Infected: ${decimalFormat.format(dataModelItem.infected)}"
        }
    }
}
