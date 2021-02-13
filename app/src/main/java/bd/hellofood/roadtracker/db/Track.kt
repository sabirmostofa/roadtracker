package bd.hellofood.roadtracker.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng


data class Point(
    var lat: Double,
    var lng: Double
)

@Entity(tableName = "tracks_table")
data class Track(
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0,
    var name: String? =  null,
    var points: List<Point>? = null

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}