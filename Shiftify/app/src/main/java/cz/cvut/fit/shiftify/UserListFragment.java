package cz.cvut.fit.shiftify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cz.cvut.fit.shiftify.data.User;

public class UserListFragment extends ListFragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        User users[] = {new User("Petr", "Pansky", "123456789"),
                new User("Vojta", "Mach", "123456789"),
                new User("Lukas", "Komarek", "123456789")};
        ArrayAdapter<User> userAdapter = new UserAdapter(getActivity(), R.layout.list_item_users, users);
        setListAdapter(userAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        return view;
    }
}
