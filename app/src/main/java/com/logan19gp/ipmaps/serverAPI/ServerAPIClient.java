package com.logan19gp.ipmaps.serverAPI;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logan19gp.ipmaps.util.Utilities;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by george on 6/14/15.
 */
public class ServerAPIClient {
    private static RequestQueue mRequestQueue = null;
    private static RetryPolicy retryPolicy = null;
    private static final int socketTimeout = 10000;//10 seconds
    protected static Gson gson = new Gson();

    public static class RawJSONandStringBody {
        public final JSONObject jsonObject;
        public final String body;
        private Serializable extra;

        public RawJSONandStringBody(String body, JSONObject jsonObject) {
            this.jsonObject = jsonObject;
            this.body = body;
        }

        public void putExtra(Serializable extra) {
            this.extra = extra;
        }
        public Serializable getExtra() {
            return extra;
        }
    }

    public static Map<String, String> serverHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "*/*");
        headers.put("DNT", "1");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
        headers.put("X-App-Token", "ypaVcXJF4NB3cg46KFjHusJgH");
        return headers;
    }

    public ServerAPIClient() {

    }

    public static void addRequestToQueue(Request request) {
        request.setRetryPolicy(retryPolicy);
        mRequestQueue.add(request);
    }

    public static synchronized void initializeRequestQueue(Context context) {
        if (mRequestQueue == null) {
            retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    public static String getServer() {
        return "";
    }

    private String constructGETrequestURL(String methodName, HashMap<String, String> parameterValues) {
        Uri.Builder builder = Uri.parse(getServer()).buildUpon();
        builder.path(methodName);
        if (parameterValues != null && parameterValues.size() > 0) {
            for (String key : parameterValues.keySet()) {
                builder.appendQueryParameter(key, parameterValues.get(key));
            }
        }
        return builder.toString();
    }

    public <T> ResponseOrError<T> addRequest_synchronous_custom(final int methodType, final String url, String requestBody,
                                                                final Class<T> responseClass, final Map<String, String> serverHeadersOverrides, final String contentTypeOverride) {
        long startTime = System.currentTimeMillis();
        try {
            RequestFuture<T> future = RequestFuture.newFuture();
            Utilities.logMsg("******** call *********" + methodType + "  " + requestBody);
            JsonRequest<T> request = new JsonRequest<T>(methodType, url, requestBody, future, future) {
                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    try {
                        ServerError serverError = null;
                        String jsonString = new String(volleyError.networkResponse.data,
                                HttpHeaderParser.parseCharset(volleyError.networkResponse.headers));
                        Utilities.logMsg("errorResp:" + jsonString);

                        if (serverError == null) {
                            return volleyError;
                        }
                        return serverError;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return volleyError;
                    }
                }

                @Override
                protected Response<T> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        Utilities.logMsg("response:  " + jsonString + " ********** ens call *********");
                        if (responseClass == JSONObject.class) {
                            JSONObject jObject = new JSONObject(jsonString);
                            return Response.success((T) jObject, HttpHeaderParser.parseCacheHeaders(response));
                        } else if (responseClass == RawJSONandStringBody.class) {
                            JSONObject jObject = new JSONObject(jsonString);
                            RawJSONandStringBody retObj = new RawJSONandStringBody(jsonString, jObject);
                            return Response.success((T) retObj, HttpHeaderParser.parseCacheHeaders(response));
                        }

                        T responseAsResponseClass = gson.fromJson(jsonString, responseClass);

                        if (responseAsResponseClass instanceof JSONObjectWithExceptionSuppression) {
                            ((JSONObjectWithExceptionSuppression) responseAsResponseClass).initializeJSONMapWithCurrentAttributes();
                        }

                        return Response.success(responseAsResponseClass, HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {
                        return Response.error(new ParseError(e));
                    } catch (Exception ex) {
                        return Response.error(new VolleyError(ex));
                    }
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    if (serverHeadersOverrides != null) {
                        return serverHeadersOverrides;
                    } else {
                        return serverHeaders();
                    }
                }

                @Override
                public String getBodyContentType() {
                    if (contentTypeOverride != null) {
                        return contentTypeOverride;
                    } else {
                        return super.getBodyContentType();
                    }
                }
            };

            addRequestToQueue(request);

            try {
                T responseAsResponseClass = null;
                responseAsResponseClass = future.get(); // this will block
                return new ResponseOrError<T>(responseAsResponseClass, null);
            } catch (InterruptedException ex) {
                return new ResponseOrError<T>(null, new VolleyError(ex));
            } catch (ExecutionException ex) {
                ex.printStackTrace();
                if (ex.getCause() instanceof ServerError) {
                    return new ResponseOrError<T>(null, (ServerError) ex.getCause());
                } else {
                    return new ResponseOrError<T>(null, new VolleyError(ex.getMessage()));
                }
            }
        } finally {
            long endTime = System.currentTimeMillis();
            Log.d("REQUEST_TIME", "REQUEST_TIME(new):" + (endTime - startTime) + ":" + url);
        }

    }

}
