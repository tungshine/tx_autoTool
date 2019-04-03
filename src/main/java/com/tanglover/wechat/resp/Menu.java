package com.tanglover.wechat.resp;

import com.tanglover.wechat.util.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * 菜单
 *
 * @author TungShine 2016年9月26日
 */
public class Menu {
    private int size = 0;
    private List<Button> button = new ArrayList<Button>(3);

    public List<Button> getButton() {
        return button;
    }

    public Menu addSubButton(Button subButton) {
        if (size < Constants.MAX_MENU_SIZE) {
            button.add(subButton);
            size++;
            return this;
        }
        throw new IllegalStateException("subButtons expand the MaxNum:" + Constants.MAX_MENU_SIZE);

    }
}