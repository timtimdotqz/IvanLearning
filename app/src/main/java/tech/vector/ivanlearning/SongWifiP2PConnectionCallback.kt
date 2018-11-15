package tech.vector.ivanlearning

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import com.wifiscanner.listener.WifiP2PConnectionCallback

interface SongWifiP2PConnectionCallback: WifiP2PConnectionCallback {

    override fun onDataReceiving() {

    }

    override fun onInitiateDiscovery() {

    }

    override fun onDataReceivedSuccess(p0: String?) {

    }

    override fun onPeerConnectionFailure() {

    }

    override fun onPeerDisconnectionFailure() {

    }

    override fun onPeerAvailable(p0: WifiP2pDeviceList?)

    override fun onDataTransferredSuccess() {

    }

    override fun onDiscoverySuccess() {

    }

    override fun onDiscoveryFailure() {

    }

    override fun onPeerConnectionSuccess() {

    }

    override fun onDataTransferring() {

    }

    override fun onDataReceivedFailure() {

    }

    override fun onPeerDisconnectionSuccess() {

    }

    override fun onDataTransferredFailure() {

    }

    override fun onPeerStatusChanged(p0: WifiP2pDevice?)
}