package com.pest.control.fragement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.pest.control.R;
import com.pest.control.util.Base64Util;
import com.pest.control.util.DecodeUtil;
import com.pest.control.util.FileUtil;
import com.pest.control.util.HttpUtil;
import com.pest.control.util.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/***
 * 图片解析界面
 *
 * */
public class DecodeFragment extends Fragment {

    public static final int REQUEST_TAKE_PHOTO_CODE = 101;

    ImageView mSelectImg;
    TextView mDecodeTv;
    Button mSelecLocalPicBtn;
    Button mTakePicBtn;

    private Uri mCutUri;

    private DecodeUtil mDecodeUtil;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_decode, container, false);
        initView(view);
        return view;
    }

    public static DecodeFragment getInstance() {
        return new DecodeFragment();
    }

    public void initView(View view){
        mSelectImg = view.findViewById(R.id.select_pic_img);
        mDecodeTv = view.findViewById(R.id.decode_res_tv);
        mSelecLocalPicBtn = view.findViewById(R.id.select_pic_local);
        mTakePicBtn = view.findViewById(R.id.select_pic_photo);

        mSelecLocalPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        mTakePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        mDecodeUtil = new DecodeUtil();
        mDecodeUtil.setListener(new DecodeUtil.IDecodeListener() {
            @Override
            public void onDecode(String result) {
                mDecodeTv.setText(result);
            }
        });
    };

    public void initData(){

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1: //从相册图片后返回的uri
                    //启动裁剪
                    Uri uri= data.getData();
                    startActivityForResult(CutForPhoto(uri),2);
                    break;
                case 2:
                    Bitmap bm1 = null;
                    try {
                        bm1 = BitmapFactory.decodeStream(
                                getContext().getContentResolver().openInputStream(mCutUri));
                        if (bm1!=null){
                            mSelectImg.setImageBitmap(bm1);
                            mDecodeUtil.decode(bm1);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_TAKE_PHOTO_CODE:
                    Bitmap bm = null;
                    try {
                        bm = BitmapFactory.decodeStream(
                                getContext().getContentResolver().openInputStream(mCutUri));
                        if (bm!=null){
                            mSelectImg.setImageBitmap(bm);
                            mDecodeUtil.decode(bm);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private Intent startPhotoZoom(Uri uri) {
        File CropPhoto = new File(getActivity().getExternalCacheDir(), "Crop.jpg");//这个是创建一个截取后的图片路径和名称。
        try {
            if (CropPhoto.exists()) {
                CropPhoto.delete();
            }
            CropPhoto.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri ImageUri = Uri.fromFile(CropPhoto);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        //输出的宽高

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);

        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        return intent;
    }

    /**
     * 图片裁剪
     * @param uri
     * @return
     */
    @NonNull
    private Intent CutForPhoto(Uri uri) {
        try {
            //直接裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置裁剪之后的图片路径文件
            String path = getContext().getFilesDir() + File.separator + "images" + File.separator;
            File cutfile = new File(path, "test.jpg");
            if (cutfile.exists()){ //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = uri; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            outputUri =  Uri.fromFile(cutfile);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //步骤二：Android 7.0及以上获取文件 Uri
                mCutUri= FileProvider.getUriForFile(getActivity(), "com.pest.control", cutfile);
            } else {
                //步骤三：获取文件Uri
                mCutUri = Uri.fromFile(cutfile);
            }
//            mCutUri = outputUri;
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop",true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", 200); //200dp
            intent.putExtra("outputY",200);
            intent.putExtra("scale",true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data",false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void takePhoto() {
        // 步骤一：创建存储照片的文件
        String path = getContext().getFilesDir() + File.separator + "images" + File.separator;
        File file = new File(path, "test.jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //步骤二：Android 7.0及以上获取文件 Uri
            mCutUri = FileProvider.getUriForFile(getActivity(), "com.pest.control", file);
        } else {
            //步骤三：获取文件Uri
            mCutUri = Uri.fromFile(file);
        }
        //步骤四：调取系统拍照
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCutUri);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO_CODE);
    }


}
