package tk.tk_test_androidapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskActivity extends Activity implements View.OnClickListener{

    Button startBtn, dialogBtn;
    TextView resultTxtView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        context = this;
        startBtn = (Button)findViewById(R.id.asyncTaskStart_btn);
        startBtn.setOnClickListener(this);
        dialogBtn = (Button)findViewById(R.id.asyncTaskDialog_btn);
        dialogBtn.setOnClickListener(this);

        resultTxtView = (TextView)findViewById(R.id.result_textView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.asyncTaskStart_btn:
                Toast.makeText(AsyncTaskActivity.this, "ckckckckk", Toast.LENGTH_SHORT).show();
                new DownloadTask().execute("http://www.naver.com");
                break;
            case R.id.asyncTaskDialog_btn:
                new AsyncTaskTest().execute(10);
        }
    }

    private class DownloadTask extends AsyncTask<String, Void, String>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            InputStream stream = null;
            String str="";

            try {
                stream = downloadUrl(urls[0]);
                str = readIt(stream, 500);

            }catch (IOException e){
                return "eeeerrrrooooo!!!";
            }finally {
                if(stream != null){
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            Log.d("tk_test", result);
            resultTxtView.setText(result);
        }

    }

    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     * @param urlString A string representation of a URL.
     * @return An InputStream retrieved from a successful HttpURLConnection.
     * @throws java.io.IOException
     */
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setReadTimeout(10000); /* milliseconds */
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect(); // Start the query

        InputStream stream = conn.getInputStream();
        return stream;
    }

    /** Reads an InputStream and converts it to a String.
     * @param stream InputStream containing HTML from targeted site.
     * @param len Length of string that this method returns.
     * @return String concatenated according to len parameter.
     * @throws java.io.IOException
     * @throws java.io.UnsupportedEncodingException
     */
    private String readIt(InputStream stream, int len) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    private class AsyncTaskTest extends AsyncTask<Integer, String, Integer>
    {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("작업시작");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            final int count = integers[0];
            publishProgress("max",Integer.toString(count)); //onProgressUpdate 실행
            for(int i=1;i<=count;i++){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }

                publishProgress("progress",Integer.toString(i),"작업번호"+Integer.toString(i)+"번 수행중!");
            }
            return count;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if(values[0].equals("progress")){
                dialog.setProgress(Integer.parseInt(values[1]));
                dialog.setMessage(values[2]);
            }else if(values[0].equals("max")){
                dialog.setMax(Integer.parseInt(values[1]));
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dialog.dismiss();
            Toast.makeText(context,Integer.toString(integer)+"개 작업완료",Toast.LENGTH_SHORT).show();
        }
    }



}
