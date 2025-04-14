package com.vallatoja.a1calculatorapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Wait 2 seconds, then go to MainActivity2
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }, 2000) // 2000ms = 2 seconds
    }
}