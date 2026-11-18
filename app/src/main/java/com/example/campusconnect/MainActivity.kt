package com.example.campusconnect

import android.R.attr.id
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

data class CampusResource(
    @StringRes val nameRes: Int,
    @StringRes val categoryRes: Int,
    @StringRes val hoursRes: Int,
    val lastUpdatedDate: LocalDate,
    val reviewCount: Int,
    val capacity: Int,
    val fee: Double

)

@RequiresApi(Build.VERSION_CODES.O)
val sampleResources = listOf(
    CampusResource(
        nameRes = R.string.resource_intl_office_name,
        categoryRes = R.string.resource_intl_office_category,
        hoursRes = R.string.resource_intl_office_hours,
        lastUpdatedDate = LocalDate.of(2026, 8, 3),
        reviewCount = 5,
        capacity = 1400,
        fee = 0.20
    ),
    CampusResource(
        nameRes = R.string.resource_library_name,
        categoryRes = R.string.resource_library_category,
        hoursRes = R.string.resource_library_hours,
        lastUpdatedDate = LocalDate.of(2026, 8, 3),
        reviewCount = 25,
        capacity = 1600,
        fee = 0.40
    ),
    CampusResource(
        nameRes = R.string.resource_veterans_name,
        categoryRes = R.string.resource_veterans_category,
        hoursRes = R.string.resource_veterans_hours,
        lastUpdatedDate = LocalDate.of(2026, 8, 3),
        reviewCount = 10,
        capacity = 1000,
        fee = 0.30
    )
)

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CampusConnectScreen()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CampusConnectScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineMedium)

        Text(stringResource(R.string.app_subtitle), style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(sampleResources) { resource ->
                ResourceCard(resource)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResourceCard(resource: CampusResource) {
    val context = LocalContext.current
    val locale = Locale.getDefault()
    val dateFullFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.FULL)
        .withLocale(locale)
    val dateShortFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.SHORT)
    val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
    val integerFormatter = NumberFormat.getIntegerInstance(locale)

    val name = stringResource(resource.nameRes)
    val category = stringResource(resource.categoryRes)
    val hours = stringResource(resource.hoursRes)

    val reviewText = context.resources.getQuantityString(
        R.plurals.review_count,
        resource.reviewCount,
        resource.reviewCount
    )

    val capacityFormatted = integerFormatter.format(resource.capacity)
    val contentDesc = stringResource(
        R.string.content_desc_resource_card,
        name,
        category,
        hours
    )

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(stringResource(resource.nameRes), style = MaterialTheme.typography.titleMedium)
            Text(stringResource(resource.categoryRes), style = MaterialTheme.typography.bodySmall)
            Text(stringResource(R.string.hours_label, stringResource(resource.hoursRes)))
            Text(reviewText)

            if (resource.fee > 0.0) {
                Text(stringResource(
                    R.string.fee_label,
                    currencyFormatter.format(resource.fee)
                ))
            }

            Text(
                stringResource(
                    R.string.updated_short_label,
                    resource.lastUpdatedDate.format(dateShortFormatter)
                ),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                stringResource(
                    R.string.last_updated_label,
                    resource.lastUpdatedDate.format(dateFullFormatter)
                ),
                style = MaterialTheme.typography.bodySmall
            )


        }
    }
}
