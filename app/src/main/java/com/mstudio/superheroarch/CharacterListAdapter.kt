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
    private var listener: ((Character) -> Unit)? = null

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val characterNameTextView: TextView = itemView.findViewById(R.id.character_name)
        private val characterStatusTextView: TextView = itemView.findViewById(R.id.character_status)
        private val characterImageView: ImageView = itemView.findViewById(R.id.character_image)

        fun bind(character: Character) {
            characterNameTextView.text = character.name
            characterStatusTextView.text = character.status
            Picasso.get().load(character.image).into(characterImageView)
        }
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

        holder.bind(character)
        holder.itemView.setOnClickListener { listener?.invoke(character) }

    }

    fun updateData(newItems: List<Character>) {
        characterList.clear()
        characterList.addAll(newItems)
        notifyItemRangeChanged(0, characterList.size)
    }

    fun setItemClickedListener(listener: (Character) -> Unit) {
        this.listener = listener
    }
}