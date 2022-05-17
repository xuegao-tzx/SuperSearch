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

public class SRContactor {
    private String title = null;
    private String message = null;
    private String bt1 = null;
    private String bt2 = null;
    private String bt3 = null;
    private String bt4 = null;

    public SRContactor(String title, String message, String bt1, String bt2, String bt3, String bt4) {
        this.title = title;
        this.message = message;
        this.bt1 = bt1;
        this.bt2 = bt2;
        this.bt3 = bt3;
        this.bt4 = bt4;
    }

    public SRContactor(String title, String bt1, String bt2, String bt3, String bt4) {
        this.title = title;
        this.bt1 = bt1;
        this.bt2 = bt2;
        this.bt3 = bt3;
        this.bt4 = bt4;
    }

    public SRContactor(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getBt1() {
        return bt1;
    }

    public void setBt1(String bt1) {
        this.bt1 = bt1;
    }

    public String getBt2() {
        return bt2;
    }

    public void setBt2(String bt2) {
        this.bt2 = bt2;
    }

    public String getBt3() {
        return bt3;
    }

    public void setBt3(String bt3) {
        this.bt3 = bt3;
    }

    public String getBt4() {
        return bt4;
    }

    public void setBt4(String bt4) {
        this.bt4 = bt4;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCXinxi() {
        return title + message;
    }
}
