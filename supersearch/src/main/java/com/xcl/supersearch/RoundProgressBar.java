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

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.render.Arc;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.agp.utils.RectFloat;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayAttributes;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import static com.xcl.supersearch.Utils.*;

/**
 * The type Round progress bar.
 */
public class RoundProgressBar extends Component implements Component.DrawTask {
    private static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00234, "RoundProgressBar");
    private final DisplayAttributes displayAttributes;
    private int backgroundColor = Color.getIntColor("#FF4C29CB");//默认紫色背景
    private int arcD = 1;
    private int arcO = 0;
    private float rotateAngle = 0;
    private int limite = 0;
    private Paint arcPaint;
    private float STROKE_WITH_VP = 3f;

    /**
     * Instantiates a new Round progress bar.
     *
     * @param context the context
     */
    public RoundProgressBar(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new Round progress bar.
     *
     * @param context the context
     * @param attrSet the attr set
     */
    public RoundProgressBar(Context context, AttrSet attrSet) {
        this(context, attrSet, "");
    }

    /**
     * Instantiates a new Round progress bar.
     *
     * @param context   the context
     * @param attrSet   the attr set
     * @param styleName the style name
     */
    public RoundProgressBar(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        DisplayManager displayManager = DisplayManager.getInstance();
        Display display = displayManager.getDefaultDisplay(this.getContext()).get();
        displayAttributes = display.getAttributes();
        setAttributes(attrSet);
        addDrawTask(this);
        initPaint();
    }

    /**
     * Init paint.
     */
    protected void initPaint() {
        try {
            arcPaint = new Paint();
            arcPaint.setAntiAlias(true);
            arcPaint.setStyle(Paint.Style.STROKE_STYLE);
        } catch (Exception e) {
            Utils.error(label, e.getMessage());
        }
    }

    /**
     * Sets attributes.
     *
     * @param attrs the attrs
     */
    protected void setAttributes(AttrSet attrs) {
        try {
            setMinHeight(vpToPx(32, displayAttributes));
            setMinWidth(vpToPx(32, displayAttributes));
            if (attrs != null) {
                String background = null;
                if (background != null) {
                    setBackgroundColor(Color.getIntColor(background));
                }
                backgroundColor = getColor(attrs, "round_progress_color", backgroundColor);
                STROKE_WITH_VP = getFloat(attrs, "round_progress_with", STROKE_WITH_VP);
            }
            setMinHeight(vpToPx(3, displayAttributes));
        } catch (Exception e) {
            Utils.error(label, e.getMessage());
        }
    }


    private void drawAnimation(Canvas canvas) {
        try {
            int halfWidth = getWidth() / 2;
            int halfHeight = getHeight() / 2;
            if (arcO == limite) {
                arcD += 6;
            }
            if (arcD >= 290 || arcO > limite) {
                arcO += 6;
                arcD -= 6;
            }
            if (arcO > limite + 290) {
                limite = arcO;
                arcO = limite;
                arcD = 1;
            }
            rotateAngle += 4;
            canvas.rotate(rotateAngle, halfWidth, halfHeight);
            int strokeWith = vpToPx(STROKE_WITH_VP, displayAttributes);
            int halfStrokeWith = vpToPx(STROKE_WITH_VP / 2f, displayAttributes);
            arcPaint.setStrokeWidth(strokeWith);
            arcPaint.setColor(new Color(backgroundColor));
            canvas.drawArc(new RectFloat(halfStrokeWith, halfStrokeWith, getWidth() - halfStrokeWith, getHeight() - halfStrokeWith),
                    new Arc(arcO, arcD, false), arcPaint);
            getContext().getUITaskDispatcher().asyncDispatch(this::invalidate);
        } catch (Exception e) {
            Utils.error(label, e.getMessage());
        }
    }

    /**
     * Sets background color.
     *
     * @param color the color
     */
    public void setBackgroundColor(int color) {
        backgroundColor = color;
        getContext().getUITaskDispatcher().asyncDispatch(this::invalidate);
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        drawAnimation(canvas);
    }
}
