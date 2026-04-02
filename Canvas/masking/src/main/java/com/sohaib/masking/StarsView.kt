package com.sohaib.masking

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.graphics.scale

class StarsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var maskImageOne: Bitmap? = null
    private var maskImageTwo: Bitmap? = null
    private var originalImageOne: Bitmap? = null
    private var originalImageTwo: Bitmap? = null

    private val imagePaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val mode by lazy { PorterDuffXfermode(PorterDuff.Mode.DST_ATOP) }

    private val endX by lazy { measuredWidth.toFloat() }
    private val endY by lazy { measuredHeight.toFloat() }

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.StarsView, 0, 0)
        maskImageOne = getBigBitmap(typedArray.getResourceId(R.styleable.StarsView_maskImageOne, 0))
        maskImageTwo = getSmallBitmap(typedArray.getResourceId(R.styleable.StarsView_maskImageTwo, 0))
        originalImageOne = getBigBitmap(typedArray.getResourceId(R.styleable.StarsView_originalImageOne, 0))
        originalImageTwo = getSmallBitmap(typedArray.getResourceId(R.styleable.StarsView_originalImageTwo, 0))
    }

    private fun getBigBitmap(resId: Int): Bitmap {
        val bitmap = BitmapFactory.decodeResource(resources, resId)
        return bitmap.scale(500, 500)
    }

    private fun getSmallBitmap(resId: Int): Bitmap {
        val bitmap = BitmapFactory.decodeResource(resources, resId)
        return bitmap.scale(150, 150)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (maskImageOne == null || maskImageTwo == null || originalImageOne == null || originalImageTwo == null) {
            Toast.makeText(context, "something is missing", Toast.LENGTH_SHORT).show()
            return
        }
        // Big Image
        canvas.drawBitmap(originalImageOne!!, endX / 2 - originalImageOne!!.width / 2, endY / 2 - originalImageOne!!.height / 2, imagePaint)
        canvas.drawBitmap(maskImageOne!!, endX / 2 - maskImageOne!!.width / 2, endY / 2 - maskImageOne!!.height / 2, imagePaint)

        // Small Image      // DST - SRC
        canvas.drawBitmap(maskImageTwo!!, 400f, 370f, imagePaint)
        imagePaint.xfermode = this.mode
        canvas.drawBitmap(originalImageTwo!!, 400f, 370f, imagePaint)
    }
}