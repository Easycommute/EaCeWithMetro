package easycommute.EaCeWithMetro.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.api.EasyCommuteApi;
import easycommute.EaCeWithMetro.api.data.response.ApiResponse;
import easycommute.EaCeWithMetro.models.City;
import easycommute.EaCeWithMetro.models.Commuter;
import easycommute.EaCeWithMetro.utils.AppConstants;
import easycommute.EaCeWithMetro.utils.BaseFragment;
import easycommute.EaCeWithMetro.utils.EasySingleton;
import easycommute.EaCeWithMetro.utils.PreferenceManager;
import easycommute.EaCeWithMetro.utils.UtilsCityList;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ProfileFragment extends BaseFragment {
    private RadioGroup radioGroup;
    private EditText phoneEdt;
    private EditText emailEdt;
    private EditText nameEdt;
    Spinner spinnerCityList;
    TextView txtBookingId;
    RelativeLayout rlLayout;
    String spinnerText;
    int cityId=0;
    List<Integer> cityIdList = new ArrayList<>();
    PreferenceManager preferenceManager;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        phoneEdt = (EditText) getView().findViewById(R.id.inputMobile);
        emailEdt = (EditText) getView().findViewById(R.id.inputEmail);
        preferenceManager=new PreferenceManager(getActivity());
        radioGroup = (RadioGroup) getView().findViewById(R.id.myRadioGroup);
        nameEdt  = (EditText) getView().findViewById(R.id.inputName);
        spinnerCityList=(Spinner) getView().findViewById(R.id.spinnerCityList);
        txtBookingId=(TextView)getView().findViewById(R.id.txtBookingId);
        rlLayout=(RelativeLayout)getView().findViewById(R.id.rlLayout);

        /*rlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferenceManager.getBookingId()==null)
                {
                    Fragment fragment = new StopSelectionFragment();
                    launchFragment(fragment, fragment.getTag());
                }
                else
                {
                    Fragment fragment = new MainTabListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstants.BOOKING_ID, Integer.parseInt(preferenceManager.getBookingId()));
                    bundle.putInt(AppConstants.FRAGMENT_NUM, AppConstants.BOARDING_DETAILS);
                    fragment.setArguments(bundle);
                    launchFragment(fragment, fragment.getTag());
                }
            }
        });

        txtBookingId.setBackgroundColor(getResources().getColor(R.color.ticket_top));
        txtBookingId.setText(preferenceManager.getBookingMessage());
*/


        getCityList();
        spinnerCityList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    cityId=0;
                    return;
                }
                else {
                    spinnerText=adapterView.getItemAtPosition(i).toString();
                    cityId=cityIdList.get(i);
                    Log.d("PRFRGMT_cityId_I",""+cityId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getView().findViewById(R.id.btn_edit_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gender = radioGroup.getCheckedRadioButtonId()
                        == R.id.male ? "M" : "F";

                String email = emailEdt.getText().toString();
                String name  = nameEdt.getText().toString();
                Commuter commuter = EasySingleton.getInstance()
                        .getCommuter();
                try {
                    verifyEmail(email);
                    verifyInput(name);

                    commuter.gender = gender;
                    commuter.email = email;
                    commuter.name = name;
                    commuter.cityId=cityId;

                    if(cityId!=0) {
                        updateCommuter(commuter);
                    }
                    else {
                        showToast("Please select city");
                    }
                } catch (Exception e) {
                    showToast(e.getMessage());
                }
            }
        });
        updateProfile();
    }

    private void updateProfile() {
        Commuter commuter = EasySingleton.getInstance()
                .getCommuter();

        emailEdt.setText(commuter.email);
        phoneEdt.setText(commuter.phone);

        nameEdt.setText(commuter.name);

//        if(commuter.cityId!=0){
//            spinnerCityList.set(city[commuter.cityId]);
//        }

        ((RadioButton) getView().findViewById(commuter.gender.equals("M") ? R.id.male
                : R.id.female)).setChecked(true);
        cityId=commuter.cityId;

    }

    private void updateCommuter(final Commuter commuter) {
        commuter.commuterId = getCommuterId();
        showProgressBar();

        EasyCommuteApi.getService().updateProfile(commuter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ApiResponse>() {
                    @Override
                    public void call(ApiResponse apiResponse) {
                        new PreferenceManager(getContext()).updateCommuter(commuter);
                        Log.d("PRFRGMT_val",""+commuter.cityId);
                        updateProfileInfo();

                        showToast(apiResponse.message);
                        hideProgressBar();
                    }
                }, errorHandler);
    }
    private void getCityList(){
        showProgressBar();

        EasyCommuteApi.getService().getCityList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<City>() {
                    @Override
                    public void call(City cityList) {
                        Commuter commuter=preferenceManager.getCommuter();
                        cityId=commuter.cityId;
                        String cityNameList[]=new String[cityList.cityDataList.size()+1];
                        cityNameList[0]="Select City";
                        cityIdList.add(0);

                        String dummy="";

                        for(int i=0;i<cityList.cityDataList.size();i++){
                            cityNameList[i+1]=cityList.cityDataList.get(i).name;
                            cityIdList.add(cityList.cityDataList.get(i).id);
                            if(cityList.cityDataList.get(i).id==cityId){
                                dummy=cityList.cityDataList.get(i).name;
                            }
                        }

                        for(int i=1;i<cityNameList.length;i++){
                            if(dummy.endsWith(cityNameList[i])){
                                cityId=i;
                                break;
                            }
                        }

                        spinnerCityList.setAdapter(new UtilsCityList().setAdapter(getActivity(),cityNameList));

                        spinnerCityList.setSelection(cityId);
                        hideProgressBar();
                    }
                }, errorHandler);
    }

    private void verifyEmail(String email) {
        if (email.isEmpty()) {
            throw new RuntimeException(getString(R.string.warning_email_empty));
        } else if (!Pattern.compile(AppConstants.EMAIL_PATTERN).matcher(email).matches()) {
            throw new RuntimeException(getString(R.string.warning_email_not_proper));
        }
    }

    private void verifyInput(String input) {
        if (input.isEmpty()) {
            throw new RuntimeException(getString(R.string.warning_name_empty));
        } else if (Pattern.compile("[^A-Za-z0-9 ]").matcher(input).find()) {
            throw new RuntimeException(getString(R.string.warning_name_spl_char));
        } else if (Character.isDigit(input.charAt(0))) {
            throw new RuntimeException(getString(R.string.warning_name_digit));
        }
    }


    private void verifyCompany(String input) {
        if (input.isEmpty()) {
            throw new RuntimeException(getString(R.string.warning_company_empty));
        }
    }

    @Override
    protected int getTitle() {
        return R.string.title_profile;
    }
}
