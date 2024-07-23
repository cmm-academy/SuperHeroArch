package com.mstudio.superheroarch.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mstudio.superheroarch.databinding.ItemCharacterLayoutBinding
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    private var items = listOf<CharactersRemoteEntity>()

    fun updateItems(dataList: List<CharactersRemoteEntity>) {
        items = dataList
        notifyItemRangeChanged(0, items.size)
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
        fun bind(character: CharactersRemoteEntity) {
            binding.nameCharacter.text = character.name
            binding.statusCharacter.text = character.status
            binding.imageCharacter.load(character.image)
        }
    }
}