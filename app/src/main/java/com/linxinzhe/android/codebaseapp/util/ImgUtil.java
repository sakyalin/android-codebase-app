package com.linxinzhe.android.codebaseapp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.linxinzhe.android.codebaseapp.BuildConfig;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * @author linxinzhe on 4/3/16.
 */
public class ImgUtil {
    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_GALLERY = 2;
    public static final int REQUEST_CODE_CROP = 3;

    public static void openImgTaker(final Context context, int isGalleryOrCamera, final GalleryFinal.OnHanlderResultCallback onHanlderResultCallback) {
        ThemeConfig theme = new ThemeConfig.Builder().build();

        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(isGalleryOrCamera == REQUEST_CODE_CAMERA)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setCropSquare(true)
                .setForceCrop(true)
                .setForceCropEdit(false)
                .setEnableRotate(true)
                .setEnablePreview(true).build();

        ImageLoader imageloader = new PicassoImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme).setDebug(BuildConfig.DEBUG).setFunctionConfig(functionConfig).build();
        GalleryFinal.init(coreConfig);

        if (isGalleryOrCamera == REQUEST_CODE_GALLERY) {
            GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, onHanlderResultCallback);
        } else if (isGalleryOrCamera == REQUEST_CODE_CAMERA) {
            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                    String selectedImgAbsPath = resultList.get(0).getPhotoPath();

                    FunctionConfig functionConfig = new FunctionConfig.Builder()
                            .setEnableCamera(false)
                            .setEnableEdit(true)
                            .setEnableCrop(true)
                            .setCropSquare(true)
                            .setEnableRotate(true)
                            .setEnablePreview(true).build();

                    GalleryFinal.openCrop(REQUEST_CODE_CROP, functionConfig, selectedImgAbsPath, onHanlderResultCallback);
                }

                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static boolean compressImage(String srcPath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Bitmap image = BitmapFactory.decodeStream(new FileInputStream(srcPath));
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;//每次都减少10
            }
            FileOutputStream fos = new FileOutputStream(srcPath);
            baos.writeTo(fos);
            fos.close();
            baos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    static class PicassoImageLoader implements cn.finalteam.galleryfinal.ImageLoader {

        private Bitmap.Config mConfig;

        public PicassoImageLoader() {
            this(Bitmap.Config.RGB_565);
        }

        public PicassoImageLoader(Bitmap.Config config) {
            this.mConfig = config;
        }

        @Override
        public void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height) {
            Picasso.with(activity)
                    .load(new File(path))
                    .placeholder(defaultDrawable)
                    .error(defaultDrawable)
                    .config(mConfig)
                    .resize(width, height)
                    .centerInside()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(imageView);
        }

        @Override
        public void clearMemoryCache() {
        }
    }
}
