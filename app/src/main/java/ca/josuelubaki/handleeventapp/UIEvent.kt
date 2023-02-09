package ca.josuelubaki.handleeventapp

/**
 * Events originate in the ViewModel and handle in the UI.
 * */
sealed class UIEvent {
    data class ShowMessage(val message: String) : UIEvent()
}
