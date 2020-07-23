package se.appshack.android.refactoring.ModelClasses

import android.content.Context
import android.view.SurfaceView

 class GameView(context: Context?) : SurfaceView(context), Runnable {

     private lateinit var  thread: Thread
     private var isPlaying : Boolean = false

     override fun run() {

         while (isPlaying){
             update()
             draw()
             sleep()
         }


     }


     fun update(){



     }


     fun draw(){

     }
     fun sleep(){

         try {
             
             Thread.sleep(17)

         }  catch (e :InterruptedException ){

             e.printStackTrace()


         }

     }

      fun resume(){


          isPlaying = true
          thread = Thread(this)
          thread.start()
     }

     fun pause(){


         try {

             isPlaying = false
             thread.join()

         }catch (e :InterruptedException ){

             e.printStackTrace()

         }


     }

 }