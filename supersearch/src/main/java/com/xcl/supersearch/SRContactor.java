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

/**
 * The type Sr contactor.
 */
public class SRContactor {
    private String title = null;
    private String message = null;
    private String bt1 = null;
    private String bt2 = null;
    private String bt3 = null;
    private String bt4 = null;

    /**
     * Instantiates a new Sr contactor.
     *
     * @param title   the title
     * @param message the message
     * @param bt1     the bt 1
     * @param bt2     the bt 2
     * @param bt3     the bt 3
     * @param bt4     the bt 4
     */
    public SRContactor(String title, String message, String bt1, String bt2, String bt3, String bt4) {
        this.title = title;
        this.message = message;
        this.bt1 = bt1;
        this.bt2 = bt2;
        this.bt3 = bt3;
        this.bt4 = bt4;
    }

    /**
     * Instantiates a new Sr contactor.
     *
     * @param title the title
     * @param bt1   the bt 1
     * @param bt2   the bt 2
     * @param bt3   the bt 3
     * @param bt4   the bt 4
     */
    public SRContactor(String title, String bt1, String bt2, String bt3, String bt4) {
        this.title = title;
        this.bt1 = bt1;
        this.bt2 = bt2;
        this.bt3 = bt3;
        this.bt4 = bt4;
    }

    /**
     * Instantiates a new Sr contactor.
     *
     * @param title   the title
     * @param message the message
     */
    public SRContactor(String title, String message) {
        this.title = title;
        this.message = message;
    }

    /**
     * Gets bt 1.
     *
     * @return the bt 1
     */
    public String getBt1() {
        return bt1;
    }

    /**
     * Sets bt 1.
     *
     * @param bt1 the bt 1
     */
    public void setBt1(String bt1) {
        this.bt1 = bt1;
    }

    /**
     * Gets bt 2.
     *
     * @return the bt 2
     */
    public String getBt2() {
        return bt2;
    }

    /**
     * Sets bt 2.
     *
     * @param bt2 the bt 2
     */
    public void setBt2(String bt2) {
        this.bt2 = bt2;
    }

    /**
     * Gets bt 3.
     *
     * @return the bt 3
     */
    public String getBt3() {
        return bt3;
    }

    /**
     * Sets bt 3.
     *
     * @param bt3 the bt 3
     */
    public void setBt3(String bt3) {
        this.bt3 = bt3;
    }

    /**
     * Gets bt 4.
     *
     * @return the bt 4
     */
    public String getBt4() {
        return bt4;
    }

    /**
     * Sets bt 4.
     *
     * @param bt4 the bt 4
     */
    public void setBt4(String bt4) {
        this.bt4 = bt4;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets c xinxi.
     *
     * @return the c xinxi
     */
    public String getCXinxi() {
        return title + message;
    }
}
