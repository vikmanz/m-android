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
    private lateinit var googleLogoPaintLine: Paint
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

        googleTextPaint.textSize = DEFAULT_LOGO_PATH_WITH * 10;
        googleTextPaint.strokeWidth = DEFAULT_TEXT_WITH;
        googleTextPaint.style = Paint.Style.FILL;

        googleLogoPaintLine = Paint(Paint.ANTI_ALIAS_FLAG)
        googleLogoPaintLine.color = gLogoColor1
        googleLogoPaintLine.style = Paint.Style.STROKE
        googleLogoPaintLine.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            DEFAULT_LOGO_PATH_WITH,
            resources.displayMetrics
        )
        //googleLogoPaintLine.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)


        googleLogoPaint1 = Paint(Paint.ANTI_ALIAS_FLAG)
        googleLogoPaint1.color = gLogoColor1
        googleLogoPaint1.style = Paint.Style.STROKE
        googleLogoPaint1.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            DEFAULT_LOGO_PATH_WITH,
            resources.displayMetrics
        )

        googleLogoPaint2 = Paint(Paint.ANTI_ALIAS_FLAG)
        googleLogoPaint2.color = gLogoColor2
        googleLogoPaint2.style = Paint.Style.STROKE
        googleLogoPaint2.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            DEFAULT_LOGO_PATH_WITH,
            resources.displayMetrics
        )

        googleLogoPaint3 = Paint(Paint.ANTI_ALIAS_FLAG)
        googleLogoPaint3.color = gLogoColor3
        googleLogoPaint3.style = Paint.Style.STROKE
        googleLogoPaint3.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            DEFAULT_LOGO_PATH_WITH,
            resources.displayMetrics
        )

        googleLogoPaint4 = Paint(Paint.ANTI_ALIAS_FLAG)
        googleLogoPaint4.color = gLogoColor4
        googleLogoPaint4.style = Paint.Style.STROKE
        googleLogoPaint4.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            DEFAULT_LOGO_PATH_WITH,
            resources.displayMetrics
        )
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

        val newWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            buttonRect.width(),
            resources.displayMetrics
        ).toInt() + paddingLeft + paddingRight

        val newHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            buttonRect.height(),
            resources.displayMetrics
        ).toInt()

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

        buttonRect.left = paddingLeft.toFloat()
        buttonRect.top = paddingTop.toFloat()
        buttonRect.right = paddingLeft + buttonNewWidth.toFloat()
        buttonRect.bottom = buttonRect.top + buttonNewHeight


    }

    private val mTextBoundRect: Rect = Rect()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (haveInvalidValues()) return

        val btnWidth: Float = width.toFloat()
        val btnHeight: Float = height.toFloat()

        val radius = DEFAULT_LOGO_PATH_WITH * 5
        val centerX = btnWidth / 2
        val centerY = btnHeight / 2

        canvas.drawRoundRect(buttonRect, 50f, 50f, googleButtonPaint);

        googleTextPaint.getTextBounds(text, 0, text.length, mTextBoundRect);

        val textWidth: Float = googleTextPaint.measureText(text);
        val textHeight: Float = mTextBoundRect.height().toFloat();



        val wightOfAlLElements = textWidth + DEFAULT_TEXT_WITH + radius * 2 + DEFAULT_LOGO_PATH_WITH + DEFAULT_MARGIN_BETWEEN_LOGO_AND_TEXT
        val startXOfComposition = (btnWidth - wightOfAlLElements) / 2
        val startXOfGLogo = startXOfComposition + DEFAULT_LOGO_PATH_WITH / 2 + radius

        val oval = RectF()
        oval.set(
            startXOfGLogo - radius,
            centerY - radius,
            startXOfGLogo + radius,
            centerY + radius
        );

        canvas.drawArc(oval, -11.5F, 70F, false, googleLogoPaint1)
        canvas.drawArc(oval, 45F, 95F, false, googleLogoPaint2)
        canvas.drawArc(oval, 135F, 95F, false, googleLogoPaint3)
        canvas.drawArc(oval, 225F, 95F, false, googleLogoPaint4)
        canvas.drawLine(
            oval.centerX() + radius + DEFAULT_LOGO_PATH_WITH,
            oval.centerY(),
            oval.centerX(),
            oval.centerY(),
            googleLogoPaintLine
        );

        val startXOfText = startXOfGLogo + DEFAULT_LOGO_PATH_WITH / 2 + radius + DEFAULT_MARGIN_BETWEEN_LOGO_AND_TEXT
        canvas.drawText(
            DEFAULT_TEXT,
            startXOfText,
            centerY + (textHeight / 2f),
            googleTextPaint
        );

    }

    private fun haveInvalidValues(): Boolean {
        if (buttonRect.width() <= 0 || buttonRect.height() <= 0) return true
        return false
    }

    companion object {

        const val DEFAULT_TEXT = "GOOGLE"

        const val DEFAULT_TEXT_COLOR = Color.BLACK
        const val DEFAULT_BUTTON_COLOR = Color.LTGRAY

        const val DEFAULT_LOGO_1_COLOR = Color.MAGENTA
        const val DEFAULT_LOGO_2_COLOR = Color.CYAN
        const val DEFAULT_LOGO_3_COLOR = Color.GRAY
        const val DEFAULT_LOGO_4_COLOR = Color.YELLOW

        const val DEFAULT_LOGO_PATH_WITH = 5f
        const val DEFAULT_TEXT_WITH = 2f
        const val DEFAULT_MARGIN_BETWEEN_LOGO_AND_TEXT = DEFAULT_LOGO_PATH_WITH * 10

        const val DESIRED_WIDTH = 1000f
        const val DESIRED_HEIGHT = 200f
    }

}