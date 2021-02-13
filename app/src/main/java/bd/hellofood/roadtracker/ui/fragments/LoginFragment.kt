package bd.hellofood.roadtracker.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import bd.hellofood.roadtracker.R
import bd.hellofood.roadtracker.other.Constants.KEY_FIRST_TIME_TOGGLE
import bd.hellofood.roadtracker.other.Constants.KEY_NAME
import bd.hellofood.roadtracker.other.Constants.KEY_WEIGHT
import bd.hellofood.roadtracker.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_setup.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {

    @Inject
    lateinit var sharedPref: SharedPreferences

    @set:Inject
    var isFirstAppOpen = true

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        //redirect if user already logged in
        if(auth.currentUser !=null) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOptions
            )
        }


        tvContinue.setOnClickListener {
            //val success = writePersonalDataToSharedPref()
            loginToFirebase()
            /*
            if(success) {
               // findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            } else {
                Snackbar.make(requireView(), "Please enter all the fields", Snackbar.LENGTH_SHORT).show()
            }
            */

        }
    }

    private fun loginToFirebase(){
        auth.signInWithEmailAndPassword(etName.text.toString(), etWeight.text.toString())
            .addOnCompleteListener(activity as AppCompatActivity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithEmail:success")
                    Toast.makeText(context, "Authentication success!",
                        Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_setupFragment_to_runFragment)

                } else {
                    // If sign in fails, display a message to the user.
                    Timber.w("signInWithEmail:failure")

                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_LONG).show()

                    // ...
                }

                // ...
            }



    }

    private fun writePersonalDataToSharedPref(): Boolean {
        val name = etName.text.toString()
        val weight = etWeight.text.toString()
        if(name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPref.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()
        val toolbarText = "RoadTracker"
        requireActivity().tvToolbarTitle.text = toolbarText
        return true
    }

}