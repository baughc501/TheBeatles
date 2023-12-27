package com.example.thebeatles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.*


var allAlbums = arrayOf<Album>()


class MainActivity : AppCompatActivity()
{

  companion object
  {
    private var instance : MainActivity? = null
    public fun getInstance() : MainActivity
    {
      return instance!!
    }
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)

    instance = this
    setContentView(R.layout.activity_main)

    val navController = Navigation.findNavController(this, R.id.fragment)

    var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

    bottomNavigationView.setupWithNavController(navController)


    var is1 = this.getAssets().open("albums.txt")

    var reader1 = BufferedReader(InputStreamReader(is1))

    var lines1 = reader1.readLines()

    var  arrayLines1 = lines1.toTypedArray()

    //create 2d array of fields
    var allData1 = arrayOf<Array<String>>()

    //Parse into fields
    for (i in 0..arrayLines1.size -1)
    {
      var array1 = arrayLines1[i].split("^")
      allData1 +=  array1.toTypedArray()

      //new stuff to try
      var newPlaylist = arrayOf<Song>()
      var filename = array1[3]+".txt"
      var is2 = this.getAssets().open(array1[3]+".txt")
      var reader2 = BufferedReader(InputStreamReader(is2))
      var lines2 = reader2.readLines()
      var  arrayLines2 = lines2.toTypedArray()
      //create 2d array of fields
      var allData2 = arrayOf<Array<String>>()
      //Parse into fields
      for (i in 0..arrayLines2.size -1)
      {
        var array2 = arrayLines2[i].split("^")
        var newSong = Song(array2[0],array2[1])
        newPlaylist += newSong
      }

      var newAlbum = Album(array1[0],array1[1],array1[2],array1[3],newPlaylist)
      allAlbums += newAlbum
    }

    reader1.close()

  }
}