package com.mstudio.superheroarch.presentation.overview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mstudio.superheroarch.databinding.ItemCharacterLayoutBinding
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity

class CharactersAdapter(
    val listener: (CharactersRemoteEntity) -> Unit
) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    private var items = listOf<CharactersRemoteEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(dataList: List<CharactersRemoteEntity>) {
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

    inner class CharacterViewHolder(private val binding: ItemCharacterLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharactersRemoteEntity) {
            binding.nameCharacter.text = character.name
            binding.statusCharacter.text = character.status
            binding.imageCharacter.load(character.image)
            binding.root.setOnClickListener {
                listener.invoke(character)
            }
        }
    }
}