package com.hdweiss.codemap.data;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

import com.hdweiss.codemap.util.Utils;

public class CodeMapState implements Serializable {
	private static final long serialVersionUID = 3L;
	
	public String workspaceName;

	public ArrayList<SerializableItem> items = new ArrayList<SerializableItem>();
	public ArrayList<SerializableLink> links = new ArrayList<SerializableLink>();

	public int scrollX = 0;
	public int scrollY = 0;
	public float zoom = 1;


	public CodeMapState(String workspaceName) {
		this.workspaceName = workspaceName;
	}
	
	public void writeState(Project project, Context context) throws IOException {
		byte[] serializeObject = Utils.serializeObject(this);
		
		FileOutputStream fos = new FileOutputStream(getStateFile(project, workspaceName, context));
		fos.write(serializeObject);
		fos.close();
	}

	public static CodeMapState readState(Project project, String workspace, Context context)
			throws IOException {
		FileInputStream fis = new FileInputStream(getStateFile(project, workspace, context));
		byte[] serializedObject = new byte[fis.available()];
		fis.read(serializedObject);
		fis.close();

		CodeMapState result = (CodeMapState) Utils.deserializeObject(serializedObject);
		return result;
	}

	private static File getStateFile(Project project, String workspace, Context context)
			throws FileNotFoundException {
		return new File(getStateFilePath(project, workspace, context));
	}
	
	private static String getStateFilePath (Project project, String workspace, Context context) {
		File directory = project.getProjectDirectory(context);
		return directory.getAbsolutePath() + File.separator + workspace + ".state";
	}

	public static void deleteState(String projectName, Context context) {
		File directory = Project.getProjectDirectory(projectName, context);
		
		File[] toBeDeleted = directory.listFiles(new FileFilter() {
			public boolean accept(File theFile) {
				if (theFile.isFile()) {
					return theFile.getName().endsWith(".state");
				}
				return false;
			}
		});

		for (File deletableFile : toBeDeleted) {
			deletableFile.delete();
		}
	}
	
	
}
