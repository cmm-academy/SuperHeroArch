package com.mstudio.superheroarch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CharacterAdapter(private var characters: List<Character>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val status: TextView
        val image: ImageView

        init {
            name = view.findViewById(R.id.character_name)
            status = view.findViewById(R. id.character_status)
            image = view.findViewById(R.id.character_image)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.name.text = character.name
        holder.status.text = character.status
        Picasso.get().load(character.image).into(holder.image)
    }

    override fun getItemCount(): Int = characters.size

    fun updateCharacters(newCharacters: List<Character>){
        characters = newCharacters
        notifyItemRangeChanged(0, characters.size)
    }
}