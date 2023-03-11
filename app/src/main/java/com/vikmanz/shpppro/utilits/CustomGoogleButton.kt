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

/**
 * Custom Google Button view class.
 */
class CustomGoogleButton(
    context: Context, attributesSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
) : View(context, attributesSet, defStyleAttr, defStyleRes) {

    /**
     * Constants
     */
    companion object {
        // Radius in % from lesser edge of button.
        private const val LOGO_RADIUS_IN_PERCENT = 30f
        // Radius of button corners in % from lesser edge of button.
        private const val CORNER_ROUND_PERCENT = 15f
        // Text scale. 1=100%, 2=200%, 0.5=50%
        private const val TEXT_SIZE_SCALAR = 1.75f
        // Default button width.
        private const val DEFAULT_BUTTON_WIDTH = 200f
        // Default button height.
        private const val DEFAULT_BUTTON_HEIGHT = 50f
        // Scale if elements bigger than button.
        private const val CONTENT_REDUCER_PERCENT = 0.25f
        // Default error colors if xml attributes are null.
        private const val THEME_ERROR_COLOR = Color.RED
    }

    /**
     * XML attributes.
     */
    private lateinit var text: String
    private var textColor by Delegates.notNull<Int>()
    private var buttonColor by Delegates.notNull<Int>()
    private var logoColor1 by Delegates.notNull<Int>()
    private var logoColor2 by Delegates.notNull<Int>()
    private var logoColor3 by Delegates.notNull<Int>()
    private var logoColor4 by Delegates.notNull<Int>()

    /**
     * Paints.
     */
    private lateinit var buttonPaint: Paint
    private lateinit var textPaint: Paint
    private lateinit var logoPaint: Paint

    /**
     * Shapes.
     */
    private val buttonRect = RectF(0f, 0f, 0f, 0f)
    private var gLogoPath = RectF(0f, 0f, 0f, 0f)
    private val mTextBoundRect: Rect = Rect()

    /**
     * Variables for calculating size and paint elements on theirs positions.
     */
    private var startXOfComposition by Delegates.notNull<Float>()
    private var logoSize by Delegates.notNull<Float>()
    private var textXOffsetFromLogo by Delegates.notNull<Float>()
    private var logoShapeDiameter by Delegates.notNull<Float>()
    private var buttonCornerRadius by Delegates.notNull<Float>()
    private var logoStrokeWidth by Delegates.notNull<Float>()
    private var referenceSize by Delegates.notNull<Float>()

    /**
     * Constructors.
     */
    constructor(context: Context, attributesSet: AttributeSet?, defStyleAttr: Int) : this(
        context, attributesSet, defStyleAttr, R.style.defaultMyGButtonStyle
    )

    constructor(context: Context, attributesSet: AttributeSet?) : this(
        context, attributesSet, R.attr.myGoogleButtonStyle
    )

    constructor(context: Context) : this(context, null)


    /**
     * Initialization of view.
     */
    init {
        initAttributes(attributesSet, defStyleAttr, defStyleRes)
        initPaints()
    }

    /**
     * Initialization of view attributes.
     */
    private fun initAttributes(
        attributesSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) {
        if (attributesSet != null) {
            initAttributesFromXml(attributesSet, defStyleAttr, defStyleRes)
        } else {
            initErrorAttributes()
        }
    }

    /**
     * Initialization of view attributes from XML attributes.
     */
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

    /**
     * Get color from XML attribute.
     */
    private fun getXmlColor(typedArray: TypedArray, colorIdInArray: Int) =
        typedArray.getColor(colorIdInArray, THEME_ERROR_COLOR)

    /**
     * Initialization of view attributes if XML attributes are null (and this view haven't theme)
     */
    private fun initErrorAttributes() {
        text = ""
        textColor = THEME_ERROR_COLOR
        buttonColor = THEME_ERROR_COLOR
        logoColor1 = THEME_ERROR_COLOR
        logoColor2 = THEME_ERROR_COLOR
        logoColor3 = THEME_ERROR_COLOR
        logoColor4 = THEME_ERROR_COLOR
    }

    /**
     * Initialization of paints.
     */
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

    /**
     * Measure desired size of view.
     */
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

    /**
     * Change size and coordinates of all elements on canvas after size of view was changed.
     */
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

        val paddingHorizontal = referenceSize
        startXOfComposition = paddingHorizontal + (buttonNewWidth - widthOfAlLElements) / 2


        val logoShapeRadius = referenceSize
        gLogoPath.set(
            startXOfComposition,
            h / 2 - logoShapeRadius,
            startXOfComposition + logoShapeDiameter,
            h / 2 + logoShapeRadius
        )

        buttonCornerRadius = min(buttonNewWidth, buttonNewHeight) / 100 * CORNER_ROUND_PERCENT
        logoPaint.strokeWidth = logoStrokeWidth
    }

    /**
     * Calculates less width of all elements for that they do not go beyond the button.
     */
    private fun calculateLessWidth(widthOfAlLElements: Float, buttonWidth: Float): Float {
        val proportion = (buttonWidth / widthOfAlLElements) * CONTENT_REDUCER_PERCENT
        return getWidthOfAllElements(widthOfAlLElements * proportion)
    }

    /**
     * Calculates width of all elements on button.
     */
    private fun getWidthOfAllElements(edgeLength: Float): Float {
        referenceSize = edgeLength / 100 * LOGO_RADIUS_IN_PERCENT
        textPaint.textSize = referenceSize * TEXT_SIZE_SCALAR
        textPaint.getTextBounds(text, 0, text.length, mTextBoundRect)
        val textBoxWidth: Float = textPaint.measureText(text)

        textXOffsetFromLogo = referenceSize
        val paddingHorizontal = referenceSize
        logoShapeDiameter = referenceSize * 2
        logoStrokeWidth = referenceSize / 2
        logoSize = logoShapeDiameter + logoStrokeWidth

        return paddingHorizontal + logoSize + textXOffsetFromLogo + textBoxWidth + paddingHorizontal
    }

    /**
     * Draw elements on canvas.
     */
    override fun onDraw(canvas: Canvas) {
        drawButton(canvas)
        drawLogo(canvas)
        drawText(canvas)
    }

    /**
     * Draw button on canvas.
     */
    private fun drawButton(canvas: Canvas) {
        canvas.drawRoundRect(           // draw button
            buttonRect,
            buttonCornerRadius,
            buttonCornerRadius,
            buttonPaint
        )
    }

    /**
     * Draw logo on canvas.
     */
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

    /**
     * Draw text on canvas.
     */
    private fun drawText(canvas: Canvas) {
        val textHeight = mTextBoundRect.height().toFloat()
        val centerYOfComposition = height / 2
        canvas.drawText(                 // draw text
            text,
            startXOfComposition + logoSize + textXOffsetFromLogo,
            centerYOfComposition + textHeight / 2,
            textPaint
        )
    }

    /**
     * Convert DP to PX.
     */
    private fun dpToPx(dp: Float): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
    )

}