package com.example.sportz.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.sportz.R
import com.example.sportz.common.Resource
import com.example.sportz.databinding.FragmentSportsDetailsBinding
import com.example.sportz.domain.model.SportsDetails
import com.example.sportz.presentation.MainActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "sportsId"

@AndroidEntryPoint
class SportsDetailsFragment : Fragment(R.layout.fragment_sports_details) {
    private var sportsId: Int? = null
    private val sportsDetailsViewModel by viewModels<SportsDetailsViewModel>()
    private lateinit var sportsDetailsFragmentBinding: FragmentSportsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sportsId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sportsDetailsFragmentBinding = FragmentSportsDetailsBinding.bind(view)
        sportsId?.let {
            sportsDetailsFragmentBinding.loading.visibility = View.VISIBLE
            sportsDetailsViewModel.response.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { it ->
                            setData(it)
                        } ?: run {
                            sportsDetailsFragmentBinding.loading.visibility = View.GONE
                            sportsDetailsFragmentBinding.tvErrorMessage.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Error -> {
                        response.data?.let {
                            sportsDetailsFragmentBinding.tvErrorMessage.visibility = View.GONE
                            sportsDetailsFragmentBinding.loading.visibility = View.GONE
                        } ?: run {
                            sportsDetailsFragmentBinding.loading.visibility = View.GONE
                            sportsDetailsFragmentBinding.tvErrorMessage.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Loading -> {
                        sportsDetailsFragmentBinding.loading.visibility = View.VISIBLE
                        sportsDetailsFragmentBinding.imSports.visibility = View.GONE
                        sportsDetailsFragmentBinding.tvErrorMessage.visibility = View.GONE
                        sportsDetailsFragmentBinding.tvSportsDescription.visibility = View.GONE
                    }
                }
            }
        }?: run {
            sportsDetailsFragmentBinding.tvErrorMessage.visibility = View.VISIBLE
        }
    }

    private fun setData(sportsDetail : SportsDetails){
        sportsDetailsFragmentBinding.tvErrorMessage.visibility = View.GONE
        sportsDetailsFragmentBinding.loading.visibility = View.GONE
        sportsDetailsFragmentBinding.imSports.visibility = View.VISIBLE
        sportsDetailsFragmentBinding.tvSportsDescription.visibility = View.VISIBLE
        Picasso.get().load(sportsDetail.image).placeholder(R.mipmap.ic_launcher).into(sportsDetailsFragmentBinding.imSports)
        sportsDetailsFragmentBinding.tvSportsDescription.text = sportsDetail.description
        (activity as MainActivity).supportActionBar?.title = sportsDetail.name
    }
}