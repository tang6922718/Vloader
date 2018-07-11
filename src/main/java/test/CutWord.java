package test;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.dictionary.DictionaryFactory;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.apdplat.word.util.WordConfTools;

import java.util.List;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/6/7-17:05
 * Modified By:
 */
public class CutWord {
    public static void main(String[] args) {
        automaticSelection("湖南省常德市汉寿县军山铺镇军山铺高速公路管理处");
    }

    public static void automaticSelection(String title) {
        WordConfTools.set("dic.path", "classpath:dic.txt，d:/custom_dic");
        DictionaryFactory.reload();//更改词典路径之后，重新加载词典
        //移除停用词进行分词[常德, 市, 武陵, 区, 白马, 湖, 街道, 办事, 处, 西堤, 社区, 路]
//        List<Word> list = WordSegmenter.seg(title, SegmentationAlgorithm.FullSegmentation);
//        System.out.println(list);
        //保留停用词[常德市, 武陵区, 白马湖, 街道, 办事处, 西堤, 社区, 人民路]
        List<Word> lists = WordSegmenter.segWithStopWords(title);
        System.out.println(lists);

    }
}
