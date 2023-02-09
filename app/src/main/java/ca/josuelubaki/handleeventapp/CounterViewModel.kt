package ca.josuelubaki.handleeventapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel() {

    private val _screenState : MutableState<MainScreenState> = mutableStateOf(
        // initial state of the screen
        MainScreenState(
            inputValue = "",
            resultValue = "Total is ...",
            isCountButtonVisible = false
        )
    )
    val screenState : State<MainScreenState> = _screenState

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow : SharedFlow<UIEvent> = _uiEventFlow.asSharedFlow()

    private var total = 0.0
    private fun addTotal(){
        total += _screenState.value.inputValue.toDouble()
        _screenState.value = _screenState.value.copy(
            resultValue = "Total is $total",
            inputValue = "",
            isCountButtonVisible = false
        )
    }

    private fun resetTotal(){
        total = 0.0
        _screenState.value = _screenState.value.copy(
            resultValue = "Total is $total",
            inputValue = "",
            isCountButtonVisible = false
        )
    }

    fun onEvent(event : CounterEvent) {
        when (event) {
            is CounterEvent.ValueEntered -> {
                _screenState.value = _screenState.value.copy(
                    inputValue = event.value,
                    isCountButtonVisible = true
                )
            }

            is CounterEvent.CountButtonClicked -> {
                addTotal()
                viewModelScope.launch {
                    _uiEventFlow.emit(UIEvent.ShowMessage(
                        message = "Value added successfully",
                    ))
                }
            }

            is CounterEvent.ResetButtonClicked -> {
                resetTotal()
                viewModelScope.launch {
                    _uiEventFlow.emit(UIEvent.ShowMessage(
                        message = "Value reset successfully",
                    ))
                }
            }
        }
    }
}