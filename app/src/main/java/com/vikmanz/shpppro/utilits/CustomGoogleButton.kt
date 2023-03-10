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

    private var minimumSizeValue by Delegates.notNull<Float>()
    private var buttonCornerRadius by Delegates.notNull<Float>()
    private var logoShapeRadius by Delegates.notNull<Float>()
    private var logoShapeDiameter by Delegates.notNull<Float>()
    private var logoStrokeWidth by Delegates.notNull<Float>()
    private var textSize by Delegates.notNull<Float>()
    private var textMargin by Delegates.notNull<Float>()
    private var startXOfComposition by Delegates.notNull<Float>()
    private var centerYOfComposition by Delegates.notNull<Float>()
    private var textHeight by Delegates.notNull<Float>()


    private val mTextBoundRect: Rect = Rect()

    private lateinit var googleButtonPaint: Paint
    private lateinit var googleTextPaint: Paint
    private lateinit var googleLogoPaintLine: Paint
    private lateinit var googleLogoMainPaint: Paint

    private var textColor by Delegates.notNull<Int>()
    private var buttonColor by Delegates.notNull<Int>()
    private var gLogoColor1 by Delegates.notNull<Int>()
    private var gLogoColor2 by Delegates.notNull<Int>()
    private var gLogoColor3 by Delegates.notNull<Int>()
    private var gLogoColor4 by Delegates.notNull<Int>()

    private val buttonRect = RectF(0f, 0f, 0f, 0f)
    private var gLogoPath = RectF(0f, 0f, 0f, 0f)


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
//        if (isInEditMode) {
//            init(context)
//        }
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
        gLogoColor1 = getXmlColor(typedArray, R.styleable.MyGButtonView_mgbLogo1Color)
        gLogoColor2 = getXmlColor(typedArray, R.styleable.MyGButtonView_mgbLogo2Color)
        gLogoColor3 = getXmlColor(typedArray, R.styleable.MyGButtonView_mgbLogo3Color)
        gLogoColor4 = getXmlColor(typedArray, R.styleable.MyGButtonView_mgbLogo4Color)
        typedArray.recycle()
    }


    private fun getXmlColor(typedArray: TypedArray, colorIdInArray: Int) =
        typedArray.getColor(colorIdInArray, THEME_ERROR_COLOR)


    private fun initErrorAttributes() {
        text = THEME_ERROR_TEXT
        textColor = THEME_ERROR_COLOR
        buttonColor = THEME_ERROR_COLOR
        gLogoColor1 = THEME_ERROR_COLOR
        gLogoColor2 = THEME_ERROR_COLOR
        gLogoColor3 = THEME_ERROR_COLOR
        gLogoColor4 = THEME_ERROR_COLOR
    }


    private fun initPaints() {
        googleButtonPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = buttonColor
            style = Paint.Style.FILL
        }
        googleLogoMainPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = gLogoColor1
            style = Paint.Style.STROKE
        }
        googleLogoPaintLine = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = gLogoColor1
            style = Paint.Style.STROKE
            //xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)
        }
        googleTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = textColor
            style = Paint.Style.FILL
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        onMeasureVariant1(widthMeasureSpec, heightMeasureSpec)
        //onMeasureVariant2(widthMeasureSpec, heightMeasureSpec)
    }

    private fun onMeasureVariant2(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val initSizeW = resolveDefaultSize(widthMeasureSpec)
        val initSizeH = resolveDefaultSize(heightMeasureSpec)
        setMeasuredDimension(initSizeW, initSizeH)
    }

    private fun onMeasureVariant1(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newWidth = dpToPx(DEFAULT_BUTTON_WIDTH).toInt()
        val newHeight = dpToPx(DEFAULT_BUTTON_HEIGHT).toInt()

        val desiredWith = max(suggestedMinimumWidth, newWidth) + paddingLeft + paddingRight
        val desiredHeight = max(suggestedMinimumHeight, newHeight) + paddingTop + paddingBottom

        setMeasuredDimension(
            resolveSize(desiredWith, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }


    fun resolveDefaultSize(spec: Int): Int = when (MeasureSpec.getMode(spec)) {
        MeasureSpec.UNSPECIFIED -> dpToPx(DEFAULT_BUTTON_WIDTH).toInt() //def size
        MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec)
        MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
        else -> MeasureSpec.getSize(spec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateSize(w, h)
    }

    private fun updateSize(w: Int, h: Int) {
        val buttonNewWidth = (w - paddingLeft - paddingRight).toFloat()
        val buttonNewHeight = (h - paddingTop - paddingBottom).toFloat()

        with(buttonRect) {
            left = paddingLeft.toFloat()
            top = paddingTop.toFloat()
            right = paddingLeft.toFloat() + buttonNewWidth
            bottom = paddingTop.toFloat() + buttonNewHeight
        }

        minimumSizeValue = min(buttonNewWidth, buttonNewHeight)

        logoShapeRadius = minimumSizeValue / 100 * LOGO_RADIUS_IN_PERCENT
        textSize = logoShapeRadius
        googleTextPaint.textSize = textSize
        googleTextPaint.getTextBounds(text, 0, text.length, mTextBoundRect)
        val textBoxWidth: Float = googleTextPaint.measureText(text)
        textHeight = mTextBoundRect.height().toFloat()
        var wightOfAlLElements = textBoxWidth + logoShapeRadius * 3.25f

        logoShapeDiameter = logoShapeRadius * 2
        logoStrokeWidth = logoShapeRadius / 2
        textMargin = logoShapeRadius

        googleLogoMainPaint.strokeWidth = logoStrokeWidth

//        while (wightOfAlLElements > buttonNewWidth) {
//            minimumSizeValue = minimumSizeValue / 100 * (100 - CONTENT_REDUCER_STEP_PERCENT)
//            wightOfAlLElements = getReducedWidth()
//        }

        startXOfComposition = paddingLeft.toFloat() + (buttonNewWidth - wightOfAlLElements) / 2
        centerYOfComposition = h.toFloat() / 2

        buttonCornerRadius = min(buttonNewWidth, buttonNewHeight) / 100 * CORNER_ROUND_PERCENT

        gLogoPath.set(
            startXOfComposition,
            centerYOfComposition - logoShapeRadius,
            startXOfComposition + logoShapeDiameter,
            centerYOfComposition + logoShapeRadius
        )
    }

    private fun getReducedWidth(): Float {
        logoShapeRadius = minimumSizeValue / 100 * LOGO_RADIUS_IN_PERCENT
        textSize = logoShapeRadius
        googleTextPaint.getTextBounds(text, 0, text.length, mTextBoundRect)
        val textBoxWidth: Float = googleTextPaint.measureText(text)
        textHeight = mTextBoundRect.height().toFloat()
        return textBoxWidth + logoShapeRadius * 3.25f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (haveInvalidValues()) return

        canvas.drawRoundRect(
            buttonRect, buttonCornerRadius, buttonCornerRadius, googleButtonPaint
        ) // draw button

        canvas.drawLine(
            gLogoPath.centerX(),
            gLogoPath.centerY(),
            gLogoPath.right + logoStrokeWidth / 2.0f * 0.9f,
            gLogoPath.centerY(),
            googleLogoMainPaint.apply {
                color = gLogoColor1
            }
        )

        canvas.drawArc(gLogoPath, -10f, 55f, false, googleLogoMainPaint.apply {
            color = gLogoColor1
        })

        canvas.drawArc(gLogoPath, 45f, 95f, false, googleLogoMainPaint.apply {
            color = gLogoColor2
        })

        canvas.drawArc(gLogoPath, 140f, 95f, false, googleLogoMainPaint.apply {
            color = gLogoColor3
        })

        canvas.drawArc(gLogoPath, 235f, 95f, false, googleLogoMainPaint.apply {
            color = gLogoColor4
        })

        val startXOfText = gLogoPath.right + logoStrokeWidth / 2 + textMargin
        canvas.drawText(
            text, startXOfText, centerYOfComposition + textHeight / 2, googleTextPaint
        )
    }

    private fun haveInvalidValues(): Boolean {
        if (buttonRect.width() <= 0 || buttonRect.height() <= 0) return true
        return false
    }

    private fun dpToPx(dp: Float): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
    )


    companion object {
        private const val CONTENT_REDUCER_STEP_PERCENT = 10f        // 15: 100% -> 85%
        private const val LOGO_RADIUS_IN_PERCENT = 30f
        private const val CORNER_ROUND_PERCENT = 15f
        private const val DEFAULT_BUTTON_WIDTH = 60f
        private const val DEFAULT_BUTTON_HEIGHT = 20f

        private const val THEME_ERROR_TEXT = "ERROR! CHECK THEME!"
        private const val THEME_ERROR_COLOR = Color.RED
    }

}