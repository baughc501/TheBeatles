package com.example.thebeatles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class Album
{
  private var title : String = ""
  private var producer : String = ""
  private var date : String = ""
  private var pic : String = ""
  private var newPlaylist = arrayOf<Song>()

  constructor(title : String, producer : String, date : String, pic : String, newPlaylist : Array<Song>)
  {
    this.title = title
    this.producer = producer
    this.date = date
    this.pic = pic
    this.newPlaylist = newPlaylist
  }

  public fun setDate(date : String)
  {
    this.date = date
  }

  public fun getDate() : String
  {
    return date
  }

  public fun getTitle() : String
  {
    return title
  }

  public fun setTitle(title : String)
  {
    this.title = title
  }

  public fun setProducer(producer : String)
  {
    this.producer = producer
  }

  public fun getProducer() : String
  {
    return producer
  }

  public fun setPic(pic : String)
  {
    this.pic = pic
  }

  public fun getPic() : String
  {
    return pic
  }

  public fun setNewPlaylist(newPlaylist: Array<Song>)
  {
    this.newPlaylist = newPlaylist
  }

  public fun getNewplaylist() : Array<Song>
  {
    return newPlaylist
  }

}


class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()
{
 /*private var allAlbums = arrayOf(Album("Please Please Me", "Produced by George Martin",
    "March 22, 1963", "pleasepleaseme"), Album("With The Beatles", "Produced by George Martin",
    "November 22, 1963", "with_the_beatles"), Album("A Hard Day's Night", "Produced by George Martin",
      "July 10. 1964", "harddaysnight"))*/


  //This tells the system how many cells are desired
  override fun getItemCount(): Int
  {
    return allAlbums.size
  }

  //This creates a ViewHolder object based on card_layout for each cell
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
  {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.album_card_layout, parent, false)
    return ViewHolder(v)
  }

  //This initializes the viewHolder to whatever the data source specifies
  override fun onBindViewHolder(holder: ViewHolder, position: Int)
  {
    holder.itemTitle.text = allAlbums[position].getTitle()
    holder.itemDetail.text = allAlbums[position].getDate()
    holder.itemImage.setImageResource(MainActivity.getInstance().resources.getIdentifier(allAlbums[position].getPic(),"drawable",
      MainActivity.getInstance().packageName))
  }



  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
  {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView

    init
    {
      itemImage = itemView.findViewById(R.id.imageView)
      itemTitle = itemView.findViewById(R.id.albumTitle)
      itemDetail = itemView.findViewById(R.id.date)

      var handler = Handler()
      itemView.setOnClickListener(handler)


    }

    inner class Handler() : View.OnClickListener
    {
      override fun onClick(v: View?)
      {
        val itemPosition = getLayoutPosition()
        //Get the navigation controller
        var navController = Navigation.findNavController(AlbumFragment.getInstance().requireView()!!)
        val bundle = Bundle()
        bundle.putInt("position", itemPosition)
        navController.navigate(R.id.albumToSong,bundle)
      }
    }


  }
}
