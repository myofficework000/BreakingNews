package com.code4galaxy.paging3withjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.code4galaxy.paging3withjetpackcompose.presentation.NewsScreen
import com.code4galaxy.paging3withjetpackcompose.ui.theme.Paging3WithJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Paging3WithJetpackComposeTheme {
                NewsScreen()
            }
        }
    }
}
