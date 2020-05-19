package com.pest.control.data;

import com.store.selection.bean.Evaluate;
import com.store.selection.bean.Store;
import com.store.selection.bean.Village;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***
 * 基础数据
 * */
public class DataBase {

    static List<Evaluate> mEvaluates = new ArrayList<>();
    static List<Store> mStores = new ArrayList<>();
    static  List<Village> mVillages = new ArrayList<>();

    static String[] mDefautlEvalute = new String[]{
        "人流因素,房屋类型,普通住宅,1,0.2,",
        "人流因素,房屋类型,公寓,3",
        "人流因素,房屋类型,别墅,5",
        "人流因素,消费水平,5k以下,1",
        "人流因素,消费水平,5k~1w,2",
        "人流因素,消费水平,1w~1.5w,3",
        "人流因素,消费水平,1.5w~2w,4",
        "人流因素,消费水平,2w以上,5",
        "人流因素,小区成熟度,3年以下,1",
        "人流因素,小区成熟度,3~5年,2",
        "人流因素,小区成熟度,5~8年,3",
        "人流因素,小区成熟度,8~10年,4",
        "人流因素,小区成熟度,10年及以上,5",

        "环境因素,同类竞争店铺情况,4家以上,1",
        "环境因素,同类竞争店铺情况,3家,2",
        "环境因素,同类竞争店铺情况,2家,3",
        "环境因素,同类竞争店铺情况,1家,4",
        "环境因素,同类竞争店铺情况,0家,5",
        "环境因素,互补类店铺情况,0种,1",
        "环境因素,互补类店铺情况,1种,2",
        "环境因素,互补类店铺情况,2种,3",
        "环境因素,互补类店铺情况,3种,4",
        "环境因素,互补类店铺情况,4种,5",
        "环境因素,周边大中型地区数量,4所及以上,1",
        "环境因素,周边大中型地区数量,3所,2",
        "环境因素,周边大中型地区数量,2所,3",
        "环境因素,周边大中型地区数量,1所,4",
        "环境因素,周边大中型地区数量,0所,5",
        "环境因素,门店可视性,门店前有障碍物直接遮挡,1",
        "环境因素,门店可视性,门店前有隔离带,2",
        "环境因素,门店可视性,门店前设置有停车位,3",
        "环境因素,门店可视性,门店前小花园,4",
        "环境因素,门店可视性,门店前无障碍物,5",

        "交通因素,范围内公交线路数目,7条以下,1",
        "交通因素,范围内公交线路数目,7~9条,2",
        "交通因素,范围内公交线路数目,10~12条,3",
        "交通因素,范围内公交线路数目,13~15条,4",
        "交通因素,范围内公交线路数目,15条以上,5",
        "交通因素,范围内公交站数量,1个,1",
        "交通因素,范围内公交站数量,2~3个,2",
        "交通因素,范围内公交站数量,4~5个,3",
        "交通因素,范围内公交站数量,5~6个,4",
        "交通因素,范围内公交站数量,6个及以上,5",
        "交通因素,离小区主要出入口的距离,500m以上,1",
        "交通因素,离小区主要出入口的距离,500m以内,2",
        "交通因素,离小区主要出入口的距离,300m以内,3",
        "交通因素,离小区主要出入口的距离,200m以内,4",
        "交通因素,离小区主要出入口的距离,100m以内,5",
        "交通因素,门店的位置,无机动车道经过,1",
        "交通因素,门店的位置,位于单向车道旁,2",
        "交通因素,门店的位置,位于双向车道旁,3",
        "交通因素,门店的位置,位于三叉路口,4",
        "交通因素,门店的位置,位于十字路口,5",
    };

    public static String[] mDefaultStore = new String[]{
            "餐饮,中餐：中式小吃、外省市菜、自助餐、家常菜、火锅、烧烤、海鲜、干锅",
            "餐饮,西餐：西式简餐、西式小吃、牛排、意大利菜、法国菜、西班牙菜、美国菜、墨西哥菜",
            "餐饮,亚洲菜：日本料理、韩国料理、东南亚菜、泰国菜、新加坡菜、印度菜、越南菜",
            "餐饮,面包甜点：甜品店、面包蛋糕",
            "餐饮,咖啡饮料：奶茶店、饮料店、咖啡厅",
            "餐饮,其他：其他",

            "零售,个人装扮：奢侈品、快时尚、男装、女装、眼镜、围巾、帽子、手表、男鞋、女鞋、旅行箱、手提包、化妆品、假发、老人服饰、伞",
            "零售,家居生活：超市、家具店、家饰家纺、茶酒店、水果生鲜、数码产品、礼品文具、花店、车、音像制品、乐器",
            "零售,运动户外：运动服饰、运动装备、运动综合",
            "零售,其他：",

            "休闲娱乐,娱乐：网吧网咖、俱乐部、棋牌桌游、私人影院、DIY手工坊、艺术空间、轰趴",
            "休闲娱乐,运动：健身、乒乓球、桌球、舞蹈、瑜伽、跆拳道、羽毛球",
            "休闲娱乐,休闲：足疗、按摩、洗浴、茶馆、书店",
            "休闲娱乐,其他：其他",

            "生活配套及服务,生活服务：宠物服务、地产服务、婚礼服务、美容美发美甲、药店、齿科、中医养生、妇婴保健、儿童健康、摄影服务",
            "生活配套及服务,生活配套：通信营业厅、酒店、旅行社、语言教育、音乐培训、运动培训、美术培训、职业培训",
            "生活配套及服务,其他：其他",

            "儿童亲子,亲子购物：母婴用品、儿童服装、儿童玩具、儿童食品",
            "儿童亲子,亲子教育：早教中心、艺术培训、儿童英语、课外辅导、亲子阅读",
            "儿童亲子,亲子服务：儿童摄影、孕妇写真、亲子餐厅",
    };

    public static String[] mDefaultVillage = new String[]{
        "河北省石家庄市长安区,桃花源小区,116.472995,39.993743",
        "河北省石家庄市长安区,天上人间小区,116.472995,39.993743",
        "河北省石家庄市长安区,工业园小区,116.472995,39.993743",

    };

    //默认的生成的评价因素
    public List<Evaluate> getDefaultEvalute(){

        try{
            for (int i= 0;i<mDefautlEvalute.length;i++){
                String paramStr = mDefautlEvalute[i];
                String[] params = paramStr.split(",");
                String lv1 = params[0];
                String lv2 = params[1];
                String lv3 = params[2];
                String weight = params[3];
                Evaluate evaluate = createEvaluate(lv1,lv2,lv3,weight);
                mEvaluates.add(evaluate);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mEvaluates;
    }

    //默认生成的门店类别
    public List<Store> getDefaultAllStore(){

        for (int i=0;i<mDefaultStore.length;i++){
            String paramStr = mDefaultStore[i];
            String[] params = paramStr.split(",");
            String lv1 = params[0];
            String[] params2 = params[1].split("：");
            String lv2 = params2[0];
            if (params2.length!=1){
                String[] params3 = params2[1].split("、");
                for (int j=0;j<params3.length;j++){
                    String lv3 = params3[j];
                    Store store = createStore(lv1,lv2,lv3);
                    mStores.add(store);
                }
            }else{

            }

        }
        return mStores;
    }

    public static List<Village> getDefaultVillages(){

        for (int i=0;i<mDefaultVillage.length;i++){
            String paramsStr = mDefaultVillage[i];
            String[] params = paramsStr.split(",");
            String addres = params[0];
            String name = params[1];
            String lon = params[2];
            String lat = params[3];
            Village village =  createVillage(addres,name,lat+","+lon);
            mVillages.add(village);
        }
        return mVillages;
    }

    public static Store createStore(String lv1,String lv2,String lv3){
        Store store = new Store();
        store.setSTORE_ID(getRandomStore_ID());
        store.setLevel_First(lv1);
        store.setLevel_Sec(lv2);
        store.setLevel_Third(lv3);
        return store;
    }

    public static Evaluate createEvaluate(String lv1,String lv2,String lv3,String weight){
        Evaluate index = new Evaluate();
        index.setEvalute_id(getRandomIndex_ID());
        index.setLevel_First(lv1);
        index.setLevel_Sec(lv2);
        index.setLevel_Third(lv3);
        index.setWeight(weight);
        return index;
    }

    public static Village createVillage(String address,String name,String gps){
        Village village = new Village();
        village.setVillage_ID(getRandomVillage_ID());
        village.setVillage_Address(address);
        village.setVillage_Name(name);
        village.setmEvalutes(getSystemEvalute());
        village.setVillage_Position(gps);
        return village;
    }

    //生成随机小区 id
    public static String getRandomVillage_ID(){
        String strRand="V" ;
        for(int i=0;i<10;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        return strRand;
    }

    //生成随机index id
    public static String getRandomIndex_ID(){
        String strRand="I" ;
        for(int i=0;i<10;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        return strRand;
    }

    //生成随机index id
    public static String getRandomStore_ID(){
        String strRand="S" ;
        for(int i=0;i<10;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        return strRand;
    }

    //给小区随机注入系统评价
    public static List<Evaluate> getSystemEvalute(){
        List<Evaluate> evaluates = new ArrayList<>();
        evaluates.add(getRandomEvaluteByKey("消费水平"));
        evaluates.add(getRandomEvaluteByKey("小区成熟度"));
        evaluates.add(getRandomEvaluteByKey("同类竞争店铺情况"));
        evaluates.add(getRandomEvaluteByKey("互补类店铺情况"));
        evaluates.add(getRandomEvaluteByKey("范围内公交线路数目"));
        evaluates.add(getRandomEvaluteByKey("范围内公交站数量"));
        return evaluates;
    }

    //随机获取一个评价
    public static Evaluate getRandomEvaluteByKey(String key){
        List<Evaluate>  tempDatas = new ArrayList<>();
        for (int i =0;i<mEvaluates.size();i++){
            Evaluate evaluate = mEvaluates.get(i);
            if (evaluate.getLevel_Sec().equals(key)){
                tempDatas.add(evaluate);
            }
        }
        int index = new Random().nextInt(tempDatas.size());
        Evaluate evaluate = tempDatas.get(index);
        return evaluate;
    }
}
