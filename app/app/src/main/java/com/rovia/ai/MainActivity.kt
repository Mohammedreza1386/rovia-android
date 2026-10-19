
package com.rovia.ai

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

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

        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setBackgroundColor(bg)
        root.setPadding(16, 16, 16, 12)

        val header = TextView(this)
        header.text = "Rovia"
        header.textSize = 30f
        header.setTextColor(Color.WHITE)
        header.gravity = Gravity.CENTER
        root.addView(header, LinearLayout.LayoutParams(-1, 55))

        val subtitle = TextView(this)
        subtitle.text = "دستیار هوش مصنوعی شخصی تو"
        subtitle.textSize = 14f
        subtitle.setTextColor(Color.LTGRAY)
        subtitle.gravity = Gravity.CENTER
        root.addView(subtitle, LinearLayout.LayoutParams(-1, 36))

        val scroll = ScrollView(this)
        scroll.isFillViewport = true

        chat = LinearLayout(this)
        chat.orientation = LinearLayout.VERTICAL
        scroll.addView(chat)

        root.addView(
            scroll,
            LinearLayout.LayoutParams( -1, 0, 1f)
        )

        addMessage("سلام! من Rovia هستم. چطور می‌تونم کمکت کنم؟", false)

        val composer = LinearLayout(this)
        composer.orientation = LinearLayout.HORIZONTAL
        composer.gravity = Gravity.CENTER_VERTICAL

        input = EditText(this)
        input.hint = "پیامت رو بنویس..."
        input.setTextColor(Color.WHITE)
        input.setHintTextColor(Color.LTGRAY)
        input.textSize = 15f
        input.setSingleLine(true)
        input.setPadding(12, 8, 12, 8)
        input.setBackgroundColor(panel)

        composer.addView(
            input,
            LinearLayout.LayoutParams(0, 52, 1f)
        )

        val send = Button(this)
        send.text = "ارسال"
        send.setTextColor(Color.WHITE)
        send.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(accent)
        )

        send.setOnClickListener {
            val message = input.text.toString().trim()

            if (message.isNotEmpty()) {
                addMessage(message, true)
                input.text.clear()
                addMessage(
                    "اتصال هوش مصنوعی هنوز فعال نشده است.",
                    false
                )
            }
        }

        composer.addView(
            send,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, 52
            )
        )

        root.addView(
            composer,
            LinearLayout.LayoutParams(-1, 62)
        )

        setContentView(root)
    }

    private fun addMessage(text: String, user: Boolean) {
        val bubble = TextView(this)
        bubble.text = text
        bubble.textSize = 15f
        bubble.setTextColor(Color.WHITE)
        bubble.setPadding(14, 12, 14, 12)
        bubble.setBackgroundColor(if (user) accent else panel)

        val row = LinearLayout(this)
        row.orientation = LinearLayout.HORIZONTAL
        row.gravity = if (user) Gravity.END else Gravity.START
        row.setPadding(0, 6, 0, 6)

        row.addView(
            bubble,
            LinearLayout.LayoutParams(
                (resources.displayMetrics.widthPixels * 0.75f).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        chat.addView(row)
    }
}
