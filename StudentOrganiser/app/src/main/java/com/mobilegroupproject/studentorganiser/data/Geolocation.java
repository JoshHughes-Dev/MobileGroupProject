package com.mobilegroupproject.studentorganiser.data;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by alex on 13/05/2016.
 */
public class Geolocation {
/*
    private boolean checkGooglePlayServices(){
        int checkGooglePlayServices = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(mContext);
        if (checkGooglePlayServices != ConnectionResult.SUCCESS) {
		*//*
		* Google Play Services is missing or update is required
		*  return code could be
		* SUCCESS,
		* SERVICE_MISSING, SERVICE_VERSION_UPDATE_REQUIRED,
		* SERVICE_DISABLED, SERVICE_INVALID.
		*//*
            GooglePlayServicesUtil.getErrorDialog(checkGooglePlayServices,
                    mContext, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();

            return false;
        }

        return true;
    }*/


}
