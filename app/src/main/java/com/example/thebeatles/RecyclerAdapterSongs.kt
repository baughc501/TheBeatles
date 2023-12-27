package com.example.thebeatles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class Song
{
  private var songTitle : String = ""
  private var songPerformer : String = ""


  constructor(songTitle : String, songPerformer : String)
  {
    this.songTitle = songTitle
    this.songPerformer = songPerformer
  }

  public fun getSongTitle() : String
  {
    return songTitle
  }

  public fun setSongTitle(songTitle : String)
  {
    this.songTitle = songTitle
  }

  public fun setSongPerformer(songPerformer : String)
  {
    this.songPerformer = songPerformer
  }

  public fun getSongPerformer() : String
  {
    return songPerformer
  }

}

class RecyclerAdapterSongs  : RecyclerView.Adapter<RecyclerAdapterSongs.ViewHolder>
{
  private var pos : Int = -1
  private var titles = arrayOf("Jim's Schedule", "Mary's Schedule", "Jo's Schedule")
  private var schedule = arrayOf<Array<String>>()
  private var jimSchedule = arrayOf("CS1: 9:00 - 9:50\n2-D arrays and intro to pointers","Data Structures: 10:00 - 10:50\nBinary Trees","Office Hours: 11:00 -3:00 pm")
  private var marySchedule = arrayOf("Intro to Computers: 10:00 -10:50\nReview MS Word","Office Hours: 11:00 - 12:00","Lunch time: 12:00 - 1:00")
  private var joSchedule = arrayOf("Algorithms: 10:00 - 10:50\nReview for Test #2", "Meeting with local company", "Meeting with high school students")
  private var days = arrayOf("Monday", "Tuesday", "Wednesday")

  private var playlist =arrayOf<Song>()
  private var allPlaylists = arrayOf<Array<String>>()
  var newSong = Song("My New Song","Me Me")
  var newSong2 = Song("Songs Are Us","U U")
  private var please_please_me_playlist = arrayOf("Do You Want To Know A Secret\nLennon & McCartney",
    "There's A Place\nLennon & McCartney","I Saw Her Standing There\nLennon & McCartney")
  private var with_the_beatles_playlist = arrayOf("Little Child\nLennon & McCartney",
    "Hold Me Tight \nLennon & McCartney","Don't Bother Me \nGeorge Harrison")

  constructor(pos : Int) : super()
  {
    this.pos = pos
  }

  init
  {
    allPlaylists += please_please_me_playlist
    allPlaylists += with_the_beatles_playlist
  }

  public fun setPos(pos : Int)
  {
    this.pos = pos
  }



  override fun onBindViewHolder(holder: RecyclerAdapterSongs.ViewHolder, position: Int)
  {
    var playlist = allAlbums[pos].getNewplaylist()
    holder.itemSongTitleHeading.text = playlist[position].getSongTitle()
    holder.itemSongPerformerTextView.text = playlist[position].getSongPerformer()
  }

  override fun getItemCount(): Int
  {
    return allAlbums[pos].getNewplaylist().size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterSongs.ViewHolder
  {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.songs_card_layout, parent, false)
    return ViewHolder(v)
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
  {
    var itemSongTitleHeading: TextView
    var itemSongPerformerTextView: TextView

    init
    {
      itemSongTitleHeading = itemView.findViewById(R.id.songTitleHeading)
      itemSongPerformerTextView = itemView.findViewById(R.id.songPerformerTextView)

      var handler = Handler()
      itemView.setOnClickListener(handler)
    }

    inner class Handler() : View.OnClickListener
    {
      override fun onClick(v: View?)
      {
        val itemPosition = getLayoutPosition()
        //Get the navigation controller
        var navController = Navigation.findNavController(SongFragment.getInstance().requireView()!!)
        val bundle = Bundle()
        bundle.putInt("position", itemPosition)
        bundle.putInt("album_position" , pos)
        navController.navigate(R.id.songToWeb,bundle)
      }
    }
  }
}
