package org.seimicrawler.xpath.expr;

import org.seimicrawler.xpath.BaseTest;
import org.seimicrawler.xpath.antlr.XpathLexer;
import org.seimicrawler.xpath.antlr.XpathParser;
import org.seimicrawler.xpath.core.XValue;
import org.seimicrawler.xpath.core.XpathProcessor;
import org.seimicrawler.xpath.exception.DoFailOnErrorHandler;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author github.com/zhegexiaohuozi seimimaster@gmail.com
 * @since 2017/12/6.
 */
public class ExprTest extends BaseTest {

    private Elements root;
    @Before
    public void init() throws Exception {
        //  https://book.douban.com/tag/%E4%BA%92%E8%81%94%E7%BD%91
        URL t = Resources.getResource("d_test.html");
        File dBook = new File(t.toURI());
        String context = Files.toString(dBook, Charset.forName("utf8"));
        root = Jsoup.parse(context).children();
    }

    @Test
    public void exp(){
        String xpath = "count(//*[@class])";
        CharStream input = CharStreams.fromString(xpath);
        XpathLexer lexer = new XpathLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        XpathParser parser = new XpathParser(tokens);
        parser.setErrorHandler(new DoFailOnErrorHandler());
        ParseTree tree = parser.main();
        XpathProcessor processor = new XpathProcessor(root);
        XValue value = processor.visit(tree);
        logger.info("visit res = {}",value);
    }

    @Test
    public void roundHalfUp(){
        int x = new BigDecimal("5.53").setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
        Assert.assertEquals(6,x);
    }
}