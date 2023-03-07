package com.vikmanz.shpppro.utilits

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class CustomGoogleButton(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : View(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private val paint: Paint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.apply {
            style = Paint.Style.FILL // стиль Заливка
            color = Color.WHITE // закрашиваем холст белым цветом
        }
        canvas?.drawPaint(paint)

        paint.apply {
            isAntiAlias = true
            color = Color.YELLOW
        }
        canvas?.drawCircle(950F, 30F, 25F, paint)

        paint.color = Color.GREEN
        canvas?.drawRect(00F, 650F, 950F, 680F, paint)

        paint.apply {
            color = Color.BLUE
            style = Paint.Style.FILL
            isAntiAlias = true
            textSize = 32F
        }
        canvas?.drawText("Лужайка только для котов", 30F, 648F, paint)

        val rect: Rect = Rect()

        val x = 810F
        val y = 190F

        paint.apply {
            color = Color.GRAY
            style = Paint.Style.FILL
            textSize = 27F
        }

        val str2rotate = "Лучик солнца!"

        canvas?.save()
        canvas?.rotate(-45F, x + rect.exactCenterX(), y + rect.exactCenterY())
        canvas?.drawText(str2rotate, x, y, paint)

        canvas?.restore()

    }
}