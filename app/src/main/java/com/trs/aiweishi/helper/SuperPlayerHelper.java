package com.trs.aiweishi.helper;

import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.rtmp.TXLiveConstants;

/**
 * Created by trs on 2018/10/31.
 */

public class SuperPlayerHelper {
    private SuperPlayerView playerView;

    public SuperPlayerHelper(SuperPlayerView playerView) {
        this.playerView = playerView;

        initPlayer();
    }

    private void initPlayer() {
        // 播放器配置
        SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
        // 开启悬浮窗播放
        prefs.enableFloatWindow = false;
//        // 设置悬浮窗的初始位置和宽高
//        SuperPlayerGlobalConfig.TXRect rect = new SuperPlayerGlobalConfig.TXRect();
//        rect.x = 0;
//        rect.y = 0;
//        rect.width = 810;
//        rect.height = 540;
//        prefs.floatViewRect = rect;
        // 播放器默认缓存个数
        prefs.maxCacheItem = 5;
        // 设置播放器渲染模式
        prefs.enableHWAcceleration = true;
        prefs.renderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
    }

    // 通过URL方式的视频信息配置
    public void playWithMode(String title, String videoUrl) {
        SuperPlayerModel model2 = new SuperPlayerModel();
        model2.title = title;
        model2.videoURL = videoUrl;
        playerView.playWithMode(model2);// 开始播放
    }

    // 重新开始播放
    public void onResume() {
        if (playerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {
            playerView.onResume();
            if (playerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FLOAT) {
                playerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
            }
        }
    }

    // 停止播放
    public void onPause() {
        if (playerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            playerView.onPause();
        }
    }

    // 释放
    public void release() {
        playerView.release();
        if (playerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            playerView.resetPlayer();
        }
    }
}
