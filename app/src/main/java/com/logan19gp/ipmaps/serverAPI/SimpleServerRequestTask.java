package com.logan19gp.ipmaps.serverAPI;

import android.app.Activity;

/**
 * Created by george on 11/19/15.
 */
public abstract class SimpleServerRequestTask extends ServerRequestTask<Object, ResponseOrError<?>> {
    public SimpleServerRequestTask(Activity activity) {
        super(activity);
    }


    protected abstract ResponseOrError<?> doInBackground();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ResponseOrError<?> doInBackground(Object... body) {
        return doInBackground();
    }
}

