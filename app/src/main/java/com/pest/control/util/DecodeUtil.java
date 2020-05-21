package com.pest.control.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecodeUtil {
    IDecodeListener listener;

    public void setListener(IDecodeListener listener) {
        this.listener = listener;

    }


    public  void decode(final Bitmap bitmap){
        new Thread(new Runnable(){
            @Override
            public void run() {
                String result = plant(bitmap);
                String final_result = getClosestResult(result);
                listener.onDecode(final_result);
            }
        }).start();
        try {
            Thread.sleep(1300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void decode(Bitmap bitmap,final String filePath){
        new Thread(new Runnable(){
            @Override
            public void run() {
                String result = plant(filePath);
                String final_result = getClosestResult(result);
                listener.onDecode(final_result);
            }
        }).start();
        try {
            Thread.sleep(1300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public interface IDecodeListener{
        public void onDecode(String result);
    };

    private String plant(String filePath) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
        try {
            // 本地文件路径
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = Token.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            Log.i("lzw", result);
            System.out.println(result);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("lzw", "获取不到");
            return "图片规格不符合";
        }
        //return null;
    }

    private String plant(Bitmap bitmap) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
        try {
            // 本地文件路径
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imgData = baos.toByteArray();
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = Token.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            Log.i("lzw", result);
            System.out.println(result);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("lzw", "获取不到");
            return "图片规格不符合";
        }
        //return null;
    }

    public String getClosestResult(String result){
        Gson gson = new Gson();
        RecResult recresult = gson.fromJson(result,RecResult.class);
        Log.i("lzw","re_ok");
        List<RecResult.ResultBean> list = recresult.getResult();
        List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
        String str = "";
        for(int i = 0;i<list.size();i++) {
            double score = list.get(i).getScore();
            String name = list.get(i).getName();
            Map<String,String> map = new HashMap<String,String>();
            double s = score;
            String n = name;
            map.put("n","名称: "+n);
            map.put("s","相似度: "+s);
            if(i==0){
                str = "本次识别的结果是"+name+"相似率为"+s;
                break;
            }
        }
        return str;
    }
}
