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

package me.panpf.androidx.content;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

import me.panpf.javax.lang.Numberx;

@SuppressWarnings("WeakerAccess")
public class Clipboardx {

    /**
     * copy
     */
    public static void copy(@NotNull Context context, @NotNull ClipData clipData) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null) throw new IllegalStateException("ClipboardManager not found");

        clipboardManager.setPrimaryClip(clipData);
    }


    /**
     * Copy text
     */
    public static void copyText(@NotNull Context context, @NotNull CharSequence label, @NotNull CharSequence[] texts) {
        if (texts.length <= 0) return;
        ClipData data = ClipData.newPlainText(label, texts[0]);
        if (texts.length > 1) {
            for (int index : Numberx.untilTo(1, texts.length)) {
                data.addItem(new ClipData.Item(texts[index]));
            }
        }
        copy(context, data);
    }

    /**
     * Copy text
     */
    public static void copyText(@NotNull Context context, @NotNull CharSequence[] texts) {
        copyText(context, "text", texts);
    }

    /**
     * Copy text
     */
    public static void copyText(@NotNull Context context, @NotNull CharSequence label, @NotNull CharSequence text) {
        copy(context, ClipData.newPlainText(label, text));
    }

    /**
     * Copy text
     */
    public static void copyText(@NotNull Context context, @NotNull CharSequence text) {
        copyText(context, "text", text);
    }


    /**
     * Copy html text
     */
    public static void copyHtmlText(@NotNull Context context, @NotNull CharSequence label, @NotNull ClipHtmlText[] htmlContents) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (htmlContents.length <= 0) return;
            ClipHtmlText htmlContent = htmlContents[0];
            ClipData data = ClipData.newHtmlText(label, htmlContent.text, htmlContent.htmlText);
            if (htmlContents.length > 1) {
                for (int index : Numberx.untilTo(1, htmlContents.length)) {
                    htmlContent = htmlContents[index];
                    data.addItem(new ClipData.Item(htmlContent.text, htmlContent.htmlText));
                }
            }
            copy(context, data);
        }
    }

    /**
     * Copy html text
     */
    public static void copyHtmlText(@NotNull Context context, @NotNull ClipHtmlText[] htmlContents) {
        copyHtmlText(context, "htmlText", htmlContents);
    }

    /**
     * Copy html text
     */
    public static void copyHtmlText(@NotNull Context context, @NotNull CharSequence label, @NotNull CharSequence text, @NotNull String htmlText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            copy(context, ClipData.newHtmlText(label, text, htmlText));
        }
    }

    /**
     * Copy html text
     */
    public static void copyHtmlText(@NotNull Context context, @NotNull CharSequence text, @NotNull String htmlText) {
        copyHtmlText(context, "htmlText", text, htmlText);
    }


    /**
     * Copy intent
     */
    public static void copyIntent(@NotNull Context context, @NotNull CharSequence label, @NotNull Intent[] intents) {
        if (intents.length <= 0) return;
        ClipData data = ClipData.newIntent(label, intents[0]);
        if (intents.length > 1) {
            for (int index : Numberx.untilTo(1, intents.length)) {
                data.addItem(new ClipData.Item(intents[index]));
            }
        }
        copy(context, data);
    }

    /**
     * Copy intent
     */
    public static void copyIntent(@NotNull Context context, @NotNull Intent[] intents) {
        copyIntent(context, "intent", intents);
    }

    /**
     * Copy intent
     */
    public static void copyIntent(@NotNull Context context, @NotNull CharSequence label, @NotNull Intent intent) {
        copy(context, ClipData.newIntent(label, intent));
    }

    /**
     * Copy intent
     */
    public static void copyIntent(@NotNull Context context, @NotNull Intent intent) {
        copyIntent(context, "intent", intent);
    }


    /**
     * Copy uri
     */
    public static void copyUri(@NotNull Context context, @NotNull CharSequence label, @NotNull Uri[] uris) {
        if (uris.length <= 0) return;
        ClipData data = ClipData.newUri(context.getContentResolver(), label, uris[0]);
        if (uris.length > 1) {
            for (int index : Numberx.untilTo(1, uris.length)) {
                data.addItem(new ClipData.Item(uris[index]));
            }
        }
        copy(context, data);
    }

    /**
     * Copy uri
     */
    public static void copyUri(@NotNull Context context, @NotNull Uri[] uris) {
        copyUri(context, "uri", uris);
    }

    /**
     * Copy uri
     */
    public static void copyUri(@NotNull Context context, @NotNull CharSequence label, @NotNull Uri uri) {
        copy(context, ClipData.newUri(context.getContentResolver(), label, uri));
    }

    /**
     * Copy uri
     */
    public static void copyUri(@NotNull Context context, @NotNull Uri uri) {
        copyUri(context, "uri", uri);
    }


    /**
     * Copy raw uri
     */
    public static void copyRawUri(@NotNull Context context, @NotNull CharSequence label, @NotNull Uri[] uris) {
        if (uris.length <= 0) return;
        ClipData data = ClipData.newRawUri(label, uris[0]);
        if (uris.length > 1) {
            for (int index : Numberx.untilTo(1, uris.length)) {
                data.addItem(new ClipData.Item(uris[index]));
            }
        }
        copy(context, data);
    }

    /**
     * Copy raw uri
     */
    public static void copyRawUri(@NotNull Context context, @NotNull Uri[] uris) {
        copyRawUri(context, "rawUri", uris);
    }

    /**
     * Copy raw uri
     */
    public static void copyRawUri(@NotNull Context context, @NotNull CharSequence label, @NotNull Uri uri) {
        copy(context, ClipData.newRawUri(label, uri));
    }

    /**
     * Copy raw uri
     */
    public static void copyRawUri(@NotNull Context context, @NotNull Uri uri) {
        copyRawUri(context, "rawUri", uri);
    }


    /**
     * Copy uri
     */
    public static void copyMimeTypeUri(@NotNull Context context, @NotNull CharSequence label, @NotNull String mimeType, @NotNull Uri[] uris) {
        if (uris.length <= 0) return;
        ClipData data = new ClipData(label, new String[]{mimeType}, new ClipData.Item(uris[0]));
        if (uris.length > 1) {
            for (int index : Numberx.untilTo(1, uris.length)) {
                data.addItem(new ClipData.Item(uris[index]));
            }
        }
        copy(context, data);
    }

    /**
     * Copy uri
     */
    public static void copyMimeTypeUri(@NotNull Context context, @NotNull String mimeType, @NotNull Uri[] uris) {
        copyMimeTypeUri(context, "mimeTypeUri", mimeType, uris);
    }

    /**
     * Copy uri
     */
    public static void copyMimeTypeUri(@NotNull Context context, @NotNull CharSequence label, @NotNull String mimeType, @NotNull Uri uri) {
        copy(context, new ClipData(label, new String[]{mimeType}, new ClipData.Item(uri)));
    }

    /**
     * Copy uri
     */
    public static void copyMimeTypeUri(@NotNull Context context, @NotNull String mimeType, @NotNull Uri uri) {
        copyMimeTypeUri(context, "mimeTypeUri", mimeType, uri);
    }

    /**
     * Copy multi type content
     */
    public static void copyContents(@NotNull Context context, @NotNull CharSequence label, @NotNull ClipContent[] contents) {
        if (contents.length <= 0) return;

        String[] mimeTypes = new String[contents.length];
        for (int index : Numberx.untilTo(0, contents.length)) {
            mimeTypes[index] = contents[index].mimeType;
        }

        ClipData data = new ClipData(label, mimeTypes, contents[0].toItem());
        for (int index : Numberx.untilTo(1, contents.length)) {
            data.addItem(contents[index].toItem());
        }

        copy(context, data);
    }

    /**
     * Copy multi type content
     */
    public static void copyContents(@NotNull Context context, @NotNull ClipContent[] contents) {
        copyContents(context, "contents", contents);
    }

    /**
     * Get current clip data
     */
    @Nullable
    public static ClipData get(@NotNull Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null) throw new IllegalStateException("ClipboardManager not found");
        return clipboardManager.getPrimaryClip();
    }

    /**
     * Get current clip label
     */
    @Nullable
    public static CharSequence getLabel(@NotNull Context context) {
        ClipData data = get(context);
        return data != null ? data.getDescription().getLabel() : null;
    }

    /**
     * Get current clip all content
     */
    @Nullable
    public static ClipContent[] getContents(@NotNull Context context) {
        ClipData data = get(context);
        if (data == null || data.getItemCount() <= 0 || data.getDescription().getMimeTypeCount() <= 0)
            return null;

        ClipDescription clipDescription = data.getDescription();
        List<ClipContent> objectList = new LinkedList<>();
        for (int index : Numberx.untilTo(0, data.getItemCount())) {
            ClipData.Item item = data.getItemAt(index);
            // Usually multiple items have only one mimeType
            String mimeType = clipDescription.getMimeType(Math.min(index, clipDescription.getMimeTypeCount() - 1));
            if (ClipDescription.MIMETYPE_TEXT_PLAIN.equals(mimeType)) {
                objectList.add(new ClipPlainText(mimeType, item.getText()));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && ClipDescription.MIMETYPE_TEXT_HTML.equals(mimeType)) {
                objectList.add(new ClipHtmlText(mimeType, item.getText(), item.getHtmlText()));
            } else if (ClipDescription.MIMETYPE_TEXT_INTENT.equals(mimeType)) {
                objectList.add(new ClipIntent(mimeType, item.getIntent()));
            } else {
                Uri uri = item.getUri();
                if (uri != null) {
                    objectList.add(new ClipUri(mimeType, uri));
                }
            }
        }
        return objectList.toArray(new ClipContent[objectList.size()]);
    }

    /**
     * Get current clip text data
     */
    @Nullable
    public static CharSequence getText(@NotNull Context context) {
        ClipData data = get(context);
        if (data == null || data.getItemCount() <= 0 || data.getDescription().getMimeTypeCount() <= 0)
            return null;

        if (ClipDescription.MIMETYPE_TEXT_PLAIN.equals(data.getDescription().getMimeType(0))) {
            return data.getItemAt(0).getText();
        } else {
            return null;
        }
    }

    /**
     * Get current clip all text data
     */
    @Nullable
    public static CharSequence[] getTexts(@NotNull Context context) {
        ClipData data = get(context);
        if (data == null || data.getItemCount() <= 0 || data.getDescription().getMimeTypeCount() <= 0)
            return null;

        ClipDescription clipDescription = data.getDescription();
        List<CharSequence> textList = new LinkedList<>();
        for (int index : Numberx.untilTo(0, data.getItemCount())) {
            // Usually multiple items have only one mimeType
            String mimeType = clipDescription.getMimeType(Math.min(index, clipDescription.getMimeTypeCount() - 1));
            if (ClipDescription.MIMETYPE_TEXT_PLAIN.equals(mimeType)) {
                textList.add(data.getItemAt(index).getText());
            }
        }
        return textList.toArray(new CharSequence[textList.size()]);
    }


    /**
     * Get current clip html text data
     */
    @Nullable
    public static ClipHtmlText getHtmlText(@NotNull Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) return null;
        ClipData data = get(context);
        if (data == null || data.getItemCount() <= 0 || data.getDescription().getMimeTypeCount() <= 0)
            return null;

        String mimeType = data.getDescription().getMimeType(0);
        if (ClipDescription.MIMETYPE_TEXT_HTML.equals(mimeType)) {
            ClipData.Item clipDataItem = data.getItemAt(0);
            return new ClipHtmlText(mimeType, clipDataItem.getText(), clipDataItem.getHtmlText());
        } else {
            return null;
        }
    }

    /**
     * Get current clip all html text data
     */
    @Nullable
    public static ClipHtmlText[] getHtmlTexts(@NotNull Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) return null;
        ClipData data = get(context);
        if (data == null || data.getItemCount() <= 0 || data.getDescription().getMimeTypeCount() <= 0)
            return null;

        ClipDescription clipDescription = data.getDescription();
        List<ClipHtmlText> htmlTestList = new LinkedList<>();
        for (int index : Numberx.untilTo(0, data.getItemCount())) {
            // Usually multiple items have only one mimeType
            String mimeType = clipDescription.getMimeType(Math.min(index, clipDescription.getMimeTypeCount() - 1));
            if (ClipDescription.MIMETYPE_TEXT_HTML.equals(mimeType)) {
                ClipData.Item clipDataItem = data.getItemAt(index);
                htmlTestList.add(new ClipHtmlText(mimeType, clipDataItem.getText(), clipDataItem.getHtmlText()));
            }
        }
        return htmlTestList.toArray(new ClipHtmlText[htmlTestList.size()]);
    }


    /**
     * Get current clip intent data
     */
    @Nullable
    public static Intent getIntent(@NotNull Context context) {
        ClipData data = get(context);
        if (data == null || data.getItemCount() <= 0 || data.getDescription().getMimeTypeCount() <= 0)
            return null;

        if (ClipDescription.MIMETYPE_TEXT_INTENT.equals(data.getDescription().getMimeType(0))) {
            return data.getItemAt(0).getIntent();
        } else {
            return null;
        }
    }

    /**
     * Get current clip all intent data
     */
    @Nullable
    public static Intent[] getIntents(@NotNull Context context) {
        ClipData data = get(context);
        if (data == null || data.getItemCount() <= 0 || data.getDescription().getMimeTypeCount() <= 0)
            return null;

        ClipDescription clipDescription = data.getDescription();
        List<Intent> intentList = new LinkedList<>();
        for (int index : Numberx.untilTo(0, data.getItemCount())) {
            // Usually multiple items have only one mimeType
            String mimeType = clipDescription.getMimeType(Math.min(index, clipDescription.getMimeTypeCount() - 1));
            if (ClipDescription.MIMETYPE_TEXT_INTENT.equals(mimeType)) {
                intentList.add(data.getItemAt(index).getIntent());
            }
        }
        return intentList.toArray(new Intent[intentList.size()]);
    }


    /**
     * Get current clip uri data
     */
    @Nullable
    public static ClipUri getUri(@NotNull Context context) {
        ClipData data = get(context);
        if (data == null || data.getItemCount() <= 0 || data.getDescription().getMimeTypeCount() <= 0)
            return null;

        String mimeType = data.getDescription().getMimeType(0);
        if (!ClipDescription.MIMETYPE_TEXT_PLAIN.equals(mimeType) && !ClipDescription.MIMETYPE_TEXT_HTML.equals(mimeType) && !ClipDescription.MIMETYPE_TEXT_INTENT.equals(mimeType)) {
            Uri uri = data.getItemAt(0).getUri();
            if (uri != null) {
                return new ClipUri(mimeType, uri);
            }
        }
        return null;
    }

    /**
     * Get current clip all uri data
     */
    @Nullable
    public static ClipUri[] getUris(@NotNull Context context) {
        ClipData data = get(context);
        if (data == null || data.getItemCount() <= 0 || data.getDescription().getMimeTypeCount() <= 0)
            return null;

        ClipDescription clipDescription = data.getDescription();
        List<ClipUri> uriList = new LinkedList<>();
        for (int index : Numberx.untilTo(0, data.getItemCount())) {
            // Usually multiple items have only one mimeType
            String mimeType = clipDescription.getMimeType(Math.min(index, clipDescription.getMimeTypeCount() - 1));
            if (!ClipDescription.MIMETYPE_TEXT_PLAIN.equals(mimeType) && !ClipDescription.MIMETYPE_TEXT_HTML.equals(mimeType) && !ClipDescription.MIMETYPE_TEXT_INTENT.equals(mimeType)) {
                Uri uri = data.getItemAt(index).getUri();
                if (uri != null) {
                    uriList.add(new ClipUri(mimeType, uri));
                }
            }
        }
        return uriList.toArray(new ClipUri[uriList.size()]);
    }


    /**
     * Add primary clip changed listener
     */
    public static void addPrimaryClipChangedListener(@NotNull Context context, @NotNull ClipboardManager.OnPrimaryClipChangedListener listener) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null) throw new IllegalStateException("ClipboardManager not found");
        clipboardManager.addPrimaryClipChangedListener(listener);
    }

    /**
     * Remove primary clip changed listener
     */
    public static void removePrimaryClipChangedListener(@NotNull Context context, @NotNull ClipboardManager.OnPrimaryClipChangedListener listener) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null) throw new IllegalStateException("ClipboardManager not found");
        clipboardManager.removePrimaryClipChangedListener(listener);
    }


    /**
     * Clean clip data
     */
    public static void clear(@NotNull Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager == null) throw new IllegalStateException("ClipboardManager not found");
            clipboardManager.clearPrimaryClip();
        }
    }
}
