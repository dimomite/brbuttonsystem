package org.dimomite.brbuttonsystem.remotecontrol

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import org.dimomite.brbuttonsystem.R
import org.dimomite.brbuttonsystem.databinding.LayoutFloatingRemoteBinding
import timber.log.Timber

class FloatingRemoteWindow(private val ctx: Context) {
    private val wm: WindowManager = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var view: View? = null

    private val canDraw: Boolean

    private var isMoving = false
    private var initialX: Int = 0
    private var initialY: Int = 0
    private var startTouchX: Float = 0.0f
    private var startTouchY: Float = 0.0f

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            canDraw = Settings.canDrawOverlays(ctx)
            Timber.i("DBG: FloatingRemoteWindow: checked canDraw: $canDraw")
        } else {
            canDraw = true
            Timber.i("DBG: FloatingRemoteWindow: canDraw: $canDraw")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initFloatingWindow() {
        if (view != null) return

        val inflater = LayoutInflater.from(ctx)
        val binding = LayoutFloatingRemoteBinding.inflate(inflater, null, false)
        view = binding.root

        val type: Int =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            }

        val res = ctx.resources
        val params = WindowManager.LayoutParams(
            res.getDimensionPixelSize(R.dimen.floating_window_width),
            res.getDimensionPixelSize(R.dimen.floating_window_height),
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        binding.remoteControlLayout.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    this.initialX = params.x
                    this.initialY = params.y
                    this.startTouchX = motionEvent.rawX
                    this.startTouchY = motionEvent.rawY
                    this.isMoving = true
                    true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    this.isMoving = false
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    if (isMoving) {
                        params.x = initialX + (motionEvent.rawX - startTouchX).toInt()
                        params.y = initialY + (motionEvent.rawY - startTouchY).toInt()
                        wm.updateViewLayout(view, params)
                    }
                    true
                }

                else -> false
            }
        }

        binding.remoteControlTopButton.setOnClickListener { handleButtonClick(RemoteControlWidgetProvider.ACTION_1) }
        binding.remoteControlBotLeftButton.setOnClickListener { handleButtonClick(RemoteControlWidgetProvider.ACTION_2) }
        binding.remoteControlBotRightButton.setOnClickListener { handleButtonClick(RemoteControlWidgetProvider.ACTION_3) }

        wm.addView(view, params)
    }

    private fun handleButtonClick(action: String) {
        val intent = RemoteControlWidgetProvider.buildIntent(ctx, action)
        ctx.sendOrderedBroadcast(intent, null)
    }

    fun destroyFloatingWindow() {
        if (view != null) {
            wm.removeView(view)
            view = null
        }
    }

}
