package ca.josuelubaki.handleeventapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.josuelubaki.handleeventapp.ui.theme.HandleEventAppTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HandleEventAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    counterViewModel : CounterViewModel = viewModel()
) {

    val screenState = counterViewModel.screenState.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true){
        counterViewModel.uiEventFlow.collectLatest { event ->
           when(event){
               is UIEvent.ShowMessage -> {
                   scaffoldState.snackbarHostState.showSnackbar(
                       message = event.message
                   )
               }
           }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ){ paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 75.dp)
                ,
                text = screenState.resultValue,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.h4.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                ),
                textAlign = TextAlign.Center,
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 32.dp)
                ,
                value = screenState.inputValue,
                onValueChange = {
                   counterViewModel.onEvent(CounterEvent.ValueEntered(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                ),
                label = { Text(text = "New Count") },
            )

            Column(
                modifier = Modifier
                   .fillMaxWidth()
                   .padding(all = 16.dp)
            ){
                if(screenState.isCountButtonVisible){
                    Button(
                        modifier = Modifier
                            .semantics { contentDescription = "Count Button" }
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        onClick = {
                            counterViewModel.onEvent(CounterEvent.CountButtonClicked)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green,
                        )
                    ){
                        Text(
                            text = "Count",
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.body1.fontSize,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    onClick = {
                        counterViewModel.onEvent(CounterEvent.ResetButtonClicked)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red,
                    )
                ){
                    Text(
                        text = "Reset",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    HandleEventAppTheme {
        MainScreen()
    }
}