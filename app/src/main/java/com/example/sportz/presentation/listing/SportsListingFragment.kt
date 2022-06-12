package com.example.sportz.presentation.listing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportz.R
import com.example.sportz.common.Resource
import com.example.sportz.databinding.FragmentSportsListingBinding
import com.example.sportz.domain.model.Sports
import com.example.sportz.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportsListingFragment : Fragment(R.layout.fragment_sports_listing) {

    private lateinit var sportListingFragmentBinding : FragmentSportsListingBinding
    private var sportsListingAdapter: RecyclerView.Adapter<SportsListingAdapter.ViewHolder>? = null
    private val sportsList = mutableListOf<Sports>()
    private val sportsViewModel by viewModels<SportsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sportListingFragmentBinding = FragmentSportsListingBinding.bind(view)

        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.screen_listing_title)
        sportsList.clear()
        sportsListingAdapter = SportsListingAdapter(sportsList) {
            val args = bundleOf("sportsId" to it)
            findNavController().navigate(
                R.id.action_sportsListingFragment_to_sportsDetailsFragment,
                args
            )
        }

        sportListingFragmentBinding.rvSportsListing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = sportsListingAdapter
        }
        sportsViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        setData(it)
                    } ?: run {
                        sportListingFragmentBinding.loading.visibility = View.GONE
                    }
                }
                is Resource.Error -> {
                    response.data?.let {
                       setData(it)
                    } ?: run {
                        sportListingFragmentBinding.loading.visibility = View.GONE
                    }
                }
                is Resource.Loading -> {
                    sportListingFragmentBinding.rvSportsListing.visibility = View.GONE
                    sportListingFragmentBinding.loading.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun setData(newList: List<Sports>){
        sportListingFragmentBinding.loading.visibility = View.GONE
        sportListingFragmentBinding.rvSportsListing.visibility = View.VISIBLE
        sportsList.clear()
        sportsList.addAll(newList)
        sportsListingAdapter?.notifyDataSetChanged()
    }
}