package tech.vector.ivanlearning

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

class SongApplication: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}