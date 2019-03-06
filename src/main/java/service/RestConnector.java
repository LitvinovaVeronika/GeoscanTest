package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

public class RestConnector {

    private final static String URL = "http://18.212.200.162:8080/employees";
    private final static String BASE_PATH = "/";

    private static String getEncoding(String user, String password) {
        String auth = user + ":" + password;
        return Base64.encodeBase64String(auth.getBytes());
    }

    private static String getErrorMessage(Integer errorNumber) {

        switch(errorNumber) {
            case 200:
                return "OK";
            case 401:
                return "Authentication information is missing or invalid";
            case 403:
                return "Accessing the resource you were trying to reach is forbidden for your role";
            case 404:
                return "The employee you were trying to reach is not found";
            case 409:
                return "Employee with username or emailId exists";
            case 422:
                return "Incorrect employee (entity fields are not valid)";
            default:
                return "Unknown code";

        }
    }

    public static Response getAllEmployees(String user, String password) {

        HttpClient httpclient = HttpClients.createDefault();
        HttpGet request = new HttpGet(URL);
        request.setHeader("Authorization", "Basic " + getEncoding(user, password));

        ObjectMapper mapper = new ObjectMapper();

        Response res = new Response();
        res.setOperationResult(false);

        try {

            HttpResponse response = httpclient.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            int code = response.getStatusLine().getStatusCode();
            res.setMessage(getErrorMessage(code));

            if (code == 200) {
                res.setOperationResult(true);
                res.setBody(mapper.readValue(json, EmployeeDto[].class));
            }

            return res;

        } catch (IOException e) {
            e.printStackTrace();
        }

        res.setOperationResult(false);
        res.setMessage("IO Problem");
        return res;
    }

    public static Response createEmployee(String admin, String adminPassword, EmployeeDto empl) {

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost request = new HttpPost(URL);
        request.setHeader("Authorization", "Basic " + getEncoding(admin, adminPassword));

        ObjectMapper mapper = new ObjectMapper();

        Response res = new Response();
        res.setOperationResult(false);

        try {
            StringEntity stringEntity = new StringEntity(mapper.writeValueAsString(empl));
            request.setEntity(stringEntity);
            request.addHeader("content-type", "application/json");

            HttpResponse response = httpclient.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            int code = response.getStatusLine().getStatusCode();
            res.setMessage(getErrorMessage(code));

            if (code == 200) {
                res.setOperationResult(true);
                res.setBody(mapper.readValue(json, EmployeeDto.class));
            }

            return res;

        } catch (IOException e) {
            e.printStackTrace();
        }

        res.setOperationResult(false);
        res.setMessage("IO Problem");
        return res;
    }

    public static Response getEmployeeById(String user, String password, int id) {

        HttpClient httpclient = HttpClients.createDefault();
        HttpGet request = new HttpGet(URL + BASE_PATH + id);
        request.setHeader("Authorization", "Basic " + getEncoding(user, password));

        ObjectMapper mapper = new ObjectMapper();

        Response res = new Response();
        res.setOperationResult(false);

        try {

            HttpResponse response = httpclient.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            int code = response.getStatusLine().getStatusCode();
            res.setMessage(getErrorMessage(code));

            if (code == 200) {
                res.setOperationResult(true);
                res.setBody(mapper.readValue(json, EmployeeDto.class));
            }

            return res;

        } catch (IOException e) {
            e.printStackTrace();
        }

        res.setOperationResult(false);
        res.setMessage("IO Problem");
        return res;
    }

    public static Response updateEmployee(String admin, String adminPassword, EmployeeDto empl, Integer id) {

        HttpClient httpclient = HttpClients.createDefault();
        HttpPut request = new HttpPut(URL + BASE_PATH + id);
        request.setHeader("Authorization", "Basic " + getEncoding(admin, adminPassword));

        ObjectMapper mapper = new ObjectMapper();

        Response res = new Response();
        res.setOperationResult(false);


        try {
            StringEntity stringEntity = new StringEntity(mapper.writeValueAsString(empl));
            request.setEntity(stringEntity);
            request.addHeader("content-type", "application/json");

            HttpResponse response = httpclient.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            int code = response.getStatusLine().getStatusCode();
            res.setMessage(getErrorMessage(code));

            if (code == 200) {
                res.setOperationResult(true);
                res.setBody(mapper.readValue(json, EmployeeDto.class));
            }

            return res;

        } catch (IOException e) {
            e.printStackTrace();
        }

        res.setOperationResult(false);
        res.setMessage("IO Problem");
        return res;
    }

    public static Response deleteEmployee(String admin, String adminPassword, int id) {

        HttpClient httpclient = HttpClients.createDefault();
        HttpDelete request = new HttpDelete(URL + BASE_PATH + id);
        request.setHeader("Authorization", "Basic " + getEncoding(admin, adminPassword));

        ObjectMapper mapper = new ObjectMapper();

        Response res = new Response();
        res.setOperationResult(false);

        try {

            HttpResponse response = httpclient.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            int code = response.getStatusLine().getStatusCode();
            res.setMessage(getErrorMessage(code));

            if (code == 200) {
                res.setOperationResult(true);
                res.setBody(mapper.readValue(json, BooleanDto.class));
            }

            return res;

        } catch (IOException e) {
            e.printStackTrace();
        }

        res.setOperationResult(false);
        res.setMessage("IO Problem");
        return res;
    }
}
