package se.appshack.android.refactoring.ModelClasses

import android.content.Context
import android.graphics.Paint
import android.view.SurfaceView


class GameView(context: Context?) : SurfaceView(context), Runnable {

     private lateinit var  thread: Thread
     private var isPlaying : Boolean = false
     private var background1: BackgroundGame? = null
     private var background2: BackgroundGame? = null
    lateinit var paint : Paint


    var screenX: Int = 0
    var screenY : Int = 0



     override fun run() {



         while (isPlaying){
             update()
             draw()
             sleep()
         }


         background1 = BackgroundGame(screenX,screenY, resources)
         background2 = BackgroundGame(screenX, screenY, resources)

         background2!!.x = screenX


         paint = Paint()
     }


     fun update(){

         background1!!.x -= 10
         background2!!.x -= 10


         if (background1!!.x + background1!!.background.width < 0){
             background1!!.x = screenX


         }

         if (background2!!.x + background2!!.background.width < 0){
             background2!!.x = screenX


         }




     }


     fun draw(){

         if (holder.surface.isValid) {

             val canvas = holder.lockCanvas()
             canvas.drawBitmap(background1!!.background, background1!!.x, background1!!.y, paint);

             }


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