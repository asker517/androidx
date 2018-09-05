/*
 * Copyright (C) 2018 Peng fei Pan <panpfpanpf@outlook.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.panpf.androidxkt.test.crypto

import me.panpf.javaxkt.crypto.*
import org.junit.Assert
import org.junit.Test
import java.security.InvalidKeyException
import javax.crypto.BadPaddingException
import javax.crypto.IllegalBlockSizeException

class AesTest {

    companion object {
        private const val SOURCE = "小红那年七岁，她跟着爸妈去赶集，站在一个卖童装的摊位旁边，盯着那条裙子，无论如何都不肯走。\n" +
                "\n" +
                "她太想要这样一条裙子了，或者说白了，她想要一件新衣服，一件不是妈妈手工做的，而是商店里买来的衣服。\n" +
                "\n" +
                "她听见已经走出了几步的妈妈跟站得更远的爸爸说，“这孩子到底随谁呢？才这么大就这么臭美？我们家里人哪有这种样子的？能不能要点脸？”\n" +
                "\n" +
                "爸爸已经非常烦躁，走上前来，不由分说劈头给了她一个耳光，“就知道穿穿穿，打扮那么排场出去干什么？”\n" +
                "\n" +
                "她哇地一声哭了，妈妈倒是很恼怒地跑过来拉住爸爸，“你有病吗？谁让你打她了？”\n" +
                "\n" +
                "可是裙子最终还是没买。她反而成了全家人的笑柄，一直到很多年后，妈妈提起她小时候，还是笑得肚子疼，“一丁点儿大，臭美得很！”\n" +
                "\n" +
                "然后有兄弟姐妹在旁边起哄揶揄她"

        private const val SOURCE_NO_PADDING = "小红那年七岁，她跟着爸妈去赶集，站在一个卖童装的摊位旁边，盯着那"
    }

    @Test
    @Throws(InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun testDefaultConfig() {
        val key = createAesKey(128)
        val decryptResult = SOURCE.toByteArray().aesEncrypt(AES, key).aesDecryptToString(AES, key)
        Assert.assertEquals("testDefaultConfig", SOURCE, decryptResult)
    }

    @Test
    @Throws(InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun testDefaultConfigWithBase64() {
        val key = createAesKey(128)
        val decryptResult = SOURCE.toByteArray().aesEncryptToBase64(AES, key).aesDecryptToStringFromBase64(AES, key)
        Assert.assertEquals("testDefaultConfigWithBase64", SOURCE, decryptResult)
    }


    @Test
    @Throws(InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun testEcbNoPadding() {
        val key = createAesKey(128)
        val decryptResult = SOURCE_NO_PADDING.toByteArray().aesEncrypt(AES_ECB_NO, key).aesDecryptToString(AES_ECB_NO, key)
        Assert.assertEquals("testEcbNoPadding", SOURCE_NO_PADDING, decryptResult)
    }

    @Test
    @Throws(InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun testEcbPKCS5Padding() {
        val key = createAesKey(128)
        val decryptResult = SOURCE.toByteArray().aesEncrypt(AES_ECB_PKCS5, key).aesDecryptToString(AES_ECB_PKCS5, key)
        Assert.assertEquals("testEcbPKCS5Padding", SOURCE, decryptResult)
    }

    @Test
    @Throws(InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun testEcbPKCS7Padding() {
        val key = createAesKey(128)
        val decryptResult = SOURCE.toByteArray().aesEncrypt(AES_ECB_PKCS7, key).aesDecryptToString(AES_ECB_PKCS7, key)
        Assert.assertEquals("testEcbPKCS7Padding", SOURCE, decryptResult)
    }

    @Test
    @Throws(InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun testEcbISO10126Padding() {
        val key = createAesKey(128)
        val decryptResult = SOURCE.toByteArray().aesEncrypt(AES_ECB_ISO10126, key).aesDecryptToString(AES_ECB_ISO10126, key)
        Assert.assertEquals("testEcbISO10126Padding", SOURCE, decryptResult)
    }


    @Test
    @Throws(InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun testCbcNoPadding() {
        val key = createAesKey(128)
        val decryptResult = SOURCE_NO_PADDING.toByteArray().aesEncrypt(AES_CBC_NO, key).aesDecryptToString(AES_CBC_NO, key)
        Assert.assertEquals("testCbcNoPadding", SOURCE_NO_PADDING, decryptResult)
    }

    @Test
    @Throws(InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun testCbcPKCS5Padding() {
        val key = createAesKey(128)
        val decryptResult = SOURCE.toByteArray().aesEncrypt(AES_CBC_PKCS5, key).aesDecryptToString(AES_CBC_PKCS5, key)
        Assert.assertEquals("testCbcPKCS5Padding", SOURCE, decryptResult)
    }

    @Test
    @Throws(InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun testCbcPKCS7Padding() {
        val key = createAesKey(128)
        val decryptResult = SOURCE.toByteArray().aesEncrypt(AES_CBC_PKCS7, key).aesDecryptToString(AES_CBC_PKCS7, key)
        Assert.assertEquals("testCbcPKCS7Padding", SOURCE, decryptResult)
    }

    @Test
    @Throws(InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun testCbcISO10126Padding() {
        val key = createAesKey(128)
        val decryptResult = SOURCE.toByteArray().aesEncrypt(AES_CBC_ISO10126, key).aesDecryptToString(AES_CBC_ISO10126, key)
        Assert.assertEquals("testCbcISO10126Padding", SOURCE, decryptResult)
    }


    @Test
    @Throws(InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun testErrorPassword() {
        val key = createAesKey(128)
        val errorKey = createAesKey(128)

        val encryptResult = SOURCE.aesEncryptToBase64(AES_CBC_PKCS5, key)
        var errorDecryptResult: String? = null
        try {
            errorDecryptResult = encryptResult.aesDecryptToStringFromBase64(AES_CBC_PKCS5, errorKey)
        } catch (e: BadPaddingException) {
            //            e.printStackTrace();
        }

        Assert.assertNotEquals("testErrorPassword", SOURCE, errorDecryptResult)
    }

    @Test
    fun testCreateKeyBySeed() {
        val seed = "" + System.currentTimeMillis()

        val key1 = seed.createAesKeyByPassword(16).toBase64()
        val key2 = seed.createAesKeyByPassword(16).toBase64()

        Assert.assertEquals("testCreateKeyBySeed", key1, key2)
    }
}