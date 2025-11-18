package com.rivera.bodyalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rivera.bodyalert.ui.screens.dashboard.DashboardScreen
import com.rivera.bodyalert.ui.theme.BodyAlertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BodyAlertTheme {
                DashboardScreen()
            }
        }
    }
}