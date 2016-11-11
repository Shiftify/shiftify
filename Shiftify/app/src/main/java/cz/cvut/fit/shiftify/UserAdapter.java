package cz.cvut.fit.shiftify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cz.cvut.fit.shiftify.data.User;

public class UserAdapter extends ArrayAdapter<User> {

    private final Context mContext;
    private final int mResourceId;

    static class ViewHolder {
        public TextView name;
        public TextView surname;
    }

    public UserAdapter(Context context, int resource, User[] users) {
        super(context, resource, users);
        mContext = context;
        mResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item_users, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.surname = (TextView) convertView.findViewById(R.id.user_surname);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User user = getItem(position);
        viewHolder.name.setText(user.FirstName);
        viewHolder.surname.setText(user.Surname);

        return convertView;
    }

}
