## SuperSearch

### 项目介绍

一个开源的ohos Java 库，旨在简化多终端搜索相关功能的实现。

### 功能列表

1. 支持模糊匹配以及精确匹配
2. 支持开启、关闭搜索记录
3. 自带搜索算法
4. 支持超多功能自定义
5. 代码高可扩展性
6. 支持多设备使用
7. 支持开箱即用

### 安装教程

- 方法一：

使用前需先导入SuperSearch依赖，并在所对应的模块build.gradle中添加依赖

```groovy
allprojects {
  repositories {
    mavenCentral()
  }
}
...
dependencies {
  ...
  implementation 'top.xuegao-tzx:SuperSearch:1.2.3.517'
  ...
}
```

- 方式二：

1. 下载模块代码添加到自己的工程
2. 关联使用

```
dependencies {
        implementation project(":supersearch")
        ……
}
```


### 使用说明

#### - 添加SuperSearch

##### 1. 加载动画(一个加载中的圆圈)

```xml
        <!--示例-->
        <com.xcl.supersearch.RoundProgressBar
            ohos:id="$id:progressBar"
            ohos:height="40vp"
            ohos:width="40vp"
            ohos:center_in_parent="true"
            ohos:visibility="hide"
            app:round_progress_color="#FF82F8EF"
            app:round_progress_with="5f"
        />
        <!--圆的颜色-->
        app:round_progress_color="#FF82F8EF"
        <!--圆的粗细-->
        app:round_progress_with="5f"
```

##### 2. 搜索框

###### 通过 XML 进行定制

```xml
        <!--示例-->
        <com.xcl.supersearch.SearchView
            ohos:id="$+id:searchView"
            ohos:height="match_parent"
            ohos:left_padding="15vp"
            ohos:right_padding="15vp"
            ohos:visibility="visible"
            ohos:width="match_parent"
            ohos:padding="5vp"
            app:search_history="$media:ic_history_black_24dp"
            app:delete_search_history="$media:ic_close_black_24dp"
            app:isDebugEnabled="true"
            app:nullWarnByMySelf="true"
            app:null_warn_text="$string:search_input_null"
            app:null_warn_text_color="#79FF006E"
            app:noneWarnByMySelf="true"
            app:none_warn_text="$string:search_answer_none"
            app:none_warn_text_color="#5CFF0000"
            app:suggestion_count="6"
            app:queryInput_hint_text="$string:search_hint_text"
            app:background_mask_degree="0.35f"
            app:queryRadius="5vp"
            app:rightButton_Clear="$graphic:ic_close"
            app:isrightButton_ClearEnabled="true"
            app:leftButton_Back="$graphic:ic_arrow_left"
            app:leftButton_Loadding_color="#FF1300FF"
            app:queryInput_background_color="#FFF8F3EC"
            app:queryInput_buttom_color="#A18770B1"
            app:queryInput_cursor_color="#FF8377FF"
            app:queryInput_hint_color="#FFFF0000"
            app:queryInput_text_color="#FF6B16FF"
        />
        <!--是否开启日志输出，默认关闭-->
        app:isDebugEnabled="true"
        <!--是否为手表，默认不是-->
        app:isWearable="true"
        <!--颜色为16进制值-->
        <!--搜索时背景颜色遮罩程度(0最小，到1最大)-->
        app:background_mask_degree="0.3f"
        <!--搜索时背景的遮罩颜色-->
        app:background_mask_color="#FFADEA6C"
        <!--搜索边框圆角半径-->
        app:queryRadius="5vp"
        <!--搜索建议显示条数(仅在已开启搜索建议时有效)-->
        app:suggestion_count="6"
        <!--搜索建议中每一条的左侧图标(仅在已开启搜索建议时有效)-->
        app:search_history="$graphic:ic_history_black"<!--注意:此处仅支持graphic中的文件，如果是其它格式请使用下方提供的工具进行转换或自行转换-->
        <!--搜索建议中每一条的右侧图标(仅在已开启搜索建议时有效)-->
        app:delete_search_history="$graphic:ic_close"<!--注意:此处仅支持graphic中的文件，如果是其它格式请使用下方提供的工具进行转换或自行转换-->
        <!--搜索输入光标的颜色-->
        app:queryInput_cursor_color="#FF8377FF"
        <!--搜索输入提示的颜色-->
        app:queryInput_hint_color="#FFFF0000"
        <!--搜索输入的提示文本-->
        app:queryInput_hint_text="$string:search_hint_text"
        <!--搜索输入文本的颜色-->
        app:queryInput_text_color="#FF000000"
        <!--搜索输入框背景颜色-->
        app:queryInput_background_color="#FFF8F3EC"
        <!--搜索输入框下侧分割线颜色-->
        app:queryInput_buttom_color="#A18770B1"
        <!--搜索输入框右侧按钮图标(不配置就会自动不显示)-->
        app:rightButton="$graphic:ic_filters"
        <!--搜索输入框右侧清空按钮图标X-->
        app:rightButton_Clear="$graphic:ic_close"
        <!--搜索输入框右侧清空按钮是否可用-->
        app:isrightButton_ClearEnabled="true"
        <!--搜索输入框左侧搜索加载动画图标O颜色-->
        app:leftButton_Loadding_color="#FF1300FF"
        <!--搜索输入框左侧返回按钮图标-->
        app:leftButton_Back="$graphic:ic_arrow_left"
        <!--搜索输入值为空-->
        <!--搜索输入值为空时，是否自定义出错信息，注意：不会影响默认告警，只是你可以增加其它你想做的-->
        app:nullWarnByMySelf="true"
        <!--搜索输入值为空时，自定义出错信息中的提示文字，不配置会走默认值-->
        app:null_warn_text="$string:search_input_null"
        <!--搜索输入值为空时，自定义出错信息中的提示文字颜色，不配置会走默认值-->
        app:null_warn_text_color="#FFFF006E"
        <!--搜索结果值为空-->
        <!--搜索结果值为空时，是否自定义出错信息，注意：不会影响默认告警，只是你可以增加其它你想做的-->
        app:noneWarnByMySelf="true"
        <!--搜索结果值为空时，自定义出错信息中的提示文字，不配置会走默认值-->
        app:none_warn_text="$string:search_answer_none"
        <!--搜索结果值为空时，自定义出错信息中的提示文字颜色，不配置会走默认值-->
        app:none_warn_text_color="#FFFF003B"
```

###### 通过 Java 代码进行辅助定制

```java
    1. 定义方式1
    private void initView() {
        SearchView sv;
        sv = (SearchView) findComponentById(ResourceTable.Id_searchView);//根据id获取控件
        sv.Onsearch(getContext(),//获取所在能力的context
                    (ListContainer) findComponentById(ResourceTable.Id_ListContainer),//根据id获取并绑定结果列表展示控件
                    sv,//根据id绑定控件
                    findComponentById(ResourceTable.Id_emptyView),//根据id获取并绑定背景展示控件
                    (com.xcl.supersearch.RoundProgressBar) findComponentById(ResourceTable.Id_progressBar),//根据id获取并绑定背景Loading加载动画控件
                    getSearch(),//传入搜索的数据
                    getSuggection(),//传入搜索建议的数据，如果不配置这个，则不会自带搜索建议
                    true);//是否开启搜索建议
    }
    //注意:搜索建议如果无需开启，可以不配置最后2项；如开启搜索建议，倒数第2项也可有可无
    private List<SRContactor> getSearch(){
        List<SRContactor> list = new ArrayList<>();
        list.add(new SRContactor("zhtzx5", "clsths2"));//支持只传标题、信息，此时后面4个按钮默认不显示
        list.add(new SRContactor("zhng", "cl1", null, ",2u", "3g", "7n7"));//支持传null，此时传null的将不会显示
        list.add(new SRContactor("zhxcl5", null));//支持只传标题，同时传null，此时后面4个按钮和信息不显示
        //TODO:注意:第一个不可为null
        list.add(new SRContactor("zhags", "clhhs1", "1操", "2", "3码", "47"));//支持传入全部信息
        return list;
    }
    private List<SHistoryContactor> getSuggection() {
        List<SHistoryContactor> list = new ArrayList<>();
        list.add(new SHistoryContactor("小草林", ResourceTable.Media_ic_history_black_24dp, -3));//这里-3代表根据默认图标或xml中配置的图标显示
        list.add(new SHistoryContactor("TEST", ResourceTable.Media_ic_history_black_24dp, -1));//这里-1代表根据不显示图标
        list.add(new SHistoryContactor("测试", -1, ResourceTable.Media_ic_close_black_24dp));
        list.add(new SHistoryContactor("test"));//可以只配置建议信息，会自动根据默认图标或xml中配置的图标显示
        return list;
    }

    2. 定义方式2
    sv.setContext(getContext())//设置所在能力的context
        .setRoundProgressBar((com.xcl.supersearch.RoundProgressBar) findComponentById(ResourceTable.Id_progressBar))//设置背景Loading加载动画控件
        .setSearchView(sv)//设置搜索控件
        .setSRListContainer(getSearch())//传入搜索的数据
        .setSuggestionisShow(true)//设置是否开启搜索建议
        .setSuggestionList(getSuggection())//设置搜索建议的数据
        .setBackComponent(findComponentById(ResourceTable.Id_emptyView))//设置背景展示控件
        .setListContainer((ListContainer) findComponentById(ResourceTable.Id_ListContainer))//设置结果列表展示控件
        .Builder();//执行构造
        
    -----------------------------一下方法2种构造均一样-----------------------------------
    sv.setCustomListener(new SearchView.CustomListener() {
        @Override
        public void onFilter(String filter) {
            //TODO:右上角的按钮事件
        }
    });
    sv.setBt1Listener(new SearchView.Bt1Listener() {
        @Override
        public void onbT1(int position) {
            Utils.error(label,"点击了按钮1，其的行号为："+position+1);
            //TODO:一行列表第一个的按钮事件
        }
    });
    //依次类推，此处省略其他3个按钮事件
    sv.setBackClickListener(new SearchView.BackClickListener() {
        @Override
        public void onBackClick() {
            terminate();
            //TODO:左上角的按钮事件
        }
    });
    sv.setNullWarnListener(new SearchView.NullWarnListener(){
        @Override
        public void onNullError() {
            Utils.error(label, "空搜索警告1");
            //TODO:支持自定义空搜索警告
        }
    });
    sv.setNoneWarnListener(new SearchView.NoneWarnListener(){
        @Override
        public void onNullWarn() {
            Utils.error(label, "空结果警告1");
            //TODO:支持自定义空结果警告
        }
    });
```

#### - 使用ImageTracerJava工具

> 参考项目地址： 我的另一个开源项目[ImageTracerJava](https://gitee.com/xuegao-tzx/ImageTracerJava)

1. 在entry/src/test目录下的java文件中含有ImageTracerJava工具的使用示例，可以将图片转换成svg图片，再通过DevEco Studio自带的svg转xml即可将图片资源从media转到graphic中
2. 使用前需先导入ImageTracerJava工具，并在build.gradle中添加依赖

```groovy
allprojects {
  repositories {
    mavenCentral()
  }
}
...
dependencies {
  ...
  implementation 'top.xuegao-tzx:ImageTracerJava:1.1.4.516'
  ...
}
```

3. 使用示例如下

```java
        // Options
        HashMap<String,Float> options = new HashMap<String,Float>();
        // Tracing
        options.put("ltres",1f);
        options.put("qtres",1f);
        options.put("pathomit",8f);
        // Color quantization
        options.put("colorsampling",1f); // 1f means true ; 0f means false: starting with generated palette
        options.put("numberofcolors",16f);
        options.put("mincolorratio",0.02f);
        options.put("colorquantcycles",3f);
        // SVG rendering
        options.put("scale",1f);
        options.put("roundcoords",1f); // 1f means rounded to 1 decimal places, like 7.3 ; 3f means rounded to 3 places, like 7.356 ; etc.
        options.put("lcpr",0f);
        options.put("qcpr",0f);
        options.put("desc",1f); // 1f means true ; 0f means false: SVG descriptions deactivated
        options.put("viewbox",0f); // 1f means true ; 0f means false: fixed width and height
        // Selective Gauss Blur
        options.put("blurradius",0f); // 0f means deactivated; 1f .. 5f : blur with this radius
        options.put("blurdelta",20f); // smaller than this RGB difference will be blurred
        // Palette
        // This is an example of a grayscale palette
        // please note that signed byte values [ -128 .. 127 ] will be converted to [ 0 .. 255 ] in the getsvgstring function
        //下方的两个数字16请参照实际图片动态调整以实现最佳效果
        byte[][] palette = new byte[16][4];
        for(int colorcnt=0; colorcnt < 16; colorcnt++){
            palette[colorcnt][0] = (byte)( -128 + colorcnt * 32); // R
            palette[colorcnt][1] = (byte)( -128 + colorcnt * 32); // G
            palette[colorcnt][2] = (byte)( -128 + colorcnt * 32); // B
            palette[colorcnt][3] = (byte)127; 		      // A
        }

        ImageTracer.saveString(
                "src\\main\\resources\\base\\media\\ic_close_black_24dp.svg" ,
                ImageTracer.imageToSVG("src\\main\\resources\\base\\media\\ic_close_black_24dp.png",options,palette)
        );
```

### 版本迭代

- v1.2.3.517

## License(许可证)

作者：田梓萱 SuperSearch 在 [AGPL V3.0 License](https://www.gnu.org/licenses/agpl-3.0.txt) 下获得许可

