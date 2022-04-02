package com.multitech.mytrips

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCalculate.setOnClickListener(this)
    }
    private fun isValid(): Boolean{
        return editDistance.text.isNotEmpty() &&
                editPrice.text.isNotEmpty() &&
                editAutonomy.text.isNotEmpty() &&
                editAutonomy.text.toString() != "R$ 0,00"
    }

    @SuppressLint("SetTextI18n")
    private fun handleCalculate(){
        val valid = isValid()
        if(valid){
            try{
                val distanceReturnSelected = editDistance.text.toString().toFloat()
                val priceReturnSelected = editPrice.text.toString().toFloat();
                val autonomyReturnSelected = editAutonomy.text.toString().toFloat();

                val dec = DecimalFormat("#,###.00")

                val result = (distanceReturnSelected * priceReturnSelected) / autonomyReturnSelected;
                textResult.text= "R$ ${dec.format(result)}"
            }
            catch(err: Exception){
                Toast.makeText(this,"Informe valores válidos", Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this,"Preencha todos os campos ", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View) {
        if(v.id == R.id.btnCalculate) handleCalculate();
    }

}