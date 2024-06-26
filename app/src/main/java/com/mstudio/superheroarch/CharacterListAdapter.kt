package com.mstudio.superheroarch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mstudio.superheroarch.data.Character
import com.squareup.picasso.Picasso

class CharacterListAdapter : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    private val characterList: MutableList<Character> = mutableListOf()

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterNameTextView: TextView = itemView.findViewById(R.id.character_name)
        val characterStatusTextView: TextView = itemView.findViewById(R.id.character_status)
        val characterImageView: ImageView = itemView.findViewById(R.id.character_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_details, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characterList[position]
        holder.characterNameTextView.text = character.name
        holder.characterStatusTextView.text = character.status
        Picasso.get().load(character.image).into(holder.characterImageView);
    }

    fun updateData(newItems: List<Character>) {
        characterList.clear()
        characterList.addAll(newItems)
        notifyDataSetChanged()
    }
}