package com.andvl.repetly.features.tutorprofile.presentation.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.andvl.repetly.R
import com.andvl.repetly.core.ui.components.InfoBlockWithLabel

@Composable
fun ReviewsNumber(
    reviewsNumber: Int,
    modifier: Modifier = Modifier
) {
    InfoBlockWithLabel(
        label = stringResource(R.string.reviews_label),
        modifier = modifier
    ) {
        Text(
            text = reviewsNumber.toString(),
            style = MaterialTheme.typography.titleLarge
        )
    }
}
