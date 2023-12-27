package com.example.thebeatles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class SlideShow : Thread  //Error message occurs because Constructor not implemented yet
{
  private var noSlides = 0
  private var duration : Long = 0
  private var count : Int = 1
  //private var imageView : ImageView? = null

  constructor()
  {
    duration = 3
    noSlides = 3
    //imageView = MainActivity.getInstance().findViewById(R.id.imageView2)
  }

  override public fun run()
  {
    var count = 0
    var files = arrayOf("intro1", "abbeyroad", "beatlesforsale", "harddaysnight","help","letitbe",
      "magicalmysterytour","pastmastersvolume1","pastmastersvolume2","pleasepleaseme","revolver",
      "rubber_soul","sgt_pepper","white","with_the_beatles","yellowsubmarine")

    while(true)
    {
      var handler = HandlerThread(files[count%files.size])
      MainActivity.getInstance().runOnUiThread(handler)
      Thread.sleep(duration*1000) //Delay
      count++
    }

  }

}

class HandlerThread : Runnable
{
  private var fn : String = ""
  private var imageView : ImageView? = null
  private var id : Int? = null
  constructor(fn : String)
  {
    this.fn = fn

    imageView = MainActivity.getInstance().findViewById<ImageView>(R.id.imageView2)

    id = MainActivity.getInstance().resources.getIdentifier(fn, "drawable",MainActivity.getInstance().packageName)
  }
  override fun run()
  {

    imageView?.setImageResource(id!!)
  }

}


class BottomMainFragment : Fragment()
{
  private var first : Boolean = true

  companion object
  {
    private var instance : BottomMainFragment? = null
    public fun getInstance() : BottomMainFragment
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
    return inflater.inflate(R.layout.fragment_bottom_main, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)

    var slideShow = SlideShow()
    if (first)
    {
      first = false
      slideShow.start()
    }

  }

}