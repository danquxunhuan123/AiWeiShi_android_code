package com.trs.aiweishi.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.blankj.utilcode.util.AppUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Liufan on 2018/7/2.
 */

public class AlbumUtil {
    public static Uri takePhone(Activity activity, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = new File(activity.getExternalCacheDir(), "image.jpeg");
//        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        /*
         * 这里就是高版本需要注意的，需用使用FileProvider来获取Uri，同时需要注意getUriForFile
         * 方法第二个参数要与AndroidManifest.xml中provider的里面的属性authorities的值一致
         * */
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        Uri imageUriFromCamera = FileProvider.getUriForFile(activity,
                AppUtils.getAppPackageName() + ".fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);

        activity.startActivityForResult(intent, requestCode); //PHOTO_ALBUM

        return imageUriFromCamera;
    }

    public static void openAlbum(Activity activity, int requestCode) {
        File outputImage = new File(activity.getExternalCacheDir(), "image.jpeg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri outputUri = FileProvider.getUriForFile(activity,
                AppUtils.getAppPackageName() + ".fileprovider", outputImage); //getApplicationContext().getPackageName() + ".provider"
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setType("image/*");
        //允许裁剪
        intent.putExtra("crop", true);
        //允许缩放
        intent.putExtra("scale", true);

        List<ResolveInfo> resInfoList = activity.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            activity.grantUriPermission(packageName, outputUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        //图片的输出位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        activity.startActivityForResult(intent, requestCode);  //PHOTO_ALBUM
    }

    public static void startCameraCrop(Activity fragment, Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        fragment.startActivityForResult(intent, requestCode);// 启动裁剪
    }

    public static void startAlbumCrop(Activity activity, Uri uri, int requestCode) {
        File camerafile = new File(activity.getExternalCacheDir(), "image.jpeg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            imageUri = FileProvider.getUriForFile(activity,
                    AppUtils.getAppPackageName() + ".fileprovider",
                    camerafile);
        } else {
            imageUri = Uri.fromFile(camerafile);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, requestCode);// 启动裁剪
    }
}
