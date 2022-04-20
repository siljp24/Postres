package com.example.postres

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.example.postres.databinding.ActivityMainBinding
import com.example.postres.utils.Postre
import java.math.RoundingMode

const val KEY_INGRESOS = "ingresos"
const val KEY_CANTIDAD_VENDIDA = "cantidad_vendida"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val postres = listOf(
        Postre(R.drawable.cupcake, 3.00, 5),
        Postre(R.drawable.donut,1.50, 10),
        Postre(R.drawable.eclair,1.80, 15),
        Postre(R.drawable.froyo,2.40, 20),
        Postre(R.drawable.gingerbread,0.8, 25),
        Postre(R.drawable.honeycomb,3.50, 30),
        Postre(R.drawable.icecreamsandwich,1.20, 35),
        Postre(R.drawable.jellybean,1.50, 40),
        Postre(R.drawable.kitkat, 2.80,45),
        Postre(R.drawable.lollipop, 0.80, 50),
        Postre(R.drawable.marshmallow, 0.30, 55),
        Postre(R.drawable.nougat, 6.20, 60),
        Postre(R.drawable.oreo, 0.50, 65)
    )

    private var ingresos = 0.00
    private var cantidadVendida = 0

    private var postreActual = postres[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState != null) {
            ingresos = savedInstanceState.getDouble(KEY_INGRESOS, 0.00)
            cantidadVendida = savedInstanceState.getInt(KEY_CANTIDAD_VENDIDA, 0)
            actualPostre()
        }
        binding.ingresos = ingresos.toBigDecimal().setScale(2, RoundingMode.HALF_DOWN).toDouble()
        binding.cantidadVendida = cantidadVendida
        binding.buttonPostre.setImageResource(postreActual.image)
        binding.buttonPostre.setOnClickListener{ postreClickado() }
    }
    private fun postreClickado(){
        ingresos += postreActual.precio
        cantidadVendida++

        binding.ingresos = ingresos.toBigDecimal().setScale(2, RoundingMode.HALF_DOWN).toDouble()
        binding.cantidadVendida =cantidadVendida
        actualPostre()
    }
    private fun actualPostre(){
        var nuevoPostre = postres[0]
        if (cantidadVendida > postres.last().cantidadInicial) {
            Toast.makeText(this, getString(R.string.all_sold), Toast.LENGTH_LONG).show()
            finish()
        }
        for(postre in postres){
            if(cantidadVendida > postre.cantidadInicial){
                if( postres.last() != postre){
                    nuevoPostre = postres[postres.indexOf(postre) + 1]
                }
            }
        }
        if(postreActual != nuevoPostre){
            binding.buttonPostre.setImageResource(nuevoPostre.image)
            postreActual = nuevoPostre
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.shareMenuButton -> {
                compartir()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun compartir(){
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_text, cantidadVendida, ingresos))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        }catch (ex:ActivityNotFoundException){
            Toast.makeText(this,getString(R.string.share_not_available),Toast.LENGTH_LONG).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble(KEY_INGRESOS, ingresos)
        outState.putInt(KEY_CANTIDAD_VENDIDA, cantidadVendida)
    }

}

