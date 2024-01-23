package com.colddelight.onestep

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.colddelight.designsystem.theme.OneStepTheme
import com.colddelight.onestep.ui.StepApp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            OneStepTheme {
                StepApp( onAppLogoClick = { onAppLogoClick() })
            }
        }
    }

    fun onAppLogoClick(){
        Log.e("TAG", "onAppLogoClick: h", )
        startActivity(Intent(this, OssLicensesMenuActivity::class.java))
    }
}
