package com.tomasmacri.accessibilitybasiccompose.ui.screens.add_coin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomasmacri.accessibilitybasiccompose.R
import com.tomasmacri.accessibilitybasiccompose.domain.model.Coin
import com.tomasmacri.accessibilitybasiccompose.ui.theme.Purple40

@Composable
fun AddCoinScreenRoot(
    viewModel: AddCoinViewModel,
    onCoinAdded: () -> Unit
) {
    val wasCoinAdded by viewModel.wasCoinAdded.collectAsStateWithLifecycle()
    AddCoin(
        wasCoinAdded = wasCoinAdded,
        onAddCoinClick = { viewModel.addCoin(it) },
        onCoinAdded = { onCoinAdded() }
    )
}

@Composable
fun AddCoin(
    wasCoinAdded: Boolean,
    onAddCoinClick: (Coin) -> Unit,
    onCoinAdded: () -> Unit
) {
    LaunchedEffect(wasCoinAdded) {
        if (wasCoinAdded) {
            onCoinAdded()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        var name by remember { mutableStateOf("") }
        var code by remember { mutableStateOf("") }
        var value by remember { mutableStateOf("") }
        var imageUrl by remember { mutableStateOf("") }
        val description = stringResource(R.string.lorem_ipsum)
        TextFieldWithCheck(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            textfieldText = "",
            label = "Nombre de la moneda",
            isOk = true
        ) {
            name = it
        }

        Spacer(modifier = Modifier.height(24.dp))


        TextFieldWithCheck(
            modifier = Modifier.fillMaxWidth(),
            value = code,
            textfieldText = stringResource(R.string.coin_code_label),
            hint = "BTC",
            isOk = false
        ) {
            code = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextFieldWithCheck(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            textfieldText = "Valor",
            hint = "",
            isOk = false,
            keyboardType = KeyboardType.Number
        ) {
            value = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextFieldWithCheck(
            modifier = Modifier.fillMaxWidth(),
            value = imageUrl,
            textfieldText = "",
            hint = "https://...",
            isOk = false
        ) {
            imageUrl = it
        }

        Spacer(modifier = Modifier.weight(1f))


        Button(
            onClick = {
                if (name.isEmpty() || code.isEmpty() || value.toIntOrNull() == null || imageUrl.isEmpty()) {
                    return@Button
                }
                onAddCoinClick(Coin(name, code, description, value.toInt(), imageUrl, false))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Purple40)
        ) {
            Text(stringResource(R.string.submit))
        }
    }
}

@Composable
fun TextFieldWithCheck(
    modifier: Modifier = Modifier,
    value: String,
    textfieldText: String? = null,
    label: String? = null,
    hint: String? = null,
    isOk: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        textfieldText?.let {
            Text(text = textfieldText)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = value,
                singleLine = true,
                onValueChange = { onValueChange(it) },
                label = { label?.let { Text(label) } },
                placeholder = { hint?.let { Text(hint.uppercase()) } },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(16.dp),
                painter = if (isOk) painterResource(R.drawable.baseline_check_circle_24) else painterResource(R.drawable.baseline_gpp_bad_24),
                tint = if (isOk) Color.Green else Color.Red,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCoinForm() {
    AddCoin(
        wasCoinAdded = false,
        onAddCoinClick = {},
        onCoinAdded = {}
    )
}
