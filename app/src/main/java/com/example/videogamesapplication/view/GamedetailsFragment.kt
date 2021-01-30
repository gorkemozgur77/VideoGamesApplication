package com.example.videogamesapplication.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.videogamesapplication.R
import com.example.videogamesapplication.model.game
import com.example.videogamesapplication.service.Client
import kotlinx.android.synthetic.main.fragment_gamedetails.*
import kotlinx.android.synthetic.main.game_list_recycler_row.view.*
import retrofit2.Call
import retrofit2.Response


class GamedetailsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        arguments?.let {
            context?.let { it1 -> getDetails(GamedetailsFragmentArgs.fromBundle(it).gameID, it1) }
        }
    }

    fun getDetails(id:Int, context : Context){
        Client().getApiService().getGameDetails(id).enqueue(object : retrofit2.Callback<game>{
            override fun onResponse(call: Call<game>, response: Response<game>) {
                if(response.isSuccessful){
                    details1.text = response.body()?.name
                    details2.text = response.body()?.relaseDate
                    details3.text = response.body()?.metascore.toString()
                    details4.text = response.body()?.description
                    val options = RequestOptions().placeholder(CircularProgressDrawable(context).apply {
                        start()
                    }).error(R.mipmap.ic_launcher_round)
                    Glide.with(context).setDefaultRequestOptions(options).load(response.body()?.gamePicture).centerCrop().into(sada)
                }
            }

            override fun onFailure(call: Call<game>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}