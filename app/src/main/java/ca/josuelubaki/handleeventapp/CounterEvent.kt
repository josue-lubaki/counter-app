package ca.josuelubaki.handleeventapp

/**
 * Events originate in the UI and handle in the ViewModel.
 * */
sealed class CounterEvent {
    data class ValueEntered(val value: String) : CounterEvent()
    object CountButtonClicked : CounterEvent()
    object ResetButtonClicked : CounterEvent()
}
