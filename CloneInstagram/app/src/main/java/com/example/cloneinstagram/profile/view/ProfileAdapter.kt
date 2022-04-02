package com.example.cloneinstagram.profile.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cloneinstagram.R
import com.example.cloneinstagram.common.model.Post
import kotlinx.android.synthetic.main.item_profile_grid.view.*

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.PostViewHolder>() {
    var items: List<Post> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_profile_grid, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position].uri)
    }

    override fun getItemCount(): Int {
        return 30
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: Uri){
            itemView.item_profile_image_grid.setImageURI(image)

        }
    }

}