package se.appshack.android.refactoring.ModelClasses

import android.R
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory


class BackgroundClass (screenX: Int, screenY: Int, res: Resources?) {
    var x = 0
    var y = 0
    var background: Bitmap

    init {
        background = BitmapFactory.decodeResource(res, se.appshack.android.refactoring.R.drawable.backgpoke)
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false)
    }
}