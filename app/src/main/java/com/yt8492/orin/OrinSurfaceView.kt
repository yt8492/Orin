package com.yt8492.orin

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.SoundPool
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.min

class OrinSurfaceView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private var mHolder = holder
    private val orin by lazy { BitmapFactory.decodeResource(resources, R.drawable.orin) }
    private val bou by lazy { BitmapFactory.decodeResource(resources, R.drawable.bou) }
    private val soundPool by lazy {
        SoundPool.Builder()
            .setAudioAttributes(AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
            .build()
    }
    private val bgm = soundPool.load(context, R.raw.sound, 1)

    init {
        mHolder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        val canvas = mHolder.lockCanvas()
        val scale = min(width.toFloat() / orin.width, height.toFloat() / orin.height)
        canvas.scale(scale, scale)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(orin, 0f, 0f, null)
        mHolder.unlockCanvasAndPost(canvas)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val canvas = mHolder.lockCanvas()
                val scale = min(width.toFloat() / bou.width, height.toFloat() / bou.height)
                canvas.scale(scale, scale)
                canvas.drawColor(Color.WHITE)
                canvas.drawBitmap(bou, 0f, 0f, null)
                mHolder.unlockCanvasAndPost(canvas)
                soundPool.play(bgm, 1f, 1f, 1, 0, 1f)
            }
            MotionEvent.ACTION_UP -> {
                val canvas = mHolder.lockCanvas()
                val scale = min(width.toFloat() / orin.width, height.toFloat() / orin.height)
                canvas.scale(scale, scale)
                canvas.drawColor(Color.WHITE)
                canvas.drawBitmap(orin, 0f, 0f, null)
                mHolder.unlockCanvasAndPost(canvas)
                soundPool.autoPause()

            }
        }
        return true
    }
}