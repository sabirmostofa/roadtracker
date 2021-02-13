package bd.hellofood.roadtracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Track::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TrackDatabase : RoomDatabase() {

    abstract fun getTrackDao(): TrackDAO
}