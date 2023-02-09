package ca.josuelubaki.handleeventapp

/**
 * This is a state that is used to keep track of the state of the app.
 * */
data class MainScreenState (
    var isCountButtonVisible: Boolean = false,
    var resultValue: String = "",
    var inputValue : String = ""
)
