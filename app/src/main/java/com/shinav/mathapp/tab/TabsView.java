package com.shinav.mathapp.tab;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.view.ButterKnifeLayout;

import javax.inject.Inject;

import butterknife.InjectView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;

public class TabsView extends ButterKnifeLayout {

    @InjectView(R.id.tab_host) MaterialTabHost tabHost;
    @InjectView(R.id.tabs_pager) ViewPager tabsPager;

    @Inject TabsPagerAdapter tabsPagerAdapter;

    private SimpleMaterialTabListener tabListener;

    public TabsView(Context context) {
        super(context);
        init(context);
    }

    public TabsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Injector.getViewComponent(context).inject(this);

        inflate(R.layout.tabs_layout, this, true);

        tabListener =  new SimpleMaterialTabListener() {
            @Override public void onTabSelected(MaterialTab materialTab) {
                tabsPager.setCurrentItem(materialTab.getPosition());
            }
        };

        tabsPager.setAdapter(tabsPagerAdapter);
        tabsPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });
    }

    public void addTab(String text, ViewGroup viewGroup) {
        addTabToTabHost(text);
        addTabToPagerAdapter(viewGroup);
    }

    private void addTabToTabHost(String text) {
        tabHost.addTab(createTab(text));
    }

    private void addTabToPagerAdapter(ViewGroup viewGroup) {
        tabsPagerAdapter.addTab(viewGroup);
    }

    private MaterialTab createTab(String text) {
        return tabHost.newTab().setText(text).setTabListener(tabListener);
    }

}
