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
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.PixelMapElement;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.xcl.supersearch.Utils.*;

/**
 * The type Search view.
 */
public class SearchView extends DependentLayout implements Component.BindStateChangedListener, ContactProviderSR.AdapterClickListener {
    private static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00234, "SearchView");
    private static final float DEFAULT_DIM_AMOUNT = 0.5f;
    private int SuggestionCount = 5;
    private List<SRContactor> CXList;
    private List<SRContactor> ANSList;
    private ContactProviderSR CPSR;
    private Context context;
    private SearchView SV;
    private Component backComponent = null;
    private ListContainer ListContaine;
    private RoundProgressBar RoundPB;
    private boolean isShow;
    private Element HistoryPicture;
    private Element DelHistoryPicture;
    private boolean isRightClearButtonEnabled;
    private boolean isDebugEnabled;
    private boolean isWearable;
    private int InputHintColor;
    private boolean NullWarnByMySelf;
    private String NullWarnText;
    private int NullWarnColor;
    private boolean NoneWarnByMySelf;
    private String NoneWarnText;
    private int NoneWarnColor;
    private int InputTextColor;
    private int InputCursorColor;
    private int InputButtomColor;
    private int LeftLoadColor;
    private int InputBackgroundColor;
    private int QueryRadius = -1;
    private float BackMaskDegree;
    private int BackMaskColor;
    private Element LeftBackButton;
    private Element RightButtonCustom;
    private Element RightClearInputButton;
    private String QueryInputHint = "";
    private List<SHistoryContactor> SuggestionList = new ArrayList<>(0);
    private Component Divider;
    private Image LeftBtnImage;
    private Image RightBtnImage;
    private Image ClearInputBtnImage;
    private RoundProgressBar RPB_System;
    private TextField InputSearchText;
    private Component SuggestionContainer;
    private ListContainer listContainer;
    private BackgroundLoadingAnimation backgroundEnterAnimation;
    private BackgroundLoadingAnimation backgroundExitAnimation;
    private BackClickListener backClickListener;
    private SearchListener searchListener;
    private CustomListener customListener;
    private RemoveListener removeListener;
    private HistoryProvider hisyoryprovider;
    private State state;
    private Component searchLayout;
    private AnimatorValue animExpand;
    private AnimatorValue historyAnim;
    private AnimatorValue animFilter;
    private Bt1Listener bt1Listener;
    private Bt2Listener bt2Listener;
    private Bt3Listener bt3Listener;
    private Bt4Listener bt4Listener;
    private NullWarnListener nullWarnListener;
    private NoneWarnListener noneWarnListener;
    private final SearchListener SListener = new SearchListener() {
        @Override
        public void onSearch(String search) {
            ListContaine.setVisibility(Component.HIDE);
            if ("".equals(search) || search == null) {
                context.getUITaskDispatcher().delayDispatch(new Runnable() {
                    @Override
                    public void run() {
                        Utils.warn(SearchView.label, "空搜索警告");
                        if (NullWarnByMySelf) {
                            nullWarnListener.onNullError();
                        }
                        InputSearchText.setHint(NullWarnText);
                        InputSearchText.setHintColor(new Color(NullWarnColor));
                        RPB_System.setVisibility(Component.HIDE);
                        LeftBtnImage.setVisibility(Component.VISIBLE);
                    }
                }, 1000);

            } else {
                InputSearchText.setHint(QueryInputHint);
                InputSearchText.setHintColor(new Color(InputHintColor));
                RoundPB.setVisibility(Component.VISIBLE);
                if (backComponent != null) {
                    backComponent.setVisibility(Component.HIDE);
                }
                ANSList = SearchView.getListData(search, CXList);
                context.getUITaskDispatcher().delayDispatch(new Runnable() {
                    @Override
                    public void run() {
                        Utils.debug(SearchView.label, "非空搜索");
                        for (int i = 0; i < ANSList.size(); i++) {
                            Utils.info(SearchView.label, i + "." + ANSList.get(i).getTitle());
                        }
                        if (!ANSList.isEmpty()) {
                            Utils.warn(SearchView.label, "搜索结果非空");
                            CPSR.setData(ANSList);
                            CPSR.notifyDataChanged();
                            ListContaine.setVisibility(Component.VISIBLE);
                            RoundPB.setVisibility(Component.HIDE);
                            RPB_System.setVisibility(Component.HIDE);
                            LeftBtnImage.setVisibility(Component.VISIBLE);
                        } else {
                            Utils.warn(SearchView.label, "搜索结果为空");
                            RoundPB.setVisibility(Component.HIDE);
                            InputSearchText.setText("");
                            if (NoneWarnByMySelf) {
                                noneWarnListener.onNullWarn();
                            }
                            InputSearchText.setHint(NoneWarnText);
                            InputSearchText.setHintColor(new Color(NoneWarnColor));
                            RPB_System.setVisibility(Component.HIDE);
                            LeftBtnImage.setVisibility(Component.VISIBLE);
                            backComponent.setVisibility(Component.VISIBLE);
                        }

                    }
                }, 1000);
                if (isShow) {
                    Utils.debug(SearchView.label, "搜索历史列表增加新搜索记录");
                    List<SHistoryContactor> suggestionItems = getSuggestionItems();
                    Utils.debug(label, suggestionItems.size() + "个搜索记录");
                    boolean has = false;
                    for (SHistoryContactor item : suggestionItems) {
                        if (search.equals(item.getSearchValue())) {
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        addSuggestionItems(new SHistoryContactor(search,
                                -3,
                                -3
                        ), 0);
                    }
                }
            }
        }
    };

    /**
     * Instantiates a new Search view.
     *
     * @param context the context
     */
    SearchView(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new Search view.
     *
     * @param context the context
     * @param attrSet the attr set
     */
    public SearchView(Context context, AttrSet attrSet) {
        this(context, attrSet, "");
    }//TODO:修饰符务必为public

    /**
     * Instantiates a new Search view.
     *
     * @param context   the context
     * @param attrSet   the attr set
     * @param styleName the style name
     */
    public SearchView(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        resManager = context.getResourceManager();
        initDefault();
        initAttrSet(attrSet);
        initAnimation();
        initView();
        setEditView();
        setDefaultBackground();
        setBindStateChangedListener(this);
    }//TODO:修饰符务必为public

    private static void showKeyboard() {
        try {
            Class inputClass = Class.forName("ohos.miscservices.inputmethod.InputMethodController");
            Method method = inputClass.getMethod("getInstance");
            Object object = method.invoke(new Object[]{});
            Method startInput = inputClass.getMethod("startInput", int.class, boolean.class);
            startInput.invoke(object, 1, true);
        } catch (Exception e) {
            Utils.error(SearchView.label, e.getMessage());
        }
    }

    private static List<SRContactor> getListData(String searchValue, List<SRContactor> CXList) {
        List<SRContactor> List = new ArrayList<>();
        CXList.stream().filter(new Predicate<SRContactor>() {
            @Override
            public boolean test(SRContactor e) {
                return e.getCXinxi().contains(searchValue);
            }
        }).forEachOrdered(new Consumer<SRContactor>() {
            @Override
            public void accept(SRContactor object) {
                List.add(object);
            }
        });
        return List;
    }

    private void filterSearchValue(String s) {
        if (!isShow || SuggestionList == null || SuggestionList.isEmpty()) {
            return;
        }
        try {
            List<SHistoryContactor> list;
            if ("".equals(s)) {
                list = SuggestionList;
            } else {
                list = new ArrayList<>();
                for (SHistoryContactor item : SuggestionList) {
                    String searchValue = item.getSearchValue();
                    if (searchValue == null) {
                        continue;
                    }
                    if (searchValue.equals("") || searchValue.toLowerCase().startsWith(s.toLowerCase())) {
                        list.add(item);
                    }
                }
            }
            Utils.debug(label, list.size() + "个搜索记录");
            hisyoryprovider.setData(list);
            hisyoryprovider.notifyDataChanged();
        } catch (Exception e) {
            Utils.error(SearchView.label, e.getMessage());
        }
    }

    private void startResetHistoryHeightAnimation() {
        if (animFilter != null && animFilter.isRunning()) {
            return;
        }
        try {
            int ch = SuggestionContainer.getHeight();
            int mh;
            if (iswear) {
                mh = AttrHelper.vp2px(24, getContext()) * (Math.min(hisyoryprovider.getCount(), SuggestionCount));
            } else {
                mh = AttrHelper.vp2px(48, getContext()) * (Math.min(hisyoryprovider.getCount(), SuggestionCount));
            }
            int tem = mh - ch;
            if (tem != 0) {
                animFilter = new AnimatorValue();
                animFilter.setDuration(100);
                animFilter.setCurveType(Animator.CurveType.LINEAR);
                animFilter.setValueUpdateListener(new AnimatorValue.ValueUpdateListener() {
                                                      @Override
                                                      public void onUpdate(AnimatorValue animatorValue, float v) {
                                                          SuggestionContainer.setHeight((int) (ch + tem * v));
                                                      }
                                                  }
                );
                animFilter.setStateChangedListener(new Animator.StateChangedListener() {
                    @Override
                    public void onStart(Animator animator) {

                    }

                    @Override
                    public void onStop(Animator animator) {
                        hisyoryprovider.notifyDataChanged();
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
                animFilter.start();
            }
        } catch (Exception e) {
            Utils.error(SearchView.label, e.getMessage());
        }
    }

    private void historyAnimationStart(boolean expand) {
        int historyHeight = Math.min(hisyoryprovider.getCount(), SuggestionCount) * AttrHelper.vp2px(48, getContext());
        if ((historyAnim != null && historyAnim.isRunning())
                || (expand && historyHeight == SuggestionContainer.getHeight())) {
            return;
        }
        historyAnim = new AnimatorValue();
        historyAnim.setCurveType(Animator.CurveType.LINEAR);
        historyAnim.setDuration(200);
        historyAnim.setValueUpdateListener(new AnimatorValue.ValueUpdateListener() {
            @Override
            public void onUpdate(AnimatorValue animatorValue, float v) {
                if (expand) {
                    SuggestionContainer.setHeight((int) (historyHeight * v));
                } else {
                    SuggestionContainer.setHeight((int) (historyHeight * (1f - v)));
                }
            }
        });
        historyAnim.start();
    }

    private void setDefaultBackground() {
        try {
            ShapeElement element = new ShapeElement();
            element.setRgbColor(RgbColor.fromArgbInt(BackMaskColor));
            if (InputSearchText.hasFocus()) {
                element.setAlpha((int) (0xFF * BackMaskDegree));
            } else {
                element.setAlpha(0);
            }
            setBackground(element);
            setClickedListener(new ClickedListener() {
                @Override
                public void onClick(Component component) {
                    InputSearchText.clearFocus();
                }
            });
            setClickable(InputSearchText.hasFocus());
        } catch (Exception e) {
            Utils.error(SearchView.label, e.getMessage());
        }
    }

    private void initAnimation() {
        try {
            Utils.debug(SearchView.label, "initAnimation");
            backgroundEnterAnimation = new BackgroundLoadingAnimation(
                    this,
                    0f,
                    BackMaskDegree
            );

            backgroundExitAnimation = new BackgroundLoadingAnimation(
                    this,
                    BackMaskDegree,
                    0f
            );
        } catch (Exception e) {
            Utils.error(SearchView.label, e.getMessage());
        }
    }

    private void initDefault() {
        try {
            InputHintColor = getContext().getColor(Color.getIntColor("#FFA0A0A0"));
            InputTextColor = getContext().getColor(Color.getIntColor("#FFA5A5A5"));
            InputCursorColor = getContext().getColor(Color.getIntColor("#A18770B1"));
            InputButtomColor = getContext().getColor(Color.getIntColor("#A18770B1"));
            LeftLoadColor = getContext().getColor(Color.getIntColor("#FF8377FF"));
            InputBackgroundColor = getContext().getColor(Color.getIntColor("#FFFFFFFF"));
            BackMaskColor = getContext().getColor(Color.getIntColor("#FF000000"));
            NullWarnColor = getContext().getColor(Color.getIntColor("#63FF0000"));
            NoneWarnColor = getContext().getColor(Color.getIntColor("#63FF0000"));
            QueryRadius = AttrHelper.vp2px(2, getContext());
            try {
                LeftBackButton = new PixelMapElement(getContext().getResourceManager().getResource(ResourceTable.Media_ic_arrow_left_black_24dp));
                RightClearInputButton = new PixelMapElement(getContext().getResourceManager().getResource(ResourceTable.Media_ic_close_black_24dp));
                HistoryPicture = new PixelMapElement(getContext().getResourceManager().getResource(ResourceTable.Media_ic_history_black_24dp));
                DelHistoryPicture = new PixelMapElement(getContext().getResourceManager().getResource(ResourceTable.Media_ic_close_black_24dp));
            } catch (Exception e) {
                Utils.error(SearchView.label, e.getMessage());
            }
            QueryInputHint = HQString(ResourceTable.String_search_hint_text_);
            BackMaskDegree = SearchView.DEFAULT_DIM_AMOUNT;
            NullWarnText = HQString(ResourceTable.String_search_input_null);
            NoneWarnText = HQString(ResourceTable.String_search_answer_none);
            isRightClearButtonEnabled = true;
            isDebugEnabled = false;
            isShow = false;
            state = State.EXPANDED;
        } catch (Exception e) {
            Utils.error(SearchView.label, e.getMessage());
        }
    }

    private void initAttrSet(AttrSet attributes) {
        if (attributes == null) {
            return;
        }
        isRightClearButtonEnabled = getBoolean(attributes, "isrightButton_ClearEnabled", isRightClearButtonEnabled);
        isDebugEnabled = getBoolean(attributes, "isDebugEnabled", isDebugEnabled);
        pd_pd = isDebugEnabled;
        isWearable = getBoolean(attributes, "isWearable", isWearable);
        iswear = isWearable;
        InputHintColor = getColor(attributes, "queryInput_hint_color", InputHintColor);
        InputTextColor = getColor(attributes, "queryInput_text_color", InputTextColor);
        InputCursorColor = getColor(attributes, "queryInput_cursor_color", InputCursorColor);
        InputButtomColor = getColor(attributes, "queryInput_buttom_color", InputButtomColor);
        LeftLoadColor = getColor(attributes, "leftButton_Loadding_color", LeftLoadColor);
        InputBackgroundColor = getColor(attributes, "queryInput_background_color", InputBackgroundColor);
        HistoryPicture = getElement(attributes, "search_history", HistoryPicture);
        DelHistoryPicture = getElement(attributes, "delete_search_history", DelHistoryPicture);
        BackMaskDegree = getFloat(attributes, "background_mask_degree", BackMaskDegree);
        BackMaskColor = getColor(attributes, "background_mask_color", BackMaskColor);
        QueryRadius = getDimension(attributes, "queryRadius", QueryRadius);
        LeftBackButton = getElement(attributes, "leftButton_Back", LeftBackButton);
        RightButtonCustom = getElement(attributes, "RightButtonCustom", RightButtonCustom);
        RightClearInputButton = getElement(attributes, "rightButton_Clear", RightClearInputButton);
        NullWarnByMySelf = getBoolean(attributes, "nullWarnByMySelf", NullWarnByMySelf);
        NullWarnText = getString(attributes, "null_warn_text", NullWarnText);
        NullWarnColor = getColor(attributes, "null_warn_text_color", NullWarnColor);
        NoneWarnByMySelf = getBoolean(attributes, "noneWarnByMySelf", NoneWarnByMySelf);
        NoneWarnText = getString(attributes, "none_warn_text", NoneWarnText);
        NoneWarnColor = getColor(attributes, "none_warn_text_color", NoneWarnColor);
        QueryInputHint = getString(attributes, "queryInput_hint_text", QueryInputHint);
        SuggestionCount = Integer.parseInt(getString(attributes, "suggestion_count", String.valueOf(SuggestionCount)));
        if (iswear) {
            if (SuggestionCount > 6) {
                SuggestionCount = 6;
            }
        }
    }

    private void initView() {
        try {
            if (iswear) {
                searchLayout = LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_search_vieww, this, false);
            } else {
                searchLayout = LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_search_view, this, false);
            }

            StackLayout.LayoutConfig config = new StackLayout.LayoutConfig();
            config.width = ComponentContainer.LayoutConfig.MATCH_PARENT;
            config.height = ComponentContainer.LayoutConfig.MATCH_CONTENT;
            Element element = searchLayout.getBackgroundElement();
            if (element instanceof ShapeElement) {
                ShapeElement e = (ShapeElement) element;
                if (QueryRadius != -1) {
                    e.setCornerRadius(QueryRadius);
                }
                if (InputBackgroundColor != 0) {
                    e.setRgbColor(RgbColor.fromArgbInt(InputBackgroundColor));
                }
            }
            addComponent(searchLayout, config);
            LeftBtnImage = (Image) findComponentById(ResourceTable.Id_leftBtnIv);
            RPB_System = (RoundProgressBar) findComponentById(ResourceTable.Id_progressBar);
            InputSearchText = (TextField) findComponentById(ResourceTable.Id_inputEt);
            ClearInputBtnImage = (Image) findComponentById(ResourceTable.Id_clearInputBtnIv);
            RightBtnImage = (Image) findComponentById(ResourceTable.Id_rightBtnIv);
            SuggestionContainer = findComponentById(ResourceTable.Id_suggestionsContainerLl);
            Divider = findComponentById(ResourceTable.Id_divider);
            listContainer = (ListContainer) findComponentById(ResourceTable.Id_suggestionsRecyclerView);
            InputSearchText.setHintColor(new Color(InputHintColor));
            InputSearchText.setTextColor(new Color(InputTextColor));
            RPB_System.setBackgroundColor(LeftLoadColor);
            ShapeElement dividerElement = new ShapeElement();
            dividerElement.setRgbColor(RgbColor.fromArgbInt(InputButtomColor));
            Divider.setBackground(dividerElement);
            LeftBtnImage.setImageElement(LeftBackButton);
            ClearInputBtnImage.setImageElement(RightClearInputButton);
            RightBtnImage.setVisibility(RightButtonCustom == null ? Component.HIDE : Component.VISIBLE);
            RightBtnImage.setImageElement(RightButtonCustom);
            InputSearchText.setHint(QueryInputHint);
            SuggestionContainer.setVisibility(Component.HIDE);
        } catch (Exception e) {
            Utils.error(SearchView.label, e.getMessage());
        }
        ClearInputBtnImage.setClickedListener(new ClickedListener() {
            private void run() {
                InputSearchText.requestFocus();
                SearchView.showKeyboard();
            }

            @Override
            public void onClick(Component component1) {
                InputSearchText.setText("");
                if (!InputSearchText.hasFocus()) {
                    getContext().getUITaskDispatcher().delayDispatch(this::run, 130);
                }
            }
        });
        RightBtnImage.setClickedListener(new ClickedListener() {
            @Override
            public void onClick(Component component14) {
                if (customListener != null) {
                    customListener.onFilter(InputSearchText.getText());
                }
            }
        });
        LeftBtnImage.setClickedListener(new ClickedListener() {
            @Override
            public void onClick(Component component13) {
                Utils.debug(SearchView.label, "Left返回Btn onClick");
                if (InputSearchText.hasFocus()) {
                    InputSearchText.clearFocus();
                } else if (backClickListener != null) {
                    backClickListener.onBackClick();
                }
            }
        });
        listContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
                Utils.debug(SearchView.label, "搜索建议列表点击了第" + i + "行");
                SHistoryContactor item = hisyoryprovider.getItem(i);
                InputSearchText.setText(item.getSearchValue());
                InputSearchText.clearFocus();
                if (searchListener != null) {
                    searchListener.onSearch(InputSearchText.getText());
                }
            }
        });
        InputSearchText.setEditorActionListener(new Text.EditorActionListener() {
            @Override
            public boolean onTextEditorAction(int i) {
                Utils.debug(SearchView.label, "点击了键盘自带的搜索按钮");
                InputSearchText.clearFocus();
                RPB_System.setVisibility(Component.VISIBLE);
                LeftBtnImage.setVisibility(Component.HIDE);
                if (searchListener != null) {
                    searchListener.onSearch(InputSearchText.getText());
                }
                return true;
            }
        });
    }

    @Override
    public void ann1(int position) {
        if (bt1Listener != null) {
            bt1Listener.onbT1(position);
        }
    }

    @Override
    public void ann2(int position) {
        if (bt2Listener != null) {
            bt2Listener.onbT2(position);
        }
    }

    @Override
    public void ann3(int position) {
        if (bt3Listener != null) {
            bt3Listener.onbT3(position);
        }
    }

    @Override
    public void ann4(int position) {
        if (bt4Listener != null) {
            bt4Listener.onbT4(position);
        }
    }

    private void setEditView() {
        try {
            InputSearchText.setFocusChangedListener(new FocusChangedListener() {
                @Override
                public void onFocusChange(Component component, boolean b) {
                    if (b) {
                        backgroundEnterAnimation.start();
                    } else {
                        backgroundExitAnimation.start();
                    }
                    if (isShow) {
                        historyAnimationStart(b);
                    }
                }
            });
            InputSearchText.addTextObserver(new Text.TextObserver() {
                @Override
                public void onTextUpdated(String s, int i, int i1, int i2) {
                    if (isRightClearButtonEnabled) {
                        if ("".equals(s)) {
                            ClearInputBtnImage.setVisibility(Component.HIDE);
                        } else {
                            ClearInputBtnImage.setVisibility(Component.VISIBLE);
                        }
                    }
                    filterSearchValue(s);
                }
            });
        } catch (Exception e) {
            Utils.error(SearchView.label, e.getMessage());
        }
    }

    private List<SHistoryContactor> getSuggestionItems() {
        return SuggestionList;
    }

    private void addSuggestionItems(SHistoryContactor suggestions, int position) {
        if (SuggestionList == null) {
            SuggestionList = new ArrayList<>();
        }
        SuggestionList.add(position, suggestions);
        Utils.debug(label, SuggestionList.size() + "个搜索记录");
    }

    /**
     * Onsearch.
     *
     * @param contex       the contex
     * @param ListContai   the list contai
     * @param sv           the sv
     * @param backComponen the back componen
     * @param pgb          the pgb
     * @param cslist       the cslist
     * @param suggestions  the suggestions
     * @param ishow        the ishow
     */
    public void Onsearch(Context contex, ListContainer ListContai, SearchView sv, Component backComponen, RoundProgressBar pgb, List<SRContactor> cslist, List<SHistoryContactor> suggestions, boolean ishow) {
        try {
            context = contex;
            RoundPB = pgb;
            SV = sv;
            CXList = cslist;
            isShow = ishow;
            backComponent = backComponen;
            CPSR = new ContactProviderSR(context);
            CPSR.setAdapterClickListener(this);
            ListContai.setItemProvider(CPSR);
            ListContai.setVisibility(Component.HIDE);
            ListContai.enableScrollBar(Component.AXIS_Y, true);
            ListContai.setLongClickable(false);
            ListContai.setScrollbarRoundRect(true);
            ListContai.setScrollbarRadius(ListContai.getScrollbarThickness() / 2);
            ListContaine = ListContai;
            setSearchListener(SListener);
            SuggestionList = suggestions;
            if (ishow) {
                SuggestionContainer.setVisibility(Component.VISIBLE);
                hisyoryprovider = new HistoryProvider(SuggestionList);
                listContainer.setItemProvider(hisyoryprovider);
            }
        } catch (Exception e) {
            Utils.error(SearchView.label, e.getMessage());
        }
    }

    /**
     * Sets context.
     *
     * @param contex the contex
     * @return the context
     */
    public SearchView setContext(Context contex) {
        context = contex;
        return Builder();
    }

    /**
     * Sets round progress bar.
     *
     * @param pgb the pgb
     * @return the round progress bar
     */
    public SearchView setRoundProgressBar(RoundProgressBar pgb) {
        RoundPB = pgb;
        return Builder();
    }

    /**
     * Sets search view.
     *
     * @param sv the sv
     * @return the search view
     */
    public SearchView setSearchView(SearchView sv) {
        SV = sv;
        return Builder();
    }

    /**
     * Sets sr list container.
     *
     * @param cslist the cslist
     * @return the sr list container
     */
    public SearchView setSRListContainer(List<SRContactor> cslist) {
        CXList = cslist;
        return Builder();
    }

    /**
     * Sets suggestionis show.
     *
     * @param ishow the ishow
     * @return the suggestionis show
     */
    public SearchView setSuggestionisShow(boolean ishow) {
        isShow = ishow;
        return Builder();
    }

    /**
     * Sets back component.
     *
     * @param BackComponent the back component
     * @return the back component
     */
    public SearchView setBackComponent(Component BackComponent) {
        backComponent = BackComponent;
        return Builder();
    }

    /**
     * Sets list container.
     *
     * @param ListContai the list contai
     * @return the list container
     */
    public SearchView setListContainer(ListContainer ListContai) {
        CPSR = new ContactProviderSR(context);
        CPSR.setAdapterClickListener(this);
        ListContai.setItemProvider(CPSR);
        ListContai.setVisibility(Component.HIDE);
        ListContai.enableScrollBar(Component.AXIS_Y, true);
        ListContai.setLongClickable(false);
        ListContai.setScrollbarRoundRect(true);
        ListContai.setScrollbarRadius(ListContai.getScrollbarThickness() / 2);
        ListContaine = ListContai;
        return Builder();
    }

    /**
     * Sets suggestion list.
     *
     * @param suggestions the suggestions
     * @return the suggestion list
     */
    public SearchView setSuggestionList(List<SHistoryContactor> suggestions) {
        SuggestionList = suggestions;
        return Builder();
    }

    /**
     * Builder search view.
     *
     * @return the search view
     */
    public SearchView Builder() {
        Utils.debug(SearchView.label, "Builder");
        if (isShow) {
            SuggestionContainer.setVisibility(Component.VISIBLE);
            hisyoryprovider = new HistoryProvider(SuggestionList);
            listContainer.setItemProvider(hisyoryprovider);
        }
        setSearchListener(SListener);
        return this;
    }

    /**
     * Onsearch.
     *
     * @param contex       the contex
     * @param ListContai   the list contai
     * @param sv           the sv
     * @param backComponen the back componen
     * @param pgb          the pgb
     * @param cslist       the cslist
     * @param ishow        the ishow
     */
    public void Onsearch(Context contex, ListContainer ListContai, SearchView sv, Component backComponen, RoundProgressBar pgb, List<SRContactor> cslist, boolean ishow) {
        try {
            context = contex;
            RoundPB = pgb;
            SV = sv;
            CXList = cslist;
            isShow = ishow;
            backComponent = backComponen;
            CPSR = new ContactProviderSR(context);
            CPSR.setAdapterClickListener(this);
            ListContai.setItemProvider(CPSR);
            ListContai.setVisibility(Component.HIDE);
            ListContai.enableScrollBar(Component.AXIS_Y, true);
            ListContai.setLongClickable(false);
            ListContai.setScrollbarRoundRect(true);
            ListContai.setScrollbarRadius(ListContai.getScrollbarThickness() / 2);
            ListContaine = ListContai;
            setSearchListener(SListener);
            if (ishow) {
                SuggestionContainer.setVisibility(Component.VISIBLE);
                hisyoryprovider = new HistoryProvider(SuggestionList);
                listContainer.setItemProvider(hisyoryprovider);
            }
        } catch (Exception e) {
            Utils.error(SearchView.label, e.getMessage());
        }
    }

    /**
     * Onsearch.
     *
     * @param contex       the contex
     * @param ListContai   the list contai
     * @param sv           the sv
     * @param backComponen the back componen
     * @param pgb          the pgb
     * @param cslist       the cslist
     */
    public void Onsearch(Context contex, ListContainer ListContai, SearchView sv, Component backComponen, RoundProgressBar pgb, List<SRContactor> cslist) {
        try {
            context = contex;
            RoundPB = pgb;
            SV = sv;
            CXList = cslist;
            isShow = false;
            backComponent = backComponen;
            CPSR = new ContactProviderSR(context);
            CPSR.setAdapterClickListener(this);
            ListContai.setItemProvider(CPSR);
            ListContai.setVisibility(Component.HIDE);
            ListContai.enableScrollBar(Component.AXIS_Y, true);
            ListContai.setLongClickable(false);
            ListContai.setScrollbarRoundRect(true);
            ListContai.setScrollbarRadius(ListContai.getScrollbarThickness() / 2);
            ListContaine = ListContai;
            setSearchListener(SListener);
        } catch (Exception e) {
            Utils.error(SearchView.label, e.getMessage());
        }
    }

    @Override
    public void onComponentBoundToWindow(Component component) {
        getContext().getUITaskDispatcher()
                .delayDispatch(new Runnable() {
                                   @Override
                                   public void run() {
                                       InputSearchText.requestFocus();
                                       SearchView.showKeyboard();
                                   }
                               },
                        200);
    }

    @Override
    public void onComponentUnboundFromWindow(Component component) {

    }

    /**
     * Sets back click listener.
     *
     * @param backClickListener the back click listener
     */
    public void setBackClickListener(BackClickListener backClickListener) {
        this.backClickListener = backClickListener;
    }

    private void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    /**
     * Sets custom listener.
     *
     * @param customlistener the customlistener
     */
    public void setCustomListener(CustomListener customlistener) {
        customListener = customlistener;
    }

    /**
     * Sets bt 1 listener.
     *
     * @param B1L the b 1 l
     */
    public void setBt1Listener(Bt1Listener B1L) {
        bt1Listener = B1L;
    }

    /**
     * Sets bt 2 listener.
     *
     * @param B2L the b 2 l
     */
    public void setBt2Listener(Bt2Listener B2L) {
        bt2Listener = B2L;
    }

    /**
     * Sets bt 3 listener.
     *
     * @param B3L the b 3 l
     */
    public void setBt3Listener(Bt3Listener B3L) {
        bt3Listener = B3L;
    }

    /**
     * Sets bt 4 listener.
     *
     * @param B4L the b 4 l
     */
    public void setBt4Listener(Bt4Listener B4L) {
        bt4Listener = B4L;
    }

    /**
     * Sets null warn listener.
     *
     * @param nullWarn the null warn
     */
    public void setNullWarnListener(NullWarnListener nullWarn) {
        nullWarnListener = nullWarn;
    }

    /**
     * Sets none warn listener.
     *
     * @param noneWarn the none warn
     */
    public void setNoneWarnListener(NoneWarnListener noneWarn) {
        noneWarnListener = noneWarn;
    }

    /**
     * Sets remove listener.
     *
     * @param removeListener the remove listener
     */
    public void setRemoveListener(RemoveListener removeListener) {
        this.removeListener = removeListener;
    }

    /**
     * Is expand boolean.
     *
     * @return the boolean
     */
    public boolean isExpand() {
        return state == State.EXPANDED;
    }

    /**
     * Show search view.
     */
    public void showSearchView() {
        startExpandAnimation(true);
    }

    /**
     * Hide search view.
     */
    public void hideSearchView() {
        startExpandAnimation(false);
    }

    /**
     * On scroll.
     *
     * @param component  the component
     * @param scrollX    the scroll x
     * @param scrollY    the scroll y
     * @param oldScrollX the old scroll x
     * @param oldScrollY the old scroll y
     */
    public void onScroll(Component component, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (InputSearchText.hasFocus()) {
            InputSearchText.clearFocus();
        }
    }

    private synchronized void startExpandAnimation(boolean expand) {
        if (animExpand != null && animExpand.isRunning()) {
            return;
        }
        state = expand ? State.EXPANDED : State.COLLAPSED;
        animExpand = new AnimatorValue();
        animExpand.setCurveType(Animator.CurveType.LINEAR);
        animExpand.setDuration(200);
        animExpand.setValueUpdateListener(new AnimatorValue.ValueUpdateListener() {
            @Override
            public void onUpdate(AnimatorValue animatorValue, float v) {
                animSearchView(v);
            }
        });
        animExpand.start();
    }

    private void animSearchView(float v) {
        int tem = searchLayout.getMarginTop() + searchLayout.getHeight() + getPaddingTop() + getPaddingBottom();
        setTranslationY(-tem * (state == State.EXPANDED ? (1f - v) : v));
    }


    private enum State {
        /**
         * Expanded state.
         */
        EXPANDED,
        /**
         * Collapsed state.
         */
        COLLAPSED
    }

    /**
     * The interface Back click listener.
     */
    public interface BackClickListener {
        /**
         * On back click.
         */
        void onBackClick();
    }

    /**
     * The interface Search listener.
     */
    public interface SearchListener {
        /**
         * On search.
         *
         * @param search the search
         */
        void onSearch(String search);
    }

    /**
     * The interface Custom listener.
     */
    public interface CustomListener {
        /**
         * On filter.
         *
         * @param filter the filter
         */
        void onFilter(String filter);
    }

    /**
     * The interface Bt 1 listener.
     */
    public interface Bt1Listener {
        /**
         * Onb t 1.
         *
         * @param position the position
         */
        void onbT1(int position);
    }

    /**
     * The interface Bt 2 listener.
     */
    public interface Bt2Listener {
        /**
         * Onb t 2.
         *
         * @param position the position
         */
        void onbT2(int position);
    }

    /**
     * The interface Bt 3 listener.
     */
    public interface Bt3Listener {
        /**
         * Onb t 3.
         *
         * @param position the position
         */
        void onbT3(int position);
    }

    /**
     * The interface Bt 4 listener.
     */
    public interface Bt4Listener {
        /**
         * Onb t 4.
         *
         * @param position the position
         */
        void onbT4(int position);
    }

    /**
     * The interface Null warn listener.
     */
    public interface NullWarnListener {
        /**
         * On null error.
         */
        void onNullError();
    }

    /**
     * The interface None warn listener.
     */
    public interface NoneWarnListener {
        /**
         * On null warn.
         */
        void onNullWarn();
    }

    /**
     * The interface Remove listener.
     */
    public interface RemoveListener {
        /**
         * On remove history.
         *
         * @param position   the position
         * @param suggestion the suggestion
         */
        void onRemoveHistory(int position, SHistoryContactor suggestion);
    }

    private class HistoryProvider extends BaseItemProvider {
        private List<SHistoryContactor> mData;

        /**
         * Instantiates a new History provider.
         *
         * @param data the data
         */
        HistoryProvider(List<SHistoryContactor> data) {
            mData = data;
        }

        /**
         * Sets data.
         *
         * @param mData the m data
         */
        void setData(List<SHistoryContactor> mData) {
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public void notifyDataChanged() {
            startResetHistoryHeightAnimation();
            super.notifyDataChanged();
        }

        @Override
        public SHistoryContactor getItem(int i) {
            return mData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
            ViewHolder holder;
            if (component == null) {
                holder = new ViewHolder();
                if (iswear) {
                    component = LayoutScatter.getInstance(componentContainer.getContext())
                            .parse(ResourceTable.Layout_persistent_search_view_suggestion_item_layoutw, componentContainer, false);
                } else {
                    component = LayoutScatter.getInstance(componentContainer.getContext())
                            .parse(ResourceTable.Layout_persistent_search_view_suggestion_item_layout, componentContainer, false);
                }

                holder.iconIv = (Image) component.findComponentById(ResourceTable.Id_iconIv);
                holder.removeBtnIv = (Image) component.findComponentById(ResourceTable.Id_removeBtnIv);
                holder.textTv = (Text) component.findComponentById(ResourceTable.Id_textTv);
                holder.removeBtnWrapperFl = component.findComponentById(ResourceTable.Id_removeBtnWrapperFl);
                component.setTag(holder);
            } else {
                holder = (ViewHolder) component.getTag();
            }
            SHistoryContactor item = getItem(i);
            if (item.getSearchValue() == null) {
                holder.textTv.setText("");
            } else {
                holder.textTv.setText(item.getSearchValue());
            }
            if (item.getSearchIcon() == -1) {
                holder.iconIv.setVisibility(Component.HIDE);
            } else {
                holder.iconIv.setVisibility(Component.VISIBLE);
            }
            if (item.getRemoveIcon() == -1) {
                holder.removeBtnWrapperFl.setVisibility(Component.HIDE);
            } else {
                holder.removeBtnWrapperFl.setVisibility(Component.VISIBLE);
            }
            try {
                if (item.getSearchIcon() == -1) {
                    holder.iconIv.setImageElement(null);
                }
                if (item.getSearchIcon() == -3) {
                    holder.iconIv.setImageElement(HistoryPicture);
                } else {
                    holder.iconIv.setImageElement(new PixelMapElement(componentContainer.getContext().getResourceManager().getResource(item.getSearchIcon())));
                }
                if (item.getRemoveIcon() == -1) {
                    holder.removeBtnIv.setImageElement(null);
                } else if (item.getRemoveIcon() == -3) {
                    holder.removeBtnIv.setImageElement(DelHistoryPicture);
                } else {
                    holder.removeBtnIv.setImageElement(new PixelMapElement(componentContainer.getContext().getResourceManager().getResource(item.getRemoveIcon())));
                }
            } catch (Exception e) {
                Utils.error(SearchView.label, e.getMessage());
            }
            holder.removeBtnWrapperFl.setClickedListener(new ClickedListener() {
                @Override
                public void onClick(Component component1) {
                    mData.remove(i);
                    notifyDataChanged();
                    if (removeListener != null) {
                        removeListener.onRemoveHistory(i, item);
                    }
                }
            });
            return component;
        }

        private class ViewHolder {
            private Image iconIv;
            private Image removeBtnIv;
            private Text textTv;
            private Component removeBtnWrapperFl;
        }
    }

}
