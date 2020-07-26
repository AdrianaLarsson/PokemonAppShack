package se.appshack.android.refactoring.Activities

import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import se.appshack.android.refactoring.ModelClasses.GameView


class GameActivity : AppCompatActivity() {

    private var gameView: GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        gameView = GameView(this, point.x, point.y)
        setContentView(gameView)
    }

    override fun onPause() {
        super.onPause()
        gameView!!.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView!!.resume()
    }
}

/*
class GameActivity : AppCompatActivity() {

    private var gameView: GameView? = null
    var flight1: Bitmap? = null
    var flight2: Bitmap? = null
    var shoot1: Bitmap? = null
    var shoot2: Bitmap? = null
    var shoot3: Bitmap? = null
    var shoot4: Bitmap? = null
    var shoot5: Bitmap? = null
    private var bullets: List<BulletClass>? = null
   var  wingCounter = 0
    var dead: Bitmap? = null
    lateinit var shootOne: Bitmap
    var toShoot = 0
    var shootCounter = 1
    private var mainLayout: ViewGroup? = null
    private lateinit var image: ImageView

    private var xDelta = 0
    private var yDelta = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(gameView)


        val point = Point()
        gameView = GameView(this, point.x, point.y)

        getWindowManager().getDefaultDisplay().getSize(point);
        mainLayout = findViewById(R.id.constraint);
        image = findViewById(R.id.pokeomonPlay);

        image.setOnTouchListener(onTouchListener());
    }
    override fun onPause() {
        super.onPause()
        gameView!!.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView!!.resume()
    }

    fun moveBackground() {

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val backgroundOne: ImageView = findViewById(se.appshack.android.refactoring.R.id.background_one) as ImageView
        val backgroundTwo: ImageView = findViewById(se.appshack.android.refactoring.R.id.background_two) as ImageView

        val animator = ValueAnimator.ofFloat(1.0f, 0.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 10000L
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width: Int = backgroundOne.getWidth()
            val translationX = width * progress
            backgroundOne.setTranslationX(translationX - width)
            backgroundTwo.setTranslationX(translationX)


        }
        animator.start()
    }


    fun pokemonPlay() {

        val bullets: ImageView = findViewById(se.appshack.android.refactoring.R.id.bullet) as ImageView

        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 10000L

        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width: Int = bullets.getWidth()
            val translationX = width * progress
            bullets.setTranslationX(translationX)
            bullets.setTranslationX(translationX - width)
        }
        animator.start()

    }

    private fun onTouchListener(): OnTouchListener? {

        val pokemonUrl = intent.extras.getString("POKEMON_URL")
        var imgPlayPoke: ImageView = findViewById(se.appshack.android.refactoring.R.id.pokeomonPlay)
        Picasso.with(this).load(pokemonUrl).into(imgPlayPoke)

        return OnTouchListener { view, event ->
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = view.layoutParams as ConstraintLayout.LayoutParams
                    xDelta = x - lParams.leftMargin
                    yDelta = y - lParams.topMargin


                }

                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = view
                            .layoutParams as ConstraintLayout.LayoutParams
                    layoutParams.leftMargin = x - xDelta
                    layoutParams.topMargin = y - yDelta
                    layoutParams.rightMargin = 0
                    layoutParams.bottomMargin = 0
                    view.layoutParams = layoutParams


                    toShoot

                }
            }
            mainLayout!!.invalidate()
            true
        }


    }


    fun shoot() {


        shootOne = BitmapFactory.decodeResource(resources, R.drawable.bulletone)
        var shootTwo = BitmapFactory.decodeResource(resources, R.drawable.bulletone)
        var shootThree = BitmapFactory.decodeResource(resources, R.drawable.bulletone)
        var shootFour = BitmapFactory.decodeResource(resources, R.drawable.bulletone)
        var shootFive = BitmapFactory.decodeResource(resources, R.drawable.bulletone)

        shootOne = Bitmap.createScaledBitmap(shootOne, 5, 5, false)
        shootTwo = Bitmap.createScaledBitmap(shootOne, 5, 5, false)
        shootThree = Bitmap.createScaledBitmap(shootOne, 5, 5, false)
        shootFour = Bitmap.createScaledBitmap(shootOne, 5, 5, false)
        shootFive = Bitmap.createScaledBitmap(shootOne, 5, 5, false)


    }



}
*/
