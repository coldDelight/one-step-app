package com.colddelight.onestep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.colddelight.data.util.LoginHelper
import com.colddelight.data.util.NetworkMonitor
import com.colddelight.designsystem.theme.OneStepTheme
import com.colddelight.onestep.ui.StepApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var loginHelper: LoginHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            OneStepTheme {
                StepApp(networkMonitor = networkMonitor, loginHelper = loginHelper)
            }
        }
    }
}
