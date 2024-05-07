package com.mstudio.superheroarch

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import retrofit2.Response
import retrofit2.http.GET

class MainActivity : AppCompatActivity(), MainViewTranslator {

    private val adapter = CharactersAdapter()
    private val viewModel = MainViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<RecyclerView>(R.id.characterList).adapter = adapter

        val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
        chipGroup.setOnCheckedStateChangeListener { group, _ ->
            viewModel.onFilterSelected(group.checkedChipId)
        }

        adapter.setItemClickedListener {
            startActivity(CharacterDetailsActivity.newIntent(this, it))
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            viewModel.onRefreshClicked()
        }

        viewModel.onViewCreated()
    }

    override fun showCharacters(characterList: List<Character>) {
        adapter.setCharacters(characterList)
    }

    override fun showErrorMessage(error: String) {
        Snackbar.make(findViewById(R.id.main), error, Snackbar.LENGTH_SHORT).show()
    }
}

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(): Response<ApiResponseWrapper>
}
