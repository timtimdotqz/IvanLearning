package tech.vector.ivanlearning

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.wifiscanner.service.WifiP2PServiceImpl
import kotlinx.android.synthetic.main.activity_main.*
import nz.co.trademe.covert.Covert
import java.io.File


class MainActivity : AppCompatActivity(), SongWifiP2PConnectionCallback {

    val songs = mutableListOf<File>()
    var player = MediaPlayer()
    var secondPlayer = MediaPlayer()
    lateinit var wifiP2PService: WifiP2PServiceImpl


    override fun onPeerAvailable(list: WifiP2pDeviceList?) {
        list?.deviceList?.toTypedArray()?.forEach {
            wifiP2PService.connectDevice(it)
        }
    }

    override fun onPeerStatusChanged(device: WifiP2pDevice?) {
        if(device?.status == WifiP2pDevice.CONNECTED) {
            wifiP2PService.startDataTransfer("message")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(), 0)
        } else {
            init()
        }

        wifiP2PService = WifiP2PServiceImpl.Builder()
            .setSender(this)
            .setWifiP2PConnectionCallback(this)
            .build()
        wifiP2PService.onCreate()

        wifiP2PService
    }

    override fun onDestroy() {
        super.onDestroy()
        wifiP2PService.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        wifiP2PService.onResume()
    }

    override fun onStop() {
        super.onStop()
        wifiP2PService.onStop()
    }

    private fun init() {
        initData()

        val adapter = SongAdapter(ArrayList(songs.map { it.name }))
        adapter.setOnItemClickListener { position ->
            player = MediaPlayer()
            player.setDataSource(songs[position].path)
            player.prepareAsync()

            secondPlayer = MediaPlayer()
            secondPlayer.setDataSource(songs[position].path)
            secondPlayer.prepareAsync()

            player.setOnPreparedListener { player ->
                player.seekTo(etMillis.text.toString().toInt())

                secondPlayer.setOnPreparedListener { secondPlayer ->
                    secondPlayer.start()
                    player.start()
                }
            }
        }

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter


        val covertConfig = Covert.Config(
            iconRes = android.R.drawable.ic_delete,
            iconDefaultColorRes = android.R.color.holo_red_light,
            actionColorRes = R.color.colorPrimary
        )

        Covert.with(covertConfig)
            .setIsActiveCallback {
                return@setIsActiveCallback true
            }
            .doOnSwipe { viewHolder, _ ->
                if (player.isPlaying) {
                    player.stop()
                }
                if(secondPlayer.isPlaying) {
                    secondPlayer.stop()
                }
            }
            .attachTo(list)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if ((grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            init()
        } else {
            finish()
        }
    }

    private fun initData() {
        val storage = Environment.getExternalStorageDirectory()
        val songs = File(storage, "Songs")

        songs.listFiles().forEach {
            this.songs.add(it)
        }

        this.songs.sortBy { it.name }
    }

}
