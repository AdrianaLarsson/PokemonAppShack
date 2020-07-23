package se.appshack.android.refactoring.Activities

import android.R
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.LinearInterpolator
import android.widget.ImageView


class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(se.appshack.android.refactoring.R.layout.game_layout)
        move()
    }



    fun move(){

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
}
