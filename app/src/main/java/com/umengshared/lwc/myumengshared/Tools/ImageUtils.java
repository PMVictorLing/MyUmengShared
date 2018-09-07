package com.umengshared.lwc.myumengshared.Tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

public class ImageUtils {

    private static final String TAG = "ImageUtils";
    // 存储图片的集合(存在内存中)
    private static Hashtable<Integer, SoftReference<Bitmap>>
            mCacheBitmapTable = new Hashtable<Integer, SoftReference<Bitmap>>();

    /**
     * 根据id获得bitmap对象, 注意: 采用了SoftReference缓存,
     *
     * @param context
     * @param imageID
     * @return 先去集合中根据imageID去取相对应的图片,
     * 如果有, 直接返回
     * 如果没有, 调用getInvertImage方法得到一个对象返回
     */
    public static Bitmap getBitmap(Context context, int imageID, int position) {

        SoftReference<Bitmap> softReference = mCacheBitmapTable.get(imageID);
        if (softReference != null) {
            Bitmap bitmap = softReference.get();
            if (bitmap != null) {
                Log.i("ImageUtils", "从内存中取: " + position);
                return bitmap;
            }
        }

        Log.i("ImageUtils", "重新加载: " + position);
        Bitmap invertImage = getInvertImage(context, imageID);

        // 取出来对应的图片之后, 往内存中存一份, 为了方便下次直接去内存中取
        mCacheBitmapTable.put(imageID, new SoftReference<Bitmap>(invertImage));
        return invertImage;
    }

    /**
     * 根据给定的id获取处理(倒影, 倒影渐变)过的bitmap
     *
     * @param imageID
     * @return
     */
    private static Bitmap getInvertImage(Context context, int imageID) {
        // 获得原图
        Options opts = new Options();
        opts.inSampleSize = 2;
        Bitmap sourceBitmap = BitmapFactory.decodeResource(context.getResources(), imageID, opts);

        // 倒影图片
        Matrix matrix = new Matrix();
        // 设置图片的反转为, 垂直反转
        matrix.setScale(1.0f, -1.0f);
//			float[] values = {
//					1.0f,	0f,		0f,
//					0f,		-1.0f,	0f,
//					0f,		0f,		1.0f
//			};
        // 倒影图片
        //Bitmap invertBitmap = Bitmap.createBitmap(sourceBitmap, 0, sourceBitmap.getHeight() /2,
        //		sourceBitmap.getWidth(), sourceBitmap.getHeight() /2, matrix, false);

        // 合成一张图片
        //Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(),
        //.getHeight(), Config.ARGB_8888);

        // 把原图添加到合成图片的左上角
        //Canvas canvas = new Canvas(resultBitmap);		// 指定画板画在合成图片上
        //canvas.drawBitmap(sourceBitmap, 0, 0, null);	// 把原图绘制到合成图上

        // 把倒影图片绘制到合成图片上
        //canvas.drawBitmap(invertBitmap, 0, sourceBitmap.getHeight() + 5, null);

        //Rect rect = new Rect(0, sourceBitmap.getHeight() + 5, resultBitmap.getWidth(), resultBitmap.getHeight());
        //Paint paint = new Paint();

        /**
         * TileMode.CLAMP 指定渲染边界以外的控件以最后的那个颜色继续往下渲染
         */
        //LinearGradient shader = new LinearGradient(0,
        //		sourceBitmap.getHeight() + 5, 0, resultBitmap.getHeight(), 0x70FFFFFF, 0x00FFFFFF, TileMode.CLAMP);

        // 设置为取交集模式
        //paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // 指定渲染器为线性渲染器
        //paint.setShader(shader);
        //canvas.drawRect(rect, paint);

        int radius = sourceBitmap.getWidth() / 2;
        BitmapShader bitmapShader = new BitmapShader(sourceBitmap, TileMode.REPEAT,
                TileMode.REPEAT);
        /*Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);*/
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(),
                sourceBitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        canvas.drawCircle(radius, radius, radius, paint);

        return resultBitmap;
    }

    /**
     * @param drawable drawable ת  Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 读取本地图片 解决图片旋转问题
     *
     * @param filePath
     * @return
     */
    public static Bitmap readBitmapFromPath(Activity context, String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
//        int be = calculateInSampleSize(context, outWidth, outHeight);
        int be = calculateInSampleSize(options, 100, 100);
        options.inSampleSize = be;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        try {
            return BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError e) {
            System.gc();
            try {
                options.inSampleSize = be + 1;
                return BitmapFactory.decodeFile(filePath, options);

            } catch (OutOfMemoryError e2) {
                return null;
            }
        }
    }

    /**
     * 计算图片缩放比例
     *
     * @param outWidth
     * @param outHeight
     * @return
     */
    public static int calculateInSampleSize(Activity context, int outWidth, int outHeight) {
        int screenWidth = context.getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = context.getWindowManager().getDefaultDisplay().getHeight();
        int be;
        if (outWidth > screenWidth || outHeight > 1.5 * screenHeight) {
            int heightRatio = Math.round(((float) outHeight) / ((float) 1.5 * screenHeight));
            int widthRatio = Math.round(((float) outWidth) / ((float) screenWidth));
            int sample = heightRatio > widthRatio ? heightRatio : widthRatio;
            if (sample < 3)
                be = sample;
            else if (sample < 6.5)
                be = 4;
            else if (sample < 8)
                be = 8;
            else
                be = sample;
        } else {
            be = 1;
        }
        if (be <= 0) {
            be = 1;
        }
        return be;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        MyLog.e(TAG, "origin, w= " + width + " h=" + height);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        MyLog.e(TAG, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }

    /**
     * 获取jpeg的旋转信息
     *
     * @param filepath
     * @return
     */
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            //LogUtil.i("test", "cannot read exif");
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Log.i("ORIENTATION", orientation + "");
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param bitmap 原图
     * @param angle2 旋转角度
     * @return
     */
    public static Bitmap rotaingImageView(Bitmap bitmap, int angle2) {
        Matrix matrix = new Matrix();
        // 旋转图片 动作
        matrix.postRotate(angle2);
        System.out.println("angle2=" + angle2);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        bitmap = BitmapFactory.decodeStream(bitmap2IS(bitmap), null, options);
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap.recycle();
        return resizedBitmap;
    }

    /**
     * Bitmap转换成InputStream方法
     *
     * @param bm
     * @return
     */
    public static InputStream bitmap2IS(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
        return sbs;
    }

    public static File saveBitmaptoSdCard(Bitmap bm, Activity mContext, String fileDir) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            File yunDir = new File(sdCardDir.getPath());
            if (!yunDir.exists()) {
                yunDir.mkdirs();
            }
            FileOutputStream fos;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] buffer = baos.toByteArray();
            File f = new File(yunDir, fileDir);
            MyLog.e(TAG,"save filepath="+f.getAbsolutePath());
            if (!f.exists()) {
            }
            try {
                f.createNewFile();
                fos = new FileOutputStream(f);
                fos.write(buffer, 0, buffer.length);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return f;
        } else {
            Toast.makeText(mContext, "sd卡不存在", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 获取bitmap的大小
     */
    public static int getBitmapSize(Bitmap bitmap) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

}
