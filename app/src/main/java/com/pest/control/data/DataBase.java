package com.pest.control.data;

import com.pest.control.R;
import com.pest.control.bean.MsgInfo;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    //树木病变类型
    String[] TreeLesionsType = new String[]{
            "斑点",
            "腐烂",
            "腐朽",
            "溃疡",
            "粉霉",
            "丛枝",
            "肿瘤",
            "枯萎",
            "黄化",
            "流脂或流胶",

    };

    //树木病变内容
    String[] TreeLesionsContext = new String[]{
            "多发生于叶片和果实上，病斑多为褐色，圆形、近圆形或不规则形，有时具有轮纹。叶上斑点扩大，会引起叶枯。如油桐黑斑病、杉木细菌性叶枯病。由真菌中的炭疽菌引起的斑点类病害称为炭疽病，病斑多为黑色或黑褐色，潮湿时病部会涌出粉红色胶状物",
            "发生于树木的各个部分。主要是由真菌或细菌分泌的酶分解细胞间的中胶层，使细胞分离，组织腐烂，并常带有酸臭味。如杨树腐烂病、油茶软腐病等",
            "专指树木根、干的木质部霉烂而言。腐朽的木质部松软易碎。由真菌引起。腐朽后期，病部往往长出蕈来。如松根朽病、木材腐朽等",
            "树木枝干的局部皮层坏死，形成凹陷病斑，周围稍隆起。如槐树和檫树溃疡病",
            "病部表面生有由某些病原真菌的表生菌丝体和孢子堆形成的白毛、黑色或锈黄色粉状物或霉层。如树木的白粉病、锈病、煤污病以及种实发霉",
            "树木的部分枝条上枝叶变小密集丛生。多由真菌或类菌质（原）体侵染所致。病原物的侵染活动，抑制了枝条顶芽的生长发育，腋芽或不定芽大量萌发，丛生许多细弱小枝，小枝的腋芽又发育成小枝，重复数次，导致枝叶密集成丛。病枝一般垂直于地面，向上生长，节间变短，叶形变小。病枝陆续枯死",
            "树木的根、干、枝条局部细胞增生而形成肿瘤。多由真菌或细菌引起。如松瘤锈病、柳杉瘿瘤病等",
            "一般专指由真菌或细菌引起的维管束病害而言。病菌侵入树木根部或干部维管束组织，沿维管束扩展，使维管束堵塞或产生毒素破坏维管束组织，使维管束失去输导功能，造成成树木整株或局部枝叶枯萎。如油桐枯萎病、木麻黄枯萎病。",
            "叶片大部或全部褪绿变成黄色或黄白色，称为黄化；叶片色泽深浅不匀，浓绿和浅绿相间称为花叶。这类症状大多由营养失调或类菌质（原）体和病毒所引起。如多种树木的黄化病和花叶病。",
            "树木的芽、枝、干流出树脂或树胶，致使树木生长衰弱或芽梢枯死，称为流脂病或流胶病。如国外松芽流脂病、桃树流胶病。国外松芽流脂病是由于土壤中缺硼，桃树流胶病的病原则很复杂",
    };

    //树木病变图片id
    //链接
    public String[] DEFAULT_URL = new String[]{
            "https://www.bbaqw.com/cs/163686.htm",
            "https://www.bbaqw.com/cs/163686.htm",
            "https://www.bbaqw.com/cs/163686.htm",
            "https://www.bbaqw.com/cs/163686.htm",
            "https://www.bbaqw.com/cs/163686.htm",
            "https://www.bbaqw.com/cs/163686.htm",
            "https://www.bbaqw.com/cs/163686.htm",
            "https://www.bbaqw.com/cs/163686.htm",
            "https://www.bbaqw.com/cs/163686.htm",
            "https://www.bbaqw.com/cs/163686.htm",

    };

    //图片
    public int[] DEFAULT_PIC = new int[]{
            R.drawable.treelesion_1,
            R.drawable.treelesion_2,
            R.drawable.treelesion_3,
            R.drawable.treelesion_4,
            R.drawable.treelesion_5,
            R.drawable.treelesion_1,
            R.drawable.treelesion_1,
            R.drawable.treelesion_1,
            R.drawable.treelesion_1,
            R.drawable.treelesion_1,
    };

    //树木病变类型
    String[] PestType = new String[]{
            "介壳虫",
            "吊丝虫",
            "卷叶蛾",
            "天牛",
    };

    //树木病变内容
    String[] PestContext = new String[]{
            "介壳虫是柑桔、柚子上的一类重要害虫，常见的有红圆蚧、褐圆蚧、康片蚧、矢尖蚧和吹绵蚧等。介壳虫为害叶片、枝条和果实。介壳虫往往是雄性有翅，能飞，雌虫和幼虫一经羽化，终生寄居在枝叶或果实上，造成叶片发黄、枝梢枯萎、树势衰退，且易诱发煤烟病。",
            "吊丝虫，Plutella xylostella (Linnaeus)，为属鳞翅目，菜蛾科菜蛾属的一种昆虫。分布在中国各地区。主要危害油菜、甘蓝、白菜、青菜花、萝卜、花椰菜等十字花科作物。低龄幼虫仅食叶肉，留下表皮。在菜叶上形成一个个透明斑，即“开天窗”；3-4龄幼虫可将菜叶吃成网状，或仅剩下叶脉。在苗期常集中心叶为害，影响包心。在留种株上为害嫩茎、幼荚和籽粒",
            "一种昆虫，成虫身体小，前翅宽。幼虫吃植物叶片，或钻进果实里面吃果实，有的把叶片卷成筒状，在里面吐丝做茧。危害果树和其他农作物。通称卷叶虫。",
            "天牛是多食亚目天牛科昆虫的总称，咀嚼式口器，有很长的触角，常常超过身体的长度，全世界约有超过20,000种。有一些种类属于害虫，其幼虫生活于木材中，可能对树或建筑物造成危害。",
    };

    //链接
    public String[] PEST_DEFAULT_URL = new String[]{
            "https://baike.baidu.com/item/介壳虫/6267627?fr=aladdin",
            "https://baike.baidu.com/item/吊丝虫/148497?fr=aladdin",
            "https://baike.baidu.com/item/卷叶蛾/148497?fr=aladdin",
            "https://baike.baidu.com/item/天牛/148497?fr=aladdin",

    };

    //图片
    public int[] PEST_DEFAULT_PIC = new int[]{
            R.drawable.pest_1,
            R.drawable.pest_2,
            R.drawable.pest_3,
            R.drawable.pest_4,
    };


    public List<MsgInfo> mMsgInfoList = new ArrayList<>();
    public List<MsgInfo> mPestInfoList = new ArrayList<>();

    public DataBase(){
        for (int i=0;i<TreeLesionsType.length;i++){
            MsgInfo info = new MsgInfo();
            info.setTitle(TreeLesionsType[i]);
            info.setContent(TreeLesionsContext[i]);
            info.setmPicId(DEFAULT_PIC[i]);
            info.setUrl(DEFAULT_URL[i]);
            mMsgInfoList.add(info);
        }

        for (int i=0;i<PestType.length;i++){
            MsgInfo info = new MsgInfo();
            info.setTitle(PestType[i]);
            info.setContent(PestContext[i]);
            info.setmPicId(PEST_DEFAULT_PIC[i]);
            info.setUrl(PEST_DEFAULT_URL[i]);
            mPestInfoList.add(info);
        }
    }
}
