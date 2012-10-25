package com.hdweiss.codemap.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import android.content.Context;

import com.hdweiss.codemap.util.Utils;

public class CodeMapState implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String projectName;

	public CodeMapState(String projectName) {
		this.projectName = projectName;
	}
	
	public void writeState(Context context) throws IOException {
		byte[] serializeObject = Utils.serializeObject(this);
		FileOutputStream fos = context.openFileOutput(getFilename(projectName),
				Context.MODE_PRIVATE);
		fos.write(serializeObject);
		fos.close();
	}

	public static CodeMapState readState(String name, Context context)
			throws IOException {
		FileInputStream fis = context.openFileInput(getFilename(name));
		byte[] serializedObject = new byte[fis.available()];
		fis.read(serializedObject);
		fis.close();

		CodeMapState result = (CodeMapState) Utils.deserializeObject(serializedObject);
		return result;
	}

	private static String getFilename(String projectName) {
		return projectName + ".state";
	}
}