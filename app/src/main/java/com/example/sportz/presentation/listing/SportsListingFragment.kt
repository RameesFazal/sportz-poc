package com.example.sportz.presentation.listing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportz.R
import com.example.sportz.common.Resource
import com.example.sportz.domain.model.Sports
import com.example.sportz.presentation.SportsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportsListingFragment : Fragment() {

    private var sportsListingAdapter: RecyclerView.Adapter<SportsListingAdapter.ViewHolder>? = null
    private lateinit var rvSportsListing: RecyclerView
    private val sportsList = mutableListOf<Sports>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sports_listing, container, false)
        rvSportsListing = view.findViewById(R.id.rv_sports_listing)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainViewModel by viewModels<SportsViewModel>()
        mainViewModel.fetchSportsList()
        sportsListingAdapter = SportsListingAdapter(sportsList)
        rvSportsListing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = sportsListingAdapter
        }

        mainViewModel.response.observe(viewLifecycleOwner) { response ->
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
                is Resource.Loading -> {
                }
            }
        }
    }
}