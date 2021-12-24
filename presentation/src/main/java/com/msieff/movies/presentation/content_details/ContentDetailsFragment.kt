package com.msieff.movies.presentation.content_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msieff.movies.domain.model.Content
import com.msieff.movies.domain.model.Video
import com.msieff.movies.domain.model.common.State
import com.msieff.movies.presentation.adapter.VideoAdapter
import com.msieff.movies.presentation.databinding.FragmentContentDetailsBinding
import com.msieff.movies.presentation.utils.openUrl
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.TestOnly

@AndroidEntryPoint
class ContentDetailsFragment : Fragment() {

    private val viewModel: ContentDetailsViewModel by viewModels()
    private lateinit var binding: FragmentContentDetailsBinding
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentContentDetailsBinding.inflate(inflater, container, false)

        arguments?.let {
            val content = it.getParcelable<Content>(ARG_CONTENT)!!
            viewModel.content = content
            binding.content = content
            setupActionBar(content.title ?: "")
        }

        binding.viewModel = viewModel
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        initViews()
        initObservers()
        updateData()

    }

    private fun setupActionBar(title: String) {
        ((activity as AppCompatActivity).supportActionBar)?.title = title
    }

    private fun initViews() {
        videoAdapter = VideoAdapter(requireActivity(), onClickOnVideo)
        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)

        binding.contentVideosRecycler.run {
            layoutManager = linearLayoutManager
            adapter = videoAdapter
        }
    }

    private fun initObservers() {
        viewModel.videoState.observe(viewLifecycleOwner, {
            if (it is State.Data)
                showData(it.data)
        })
    }

    private fun updateData() {
        viewModel.loadData()
    }

    private fun showData(data: List<Video>?) {
        data?.let {
            videoAdapter.submitList(it)
        } ?: videoAdapter.submitList(listOf())
    }

    private val onClickOnVideo: (video: Video) -> Unit = {
        openUrl(it.youtubeLink, requireContext())
    }

    @TestOnly
    internal fun setTestBindingViewModel(viewModel: ContentDetailsViewModel) {
        binding.viewModel = viewModel
    }

    companion object {
        const val ARG_CONTENT = "content"
    }
}