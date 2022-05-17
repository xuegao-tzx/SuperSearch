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


public class SHistoryContactor {
    private String searchValue;
    private int searchIcon = -1;
    private int removeIcon = -1;

    public SHistoryContactor(String searchValue, int searchIcon, int removeIcon) {
        this.searchValue = searchValue;
        this.searchIcon = searchIcon;
        this.removeIcon = removeIcon;
    }

    public SHistoryContactor(String searchValue) {
        this.searchValue = searchValue;
        this.searchIcon = -3;
        this.removeIcon = -3;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getSearchIcon() {
        return searchIcon;
    }

    public void setSearchIcon(int searchIcon) {
        this.searchIcon = searchIcon;
    }

    public int getRemoveIcon() {
        return removeIcon;
    }

    public void setRemoveIcon(int removeIcon) {
        this.removeIcon = removeIcon;
    }
}
