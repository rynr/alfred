package com.sinnerschrader.alfred;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.sinnerschrader.alfred.objects.Visit;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;


public class MainActivity extends ActionBarActivity {

    EditText email;
    EditText name;
    EditText company;
    Button submit;
    SharedPreferences prefs;
    DefaultHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, UserSettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public Button getSubmit() {
        if (submit == null) {
            submit = (Button) findViewById(R.id.field_form_submit);
        }
        return submit;
    }

    public EditText getCompany() {
        if (company == null) {
            company = (EditText) findViewById(R.id.field_form_company);
        }
        return company;
    }

    public EditText getName() {
        if (name == null) {
            name = (EditText) findViewById(R.id.field_form_name);
        }
        return name;
    }

    public EditText getEmail() {
        if (email == null) {
            email = (EditText) findViewById(R.id.field_form_email);
        }
        return email;
    }

    public SharedPreferences getPreferences() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
        }
        return prefs;
    }

    public void sendMessage(View view) {
        if (view.equals(getSubmit())) {
            try {
                persist(getVisit());
                clearForm();
            } catch (UnsupportedEncodingException e) {
                Log.e(getClass().getName(), e.getMessage());
            } catch (IOException e) {
                Log.e(getClass().getName(), e.getMessage());
            } catch (JSONException e) {
                Log.e(getClass().getName(), e.getMessage());
            }
        } else {
            Log.e(getClass().getName(), "Not submit");
        }
    }

    private Visit getVisit() {
        Visit result = new Visit();
        result.setEmail(((EditText) findViewById(R.id.field_form_email)).getText().toString());
        result.setName(((EditText) findViewById(R.id.field_form_name)).getText().toString());
        result.setCompany(((EditText) findViewById(R.id.field_form_company)).getText().toString());
        return result;
    }

    private DefaultHttpClient getClient() {
        if (client == null) {
            client = new DefaultHttpClient();
        }
        return client;
    }

    private void clearForm() {
        getEmail().setText("");
        getName().setText("");
        getCompany().setText("");
        if(getEmail().requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            getEmail().clearFocus();
            getCompany().clearFocus();
        }
    }

    private void persist(Visit visit) throws UnsupportedEncodingException, IOException, JSONException {
        new PersistTask().execute(visit);
    }

    class PersistTask extends AsyncTask<Visit, Void, HttpResponse> {

        private Exception exception;

        protected HttpResponse doInBackground(Visit... visits) {
            try {
                Visit visit = visits[0];
                HttpPost post = new HttpPost("http://jever.rjung.org:8080/visits");
                post.setHeader("Content-type", "application/json");
                JSONObject entity = new JSONObject();
                entity.accumulate("name", visit.getName());
                entity.accumulate("company", visit.getCompany());
                entity.accumulate("email", visit.getEmail());
                post.setEntity(new StringEntity(entity.toString(), "UTF-8"));
                return getClient().execute(post);
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(HttpResponse result) {
            if (result != null) {
                Log.d(getClass().getName(), new Integer(result.getStatusLine().getStatusCode()).toString());
            }
            if (this.exception != null) {
                Log.e(getClass().getName(), this.exception.getMessage());
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_meetup, container, false);
            return rootView;
        }
    }
}
