package com.example.videogamesapplication.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.videogamesapplication.R
import com.example.videogamesapplication.firebase.FirebaseService
import com.example.videogamesapplication.viewmodel.GamedetailsViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_gamedetails.*


class GamedetailsFragment : Fragment() {
    private lateinit var viewModel : GamedetailsViewModel
    private var firebaseService = FirebaseService()

    override fun onStart() {
        super.onStart()
        firebaseService.logEvent(FirebaseService.EventType.DETAIL_PAGE)

    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gamedetails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).bottomNav.visibility = View.GONE
        var gameid = 0

        viewModel = ViewModelProviders.of(this).get(GamedetailsViewModel::class.java)

        arguments?.let {
           gameid = GamedetailsFragmentArgs.fromBundle(it).gameID
        }
        viewModel.getDetails(gameid)
        viewModel.isFavorited(gameid)
        observeLiveData()

        likeCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.addFav(gameid)
                firebaseService.logButton(FirebaseService.ButtonType.ADDING_FAVOURITE, likeCheckbox.id)

            }
            else {
            viewModel.deleteFav(gameid)
            firebaseService.logButton(FirebaseService.ButtonType.REMOVE_FAVOURITE, likeCheckbox.id)


            }
        }
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).bottomNav.visibility = View.VISIBLE

    }

    @SuppressLint("SetTextI18n")
    private fun observeLiveData()
    {
        viewModel.detailedGame.observe(viewLifecycleOwner)
        {
            details1.text = it.gamename
            details2.text = it.gamerelaseDate
            details3.text = "Metacritic Score: "+it.metacritic.toString()
            details4.text = Html.fromHtml(it.description,Html.FROM_HTML_MODE_LEGACY)
            context?.let { it1 -> Glide.with(it1).load(it.gamePicture).centerCrop().into(detailPageImageView) }
        }
        viewModel.progressBarVisibility.observe(viewLifecycleOwner){
            if (it)
            {
                detailPageLayout.visibility = View.GONE
                detailPageProgressBar.visibility = View.VISIBLE
            }
            else
            {
                detailPageLayout.visibility = View.VISIBLE
                detailPageProgressBar.visibility = View.GONE
            }
        }
        viewModel.isFavorited.observe(viewLifecycleOwner){
            if (it==1){
                likeCheckbox.isChecked = true
            }
            

        }
    }
}