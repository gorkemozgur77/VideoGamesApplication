package com.example.videogamesapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.videogamesapplication.R
import com.example.videogamesapplication.model.game
import com.example.videogamesapplication.view.GamelistFragmentDirections
import kotlinx.android.synthetic.main.game_list_recycler_row.view.*

class GamelistRecyclerAdapter : RecyclerView.Adapter<GamelistRecyclerAdapter.GamelistRecyclerViewHolder>(){
    class GamelistRecyclerViewHolder(Itemview : View) : RecyclerView.ViewHolder(Itemview)

    var gamelist : List<game> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamelistRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.game_list_recycler_row,parent,false)
        return GamelistRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: GamelistRecyclerViewHolder, position: Int) {
        holder.itemView.game_name_textview_id.text = gamelist[position].name
        holder.itemView.game_rating_textview_id.text = gamelist[position].rating.toString()
        holder.itemView.game_relasedate_textview_id.text = gamelist[position].relaseDate

        val options = RequestOptions().placeholder(CircularProgressDrawable(holder.itemView.context).apply {
            start()
        }).error(R.mipmap.ic_launcher_round)
        Glide.with(holder.itemView.context).setDefaultRequestOptions(options).load(gamelist[position].gamePicture).centerCrop().into(holder.itemView.game_image_id)
        holder.itemView.setOnClickListener {
            val action = GamelistFragmentDirections.actionGamelistFragmentToGamedetailsFragment(gamelist[position].id)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return gamelist.size
    }

    fun saveGamelist(gamelist: List<game>){
        this.gamelist = gamelist
        notifyDataSetChanged()
    }
}