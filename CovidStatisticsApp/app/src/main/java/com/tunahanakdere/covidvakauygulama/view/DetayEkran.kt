package com.tunahanakdere.covidvakauygulama.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tunahanakdere.covidvakauygulama.R
import com.tunahanakdere.covidvakauygulama.databinding.ActivityDetayEkranBinding
import com.tunahanakdere.covidvakauygulama.model.DataModelItem
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Locale

class DetayEkran : AppCompatActivity() {

    private lateinit var binding: ActivityDetayEkranBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ViewBinding
        binding = ActivityDetayEkranBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // "Country" adıyla gelen veriyi alır ve DataModelItem türünde bir değişkene atar
        val selectedCountry = intent.getSerializableExtra("Country") as DataModelItem

        binding.selectedCountrytextView.text = selectedCountry.country

        //Varsayılan yerel ayarlara göre bir DecimalFormatSymbols nesnesi oluşturur
        val decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault())
        //Gruplama ayırıcısını nokta olarak belirledik. Binlik olarak ayırmak için kullanılır.
        decimalFormatSymbols.groupingSeparator = '.'
        //DecimalFormat nesnesi oluşturur ve parametre olarak bir şablon ("#,###") ve bir DecimalFormatSymbols nesnesi alır.
        val decimalFormat = DecimalFormat("#,###",decimalFormatSymbols)

        binding.selectedInfectedCount.text = decimalFormat.format(selectedCountry.infected)

        //Deceased değişkenin kontrolu yapılıyor eğer geçerli bir değer yoksa texte "-" yazdırılıyor
        binding.selectedDeceasedCount.text = try {
            if (selectedCountry.deceased != null && selectedCountry.deceased != "N/A") {
                decimalFormat.format(selectedCountry.deceased.toInt())
            } else {
                "-"
            }
        } catch (e: NumberFormatException) {
            "-"
        }

        //Tested değişkenin kontrolu yapılıyor eğer geçerli bir değer yoksa texte "-" yazdırılıyor
        binding.selectedTestedCount.text = try {
            if (selectedCountry.tested != null && selectedCountry.tested != "N/A"){
                decimalFormat.format(selectedCountry.tested.toInt())
            }else{
                "-"
            }
        }catch (e: NumberFormatException){
            "-"
        }

        //Recovered değişkenin kontrolu yapılıyor eğer geçerli bir değer yoksa texte "-" yazdırılıyor
        binding.selectedRecoveredCount.text = try {
            if (selectedCountry.recovered != null && selectedCountry.recovered != "N/A"){
                decimalFormat.format(selectedCountry.recovered.toInt())
            }else{
                "-"
            }
        }catch (e: NumberFormatException){
            "-"
        }

        val lastupdatedapify = selectedCountry.lastUpdatedApify
        val lastupdatedsource = selectedCountry.lastUpdatedSource

        //SimpleDateFormat sınıfı kullanılarak api den gelen verinin formatını alıyoruz
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        //İstediğimiz formatta olması için yine SimpleDateFormat sınıfını kullanıyoruz ve
        // -> outputFormatın gün ay yıl tarih saat olması gerektiğini belirtiyoruz
        val outputFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())

        //lastupdatedapify verisini biçimlendirip, formattedlastupdatedapify değişkenine atıyoruz
        val formattedlastupdatedapify = outputFormat.format(inputFormat.parse(lastupdatedapify))

        //lastupdatedsource verisini önce null mı kontorlu yapıyoruz daha sonra biçimlendirip,
        // -> formattedlastupdatedapify değişkenine atıyoruz null ise texte "-" yazdırıyoruz
        val formattedlastupdatesource = if (lastupdatedsource != null && lastupdatedsource != "N/A") {
            outputFormat.format(inputFormat.parse(lastupdatedsource))
        } else {
            "-"
        }

        binding.selectedLastupdatedApify.text = formattedlastupdatedapify
        binding.selectedLastupdatedSource.text = formattedlastupdatesource

        //LINK ŞEKLİNDE GÖSTERME
        /*
         Ülkenin kaynak URL'sini temsil eden selectedCountry.sourceUrl değeri boş değilse,
         HTML biçiminde bir text(htmlText) oluşturuyoruz. Bu text(htmlText) bağlantı içerir ve selectedCountry.sourceUrl değerini hedef olarak kullanır.
         Eğer selectedCountry.sourceUrl değeri boş ise, texte "-" metni atanır.
        */
        val htmlText = if ( !selectedCountry.sourceUrl.isNullOrEmpty()){
            "<a href=\"${selectedCountry.sourceUrl}\">${selectedCountry.sourceUrl}</a>"
        }else{
            "-"
        }

        // binding.selectedSourceurlLink TextView'i HTML biçimli metinle günceller.
        // Html.fromHtml() yöntemi, HTML biçimlendirme işaretlerini kullanarak metni biçimlendirir.
        binding.selectedSourceurlLink.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)

        // binding.selectedSourceurlLink TextView'i tıklanabilir hale getirir.
        binding.selectedSourceurlLink.movementMethod = LinkMovementMethod.getInstance()

        // binding.selectedSourceurlLink TextView'e bir tıklama olayı dinleyicisi eklenir.
        binding.selectedSourceurlLink.setOnClickListener {
            val sourceUrl = selectedCountry.sourceUrl
            // selectedCountry.sourceUrl değeri boş değilse, bir Intent oluşturulur ve belirtilen URL'yi açmak için kullanılır.
            if (!sourceUrl.isNullOrEmpty()){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl))
                startActivity(intent)
            }

        }
    }

}
