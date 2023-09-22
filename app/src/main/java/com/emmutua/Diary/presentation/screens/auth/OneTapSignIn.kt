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
                    }
                }
            }
        }
    LaunchedEffect(key1 = state.opened) {
        if (state.opened) {
            val oneTapClient = Identity.getSignInClient(activity)
            var signInRequest: BeginSignInRequest = BeginSignInRequest.Builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.Builder()
                        .setSupported(true)
                        .setServerClientId(CLIENT_ID)
                        .setNonce(null)
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                .setAutoSelectEnabled(true)
                .build()

            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(activity) { result ->
                    try {
                        activityLauncher.launch(
                            IntentSenderRequest.Builder(
                                result.pendingIntent.intentSender
                            ).build()
                        )
                    } catch (e: Exception) {
                        onDialogDismissed(e.message.toString())
                        state.close()
                    }
                }
                .addOnFailureListener(activity) {
                    onDialogDismissed(it.message.toString())
                    state.close()
                    Log.e(TAG, "${it.message}")
                }
        }
    }
}

private fun signUp(
    activity: Activity,
    clientId: String,
    nonce: String?,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    onError: (String) -> Unit
) {
    val oneTapClient = Identity.getSignInClient(activity)
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setNonce(nonce)
                .setServerClientId(clientId)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    oneTapClient.beginSignIn(signInRequest)
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
        .addOnFailureListener {
            onError("Google Account not Found.")
            Log.e("OneTapCompose", "${it.message}")
        }
}