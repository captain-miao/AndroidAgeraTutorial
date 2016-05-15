package com.github.captain_miao.agera.tutorial.helper;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author YanLu
 * @since 16/4/25
 */
public class PicassoBinding {
    private static final String TAG = "PicassoBinding";

    @BindingAdapter({"imageUrl"})
    public static void imageLoader(ImageView imageView, String url) {
        Picasso.Builder builder = new Picasso.Builder(imageView.getContext());
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
                Log.e("Picasso Error", uri.toString());
            }
        });
        builder.build().load(url).into(imageView);


        //Picasso.with(imageView.getContext()).load(url).into(imageView);
    }
    @BindingAdapter({"imageUrl", "error"})
    public static void imageLoader(ImageView imageView, String url, Drawable error) {
        Picasso.Builder builder = new Picasso.Builder(imageView.getContext());
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
                Log.e("Picasso Error", uri.toString());
            }
        });
        builder.build()
                .load(url)
                .error(error)
                .into(imageView);


        //Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

//    @BindingAdapter({"imageUrl", "error", "android:clickable"})
//    public static void imageLoader(ImageView imageView, String url, Drawable error, boolean clickable) {
//        Picasso.Builder builder = new Picasso.Builder(imageView.getContext());
//        builder.listener(new Picasso.Listener() {
//            @Override
//            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
//                exception.printStackTrace();
//                Log.e("Picasso Error", uri.toString());
//            }
//        });
//        builder.build()
//                .load(url)
//                .error(error)
//                .into(imageView);
//        Log.d(TAG, "android:clickable = " + clickable);
//
////        Picasso.with(imageView.getContext()).load(url).error(error).into(imageView);
//    }
}
