package com.cube.remotechains.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.cube.remotechains.R
import com.cube.remotechains.data.model.Job
import com.cube.remotechains.data.model.JobToSave
import com.cube.remotechains.databinding.FragmentJobDetailBinding
import com.cube.remotechains.ui.main.view.MainActivity
import com.cube.remotechains.ui.main.viewmodel.RemoteJobViewModel
import com.google.android.material.snackbar.Snackbar

class JobDetailFragment : Fragment(R.layout.fragment_job_detail) {

    private var _binding: FragmentJobDetailBinding? = null
    private val binding get() = _binding!!
    private val args: JobDetailFragmentArgs by navArgs()
    private lateinit var currentJob: Job
    private lateinit var viewModel: RemoteJobViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        currentJob = args.job!!

        setupWebView()

        binding.favourite.setOnClickListener { JobtoFavourite(view) }
    }

    private fun JobtoFavourite(view: View) {
        val job = JobToSave(
            0,
            currentJob.candidateRequiredLocation, currentJob.category,
            currentJob.companyLogoUrl, currentJob.companyName,
            currentJob.description, currentJob.id, currentJob.jobType,
            currentJob.publicationDate, currentJob.salary,
            currentJob.title, currentJob.url
        )
        viewModel.insert(job)
        Snackbar.make(view,"Job saved",Snackbar.LENGTH_SHORT).show()
    }

    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let { loadUrl(it) }
        }

        binding.webView.settings.apply {
            javaScriptEnabled = true
            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            textZoom = 100
            blockNetworkImage = false
            loadsImagesAutomatically = true
        }
    }
        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
}