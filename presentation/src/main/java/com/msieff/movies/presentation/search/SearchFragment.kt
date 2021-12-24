package com.msieff.movies.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.msieff.movies.domain.model.Content
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.presentation.R
import com.msieff.movies.presentation.adapter.ContentAdapter
import com.msieff.movies.presentation.adapter.SearchAdapter
import com.msieff.movies.presentation.databinding.FragmentSearchBinding
import com.msieff.movies.presentation.model.getContentTypeById
import com.msieff.movies.presentation.utils.hideKeyboard
import com.msieff.movies.presentation.utils.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        initViews()
        initObservers()
        showKeyboard()

        getContentTypeById(arguments?.getInt("contentType") ?: -1)?.let {
            viewModel.contentType = it
        } ?: throw RuntimeException("Invalid content type")
    }

    private fun initViews() {
        searchAdapter = SearchAdapter(requireActivity(), listOf(), onClickOnContent)

        val gridLayoutManager =
            GridLayoutManager(requireActivity(), 3, RecyclerView.VERTICAL, false)

        binding.list.run {
            layoutManager = gridLayoutManager
            adapter = searchAdapter
        }

        binding.searchInput.addTextChangedListener {
            viewModel.search()
        }
    }

    private fun initObservers() {
        viewModel.contentsState.observe(viewLifecycleOwner, {
            when (it) {
                is State.Data -> showData(it.data)
                is State.Error -> showError()
                else -> {
                }
            }
        })
    }

    private fun showError() {
        showEmptyStateIfIsNecessary()
        Snackbar.make(binding.root,
            getString(R.string.discover_generic_error),
            Snackbar.LENGTH_SHORT).show()
        hideKeyboard()
    }

    private fun showData(data: List<Content>?) {
        data?.let {
            searchAdapter.submitList(it)
        } ?: searchAdapter.submitList(listOf())

        showEmptyStateIfIsNecessary()
    }

    private fun showEmptyStateIfIsNecessary() {
        if (searchAdapter.itemCount == 0) {
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
        hideKeyboard()

        val extras = Bundle().apply {
            putParcelable("content", content)
        }

        findNavController().navigate(R.id.navigateToContentDetails, extras)
    }
}