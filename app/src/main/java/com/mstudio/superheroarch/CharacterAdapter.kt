package com.mstudio.superheroarch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var mListener: onItemClickListener? = null

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

     var characters: MutableList<Character> = mutableListOf()

    class CharacterViewHolder(view: View, private val listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.character_name)
        val status: TextView = view.findViewById(R.id.character_status)
        val image: ImageView = view.findViewById(R.id.character_image)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return mListener?.let {
            CharacterViewHolder(view, it)
        } ?: throw IllegalStateException("Listener no puede ser nulo. Asegúrate de llamar a setOnItemClickListener antes de crear el adaptador.")
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.name.text = character.name
        holder.status.text = character.status
        Picasso.get().load(character.image).placeholder(R.drawable.placeholder).error(R.drawable.error).into(holder.image)
    }

    override fun getItemCount(): Int = characters.size

    fun updateCharacters(newCharacters: List<Character>) {
        characters.clear()
        characters.addAll(newCharacters)
        notifyItemRangeChanged(0, characters.size)
    }
}