package com.example.pokemons.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.pokemons.MyApplication;
import com.example.pokemons.enums.NetworkConnectStates;

public class NetworkConnect {
    private static NetworkConnectStates connectState = NetworkConnectStates.NOT_DEFINED;

    private NetworkConnect() {
    }

    public static void setConnectStates(NetworkConnectStates state) {
        connectState = state;
    }

    public static boolean isConnected() {
        if (connectState == NetworkConnectStates.NOT_CONNECT)
            return false;

        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
