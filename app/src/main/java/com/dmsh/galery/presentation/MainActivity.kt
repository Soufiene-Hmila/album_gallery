package com.dmsh.galery.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dmsh.galery.AlbumApplication
import com.dmsh.galery.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var dataAdapterAlbum: DataAdapterAlbum

    private val albumViewModel: AlbumViewModel by lazy {
        val factory = AlbumViewModel.BooksViewModelFactory(
            ((this@MainActivity.application) as AlbumApplication).getAlbumUseCase,
            ((this@MainActivity.application) as AlbumApplication).setAlbumUseCase,
            ((this@MainActivity.application) as AlbumApplication).getRemoteAlbumUseCase,
        )
        ViewModelProvider(this@MainActivity, factory)[AlbumViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        albumViewModel.getDataAlbum()

        textView.visibility = View.INVISIBLE

        albumViewModel.dataLoading.observe(this@MainActivity) { loading ->
            when (loading) {
                true -> {
                    textView.visibility = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE
                    recyclerViewAlbum.visibility = View.INVISIBLE
                }
                false -> {
                    textView.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                    recyclerViewAlbum.visibility = View.VISIBLE
                }
            }
        }

        albumViewModel.album.observe(this@MainActivity) {

            textViewListSize.text = "${it.size} Albums"
            dataAdapterAlbum = DataAdapterAlbum(this@MainActivity, it)
            recyclerViewAlbum.adapter = dataAdapterAlbum

        }


        albumViewModel.error.observe(this@MainActivity) {
            Toast.makeText(
                this@MainActivity,
                "Check your network !",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}