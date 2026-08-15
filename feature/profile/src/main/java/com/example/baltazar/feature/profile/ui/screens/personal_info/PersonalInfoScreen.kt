package com.example.baltazar.feature.profile.ui.screens.personal_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.components.CustomDatePickerField
import com.example.baltazar.core.core.components.RoundedButton
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CornerShape
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.core.core.utils.SnackbarType

@Composable
fun PersonalInfoScreen(
    navController: NavHostController,
    viewModel: PersonalInfoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.popBackStack()
        }
    }

    LaunchedEffect(state.userMessage) {
        state.userMessage?.let { userMsg ->
            val text = userMsg.text.asString(context)
            when (userMsg.type) {
                SnackbarType.SUCCESS -> AppSnackbar.success(text)
                SnackbarType.ERROR -> AppSnackbar.error(text)
            }
            viewModel.onMessageShown()
        }
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(R.string.personal_info_title),
                alignment = TitleAlignment.CENTER,
                onBackClick = { navController.popBackStack() }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(Paddings.LargeMinus),
            verticalArrangement = Arrangement.spacedBy(Spaces.Medium)
        ) {
            Text(
                text = stringResource(R.string.overwrite_warning),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            CustomDatePickerField(
                value = state.dateOfBirth,
                onDateSelected = viewModel::onDateOfBirthChange,
                label = stringResource(R.string.date_of_birth_label),
                placeholder = "YYYY-MM-DD",
                errorText = state.dateOfBirthError?.asString(context)
            )

            OutlinedTextField(
                value = state.address,
                onValueChange = viewModel::onAddressChange,
                label = { Text(text = stringResource(R.string.address_label)) },
                placeholder = { Text(text = "Bakı ş., Nizami r.") },
                isError = state.addressError != null,
                supportingText = state.addressError?.let { err ->
                    { Text(text = err.asString(context), color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(BorderRadiuses.Medium),
                singleLine = true
            )

            OutlinedTextField(
                value = state.idNumber,
                onValueChange = viewModel::onIdNumberChange,
                label = { Text(text = stringResource(R.string.id_number_label)) },
                placeholder = { Text(text = "AZE12345678") },
                isError = state.idNumberError != null,
                supportingText = state.idNumberError?.let { err ->
                    { Text(text = err.asString(context), color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(BorderRadiuses.Medium),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(Spaces.Small))

            RoundedButton(
                text = stringResource(R.string.save),
                onClick = { viewModel.save() },
                isLoading = state.isLoading,
                enabled = !state.isLoading,
                shape = CornerShape.Rounded,
                borderRadius = BorderRadiuses.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }
}
