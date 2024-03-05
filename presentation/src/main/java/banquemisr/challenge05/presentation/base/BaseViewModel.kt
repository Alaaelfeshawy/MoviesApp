package banquemisr.challenge05.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<Event : UIEvent, State : UIState, Effect : UIEffect>(private val coroutineDispatcher: CoroutineDispatcher) :
    ViewModel() {

    abstract fun createInitialState(): State
//    abstract fun onExceptionThrown(throwable: Throwable)
    abstract fun handleEvent(uiEvent: UIEvent)

    private val initialState: State by lazy { createInitialState() }

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect?> = Channel()
    var effect = _effect.receiveAsFlow()

    val currentState: State
        get() = uiState.value

//    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        onExceptionThrown(throwable)
//    }

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        launchCoroutineScope {
            withContext(Dispatchers.Main) {
                _event.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    fun setEvent(event: Event) {
        val newEvent = event
        launchCoroutineScope(Dispatchers.Main) {
            _event.resetReplayCache()
            _event.emit(newEvent)
        }
    }

    fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    fun setEffect(effect: () -> Effect) {
        val effectValue = effect()
        launchCoroutineScope {
            _effect.send(effectValue)
        }
    }

    protected fun resetEffect() {
        launchCoroutineScope {
            _effect.send(null)
        }
    }

    protected fun launchCoroutineScope(
        dispatcher: CoroutineDispatcher = coroutineDispatcher,
        func: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher /*+ coroutineExceptionHandler*/) {
            func.invoke()
        }
    }

    override fun onCleared() {
        launchCoroutineScope {
            currentCoroutineContext().cancelChildren()
        }
        super.onCleared()
    }
}