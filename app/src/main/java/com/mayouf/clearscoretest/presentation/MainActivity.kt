package com.mayouf.clearscoretest.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mayouf.clearscoretest.R
import com.mayouf.clearscoretest.presentation.creditreport.CreditReportFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                CreditReportFragment.newInstance(),
                CreditReportFragment.FRAGMENT_NAME
            )
            .commitAllowingStateLoss()
    }
}