package com.imDigital.chitchat.model;

import android.os.AsyncTask;
import android.util.Log;

import com.imDigital.chitchat.constants.AppConstant;
import com.imDigital.chitchat.interfaces.GNetworkEvent;
import com.imDigital.chitchat.parsers.ParserKeys;
import com.imDigital.chitchat.preferences.AppPreferences;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class NetworkService {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String service;
    private String mMethod;
    private GNetworkEvent networkEvent;


    /** Constructor */
    public NetworkService(String serviceName, String method, GNetworkEvent networkEvent) {
        this.service = serviceName;
        this.mMethod = method;
        this.networkEvent = networkEvent;
    }
    /** End Contructor */

    /** Converting Stream to string */
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    /** End Converting Stream to string */

    /** Setting Service Url Externally */
    public void setService(String serviceName) {
        service = serviceName;
    }
    /** End Setting Service Url Externally */

    /** Call to network async event with input as request*/
    public void call(NetworkModel input){
        new NetworkTask().execute(input);
    }

    /** Async Operation of Service */
    private class NetworkTask extends AsyncTask<NetworkModel, Void, String>{
        boolean isError = false;
        String message = "";

        @Override
        protected void onPreExecute() {
            if (networkEvent != null) {
                networkEvent.onNetworkCallInitiated(service);
            }
        }

        @Override
        protected String doInBackground(NetworkModel... networkModels){

            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            NetworkModel input = networkModels[0];
            int responseCode = -1;

            try {
                String service = NetworkService.this.service.split(",")[0];

                final OkHttpClient client = new OkHttpClient();
                client.setReadTimeout(60, TimeUnit.SECONDS);

                if (!service.startsWith("http"))
                    service = AppConstant.URL + service;

                String jsonReq = input.getJsonBody();

                String accessToken = "";

                if (AppPreferences.INSTANCE.getAccessToken().equals(""))
                    accessToken = AppConstant.ACCESS_TOKEN;
                else
                    accessToken = AppPreferences.INSTANCE.getAccessToken();

                Log.e("access_token_header ", "==>  " + accessToken);

                RequestBody requestBody = RequestBody.create(JSON, jsonReq);
                Log.e("JSON ", "Request==>  " + service +"=> " +jsonReq);

                Request request = null;
                if (mMethod.equalsIgnoreCase(AppConstant.METHOD_POST)){
                    request = new Request.Builder()
                            .url(service)
                            .addHeader(ParserKeys.accessToken.toString(), accessToken)
                            .post(requestBody)
                            .build();
                } else if (mMethod.equalsIgnoreCase(AppConstant.METHOD_GET)){
//                    String url[]=service.split("\\?");
//                    try {
//                        service = URLEncoder.encode(url[1], "utf-8");
//                        service=url[0]+"?"+service;
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
                    request = new Request.Builder()
                            .url(service)
                            .get()
                            .addHeader(ParserKeys.accessToken.toString(), accessToken)
                            .build();
                } else if (mMethod.equalsIgnoreCase(AppConstant.METHOD_PUT)) {
                    request = new Request.Builder()
                            .url(service)
                            .addHeader(ParserKeys.accessToken.toString(), accessToken)
                            .put(requestBody)
                            .build();
                } else if (mMethod.equalsIgnoreCase(AppConstant.METHOD_DELETE)) {
                    request = new Request.Builder()
                            .url(service)
                            .delete()
                            .addHeader(ParserKeys.accessToken.toString(), accessToken)
                            .build();
                    Log.e("deleteApi======>",""+ParserKeys.accessToken.toString()+" "+accessToken);
                }

                Response response = client.newCall(request).execute();

                Log.e("response in NW", response.networkResponse().toString());
                if (response.isSuccessful()) {
                    isError = false;
                    String resStr = response.body().string();
                    return resStr;
                }
                else if(response.code()==401){
                    isError = false;
                    return "Invalid Credentials";
                }
                else {
                    isError = true;
                    message = "response error";
                }

            } catch (UnsupportedEncodingException e) {
                isError = true;
                message = e.getMessage();
                e.printStackTrace();
            } catch (IOException e) {
                isError = true;
                message = e.getMessage();
                e.printStackTrace();
            } catch (android.net.ParseException e) {
              isError = true;
                message = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                isError = true;
                message = e.getMessage();
                e.printStackTrace();
            }
            return responseCode + ": " + message;
        }

        @Override
        protected void onPostExecute(String resStr) {
            if (networkEvent != null) {
                try {
                    if (isError) {
                        networkEvent.onNetworkCallError(service, message);
                    } else {
                        Log.d("ResponseJson: ", ""+resStr);
                        networkEvent.onNetworkCallCompleted(service, resStr);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
