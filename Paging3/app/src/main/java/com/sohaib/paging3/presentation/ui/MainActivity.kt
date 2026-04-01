package com.sohaib.paging3.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.sohaib.paging3.R
import com.sohaib.paging3.databinding.ActivityMainBinding
import com.sohaib.paging3.presentation.adapter.QuotePagingAdapter
import com.sohaib.paging3.presentation.adapter.QuotesLoadStateAdapter
import com.sohaib.paging3.presentation.viewModels.QuotesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val quoteAdapter by lazy { QuotePagingAdapter() }
    private val loadStateAdapter = QuotesLoadStateAdapter(onRetry = { quoteAdapter.refresh() })

    private val viewModel: QuotesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fullScreenRetry.setOnClickListener { quoteAdapter.refresh() }

        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        binding.rvList.adapter = quoteAdapter.withLoadStateFooter(loadStateAdapter)
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.quotes.collect { pagingData ->
                        quoteAdapter.submitData(lifecycle, pagingData)
                    }
                }
                launch {
                    quoteAdapter.loadStateFlow.collect { loadState ->
                        binding.root.post { bindListState(loadState) }
                    }
                }
            }
        }
    }

    /**
     * Maps Paging [CombinedLoadStates] + current item count to UI:
     * - **Loading**: initial refresh, no items yet.
     * - **Error**: initial refresh failed, no items (pagination errors stay on the footer).
     * - **Empty**: refresh finished, no more pages, list is empty (not an error).
     * - **Success**: at least one item; list is shown (footer still reflects append/prepend).
     */
    private fun bindListState(loadState: CombinedLoadStates) {
        val itemCount = quoteAdapter.itemCount
        val refresh = loadState.refresh

        val loading = refresh is LoadState.Loading && itemCount == 0
        val error = refresh is LoadState.Error && itemCount == 0
        val empty = refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && itemCount == 0

        binding.progressBar.isVisible = loading
        binding.loadingMessage.isVisible = loading
        binding.fullScreenError.isVisible = error
        binding.emptyState.isVisible = empty
        binding.rvList.isVisible = itemCount > 0

        if (error) {
            val err = refresh.error
            binding.fullScreenErrorText.text = err.localizedMessage ?: err.message ?: getString(R.string.error_loading_quotes)
        }
    }
}
