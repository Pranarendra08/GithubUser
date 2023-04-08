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
import com.example.githubuser.database.Favorite

class FavoriteAdapter(private val listFavorite: List<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tv_nama_user)
        val ivFoto: ImageView = view.findViewById(R.id.iv_foto_user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNama.text = listFavorite[position].username
        Glide.with(holder.itemView.context)
            .load(listFavorite[position].avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.ivFoto)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFavorite[holder.adapterPosition])
        }
    }

    override fun getItemCount() = listFavorite.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorite)
    }
}
