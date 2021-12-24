package com.msieff.movies.presentation.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.msieff.movies.domain.model.Category
import com.msieff.movies.domain.model.Content
import com.msieff.movies.domain.model.ContentCategory
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.presentation.R
import com.msieff.movies.presentation.adapter.DiscoverContentAdapter
import com.msieff.movies.presentation.databinding.FragmentDiscoverContentBinding
import com.msieff.movies.presentation.model.ContentPlaceholder
import com.msieff.movies.presentation.model.getContentTypeById
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverContentFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverContentBinding
    private val viewModel: DiscoverContentViewModel by viewModels()
    private lateinit var discoverAdapter: DiscoverContentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDiscoverContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()

        getContentTypeById(arguments?.getInt("contentType") ?: -1)?.let {
            viewModel.contentType = it
        } ?: throw RuntimeException("Invalid content type")

        updateData()
    }

    private fun initViews() {
        discoverAdapter = DiscoverContentAdapter(requireActivity(), onClickOnContent)
        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        binding.list.run {
            layoutManager = linearLayoutManager
            adapter = discoverAdapter
        }
    }

    private fun initObservers() {
        viewModel.categoriesState.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> showLoading()
                is State.Data -> showData(it.data)
                is State.Error -> showError()
            }
        })
    }

    private fun updateData() {
        viewModel.loadData()
    }

    private fun showData(data: List<ContentCategory>?) {
        data?.let {
            discoverAdapter.submitList(it)
        } ?: discoverAdapter.submitList(listOf())

        showEmptyStateIfIsNecessary()
    }

    private fun showError() {
        hideLoading()
        showEmptyStateIfIsNecessary()
        Snackbar.make(
            binding.root,
            getString(R.string.discover_generic_error),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(getString(
                R.string.action_retry)) { updateData() }
            .show()
    }

    private fun showLoading() {
        val popular = ContentCategory(
            Category.POPULAR,
            generateContentPlaceholders(4)
        )
        val topRated = ContentCategory(
            Category.TOP_RATED,
            generateContentPlaceholders(4)
        )

        discoverAdapter.submitList(listOf(popular, topRated))
    }

    private fun hideLoading() {
        discoverAdapter.submitList(listOf())
    }

    private fun generateContentPlaceholders(count: Int): List<ContentPlaceholder> {
        val placeholders = mutableListOf<ContentPlaceholder>()
        for (i in 0..count)
            placeholders.add(ContentPlaceholder())
        return placeholders
    }

    private fun showEmptyStateIfIsNecessary() {
        if (discoverAdapter.itemCount == 0) {
            binding.emptyMessage.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        } else {
            binding.emptyMessage.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
        }
    }

    private val onClickOnContent: (content: Content) -> Unit = {
        navigateToContentDetails(it)
    }

    private fun navigateToContentDetails(content: Content) {
        val extras = Bundle().apply {
            putParcelable("content", content)
        }

        findNavController().navigate(R.id.navigateToContentDetails, extras)
    }

}
