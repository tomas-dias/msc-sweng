package cm.tp.paint.listeners

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class FusedLocation private constructor(var context: Context): LocationCallback() {

    private val TAG = FusedLocation::class.java.simpleName

    private val TIME_BETWEEN_UPDATES = 5* 1000L

    private var locationRequest: LocationRequest? = null

    private var client = FusedLocationProviderClient(context)


    init {
        locationRequest = LocationRequest()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = TIME_BETWEEN_UPDATES

        val locationSettingsRequest = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!).build()

        LocationServices.getSettingsClient(context).checkLocationSettings(locationSettingsRequest)
    }

    companion object{
        private  var listener: OnLocationChangedListener? = null
        private var instance: FusedLocation? = null

        fun registerListener(listener: OnLocationChangedListener){
            Companion.listener = listener
        }

        fun unregistenerListener(){
            listener = null
        }

        fun notifyListeners(locationResult: LocationResult){
            listener?.onLocationChanged(locationResult)
        }

        fun start(context: Context){
            instance = if (instance == null) FusedLocation(context) else instance
            instance?.startLocationUpdates()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }else{
            client.requestLocationUpdates(locationRequest,this, Looper.myLooper())
        }
    }

    override fun onLocationResult(locationResult: LocationResult?) {

        locationResult?.let { notifyListeners(it) }
        super.onLocationResult(locationResult)
    }
}