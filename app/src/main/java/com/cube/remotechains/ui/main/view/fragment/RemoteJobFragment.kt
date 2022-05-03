package com.cube.remotechains.ui.main.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cube.remotechains.ui.main.adapters.RemoteJobAdapter
import com.cube.remotechains.databinding.FragmentRemoteJobBinding
import com.cube.remotechains.ui.main.viewmodel.RemoteJobViewModel

class RemoteJobFragment : Fragment() {

    private var _binding: FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var remoteJobAdapter: RemoteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRemoteJobBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}