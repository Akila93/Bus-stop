package adapter.local;

import java.util.List;

import row.pack.Bus;
import com.bustop.R;

import adapter.local.MyAdapter.ViewHolder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

public class BusAdapter extends ArrayAdapter<Bus> {
    
    private final List<Bus> list;
    private final Activity context;


    static class ViewHolder {
        protected TextView text;
    }
    public BusAdapter(Activity context, List<Bus> list) {
        super(context, R.layout.bus_row, list);
        this.context = context;
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            convertView = inflator.inflate(R.layout.bus_row, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.bus_label);
           
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.bus_label, viewHolder.text);
            } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.text.setText(list.get(position).getPathName());
        
        return convertView;
    }
}
