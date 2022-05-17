/*
 * Copyright 2022 田梓萱, xcl@xuegao-tzx.top
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xcl.supersearch;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.Attr;
import ohos.agp.components.AttrSet;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.window.service.DisplayAttributes;
import ohos.global.resource.ResourceManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.Optional;

public class Utils {
    public static boolean pd_pd = false;
    public static boolean iswear = false;
    static ResourceManager resManager;

    public static boolean getBoolean(AttrSet attrSet, String attrName, boolean defaultValue) {
        if (attrSet == null || attrName == null || attrName.isEmpty()) {
            return defaultValue;
        }
        Optional<Attr> attrOptional = attrSet.getAttr(attrName);
        if (attrOptional == null || !attrOptional.isPresent()) {
            return defaultValue;
        }
        return attrOptional.get().getBoolValue();
    }

    public static float getFloat(AttrSet attrSet, String attrName, float defaultValue) {
        if (attrSet == null || attrName == null || attrName.isEmpty()) {
            return defaultValue;
        }
        Optional<Attr> attrOptional = attrSet.getAttr(attrName);
        if (attrOptional == null || !attrOptional.isPresent()) {
            return defaultValue;
        }
        return attrOptional.get().getFloatValue();
    }

    public static int getColor(AttrSet attrSet, String attrName, int defaultColor) {
        if (attrSet == null || attrName == null || attrName.isEmpty()) {
            return defaultColor;
        }

        Optional<Attr> attrOptional = attrSet.getAttr(attrName);
        if (attrOptional == null || !attrOptional.isPresent()) {
            return defaultColor;
        }
        return attrOptional.get().getColorValue().getValue();
    }

    public static int getDimension(AttrSet attrSet, String attrName, int defaultValue) {
        if (attrSet == null || attrName == null || attrName.isEmpty()) {
            return defaultValue;
        }
        Optional<Attr> attrOptional = attrSet.getAttr(attrName);
        if (attrOptional == null || !attrOptional.isPresent()) {
            return defaultValue;
        }
        return attrOptional.get().getDimensionValue() * 3;
    }

    public static Element getElement(AttrSet attrSet, String attrName, Element defaultElement) {
        if (attrSet == null || attrName == null || attrName.isEmpty()) {
            return defaultElement;
        }
        Optional<Attr> attrOptional = attrSet.getAttr(attrName);
        if (attrOptional == null || !attrOptional.isPresent()) {
            return defaultElement;
        }
        return attrOptional.get().getElement();
    }

    public static int vpToPx(float vp, DisplayAttributes displayAttributes) {
        return (int) (vp * displayAttributes.densityPixels + 0.5f);
    }

    public static String getString(AttrSet attrSet, String attrName, String defaultValue) {
        if (attrSet == null || attrName == null || attrName.isEmpty()) {
            return defaultValue;
        }
        Optional<Attr> attrOptional = attrSet.getAttr(attrName);
        if (attrOptional == null || !attrOptional.isPresent()) {
            return defaultValue;
        }
        return attrOptional.get().getStringValue();
    }

    public static String HQString(int a1) {
        try {
            String text = resManager.getElement(a1).getString();
            return text;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static Element adjustColorAlpha(int color, float alpha) {
        final int alphaChannel = (int) (255 * alpha);
        final int redChannel = (color >> 16) & 0xFF;
        final int greenChannel = (color >> 8) & 0xFF;
        final int blueChannel = color & 0xFF;

        ShapeElement element = new ShapeElement();
        element.setAlpha(alphaChannel);
        element.setRgbColor(RgbColor.fromArgbInt(Color.rgb(redChannel, greenChannel, blueChannel)));
        return element;
    }

    public static void info(HiLogLabel label, String message) {
        if (pd_pd) {
            HiLog.info(label, "信息:[" + message + "]");
        }
    }

    public static void warn(HiLogLabel label, String message) {
        if (pd_pd) {
            HiLog.warn(label, "警告:[" + message + "]");
        }
    }

    public static void error(HiLogLabel label, String message) {
        if (pd_pd) {
            HiLog.error(label, "错误:[" + message + "]");
        }
    }

    public static void debug(HiLogLabel label, String message) {
        if (pd_pd) {
            HiLog.debug(label, "调试:[" + message + "]");
        }
    }

    protected int makePressColor(int backgroundColor) {
        int r = (backgroundColor >> 16) & 0xFF;
        int g = (backgroundColor >> 8) & 0xFF;
        int b = (backgroundColor) & 0xFF;
        return Color.argb(128, r, g, b);
    }
}
