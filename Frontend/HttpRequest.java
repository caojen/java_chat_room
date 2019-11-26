package Frontend;

import java.util.Map;

/**
 * To send HttpRequest to specific url
 */
public interface HttpRequest {
  /**
   * To send get-http-request to url with param formatted into map
   * @param url Target url
   * @param map Params
   * @return a key-value map
   * @throws Exception
   */
  public Map<String, String> get(String url, Map<String, String> map) throws Exception;

  /**
   * To send post-http-request to url with param formatted into map
   * @param url Target url
   * @param map Params
   * @return a key-value map
   * @throws Exception
   */
  public Map<String, String> post(String url, Map<String, String> map) throws Exception;

  /**
   * To send put-http-request to url with param formatted into map
   * @param url Target url
   * @param map Params
   * @return a key-value map
   * @throws Exception
   */
  public Map<String, String> put(String url, Map<String, String> map) throws Exception;

  /**
   * To send delete-http-request to url with param formatted into map
   * @param url Target url
   * @param map Params
   * @return a key-value map
   * @throws Exception
   */
  public Map<String, String> delete(String url, Map<String, String> map) throws Exception;

  /**
   * To Format The Http-Result From Server
   * The data should be formatted in this way:
   * status=200, 403, etc       ('\n')  
   * data=????                    ('\n')  
   * ...                              (break with '\n', but end with EOF)  
   * @param data The callback-data
   * @return a key-value map
   * @throws Exception if data format is not correct
   */
  Map<String, String> formatHttpResult(String data) throws Exception;
}