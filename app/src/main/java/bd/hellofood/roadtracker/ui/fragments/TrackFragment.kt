package bd.hellofood.roadtracker.ui.fragments

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bd.hellofood.roadtracker.R
import bd.hellofood.roadtracker.adapters.TrackAdapter
import bd.hellofood.roadtracker.db.Track
import bd.hellofood.roadtracker.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import bd.hellofood.roadtracker.other.SortType
import bd.hellofood.roadtracker.other.TrackingUtility
import bd.hellofood.roadtracker.ui.MainActivity
import bd.hellofood.roadtracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_run.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

@AndroidEntryPoint
class TrackFragment : Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks, TrackAdapter.ItemListener {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var trackAdapter: TrackAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        setupRecyclerView()

        when(viewModel.sortType) {
            SortType.DATE -> spFilter.setSelection(0)
            SortType.RUNNING_TIME -> spFilter.setSelection(1)
            SortType.DISTANCE -> spFilter.setSelection(2)
            SortType.AVG_SPEED -> spFilter.setSelection(3)

        }

        spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                when(pos) {
                    0 -> viewModel.sortTracks(SortType.DATE)
                    1 -> viewModel.sortTracks(SortType.RUNNING_TIME)
                    2 -> viewModel.sortTracks(SortType.DISTANCE)
                    3 -> viewModel.sortTracks(SortType.AVG_SPEED)

                }
            }
        }

        viewModel.runs.observe(viewLifecycleOwner, Observer {
            trackAdapter.submitList(it)
        })

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }

    private fun setupRecyclerView() = rvRuns.apply {
        trackAdapter = TrackAdapter()
        trackAdapter.setListener(this@TrackFragment)
        adapter = trackAdapter
        layoutManager = LinearLayoutManager(requireContext())

    }

    private fun requestPermissions() {
        if(TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onItemClicked(track: Track, action: String) {

        when(action){
            "delete" -> deleteTrack(track)
            "upload" -> uploadTrack(track)
        }

    }

    fun deleteTrack(track: Track){
        Timber.d("deleting now")
        viewModel.deleteRun(track);
        Toast.makeText(context, "Track has been deleted!",Toast.LENGTH_SHORT).show();

    }


    fun uploadTrack(track: Track){
        Timber.d("uploading now")

        Toast.makeText(context, "Track has been uploaded!",Toast.LENGTH_SHORT).show();
    }
}






