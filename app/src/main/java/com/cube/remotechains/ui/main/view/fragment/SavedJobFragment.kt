package com.cube.remotechains.ui.main.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cube.remotechains.R
import com.cube.remotechains.data.model.JobToSave
import com.cube.remotechains.databinding.FragmentSavedJobBinding
import com.cube.remotechains.ui.main.adapters.RemoteJobSavedAdapter
import com.cube.remotechains.ui.main.view.MainActivity
import com.cube.remotechains.ui.main.viewmodel.RemoteJobViewModel

class SavedJobFragment : Fragment(R.layout.fragment_saved_job),RemoteJobSavedAdapter.OnItemClickListener {

    private var _binding: FragmentSavedJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var savedViewModel: RemoteJobViewModel
    private lateinit var savedJobAdapter: RemoteJobSavedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedJobBinding.inflate(
            inflater,container,false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedViewModel = (activity as MainActivity).viewModel
        setRecyclerView()
    }

    private fun setRecyclerView() {
        savedJobAdapter = RemoteJobSavedAdapter(this)
        binding.rvJobsSaved.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(
                activity,LinearLayout.VERTICAL
            ){})
            adapter = savedJobAdapter
        }
        savedViewModel.getAllJob().observe(viewLifecycleOwner,{
            jobToSave -> savedJobAdapter.differ.submitList(jobToSave)
            updateUI(jobToSave)
        })
    }

    private fun updateUI(list: List<JobToSave>?) {
        if(list!!.isNotEmpty()){
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardAvail.visibility = View.VISIBLE
        }

    }

    override fun onItemClick(job: JobToSave, view: View, position: Int) {
        deleteJob(job)
    }

    private fun deleteJob(job: JobToSave) {
        AlertDialog.Builder(activity).apply{
            setTitle("Delete Job")
            setMessage("Do you want to permanently delete this job?")
            setPositiveButton("Delete") {
                _,_ -> savedViewModel.deleteJob(job)
                Toast.makeText(activity,"Job Deleted",Toast.LENGTH_LONG).show()
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}