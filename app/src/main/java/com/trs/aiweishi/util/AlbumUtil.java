package com.trs.aiweishi.util;

import android.app.Activity;
import android.content.Context;
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
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        Uri imageUriFromCamera = FileProvider.getUriForFile(activity,
                AppUtils.getAppPackageName() + ".fileprovider", photoFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
        activity.startActivityForResult(intent, requestCode);

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

    public static void startCrop(Activity activity, Uri uri, int requestCode) {
        File camerafile = new File(activity.getExternalCacheDir(), "image.jpeg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri imageUri = getUriForFile(activity, camerafile);
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT < 24) {
            grantPermissions(activity, intent, uri, true);
        }
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, requestCode);// 启动裁剪
    }

    private static Uri getUriForFile(Activity activity, File camerafile) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", camerafile);
        } else {
            fileUri = Uri.fromFile(camerafile);
        }
        return fileUri;
    }

    /**
     * @param writeAble 是否可读
     */
    public static void grantPermissions(Context context, Intent intent, Uri uri, boolean writeAble) {
        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
        if (writeAble) {
            flag |= Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        }
        intent.addFlags(flag);
        List<ResolveInfo> resInfoList = context.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, flag);
        }
    }

//    public static void startAlbumCrop(Activity activity, Uri uri, int requestCode) {
//        File camerafile = new File(activity.getExternalCacheDir(), "image.jpeg");
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        Uri imageUri = getUriForFile(activity, camerafile);
//        intent.setDataAndType(uri, "image/*");
//        if (Build.VERSION.SDK_INT < 24) {
//            grantPermissions(activity, intent, uri, true);
//        }
//        intent.putExtra("scale", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        activity.startActivityForResult(intent, requestCode);// 启动裁剪
//    }
//
//    public static void startCameraCrop(Activity activity, Uri uri, int requestCode) {
//        File camerafile = new File(activity.getExternalCacheDir(), "image.jpeg");
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        Uri imageUri = getUriForFile(activity, camerafile);
//        intent.setDataAndType(uri, "image/*");
//        if (Build.VERSION.SDK_INT < 24) {
//            grantPermissions(activity, intent, uri, true);
//        }
//
//        intent.putExtra("scale", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        activity.startActivityForResult(intent, requestCode);// 启动裁剪
//    }
}
