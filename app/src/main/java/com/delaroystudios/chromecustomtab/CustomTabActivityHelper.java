package com.delaroystudios.chromecustomtab;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.telecom.Connection;

import org.chromium.customtabsclient.shared.CustomTabsHelper;
import org.chromium.customtabsclient.shared.ServiceConnection;
import org.chromium.customtabsclient.shared.ServiceConnectionCallback;

import java.util.List;

/**
 * Created by delaroy on 2/19/17.
 */
public class CustomTabActivityHelper implements ServiceConnectionCallback {

    private CustomTabsSession mcustomTabsSession;
    private CustomTabsClient mClient;
    private CustomTabsServiceConnection mConnection;
    private ConnectionCallback mConnectionCallback;


    public static void openCustomTab(Activity activity,
                                     CustomTabsIntent customTabsIntent,
                                     Uri uri,
                                     CustomTabFallback fallback){
        String packageName = CustomTabsHelper.getPackageNameToUse(activity);

        if(packageName == null){
            if(fallback != null){
                fallback.openUri(activity, uri);
            }
        }else{
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        }

    }

    public void unbindCustomTabsService(Activity activity){
        if(mConnection == null) return;
        activity.unbindService(mConnection);
        mClient = null;
        mcustomTabsSession = null;
        mConnection = null;

    }

    public CustomTabsSession getSession(){
        if(mClient == null){
            mcustomTabsSession = null;
        }else if(mcustomTabsSession == null){
            mcustomTabsSession = mClient.newSession(null);
        }
        return mcustomTabsSession;
    }

    public void setConnectionCallback(ConnectionCallback connectionCallback){
        this.mConnectionCallback = connectionCallback;
    }

    public void bindCustomTabsService(Activity activity){
        if (mClient != null) return;

        String packageName = CustomTabsHelper.getPackageNameToUse(activity);
        if(packageName == null) return;

        mConnection = new ServiceConnection(this);
        CustomTabsClient.bindCustomTabsService(activity, packageName, mConnection);
    }

    public boolean mayLaunchUrl(Uri uri, Bundle extras, List<Bundle> otherLikelyBundles){
        if (mClient == null) return false;

        CustomTabsSession session = getSession();
        if(session == null) return false;

        return session.mayLaunchUrl(uri, extras, otherLikelyBundles);

    }

    @Override
    public void onServiceConnected(CustomTabsClient client){
        mClient = client;
        mClient.warmup(0L);
        if(mConnectionCallback != null) mConnectionCallback.onCustomTabsConnected();

    }

    @Override
    public void onServiceDisconnected(){
        mClient = null;
        mcustomTabsSession = null;
        if (mConnectionCallback != null) mConnectionCallback.onCustomTabsDisconnected();

    }

    public interface ConnectionCallback{

        void  onCustomTabsConnected();

        void onCustomTabsDisconnected();

    }

    public interface CustomTabFallback{

        void openUri(Activity activity, Uri uri);

    }
}
