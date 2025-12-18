package com.mstudio.superheroarch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CharacterAdapter(

    var characters: List<Character>
): RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterTextView: TextView = itemView.findViewById(R.id.characterNameTextView)
        val characterStatusTextView: TextView = itemView.findViewById(R.id.characterStatusTextView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CharacterViewHolder,
        position: Int
    ) {
        val character = characters[position]
        holder.characterTextView.text = character.name
        holder.characterStatusTextView.text = character.status
    }

    override fun getItemCount(): Int {
        return characters.size
    }
}
