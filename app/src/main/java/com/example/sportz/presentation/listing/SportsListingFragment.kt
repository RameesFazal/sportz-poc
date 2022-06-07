package com.example.sportz.presentation.listing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportz.R
import com.example.sportz.common.Resource
import com.example.sportz.domain.model.Sports
import com.example.sportz.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportsListingFragment : Fragment() {

    private var sportsListingAdapter: RecyclerView.Adapter<SportsListingAdapter.ViewHolder>? = null
    private lateinit var rvSportsListing: RecyclerView
    private val sportsList = mutableListOf<Sports>()
    private val sportsViewModel by viewModels<SportsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.screen_listing_title)
        val view = inflater.inflate(R.layout.fragment_sports_listing, container, false)
        rvSportsListing = view.findViewById(R.id.rv_sports_listing)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sportsList.clear()
        sportsListingAdapter = SportsListingAdapter(sportsList) {
            val args = bundleOf("sportsId" to it)
            findNavController().navigate(
                R.id.action_sportsListingFragment_to_sportsDetailsFragment,
                args
            )
        }

        rvSportsListing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = sportsListingAdapter
        }

        sportsViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        sportsList.clear()
                        sportsList.addAll(it)
                        sportsListingAdapter?.notifyItemRangeInserted(0, sportsList.size - 1)
                    }
                }
                is Resource.Error -> {
                    response.data?.let {
                        sportsList.clear()
                        sportsList.addAll(it)
                        sportsListingAdapter?.notifyItemRangeInserted(0, sportsList.size - 1)
                    }
                }
            }
        }
    }
}