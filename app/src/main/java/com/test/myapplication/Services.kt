package com.test.myapplication

import android.os.Parcelable
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.parcelize.Parcelize

@Composable
fun ServicesScreen(navController: NavHostController) {
    // TODO: bu sehifede yeqin ki api(ler)e muraciet(ler) olacaq.
    //  hem kateqoriya qruplari, hem de onlara uygun kateqoriyalar.

    Column(Modifier.fillMaxWidth().padding(bottom = 56.dp)) {
        ServicesGrid(navController)
    }
}

@Composable
private fun ServicesGrid(navController: NavHostController) {
    val serviceCategory = listOf(
        ServiceCategory(
            id = 1,
            name = "Sub item 2.1",
            route = "route_2_1",
            groupId = 2,
        ),
        ServiceCategory(
            id = 2,
            name = "Sub item 2.2",
            route = "route_2_2",
            groupId = 2,
        ),
        ServiceCategory(
            id = 4,
            name = "Sub item 3.1",
            route = "route_3_1",
            groupId = 3,
        ),
        ServiceCategory(
            id = 6,
            name = "Sub item 3.2",
            route = "route_3_2",
            groupId = 3,
        ),
    )

    val serviceCategoryGroups = listOf(
        ServiceCategoryGroup(2, "Item 2", "route_2", isEnabled = true),
        ServiceCategoryGroup(3, "Item 3", "route_3", isEnabled = true),
    ).apply {
        elementAt(0).serviceCategories = serviceCategory
    }

    val associatedCategories = serviceCategory.groupBy(ServiceCategory::groupId)

    val gridItems =
        serviceCategoryGroups.groupBy(
            keySelector = { it },
            valueTransform = {
                it.serviceCategories = associatedCategories[it.id] ?: emptyList()
            }
        ).keys.toList()

    Box {
        LazyColumn {
            items(gridItems) { item ->
                GridItem(item, navController)
            }
        }
    }
}

@Composable
private fun GridItem(
    serviceCategoryGroup: ServiceCategoryGroup,
    navController: NavHostController
) {
    Box(
        Modifier.fillMaxWidth().border(.5f.dp, Color(0xFFD8E2F3)).clickable {
            // put parcelable to the arguments and navigate to sub-service screen
            navController.navigateWith(serviceCategoryGroup, SERVICE_ROUTE_PARAM, serviceCategoryGroup.route)
        }
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = serviceCategoryGroup.name,
                maxLines = 4,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Parcelize
data class ServiceCategoryGroup(
    val id: Short,
    val name: String,
    val route: String,
    val isEnabled: Boolean = false,
    var serviceCategories: List<ServiceCategory> = emptyList()
) : Parcelable {
    val serviceCategoryCount: Int
        get() = serviceCategories.size
}

@Parcelize
data class ServiceCategory(
    val id: Short,
    val name: String,
    val route: String,
    val groupId: Short,
) : Parcelable

const val SERVICE_ROUTE_PARAM = "route_param_services"

