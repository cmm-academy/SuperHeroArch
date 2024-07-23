package com.mstudio.superheroarch.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mstudio.superheroarch.databinding.ItemCharacterLayoutBinding
import com.mstudio.superheroarch.model.CharacterModel

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    private var items = listOf<CharacterModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(dataList: List<CharacterModel>) {
        items = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class CharacterViewHolder(private val binding: ItemCharacterLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterModel) {
            binding.nameCharacter.text = character.name
            binding.statusCharacter.text = character.status
            binding.imageCharacter.load(character.image)
        }
    }
}