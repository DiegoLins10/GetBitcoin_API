package com.example.bitcoin_api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bloco_cotacao.*
import kotlinx.android.synthetic.main.bloco_data.*
import kotlinx.android.synthetic.main.bloco_entrada.*
import kotlinx.android.synthetic.main.bloco_maior.*
import kotlinx.android.synthetic.main.bloco_menor.*
import kotlinx.android.synthetic.main.bloco_saida.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val API_URL = "https://www.mercadobitcoin.net/api/BTC/ticker/"

    var cotacaoBitcoin: Double = 0.0
    var maiorBitcoin: Double = 0.0
    var menorBitcoin: Double = 0.0
    var dataBitcoin: Long = 0
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
        btnCalcular.setOnClickListener(){
            calcular()
        }
    }
    fun buscaCotacao(){
        doAsync {
            //acessar API e buscar seu resultado
            val resposta = URL(API_URL).readText()
            cotacaoBitcoin = JSONObject(resposta).getJSONObject("ticker").getDouble("last")
            maiorBitcoin = JSONObject(resposta).getJSONObject("ticker").getDouble("high")
            menorBitcoin = JSONObject(resposta).getJSONObject("ticker").getDouble("low")
            dataBitcoin = JSONObject(resposta).getJSONObject("ticker").getLong("date")
            val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            val date: Date = Date(dataBitcoin * 1000L)
            val formato = "dd/MM/yyyy HH:mm:ss"
            val format = SimpleDateFormat(formato)
            val dataFormatada = format.format(date)
            val cotacaoFormatada = f.format(cotacaoBitcoin)
            val maiorFormatada = f.format(maiorBitcoin)
            val menorFormatada = f.format(maiorBitcoin)
            uiThread {
               // alert ("$cotacaoBitcoin").show()
               // alert ("$cotacaoFormatada").show()
            txtCotacao.setText("$cotacaoFormatada")
            txtMaior.setText("$maiorFormatada")
            txtMenor.setText("$menorFormatada")
            txtData.setText("$dataFormatada")
            }
        }
    }
    fun calcular(){
        if(txtValor.text.isEmpty()){
            txtValor.error = "Preencha um valor"
            return
        }
        //valor digitado usuario
        val valorDigitado = txtValor.text.toString()
            .replace(",", ".").toDouble()
            //.replace(".","").toDouble().toDouble()
        val resultado = if(cotacaoBitcoin>0) valorDigitado / cotacaoBitcoin
        else 0.0
        txtQtdBitcoins.text = "%.8f".format(resultado)
    }
}