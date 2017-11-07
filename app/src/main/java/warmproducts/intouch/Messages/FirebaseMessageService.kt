package warmproducts.intouch.Messages

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.TextView
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by olga on 06.11.17.
 */
class FirebaseMessageService : FirebaseInstanceIdService() {

    override fun onTokenRefresh()
    {
        // Get updated InstanceID token.
        var refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }
}
