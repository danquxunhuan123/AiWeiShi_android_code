package com.trs.aiweishi.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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
        File photoFile = new File(activity.getExternalCacheDir(), "image.jpeg");

        Uri imageUriFromCamera = getUriForFile(activity, photoFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            grantPermissions(activity, intent, imageUriFromCamera, true);
        }

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

        Uri outputUri = getUriForFile(activity, outputImage);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            grantPermissions(activity, intent, outputUri, true);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);  //图片的输出位置
        activity.startActivityForResult(intent, requestCode);
    }

    public static Uri startCrop(Activity activity, Uri uri, int requestCode) {
        File camerafile = new File(activity.getExternalCacheDir(), "image.jpeg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri imageUri = getUriForFile(activity, camerafile);
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            grantPermissions(activity, intent, uri, true);
        }
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, requestCode);// 启动裁剪

        return imageUri;
    }

    private static Uri getUriForFile(Activity activity, File camerafile) {
        Uri fileUri;
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
}
