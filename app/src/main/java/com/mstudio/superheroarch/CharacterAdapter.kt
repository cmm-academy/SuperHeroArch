package com.mstudio.superheroarch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mstudio.superheroarch.model.MainViewModel
import com.squareup.picasso.Picasso

class CharacterAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    var characters: List<Character> = mutableListOf()

    inner class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.character_name)
        private val status: TextView = view.findViewById(R.id.character_status)
        private val image: ImageView = view.findViewById(R.id.character_image)
        private var currentCharacter: Character? = null

        init {
            itemView.setOnClickListener {
                currentCharacter?.let {
                    viewModel.onCharacterClicked(it)
                }
            }
        }

        fun bind(character: Character) {
            currentCharacter = character
            name.text = character.name
            status.text = character.status
            Picasso.get().load(character.image).placeholder(R.drawable.placeholder).error(R.drawable.error).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.caracter_view_holder, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int = characters.size
}
