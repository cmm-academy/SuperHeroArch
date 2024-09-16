package com.mstudio.superheroarch

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var mListener: OnItemClickListener? = null
    private var characters: List<CharacterEntity> = emptyList()

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class CharacterViewHolder(view: View, private val listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.character_name)
        private val status: TextView = view.findViewById(R.id.character_status)
        private val image: ImageView = view.findViewById(R.id.character_image)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(bindingAdapterPosition)
            }
        }

        fun bind(character: CharacterEntity) {
            name.text = character.name
            status.text = character.status
            Picasso.get().load(character.image).placeholder(R.drawable.placeholder)
                .error(R.drawable.error).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.caracter_view_holder, parent, false)
        return CharacterViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = characters.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCharacters(newCharacters: List<CharacterEntity>) {
        characters = newCharacters
        notifyDataSetChanged()
    }
}
