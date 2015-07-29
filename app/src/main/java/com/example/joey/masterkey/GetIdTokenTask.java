package com.example.joey.masterkey;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by joey on 7/16/15.
 */
public class GetIdTokenTask extends AsyncTask<Void, Void, String> {

    private static final String SERVER_CLIENT_ID = "635248478115-khks0610shmbkpk8qh4btdgeos4c2n3e.apps.googleusercontent.com";
    private GoogleApiClient mGoogleApiClient;
    private Context mContext;

    public GetIdTokenTask(GoogleApiClient mGoogleApiClient, Context context) {
        this.mGoogleApiClient = mGoogleApiClient;
        this.mContext = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        String accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);
        Account account = new Account(accountName, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String scopes = "audience:server:client_id:" + SERVER_CLIENT_ID;
        try{
            String token = GoogleAuthUtil.getToken(mContext, account.name, scopes);
            Log.d("MrKey", "Connection made!");
            Message m = new Message(Message.Type.CONN, token, "nA");
            return serverDao.sendMessage(m);
        } catch (UserRecoverableAuthException e) {
            Log.d("MrKey", "Auth Error retrieving token: ", e);
            return null;
        } catch (GoogleAuthException e) {
            Log.d("MrKey", "Google Auth Error retrieving token: ", e);
            return null;
        } catch (IOException e) {
            Log.d("MrKey", "IO Error retrieving token: ", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("MrKey", "ID token:" + s);
    }
}
