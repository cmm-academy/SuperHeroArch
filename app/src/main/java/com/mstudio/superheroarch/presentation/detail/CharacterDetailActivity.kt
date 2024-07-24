package com.mstudio.superheroarch.presentation.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.mstudio.superheroarch.R
import com.mstudio.superheroarch.databinding.CharacterDetailActivityBinding
import com.mstudio.superheroarch.presentation.overview.MainActivity.Companion.CHARACTER_DATA_KEY
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity

class CharacterDetailActivity : AppCompatActivity() {
    private lateinit var binding: CharacterDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CharacterDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val character = intent.getSerializableExtra(CHARACTER_DATA_KEY) as CharactersRemoteEntity

        with(binding) {
            topBar.navigationIcon = ContextCompat.getDrawable(this@CharacterDetailActivity, R.drawable.ic_arrow_left)
            topBar.setNavigationOnClickListener {
                finish()
            }
            topBar.title = character.name
            topBar.isTitleCentered = true
            imageDetail.load(character.image)
            statusDetail.text = resources.getString(R.string.character_status, character.status)
            speciesDetail.text = resources.getString(R.string.character_species, character.species)
            originDetail.text = resources.getString(R.string.character_origin, character.origin.name)
            locationDetail.text = resources.getString(R.string.character_location, character.location.name)
        }
    }
}