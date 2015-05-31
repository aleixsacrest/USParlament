package es.upc.fib.prop.usParlament.data;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which using one file for each congress saving information about it.
 * Where first line is congress and each other lines is partition where with name and save data.
 *
 * Created by ondrej on 20.5.15.
 */
public class DataControllerImpl implements DataController {

	private String path;

	public DataControllerImpl(String path) {
		this.path = path;
	}

	@Override
	public String saveCongress(String name, String congress) {
		CongressString congrString;
		try {
			congrString = loadCongressFromFile(name);
		} catch (IOException e) {
			return exceptionMaker(e);
		}
		congrString = editSaveCongress(name, congress, congrString);
		try {
			saveCongressToFile(congrString);
		} catch (IOException e) {
			return exceptionMaker(e);
		} catch (SecurityException e) {
			return exceptionMaker(e);
		}
		return "{}";
	}
	private CongressString editSaveCongress(String name, String congress, CongressString congrString) {
		congrString.congress = congress;
		return congrString;
	}


	@Override
	public String loadCongress(String name) {
		try {
			CongressString congrString = loadCongressFromFile(name);
			return congrString.congress;
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}


	@Override
	public String loadAllCongressesNames() {
		try {
			File dir = new File(path);
			if (!dir.exists()) {
				throw new IOException("Folder with congresses doesn't exists");
			}
			boolean first = true;
			String res = "{\"congressesNames\":[";
			for (File f : dir.listFiles()) {
				if (!first) {
					res += ",";
				}
				res += "\"" +f.getName()+ "\"";
				first = false;
			}
			res += "]}";
			return res;
		} catch (FileNotFoundException e) {
			return exceptionMaker(e);
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}


	@Override
	public String savePartition(String congressName, String partitionName, String partition) {
		CongressString congrString;
		try {
			congrString = loadCongressFromFile(congressName);
		} catch (IOException e) {
			return exceptionMaker(e);
		}
		congrString = editSavePartition(partitionName, partition, congrString);
		try {
			saveCongressToFile(congrString);
		} catch (IOException e) {
			return exceptionMaker(e);
		} catch (SecurityException e) {
			return exceptionMaker(e);
		}
		return "{}";
	}
	private CongressString editSavePartition(String name, String partition, CongressString congrString) {
		congrString.partitions.put(name, partition);
		return congrString;
	}


	@Override
	public String loadPartition(String congressName, String partitionName) {
		try {
			CongressString congrString = loadCongressFromFile(congressName);
			return congrString.partitions.get(partitionName);
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}


	@Override
	public String loadAllPartitionsOfCongress(String congressName) {
		try {
			CongressString congrString = loadCongressFromFile(congressName);
			return listToJson(new ArrayList<String>(congrString.partitions.values()), "partitions");
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}

	@Override
	public String loadAllPartitionNamesOfCongress(String congressName) {
		try {
			CongressString congrString = loadCongressFromFile(congressName);
			return stringListToJson(new ArrayList<String>(congrString.partitions.keySet()), "partitionNames");
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}


	private String listToJson(List<String> list, String name)  {
		boolean first = true;
		String res = "{\"" +name+ "\":[";
		for (String item : list) {
			if (!first) {
				res += ",";
			}
			res += item;
			first = false;
		}
		res += "]}";
		return res;
	}
	private String stringListToJson(List<String> list, String name)  {
		boolean first = true;
		String res = "{\"" +name+ "\":[";
		for (String item : list) {
			if (!first) {
				res += ",";
			}
			res += "\""+item+"\"";
			first = false;
		}
		res += "]}";
		return res;
	}


	private File createFileIfNotExists(String name) throws IOException {
		File dir = createDirIfNotExists();
		File file = new File(path, name);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch(SecurityException e){
				throw new IOException("doesn't have enough privileges to create file.", e);
			}
		}
		return file;
	}

	private File createDirIfNotExists() throws IOException {
		File dir = new File(path);
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch(SecurityException e){
				throw new IOException("doesn't have enough privileges to create folder.", e);
			}
		}
		return dir;
	}


	private CongressString loadCongressFromFile(String fileName) throws IOException {
		File file = createFileIfNotExists(fileName);
		CongressString congress = new CongressString();
		congress.name = fileName;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			congress.congress = br.readLine();
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().equals("")) {
					continue;
				}
				String[] ls = line.split(" ", 2);
				congress.partitions.put(ls[0], ls[1]);
			}
		}
		return congress;
	}

	private void saveCongressToFile(CongressString congress) throws IOException {
		File file = createFileIfNotExists(congress.name);
		try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
			pw.println(congress.congress);
			for (Map.Entry part : congress.partitions.entrySet()) {
				pw.println(part.getKey() + " " + part.getValue());
			}
			pw.flush();
		}
	}


	private String exceptionMaker(Exception e) {
		return "{\"Exception\":{\"Name\":\"" +e.getClass().getSimpleName()+ "\",Message\":\"" +e.getMessage()+ "\"}}";
	}

	private class CongressString {
		String name;
		String congress;
		Map<String, String> partitions = new HashMap();

	}
}
