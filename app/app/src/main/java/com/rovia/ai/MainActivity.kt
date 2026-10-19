package com.rovia.ai

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MainActivity : Activity() {

    private val bg = Color.rgb(11, 16, 32)
    private val panel = Color.rgb(23, 30, 51)
    private val accent = Color.rgb(124, 108, 255)
    private lateinit var chat: LinearLayout
    private lateinit var input: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.systemUiVisibility = 0

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
            setPadding(20, 18, 20, 14)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        val header = TextView(this).apply {
            text = "Rovia"
            textSize = 30f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setTypeface(null, 1)
        }
        root.addView(header, params(
            ViewGroup.LayoutParams.MATCH_PARENT, 54
        ))

        val subtitle = TextView(this).apply {
            text = "دستیار هوش مصنوعی شخصی تو"
            textSize = 14f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }
        root.addView(subtitle, params(
            ViewGroup.LayoutParams.MATCH_PARENT, 32
        ))

        val scroll = ScrollView(this).apply {
            isFillViewport = true
            clipToPadding = false
            setPadding(0, 16, 0, 16)
        }

        chat = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
        scroll.addView(chat)

        root.addView(scroll, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))

        addMessage(
            "سلام! من Rovia هستم. چطور می‌تونم کمکت کنم؟",
            false
        )

        val composer = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(0, 10, 0, 0)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        input = EditText(this).apply {
            hint = "پیامت رو بنویس..."
            textSize = 15f
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
            setSingleLine(true)
            setPadding(14, 10, 14, 10)
            setBackgroundColor(panel)
        }

        composer.addView(input, LinearLayout.LayoutParams(
            0, 54, 1f
        ).apply {
            marginEnd = 8
        })

        val send = Button(this).apply {
            text = "ارسال"
            setTextColor(Color.WHITE)
            setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(accent)
            )
        }

        send.setOnClickListener {
            val message = input.text.toString().trim()
            if (message.isNotEmpty()) {
                addMessage(message, true)
                input.text.clear()
                addMessage(
                    "برای پاسخ واقعی، اتصال هوش مصنوعی را در مرحله بعد فعال می‌کنیم.",
                    false
                )
            }
        }

        composer.addView(send, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, 54
        ))

        root.addView(composer, params(
            ViewGroup.LayoutParams.MATCH_PARENT, 64
        ))

        setContentView(root)
    }

    private fun addMessage(text: String, user: Boolean) {
        val bubble = TextView(this).apply {
            this.text = text
            textSize = 15f
            setTextColor(Color.WHITE)
            setPadding(16, 12, 16, 12)
            setBackgroundColor(if (user) accent else panel)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = if (user) Gravity.START else Gravity.END
            setPadding(0, 6, 0, 6)
            addView(bubble, LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                width = (resources.displayMetrics.widthPixels * 0.72f).toInt()
            })
        }

        chat.addView(row)
    }

    private fun params(width: Int, height: Int) =
        LinearLayout.LayoutParams(width, height)
}
