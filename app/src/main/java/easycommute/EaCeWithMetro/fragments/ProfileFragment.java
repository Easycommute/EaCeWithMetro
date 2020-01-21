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

// All the external communication string (which would be visible to
// the android users, be it in exceptions or screen title or anything else,
// They need to be standardized and kept in one file. This would prepare
// our app to be multilingual.
// Aditi will mark those as POTENTIAL_MULTILINGUAL_STRINGS in her comments

// At some places we are prepared for us being multilingual in this fragment
// Example: verifyCompany function
// throw new RuntimeException(getString(R.string.warning_company_empty));

public class ProfileFragment extends BaseFragment {
    private RadioGroup radioGroup;
    private EditText phoneEdt;
    private EditText emailEdt;
    private EditText nameEdt;
    Spinner spinnerCityList;
    TextView txtBookingId;
    RelativeLayout rlLayout;
    String spinnerText;

    // TODO_NAVEEN :- Add the comment to mention why 0 is being used and not some other value
    // Shall we be putting all the hardcoded values for this class in a seperate file? if yes,
    // decide what that file would be & do the rest of implementation.

    int cityId = 0;  // 0 is for hyderabad metro

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
        radioGroup = (RadioGroup) getView().findViewById(R.id.myRadioGroup);
        nameEdt  = (EditText) getView().findViewById(R.id.inputName);
        spinnerCityList = (Spinner) getView().findViewById(R.id.spinnerCityList);
        txtBookingId = (TextView)getView().findViewById(R.id.txtBookingId);
        rlLayout = (RelativeLayout)getView().findViewById(R.id.rlLayout);

        preferenceManager = new PreferenceManager(getActivity());


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
                    commuter.cityId = cityId;

                    if(cityId!=0) {
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fetchProfileDetails();
    }

    //TODO_NAVEEN: Please add comment here to explain what this private
    // function does. And does it have some prerequsites? Like this fetchProfileDetails
    // function assumes that the RadioButton of this view is initialized.

    private void fetchProfileDetails()  // load the profile form details
    {
        Commuter commuter = EasySingleton.getInstance()
                .getCommuter();
        emailEdt.setText(commuter.email);
        phoneEdt.setText(commuter.phone);
        nameEdt.setText(commuter.name);

        //TODO_NAVEEN: There is a better way to write this, figure it out & discuss.
        ((RadioButton) getView().findViewById(commuter.gender.equals("M") ? R.id.male : R.id.female)).setChecked(true);

        // So, here we are setting the cityId variable as per the information from
        // oommuters -- Then is this not fetchProfile? Why is it called update?
        // As I see it, we are setting local variable of our class by looking up the
        // backend. Naveen: What do you think?
        cityId = commuter.cityId;
    }

    //TODO_NAVEEN: Please add comment here to explain what this private
    // function does. And does it have some prerequsites?

    private void updateCommuter(final Commuter commuter)// update the profile form details
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

    //TODO_NAVEEN: Please add comment here to explain what the output of this function would be?
    // Give sample output for clarity purpose in the comment.

    private void getCityList()  // fetch the city list e.g, Delhi, hyderabad, Bangalore etc
    {
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

                        //Hardcoded values in code! - Move to the constant files please.
                        cityNameList[0] = getResources().getString(R.string.select_city);
                        cityIdList.add(0);
                        String selectedCity=""; // dont call a variable dummy please :)

                        //NOTICE: How aditi has added spaces to code to look cleaner and readable.
                        //TODO_NAVEEN: Follow my example to fix readability of code in all our products.
                        for(int i = 0; i < cityList.cityDataList.size(); i++)
                        {
                            cityNameList[i+1]=cityList.cityDataList.get(i).name;
                            cityIdList.add(cityList.cityDataList.get(i).id);
                            if(cityList.cityDataList.get(i).id==cityId)
                            {
                                selectedCity=cityList.cityDataList.get(i).name;
                            }
                        }
                        for(int i=1;i<cityNameList.length;i++)
                        {
                            if(selectedCity.endsWith(cityNameList[i]))
                            {
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
            // POTENTIAL_MULTILINGUAL_STRINGS
            throw new RuntimeException(getString(R.string.warning_email_empty));
        } else if (!Pattern.compile(AppConstants.EMAIL_PATTERN).matcher(email).matches()) {
            // POTENTIAL_MULTILINGUAL_STRINGS
            throw new RuntimeException(getString(R.string.warning_email_not_proper));
        }
    }

    private void verifyInput(String input) {
        if (input.isEmpty()) {
            //POTENTIAL_MULTILINGUAL_STRINGS
            throw new RuntimeException(getString(R.string.warning_name_empty));
        } else if (Pattern.compile("[^A-Za-z0-9 ]").matcher(input).find()) {
            //POTENTIAL_MULTILINGUAL_STRINGS
            throw new RuntimeException(getString(R.string.warning_name_spl_char));
        } else if (Character.isDigit(input.charAt(0))) {
            //POTENTIAL_MULTILINGUAL_STRINGS
            throw new RuntimeException(getString(R.string.warning_name_digit));
        }
    }



    @Override
    protected int getTitle() {
        // POTENTIAL_MULTILINGUAL_STRINGS
        return R.string.title_profile;
    }
}
