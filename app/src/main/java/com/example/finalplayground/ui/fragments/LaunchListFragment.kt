package com.example.finalplayground.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.finalplayground.R
import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.databinding.FragmentLaunchListBinding
import com.example.finalplayground.domain.model.Launch
import com.example.finalplayground.ui.adapters.ItemClickListener
import com.example.finalplayground.ui.adapters.LaunchListRecyclerViewAdapter
import com.example.finalplayground.ui.common.LaunchItem
import com.example.finalplayground.ui.common.showErrorBar
import com.example.finalplayground.ui.viewmodel.LaunchViewModel
import com.example.finalplayground.ui.viewmodel.Mode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchListFragment : Fragment(), ItemClickListener {
    private val viewModel: LaunchViewModel by hiltNavGraphViewModels(R.id.launchListFragment)
    private lateinit var binding: FragmentLaunchListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLaunchListBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = this@LaunchListFragment
            vm = this@LaunchListFragment.viewModel
            clickListener = this@LaunchListFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupDivider()
        observeLaunchList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    private fun setupDivider() {
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)
            ?.let { itemDecorator.setDrawable(it) }
        binding.launchList.addItemDecoration(itemDecorator)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val adapter = (binding.launchList.adapter as? LaunchListRecyclerViewAdapter)
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.refresh(Mode.REFRESH, listOf())
                true
            }
            R.id.action_launch_date -> {
                viewModel.refresh(Mode.SORT_LAUNCH_DATE, adapter?.data ?: listOf())
                true
            }
            R.id.action_mission_name -> {
                viewModel.refresh(Mode.SORT_MISSION_NAME, adapter?.data ?: listOf())
                true
            }
            R.id.action_filter_launch_success -> {
                viewModel.refresh(Mode.FILTER_LAUNCH_SUCCESS, adapter?.data ?: listOf())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeLaunchList() {
        viewModel.remoteLaunches.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.isLoading = true
                    binding.isEmpty = false
                }
                Resource.Status.SUCCESS -> handleSuccessResponse(data = it.data)
                Resource.Status.ERROR -> handleErrorResponse(msg = it.message)
            }
        }
    }

    private fun handleSuccessResponse(data: List<Launch>? = null) {
        binding.isLoading = false
        data?.run {
            binding.isEmpty = data.isEmpty()
            if (binding.launchList.adapter == null) {
                val adapter = LaunchListRecyclerViewAdapter(
                    data,
                    viewModel.mapOfLaunches(data),
                    this@LaunchListFragment
                )
                binding.launchList.adapter = adapter
            } else {
                (binding.launchList.adapter as LaunchListRecyclerViewAdapter).setItems(viewModel.mapOfLaunches(data), data)
            }
        }
    }

    private fun handleErrorResponse(msg: String? = null) {
        binding.isLoading = false
        binding.isEmpty = true
        showErrorBar(msg)
    }

    override fun onItemClick(item: Any?) {
        if (item is LaunchItem) {
            findNavController().navigate(LaunchListFragmentDirections.listFragmentAction(item.launch))
        }
    }
}
