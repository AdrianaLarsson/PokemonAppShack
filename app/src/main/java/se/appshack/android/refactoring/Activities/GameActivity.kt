package se.appshack.android.refactoring.Activities

import android.animation.ValueAnimator
import android.graphics.Point
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.game_layout.*
import se.appshack.android.refactoring.R


class GameActivity : AppCompatActivity() {



    private var mainLayout: ViewGroup? = null
    private lateinit var image: ImageView

    private var xDelta = 0
    private var yDelta = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_layout)


        val point = Point()


        getWindowManager().getDefaultDisplay().getSize(point);
        mainLayout = findViewById(R.id.constraint);
        image = findViewById(R.id.pokeomonPlay);

        image.setOnTouchListener(onTouchListener());

        moveBackground()

information()

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




    private fun onTouchListener(): View.OnTouchListener? {

        val pokemonUrl = intent.extras.getString("POKEMON_URL")
        var imgPlayPoke: ImageView = findViewById(se.appshack.android.refactoring.R.id.pokeomonPlay)
        Picasso.with(this).load(pokemonUrl).into(imgPlayPoke)

        return View.OnTouchListener() { view, event ->
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




                }
            }
            mainLayout!!.invalidate()
            true
        }


    }




fun information(){




    val pokemonH = intent.extras.getString("POKEMON_HEIGHT")
    val pokemonW = intent.extras.getString("POKEMON_WEIGHT")
    val pokemonNu = intent.extras.getString("POKEMON_NUMBER")
    val pokemonNa = intent.extras.getString("POKEMON_NAME")


    pokemonHeight.text = pokemonH
    pokemonWeight.text = pokemonW
    pokemonNumber.text = pokemonNu
    pokemonName.text = pokemonNa




}



}


