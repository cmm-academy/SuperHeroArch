package com.mstudio.superheroarch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val onItemClick: (Character) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    var characters: List<Character> = mutableListOf()

    inner class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name: TextView = view.findViewById(R.id.character_name)
        private val status: TextView = view.findViewById(R.id.character_status)
        private val image: ImageView = view.findViewById(R.id.character_image)

        private val _character = MutableStateFlow<Character?>(null)
        val character: StateFlow<Character?> get() = _character

        init {
            lifecycleOwner.lifecycleScope.launch {
                character.collect { character ->
                    character?.let {
                        name.text = it.name
                        status.text = it.status
                        Picasso.get().load(it.image).placeholder(R.drawable.placeholder).error(R.drawable.error).into(image)
                    }
                }
            }

            itemView.setOnClickListener {
                character.value?.let {
                    onItemClick(it)
                }
            }
        }

        fun bind(character: Character) {
            _character.value = character
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