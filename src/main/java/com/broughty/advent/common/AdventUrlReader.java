package com.broughty.advent.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AdventUrlReader {

    public static final Logger logger = LoggerFactory.getLogger(AdventUrlReader.class);
    /**
     * The file lines read in from the input fieUrl
     */
    protected List<String> fileLines = new ArrayList<>();
    /**
     * The file to read for the calibration - see advent.dayNFile in application.properties
     */
    String fileUrl;
    /**
     * Need the cookie to get the adventofcode.com input files - see advent.cookie in application.properties
     */
    String cookie;

    public AdventUrlReader(String file) {
        this.fileUrl = file;
    }


    public void readFileLines() {

        try {
            URL url = new URI(getFileUrl()).toURL();

            URLConnection urlConn = url.openConnection();

            urlConn.setRequestProperty("Cookie", getCookie());
            urlConn.setUseCaches(true);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(urlConn.getInputStream()));


            fileLines = reader.lines().collect(Collectors.toList());

            reader.close();

        } catch (Exception e) {
            logger.error("Error reading file {} defaulting to empty list.  " +
                    "Probably cookie related - see advent.cookie in application.properties", e.getMessage());
        }
    }


    public String getFileUrl() {
        return fileUrl;
    }

    public String getCookie() {
        return cookie;
    }

    @Autowired
    public final void setCookie(@Value("${advent.cookie}") String cookie) {
        this.cookie = cookie;
    }

    public List<String> getFileLines() {
        return fileLines;
    }

    public void setFileLines(List<String> lines) {
        fileLines = lines;
    }
}
