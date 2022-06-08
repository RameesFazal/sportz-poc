package com.example.sportz.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.sportz.R
import com.example.sportz.common.Resource
import com.example.sportz.domain.model.SportsDetails
import com.example.sportz.presentation.MainActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "sportsId"

@AndroidEntryPoint
class SportsDetailsFragment : Fragment() {
    private var sportsId: Int? = null
    private lateinit var imSports: ImageView
    private lateinit var tvSportsDescription: TextView
    private lateinit var loading: ProgressBar
    private lateinit var tvErrorNoData: TextView
    private val sportsDetailsViewModel by viewModels<SportsDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sportsId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sports_details, container, false)

        imSports = view.findViewById(R.id.im_sports)
        tvSportsDescription = view.findViewById(R.id.tv_sports_description)
        loading = view.findViewById(R.id.loading)
        tvErrorNoData = view.findViewById(R.id.tv_error_message)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sportsId?.let {
            loading.visibility = View.VISIBLE
            sportsDetailsViewModel.response.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { it ->
                            setData(it)
                        } ?: run {
                            tvErrorNoData.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Error -> {
                        response.data?.let {
                            tvErrorNoData.visibility = View.GONE
                            loading.visibility = View.GONE
                        } ?: run {
                            loading.visibility = View.GONE
                            tvErrorNoData.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Loading -> {
                        loading.visibility = View.VISIBLE
                        imSports.visibility = View.GONE
                        tvErrorNoData.visibility = View.GONE
                        tvSportsDescription.visibility = View.GONE
                    }
                }
            }
        }?: run {
            tvErrorNoData.visibility = View.VISIBLE
        }
    }

    private fun setData(sportsDetail : SportsDetails){
        tvErrorNoData.visibility = View.GONE
        loading.visibility = View.GONE
        imSports.visibility = View.VISIBLE
        tvSportsDescription.visibility = View.VISIBLE
        Picasso.get().load(sportsDetail.image).placeholder(R.mipmap.ic_launcher).into(imSports)
        tvSportsDescription.text = sportsDetail.description
        (activity as MainActivity).supportActionBar?.title = sportsDetail.name
    }
}