package info.jiangchuan.wrca.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import info.jiangchuan.wrca.R;

/**
 * Created by jiangchuan on 1/25/15.
 */
public class SimpleArrayAdapter extends ArrayAdapter{

    public SimpleArrayAdapter(Context context, Object[] objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
}
