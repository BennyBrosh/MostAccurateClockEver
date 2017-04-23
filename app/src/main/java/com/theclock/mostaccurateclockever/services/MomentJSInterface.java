package com.theclock.mostaccurateclockever.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by beno on 22-Apr-17.
 */

public interface MomentJSInterface {
    @GET("/downloads/moment.min.js")
    Call<ResponseBody> getMomentJs();
}
