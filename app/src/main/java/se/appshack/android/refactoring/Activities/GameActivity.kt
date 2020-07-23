package se.appshack.android.refactoring.Activities

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.squareup.picasso.Picasso
import se.appshack.android.refactoring.R


class GameActivity : AppCompatActivity() {

    private var mainLayout: ViewGroup? = null
    private lateinit var image: ImageView

    private var xDelta = 0
    private var yDelta = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(se.appshack.android.refactoring.R.layout.game_layout)
        moveBackground()



        mainLayout =  findViewById(R.id.constraint);
        image =  findViewById(R.id.pokeomonPlay);

        image.setOnTouchListener(onTouchListener());
    }



    fun moveBackground(){

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val backgroundOne: ImageView = findViewById(se.appshack.android.refactoring.R.id.background_one) as ImageView
        val backgroundTwo: ImageView = findViewById(se.appshack.android.refactoring.R.id.background_two) as ImageView

        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 10000L
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width: Int = backgroundOne.getWidth()
            val translationX = width * progress
            backgroundOne.setTranslationX(translationX)
            backgroundTwo.setTranslationX(translationX - width)
        }
        animator.start()
    }

/*
    fun pokemonPlay(){



        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 10000L

        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width: Int = imgPlayPoke.getWidth()
            val translationX = width * progress
            imgPlayPoke.setTranslationX(translationX)
            imgPlayPoke.setTranslationX(translationX - width)
        }
        animator.start()

    }*/

    private fun onTouchListener(): OnTouchListener? {

        val pokemonUrl = intent.extras.getString("POKEMON_URL")

        var imgPlayPoke : ImageView = findViewById(se.appshack.android.refactoring.R.id.pokeomonPlay)
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
                }
            }
            mainLayout!!.invalidate()
            true
        }
    }


}
