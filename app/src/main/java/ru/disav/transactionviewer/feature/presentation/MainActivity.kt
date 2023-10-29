package ru.disav.transactionviewer.feature.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.disav.transactionviewer.R
import ru.disav.transactionviewer.databinding.ActivityMainBinding
import ru.disav.transactionviewer.feature.presentation.adapter.TransactionAdapter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var adapter: TransactionAdapter

    private val transactionList get() = binding.transactionList
    private val resetButton get() = binding.resetButton

    private var timerJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel.loadData()

        bindStateUpdates()
        setupEvents()

        timerJob = startTimer()

        resetButton.setOnClickListener {
            timerJob?.cancel()
            timerJob = null
            viewModel.resetData()
            timerJob = startTimer()
        }
    }

    private fun setupEvents() = lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.eventFlow.collect {
                when (it) {
                    UiEvent.LoadingError -> {
                        showMessage(resources.getString(R.string.loading_error_message))
                    }

                    UiEvent.RefreshError -> {
                        showMessage(resources.getString(R.string.refresh_error_message))
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = TransactionAdapter()
        transactionList.layoutManager = LinearLayoutManager(baseContext)
        transactionList.adapter = adapter
        transactionList.setHasFixedSize(true)
    }

    private fun bindStateUpdates() = lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.uiState.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun startTimer() = lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            timer(
                period = REFRESH_INTERVAL,
                initialDelay = 0.seconds
            )
                .onEach {
                    viewModel.requestUpdate()
                }
                .launchIn(this)
        }
    }

    private fun showMessage(text: String) {
        Toast.makeText(
            baseContext,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    private suspend fun timer(period: Duration, initialDelay: Duration = Duration.ZERO) =
        flow {
            delay(initialDelay)
            while (true) {
                emit(Unit)
                delay(period)
            }
        }

    companion object {
        val REFRESH_INTERVAL = 2.seconds
    }
}