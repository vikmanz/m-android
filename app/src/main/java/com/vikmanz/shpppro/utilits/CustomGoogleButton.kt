package com.vikmanz.shpppro.utilits

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.google.android.ads.mediationtestsuite.utils.AppInfoUtil.init
import com.vikmanz.shpppro.R
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates


class CustomGoogleButton(
    context: Context, attributesSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
) : View(context, attributesSet, defStyleAttr, defStyleRes) {

    private lateinit var text: String

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
            strokeWidth = convertDpToPx(DEFAULT_LOGO_PATH_WITH)
            //xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)
        }
        googleTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = textColor
            textSize = convertDpToPx(DEFAULT_TEXT_SCALAR)
            strokeWidth = convertDpToPx(DEFAULT_TEXT_WITH)
            style = Paint.Style.FILL
        }
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(
            buttonRect, CORNER_ROUND_VALUE, CORNER_ROUND_VALUE, googleButtonPaint
        ) // draw button
        drawLogoAndText(canvas)

    }

    private fun drawLogoAndText(canvas: Canvas) {
        if (haveInvalidValues()) return

        val btnWidth: Float = width.toFloat()
        val btnHeight: Float = height.toFloat()

        val radius = min(btnWidth, btnHeight) / 10
        val diameter = radius * 2
        val strokeWith = convertDpToPx(DEFAULT_LOGO_PATH_WITH)
        val strokeTextWith = convertDpToPx(DEFAULT_TEXT_WITH)

        //val canvasCenterX = btnWidth / 2
        val canvasCenterY = btnHeight / 2

        googleTextPaint.getTextBounds(text, 0, text.length, mTextBoundRect)

        val textBoxWidth: Float = googleTextPaint.measureText(text)
        val textBoxHeight: Float = mTextBoundRect.height().toFloat()

        val wightOfAlLElements = textBoxWidth + strokeTextWith + diameter + strokeWith

        val startXOfComposition = (btnWidth - wightOfAlLElements) / 2

        gLogoPath.set(
            startXOfComposition + radius,
            canvasCenterY - radius,
            startXOfComposition + diameter,
            canvasCenterY + radius
        )

        googleLogoMainPaint.strokeWidth = strokeWith


        canvas.drawArc(gLogoPath, 0f, 45f, false, googleLogoMainPaint.apply {
            color = gLogoColor1
        })

        gLogoPath.set(
            gLogoPath.left,
            gLogoPath.top,
            gLogoPath.right,
            gLogoPath.bottom
        )

        canvas.drawArc(gLogoPath, 45f, 95f, false, googleLogoMainPaint.apply {
            color = gLogoColor2
        })

        canvas.drawArc(gLogoPath, 140f, 95f, false, googleLogoMainPaint.apply {
            color = gLogoColor3
        })

        canvas.drawArc(gLogoPath, 235f, 95f, false, googleLogoMainPaint.apply {
            color = gLogoColor4
        })

        canvas.drawLine(
            gLogoPath.centerX(),
            gLogoPath.centerY(),
            gLogoPath.right + googleLogoPaintLine.strokeWidth,
            gLogoPath.centerY(),
            googleLogoPaintLine
        )

        val startXOfText = gLogoPath.left + strokeWith / 2 + radius
        canvas.drawText(
            text, startXOfText, canvasCenterY + (textBoxHeight / 2f), googleTextPaint
        )
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newWidth = convertDpToPx(buttonRect.width()).toInt() + paddingLeft + paddingRight
        val newHeight = convertDpToPx(buttonRect.height()).toInt()
        val desiredWith = max(suggestedMinimumWidth, newWidth) + paddingTop + paddingBottom
        val desiredHeight = max(suggestedMinimumHeight, newHeight) + paddingTop + paddingBottom
        setMeasuredDimension(
            resolveSize(desiredWith, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateSize(w, h)
    }

    private fun updateSize(w: Int, h: Int) {
        val buttonNewWidth = w - paddingLeft - paddingRight
        val buttonNewHeight = h - paddingTop - paddingBottom

        //val isVertical = buttonNewWidth < buttonNewHeight

        buttonRect.left = paddingLeft.toFloat()
        buttonRect.top = paddingTop.toFloat()
        buttonRect.right = paddingLeft + buttonNewWidth.toFloat()
        buttonRect.bottom = buttonRect.top + buttonNewHeight
    }


    private fun haveInvalidValues(): Boolean {
        if (buttonRect.width() <= 0 || buttonRect.height() <= 0) return true
        return false
    }

    private fun convertDpToPx(inputDpValue: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, inputDpValue, resources.displayMetrics
        )
    }


    companion object {
        private const val DEFAULT_LOGO_SCALAR = 1f
        private const val DEFAULT_TEXT_SCALAR = DEFAULT_LOGO_SCALAR * 10
        private const val DEFAULT_LOGO_PATH_WITH = 5f
        private const val DEFAULT_TEXT_WITH = 2f

        private const val CORNER_ROUND_VALUE = 50f

        private const val THEME_ERROR_TEXT = "ERROR! CHECK THEME!"
        private const val THEME_ERROR_COLOR = Color.RED
    }

}