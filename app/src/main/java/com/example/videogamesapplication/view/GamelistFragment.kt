package com.example.videogamesapplication.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videogamesapplication.R
import com.example.videogamesapplication.adapter.GamelistRecyclerAdapter
import com.example.videogamesapplication.adapter.GamelistViewpagerAdapter
import com.example.videogamesapplication.firebase.FirebaseService
import com.example.videogamesapplication.viewmodel.GamelistViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_gamelist.*


class GamelistFragment : Fragment(), SearchView.OnQueryTextListener {

    lateinit var gamelistRecyclerAdapter: GamelistRecyclerAdapter
    lateinit var gamelistViewpagerAdapter: GamelistViewpagerAdapter
    private lateinit var viewModel : GamelistViewModel
    private var firebaseService = FirebaseService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        firebaseService.logEvent(FirebaseService.EventType.HOME_PAGE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gamelist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { setupRecyclerView(it) }
        viewModel = ViewModelProviders.of(this).get(GamelistViewModel::class.java)
        viewModel.getData()
        tablayId.setupWithViewPager(viewpagerid)
        observeLiveData()
        searchViewId.setOnQueryTextListener(this)
    }

     private fun observeLiveData()
     {
         gamelist_progressbar_id.visibility = View.VISIBLE
         gamelist_fragment_linearlayout_id.visibility = View.GONE
        viewModel.games.observe(viewLifecycleOwner,
                {
                    gamelistRecyclerAdapter.saveGamelist(it - it.take(3))
                    gamelistViewpagerAdapter.savefirstThreeGame(it.take(3))
                    gamelist_progressbar_id.visibility = View.GONE
                    gamelist_fragment_linearlayout_id.visibility = View.VISIBLE
        })
    }
    fun setupRecyclerView(context : Context)
    {
        gamelistRecyclerAdapter = GamelistRecyclerAdapter()
        gamelistViewpagerAdapter = GamelistViewpagerAdapter(context)
        gamelist_recyclerview_id.layoutManager = LinearLayoutManager(context)
        gamelist_recyclerview_id.adapter = gamelistRecyclerAdapter
        viewpagerid.adapter = gamelistViewpagerAdapter
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null)
        {
            if (query.length > 2 || query.isEmpty()) {
                searchDatabase(query)
            }
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {

        if (query != null)
        {
            if (query.length > 2 || query.isEmpty())
            {
                searchDatabase(query)
            }
        }
        return true
    }

    private fun searchDatabase(query: String){
        val searchquery = "%$query%"

        viewModel.searchData(searchquery)
        viewModel.searchedGames.observe(viewLifecycleOwner,{
            searchErrorTextView.visibility = View.GONE
            if (query.isNotEmpty())
            {
                cardviewId.visibility = View.GONE
                tablayId.visibility = View.GONE
                gamelistRecyclerAdapter.saveGamelist(it)
                if (it.isEmpty())
                    searchErrorTextView.visibility = View.VISIBLE
            }
            else
            {
                cardviewId.visibility = View.VISIBLE
                tablayId.visibility = View.VISIBLE
                gamelistRecyclerAdapter.saveGamelist(it - it.take(3))
            }


        })

    }

}