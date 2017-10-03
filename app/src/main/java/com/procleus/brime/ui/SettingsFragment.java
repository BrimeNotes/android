/**
 * @author Saad Hassan <hassan.saad.mail@gmail.com>
 * @author Suraj Rawat <suraj.raw120@gmail.com>
 * @author Swastik Binjola <swastik.binjola2561@gmail.com>
 * @author Ujjwal Bhardwaj <ujjwalb1996@gmail.com>
 *
 * @license AGPL-3.0
 *
 * This code is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License, version 3,
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package com.procleus.brime.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.Window;


import com.procleus.brime.R;

public class SettingsFragment extends PreferenceFragment {

    private static final String PREFERENCE_ACCOUNT_INFO="accinfo";
    private static final String PREFERENCE_ABOUT_US="aboutus";
    private static final String PREFERENCE_CHANGE_PASSWORD="changepass";
    private static final String PREFERENCE_SHARE="shareme";
    private static final String PREFERENCE_HELP="helpme";
    private static final String PREFERENCE_SIGN_OUT="signout";
    private Activity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_settings);
        this.findPreference(PREFERENCE_ABOUT_US).setOnPreferenceClickListener(new SettingsClickListener(PREFERENCE_ABOUT_US));
        this.findPreference(PREFERENCE_ACCOUNT_INFO).setOnPreferenceClickListener(new SettingsClickListener(PREFERENCE_ACCOUNT_INFO));
        this.findPreference(PREFERENCE_SIGN_OUT).setOnPreferenceClickListener(new SettingsClickListener(PREFERENCE_SIGN_OUT));
        this.findPreference(PREFERENCE_SHARE).setOnPreferenceClickListener(new SettingsClickListener(PREFERENCE_SHARE));
        this.findPreference(PREFERENCE_HELP).setOnPreferenceClickListener(new SettingsClickListener(PREFERENCE_HELP));
        this.findPreference(PREFERENCE_CHANGE_PASSWORD).setOnPreferenceClickListener(new SettingsClickListener(PREFERENCE_CHANGE_PASSWORD));



    }

    public void signOut() {
    }


    //required to get current activity
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }


    private class SettingsClickListener implements Preference.OnPreferenceClickListener{

        private String option;
        private String title="Setting";

        public SettingsClickListener(String option){
            this.option=option;
        }

        @Override
            public boolean onPreferenceClick(Preference preference) {
                Fragment fragment = null;

                switch (option) {
                    case PREFERENCE_ACCOUNT_INFO:
                        fragment = new AccountInfoFragment();
                        title="Account Info";
                        break;
                    case PREFERENCE_ABOUT_US:
                        title="About Us";
                        Dialog dialog = new Dialog(activity);
                        dialog.setContentView(R.layout.about_us_layout);
                        dialog.setTitle(title);
                        dialog.show();
                        break;
                    case PREFERENCE_SIGN_OUT:
                        signOut();
                        break;
                    case PREFERENCE_SHARE:
                        //TODO: share app
                        break;
                    case PREFERENCE_CHANGE_PASSWORD:
                        fragment = new ChangePasswordFragment();
                        title="Change Password";
                        break;
                    case PREFERENCE_HELP:
                        //fragment = new HelpFragment();
                        title="Help";
                        break;
                    default:
                        throw new AssertionError();
                }

                if (fragment != null) {
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, fragment,"pref_setting");
                    ((SettingsActivity) getActivity()).setActionBarTitle(title);
                    ft.addToBackStack("Settings");
                    ft.commit();

                }

                return true;
        }
    }


}



