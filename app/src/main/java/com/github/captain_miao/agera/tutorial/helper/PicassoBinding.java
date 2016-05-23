package com.github.captain_miao.agera.tutorial.helper;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.github.captain_miao.agera.tutorial.R;
import com.github.captain_miao.agera.tutorial.recycleview.PicassoOnScrollListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * @author YanLu
 * @since 16/4/25
 */
public class PicassoBinding {
    private static final String TAG = "PicassoBinding";

    @BindingAdapter({"imageUrl"})
    public static void imageLoader(ImageView imageView, String url) {
//        Picasso.Builder builder = new Picasso.Builder(imageView.getContext());
//        builder.listener(new Picasso.Listener() {
//            @Override
//            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
//                exception.printStackTrace();
//                Log.e("Picasso Error", uri.toString());
//            }
//        });
//        builder.build().load(url).into(imageView);


        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }
    @BindingAdapter({"imageUrl", "error"})
    public static void imageLoader(ImageView imageView, String url, Drawable error) {
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


        Picasso.with(imageView.getContext()).load(url).error(error).into(imageView);
    }

    @BindingAdapter({"compressImageUrl"})
    public static void loadImageCompress(ImageView imageView, String url) {
        //large -> b middle
//        Picasso.Builder builder = new Picasso.Builder(imageView.getContext().getApplicationContext());
//        builder.listener(new Picasso.Listener() {
//            @Override
//            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
//                exception.printStackTrace();
//                Log.e("Picasso Error", uri.toString());
//            }
//        });
        //recycle bitmap
//        Drawable drawable = imageView.getDrawable();
//        if (drawable instanceof BitmapDrawable) {
//            imageView.setImageDrawable(null);
//            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//            Log.d(TAG, "recycle bitmap, w:" + bitmap.getWidth() + ", h:" + bitmap.getHeight());
//            bitmap.recycle();
//        }
        Picasso.with(imageView.getContext().getApplicationContext())
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .placeholder(R.drawable.ic_image_load_place_holder)
                .config(Bitmap.Config.RGB_565)
                .tag(PicassoOnScrollListener.TAG)
                .into(imageView);
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("{imageBitmap}")
    public static void setImageViewBitmap(ImageView iv, Bitmap bitmap) {
       iv.setImageBitmap(bitmap);
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
