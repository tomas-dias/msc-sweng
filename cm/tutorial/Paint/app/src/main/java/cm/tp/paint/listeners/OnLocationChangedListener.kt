package cm.tp.paint.listeners

import com.google.android.gms.location.LocationResult

interface OnLocationChangedListener {
    fun onLocationChanged(locationResult: LocationResult)
}