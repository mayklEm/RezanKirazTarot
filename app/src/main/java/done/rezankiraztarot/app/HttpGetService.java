package done.rezankiraztarot.app;

import android.os.AsyncTask;

/**
 * Created by Peter on 16.6.2014.
 */
public class HttpGetService extends AsyncTask<String, Integer, String> {
    String url;

    // class constructor
    public HttpGetService(String url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        try {
            String capabilityToken = HttpHelper.httpGet(url);

            // it goes to onPostExecute as a parameter.(result)
            return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzY29wZSI6InNjb3BlOmNsaWVudDppbmNvbWluZz9jbGllbnROYW1lPWplbm55IHNjb3BlOmNsaWVudDpvdXRnb2luZz9hcHBTaWQ9QVAyNWVmNzJmOGE2YWU5ZTU4Yzg0ODYzYmRhZjE5MjQ5OCZhcHBQYXJhbXM9JmNsaWVudE5hbWU9amVubnkiLCJpc3MiOiJBQ2I0NjQ0NWE1NDRhOTZkM2UxMGNkYmI0ZDEwNGEwNmExIiwiZXhwIjoxNDAzMjc1MTA4fQ.GrOGICncnGRU01oUaoCm6M8eo0R5c5o4YMhv9QkIWDI";
        } catch (Exception e) { }
        return null;
    }
}
