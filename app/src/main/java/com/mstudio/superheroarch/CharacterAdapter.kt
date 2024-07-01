package com.mstudio.superheroarch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

     var characters: MutableList<Character> = mutableListOf()

    class CharacterViewHolder(view: View, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.character_name)
        private val status: TextView = view.findViewById(R.id.character_status)
        private val image: ImageView = view.findViewById(R.id.character_image)
        private var currentCharacter: Character? = null

        init {
            itemView.setOnClickListener{
                listener.onItemClick(bindingAdapterPosition)
            }
        }
        fun bind(character: Character){
            currentCharacter = character
            name.text = character.name
            status.text = character.status
            Picasso.get().load(character.image).placeholder(R.drawable.placeholder).error(R.drawable.error).into(image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return mListener?.let {
            CharacterViewHolder(view, it)
        } ?: throw IllegalStateException("Listener cannot be null.")
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = characters.size

    fun updateCharacters(newCharacters: List<Character>) {
        characters.clear()
        characters.addAll(newCharacters)
        notifyItemRangeChanged(0, characters.size)
    }
}