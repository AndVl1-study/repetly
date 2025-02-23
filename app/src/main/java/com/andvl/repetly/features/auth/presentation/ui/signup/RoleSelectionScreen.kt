package com.andvl.repetly.features.auth.presentation.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.andvl.repetly.R

@Composable
fun RoleSelectionScreen(
    setCanBeTutor: (Boolean) -> Unit,
    navigateNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.registration_choose_role),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large))
        )

        Row {
            RoleButton(
                role = stringResource(R.string.pupil),
                onClick = {
                    setCanBeTutor(false)
                    navigateNext()
                },
                modifier = Modifier.weight(0.5f)
            )

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))

            RoleButton(
                role = stringResource(R.string.tutor),
                onClick = {
                    setCanBeTutor(true)
                    navigateNext()
                },
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}

@Composable
fun RoleButton(
    role: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        onClick = onClick
    ) {
        Text(
            text = role,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RoleSelectionScreen({}, {})
}
