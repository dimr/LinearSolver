import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import org.apache.commons.lang3.time.StopWatch;

public class Test {

	public static void main(String[] args) {
		String path = "/media/storage/storage/courseEra/Linear and Integer Programming/Assignments/part1TestCases/part1TestCases/unitTests/";
		String path2 = "/media/storage/storage/courseEra/Linear and Integer Programming/Assignments/part1TestCases/part1TestCases/assignmentParts/";
		String path3 = "/media/storage/storage/courseEra/Linear and Integer Programming/Assignments/part2TestCases/part2TestCases/unitTests/";
		String path4 = "/media/storage/storage/courseEra/Linear and Integer Programming/Assignments/part2TestCases/part2TestCases/assignmentParts/";
		String file = "dict7";
		String file2 = "part1.dict";

		String file3 = "dict5";

		String file4 = "part4.dict";
		Stopwatch w = new Stopwatch();
		// Dict d1 = DictReader.initialiize(f);

		File dir = new File(path3);
		String[] files = (dir.list(new DictFilter()));
		for (int i = 0; i < files.length; i++) {
			if (!files[i].equals("dict6"))
				System.out.println(files[i] + " = " + new Solution(DictReader.initialiize(path3 + files[i]), 100).toString());
		}
		System.out.println(w.elapsedTime() + " secs");

	} // MAIN

	private static final class DictFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return !name.endsWith(".ampl") & !name.endsWith(".math") & !name.endsWith(".output") & !name.endsWith(".pdf");

		}
	}

}
