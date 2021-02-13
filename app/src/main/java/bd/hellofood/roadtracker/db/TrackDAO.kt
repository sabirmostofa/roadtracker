package bd.hellofood.roadtracker.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TrackDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(run: Track)

    @Delete
    suspend fun deleteTrack(run: Track)

    @Query("SELECT * FROM tracks_table ORDER BY timestamp DESC")
    fun getAllTracksSortedByDate(): LiveData<List<Track>>

    @Query("SELECT * FROM tracks_table ORDER BY timeInMillis DESC")
    fun getAllTracksSortedByTimeInMillis(): LiveData<List<Track>>

    @Query("SELECT * FROM tracks_table ORDER BY caloriesBurned DESC")
    fun getAllTracksSortedByCaloriesBurned(): LiveData<List<Track>>

    @Query("SELECT * FROM tracks_table ORDER BY avgSpeedInKMH DESC")
    fun getAllTracksSortedByAvgSpeed(): LiveData<List<Track>>

    @Query("SELECT * FROM tracks_table ORDER BY distanceInMeters DESC")
    fun getAllTracksSortedByDistance(): LiveData<List<Track>>

    @Query("SELECT SUM(timeInMillis) FROM tracks_table")
    fun getTotalTimeInMillis(): LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM tracks_table")
    fun getTotalCaloriesBurned(): LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM tracks_table")
    fun getTotalDistance(): LiveData<Int>

    @Query("SELECT AVG(avgSpeedInKMH) FROM tracks_table")
    fun getTotalAvgSpeed(): LiveData<Float>
}










