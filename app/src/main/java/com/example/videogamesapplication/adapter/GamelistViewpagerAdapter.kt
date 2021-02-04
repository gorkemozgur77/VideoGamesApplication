package com.example.videogamesapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.videogamesapplication.R
import com.example.videogamesapplication.model.Games
import com.example.videogamesapplication.view.GamelistFragmentDirections

class GamelistViewpagerAdapter( var contx: Context) : PagerAdapter() {
    var gamelist : List<Games> = listOf()
    override fun getCount(): Int {
        return gamelist.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        var view = LayoutInflater.from(contx).inflate(R.layout.game_list_viewpager_row,container,false)
        val img = view.findViewById<ImageView>(R.id.viewpagerImageId)
        val options = RequestOptions().placeholder(CircularProgressDrawable(contx).apply {
            start()
        }).error(R.mipmap.ic_launcher_round)
        Glide.with(contx).setDefaultRequestOptions(options).load(gamelist[position].gamePicture).centerCrop().into(img)
        container.addView(view)

        img.setOnClickListener {
            val action = GamelistFragmentDirections.actionGamelistFragmentToGamedetailsFragment(gamelist[position].gameid)
            Navigation.findNavController(it).navigate(action)
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun savefirstThreeGame(gamelist : List<Games>){
        this.gamelist = gamelist
        notifyDataSetChanged()
    }


}