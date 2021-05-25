package com.karim.task.presentation.ui.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.karim.task.R
import com.karim.task.data.api.entity.Status
import com.karim.task.data.entity.JobsItem
import com.karim.task.databinding.FragmentPostsListBinding
import com.karim.task.presentation.adapter.JobsAdapter
import com.karim.task.presentation.base.BaseSimpleBindingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JobsListFragment : Fragment(), BaseSimpleBindingAdapter.OnItemClickListener<JobsItem> {

    lateinit var list: ArrayList<String>
    lateinit var searchAdapter: ArrayAdapter<String>

    private val viewModel by viewModels<JobsModel>()
    private lateinit var binding: FragmentPostsListBinding
    private val adapter by lazy {
        JobsAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentPostsListBinding>(
            inflater,
            R.layout.fragment_posts_list,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = ArrayList()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                if (list.contains(query)) {
                    searchAdapter.filter.filter(query)
                } else {
                    Toast.makeText(requireContext(), "No Match found", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchAdapter.filter.filter(newText)
                if (newText.isEmpty()) {
                    binding.listView.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                } else {
                    binding.listView.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
                return false
            }
        })

        binding.recyclerView.adapter = adapter
        observeDataChanges()
    }

    private fun observeDataChanges() {

        viewModel.jobsData.observe(viewLifecycleOwner, { result ->
            when (result.status.get()) {
                Status.SUCCESS -> result.data?.let {
                    adapter.submitList(it as List<JobsItem>)
                    for (i in it) {
                        list.add(i.company)
                    }
                    searchAdapter = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        list
                    )
                    binding.listView.adapter = searchAdapter

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), result.msg, Toast.LENGTH_SHORT)
                        .show()
                }
                Status.LOADING -> {
                }
                else -> {
                    Toast.makeText(requireContext(), "else", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onItemClicked(item: JobsItem) {
        findNavController().navigate(
            JobsListFragmentDirections.actionJobsListFragmentToJobsDetailsFragment(
                item
            )
        )

    }

    override fun onSaveItemClicked(item: JobsItem) {
        GlobalScope.launch(Dispatchers.IO) {
            item.favorite = item.favorite != true
            viewModel.updateFavorite(item)
        }
    }

}