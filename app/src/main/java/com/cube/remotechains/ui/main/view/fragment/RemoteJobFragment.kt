package com.cube.remotechains.ui.main.view.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cube.remotechains.R
import com.cube.remotechains.databinding.FragmentRemoteJobBinding
import com.cube.remotechains.ui.main.adapters.RemoteJobAdapter
import com.cube.remotechains.ui.main.view.MainActivity
import com.cube.remotechains.ui.main.viewmodel.RemoteJobViewModel
import com.cube.remotechains.utils.Constants

class RemoteJobFragment : Fragment(R.layout.fragment_remote_job),
    SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var remoteViewModel: RemoteJobViewModel
    private lateinit var remoteJobAdapter: RemoteJobAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var page = 1;
    private var limit = 10

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRemoteJobBinding.inflate(inflater, container, false)
        swipeRefreshLayout = binding.container
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeColors(
            Color.BLUE, Color.MAGENTA, Color.YELLOW, Color.GREEN
        )
        swipeRefreshLayout.post {
            swipeRefreshLayout.isRefreshing = true
            setRecyclerview()
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setRecyclerview() {
        remoteJobAdapter = RemoteJobAdapter()
        binding.rvRemoteJob.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object :
                DividerItemDecoration(activity, LinearLayout.VERTICAL) {})
                adapter = remoteJobAdapter
        }
        fetchData()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fetchData() {
        activity?.let {
            if(Constants.isNetworkAvailable((requireActivity()))) {
                remoteViewModel.remoteJobResult()
                    .observe(viewLifecycleOwner,{remoteJob ->
                        if(remoteJob != null) {
                            remoteJobAdapter.differ.submitList(remoteJob.jobs)
                            swipeRefreshLayout.isRefreshing  = false
                        }
                    })
            } else {
                Toast.makeText(activity,"No internet connection",Toast.LENGTH_LONG).show()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        remoteViewModel = (activity as MainActivity).viewModel
        setRecyclerview()
        binding.container.setOnRefreshListener { fetchData() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRefresh() {
        setRecyclerview()
    }
}