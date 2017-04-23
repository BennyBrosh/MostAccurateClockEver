package com.theclock.mostaccurateclockever.presenters;

import android.content.Context;

import com.theclock.mostaccurateclockever.services.MomentJSInterface;
import com.theclock.mostaccurateclockever.services.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by beno on 22-Apr-17.
 */

public class ClockPresenter {
    private final Context context;
    private final JSDownloadListener mListener;

    public ClockPresenter(JSDownloadListener listener, Context context) {
        this.mListener = listener;
        this.context = context;
    }

    public void getMomentJS() {
        MomentJSInterface service = RetrofitClient.getClient().create(MomentJSInterface.class);
        Call<ResponseBody> result = service.getMomentJs();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    mListener.momentJSDownloaded(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public interface JSDownloadListener {
        void momentJSDownloaded(String javascript);
    }
}
