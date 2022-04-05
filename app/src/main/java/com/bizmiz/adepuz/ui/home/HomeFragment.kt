package com.bizmiz.adepuz.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.adapter.NewsAdapter
import com.bizmiz.adepuz.adapter.PostsAdapter
import com.bizmiz.adepuz.databinding.FragmentHomeBinding
import com.bizmiz.adepuz.utils.ResourceState
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val getDataViewModel:GetDataViewModel by viewModel()
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))
        postsAdapter = PostsAdapter()
        newsAdapter = NewsAdapter()
        binding.homePostsRecyclerview.adapter = postsAdapter
        binding.homeNewsRecyclerview.adapter = newsAdapter
        getDataViewModel.getArticles()
        getDataViewModel.getNews()
        articlesObserve()
        newsObserve()
        return binding.root
    }
    private fun articlesObserve(){
        getDataViewModel.articles.observe(viewLifecycleOwner, Observer { articles->
            when (articles.status) {
                ResourceState.SUCCESS -> {
                   postsAdapter.postsList = articles.data?.body()!!
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), articles.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun newsObserve(){
        getDataViewModel.news.observe(viewLifecycleOwner, Observer { news->
            when (news.status) {
                ResourceState.SUCCESS -> {
                    newsAdapter.newsList = news.data?.body()!!
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), news.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}