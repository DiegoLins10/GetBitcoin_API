package com.example.bitcoin_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import java.net.URL
import android.util.Log
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.lang.RuntimeException

import kotlinx.android.synthetic.main.bloco_cotacao.*
import kotlinx.android.synthetic.main.bloco_entrada.*
import kotlinx.android.synthetic.main.bloco_saida.*
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val API_URL = "https://www.mercadobitcoin.net/api/BTC/ticker/"

    var cotacaoBitcoin: Double = 0.0
    private val TAG = "Fatec Ferraz"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       /* Log.v(TAG, "LOG DE VERBOSE")
        Log.d(TAG, "LOG DE DEBUG")
        Log.i(TAG, "LOG DE INFORMATION")
        Log.w(TAG, "LOG DE ALERTA")
        Log.e(TAG, "LOG DE ERRO", RuntimeException("TESTE ERRO"))*/
        buscaCotacao()
    }
    fun buscaCotacao(){
        doAsync {
            //acessar API e buscar seu resultado
            val resposta = URL(API_URL).readText()
            cotacaoBitcoin = JSONObject(resposta).getJSONObject("ticker").getDouble("last")
            val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            val cotacaoFormatada = f.format(cotacaoBitcoin)
            uiThread {
                alert ("$cotacaoBitcoin").show()
                alert ("$cotacaoFormatada").show()
                //txtCotacao.setText(cotacaoFormatada)
            }
        }
    }
}