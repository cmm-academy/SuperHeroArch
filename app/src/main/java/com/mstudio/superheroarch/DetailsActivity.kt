package com.mstudio.superheroarch

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_screen)

        val characterNameTextView: TextView = findViewById(R.id.character_name)
        val characterStatusTextView: TextView = findViewById(R.id.character_status)
        val backButton: ImageButton = findViewById(R.id.back)
        val characterImageView: ImageView = findViewById(R.id.character_image)
        val characterLocationTextView: TextView = findViewById(R.id.location)
        val characterOriginTextView: TextView = findViewById(R.id.origin)

        backButton.setOnClickListener {
            finish()
        }

        val characterImage = intent.getStringExtra(MainActivity.EXTRA_IMAGE)
        characterNameTextView.text = intent.getStringExtra(MainActivity.EXTRA_NAME)
        characterStatusTextView.text = intent.getStringExtra(MainActivity.EXTRA_STATUS)
        characterLocationTextView.text = intent.getStringExtra(MainActivity.EXTRA_LOCATION)
        characterOriginTextView.text = intent.getStringExtra(MainActivity.EXTRA_ORIGIN)

        Picasso.get().load(characterImage).placeholder(R.drawable.placeholder).error(R.drawable.error).into(characterImageView)

    }
}
