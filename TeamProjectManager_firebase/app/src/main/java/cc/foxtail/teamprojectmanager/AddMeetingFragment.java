package cc.foxtail.teamprojectmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;

public class AddMeetingFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MeetingTimeAdapter meetingTimeAdapter;
    private List<CalendarDay> selectedDateList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_meeting, container, false);
        selectedDateList = new ArrayList<>();


        final List<MeetingTime> meetingTimeList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            meetingTimeList.add(new MeetingTime(i, i + 1));
        }


        mRecyclerView = (RecyclerView) view.findViewById(R.id.time_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        meetingTimeAdapter = new MeetingTimeAdapter(meetingTimeList);


        Button returnButton = (Button) view.findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        Button addMeetingButton = (Button) view.findViewById(R.id.add_meeting_button);
        addMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selectedDateList.size() == 0) {
                    getFragmentManager().popBackStack();
                    return;
                }

                List<MeetingTime> checkedList = new ArrayList<>();

                for (int i = 0; i < meetingTimeList.size(); i++) {
                    MeetingTime meetingTime = meetingTimeList.get(i);

                    if (meetingTime.isChecked()) {
                        meetingTime.setChecked(false);
                        checkedList.add(meetingTime);
                    }
                }

                if(checkedList.size()==0){
                    Toast.makeText(getActivity(),"시간을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                Meeting meeting = new Meeting(selectedDateList, checkedList);

                Fragment fragment = getTargetFragment();
                ((MeetingFragment) fragment).addMeeting(meeting);

                getFragmentManager().popBackStack();
            }

        });


        return view;
    }


    public void setSelectedDateList(List<CalendarDay> selectedDateList) {
        this.selectedDateList.clear();
        this.selectedDateList.addAll(selectedDateList);

        if (selectedDateList.size() != 0) {
            mRecyclerView.setAdapter(meetingTimeAdapter);
        } else {
            mRecyclerView.setAdapter(null);
        }
    }
    /*
    * 달력 날짜를 클릭할때 리스트에 추가하고 다시 또 클릭이되면 리스트에서 제거를 하는게 더 좋을듯하다.
    * onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected)
    * 여기서 date값을 로그 찍어보니 선택되고 해제될때마다 로그가 나왔다.
    *
    * */


    public class MeetingTimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private MeetingTime mMeetingTime;

        public MeetingTimeHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.time_range_text_view);
            itemView.setOnClickListener(this);

        }

        public void bindTimeTable(MeetingTime meetingTime) {
            mMeetingTime = meetingTime;
            mTitleTextView.setText(mMeetingTime.getStartHour() + ":00" + " ~ " + mMeetingTime.getEndHour() + ":00");
        }

        @Override
        public void onClick(View v) {
            if (!mMeetingTime.isChecked())
                mMeetingTime.setChecked(true);
            else {
                mMeetingTime.setChecked(false);
            }
            meetingTimeAdapter.notifyDataSetChanged();

        }
    }

    public class MeetingTimeAdapter extends RecyclerView.Adapter<MeetingTimeHolder> {
        private List<MeetingTime> meetingTimeList;

        public MeetingTimeAdapter(List<MeetingTime> meetingTimeList) {
            this.meetingTimeList = meetingTimeList;
        }

        @Override
        public MeetingTimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_time, parent, false);


            return new MeetingTimeHolder(view);
        }

        @Override
        public void onBindViewHolder(final MeetingTimeHolder holder, int position) {
            MeetingTime meetingTime = meetingTimeList.get(position);
            holder.bindTimeTable(meetingTime);

            if (meetingTime.isChecked())
                holder.mTitleTextView.setBackgroundResource(R.drawable.time_table_selected_background);
            else {
                holder.mTitleTextView.setBackgroundResource(R.drawable.time_table_unselected_background);
            }

        }

        @Override
        public int getItemCount() {
            return meetingTimeList.size();
        }


    }


}
