package com.andvl.repetly.features.tutorsearch.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.andvl.repetly.R
import com.andvl.repetly.common.data.models.user.Tutor

@Composable
fun TutorList(
    tutors: List<Tutor>,
    onTutorCardClicked: (Tutor) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_medium),
            Alignment.Top
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        items(tutors) {
            TutorCard(
                modifier = Modifier.fillMaxWidth(),
                tutor = it,
                onCardClicked = onTutorCardClicked
            )
        }
    }
}
