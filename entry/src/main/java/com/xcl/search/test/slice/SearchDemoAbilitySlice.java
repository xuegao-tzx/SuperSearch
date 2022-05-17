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
package com.xcl.search.test.slice;

import com.xcl.search.test.ResourceTable;
import com.xcl.supersearch.SHistoryContactor;
import com.xcl.supersearch.SRContactor;
import com.xcl.supersearch.SearchView;
import com.xcl.supersearch.Utils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;
import ohos.agp.window.service.WindowManager;
import ohos.data.distributed.common.KvManagerConfig;
import ohos.data.distributed.common.KvManagerFactory;
import ohos.distributedhardware.devicemanager.DeviceInfo;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.ArrayList;
import java.util.List;

public class SearchDemoAbilitySlice extends AbilitySlice {
    private static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00234, "SearchDemoAbilitySlice");
    private int PDD = -2;
    private SearchView sV;

    private static List<SRContactor> getSearch() {
        List<SRContactor> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new SRContactor("zhangsan", "class1", "1", "2你", "3", "4.7"));
        }
        list.add(new SRContactor("zhtzx5", "clsths2"));//支持只传标题、信息，此时后面4个按钮默认不显示
        list.add(new SRContactor("zhng", "cl1", null, ",2u", "3g", "7n7"));//支持传null，此时传null的将不会显示
        list.add(new SRContactor("zhxcl5", null));//支持只传标题，同时传null，此时后面4个按钮和信息不显示
        //TODO:注意:第一个不可为null
        list.add(new SRContactor("zhags", "clhhs1", "1操", "2", "3码", "47"));//支持传入全部信息
        return list;
    }

    private static List<SHistoryContactor> getSuggection() {
        List<SHistoryContactor> list = new ArrayList<>();
        list.add(new SHistoryContactor("小草林", ResourceTable.Media_ic_history_black_24dp, -3));//这里-3代表根据默认图标或xml中配置的图标显示
        list.add(new SHistoryContactor("TEST", ResourceTable.Media_ic_history_black_24dp, -1));//这里-1代表根据不显示图标
        list.add(new SHistoryContactor("测试", -1, ResourceTable.Media_ic_close_black_24dp));
        list.add(new SHistoryContactor("...", ResourceTable.Media_ic_history_black_24dp, ResourceTable.Media_ic_close_black_24dp));
        list.add(new SHistoryContactor("test"));//可以只配置建议信息，会自动根据默认图标或xml中配置的图标显示
        return list;
    }

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        getWindow().addFlags(WindowManager.LayoutConfig.MARK_ALLOW_EXTEND_LAYOUT);
        ohos.data.distributed.device.DeviceInfo localDeviceInfo = KvManagerFactory.getInstance()
                .createKvManager(new KvManagerConfig((getContext()))).getLocalDeviceInfo();
        int localDeviceType = Integer.parseInt(localDeviceInfo.getType());
        if (localDeviceType == DeviceInfo.DeviceType.WEARABLE.value()) {
            super.setUIContent(ResourceTable.Layout_ability_search_demow);
        } else {
            super.setUIContent(ResourceTable.Layout_ability_search_demo);
        }
        Utils.pd_pd = true;
        sV = (SearchView) findComponentById(ResourceTable.Id_searchview);
        if (sV == null) {
            Utils.info(SearchDemoAbilitySlice.label, "null");
        } else {
            Utils.info(SearchDemoAbilitySlice.label, "not null");
        }
        if (intent != null) {
            String action = intent.getStringParam("SEARCH_PD");
            if ("withoutSug".equals(action)) {
                PDD = -1;
            } else if ("recentSug".equals(action)) {
                PDD = 0;
            } else if ("hasknownSug".equals(action)) {
                PDD = 1;
            }
        } else {
            terminate();
        }

        initView();
    }

    private void initView() {


        if (PDD == 0) {
            /*sv.Onsearch(getContext(),
                    (ListContainer) findComponentById(ResourceTable.Id_ListContainer),
                    sv,
                    findComponentById(ResourceTable.Id_emptyView),
                    (com.xcl.supersearch.RoundProgressBar) findComponentById(ResourceTable.Id_progressBar),
                    getSearch(),
                    true);*///TODO:方法一，无需建议列表只需配置true即可
            sV.setContext(getContext())
                    .setRoundProgressBar((com.xcl.supersearch.RoundProgressBar) findComponentById(ResourceTable.Id_progressBar))
                    .setSearchView(sV)
                    .setSRListContainer(SearchDemoAbilitySlice.getSearch())
                    .setSuggestionisShow(true)
                    .setBackComponent(findComponentById(ResourceTable.Id_emptyView))
                    .setListContainer((ListContainer) findComponentById(ResourceTable.Id_ListContainer))
                    .Builder();//TODO:方法二
        } else if (PDD == 1) {
            /*sv.Onsearch(getContext(),//获取所在能力的context
                    (ListContainer) findComponentById(ResourceTable.Id_ListContainer),//根据id获取并绑定结果列表展示控件
                    sv,//根据id绑定控件
                    findComponentById(ResourceTable.Id_emptyView),//根据id获取并绑定背景展示控件
                    (com.xcl.supersearch.RoundProgressBar) findComponentById(ResourceTable.Id_progressBar),//根据id获取并绑定背景Loading加载动画控件
                    getSearch(),//传入搜索的数据
                    getSuggection(),//传入搜索建议的数据，如果不配置这个，则不会自带搜索建议
                    true);//是否开启搜索建议
            *///TODO:方法一
            sV.setContext(getContext())//设置所在能力的context
                    .setRoundProgressBar((com.xcl.supersearch.RoundProgressBar) findComponentById(ResourceTable.Id_progressBar))//设置背景Loading加载动画控件
                    .setSearchView(sV)//设置搜索控件
                    .setSRListContainer(SearchDemoAbilitySlice.getSearch())//传入搜索的数据
                    .setSuggestionisShow(true)//设置是否开启搜索建议
                    .setSuggestionList(SearchDemoAbilitySlice.getSuggection())//设置搜索建议的数据
                    .setBackComponent(findComponentById(ResourceTable.Id_emptyView))//设置背景展示控件
                    .setListContainer((ListContainer) findComponentById(ResourceTable.Id_ListContainer))//设置结果列表展示控件
                    .Builder();//执行构造
            //TODO:方法二
        } else if (PDD == -1) {
            sV.setContext(getContext())
                    .setRoundProgressBar((com.xcl.supersearch.RoundProgressBar) findComponentById(ResourceTable.Id_progressBar))
                    .setSearchView(sV)
                    .setSRListContainer(SearchDemoAbilitySlice.getSearch())
                    .setBackComponent(findComponentById(ResourceTable.Id_emptyView))
                    .setListContainer((ListContainer) findComponentById(ResourceTable.Id_ListContainer))
                    .Builder();//TODO:方法二
            /*sv.Onsearch(getContext(),
                    (ListContainer) findComponentById(ResourceTable.Id_ListContainer),
                    sv,
                    findComponentById(ResourceTable.Id_emptyView),
                    (com.xcl.supersearch.RoundProgressBar) findComponentById(ResourceTable.Id_progressBar),
                    getSearch());*///TODO:方法一，不配置默认不开启搜索建议
        }
        sV.setCustomListener(new SearchView.CustomListener() {
            @Override
            public void onFilter(String filter) {
                //TODO:右上角的按钮事件
            }
        });
        sV.setBt1Listener(new SearchView.Bt1Listener() {
            @Override
            public void onbT1(int position) {
                HiLog.error(SearchDemoAbilitySlice.label, "点击了按钮1，其的行号为：" + position + 1);
                //TODO:一行列表第一个的按钮事件
            }
        });
        sV.setBackClickListener(new SearchView.BackClickListener() {
            @Override
            public void onBackClick() {
                terminate();
                //TODO:左上角的按钮事件
            }
        });
        sV.setNullWarnListener(new SearchView.NullWarnListener() {
            @Override
            public void onNullError() {
                HiLog.error(SearchDemoAbilitySlice.label, "空搜索警告1");
                //TODO:支持自定义空搜索警告
            }
        });
        sV.setNoneWarnListener(new SearchView.NoneWarnListener() {
            @Override
            public void onNullWarn() {
                HiLog.error(SearchDemoAbilitySlice.label, "空结果警告1");
                //TODO:支持自定义空结果警告
            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
