package com.palsaloid.dicodingstoryapp.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.widget.Button
import androidx.appcompat.widget.AppCompatEditText
import com.palsaloid.dicodingstoryapp.R

class CustomPasswordEditText : AppCompatEditText{

    private var button: Button? = null

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleSet: Int): super(context,
        attrs, defStyleSet
    ) {
        init()
    }

    fun setButton(button: Button) {
        this.button = button
        button.isEnabled = (text?.length ?: 0) >= 8
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                // Do Nothing
            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                if (sequence != null) {
                    when {
                        sequence.length < 8 -> {
                            error = "Karakter kurang dari 8"
                            button?.isEnabled = false
                        }

                        sequence.isEmpty() -> {
                            error = "Field ini tidak boleh kosong"
                            button?.isEnabled = false
                        }

                        else -> {
                            button?.isEnabled = true
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do Nothing
            }
        })
    }
}