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

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.element.Element;

public final class BackgroundLoadingAnimation {
    private boolean isRecycled;

    private float fromAlpha;
    private float toAlpha;

    private ComponentContainer view;

    private AnimatorValue animator;

    public BackgroundLoadingAnimation(ComponentContainer view, float fromAlpha, float toAlpha) {
        this.view = view;
        this.fromAlpha = fromAlpha;
        this.toAlpha = toAlpha;
        this.isRecycled = false;
        initAnimator();
    }

    private void initAnimator() {
        animator = new AnimatorValue();
        animator.setDuration(200);
        animator.setCurveType(Animator.CurveType.LINEAR);
        animator.setValueUpdateListener((valueAnimator, v) -> {
                    Element element = view.getBackgroundElement();
                    element.setAlpha((int) (fromAlpha * 0xFF + (toAlpha - fromAlpha) * 0xFF * v));
                }
        );
        animator.setStateChangedListener(new Animator.StateChangedListener() {
            @Override
            public void onStart(Animator animator) {

            }

            @Override
            public void onStop(Animator animator) {
                view.setClickable(toAlpha != 0);
            }

            @Override
            public void onCancel(Animator animator) {

            }

            @Override
            public void onEnd(Animator animator) {

            }

            @Override
            public void onPause(Animator animator) {

            }

            @Override
            public void onResume(Animator animator) {

            }
        });
    }

    public BackgroundLoadingAnimation setFromAlpha(float fromAlpha) {
        this.fromAlpha = fromAlpha;
        return this;
    }


    public BackgroundLoadingAnimation setToAlpha(float toAlpha) {
        this.toAlpha = toAlpha;
        return this;
    }


    public BackgroundLoadingAnimation setInterpolator(int interpolator) {
        animator.setCurveType(interpolator);
        return this;
    }


    public BackgroundLoadingAnimation setDuration(long duration) {
        animator.setDuration(duration);
        return this;
    }


    public void start() {
        if (isRecycled() || view.getAlpha() == toAlpha) {
            return;
        }

        stop();
        animator.start();
    }


    public void stop() {
        if (!isRecycled() && isRunning()) {
            animator.cancel();
        }
    }


    public boolean isRunning() {
        return animator.isRunning();
    }


    public void recycle() {
        if (isRecycled) {
            return;
        }

        view = null;
        animator = null;
        isRecycled = true;
    }


    public boolean isRecycled() {
        return isRecycled;
    }


}
