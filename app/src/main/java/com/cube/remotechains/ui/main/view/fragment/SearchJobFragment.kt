package com.cube.remotechains.ui.main.view.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cube.remotechains.R
import com.cube.remotechains.databinding.FragmentSearchJobBinding
import com.cube.remotechains.ui.main.adapters.RemoteJobAdapter
import com.cube.remotechains.ui.main.view.MainActivity
import com.cube.remotechains.ui.main.viewmodel.RemoteJobViewModel
import com.cube.remotechains.utils.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchJobFragment : Fragment(R.layout.fragment_search_job) {

    private var _binding : FragmentSearchJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchViewModel: RemoteJobViewModel
    private lateinit var searchJobAdapter: RemoteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchJobBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel = (activity as MainActivity).viewModel
        if(Constants.isNetworkAvailable(requireContext())){
            searchJob()
        }
        else{
            Toast.makeText(activity,"No internet connection",Toast.LENGTH_LONG).show()
        }
    }

    private fun searchJob() {
        var job: Job? = null
        binding.search.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        searchViewModel.searchJob(editable.toString())
                    }
                }
            }
        }
        setRecyclerView()
    }

    private fun setRecyclerView() {
        searchJobAdapter = RemoteJobAdapter()
        binding.search.apply {
//            layoutMan = LinearLayoutManager(activity)
//            setHasFixedSize(true)
 //             adapter = searchJobAdapter
        }
        searchViewModel.searchResult().observe(viewLifecycleOwner,{ remoteJob ->
            searchJobAdapter.differ.submitList(remoteJob.jobs)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}