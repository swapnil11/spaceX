package com.example.finalplayground.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.example.finalplayground.R
import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.databinding.FragmentLaunchDetailBinding
import com.example.finalplayground.domain.model.RocketDetails
import com.example.finalplayground.ui.common.showErrorBar
import com.example.finalplayground.ui.viewmodel.LaunchDetailViewModel

class LaunchDetailFragment : Fragment() {
    private lateinit var binding: FragmentLaunchDetailBinding
    private val viewModel by hiltNavGraphViewModels<LaunchDetailViewModel>(R.id.launchDetailFragment)
    private val args by navArgs<LaunchDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLaunchDetailBinding.inflate(inflater, container, false).apply {
            binding = this
            binding.launchItem = args.item
            lifecycleOwner = this@LaunchDetailFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRocketDetails()
        requireActivity().title = args.item.name
    }

    private fun observeRocketDetails() {
        viewModel.rocketDetails.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.isLoading = true
                }
                Resource.Status.SUCCESS -> handleSuccessResponse(data = it.data)
                Resource.Status.ERROR -> handleErrorResponse(msg = it.message)
            }
        }
    }

    private fun handleSuccessResponse(data: RocketDetails? = null) {
        binding.isLoading = false
        binding.isError = data == null
        binding.rocketItem = data
    }

    private fun handleErrorResponse(msg: String? = null) {
        binding.isLoading = false
        binding.isError = true
        showErrorBar(msg)
    }
}
