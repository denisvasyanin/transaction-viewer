package ru.disav.transactionviewer.feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.disav.transactionviewer.feature.domain.usecase.LoadTransactionsUseCase
import ru.disav.transactionviewer.feature.domain.usecase.RequestUpdateUseCase
import ru.disav.transactionviewer.feature.domain.usecase.ResetDataUseCase
import ru.disav.transactionviewer.feature.presentation.model.TransactionUiModel
import ru.disav.transactionviewer.feature.presentation.model.toUiModel
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val loadTransactionsUseCase: LoadTransactionsUseCase,
    private val requestUpdateUseCase: RequestUpdateUseCase,
    private val resetDataUseCase: ResetDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(emptyList<TransactionUiModel>())
    val uiState = _uiState.asStateFlow()

    private val eventChannel = Channel<UiEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            loadTransactionsUseCase()
                .catch {
                    eventChannel.send(UiEvent.LoadingError)
                }
                .collect {
                    val items = mutableSetOf<String>()

                    _uiState.value = it.flatMap { transactionModel ->
                        val transaction = transactionModel.toUiModel()

                        val date = timeFormatter.format(transactionModel.created)

                        if (items.contains(date).not()) {
                            items.add(date)

                            listOf(
                                TransactionUiModel.TimeItem(
                                    time = timeFormatter.format(transactionModel.created)
                                ),
                                transaction
                            )
                        } else {
                            listOf(transaction)
                        }
                    }
                }
        }
    }

    fun resetData() {
        viewModelScope.launch(Dispatchers.IO) {
            resetDataUseCase()
                .catch {
                    eventChannel.send(UiEvent.LoadingError)
                }
                .collect {}
        }
    }

    suspend fun requestUpdate() {
        requestUpdateUseCase()
            .catch {
                eventChannel.send(UiEvent.RefreshError)
            }
            .collect {}
    }

    companion object {
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    }
}

