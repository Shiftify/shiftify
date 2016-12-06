package cz.cvut.fit.shiftify.exceptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import cz.cvut.fit.shiftify.PersonDetailActivity;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by petr on 12/6/16.
 */

public class ExceptionEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_edit);

        Intent intent = getIntent();
        int exceptionId = intent.getIntExtra(ExceptionListActivity.EXCEPTION_ID, 0);
        int userId = intent.getIntExtra(ExceptionListActivity.USER_ID, 0);

        if (exceptionId == 0) {
            ToolbarUtils.setToolbar(this, R.string.exception_add);
        } else {
            ToolbarUtils.setToolbar(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
