package com.umengshared.lwc.myumengshared.umeng;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyLog;
import com.umengshared.lwc.myumengshared.Tools.MyToast;
import com.umengshared.lwc.myumengshared.Tools.Tool;

/**
 * @Description:  Share
 * @Author:  lingwancai
 * @Time:  2018/7/30 16:12
 */
public class Share {

    private static final  String TAG="Share";
    private Activity context;
    private UMImage image;
    private String url;
    private String title = "myumengshared";
    private String content = "myumengshared";

    //分享面板
    public void init() {
        new ShareAction(context).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .withText("来自友盟分享面板")
                .withMedia(image)
                .setCallback(umShareListener)
                .open();
    }

    public Share(Activity context, String url, UMImage image,String content) {
        this.context = context;
        if (!Tool.isNull(content)) {
            this.content = content;
        }
        this.url = url;
        this.image = new UMImage(context, R.mipmap.ic_launcher);
    }

    public void initSina() {
        MyLog.e(TAG, "initSina");
        ShareAction shareAction = new ShareAction(context);
        shareAction.setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                .withText(content)
                .withMedia(image);
        if (!Tool.isNull(url)) {
            UMWeb web = new UMWeb(url);
            shareAction.withMedia(web);
        }
        shareAction.share();
    }

    public void initWX() {
        ShareAction shareAction = new ShareAction(context);
        shareAction.setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                .withText(content)
                .withMedia(image);
        if (!Tool.isNull(url)) {
            UMWeb web = new UMWeb(url);
            shareAction.withMedia(web);
        }
        shareAction.share();
    }

    public void initWxCircle() {
        ShareAction shareAction = new ShareAction(context);
        shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                .withText(content)
                .withMedia(image);
        if (!Tool.isNull(url)) {
            UMWeb web = new UMWeb(url);
            shareAction.withMedia(web);
        }
        shareAction.share();
    }

    public void initQQ() {
        ShareAction shareAction = new ShareAction(context);
        shareAction.setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                .withText(content)
                .withMedia(image);
        if (!Tool.isNull(url)) {
            UMWeb web = new UMWeb(url);
            shareAction.withMedia(web);
        }
        shareAction.share();
        //.withMedia(music)
        //.withTargetUrl(url)
        //.withTitle("qqshare")
    }

    public void initQzone() {
        MyLog.e(TAG,"initQzone");
        ShareAction shareAction = new ShareAction(context);
        shareAction.setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener)
                .withText(content)
                .withMedia(image);
        if (!Tool.isNull(url)) {
            UMWeb web = new UMWeb(url);
            shareAction.withMedia(web);
        }
        shareAction.share();
        //.withMedia(video)
    }

    public void initencent() {
        ShareAction shareAction = new ShareAction(context);
        shareAction.setPlatform(SHARE_MEDIA.TENCENT).setCallback(umShareListener)
                .withText(content)
                .withMedia(image);
        if (!Tool.isNull(url)) {
            UMWeb web = new UMWeb(url);
            shareAction.withMedia(web);
        }
        shareAction.share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                MyToast.showToast(platform + " 收藏成功啦");
            } else {
                MyToast.showToast(platform + " 分享成功啦");
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            MyToast.showToast(platform + " 分享失败啦"+t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            MyToast.showToast(platform + " 分享取消了");
        }
    };
}
