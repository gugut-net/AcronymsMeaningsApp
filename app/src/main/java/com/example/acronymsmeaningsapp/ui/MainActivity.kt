package com.example.acronymsmeaningsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.acronymsmeaningsapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment()
    }

    private fun addFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container_fragment, HomeFragment()).commit()
    }
}