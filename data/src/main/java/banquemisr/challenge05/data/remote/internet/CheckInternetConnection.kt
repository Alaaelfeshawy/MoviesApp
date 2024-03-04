package banquemisr.challenge05.data.remote.internet

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

class CheckInternetConnection @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : ICheckInternetConnection {

    override fun isNetworkAvailable(): Boolean {
        return checkNetworkState()
    }

    private fun checkNetworkState(): Boolean {
        val network = connectivityManager.activeNetwork
        val connection = connectivityManager.getNetworkCapabilities(network)
        return connection?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true || connection?.hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI
        ) == true || connection?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    }
}