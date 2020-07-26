package se.appshack.android.refactoring.ModelClasses

import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Paint
import android.media.SoundPool
import android.view.SurfaceView
import se.appshack.android.refactoring.Activities.GameActivity

import kotlin.random.Random


class GameView(gameActivity: GameActivity, screenX : Int, screenY : Int) : SurfaceView(gameActivity), Runnable {

    private var thread: Thread? = null
    private var isPlaying = false
    private var isGameOver = false
    private var screenX = 0
    private var screenY: Int = 0
    private var score: Int = 0
    var screenRatioX = 0f
    var screenRatioY = 0f
    private var paint: Paint? = Paint()

    //  private val birds: Array<Bird>
    private val prefs: SharedPreferences? = null
    private val random: Random? = null
    private val soundPool: SoundPool? = null

    // private val bullets: List<Bullet>? = null
    private val sound = 0

    // private val flight: Flight? = null
    private val activity: GameActivity? = null
    private var background1: BackgroundClass
    private var background2: BackgroundClass?


    init {

        paint = Paint()
        this.screenX = screenX
        this.screenY = screenY
        screenRatioX = 1920f / screenX
        screenRatioY = 1080f / screenY

        background1 = BackgroundClass(screenX, screenY, resources)
        background2 = BackgroundClass(screenX, screenY, resources)

        background2!!.x = screenX


    }


    override fun run() {
        while (isPlaying) {

            update()
            draw()
            sleep()

        }
    }

    fun resume() {
        isPlaying = true
        thread = Thread(this)
        thread!!.start()
    }

    fun pause() {
        try {
            isPlaying = false
            thread!!.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


    private fun update() {

        background1?.x?.minus(10)?.times(screenRatioX)
        background2?.x?.minus(10)?.times(screenRatioX)
        // background1.x -= 10 * screenRatioX;
        //background2.x -= 10 * screenRatioX;


        if (background1?.x?.plus(background1!!.background.width)!! < 0) {


        }


        if (background2!!.x + background2!!.background.getWidth() < 0) {
            background2!!.x = screenX;
        }

    }

    private fun sleep() {
        try {
            Thread.sleep(17)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


    private fun draw() {


        if (holder.surface.isValid) {
            val canvas: Canvas = holder.lockCanvas()


            //canvas.drawBitmap(background1!!.background, 1.0f, 0.0f, paint)
            //canvas.drawBitmap(background2!!.background, 1.0f, 0.0f, paint)
          var x =  background1.x.toFloat()
          var xtwo =  background2!!.x.toFloat()
            var y = background2!!.y.toFloat()
            canvas.drawBitmap(background1.background, x, x, paint);
            canvas.drawBitmap(background2!!.background, xtwo, y, paint);

            getHolder().unlockCanvasAndPost(canvas)

        }
    }
}