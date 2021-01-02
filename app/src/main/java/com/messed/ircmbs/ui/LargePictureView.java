package com.messed.ircmbs.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.messed.ircmbs.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class LargePictureView extends PopupWindow {

    View view;
    Context mContext;
    PhotoView photoView;
    ViewGroup parent;

    public LargePictureView(Context ctx, View v, String imageUrl) {
        super(((LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.large_profile_view, null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        if (Build.VERSION.SDK_INT >= 21) {
            setElevation(5.0f);
        }
        this.mContext = ctx;
        this.view = getContentView();
        ImageButton closeButton = this.view.findViewById(R.id.ib_close);
        final ProgressBar progressBar = (ProgressBar) this.view.findViewById(R.id.progressbar);
        closeButton.setOnClickListener(view -> dismiss());
        setOutsideTouchable(true);
        setFocusable(true);
        photoView = view.findViewById(R.id.image);
        photoView.setMaximumScale(6);
        parent = (ViewGroup) photoView.getParent();
        showAtLocation(v, Gravity.CENTER, 0, 0);
        Glide.with(mContext)
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.drawable.templogo)
                .into(photoView);
    }
}
