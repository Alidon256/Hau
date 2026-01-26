package org.hau.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.hau.project.di.SettingsFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        SettingsFactory.setContext(this)
        setContent {
            App(SettingsFactory())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // Note: Previews might need a mock SettingsFactory or won't work easily with actual platform code
    // App(MockSettingsFactory())
}
