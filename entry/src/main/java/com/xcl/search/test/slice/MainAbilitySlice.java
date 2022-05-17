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
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.window.service.WindowManager;
import ohos.utils.net.Uri;

public class MainAbilitySlice extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        this.getWindow().addFlags(WindowManager.LayoutConfig.MARK_ALLOW_EXTEND_LAYOUT);
        this.findComponentById(ResourceTable.Id_withoutSug).setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component v) {
                Route("withoutSug");
            }
        });
        this.findComponentById(ResourceTable.Id_recentSug).setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component v) {
                Route("recentSug");
            }
        });
        this.findComponentById(ResourceTable.Id_hasknownSug).setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component v) {
                Route("hasknownSug");
            }
        });
    }

    public void Route(String route) {
        Intent intent1 = new Intent();
        intent1.setParam("SEARCH_PD", route);
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withBundleName(MainAbilitySlice.this.getBundleName())
                .withAbilityName("com.xcl.search.test.SearchDemoAbility")
                .build();
        intent1.setOperation(operation);
        MainAbilitySlice.this.startAbility(intent1);
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
