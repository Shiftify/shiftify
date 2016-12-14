package cz.cvut.fit.shiftify.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.cvut.fit.shiftify.R;

public class AboutFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        TextView githubLink = (TextView) view.findViewById(R.id.more_github);
        String htmlLink = "<a href='" + getResources().getString(R.string.github_url) + "'>" +
                getResources().getString(R.string.more_github) + "</a>";
        githubLink.setText(Html.fromHtml(htmlLink));
        githubLink.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }
}
