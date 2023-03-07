package com.vikmanz.shpppro.utilits

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.vikmanz.shpppro.R
import java.lang.Integer.max
import kotlin.properties.Delegates

class CustomGoogleButton(
    context: Context, attributesSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
) : View(context, attributesSet, defStyleAttr, defStyleRes) {


    private lateinit var text: String
    private var textColor by Delegates.notNull<Int>()
    private var buttonColor by Delegates.notNull<Int>()
    private var gLogoColor1 by Delegates.notNull<Int>()
    private var gLogoColor2 by Delegates.notNull<Int>()
    private var gLogoColor3 by Delegates.notNull<Int>()
    private var gLogoColor4 by Delegates.notNull<Int>()

    private val buttonRect = RectF(0f, 0f, 0f, 0f)

    private lateinit var googleButtonPaint: Paint
    private lateinit var googleTextPaint: Paint
    private lateinit var googleLogoPaint1: Paint
    private lateinit var googleLogoPaint2: Paint
    private lateinit var googleLogoPaint3: Paint
    private lateinit var googleLogoPaint4: Paint


    constructor(context: Context, attributesSet: AttributeSet?, defStyleAttr: Int) : this(
        context, attributesSet, defStyleAttr, R.style.defaultCustomGoogleButtonStyle
    )

    constructor(context: Context, attributesSet: AttributeSet?) : this(
        context, attributesSet, R.attr.customGoogleButtonStyle
    )

    constructor(context: Context) : this(context, null)

    init {
        if (attributesSet != null) {
            initAttributes(attributesSet, defStyleAttr, defStyleRes)
        } else {
            initDefaultColors()
        }
        initPaints()
        if (isInEditMode) {

        }
    }

    private fun initPaints() {

        googleButtonPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        googleButtonPaint.color = DEFAULT_BUTTON_COLOR
        googleButtonPaint.style = Paint.Style.FILL

        googleTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        googleTextPaint.color = DEFAULT_TEXT_COLOR

        googleTextPaint.textSize = 50.0f;
        googleTextPaint.strokeWidth = 2.0f;
        googleTextPaint.style = Paint.Style.FILL;

        googleLogoPaint1 = Paint(Paint.ANTI_ALIAS_FLAG)
        googleLogoPaint1.color = gLogoColor1
        googleLogoPaint1.style = Paint.Style.STROKE
        googleLogoPaint1.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)

        googleLogoPaint2 = Paint(Paint.ANTI_ALIAS_FLAG)
        googleLogoPaint2.color = gLogoColor2
        googleLogoPaint2.style = Paint.Style.STROKE
        googleLogoPaint2.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)

        googleLogoPaint3 = Paint(Paint.ANTI_ALIAS_FLAG)
        googleLogoPaint3.color = gLogoColor3
        googleLogoPaint3.style = Paint.Style.STROKE
        googleLogoPaint3.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)

        googleLogoPaint4 = Paint(Paint.ANTI_ALIAS_FLAG)
        googleLogoPaint4.color = gLogoColor4
        googleLogoPaint4.style = Paint.Style.STROKE
        googleLogoPaint4.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)
    }


    @SuppressLint("CustomViewStyleable")
    private fun initAttributes(
        attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.CustomGoogleButtonView, defStyleAttr, defStyleRes
        )

        text = typedArray.getString(R.styleable.CustomGoogleButtonView_cgbText).toString()
        textColor =
            typedArray.getColor(R.styleable.CustomGoogleButtonView_cgbTextColor, DEFAULT_TEXT_COLOR)
        buttonColor = typedArray.getColor(
            R.styleable.CustomGoogleButtonView_cgbButtonColor, DEFAULT_BUTTON_COLOR
        )
        gLogoColor1 = typedArray.getColor(
            R.styleable.CustomGoogleButtonView_cgbLogo1Color, DEFAULT_LOGO_1_COLOR
        )
        gLogoColor2 = typedArray.getColor(
            R.styleable.CustomGoogleButtonView_cgbLogo2Color, DEFAULT_LOGO_2_COLOR
        )
        gLogoColor3 = typedArray.getColor(
            R.styleable.CustomGoogleButtonView_cgbLogo3Color, DEFAULT_LOGO_3_COLOR
        )
        gLogoColor4 = typedArray.getColor(
            R.styleable.CustomGoogleButtonView_cgbLogo4Color, DEFAULT_LOGO_4_COLOR
        )

        typedArray.recycle()
    }

    private fun initDefaultColors() {
        textColor = DEFAULT_TEXT_COLOR
        buttonColor = DEFAULT_BUTTON_COLOR
        gLogoColor1 = DEFAULT_LOGO_1_COLOR
        gLogoColor2 = DEFAULT_LOGO_2_COLOR
        gLogoColor3 = DEFAULT_LOGO_3_COLOR
        gLogoColor4 = DEFAULT_LOGO_4_COLOR
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minimumWith = suggestedMinimumWidth + paddingLeft + paddingRight
        val minimumHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        val buttonWidthInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DESIRED_WIDTH, resources.displayMetrics).toInt() + paddingLeft + paddingRight
        val buttonHeightInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DESIRED_HEIGHT, resources.displayMetrics).toInt() + paddingTop + paddingBottom

        val desiredWith = max(minimumWith, buttonWidthInPixels)
        val desiredHeight = max(minimumHeight, buttonHeightInPixels)

        setMeasuredDimension(
            resolveSize(desiredWith, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateSize()
    }

    private fun updateSize() {
        val safeWith = width - paddingLeft - paddingRight
        val safeHeight = height - paddingTop - paddingBottom

        val buttonCurrentWidth = DESIRED_WIDTH
        val buttonCurrentHeight = DESIRED_HEIGHT

        buttonRect.left = paddingLeft + ((safeWith - buttonCurrentWidth) / 2)
        buttonRect.top = paddingTop + ((safeHeight - buttonCurrentHeight) / 2)
        buttonRect.right = buttonRect.left + buttonCurrentWidth
        buttonRect.bottom = buttonRect.top + buttonCurrentHeight
    }

    private val mTextBoundRect: Rect = Rect()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (haveInvalidValues()) return

        val centerX: Float
        val centerY: Float
        val width: Float = width.toFloat()
        val height: Float = height.toFloat()
        centerX = width / 2
        centerY = height / 2

        canvas.drawRoundRect(buttonRect, 50f, 50f, googleButtonPaint);

        googleTextPaint.getTextBounds(text, 0, text.length, mTextBoundRect);

        val textWidth: Float = googleTextPaint.measureText(text);
        val textHeight: Float = mTextBoundRect.height().toFloat();

        canvas.drawText(DEFAULT_TEXT,
            centerX - (textWidth / 2f),
            centerY + (textHeight /2f),
            googleTextPaint
        );
        //canvas.drawText(DEFAULT_TEXT, 20f, 200f, googleTextPaint)

    }

    private fun haveInvalidValues(): Boolean{
        if (buttonRect.width() <= 0 || buttonRect.height() <=0) return true
        return false
    }

    companion object {

        const val DEFAULT_TEXT = "GOOGLE"

        const val DEFAULT_TEXT_COLOR = Color.BLACK
        const val DEFAULT_BUTTON_COLOR = Color.LTGRAY

        const val DEFAULT_LOGO_1_COLOR = Color.YELLOW
        const val DEFAULT_LOGO_2_COLOR = Color.GRAY
        const val DEFAULT_LOGO_3_COLOR = Color.CYAN
        const val DEFAULT_LOGO_4_COLOR = Color.MAGENTA


        const val DESIRED_WIDTH = 800f
        const val DESIRED_HEIGHT = 100f
    }

}