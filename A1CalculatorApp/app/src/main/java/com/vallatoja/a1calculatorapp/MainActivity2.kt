package com.vallatoja.a1calculatorapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val selectDateButton = findViewById<Button>(R.id.selectDateButton)
        val selectedDateText = findViewById<TextView>(R.id.selectedDateText)
        val zodiacImage = findViewById<ImageView>(R.id.zodiacImage)

        selectDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val birthDate = Calendar.getInstance()
                birthDate.set(year, month, dayOfMonth)

                val today = Calendar.getInstance()
                var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)

                if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
                    age--
                }

                val zodiac = getZodiacSign(dayOfMonth, month)
                selectedDateText.text = "You are $age years old\nZodiac Sign: $zodiac"

                // Load zodiac image based on sign
                val zodiacDrawableName = "zodiac_${zodiac.lowercase()}"
                val resId = resources.getIdentifier(zodiacDrawableName, "drawable", packageName)

                if (resId != 0) {
                    zodiacImage.setImageResource(resId)
                    zodiacImage.visibility = View.VISIBLE
                } else {
                    // optional: keep it invisible if no icon
                    zodiacImage.visibility = View.INVISIBLE
                }

            }, currentYear, currentMonth, currentDay)

            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
    }

    private fun getZodiacSign(day: Int, month: Int): String {
        return when (month + 1) { // month is 0-indexed
            1 -> if (day < 20) "Capricorn" else "Aquarius"
            2 -> if (day < 19) "Aquarius" else "Pisces"
            3 -> if (day < 21) "Pisces" else "Aries"
            4 -> if (day < 20) "Aries" else "Taurus"
            5 -> if (day < 21) "Taurus" else "Gemini"
            6 -> if (day < 21) "Gemini" else "Cancer"
            7 -> if (day < 23) "Cancer" else "Leo"
            8 -> if (day < 23) "Leo" else "Virgo"
            9 -> if (day < 23) "Virgo" else "Libra"
            10 -> if (day < 23) "Libra" else "Scorpio"
            11 -> if (day < 22) "Scorpio" else "Sagittarius"
            12 -> if (day < 22) "Sagittarius" else "Capricorn"
            else -> "Unknown"
        }
    }
}
