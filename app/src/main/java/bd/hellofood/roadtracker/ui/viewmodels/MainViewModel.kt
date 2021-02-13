package bd.hellofood.roadtracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bd.hellofood.roadtracker.db.Track
import bd.hellofood.roadtracker.other.SortType
import bd.hellofood.roadtracker.repositories.MainRepository

import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
): ViewModel() {

    private val runsSortedByDate = mainRepository.getAllTracksSortedByDate()
    private val runsSortedByDistance = mainRepository.getAllTracksSortedByDistance()
    private val runsSortedByCaloriesBurned = mainRepository.getAllTracksSortedByCaloriesBurned()
    private val runsSortedByTimeInMillis = mainRepository.getAllTracksSortedByTimeInMillis()
    private val runsSortedByAvgSpeed = mainRepository.getAllTracksSortedByAvgSpeed()

    val runs = MediatorLiveData<List<Track>>()

    var sortType = SortType.DATE

    init {

        runs.addSource(runsSortedByDate) { result ->
            if(sortType == SortType.DATE) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByAvgSpeed) { result ->
            if(sortType == SortType.AVG_SPEED) {
                result?.let { runs.value = it }
            }
        }

        runs.addSource(runsSortedByDistance) { result ->
            if(sortType == SortType.DISTANCE) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByTimeInMillis) { result ->
            if(sortType == SortType.RUNNING_TIME) {
                result?.let { runs.value = it }
            }
        }
    }

    fun sortTracks(sortType: SortType) = when(sortType) {
        SortType.DATE -> runsSortedByDate.value?.let { runs.value = it }
        SortType.RUNNING_TIME -> runsSortedByTimeInMillis.value?.let { runs.value = it }
        SortType.AVG_SPEED -> runsSortedByAvgSpeed.value?.let { runs.value = it }
        SortType.DISTANCE -> runsSortedByDistance.value?.let { runs.value = it }

    }.also {
        this.sortType = sortType
    }

    fun insertRun(run: Track) = viewModelScope.launch {
        mainRepository.insertTrack(run)
        
    }

    fun deleteRun(run: Track) = viewModelScope.launch {
        mainRepository.deleteTrack(run)
    }
}















