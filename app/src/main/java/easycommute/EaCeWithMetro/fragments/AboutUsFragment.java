package easycommute.EaCeWithMetro.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.api.EasyCommuteApi;
import easycommute.EaCeWithMetro.models.AboutUs;
import easycommute.EaCeWithMetro.utils.BaseFragment;
import easycommute.EaCeWithMetro.utils.PreferenceManager;
import easycommute.EaCeWithMetro.utils.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.widget.RelativeLayout;

import rx.functions.Action1;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.content.Intent;
import android.content.pm.PackageManager;


/**
 * Created by Ram Prasad on 10/28/2015.
 */
public class AboutUsFragment extends BaseFragment {
    private TextView phone, email, whatsApp, description, version,txtBookingId;
    private AboutUs about;
    RelativeLayout rlLayout;
    PreferenceManager preferenceManager;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_us, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        whatsApp = (TextView) getView().findViewById(R.id.whatsapp);
        rlLayout = (RelativeLayout) getView().findViewById(R.id.rlLayout);
        phone    = (TextView) getView().findViewById(R.id.phone);
        email    = (TextView) getView().findViewById(R.id.email);
        description = (TextView) getView().findViewById(R.id.description);

        fetchAboutUsDetails();

    }

    private void fetchAboutUsDetails() {
        EasyCommuteApi.getService().getAboutUsDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AboutUs>() {
                    @Override
                    public void call(AboutUs aboutUs) {
                        hideProgressBar();
                        about = aboutUs;
                        if(aboutUs!=null)
                            updateContent(aboutUs);
                    }
                }, errorHandler);
    }

    private void updateContent(final AboutUs aboutUs) {
        if(aboutUs.aboutUsDetails.description!=null)
            description.setText(Html.fromHtml(aboutUs.aboutUsDetails.description));
        description.setMovementMethod(LinkMovementMethod.getInstance());
        // description.setText(aboutUs.aboutUsDetails.description);
        if(aboutUs.aboutUsDetails.phone!=null)
            phone.setText("Phone: " +aboutUs.aboutUsDetails.phone);
        if(aboutUs.aboutUsDetails.email!=null)
            email.setText("Email: " +aboutUs.aboutUsDetails.email);
        if(aboutUs.aboutUsDetails.whatsapp!=null)
            whatsApp.setText("WhatsApp: " +aboutUs.aboutUsDetails.whatsapp);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPerson(aboutUs.aboutUsDetails.phone);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + aboutUs.aboutUsDetails.email));
                startActivity(intent);
            }
        });
        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent;
                boolean whatsappFound = Utils.isAppInstalled(getContext(), "com.whatsapp");
                Uri uri = Uri.parse("smsto:" + aboutUs.aboutUsDetails.whatsapp);
                sendIntent = new Intent(Intent.ACTION_SENDTO, uri);

                if (whatsappFound)
                    sendIntent.setPackage("com.whatsapp");

                startActivity(sendIntent);
            }
        });
    }

    private void callPerson(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        try{
            if(checkCallPermission()) {
                startActivity(intent);
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected int getTitle() {
        return R.string.title_about_us;
    }

    public boolean checkCallPermission() {
        //TODO_NAVEEN
       /* if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions(new String[] {android.Manifest.permission.CALL_PHONE}, 9001);
        }*/
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == 9001 && permissions[0] == android.Manifest.permission.CALL_PHONE &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(about != null) {
                callPerson(about.aboutUsDetails.phone);
            }
        }
    }
}
