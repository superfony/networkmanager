package com.fony.networkmanager.webservice;

import android.os.Looper;

import com.fony.networkmanager.webservice.model.StatusEntity;

public class DefaultModuleResponseProcessor implements ModuleResponseProcessor {

	@Override
	public void processResponse(WebServiceBaseHelp baseHttpModule,
								Object parseObj) {
		if (parseObj instanceof StatusEntity) {
			StatusEntity se = (StatusEntity) parseObj;
			Looper.prepare();
			Looper.loop();
			Looper.myLooper().quit();
		}
	}
}
