package com.sohaib.fingerprintauthentication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.sohaib.fingerprintauthentication.databinding.ActivityMainBinding

@Suppress("SameParameterValue")
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authenticateUser(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        //authenticateUser(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        //authenticateUser(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
    }

    /**
     * @param biometricWeak: they are of three types
     *  -> BiometricManager.Authenticators.BIOMETRIC_WEAK
     *  -> BiometricManager.Authenticators.BIOMETRIC_STRONG
     *  -> BiometricManager.Authenticators.DEVICE_CREDENTIAL
     */

    private fun authenticateUser(biometricWeak: Int) {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(biometricWeak)) {
            BiometricManager.BIOMETRIC_SUCCESS -> showBiometricPrompt()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> showToast("There is no suitable hardware")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> showToast("The hardware is unavailable. Try again later")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> showToast("No biometric or device credential is enrolled")
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> showToast("A security vulnerability has been discovered with one or more hardware sensors")
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> showToast("The specified options are incompatible with the current Android version")
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> showToast("Unable to determine whether the user can authenticate")
        }
    }

    private fun showBiometricPrompt() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.app_name))
            .setSubtitle("Authenticate via Biometric")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .setNegativeButtonText("Cancel")
            .setConfirmationRequired(true)
            .build()
        val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this), authCallback)
        biometricPrompt.authenticate(promptInfo)
    }

    private val authCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            showToast("Successful")
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            showToast("Unrecoverable error => $errString")
        }

        override fun onAuthenticationFailed() {
            showToast("Could not recognise the user")
        }
    }

    private fun showToast(message: String) {
        binding.tvTextMain.text = message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}