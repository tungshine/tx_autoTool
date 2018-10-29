package wechat.resp;

import java.util.ArrayList;
import java.util.List;

import com.shifenkafei.sflc.wechat.util.Constants;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:11
 * @description: 复杂按钮（父按钮）
 */
public class ComplexButton extends Button {

    private int size = 0;

    private List<Button> sub_button = new ArrayList<Button>(5);

    public List<Button> getSub_button() {
        return sub_button;
    }

    public ComplexButton addButton(Button subButton) {
        if (size < Constants.MAX_BUTTON_SIZE) {
            sub_button.add(subButton);
            size++;
            return this;
        }
        throw new IllegalStateException("subButtons expand the MaxNum:" + Constants.MAX_BUTTON_SIZE);

    }
}