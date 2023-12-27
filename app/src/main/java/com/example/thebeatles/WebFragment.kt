package com.example.thebeatles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.net.URL

class UIThreadHelper : Runnable
{
  private var video : String = ""
  constructor(video : String)
  {
    this.video = video
  }
  override fun run()
  {

    //Update the webView
    var web = MainActivity.getInstance().findViewById<WebView>(R.id.web)
    val settings = web.getSettings()
    settings.setJavaScriptEnabled(true)
    settings.setDomStorageEnabled(true)
    settings.setMinimumFontSize(10)
    settings.setLoadWithOverviewMode(true)
    settings.setUseWideViewPort(true)
    settings.setBuiltInZoomControls(true)
    settings.setDisplayZoomControls(false)
    web.setVerticalScrollBarEnabled(false)
    settings.setDomStorageEnabled(true)
    web.setWebViewClient(WebViewClient())
    var str = "https://www.youtube.com/watch?v=" + video
    web.loadUrl(str)

  }
}


class Helper : Runnable
{
  private var url : String = ""
  private var song : String = ""
  private var artist : String = ""
  private var album : String = ""

  constructor(url : String, song:String, artist:String, album:String)
  {
    this.url = url
    this.song = song
    this.artist = artist
    this.album = album
  }

  override fun run()
  {
    val data = URL(url).readText()
    println(data)

    var json = JSONObject(data)

    var items = json.getJSONArray("items") // this is the "items: [ ] part

    var titles = ArrayList<String>()
    var videos = ArrayList<String>()
    var descriptions = ArrayList<String>()

    for (i in 0 until items.length())
    {
      var videoObject = items.getJSONObject(i)
      //val title = videoObject.getString("title")
      //val videoId = videoObject.getString("id")
      println(videoObject)
      var idDict = videoObject.getJSONObject("id")
      println(idDict)
      var videoId = idDict.getString("videoId")
      println(videoId)
      var snippetDict = videoObject.getJSONObject("snippet")
      var title =  snippetDict.getString("title")
      println(title)
      var description = snippetDict.getString("description")
      descriptions.add(description)
      titles.add(title)
      videos.add(videoId)
    }

    //hard coded
    var selected_video : String = ""
    var selected_title : String = ""
    var selected_description : String =""

    var i=0
    for (i in 0..titles.size -1)
    {
      if (titles[i].contains(artist) && titles[i].contains(song) && !titles[i].contains("cover"))//&& descriptions[i].contains(artist)) //&& descriptions[i].contains(album))
      //tester filters
      // && !titles[i].contains("cover")
      {
        selected_video = videos[i]
        selected_title = titles[i]
        //selected_description = descriptions[i]
      }
    }


    var helper1 = UIThreadHelper(selected_video)
    MainActivity.getInstance().runOnUiThread(helper1)


  }

}
class WebFragment : Fragment()
{

  companion object
  {
    private var instance : WebFragment? = null
    public fun getInstance() : WebFragment
    {
      return instance!!
    }
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)

    instance = this

  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View?
  {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_web, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)

    //my stuff
    var arguments = this.getArguments()
    var pos = arguments?.getInt("position")
    var album_pos = arguments?.getInt("album_position")

    //var song = "Please Please Me"
    var playlist = allAlbums[album_pos!!].getNewplaylist()
    var song = playlist[pos!!].getSongTitle()
    song = song.replace(" ", "+")
    var origSong = playlist[pos!!].getSongTitle() //"Please Please Me"

    //Set the artist
    var artist = "The Beatles"
    artist = artist.replace(" ","+")
    var origArtist = "The Beatles"

    //set the album title
    var album = allAlbums[album_pos!!].getTitle()
    album = album.replace(" ","+")
    var origAlbum = allAlbums[album_pos!!].getTitle()

    //Encode search for YouTube
    val keywords = artist + "+" + album + "+" +  song
    val max = 50

    //Set the youtube search
    //with dr smith key
    //val string = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=$keywords&order=viewCount&maxResults=$max&type=video&videoCategory=Music&key=AIzaSyDtzKWgA0ne39VD_-i0oJwCd4WOdFKZy4I"

    //with my key
    val string = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=$keywords&order=viewCount&maxResults=$max&type=video&videoCategory=Music&key=AIzaSyByUcGC8E4y82kgcl4QSemGiTmFMZtbp5E"

    var helper = Helper(string, origSong, origArtist, origAlbum)
    var thread = Thread(helper)
    thread.start()

  }


}