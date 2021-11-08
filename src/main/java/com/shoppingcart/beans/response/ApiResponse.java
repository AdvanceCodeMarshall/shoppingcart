package com.shoppingcart.beans.response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiResponse {

	private String message;
	private String token;
	JSONArray array = null;
	HashMap<String, String> data = null;
	JSONObject json = null;
	private List<String> errors;

	public ApiResponse(String message, List<String> errors) {
		super();
		this.message = message;
		this.errors = errors;
	}

	public ApiResponse(HashMap<String, String> data, String token) {
		this.data = data;
		this.token = token;
	}

	public ApiResponse(JSONArray array, String token) {
		this.array = array;

		this.token = token;
	}

	public ApiResponse(JSONObject json, String token) {
		this.json = json;
		this.token = token;
	}

	public ApiResponse(String token, String message, List<String> errors) {
		super();
		this.token = token;
		this.message = message;
		this.errors = errors;
	}

	public ApiResponse(String message, String error) {
		super();
		this.message = message;
		errors = Arrays.asList(error);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JSONArray getArray() {
		return array;
	}

	public void setArray(JSONArray array) {
		this.array = array;
	}

	public HashMap<String, String> getData() {
		return data;
	}

	public void setData(HashMap<String, String> data) {
		this.data = data;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
