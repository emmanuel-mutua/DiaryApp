package com.emmutua.Diary.presentation.screens.auth

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.emmutua.Diary.utils.Constants.CLIENT_ID
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes

@Composable
fun rememberOneTapSignInState(): OneTapSignInState {
    return remember {
        OneTapSignInState()
    }
}


@Composable
fun OneTapSignIn(
    state: OneTapSignInState,
    onTokenIdReceived: (String) -> Unit,
    onDialogDismissed: (String) -> Unit
) {
    val TAG = "OneTapCompose"
    val activity = LocalContext.current as Activity
    //Handling user response
    val activityLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    var oneTapClient: SignInClient = Identity.getSignInClient(activity)
                    val credentials = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val tokenId = credentials.googleIdToken
                    if (tokenId != null) {
                        onTokenIdReceived(tokenId)
                        state.close()
                    }
                } else {
                    onDialogDismissed("DialogClosed")
                    state.close()
                }
            } catch (e: ApiException) {
                Log.e(TAG, "${e.message}")
                when (e.statusCode) {
                    CommonStatusCodes.CANCELED -> {
                        onDialogDismissed("Dialog Cancelled")
                    }

                    CommonStatusCodes.NETWORK_ERROR -> {
                        onDialogDismissed("Network Error")
                        state.close()
                    }

                    else -> {
                        onDialogDismissed(e.message.toString())
                        state.close()
                    }
                }
            }
        }
    LaunchedEffect(key1 = state.opened) {
        if (state.opened) {
            signsIn(
                activity = activity,
                clientId = CLIENT_ID,
                nonce = null,
                launchActivityResult = {
                    activityLauncher.launch(it)
                },
                onError = onDialogDismissed,
                state = OneTapSignInState()
            )
        }
    }
}

private fun signsIn(
    activity: Activity,
    clientId: String,
    nonce: String?,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    onError: (String) -> Unit,
    state: OneTapSignInState
) {

    val signInRequest = signInSignUpRequest(
        clientId = clientId,
        nonce = nonce,
        filterAuthorisedAccounts = true,
        autoSelectEnabled = true
    )
    beginSignInSignUpRequest(
        activity = activity,
        signInSignUpRequest = signInRequest,
        launchActivityResult = launchActivityResult,
        onError = onError,
        onFailureListener = {
            signUp(
                activity = activity,
                clientId = clientId,
                nonce = null,
                launchActivityResult = launchActivityResult,
                onError = onError,
                state = state
            )
        }
    )

}

private fun signUp(
    activity: Activity,
    clientId: String,
    nonce: String?,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    onError: (String) -> Unit,
    state: OneTapSignInState
) {

    val signUpRequest = signInSignUpRequest(
        clientId = clientId,
        nonce = nonce,
        filterAuthorisedAccounts = false,
        autoSelectEnabled = false
    )
    beginSignInSignUpRequest(
        activity = activity,
        signInSignUpRequest = signUpRequest,
        launchActivityResult = launchActivityResult,
        onError = onError,
        onFailureListener = {
            state.close()
            onError("Google Account not Found.")
            Log.e("OneTapCompose", "${it.message}")
        }
    )

}

private fun signInSignUpRequest(
    clientId: String,
    nonce: String?,
    filterAuthorisedAccounts: Boolean,
    autoSelectEnabled: Boolean
): BeginSignInRequest = BeginSignInRequest.builder()
    .setGoogleIdTokenRequestOptions(
        BeginSignInRequest.GoogleIdTokenRequestOptions.Builder()
            .setSupported(true)
            .setNonce(nonce)
            .setServerClientId(clientId)
            .setFilterByAuthorizedAccounts(filterAuthorisedAccounts)
            .build()

    )
    .setAutoSelectEnabled(autoSelectEnabled)
    .build()

private fun beginSignInSignUpRequest(
    activity: Activity,
    signInSignUpRequest: BeginSignInRequest,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    onError: (String) -> Unit,
    onFailureListener: (Exception) -> Unit
) {
    val oneTapClient = Identity.getSignInClient(activity)
    oneTapClient.beginSignIn(signInSignUpRequest)
        .addOnSuccessListener { result ->
            try {
                launchActivityResult(
                    IntentSenderRequest.Builder(
                        result.pendingIntent.intentSender
                    ).build()
                )
            } catch (e: Exception) {
                onError(e.message.toString())
                Log.e("OneTapCompose", "${e.message}")
            }
        }
        .addOnFailureListener { onFailureListener(it) }

}