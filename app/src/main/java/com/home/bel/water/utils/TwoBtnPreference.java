package com.home.bel.water.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.home.bel.water.R;

/**
 * Interface of custom preference with two possible values
 * with one button for each.
 */
public abstract class TwoBtnPreference extends Preference implements View.OnClickListener, CustomPreferenceInterface {

    private Button btnFirst;
    private Button btnSecond;

    private int btnFirstId;
    private int btnSecondId;

    private Object valueFirst;
    private Object valueSecond;

    public abstract void init(Context context);

    public TwoBtnPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /* Abstract methods */

    public void setButtonIds(int btnFirstId, int btnSecondId){
        this.btnFirstId = btnFirstId;
        this.btnSecondId = btnSecondId;
    }

    public void setValues(Object valueFirst, Object valueSecond){
        this.valueFirst = valueFirst;
        this.valueSecond = valueSecond;
    }

    /* */

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        // Disable click from the Preference View
        holder.itemView.setClickable(false);

        btnFirst = (Button) holder.findViewById(btnFirstId);
        btnSecond = (Button) holder.findViewById(btnSecondId);
        btnFirst.setOnClickListener(this);
        btnSecond.setOnClickListener(this);

        Object currentValue = getPersistValue();
        final Button bFocused = currentValue.equals(valueFirst) ? btnFirst : btnSecond;
        focusButton(bFocused);
    }

    private void focusButton(Button btn){
        btn.setActivated(true);
        btn.setEnabled(false);

        final Button oppositeButton = getOppositeBtn(btn);
        oppositeButton.setEnabled(true);
        oppositeButton.setActivated(false);
    }

    private Button getOppositeBtn(Button button){
        return button.equals(btnFirst) ? btnSecond : btnFirst;
    }

    @Override
    public void onClick(View v) {
        Button btnFocused = v.equals(btnFirst) ? btnFirst : btnSecond;
        Object value = v.equals(btnFirst) ? valueFirst : valueSecond;
        focusButton(btnFocused);
        setPersistValue(value);
    }

    protected Object getValueFirst(){
        return valueFirst;
    }

}
