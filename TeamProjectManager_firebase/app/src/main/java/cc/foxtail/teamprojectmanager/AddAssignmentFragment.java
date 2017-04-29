package cc.foxtail.teamprojectmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;

public class AddAssignmentFragment extends Fragment {
    private Button mAddAssignButton;
    private List<CalendarDay> selectedDateList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_assignment, container, false);
        selectedDateList = new ArrayList<>();

        final EditText titleEditText = (EditText) view.findViewById(R.id.assignment_title_edit_text);
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mAddAssignButton.setEnabled(true);
                } else {
                    mAddAssignButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        final EditText assignmentDetialEditText = (EditText) view.findViewById(R.id.assignment_detail_edit_text);


        mAddAssignButton = (Button) view.findViewById(R.id.add_assign_button);
        mAddAssignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String detail = assignmentDetialEditText.getText().toString();

                if(title.length() <= 0) {
                    Toast.makeText(getActivity(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedDateList.size()==0){
                    Toast.makeText(getActivity(), "날짜를 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Assignment assignment = new Assignment(title,detail,changeDateToString(selectedDateList));

                Fragment fragment = getTargetFragment();
                ((AssignmentFragment) fragment).addAssignment(assignment);

                getFragmentManager().popBackStack();

            }
        });

        Button returnButton = (Button) view.findViewById(R.id.return_assign_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }
    private List<String> changeDateToString(List<CalendarDay> dateList) {

        List<String> stringDateList = new ArrayList<>();

        for (CalendarDay date : dateList) {
            String dateToString = String.valueOf(date.getYear() + "." + (date.getMonth() + 1) + "." + date.getDay());
            stringDateList.add(dateToString);
        }
        return stringDateList;
    }

    public void setSelectedDateList(List<CalendarDay> selectedDateList) {
        this.selectedDateList.clear();
        this.selectedDateList.addAll(selectedDateList);
    }
}
