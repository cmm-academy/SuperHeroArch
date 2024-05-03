package com.mstudio.superheroarch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CharactersAdapter: RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>(){

    private var characters = listOf<Character>()

    fun setCharacters(characters: List<Character>){
        this.characters = characters
        notifyItemRangeInserted(0, characters.size)
    }

    override fun getItemCount(): Int {
        return characters.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_row, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        private val characterImageView: ImageView = itemView.findViewById(R.id.characterImageView)

        fun bind(character: Character){
            nameTextView.text = character.name
            statusTextView.text = character.status
            Glide.with(itemView.context)
                .load(character.image)
                .into(characterImageView)
        }
    }
}