package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
private const val operandstored = " Operand"
private const val operatorstored = "Operator"
private const val storedstate = " xyz"


class MainActivity : AppCompatActivity() {
    private var operand:Double?= null
    private var pendingoperator=  "="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title =
            "My Calculator"
        val listener = View.OnClickListener { v ->
            val b = v as Button
            text_opeartion.append(b.text)
        }

        val listener2 = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = text_opeartion.text.toString().toDouble()
                operationperform(value, op)
            } catch (e: NumberFormatException) {
                text_opeartion.setText("")
            }
            pendingoperator = op
            text_operand.text=pendingoperator
        }
        val listener3 = View.OnClickListener { v ->
            text_opeartion.setText("-")
            operand= operand?.times(-1)
        }
        val listerner4 = View.OnClickListener { v->
            pendingoperator= "="
            text_opeartion.setText("")
            text_operand.setText("")
            text_result.setText("")
            operand=null
        }
        button_0.setOnClickListener(listener)
        button_1.setOnClickListener(listener)
        button_2.setOnClickListener(listener)
        button_3.setOnClickListener(listener)
        button_4.setOnClickListener(listener)
        button_5.setOnClickListener(listener)
        button_6.setOnClickListener(listener)
        button_7.setOnClickListener(listener)
        button_8.setOnClickListener(listener)
        button_9.setOnClickListener(listener)
        button_divide.setOnClickListener(listener2)
        button_minus.setOnClickListener(listener2)
        button_multiply.setOnClickListener(listener2)
        button_plus.setOnClickListener(listener2)
        button_dot.setOnClickListener(listener)
        button_equals.setOnClickListener(listener2)
        button_neg.setOnClickListener(listener3)
        button_clear.setOnClickListener(listerner4)

    }

    private fun operationperform(value: Double, op: String) {
        if (operand == null) {
            operand = value
        } else {
            if (pendingoperator == "=")
                pendingoperator = op
            when (pendingoperator) {
                "=" -> operand = value
                "*" -> operand = operand!! * value
                "+" -> operand = operand!! + value
                "-" -> operand = operand!! - value
                "/" -> operand = if (value == 0.0) {
                    Double.NaN
                } else
                    operand!! / value
            }
        }
        text_result.setText(operand.toString())
        text_opeartion.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(operand != null) {
            outState.putDouble(operandstored, operand!!)
            outState.putBoolean(storedstate, true)
        }
        outState.putString(operatorstored, pendingoperator)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState.getBoolean(storedstate,false))
        {
            operand= savedInstanceState.getDouble(operandstored)
        }
        else
        { operand = null}
        pendingoperator= savedInstanceState.getString(operatorstored).toString()
        text_operand.text =pendingoperator
    }
}
