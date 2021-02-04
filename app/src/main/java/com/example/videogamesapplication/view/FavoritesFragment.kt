package com.example.videogamesapplication.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videogamesapplication.R
import com.example.videogamesapplication.adapter.FavoriteListRecyclerAdapter
import com.example.videogamesapplication.firebase.FirebaseService
import com.example.videogamesapplication.viewmodel.FavoritesViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_gamelist.*

class FavoritesFragment : Fragment() {

    private lateinit var viewModel : FavoritesViewModel
    private lateinit var recyclerAdapter: FavoriteListRecyclerAdapter
    private var firebaseService = FirebaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        firebaseService.logEvent(FirebaseService.EventType.FAVORITE_PAGE)

    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { setupRecyclerView(it) }
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        viewModel.getGames()
        observeLiveData()
    }

    fun setupRecyclerView(context : Context)
    {
        recyclerAdapter = FavoriteListRecyclerAdapter()
        favoriteListRecyclerViewId.layoutManager = LinearLayoutManager(context)
        favoriteListRecyclerViewId.adapter = recyclerAdapter
    }

    fun observeLiveData()
    {
        viewModel.gameList.observe(viewLifecycleOwner){
            recyclerAdapter.saveGamelist(it)
            if (it.isEmpty())
                nofavoriteTextView.visibility = View.VISIBLE
            else
                nofavoriteTextView.visibility = View.GONE
        }
    }


}