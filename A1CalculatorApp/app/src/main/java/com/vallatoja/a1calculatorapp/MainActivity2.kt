package com.vallatoja.a1calculatorapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        // func for welcome pop up
        showWelcomeDialog()
        // assigning values to main activity 2 ui elements
        val selectDateButton = findViewById<Button>(R.id.selectDateButton)
        val selectedDateText = findViewById<TextView>(R.id.selectedDateText)
        val zodiacImage = findViewById<ImageView>(R.id.zodiacImage)
        val zodiacDescriptionText = findViewById<TextView>(R.id.zodiacDescriptionText)

        // make sure zodiac image is invisible by default
        zodiacImage.visibility = View.INVISIBLE

        selectDateButton.setOnClickListener {
            // assigning values to calendar inputs
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            // getting the recent date
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val birthDate = Calendar.getInstance()
                birthDate.set(year, month, dayOfMonth)
                // calculates exact age if user gets their birthday this year
                val today = Calendar.getInstance()
                var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
                if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
                    age--
                }
                // uses costume logic to figure out zodiac depending on birth month
                val zodiac = getZodiacSign(dayOfMonth, month)
                selectedDateText.text = "You are $age years old\nZodiac Sign: $zodiac"

                // load zodiac image dynamically
                val zodiacDrawableName = "zodiac_${zodiac.lowercase()}"
                val resId = resources.getIdentifier(zodiacDrawableName, "drawable", packageName)
                // if image is found, show it. if not, stay to placeholder.
                if (resId != 0) {
                    zodiacImage.setImageResource(resId)
                    zodiacImage.visibility = View.VISIBLE
                } else {
                    zodiacImage.setImageResource(R.drawable.ic_zodiac_placeholder) // zodiac_x.png replaces ic_zodiac_placeholder (template)
                    zodiacImage.visibility = View.VISIBLE
                }

                // show description
                val zodiacDescription = zodiacDescriptions[zodiac] ?: "No description available."
                zodiacDescriptionText.text = zodiacDescription

            }, currentYear, currentMonth, currentDay)
            // limit to current date
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            // show calendar modal
            datePickerDialog.show()
        }
    }

    // utility function for zodiac sign based on birthdate
    private fun getZodiacSign(day: Int, month: Int): String { // returns a zodiac name based on day and month
        return when (month + 1) {
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

    // welcome dialog
    private fun showWelcomeDialog() {
        AlertDialog.Builder(this)
            .setTitle("Welcome!")
            .setMessage("Thanks for using my Age Calculator ✨\n\nHere you can choose your birthdate to discover your age and zodiac sign.")
            .setPositiveButton("Let's go!") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    // zodiac descriptions
    private val zodiacDescriptions = mapOf( // an immutable description map. a look up table that holds 12 zodiac signs names and their respective descriptions.
        "Aries" to "Loves to be number one. No stranger to competition.",
        "Taurus" to "Adores the essence of serene relaxation and tranquility.",
        "Gemini" to "Spontaneous, playful and adorably erratic.",
        "Cancer" to "Highly intuitive and their psyche shines in tangible spaces.",
        "Leo" to "Passionate, loyal and infamously dramatic.",
        "Virgo" to "Logical, practical and systematic in their approach to life.",
        "Libra" to "Defines balance, harmony and justice. Strives to create equilibrium in all areas of life.",
        "Scorpio" to "Elusive and mysterious. Derives extraordinary courage.",
        "Sagittarius" to "Always on a quest for knowledge. Chasing after geographical intellectual and spiritual adventures.",
        "Capricorn" to "Knows patience, perseverance and dedication.",
        "Aquarius" to "Innovative, progressive and shamelessly revolutionary. Dedicated to making the world a better place.",
        "Pisces" to "Most intuitive, sensitive and empathetic."
    )
}
