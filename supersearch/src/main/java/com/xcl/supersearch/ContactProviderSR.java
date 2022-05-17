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

import ohos.agp.components.*;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.ArrayList;
import java.util.List;

import static com.xcl.supersearch.Utils.iswear;


/**
 * The type Contact provider sr.
 */
public class ContactProviderSR extends BaseItemProvider {
    private static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00666, "ContactProviderSR");
    private final Context context;
    private List<SRContactor> contactArrays = new ArrayList<>(0);
    private AdapterClickListener adapterClickListener;

    /**
     * Instantiates a new Contact provider sr.
     *
     * @param context       the context
     * @param contactArrays the contact arrays
     */
    public ContactProviderSR(Context context, List<SRContactor> contactArrays) {
        this.context = context;
        this.contactArrays = contactArrays;
    }

    /**
     * Instantiates a new Contact provider sr.
     *
     * @param context the context
     */
    public ContactProviderSR(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.contactArrays == null ? 0 : this.contactArrays.size();
    }

    @Override
    public SRContactor getItem(int position) {
        if (position < this.contactArrays.size() && position >= 0) return this.contactArrays.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component componentPara, ComponentContainer componentContainer) {
        Component component = componentPara;
        try {
            ViewHolder viewHolder = null;
            if (component == null) {
                if (iswear) {
                    component = LayoutScatter.getInstance(this.context).parse(ResourceTable.Layout_searchresw,
                            null, false);
                } else {
                    component = LayoutScatter.getInstance(this.context).parse(ResourceTable.Layout_searchres,
                            null, false);
                }
                viewHolder = new ViewHolder();
                viewHolder.button1 = (Button) component.findComponentById(ResourceTable.Id_button1);
                viewHolder.button2 = (Button) component.findComponentById(ResourceTable.Id_button2);
                viewHolder.button3 = (Button) component.findComponentById(ResourceTable.Id_button3);
                viewHolder.button4 = (Button) component.findComponentById(ResourceTable.Id_button4);
                viewHolder.text2 = (Text) component.findComponentById(ResourceTable.Id_message);
                viewHolder.text1 = (Text) component.findComponentById(ResourceTable.Id_title);
                component.setTag(viewHolder);
            } else if (component.getTag() instanceof ViewHolder) viewHolder = (ViewHolder) component.getTag();
            if (viewHolder != null) {
                viewHolder.text1.setVisibility(Component.VISIBLE);
                viewHolder.text2.setVisibility(Component.VISIBLE);
                viewHolder.button1.setVisibility(Component.VISIBLE);
                viewHolder.button2.setVisibility(Component.VISIBLE);
                viewHolder.button3.setVisibility(Component.VISIBLE);
                viewHolder.button4.setVisibility(Component.VISIBLE);
                if (this.contactArrays.get(position).getBt1() != null) {
                    viewHolder.button1.setText(this.contactArrays.get(position).getBt1());
                } else {
                    viewHolder.button1.setVisibility(Component.HIDE);
                }
                viewHolder.button1.setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component deleteComponent) {
                        if (ContactProviderSR.this.adapterClickListener != null)
                            ContactProviderSR.this.adapterClickListener.ann1(position);
                    }
                });
                if (this.contactArrays.get(position).getBt2() != null) {
                    viewHolder.button2.setText(this.contactArrays.get(position).getBt2());
                } else {
                    viewHolder.button2.setVisibility(Component.HIDE);
                }
                viewHolder.button2.setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component deleteComponent) {
                        if (ContactProviderSR.this.adapterClickListener != null)
                            ContactProviderSR.this.adapterClickListener.ann2(position);
                    }
                });
                if (this.contactArrays.get(position).getBt3() != null) {
                    viewHolder.button3.setText(this.contactArrays.get(position).getBt3());
                } else {
                    viewHolder.button3.setVisibility(Component.HIDE);
                }
                viewHolder.button3.setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component deleteComponent) {
                        if (ContactProviderSR.this.adapterClickListener != null)
                            ContactProviderSR.this.adapterClickListener.ann3(position);
                    }
                });
                if (this.contactArrays.get(position).getBt4() != null) {
                    viewHolder.button4.setText(this.contactArrays.get(position).getBt4());
                } else {
                    viewHolder.button4.setVisibility(Component.HIDE);
                }
                viewHolder.button4.setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component deleteComponent) {
                        if (ContactProviderSR.this.adapterClickListener != null)
                            ContactProviderSR.this.adapterClickListener.ann4(position);
                    }
                });
                if (this.contactArrays.get(position).getTitle() != null) {
                    viewHolder.text1.setText(this.contactArrays.get(position).getTitle());
                } else {
                    viewHolder.text1.setVisibility(Component.HIDE);
                }
                if (this.contactArrays.get(position).getMessage() != null) {
                    viewHolder.text2.setText(this.contactArrays.get(position).getMessage());
                } else {
                    viewHolder.text2.setVisibility(Component.HIDE);
                }
            }
        } catch (Exception e) {
            Utils.error(label, e.getMessage());
        }
        return component;
    }

    /**
     * Sets adapter click listener.
     *
     * @param adapterClickListener the adapter click listener
     */
    void setAdapterClickListener(AdapterClickListener adapterClickListener) {
        this.adapterClickListener = adapterClickListener;
    }

    /**
     * Sets data.
     *
     * @param listData the list data
     */
    public void setData(List<SRContactor> listData) {
        contactArrays = listData;
    }

    /**
     * The interface Adapter click listener.
     */
    public interface AdapterClickListener {
        /**
         * Ann 1.
         *
         * @param position the position
         */
        void ann1(int position);

        /**
         * Ann 2.
         *
         * @param position the position
         */
        void ann2(int position);

        /**
         * Ann 3.
         *
         * @param position the position
         */
        void ann3(int position);

        /**
         * Ann 4.
         *
         * @param position the position
         */
        void ann4(int position);
    }

    private static class ViewHolder {
        private Text text1;
        private Text text2;
        private Button button1;
        private Button button2;
        private Button button3;
        private Button button4;
    }
}
