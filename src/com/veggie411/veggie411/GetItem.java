package com.veggie411.veggie411;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GetItem extends AsyncTask<Object, Integer, String> {

	String code;
	String URI;
	ProgressDialog dialog;
	Context context;

	//need context?
	public GetItem(Context c, String s) {
		MainActivity.requesting = true;
		Log.i("GOT STRING", s);
		context = c;
		this.code = s;
		URI = Constants.URI;
	}


	@Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...getting product....");
        dialog.show();
    }

	@Override
	protected String doInBackground(Object... arg0) {
//		for (int i=0;i<900000000;i++) {
//		}
		getItem();
		return "SUCCESS";
	}
	
	@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        dialog.dismiss();
        if (MainActivity.curProduct != null) {
        	Intent viewProductActivity = new Intent(context, ViewProductActivity.class);
			viewProductActivity.putExtra(Constants.BARCODE, code);
			context.startActivity(viewProductActivity);
        } else {
        	Toast.makeText(context, Constants.TOAST_NO_PRODUCT, Toast.LENGTH_SHORT).show();
        }
    }


	public static String readInputStream(InputStream in) {
		return read(new InputStreamReader(in));
	}

	public static String read(InputStreamReader input) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStreamReader inputStreamReader = input;
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			bufferedReader.close();
			inputStreamReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "null";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "null";
		}
		return sb.toString();
	}

	private void getItem() {
		Log.i("GET_ITEM","starting");
		HttpClient httpClient = new DefaultHttpClient();
		JSONObject json = new JSONObject();
		//JSONArray jsonArray = new JSONArray();
		//URI = "https://api.nutritionix.com/v1_1/item";
		URI = "https://api.nutritionix.com/v1_1/item?appId=82d7fc68&appKey=42574fb537accef0d28c1003cbb50826&upc=";
		HttpGet httpGet = new HttpGet(URI+code);

		BasicHttpResponse httpResponse = null;
		try {
			Log.i("GET_ITEM","executing");
			httpResponse = (BasicHttpResponse) httpClient.execute(httpGet);
			Log.i("GET_ITEM","response");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			Log.i("SUCCESS", "IT WORKED");
			HttpEntity result = httpResponse.getEntity();
			if (result != null) {
				String name;
				String brand;
				String ingredients;
				try {
					//InputStream inputStream = result.getContent();
					//Log.i("RESULT", readInputStream(inputStream));
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(result.getContent(), baos);
					JSONObject jsonObject = null;
					try {
						jsonObject = new JSONObject(baos.toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						name = (String) jsonObject.get("item_name");
						Log.i("NAME", name);
						brand = (String) jsonObject.get("brand_name");
						Log.i("BRAND", brand);
						ingredients = (String) jsonObject.get("nf_ingredient_statement");
						Log.i("INGREDIENTS", ingredients);
						ArrayList<String> ingreds = getAllIngredients(ingredients);
						Product p = new Product(code);
						p.setName(name);
						p.setBrand(brand);
						p.setIngredients(ingreds);
						MainActivity.curProduct = p;
						MainActivity.requesting = false;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			MainActivity.curProduct = null;
			MainActivity.requesting = false;
			try {
				Log.i("MORE", readInputStream(httpResponse.getEntity().getContent()));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private ArrayList<String> getAllIngredients(String i) {
		ArrayList<String> ingreds = new ArrayList<String>();
		Log.i("working on", i);
		String[] next = null;
		Stack<String[]> stack = new Stack<String[]>();
		String[] temp = null;
		String[] cur = new String[1];
		cur[0] = i;
		stack.add(cur);
		
		while (!stack.isEmpty()) {
			cur = stack.pop();
			for (String s:cur) {
				//Log.i("CUR", s);
				if (s.contains(". ")) {
					next = s.split("\\. ");
					stack.add(next);
				} else if (s.contains(".")) {
					next = s.split("\\.");
					stack.add(next);
				} else if (s.contains("(") && s.contains(")")) {
					temp = new String[1];
					temp[0] = s.replaceAll("\\(.*\\)", "");
					stack.add(temp);
				} else if (s.contains("<") && s.contains(">")) {
					temp = new String[1];
					temp[0] = s.replaceAll("\\<.*\\>", "");
					stack.add(temp);
				} else if (s.contains(", ")) {
					next = s.split(", ");
					stack.add(next);
				} else if (s.contains(",")) {
					next = s.split(",");
					stack.add(next);
				} else if (s.contains(": ")) {
					next = s.split(": ");
					temp = new String[1];
					temp[0] = next[1];
					stack.add(temp);
				} else if (s.contains(":")) {
					next = s.split(":");
					temp = new String[1];
					temp[0] = next[1];
					stack.add(temp);
				} else if (s.contains(" and ")) {
					next = s.split(" and ");
					stack.add(next);
				} else {
					Log.i("now", s);
					ingreds.add(s.toUpperCase());
				}
			}
		}
		return ingreds;
	}
}
