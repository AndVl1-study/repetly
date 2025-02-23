package com.andvl.repetly.features.students.presentation.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andvl.repetly.R
import com.andvl.repetly.common.data.models.user.User
import com.andvl.repetly.core.ui.components.UserImage


@Composable
fun StudentItem(
    student: User,
    onRemoveStudent: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        leadingContent = { UserImage(photoSrc = student.photoSrc, size = 64.dp) },
        headlineContent = {
            Text(
                "${student.surname} ${student.name}",
                style = MaterialTheme.typography.titleLarge,
            )
        },
        trailingContent = {
            IconButton(onClick = onRemoveStudent, ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun StudentItemPreview() {
    StudentItem(
        student = User.None, {}
    )
}
