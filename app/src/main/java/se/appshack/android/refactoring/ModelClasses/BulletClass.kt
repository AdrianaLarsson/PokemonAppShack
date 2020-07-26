package se.appshack.android.refactoring.ModelClasses

import android.R
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect


class BulletClass internal constructor(res: Resources?) {
    var x = 0
    var y = 0
    var width: Int
    var height: Int
    var bullet: Bitmap

    val collisionShape: Rect

        get() = Rect(x, y, x + width, y + height)

    init {
        bullet = BitmapFactory.decodeResource(res, se.appshack.android.refactoring.R.drawable.bulletone)
        width = bullet.width
        height = bullet.height
        width /= 4
        height /= 4
//        width = (width * screenRatioX) as Int
//        height = (height * screenRatioY) as Int
        bullet = Bitmap.createScaledBitmap(bullet, width, height, false)
    }
}