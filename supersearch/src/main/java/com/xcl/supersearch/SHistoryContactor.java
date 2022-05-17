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
 * The type S history contactor.
 */
public class SHistoryContactor {
    private String searchValue;
    private int searchIcon = -1;
    private int removeIcon = -1;

    /**
     * Instantiates a new S history contactor.
     *
     * @param searchValue the search value
     * @param searchIcon  the search icon
     * @param removeIcon  the remove icon
     */
    public SHistoryContactor(String searchValue, int searchIcon, int removeIcon) {
        this.searchValue = searchValue;
        this.searchIcon = searchIcon;
        this.removeIcon = removeIcon;
    }

    /**
     * Instantiates a new S history contactor.
     *
     * @param searchValue the search value
     */
    public SHistoryContactor(String searchValue) {
        this.searchValue = searchValue;
        this.searchIcon = -3;
        this.removeIcon = -3;
    }

    /**
     * Gets search value.
     *
     * @return the search value
     */
    public String getSearchValue() {
        return searchValue;
    }

    /**
     * Sets search value.
     *
     * @param searchValue the search value
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    /**
     * Gets search icon.
     *
     * @return the search icon
     */
    public int getSearchIcon() {
        return searchIcon;
    }

    /**
     * Sets search icon.
     *
     * @param searchIcon the search icon
     */
    public void setSearchIcon(int searchIcon) {
        this.searchIcon = searchIcon;
    }

    /**
     * Gets remove icon.
     *
     * @return the remove icon
     */
    public int getRemoveIcon() {
        return removeIcon;
    }

    /**
     * Sets remove icon.
     *
     * @param removeIcon the remove icon
     */
    public void setRemoveIcon(int removeIcon) {
        this.removeIcon = removeIcon;
    }
}
