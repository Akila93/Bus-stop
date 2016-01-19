package adapter.local;

import java.util.List;

import com.bustop.R;
import row.pack.Route;

import adapter.local.BusAdapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RouteAdapter extends ArrayAdapter<Route> {

	private final List<Route> list;
    private final Activity context;


    static class ViewHolder {
        protected TextView text;
    }
    public RouteAdapter(Activity context, List<Route> list) {
        super(context, R.layout.route_row, list);
        this.context = context;
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            convertView = inflator.inflate(R.layout.route_row, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.route_label);
           
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.route_label, viewHolder.text);
            } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.text.setText(list.get(position).getName()+" "+list.get(position).getRoute_id());
        
        return convertView;
    }

}
