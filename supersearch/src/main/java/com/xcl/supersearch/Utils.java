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

/**
 * The type Utils.
 */
public class Utils {
    /**
     * The constant pd_pd.
     */
    public static boolean pd_pd = false;
    /**
     * The constant iswear.
     */
    public static boolean iswear = false;
    /**
     * The Res manager.
     */
    static ResourceManager resManager;

    /**
     * Gets boolean.
     *
     * @param attrSet      the attr set
     * @param attrName     the attr name
     * @param defaultValue the default value
     * @return the boolean
     */
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

    /**
     * Gets float.
     *
     * @param attrSet      the attr set
     * @param attrName     the attr name
     * @param defaultValue the default value
     * @return the float
     */
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

    /**
     * Gets color.
     *
     * @param attrSet      the attr set
     * @param attrName     the attr name
     * @param defaultColor the default color
     * @return the color
     */
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

    /**
     * Gets dimension.
     *
     * @param attrSet      the attr set
     * @param attrName     the attr name
     * @param defaultValue the default value
     * @return the dimension
     */
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

    /**
     * Gets element.
     *
     * @param attrSet        the attr set
     * @param attrName       the attr name
     * @param defaultElement the default element
     * @return the element
     */
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

    /**
     * Vp to px int.
     *
     * @param vp                the vp
     * @param displayAttributes the display attributes
     * @return the int
     */
    public static int vpToPx(float vp, DisplayAttributes displayAttributes) {
        return (int) (vp * displayAttributes.densityPixels + 0.5f);
    }

    /**
     * Gets string.
     *
     * @param attrSet      the attr set
     * @param attrName     the attr name
     * @param defaultValue the default value
     * @return the string
     */
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

    /**
     * Hq string string.
     *
     * @param a1 the a 1
     * @return the string
     */
    public static String HQString(int a1) {
        try {
            String text = resManager.getElement(a1).getString();
            return text;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Adjust color alpha element.
     *
     * @param color the color
     * @param alpha the alpha
     * @return the element
     */
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

    /**
     * Info.
     *
     * @param label   the label
     * @param message the message
     */
    public static void info(HiLogLabel label, String message) {
        if (pd_pd) {
            HiLog.info(label, "信息:[" + message + "]");
        }
    }

    /**
     * Warn.
     *
     * @param label   the label
     * @param message the message
     */
    public static void warn(HiLogLabel label, String message) {
        if (pd_pd) {
            HiLog.warn(label, "警告:[" + message + "]");
        }
    }

    /**
     * Error.
     *
     * @param label   the label
     * @param message the message
     */
    public static void error(HiLogLabel label, String message) {
        if (pd_pd) {
            HiLog.error(label, "错误:[" + message + "]");
        }
    }

    /**
     * Debug.
     *
     * @param label   the label
     * @param message the message
     */
    public static void debug(HiLogLabel label, String message) {
        if (pd_pd) {
            HiLog.debug(label, "调试:[" + message + "]");
        }
    }

    /**
     * Make press color int.
     *
     * @param backgroundColor the background color
     * @return the int
     */
    protected int makePressColor(int backgroundColor) {
        int r = (backgroundColor >> 16) & 0xFF;
        int g = (backgroundColor >> 8) & 0xFF;
        int b = (backgroundColor) & 0xFF;
        return Color.argb(128, r, g, b);
    }
}
