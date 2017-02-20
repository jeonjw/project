package cc.foxtail.teamprojectmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ProjectListActivity extends SingleFragmentActivity {


    public static Intent newIntent (Context packageContext){
        Intent i = new Intent(packageContext,ProjectListActivity.class);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return new ProjectListFragment();
    }


}
