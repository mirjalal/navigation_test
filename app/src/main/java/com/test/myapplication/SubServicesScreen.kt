package com.test.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SubServicesScreen(navController: NavHostController, serviceCategoryGroup: ServiceCategoryGroup) {
    Column(Modifier.fillMaxWidth().padding(bottom = 56.dp)) {
        SubServicesList(navController, serviceCategoryGroup)
    }
}

@Composable
private fun SubServicesList(
    navController: NavHostController,
    serviceCategoryGroup: ServiceCategoryGroup
) {
    LazyColumn {
        items(serviceCategoryGroup.serviceCategories) {
            SubServiceItem(it, navController)
        }
    }
}

@Composable
private fun SubServiceItem(item: ServiceCategory, navController: NavHostController) {
    Column {
        Box(
            Modifier.fillMaxWidth().border(.5f.dp, Color(0xFFD8E2F3)).clickable {
                navController.navigate(item.route)
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f).padding(start = 16.dp)
                )
                Image(
                    imageVector = Icons.Sharp.ChevronRight,
                    contentDescription = null,
                )
            }
        }
    }
}
