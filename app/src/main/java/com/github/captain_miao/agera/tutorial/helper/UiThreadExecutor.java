package com.github.captain_miao.agera.tutorial.helper;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * @author YanLu
 * @since 16/5/25
 */
public class UiThreadExecutor implements Executor {
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }

    public void shutdown(){
        // TODO: 16/5/25
    }

    // how to release it?
    public static Executor newUiThreadExecutor() {
        return new UiThreadExecutor();
    }

    private UiThreadExecutor() {
    }
}
