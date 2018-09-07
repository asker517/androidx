package me.panpf.androidxkt.test.content

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import me.panpf.androidxkt.content.*
import me.panpf.androidxkt.runInUI
import me.panpf.androidxkt.test.util.WeakAsyncTaskTestActivity
import me.panpf.androidxkt.test.widget.ToastTestActivity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class ClipboardTest {

    private fun prepareContext(): Context {
        val context = InstrumentationRegistry.getContext()

        /*
         * 在测试环境首次获取 ClipboardManager 时 Android 内部会创建 ClipboardManager，并创建 Handler，
         * 由于测试代码是在异步线程执行的，因此这里就涉及到了在异步线程创建 Handler 的问题
         */
        val countDownLatch = CountDownLatch(1)
        runInUI{
            context.getClipData()
            countDownLatch.countDown()
        }
        try {
            countDownLatch.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return context
    }

    @Test
    fun testLabel() {
        val context = prepareContext()

        context.copyText("clipLabel", TEST_TEXT)
        Assert.assertEquals(context.getClipDataLabel(), "clipLabel")
    }

    @Test
    fun testText() {
        val context = prepareContext()

        context.copyText(TEST_TEXT)
        Assert.assertEquals(context.getClipText(), TEST_TEXT)

        context.copyText(arrayOf<CharSequence>(TEST_TEXT, TEST_TEXT2))
        val texts = context.getClipTexts()
        Assert.assertEquals(if (texts != null) texts[0] else "null", TEST_TEXT)
        Assert.assertEquals(if (texts != null) texts[1] else "null", TEST_TEXT2)
    }

    @Test
    fun testHtmlText() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val context = prepareContext()

            context.copyHtmlText(TEST_HTML_TEXT, TEST_HTML_HTML)
            val clipHtmlText = context.getClipHtmlText()
            Assert.assertEquals(if (clipHtmlText != null) clipHtmlText.text else "null", TEST_HTML_TEXT)
            Assert.assertEquals(if (clipHtmlText != null) clipHtmlText.htmlText else "null", TEST_HTML_HTML)

            context.copyHtmlText(arrayOf(ClipHtmlText(TEST_HTML_TEXT, TEST_HTML_HTML), ClipHtmlText(TEST_HTML_TEXT2, TEST_HTML_HTML2)))
            val htmlTexts = context.getClipHtmlTexts()

            val clipHtmlText1 = if (htmlTexts != null) htmlTexts[0] else null
            Assert.assertEquals(if (clipHtmlText1 != null) clipHtmlText1.text else "null", TEST_HTML_TEXT)
            Assert.assertEquals(if (clipHtmlText1 != null) clipHtmlText1.htmlText else "null", TEST_HTML_HTML)

            val clipHtmlText2 = if (htmlTexts != null) htmlTexts[1] else null
            Assert.assertEquals(if (clipHtmlText2 != null) clipHtmlText2.text else "null", TEST_HTML_TEXT2)
            Assert.assertEquals(if (clipHtmlText2 != null) clipHtmlText2.htmlText else "null", TEST_HTML_HTML2)
        }
    }

    @Test
    fun testIntent() {
        val context = prepareContext()

        val intent = Intent(context, ToastTestActivity::class.java)
        context.copyIntent(intent)

        val result = context.getClipIntent()
        Assert.assertEquals(result?.getComponent().toString(), intent.component.toString())

        val intent2 = Intent(context, WeakAsyncTaskTestActivity::class.java)
        context.copyIntent(arrayOf(intent, intent2))

        val results = context.getClipIntents()
        Assert.assertEquals(results?.get(0)?.getComponent().toString(), intent.component.toString())
        Assert.assertEquals(results?.get(1)?.getComponent().toString(), intent2.component.toString())
    }

    @Test
    fun testRawUri() {
        val context = prepareContext()

        val uri = Uri.parse("https://www.github.com")
        context.copyRawUri(uri)

        val result = context.getClipUri()
        Assert.assertEquals(result?.mimeType, ClipDescription.MIMETYPE_TEXT_URILIST)
        Assert.assertEquals(result?.uri.toString(), uri.toString())

        val uri2 = Uri.parse("https://www.youtube.com")
        context.copyRawUri(arrayOf(uri, uri2))

        val results = context.getClipUris()
        Assert.assertEquals(results?.get(0)?.mimeType, ClipDescription.MIMETYPE_TEXT_URILIST)
        Assert.assertEquals(results?.get(0)?.uri.toString(), uri.toString())
        Assert.assertEquals(results?.get(1)?.mimeType, ClipDescription.MIMETYPE_TEXT_URILIST)
        Assert.assertEquals(results?.get(1)?.uri.toString(), uri2.toString())
    }

    @Test
    fun testMimeTypeUri() {
        val context = prepareContext()

        val uri = Uri.parse("https://www.github.com")
        context.copyMimeTypeUri("app/android", uri)

        val result = context.getClipUri()
        Assert.assertEquals(result?.mimeType, "app/android")
        Assert.assertEquals(result?.uri.toString(), uri.toString())

        val uri2 = Uri.parse("https://www.youtube.com")
        context.copyMimeTypeUri("app/android", arrayOf(uri, uri2))

        val results = context.getClipUris()
        Assert.assertEquals(results?.get(0)?.mimeType, "app/android")
        Assert.assertEquals(results?.get(0)?.uri.toString(), uri.toString())
        Assert.assertEquals(results?.get(1)?.mimeType, "app/android")
        Assert.assertEquals(results?.get(1)?.uri.toString(), uri2.toString())
    }

    @Test
    fun testUri() {
        val context = prepareContext()

        val uri = Uri.parse("https://www.github.com")
        context.copyUri(uri)

        val result = context.getClipUri()
        Assert.assertEquals(result?.mimeType, ClipDescription.MIMETYPE_TEXT_URILIST)
        Assert.assertEquals(result?.uri.toString(), uri.toString())

        val uri2 = Uri.parse("https://www.youtube.com")
        context.copyUri(arrayOf(uri, uri2))

        val results = context.getClipUris()
        Assert.assertEquals(results?.get(0)?.mimeType, ClipDescription.MIMETYPE_TEXT_URILIST)
        Assert.assertEquals(results?.get(0)?.uri.toString(), uri.toString())
        Assert.assertEquals(results?.get(1)?.mimeType, ClipDescription.MIMETYPE_TEXT_URILIST)
        Assert.assertEquals(results?.get(1)?.uri.toString(), uri2.toString())
    }

    @Test
    fun testListener() {
        val context = prepareContext()

        val result = arrayOfNulls<String>(1)
        val listener = ClipboardManager.OnPrimaryClipChangedListener { result[0] = "onPrimaryClipChanged" }

        context.addPrimaryClipChangedListener(listener)
        context.copyText("Hello Word")
        // Callback will be delayed
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Assert.assertEquals(result[0], "onPrimaryClipChanged")

        result[0] = "None"
        context.removePrimaryClipChangedListener(listener)
        context.copyText("Hello Word")
        // Callback will be delayed
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Assert.assertEquals(result[0], "None")
    }

    @Test
    fun testMultiType() {
        val context = prepareContext()

        val text = ClipPlainText(TEST_TEXT)
        val htmlText = ClipHtmlText(TEST_HTML_TEXT, TEST_HTML_HTML)
        val intent = ClipIntent(Intent(context, ToastTestActivity::class.java))
        val uri = ClipUri("app/android", Uri.parse("https://www.youtube.com"))

        context.copyContents(arrayOf(text, htmlText, intent, uri))

        val results = context.getClipDataContents()

        val textResult = results?.get(0) as ClipPlainText
        Assert.assertEquals(textResult.text, text.text)

        val htmlTextResult = results[1] as ClipHtmlText
        Assert.assertEquals(htmlTextResult.text, htmlText.text)
        Assert.assertEquals(htmlTextResult.htmlText, htmlText.htmlText)

        val intentResult = results[2] as ClipIntent
        Assert.assertEquals(intentResult.intent.component.toString(), intent.intent.component.toString())

        val uriResult = results[3] as ClipUri
        Assert.assertEquals(uriResult.mimeType, uri.mimeType)
        Assert.assertEquals(uriResult.uri.toString(), uri.uri.toString())
    }

    @Test
    fun testClean() {
        if (Build.VERSION.SDK_INT >= 28) {
            val context = prepareContext()

            context.copyText("Hello Word")
            Assert.assertEquals(context.getClipText(), "Hello Word")

            context.clearClip()
            Assert.assertNull(context.getClipText())
        }
    }

    companion object {

        private val TEST_TEXT = "我也在奢望，奢望有一天，你能来找我"
        private val TEST_TEXT2 = "你还会回来吗？应该不会了。"
        private val TEST_HTML_TEXT = "短文学伤感文章情感日志心情日记散文精选诗歌大全经典语句专题长篇小说投稿网址积分商城"
        private val TEST_HTML_HTML = "<ul>\n" +
                "\t\t<li class=\"thiscase\"><a href=\"/sanwen/suibi/\">散文随笔</a></li>\n" +
                "\t\t<li><a href=\"/qinggan/meiwen/\">美文欣赏</a></li>\n" +
                "\t\t<li><a href=\"/yuju/shanggan/\">伤感的句子</a></li>\n" +
                "\t\t<li><a href=\"/yuju/youmei/\">优美的句子</a></li>\n" +
                "\t\t<li><a href=\"/yuju/weimei/\">唯美的句子</a></li>\n" +
                "\t\t<li><a href=\"/yuju/shangxin/\">伤心的句子</a></li>\n" +
                "\t\t<li><a href=\"/duanwen/mingyan/\">名言名句</a></li>\n" +
                "\t\t<li><a href=\"/yuju/xiangnian/\">想念的句子</a></li>\n" +
                "\t\t<li><a href=\"/duanwen/lizhi/\">励志签名</a></li>\n" +
                "\t\t<li><a href=\"/duanwen/gerenqianming/\">个人签名</a></li>\n" +
                "\t\t<li><a href=\"/huayu/ganren/\">感人的话</a></li>\n" +
                "\t\t<li class=\"thiscase\"><a href=\"/yulu/aiqing/\">爱情语录</a></li>\n" +
                "\t\t<li><a href=\"/huayu/biaobai/\">表白的话</a></li>\n" +
                "\t\t<li><a href=\"/juzi/beishang/\">悲伤的句子</a></li>\n" +
                "\t\t<li><a href=\"/yulu/gaoxiao/\">搞笑语录</a></li>\n" +
                "\t\t<li><a href=\"/yulu/aiqingxuanyan/\">爱情宣言</a></li>\n" +
                "\t\t<li><a href=\"/juzi/biaobai/\">表白的句子</a></li>\n" +
                "\t\t<li><a href=\"/duanwen/geyan/\">人生格言</a></li>\n" +
                "\t\t<li><a href=\"/yulu/yijuhua/\">一句话经典语录</a></li>\n" +
                "\t\t<li><a href=\"/huayu/lizhi/\">励志的话</a></li>\n" +
                "\t\t<li><a href=\"/yulu/shangxinqianming/\">伤心的个性签名</a></li>\n" +
                "\t\t<li><a href=\"/huayu/zheli/\">有哲理的话</a></li>\n" +
                "\t</ul>\n"
        private val TEST_HTML_TEXT2 = "散文随笔美文欣赏伤感的句子优美的句子唯美的句子伤心的句子名言名句想念的句子励志签名个人签名感人的话爱情语录表白的话悲伤的句子搞笑语录爱情宣言表白的句子人生格言一句话经典语录励志的话伤心的个性签名有哲理的话"
        private val TEST_HTML_HTML2 = "<ul>\n" +
                "    <li><a href=\"/\" >短文学</a></li>\n" +
                "    <li><a href=\"/shanggan/\" >伤感文章</a></li>\n" +
                "    <li><a href=\"/qinggan/\" >情感日志</a></li>\n" +
                "    <li><a href=\"/diary/\" >心情日记</a></li>\n" +
                "    <li><a href=\"/sanwen/\" >散文精选</a></li>\n" +
                "    <li><a href=\"/shige/\" >诗歌大全</a></li>\n" +
                "    <li><a href=\"/yuju/\" >经典语句</a></li>\n" +
                "    <li><a href=\"/haowenzhang/\" >专题</a></li>\n" +
                "    <li><a href=\"/book/\">长篇小说</a></li>\n" +
                "    <li><a href=\"/tougao.html\">投稿网址</a></li>\n" +
                "    <li><a href=\"/shop\">积分商城</a></li>\n" +
                "  </ul>\n"
    }
}