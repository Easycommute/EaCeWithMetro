package easycommute.EaCeWithMetro.fragments;

import android.os.Bundle;
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
import java.util.regex.Matcher;
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
import easycommute.EaCeWithMetro.models.CityReq;
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
    RadioButton male, female;
    int cityId = 0;    // 0 is for default value

    List<Integer> cityIdList = new ArrayList<>();
    List<String> cityIdListDescription = new ArrayList<>();

    PreferenceManager preferenceManager = null;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        phoneEdt = (EditText) getView().findViewById(R.id.inputMobile);
        emailEdt = (EditText) getView().findViewById(R.id.inputEmail);
        radioGroup = (RadioGroup) getView().findViewById(R.id.myRadioGroup);
        nameEdt  = (EditText) getView().findViewById(R.id.inputName);
        spinnerCityList = (Spinner) getView().findViewById(R.id.spinnerCityList);
        txtBookingId = (TextView)getView().findViewById(R.id.txtBookingId);
        rlLayout = (RelativeLayout)getView().findViewById(R.id.rlLayout);

        male=(RadioButton)getView().findViewById(R.id.male);
        female=(RadioButton)getView().findViewById(R.id.female);
        preferenceManager = new PreferenceManager(getActivity());
        showProgressBar();

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
                    verifyName(name);
                    commuter.gender = gender;
                    commuter.email = email;
                    commuter.name = name;
                    commuter.cityId = cityId;

                    if(cityId != 0) {
                        updateCommuter(commuter);
                    }
                    else {
                        // POTENTIAL_MULTILINGUAL_STRINGS
                        showToast(getResources().getString(R.string.please_select_city));
                    }
                } catch (Exception e) {
                    showToast(e.getMessage());
                }
            }
        });

        loadCitySpinner();

        spinnerCityList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    cityId = 0;
                    return;
                }
                else {
                    spinnerText=adapterView.getItemAtPosition(i).toString();
                    cityId=cityIdList.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fetchProfileDetails();
    }

    // load the profile form details
    private void fetchProfileDetails()
    {
        Commuter commuter = EasySingleton.getInstance().getCommuter();
        emailEdt.setText(commuter.email);
        phoneEdt.setText(commuter.phone);
        nameEdt.setText(commuter.name);

        if (commuter.gender.equals("M"))
        {
            male.setChecked(true);
        }
        else
        {
            female.setChecked(true);
        }
        cityId = commuter.cityId;
    }

    // update the profile form details
    private void updateCommuter(final Commuter commuter)
    {
        commuter.commuterId = getCommuterId();
        showProgressBar();

        EasyCommuteApi.getService().updateProfile(commuter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ApiResponse>() {
                    @Override
                    public void call(ApiResponse apiResponse) {
                        new PreferenceManager(getContext()).updateCommuter(commuter);
                        updateProfileInfo();

                        showToast(apiResponse.message);
                        hideProgressBar();
                    }
                }, errorHandler);
    }


    // Fetch the city list where easycommute is deployed
    private void loadCitySpinner()
    {
        CityReq cObj= new CityReq(1);
        EasyCommuteApi.getService().getCityActiveList(cObj)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<City>() {
                    @Override
                    public void call(City cityList) {
                        Commuter commuter=preferenceManager.getCommuter();
                        cityId = commuter.cityId;
                        cityIdList.add(0);
                        cityIdListDescription.add(getResources().getString(R.string.select_city));
                        for(int i = 0; i < cityList.cityDataList.size(); i++)
                        {
                            cityIdList.add(cityList.cityDataList.get(i).id);
                            cityIdListDescription.add(cityList.cityDataList.get(i).name);
                        }
                        spinnerCityList.setAdapter(new UtilsCityList().setAdapter(getActivity(),cityIdListDescription));

                        spinnerCityList.setSelection(cityId);
                        hideProgressBar();
                    }
                }, errorHandler);
    }

    private void verifyEmail(String email) {
        if (email.isEmpty()) {
            // POTENTIAL_MULTILINGUAL_STRINGS
            throw new RuntimeException(getString(R.string.warning_email_empty));
        } else if (!Pattern.compile(AppConstants.EMAIL_PATTERN).matcher(email).matches()) {
            // POTENTIAL_MULTILINGUAL_STRINGS
            throw new RuntimeException(getString(R.string.warning_email_not_proper));
        }
    }

    private void verifyName(String input) {
        if (input.isEmpty()) {
            //POTENTIAL_MULTILINGUAL_STRINGS
            throw new RuntimeException(getString(R.string.warning_name_empty));
        }
        CharSequence inputStr = input;
        Pattern pattern = Pattern.compile(new String (AppConstants.NAME_PATTERN));
        Matcher matcher = pattern.matcher(inputStr);
        if(!matcher.matches())
        {
            //POTENTIAL_MULTILINGUAL_STRINGS
            throw new RuntimeException(getString(R.string.warning_name_spl_char));
        }

    }

    @Override
    protected int getTitle() {
        // POTENTIAL_MULTILINGUAL_STRINGS
        return R.string.title_profile;
    }
}
