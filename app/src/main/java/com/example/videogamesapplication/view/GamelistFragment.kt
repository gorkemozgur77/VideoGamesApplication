package com.example.videogamesapplication.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.videogamesapplication.R
import com.example.videogamesapplication.model.responseModel.gamelistResponse
import com.example.videogamesapplication.adapter.GamelistRecyclerAdapter
import com.example.videogamesapplication.adapter.GamelistViewpagerAdapter
import com.example.videogamesapplication.service.Client
import kotlinx.android.synthetic.main.fragment_gamelist.*
import retrofit2.Call
import retrofit2.Response


class GamelistFragment : Fragment() {

    lateinit var gamelistRecyclerAdapter: GamelistRecyclerAdapter
    lateinit var gamelistViewpagerAdapter: GamelistViewpagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        getGamelist()

    }

    fun setupRecyclerView(context : Context){
        gamelistRecyclerAdapter = GamelistRecyclerAdapter()
        gamelistViewpagerAdapter = GamelistViewpagerAdapter(context)
        gamelist_recyclerview_id.layoutManager = LinearLayoutManager(context)
        gamelist_recyclerview_id.adapter = gamelistRecyclerAdapter
        viewpagerid.adapter = gamelistViewpagerAdapter
    }

    fun getGamelist(){
        gamelist_progressbar_id.visibility = View.VISIBLE
        gamelist_fragment_linearlayout_id.visibility = View.GONE
        Client().getApiService().getListofGames().enqueue(object : retrofit2.Callback<gamelistResponse>{
            override fun onResponse(call: Call<gamelistResponse>, response: Response<gamelistResponse>
            ) {
                println(response.body()?.results)
                if(response.isSuccessful){
                    response.body()?.let {
                        gamelistRecyclerAdapter.saveGamelist(it.results-it.results.take(3))
                        gamelistViewpagerAdapter.savefirstThreeGame(it.results.take(3))
                    }
                    gamelist_progressbar_id.visibility = View.GONE
                    gamelist_fragment_linearlayout_id.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<gamelistResponse>, t: Throwable) {
                println(t.cause)
                println(t.message)
                println(t.stackTrace)
            }
        })
    }


}