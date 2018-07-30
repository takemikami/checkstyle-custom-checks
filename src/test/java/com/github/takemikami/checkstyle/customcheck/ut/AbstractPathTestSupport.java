package com.github.takemikami.checkstyle.customcheck.ut;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractPathTestSupport {

    protected static final String LF_REGEX = "\\\\n";

    protected static final String CRLF_REGEX = "\\\\r\\\\n";

    /**
     * Returns the exact location for the package where the file is present.
     * @return path for the package name for the file.
     */
    protected abstract String getPackageLocation();

    /**
     * Returns canonical path for the file with the given file name.
     * The path is formed base on the root location.
     * This implementation uses 'src/test/resources/'
     * as a root location.
     * @param filename file name.
     * @return canonical path for the file name.
     * @throws IOException if I/O exception occurs while forming the path.
     */
    protected final String getPath(String filename) throws IOException {
        return new File("src/test/resources/" + getPackageLocation() + "/" + filename)
                .getCanonicalPath();
    }

    protected final String getResourcePath(String filename) {
        return "/" + getPackageLocation() + "/" + filename;
    }

    /** Reads the contents of a file.
     * @param filename the name of the file whose contents are to be read
     * @return contents of the file with all {@code \r\n} replaced by {@code \n}
     * @throws IOException if I/O exception occurs while reading
     */
    protected static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(
                Paths.get(filename)), StandardCharsets.UTF_8)
                .replaceAll(CRLF_REGEX, LF_REGEX);
    }
}
