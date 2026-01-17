package com.hazrat.dashboard.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hazrat.ui.AppDimens
import com.hazrat.ui.AppDimensions


/**
 * @author hazratummar
 * Created on 10/01/26
 */
 
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onSettingIconClick: () -> Unit = {},
    onFabClick: () -> Unit = {}
){
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Message Later",
                        style = AppTextStyles.screenTitle
                    )
                },
                actions = {
                    IconButton(
                        onClick = onSettingIconClick
                    ){
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "App Settings Icon",
                            modifier = Modifier.padding(end = AppDimens.spacingSm),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Reminder",

                )
            }
        }
    ){paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize()
        ){

        }
    }

}