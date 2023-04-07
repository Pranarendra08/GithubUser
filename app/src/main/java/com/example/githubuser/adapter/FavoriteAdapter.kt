package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.api.response.ItemsFavorite
import com.example.githubuser.api.response.ItemsItem

class FavoriteAdapter(private val listFavorite: List<ItemsFavorite>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tv_nama_user)
        val ivFoto: ImageView = view.findViewById(R.id.iv_foto_user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNama.text = listFavorite[position].login
        Glide.with(holder.itemView.context)
            .load(listFavorite[position].avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.ivFoto)
    }

    override fun getItemCount() = listFavorite.size

}
