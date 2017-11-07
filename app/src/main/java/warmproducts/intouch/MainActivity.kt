package warmproducts.intouch

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import java.io.ByteArrayOutputStream
import java.io.InputStream
import android.os.Vibrator




class MainActivity : AppCompatActivity() {

    private var mTextMessage: TextView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage!!.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mTextMessage!!.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mTextMessage = findViewById<View>(R.id.message)
        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView?
        navigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

//        val channelId = getString(R.string.default_notification_channel_id)
//        val channelName = "From console"
//        val notificationManager = getSystemService(NotificationManager::class.java)
//        notificationManager!!.createNotificationChannel(NotificationChannel(channelId,
//                channelName, NotificationManager.IMPORTANCE_LOW))

        setContentView(SampleView(this));

        val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vb.vibrate(100000);
    }

    private class SampleView(context: Context) : View(context) {
        private var mMovie: Movie? = null
        private var mMovieStart: Long = 0

        init {
            isFocusable = true

            var inputStream: java.io.InputStream
            inputStream = context.resources.openRawResource(R.raw.heart_animation_repost)

            if (DECODE_STREAM) {
                mMovie = Movie.decodeStream(inputStream)
            } else {
                val array = streamToBytes(inputStream)
                mMovie = Movie.decodeByteArray(array, 0, array.size)
            }
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawColor(-0x333334)

            val p = Paint()
            p.setAntiAlias(true)

            val now = android.os.SystemClock.uptimeMillis()
            if (mMovieStart == 0L) {   // first time
                mMovieStart = now
            }
            if (mMovie != null) {
                var dur = mMovie!!.duration()
                if (dur == 0) {
                    dur = 1000
                }
                val relTime = ((now - mMovieStart) % dur).toInt()
                mMovie!!.setTime(relTime)
                mMovie!!.draw(canvas, (width/2 - mMovie!!.width()/2).toFloat(),
                        (height/2 - mMovie!!.height()/2).toFloat())
                invalidate()


            }
        }

        companion object {

            //Set to false to use decodeByteArray
            private val DECODE_STREAM = true

            private fun streamToBytes(`is`: InputStream): ByteArray {
                val os = ByteArrayOutputStream(1024)
                val buffer = ByteArray(1024)
                var len: Int
                try {
                    len = `is`.read(buffer)
                    while (len >= 0) {
                        os.write(buffer, 0, len)
                        len = `is`.read(buffer)
                    }
                } catch (e: java.io.IOException) {
                }

                return os.toByteArray()
            }
        }
    }

}
