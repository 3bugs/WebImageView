package com.example.webimageview;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WebImageView extends ImageView {

    private static final String SAVED_INSTANCE_STATE = "instance_state";
    private static final String SAVED_BITMAP = "saved_bitmap";

    private Context mContext;
    private Drawable mPlaceholder;
    private Bitmap mServerImageBitmap;

    public WebImageView(Context context) {
        this(context, null);
    }

    public WebImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebImageView(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        mContext = context;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SAVED_INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putParcelable(SAVED_BITMAP, mServerImageBitmap);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mServerImageBitmap = bundle.getParcelable(SAVED_BITMAP);
            showImage();
            state = bundle.getParcelable(SAVED_INSTANCE_STATE);
        }
        super.onRestoreInstanceState(state);
    }

    public void setPlaceholderImage(Drawable drawable) {
        mPlaceholder = drawable;
        setImageDrawable(mPlaceholder);
    }

    public void setPlaceholderImage(int resid) {
        mPlaceholder = getResources().getDrawable(resid);
        setImageDrawable(mPlaceholder);
    }

    public void setImageUrl(String url) {
        DownloadTask task = new DownloadTask();
        task.execute(url);
    }

    private class DownloadTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {
            String strUrl = urls[0];
            try {
                URL url = new URL(strUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                mServerImageBitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            showImage();
        }
    }

    private void showImage() {
        Drawable drawable = new BitmapDrawable(getResources(), mServerImageBitmap);
        if (drawable != null) {
            setImageDrawable(drawable);
        }
    }
}