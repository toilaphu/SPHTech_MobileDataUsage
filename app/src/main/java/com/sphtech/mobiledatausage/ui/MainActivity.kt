package com.sphtech.mobiledatausage.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sphtech.mobiledatausage.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.app_label)
    }

}
