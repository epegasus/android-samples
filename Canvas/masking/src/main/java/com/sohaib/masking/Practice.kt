package com.sohaib.masking

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.graphics.scale

class Practice @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var maskImage: Bitmap? = null
    private var originalImage: Bitmap? = null

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.Practice, 0, 0)
        maskImage = getBitmap(typedArray.getResourceId(R.styleable.Practice_maskImage, 0))
        originalImage = getBitmap(typedArray.getResourceId(R.styleable.Practice_originalImage, 0))
    }

    private fun getBitmap(resId: Int): Bitmap {
        val bitmap = BitmapFactory.decodeResource(resources, resId)
        return bitmap.scale(300, 200)
    }

    /* --------------------------------------- Paints --------------------------------------- */

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 50f
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.YELLOW
        textSize = 50f
    }

    private val imagePaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val mode by lazy { PorterDuffXfermode(PorterDuff.Mode.DST_IN) }

    /* --------------------------------------- Points --------------------------------------- */

    private val endX by lazy { measuredWidth.toFloat() }
    private val endY by lazy { measuredHeight.toFloat() }

    private val floats by lazy { arrayOf(0f, 0f, 0f, endY, 0f, endY, endX, endY, endX, endY, endX, 0f, endX, 0f, 0f, 0f) }
    private val linesPoints by lazy { FloatArray(floats.size) { floats[it] } }

    private val path by lazy {
        Path().apply {
            moveTo(10f, endY)          // Bottom-Left
            lineTo(30f, endY - 50)
            lineTo(50f, endY)
            lineTo(0f, endY - 30f)
            lineTo(60f, endY - 30f)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Drawing background
        canvas.drawColor(Color.LTGRAY)

        // Drawing Text (header)
        canvas.drawText("Welcome on Canvas!", 0f, paint.textSize, paint)

        // Drawing Line (diagonally)
        canvas.drawLine(0f, 0f, endX, endY, linePaint)

        // Drawing Lines (border)
        canvas.drawLines(linesPoints, linePaint)

        // Drawing Path (star)
        canvas.drawPath(path, paint)

        // Masking
        if (maskImage == null || originalImage == null) {
            Toast.makeText(context, "$maskImage - $originalImage", Toast.LENGTH_SHORT).show()
            return
        }

        maskImage?.let { mask ->
            originalImage?.let { original ->
                canvas.drawBitmap(original, endX / 2 - original.width / 2, endY / 2 - original.height / 2, imagePaint)
                imagePaint.xfermode = mode
                canvas.drawBitmap(mask, endX / 2 - mask.width / 2, endY / 2 - mask.height / 2, imagePaint)
            }
        }
    }
}