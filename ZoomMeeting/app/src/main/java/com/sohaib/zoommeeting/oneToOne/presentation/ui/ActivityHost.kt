package com.sohaib.zoommeeting.oneToOne.presentation.ui

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.sohaib.zoommeeting.R
import com.sohaib.zoommeeting.databinding.ActivityHostBinding
import com.sohaib.zoommeeting.oneToOne.data.dataSources.network.ZoomManager
import com.sohaib.zoommeeting.oneToOne.data.repository.RepositoryHost
import com.sohaib.zoommeeting.oneToOne.domain.useCases.UseCaseHost
import com.sohaib.zoommeeting.oneToOne.presentation.viewModels.ViewModelHost
import com.sohaib.zoommeeting.oneToOne.presentation.viewModels.ViewModelProviderHost
import com.sohaib.zoommeeting.utilities.base.BaseActivity
import com.sohaib.zoommeeting.utilities.utils.ConstantUtils.TAG

class ActivityHost : BaseActivity<ActivityHostBinding>(ActivityHostBinding::inflate) {

    // MVVM
    private val dataSourceZoomManager by lazy { ZoomManager(this) }
    private val repository by lazy { RepositoryHost(dataSourceZoomManager) }
    private val useCase by lazy { UseCaseHost(repository) }
    private val viewModel by viewModels<ViewModelHost> { ViewModelProviderHost(useCase) }

    override fun onCreated() {
        initObservers()

        binding.mbStartMeetingHost.setOnClickListener { viewModel.startMeeting() }
    }

    private fun initObservers() {
        viewModel.sdkInitializedLiveData.observe(this) { isInitialized ->
            binding.mbStartMeetingHost.isEnabled = isInitialized
            when (isInitialized) {
                true -> binding.mtvStatusHost.text = getString(R.string.initialization_success)
                false -> binding.mtvStatusHost.text = getString(R.string.initialization_failure)
            }
        }

        viewModel.intentLiveData.observe(this) { intent ->
            startActivity(intent)
        }

        viewModel.meetingStatusLiveData.observe(this) { status ->
            binding.progressBarHost.isVisible = status == 0
            when (status) {
                0 -> binding.mtvStatusHost.text = getString(R.string.meeting_status_progress)
                1 -> binding.mtvStatusHost.text = getString(R.string.meeting_status_success)
                2 -> binding.mtvStatusHost.text = getString(R.string.meeting_status_failure)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        Log.d(TAG, "onNewIntent: called: $intent")
        viewModel.onNewIntent(intent)

        /*if (ConstantUtils.ACTION_RETURN_FROM_MEETING == intent.action) {
            startMeeting()
        }*/
    }

    private fun startMeeting() {
    }
}