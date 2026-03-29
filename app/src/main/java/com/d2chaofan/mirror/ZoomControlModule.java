package com.d2chaofan.mirror;

import android.view.View;
import android.widget.Button;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.ZoomState;
import androidx.lifecycle.LifecycleOwner;

public class ZoomControlModule {
    private static final float ZOOM_STEP = 0.5f;
    private final Button zoomInButton;
    private final Button zoomOutButton;
    private Camera camera;
    private float maxZoom;
    private float minZoom;

    public ZoomControlModule(View rootView, Camera camera, LifecycleOwner lifecycleOwner) {
        this.zoomInButton = rootView.findViewById(R.id.btnZoomIn);
        this.zoomOutButton = rootView.findViewById(R.id.btnZoomOut);
        this.camera = camera;

        CameraInfo cameraInfo = camera.getCameraInfo();
        ZoomState zoomState = cameraInfo.getZoomState().getValue();
        if (zoomState != null) {
            maxZoom = zoomState.getMaxZoomRatio();
            minZoom = zoomState.getMinZoomRatio();
        }

        setupClickListeners();
    }

    private void setupClickListeners() {
        zoomInButton.setOnClickListener(v -> {
            float currentZoom = camera.getCameraInfo().getZoomState().getValue().getZoomRatio();
            float newZoom = Math.min(currentZoom + ZOOM_STEP, maxZoom);
            camera.getCameraControl().setZoomRatio(newZoom);
        });
        
        zoomOutButton.setOnClickListener(v -> {
            float currentZoom = camera.getCameraInfo().getZoomState().getValue().getZoomRatio();
            float newZoom = Math.max(currentZoom - ZOOM_STEP, minZoom);
            camera.getCameraControl().setZoomRatio(newZoom);
        });
    }
}