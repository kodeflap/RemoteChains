package com.cube.remotechains.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cube.remotechains.R
import com.cube.remotechains.databinding.FragmentMainBinding
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabBar();
    }

    private fun setUpTabBar() {
        val adapter = FragmentPagerItemAdapter(
            childFragmentManager,
            FragmentPagerItems.with(activity)
                .add("Jobs",RemoteJobFragment::class.java)
                .add("Search", SavedJobFragment::class.java)
                .add("Jobs",SavedJobFragment::class.java)
                .create()
        )

        binding.viewPager.adapter = adapter
        binding.viewPagerTab.setViewPager(binding.viewPager)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}