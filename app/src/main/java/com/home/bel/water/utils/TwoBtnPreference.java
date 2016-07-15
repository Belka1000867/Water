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
public abstract class TwoBtnPreference extends Preference implements View.OnClickListener {

    private Button btnFirst;
    private Button btnSecond;

    private int btnFirstId;
    private int btnSecondId;

    private Object valueFirst;
    private Object valueSecond;

    public TwoBtnPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public TwoBtnPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public TwoBtnPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TwoBtnPreference(Context context) {
        super(context);
        init(context);
    }

    abstract void init(Context context);

    abstract void setPersistValue(Object value);

    abstract Object getPersistValue();

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        // Disable click from the Preference View
        holder.itemView.setClickable(false);

        btnFirst = (Button) holder.findViewById(getBtnFirstId());
        btnSecond = (Button) holder.findViewById(getBtnSecondId());

        btnFirst.setOnClickListener(this);
        btnSecond.setOnClickListener(this);

        Object currentValue = getPersistValue();
        final Button bFocused = currentValue.equals(valueFirst) ? btnFirst : btnSecond;
        focusButton(bFocused);
    }

    private void focusButton(Button btn){
        btn.setBackgroundColor(Color.rgb(121, 218, 255));
        btn.setEnabled(false);
        btn.setTextColor(Color.rgb(255, 133, 0));

        final Button oppositeButton = getOppositeBtn(btn);
        oppositeButton.setTextColor(Color.BLACK);
        oppositeButton.setBackgroundResource(0);
        oppositeButton.setEnabled(true);
    }

    private Button getOppositeBtn(Button button){
        return button.equals(btnFirst) ? btnSecond : btnFirst;
    }

    @Override
    public void onClick(View v) {

        if(v.equals(btnFirst)){
            focusButton(btnFirst);
            setPersistValue(valueFirst);
        }
        else{
            focusButton(btnSecond);
            setPersistValue(valueSecond);
        }

    }

    void setBtnFirstId(int id){
        btnFirstId = id;
    }

    void setBtnSecondId(int id){
        btnSecondId = id;
    }

    int getBtnFirstId(){
        return btnFirstId;
    }

    int getBtnSecondId(){
        return btnSecondId;
    }

    void setValueFirst(Object value){
        valueFirst = value;
    }

    void setValueSecond(Object value){
        valueSecond = value;
    }

    Object getValueFirst(){
        return valueFirst;
    }

    Object getValueSecond(){
        return valueSecond;
    }

}
