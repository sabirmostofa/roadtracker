package bd.hellofood.roadtracker.repositories

import bd.hellofood.roadtracker.db.Track
import bd.hellofood.roadtracker.db.TrackDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val trackDao: TrackDAO
) {
    suspend fun insertTrack(run: Track) = trackDao.insertTrack(run)

    suspend fun deleteTrack(run: Track) = trackDao.deleteTrack(run)

    fun getAllTracksSortedByDate() = trackDao.getAllTracksSortedByDate()

    fun getAllTracksSortedByDistance() = trackDao.getAllTracksSortedByDistance()

    fun getAllTracksSortedByTimeInMillis() = trackDao.getAllTracksSortedByTimeInMillis()

    fun getAllTracksSortedByAvgSpeed() = trackDao.getAllTracksSortedByAvgSpeed()

    fun getAllTracksSortedByCaloriesBurned() = trackDao.getAllTracksSortedByCaloriesBurned()

    fun getTotalAvgSpeed() = trackDao.getTotalAvgSpeed()

    fun getTotalDistance() = trackDao.getTotalDistance()

    fun getTotalCaloriesBurned() = trackDao.getTotalCaloriesBurned()

    fun getTotalTimeInMillis() = trackDao.getTotalTimeInMillis()
}