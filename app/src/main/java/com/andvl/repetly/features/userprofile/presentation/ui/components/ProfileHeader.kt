package com.andvl.repetly.features.userprofile.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andvl.repetly.R
import com.andvl.repetly.core.ui.components.UserImage
import com.andvl.repetly.features.userprofile.data.models.ProfileUserData

@Composable
fun ProfileHeader(
    profileUserData: ProfileUserData,
    onImageClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        shape = RectangleShape,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            UserImage(photoSrc = profileUserData.photoSrc, size = 80.dp, onClick = onImageClick)
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "${profileUserData.name} ${profileUserData.surname}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = if (profileUserData.isTutor) stringResource(R.string.profile_screen_tutor_status) else stringResource(
                        R.string.profile_screen_student_status
                    ),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = profileUserData.phoneNumber,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileHeaderPreview() {
    ProfileHeader(
        ProfileUserData(
            name = "Вася",
            surname = "Пупкин",
            phoneNumber = "+8-888-888-88-88",
            isTutor = true,
        ), onImageClick = {}
    )
}
