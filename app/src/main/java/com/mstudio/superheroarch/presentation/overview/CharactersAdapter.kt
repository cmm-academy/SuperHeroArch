package com.mstudio.superheroarch.presentation.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mstudio.superheroarch.databinding.ItemCharacterLayoutBinding
import com.mstudio.superheroarch.presentation.model.CharacterData

class CharactersAdapter(
    val listener: (CharacterData) -> Unit
) : ListAdapter<CharacterData, CharactersAdapter.CharacterViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterData) {
            binding.nameCharacter.text = character.name
            binding.statusCharacter.text = character.status
            binding.imageCharacter.load(character.image)
            binding.root.setOnClickListener {
                listener.invoke(character)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<CharacterData>() {
    override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
        return oldItem == newItem
    }
}