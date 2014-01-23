/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package com.dextrys.cordova.extgap;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//
import android.util.Log;
import android.content.Intent;
import android.net.Uri;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//

/**
 * This class provides ExtGapToolkits on the device.
 */
public class ExtGapToolkits extends CordovaPlugin {

    /**
     * Constructor.
     */
    public ExtGapToolkits() {
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArray of arguments for the plugin.
     * @param callbackContext   The callback context used when calling back into JavaScript.
     * @return                  True when the action was valid, false otherwise.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        // dirty inject
        if (action.equals("droidNaviTo")) {
			Log.i(DTAG,"droidNaviTo  begin");
            this.droidNaviTo(args.getString(0),args.getString(1));
			Log.i(DTAG,"droidNaviTo  end");
            //callbackContext.sendPluginResult(new PluginResult(status, b));//
            return true;
        }else if (action.equals("clipImage")) {
			Log.i(DTAG,"clipImage  begin");
            this.clipImage(args.getString(0),args.getString(1),args.getInt(2),args.getInt(3),args.getInt(4),args.getInt(5));
			Log.i(DTAG,"clipImage  end");
            //callbackContext.sendPluginResult(new PluginResult(status, b));//
            return true;
        }else if (action.equals("resizeImage")) {
			Log.i(DTAG,"resizeImage  begin");
            this.resizeImage(args.getString(0),args.getString(1),args.getInt(2),args.getInt(3));
			Log.i(DTAG,"resizeImage  end");
            //callbackContext.sendPluginResult(new PluginResult(status, b));//
            return true;
        }
        //
        else {
            return false;
        }

        // Only alert and confirm are async.
        callbackContext.success();
        return true;
    }

    //--------------------------------------------------------------------------
    // LOCAL METHODS
    //--------------------------------------------------------------------------







	// dirty inject
	
    public void droidNaviTo(String lat, String lon) {
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat +","+ lon)); 
		    this.cordova.getActivity().startActivity(i);
    }
	
	
    
    public void clipImage(String src,String dest,int x,int y,int w,int h) {
	    BitmapFactory.Options opt = new BitmapFactory.Options();
	    opt.inPreferredConfig=Bitmap.Config.RGB_565;//表示16位位图 565代表对应三原色占的位数
	    opt.inInputShareable=true;
	    opt.inPurgeable=true;//设置图片可以被回收
	    opt.inTempStorage = new byte[1024*1024*10];
	    
    	Bitmap bitmap  = BitmapFactory.decodeFile(src,opt);//decodeStream
    	Bitmap newBmp=Bitmap.createBitmap(bitmap, x, y, w, h, null, false);
    	saveBitmap2JPG(dest,newBmp);
    	if(bitmap!=null){
    		bitmap.recycle();
    	}
    }
	
    public void resizeImage(String src,String dest,int sample,int quality) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig=Bitmap.Config.RGB_565;//表示16位位图 565代表对应三原色占的位数
		opt.inInputShareable=true;
		opt.inPurgeable=true;//设置图片可以被回收
		opt.inTempStorage = new byte[1024*1024*10];
		opt.inSampleSize = sample;
	    Bitmap bitmap  = BitmapFactory.decodeFile(src,opt);//decodeStream
	    Bitmap newBmp=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), null, false);
	    saveBitmap2JPG(dest,newBmp,quality);
    	if(bitmap!=null){
    		bitmap.recycle();
    	}
    }
    
    
	public void saveBitmap2JPG(String path,Bitmap bitmap){
		  File f = new File(path);//("/sdcard/" + bitName + ".png");
		  FileOutputStream fOut = null;
		  try {
			   f.createNewFile();
			   fOut = new FileOutputStream(f);
			   bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
			   fOut.flush();
			   fOut.close();
		  }catch (FileNotFoundException e) {
			  e.printStackTrace();
		  }catch (IOException e) {
			  e.printStackTrace();
		  }
	}
	
	public void saveBitmap2JPG(String path,Bitmap bitmap,int quality){
		  File f = new File(path);//("/sdcard/" + bitName + ".png");
		  FileOutputStream fOut = null;
		  try {
			   f.createNewFile();
			   fOut = new FileOutputStream(f);
			   bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
			   fOut.flush();
			   fOut.close();
		  }catch (FileNotFoundException e) {
			  e.printStackTrace();
		  }catch (IOException e) {
			  e.printStackTrace();
		  }
	}
    
	
	//
    







}
