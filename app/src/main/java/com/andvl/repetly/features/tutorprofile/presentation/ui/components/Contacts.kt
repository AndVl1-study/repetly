package com.andvl.repetly.features.tutorprofile.presentation.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andvl.repetly.R
import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.contact.Contact
import com.andvl.repetly.common.data.models.contact.ContactType
import com.andvl.repetly.core.ui.theme.repetlyTheme

@Composable
fun Contacts(
    contacts: List<Contact>,
    modifier: Modifier = Modifier,
    @StringRes title: Int = R.string.my_contacts,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier)
        contacts.forEach {
            ContactItem(
                contact = it,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_small))
            )
        }
    }

}

@Preview
@Composable
fun ContactsPreview() {
    repetlyTheme {
        Contacts(
            contacts = listOf(
                Contact(
                    id = Id(""),
                    value = "ddafs",
                    type = ContactType.TELEGRAM.value
                ),
                Contact(
                    id = Id(""),
                    value = "adsadsdf",
                    type = ContactType.WHATSAPP.value
                ),
                Contact(
                    id = Id(""),
                    value = "adsadsdf",
                    type = ContactType.OTHER.value
                )
            )
        )
    }
}

