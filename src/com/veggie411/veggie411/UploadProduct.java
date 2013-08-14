package com.veggie411.veggie411;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;

import android.os.AsyncTask;
import android.util.Log;

public class UploadProduct extends AsyncTask<Object, Integer, String> {

	static void post(Product p) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(Constants.SERVER_URI);
		MultipartEntity multipartEntity = new MultipartEntity();
		BasicHttpResponse httpResponse = null;
		//MainActivity.UploadProductLock.lock();

		try {
			//File fi = new File(HeartBrainService.filepath);
			multipartEntity.addPart("barcode", new StringBody(p.getBarcode()));
			multipartEntity.addPart("name", new StringBody(p.getName()));
			multipartEntity.addPart("ingredients", new StringBody(p.getIngredients().toString()));
			multipartEntity.addPart("brand", new StringBody(p.getBrand()));
			
			//InputStreamBody isb = new InputStreamBody(new ByteArrayInputStream(byteArray), "image/jpeg", fileImage);
	        //multipartEntity.addPart("image", isb);
			//MultipartEntity.addPart("email", new StringBody());
			//multipartEntity.addPart(Constants.PHONE_ID, new StringBody(phoneID));
			//multipartEntity.addPart("file", new FileBody((fi), "application/zip"));
			//multipartEntity.addPart("file", new FileBody(fi));
			httpPost.setEntity(multipartEntity);
			Log.i("in upload", "executing httpPost");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			String error = "PD1 EXCEPTION @"+new Date().toString()+":"+e1.getClass()+","+e1.getMessage();
			//HelperMethods.saveError(error);
			//HeartBrainService.sErrorLog.println(error);
		} catch (Exception e) {
			e.printStackTrace();
			String error = "PD1.1 EXCEPTION @"+new Date().toString()+":"+e.getClass()+","+e.getMessage();
			//HelperMethods.saveError(error);
			//HeartBrainService.sErrorLog.println(error);
		}  finally {
			//MainActivity.UploadProductLock.unlock();
		}
		try {
			httpResponse = (BasicHttpResponse) httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			String error = "PD2 EXCEPTION @"+new Date().toString()+":"+e.getClass()+","+e.getMessage();
			//HelperMethods.saveError(error);
			//HeartBrainService.sErrorLog.println(error);
		} catch (Exception e) {
			e.printStackTrace();
			String error = "PD2.1 EXCEPTION @"+new Date().toString()+":"+e.getClass()+","+e.getMessage();
			//HelperMethods.saveError(error);
			//HeartBrainService.sErrorLog.println(error);
		}
		String totalResponse = null;
		try {
			Log.i("received", httpResponse.getStatusLine().toString()+", "+
					httpResponse.getProtocolVersion().toString());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				//TODO SUCCESS
			} else {
				//TODO FAIL
			}
			//totalResponse = HelperMethods.readInputStream(httpResponse.getEntity().getContent());
			//Log.i("all results", totalResponse.toString());
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			String error = "PD4 EXCEPTION @"+new Date().toString()+":"+e1.getClass()+","+e1.getMessage();
		//} catch (IOException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
			//String error = "PD3.1 EXCEPTION @"+new Date().toString()+":"+e2.getClass()+","+e2.getMessage();
		} 
		catch (Exception e) {
			e.printStackTrace();
			String error = "PD3.2 EXCEPTION @"+new Date().toString()+":"+e.getClass()+","+e.getMessage();
		}
	}
	
	@Override
	protected String doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		Product p = (Product) arg0[0];
		Log.i("called", "post");
		post(p);
		return "SUCCESS";
	}
}
