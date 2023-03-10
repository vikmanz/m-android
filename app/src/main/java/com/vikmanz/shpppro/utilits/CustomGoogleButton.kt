package com.vikmanz.shpppro.utilits

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.vikmanz.shpppro.R
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates


class CustomGoogleButton(
    context: Context, attributesSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
) : View(context, attributesSet, defStyleAttr, defStyleRes) {

    private lateinit var text: String
    private var buttonCornerRadius by Delegates.notNull<Float>()
    private var logoShapeRadius by Delegates.notNull<Float>()
    private var logoShapeDiameter by Delegates.notNull<Float>()
    private var logoStrokeWidth by Delegates.notNull<Float>()
    private var textXOffsetFromLogo by Delegates.notNull<Float>()
    private var paddingHorizontal by Delegates.notNull<Float>()
    private var startXOfComposition by Delegates.notNull<Float>()
    private var centerYOfComposition by Delegates.notNull<Float>()
    private var textHeight by Delegates.notNull<Float>()
    private var logoSize by Delegates.notNull<Float>()

    private val buttonRect = RectF(0f, 0f, 0f, 0f)
    private var gLogoPath = RectF(0f, 0f, 0f, 0f)
    private val mTextBoundRect: Rect = Rect()

    private lateinit var buttonPaint: Paint
    private lateinit var textPaint: Paint
    private lateinit var logoPaint: Paint

    private var textColor by Delegates.notNull<Int>()
    private var buttonColor by Delegates.notNull<Int>()
    private var logoColor1 by Delegates.notNull<Int>()
    private var logoColor2 by Delegates.notNull<Int>()
    private var logoColor3 by Delegates.notNull<Int>()
    private var logoColor4 by Delegates.notNull<Int>()

    constructor(context: Context, attributesSet: AttributeSet?, defStyleAttr: Int) : this(
        context, attributesSet, defStyleAttr, R.style.defaultMyGButtonStyle
    )

    constructor(context: Context, attributesSet: AttributeSet?) : this(
        context, attributesSet, R.attr.myGoogleButtonStyle
    )

    constructor(context: Context) : this(context, null)

    init {
        initAttributes(attributesSet, defStyleAttr, defStyleRes)
        initPaints()
    }

    private fun initAttributes(
        attributesSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) {
        if (attributesSet != null) {
            initAttributesFromXml(attributesSet, defStyleAttr, defStyleRes)
        } else {
            initErrorAttributes()
        }
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttributesFromXml(
        attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.MyGButtonView, defStyleAttr, defStyleRes
        )
        text = typedArray.getString(R.styleable.MyGButtonView_mgbText).toString()
        textColor = getXmlColor(typedArray, R.styleable.MyGButtonView_mgbTextColor)
        buttonColor = getXmlColor(typedArray, R.styleable.MyGButtonView_mgbButtonColor)
        logoColor1 = getXmlColor(typedArray, R.styleable.MyGButtonView_mgbLogo1Color)
        logoColor2 = getXmlColor(typedArray, R.styleable.MyGButtonView_mgbLogo2Color)
        logoColor3 = getXmlColor(typedArray, R.styleable.MyGButtonView_mgbLogo3Color)
        logoColor4 = getXmlColor(typedArray, R.styleable.MyGButtonView_mgbLogo4Color)
        typedArray.close()
    }


    private fun getXmlColor(typedArray: TypedArray, colorIdInArray: Int) =
        typedArray.getColor(colorIdInArray, THEME_ERROR_COLOR)


    private fun initErrorAttributes() {
        text = ""
        textColor = THEME_ERROR_COLOR
        buttonColor = THEME_ERROR_COLOR
        logoColor1 = THEME_ERROR_COLOR
        logoColor2 = THEME_ERROR_COLOR
        logoColor3 = THEME_ERROR_COLOR
        logoColor4 = THEME_ERROR_COLOR
    }


    private fun initPaints() {
        buttonPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = buttonColor
            style = Paint.Style.FILL
        }
        logoPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = logoColor1
            style = Paint.Style.STROKE
        }
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = textColor
            style = Paint.Style.FILL
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newWidth = dpToPx(DEFAULT_BUTTON_WIDTH).toInt()
        val newHeight = dpToPx(DEFAULT_BUTTON_HEIGHT).toInt()

        val desiredWith = max(suggestedMinimumWidth, newWidth) + paddingLeft + paddingRight
        val desiredHeight = max(suggestedMinimumHeight, newHeight) + paddingTop + paddingBottom

        setMeasuredDimension(
            resolveSize(desiredWith, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val buttonNewWidth = (w - paddingLeft - paddingRight).toFloat()
        val buttonNewHeight = (h - paddingTop - paddingBottom).toFloat()

        with(buttonRect) {
            left = paddingLeft.toFloat()
            top = paddingTop.toFloat()
            right = left + buttonNewWidth
            bottom = top + buttonNewHeight
        }

        var widthOfAlLElements = getWidthOfAllElements(min(buttonNewWidth, buttonNewHeight))
        if (widthOfAlLElements > buttonRect.width()) widthOfAlLElements =
            calculateLessWidth(widthOfAlLElements, buttonRect.width())

        logoPaint.strokeWidth = logoStrokeWidth
        startXOfComposition = paddingHorizontal + (buttonNewWidth - widthOfAlLElements) / 2
        centerYOfComposition = h.toFloat() / 2

        buttonCornerRadius = min(buttonNewWidth, buttonNewHeight) / 100 * CORNER_ROUND_PERCENT

        gLogoPath.set(
            startXOfComposition,
            centerYOfComposition - logoShapeRadius,
            startXOfComposition + logoShapeDiameter,
            centerYOfComposition + logoShapeRadius
        )
    }

    private fun calculateLessWidth(widthOfAlLElements: Float, buttonWidth: Float): Float {
        val proportion = (buttonWidth / widthOfAlLElements) * CONTENT_REDUCER_PERCENT
        return getWidthOfAllElements(widthOfAlLElements * proportion)
    }

    private fun getWidthOfAllElements(widthSize: Float): Float {
        val referenceSize = widthSize / 100 * LOGO_RADIUS_IN_PERCENT
        textPaint.textSize = referenceSize
        textPaint.getTextBounds(text, 0, text.length, mTextBoundRect)
        val textBoxWidth: Float = textPaint.measureText(text)
        textHeight = mTextBoundRect.height().toFloat()

        logoShapeRadius = referenceSize
        textXOffsetFromLogo = referenceSize
        logoShapeDiameter = referenceSize * 2
        logoStrokeWidth = referenceSize / 2
        paddingHorizontal = referenceSize
        logoSize = logoShapeDiameter + logoStrokeWidth

        return paddingHorizontal + logoSize + textXOffsetFromLogo + textBoxWidth + paddingHorizontal
    }


    override fun onDraw(canvas: Canvas) {

        canvas.drawRoundRect(           // draw button
            buttonRect,
            buttonCornerRadius,
            buttonCornerRadius,
            buttonPaint
        )

        drawLogo(canvas)                 // draw logo

        canvas.drawText(                 // draw text
            text,
            startXOfComposition + logoSize + textXOffsetFromLogo,
            centerYOfComposition + textHeight / 2,
            textPaint
        )
    }

    private fun drawLogo(canvas: Canvas) {
        canvas.drawLine(
            gLogoPath.centerX(),
            gLogoPath.centerY(),
            gLogoPath.right + logoStrokeWidth / 2.0f,
            gLogoPath.centerY(),
            logoPaint.apply {
                color = logoColor1
            }
        )
        canvas.drawArc(gLogoPath, -10f, 55f, false, logoPaint.apply {
            color = logoColor1
        })
        canvas.drawArc(gLogoPath, 45f, 95f, false, logoPaint.apply {
            color = logoColor2
        })
        canvas.drawArc(gLogoPath, 140f, 95f, false, logoPaint.apply {
            color = logoColor3
        })
        canvas.drawArc(gLogoPath, 235f, 95f, false, logoPaint.apply {
            color = logoColor4
        })
    }


    private fun dpToPx(dp: Float): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
    )


    companion object {
        private const val CONTENT_REDUCER_PERCENT = 0.25f
        private const val LOGO_RADIUS_IN_PERCENT = 30f
        private const val CORNER_ROUND_PERCENT = 15f
        private const val DEFAULT_BUTTON_WIDTH = 200f
        private const val DEFAULT_BUTTON_HEIGHT = 50f

        private const val THEME_ERROR_COLOR = Color.RED
    }

}