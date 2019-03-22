package lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Maintest {

    public static void main(String[] args) throws IOException {
//        String text = "黄初三年，余朝京师，还济洛川。古人有言，斯水之神，名曰宓妃。感宋玉对楚王神女之事，遂作斯赋。其辞曰："
//                + "余从京域，言归东藩。背伊阙，越轘辕，经通谷，陵景山。日既西倾，车殆马烦。尔乃税驾乎蘅皋，秣驷乎芝田，容与乎阳林，流眄乎洛川。于是精移神骇，忽焉思散。俯则未察，仰以殊观，睹一丽人，于岩之畔。乃援御者而告之曰：“尔有觌于彼者乎？彼何人斯？若此之艳也！”御者对曰：“臣闻河洛之神，名曰宓妃。然则君王所见，无乃是乎？其状若何？臣愿闻之。”"
//                + "余告之曰：“其形也，翩若惊鸿，婉若游龙。荣曜秋菊，华茂春松。髣髴兮若轻云之蔽月，飘飖兮若流风之回雪。远而望之，皎若太阳升朝霞；迫而察之，灼若芙蕖";
        String en = "Newsgd.com is the premier online source of Guangdong news and information, fully displaying Guangdong through channels including Guangdong News, Guangdong ";
        // useAnalyze(en);
        Analyzer anz = new StandardAnalyzer();

        IndexWriterConfig conf = new IndexWriterConfig(anz);
        conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
        
        //存到文件中 也可以存内存中 RAMDirectory
        Directory dir = FSDirectory.open(new File("d://lucenetest").toPath());
        
        IndexWriter  writer=new IndexWriter(dir, conf);
        Document doc=new Document();
        
        FieldType type=new org.apache.lucene.document.FieldType();
        
        type.setTokenized(true);
        type.setStored(true);
        type.setIndexOptions(IndexOptions.DOCS);
        type.setStoreTermVectors(true);
        type.setStoreTermVectorOffsets(true);
        type.freeze();
        Field field=new  Field("sxc4", en, type);
        
        
        doc.add(field);
        writer.addDocument(doc);
        writer.flush();
        writer.commit();
        writer.close();
        dir.close();
       
    }

    private static void useAnalyze(String en) {
        try (Analyzer anz = new StandardAnalyzer()) {
            TokenStream ts = anz.tokenStream("中文", en);

            ts.reset();
            CharTermAttribute cta = ts.getAttribute(CharTermAttribute.class);

            while (ts.incrementToken()) {
                System.out.print(cta.toString() + "|");
            }
            ts.end();
            ts.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
