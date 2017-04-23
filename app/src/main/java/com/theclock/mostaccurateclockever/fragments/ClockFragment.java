package com.theclock.mostaccurateclockever.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theclock.mostaccurateclockever.R;
import com.theclock.mostaccurateclockever.presenters.ClockPresenter;
import com.theclock.mostaccurateclockever.views.InjectWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import github.ishaan.buttonprogressbar.ButtonProgressBar;


public class ClockFragment extends Fragment implements ClockPresenter.JSDownloadListener, InjectWebView.OnCloseButtonListener {

    ClockPresenter presenter;

    @BindView(R.id.fragment_clock_button)
    ButtonProgressBar showClockButton;
    @BindView(R.id.fragment_clock_webview)
    InjectWebView webview;

    public ClockFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ClockPresenter(this, getContext());
    }

    @OnClick(R.id.fragment_clock_button)
    public void showLisbonClock(View view) {
        showClockButton.startLoader();
        presenter.getMomentJS();
    }


    @Override
    public void momentJSDownloaded(String javascript) {
        showClockButton.stopLoader();
        webview.injectJsAndLoad(javascript, this);
    }


    @Override
    public void closeButtonClicked() {
        showClockButton.reset();
    }
}
