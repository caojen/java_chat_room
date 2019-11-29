package Frontend.Http;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.Receiver;

import Frontend.Log.Log;

public class HttpRequestSender implements HttpRequest {
  @Override
  public Map<String, String> get(String url, Map<String, String> map) throws Exception {
    if(map == null) {
      map = new HashMap<String, String>();
    }

    map.put("method", "GET");
    Log.writeMap(map);

    String param = "";
    String value = "";
    try {
      for (String key : map.keySet()) {
        value = map.get(key);
        value = URLEncoder.encode(value, "utf-8");
        if (param.equals("")) {
          param = param + "?" + key + "=" + value;
        } else {
          param = param + "&" + key + "=" + value;
        }
      }

      URL _url = new URL(url + param);
      HttpURLConnection connection = (HttpURLConnection) _url.openConnection();
      connection.setDoOutput(true);
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset = UTF-8");

      BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
      String receiveData = null;
      StringBuilder result = new StringBuilder();
      while ((receiveData = br.readLine()) != null) {
        result.append(receiveData + "\n");
      }

      connection.disconnect();
      return formatHttpResult(result.toString());
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @Override
  public Map<String, String> post(String url, Map<String, String> map) throws Exception {
    if(map == null) {
      map = new HashMap<String, String>();
    }

    map.put("method", "POST");
    Log.writeMap(map);

    URL _url = new URL(url);
    HttpURLConnection connection = (HttpURLConnection) _url.openConnection();
    
    connection.setDoInput(true);
    connection.setDoOutput(true);
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset = UTF-8");
    PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));

    String param = "";
    try {
      for(String key: map.keySet()) {
        String value = URLEncoder.encode(map.get(key), "utf-8");
        if(!param.equals("")) {
          param += "&";
        }
        param += key + "=" + value;
      }
    } catch (Exception e) {
      throw new Exception(e);
    }

    pw.write(param);
    pw.flush();
    pw.close();

    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

    String receiveData = null;
    StringBuilder result = new StringBuilder();
    while((receiveData = br.readLine()) != null) {
      result.append(receiveData + '\n');
    }
    connection.disconnect();

    return formatHttpResult(result.toString());
  }

  @Override
  @Deprecated
  public Map<String, String> put(String url, Map<String, String> map) throws Exception {
    throw new Exception("Not-Allowed");
  }

  @Override
  @Deprecated
  public Map<String, String> delete(String url, Map<String, String> map) throws Exception {
    throw new Exception("Not-Allowed");
  }

  @Override
  public Map<String, String> formatHttpResult(String data) throws Exception {
    String[] datas = data.split("\n");
    Map<String, String> result = new HashMap<String, String>();

    for(String value: datas) {
      String[] key_value = value.split("=");
      if(key_value.length != 2) {
        throw new Exception("Return_Value_Format_Exception");
      }
      result.put(key_value[0], URLDecoder.decode(key_value[1], "utf-8"));
    }
    return result;
  }
}