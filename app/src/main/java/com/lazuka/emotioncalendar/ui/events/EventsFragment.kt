package com.lazuka.emotioncalendar.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.lazuka.emotioncalendar.R
import com.lazuka.emotioncalendar.databinding.FragmentEventsBinding
import com.lazuka.emotioncalendar.ui.events.adapter.EventItemDecoration
import com.lazuka.emotioncalendar.ui.events.adapter.EventsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventsViewModel by viewModels()

    private val adapter by lazy { EventsAdapter(viewModel::onEventAction) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        btnCustomize.isVisible = !viewModel.hasFilled()
        btnCustomize.setOnClickListener {
            viewModel.customizeEvents()
        }

        rvEvents.adapter = adapter
        rvEvents.addItemDecoration(EventItemDecoration())
    }

    private fun observeViewModel() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                eventsFlow.collectLatest { list ->
                    binding.groupEmpty.isVisible = list.isEmpty()
                    adapter.submitList(list)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                navigateToProfileFlow.collectLatest {
                    findNavController().navigate(R.id.actionProfile)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                msgFlow.collectLatest(::showMessage)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                isLoading.collectLatest { visible -> binding.progress.isVisible = visible }
            }
        }
    }

    private fun showMessage(@StringRes messageRes: Int) {
        Toast.makeText(requireContext(), messageRes, Toast.LENGTH_LONG).show()
    }
}