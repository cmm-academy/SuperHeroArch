package com.mstudio.superheroarch.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mstudio.superheroarch.data_remote.ApiRick
import com.mstudio.superheroarch.data_remote.ApiService
import com.mstudio.superheroarch.data_local.AppDatabase
import com.mstudio.superheroarch.ui.adapter.CharacterAdapter
import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.data_local.LocalDataSourceImpl
import com.mstudio.superheroarch.R
import com.mstudio.superheroarch.data_remote.RemoteDataSourceImpl
import com.mstudio.superheroarch.domain.FilterCharactersByStatusUseCase
import com.mstudio.superheroarch.domain.GetCharactersUseCase
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.presentation.MainViewModel
import com.mstudio.superheroarch.presentation.ViewTranslator

class MainActivity : AppCompatActivity(), ViewTranslator {

    private var viewModel: MainViewModel? = null
    private val adapter = CharacterAdapter()

    companion object {
        const val EXTRA_CHARACTER = "com.mstudio.superheroarch.ui.activity.MainActivity.EXTRA_CHARACTER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val database = AppDatabase.getDatabase(this)
        val characterDao = database.characterDao()
        val apiRick: ApiRick = ApiService.retrofit.create(ApiRick::class.java)
        val remoteDataSource = RemoteDataSourceImpl(apiRick)
        val localDataSource = LocalDataSourceImpl(characterDao)
        val repository = RickAndMortyRepository(remoteDataSource, localDataSource)

        val getCharactersUseCase = GetCharactersUseCase(repository)
        val filterCharactersByStatusUseCase = FilterCharactersByStatusUseCase()

        viewModel = MainViewModel(this, getCharactersUseCase, filterCharactersByStatusUseCase)
        viewModel?.onCreate()

        val allButton = findViewById<Button>(R.id.all)
        val aliveButton = findViewById<Button>(R.id.alive)
        val deadButton = findViewById<Button>(R.id.dead)
        val unknownButton = findViewById<Button>(R.id.unknown)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val characterRecyclerView = findViewById<RecyclerView>(R.id.characters_recycler)
        characterRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : CharacterAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                viewModel?.onCharacterClicked(position)
            }
        })

        allButton.setOnClickListener {
            viewModel?.onFilterClicked(null)
        }
        aliveButton.setOnClickListener {
            viewModel?.onFilterClicked("Alive")
        }
        deadButton.setOnClickListener {
            viewModel?.onFilterClicked("Dead")
        }
        unknownButton.setOnClickListener {
            viewModel?.onFilterClicked("unknown")
        }
    }

    override fun showEmptyError() {
        Snackbar.make(findViewById(R.id.main), "No characters available", Snackbar.LENGTH_LONG)
            .show()
    }

    override fun showCharacters(characters: List<CharacterEntity>) {
        adapter.updateCharacters(characters)
    }

    override fun navigateToDetails(character: CharacterEntity) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(EXTRA_CHARACTER, character)
        }
        startActivity(intent)
    }
}
